package server;

import java.util.Collection;
import java.util.Vector;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

public class ManipuladorPersistencia extends Thread{
	
	private PersistenceManager pm;
	private Transaction tx;
	
	
	public ManipuladorPersistencia(){
	}
	
	public synchronized void initPersistencia(){
		pm = SingletonPersistencia.getInstance().getPM();
		tx = pm.currentTransaction();
		while (tx.isActive()) {
			try {
				sleep(5000);// 5 seg
				tx = pm.currentTransaction();
			} catch (Exception ex){
			}
		}
		if(!tx.isActive())
			try {
				tx.begin();
			} catch (Exception ex){
				initPersistencia();
			}
			else
				initPersistencia();
	}
	
	public boolean commit(){
		try {
			tx.commit(); 
		} catch (RuntimeException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean close(){
		try {
			pm.close();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void rollback(){
		if (tx.isActive()) tx.rollback();
	}
	
	public void hacerPersistente(Object obj)throws Exception{
		try{
			pm.makePersistent(obj);
		} catch(Exception e) {
			throw e;
		}
	}
	
	public void borrar(Object obj)throws Exception{
		try{
			pm.deletePersistent(obj);
		} catch(Exception e){
			throw e;
		}
	}
	
	public Vector getAll(Class clase)throws Exception{
		Vector objetos=new Vector();
		try{
			Class clienteClass = clase;
			Extent clnCliente = pm.getExtent(clienteClass, false);
			Query query = pm.newQuery(clnCliente,"");
			Collection coleccion=(Collection)query.execute();
			objetos.addAll(coleccion);
		} catch(Exception e){
			throw e;
		}
		return objetos;
	}
	
	public Vector getObjectsSubc(Class clase,String filter)throws Exception{
		Vector objetos=new Vector();
		try{
			Class clienteClass = clase;
			Extent clnCliente = pm.getExtent(clienteClass, true);
			Query query = pm.newQuery(clnCliente,filter);
			Collection coleccion=(Collection)query.execute();
			objetos.addAll(coleccion);
		} catch(Exception e) {
			throw e;
		}
		return objetos;
	}
	
	public Vector getObjects(Class clase,String filter)throws Exception{
		Vector objetos=new Vector();
		try{
			Class clienteClass = clase;
			Extent clnCliente = pm.getExtent(clienteClass, false);
			Query query = pm.newQuery(clnCliente,filter);
			Collection coleccion=(Collection)query.execute();
			objetos.addAll(coleccion);
		} catch(Exception e) {
			throw e;
		}
		return objetos;
	}
	
	public Vector getAllOrdered(Class clase, String ordering)throws Exception{
		Vector objetos=new Vector();
		try{
			Class clienteClass = clase;
			Extent clnCliente = pm.getExtent(clienteClass, false);
			Query query = pm.newQuery(clnCliente,"");
			query.setOrdering(ordering);  
			Collection coleccion=(Collection)query.execute();
			objetos.addAll(coleccion);
		} catch(Exception e){
			throw e;
		}
		return objetos;
	}
	
	public Vector getObjectsOrdered(Class clase,String filter, String ordering)throws Exception{
		Vector objetos=new Vector();
		try{
			Class clienteClass = clase;
			Extent clnCliente = pm.getExtent(clienteClass, false);
			Query query = pm.newQuery(clnCliente,filter);
			query.setOrdering(ordering); 
			Collection coleccion=(Collection)query.execute();
			objetos.addAll(coleccion);
		} catch(Exception e) {
			throw e;
		}
		return objetos;
	}
	
	public Vector getAllOrderedSubc(Class clase, String ordering)throws Exception{
		Vector objetos=new Vector();
		try{
			Class clienteClass = clase;
			Extent clnCliente = pm.getExtent(clienteClass,true); 
			Query query = pm.newQuery(clnCliente,"");
			query.setOrdering(ordering);  
			Collection coleccion=(Collection)query.execute();
			objetos.addAll(coleccion);
		} catch(Exception e){
			throw e;
		}
		return objetos;
	}
	
	public Vector getObjectsOrderedSubc(Class clase,String filter, String ordering)throws Exception{
		Vector objetos=new Vector();
		try{
			Class clienteClass = clase;
			Extent clnCliente = pm.getExtent(clienteClass, true);
			Query query = pm.newQuery(clnCliente,filter);
			query.setOrdering(ordering);  
			Collection coleccion=(Collection)query.execute();
			objetos.addAll(coleccion);
		} catch(Exception e) {
			throw e;
		}
		return objetos;
	}
	
	public Vector getObjetosPorClase(Class clase,String filter, String ordering, String vars, String parametros,
			Object parametro1,Object parametro2,Object parametro3, boolean extend ) throws Exception {
		Collection objetos = null;
		Vector objetosRetorno=new Vector();
		try {
//			Crear el objeto query y todo lo necesario
			Class clienteClass = clase;
			Extent clnObjeto = pm.getExtent(clienteClass, extend);
			Query query= null;
			query = pm.newQuery(clnObjeto,filter);
			if (vars!=null)
				query.declareVariables(vars);
			if (parametros!=null)
				query.declareParameters(parametros);
			query.setOrdering(ordering);
			
//			Ejecuta el query dependiendo de los parametros
			if (parametro1==null)
				objetos= (Collection)query.execute();
			else {
				if (parametro2==null)
					objetos= (Collection)query.execute(parametro1);
				else {
					if (parametro3==null)
						objetos= (Collection)query.execute(parametro1,parametro2);
					else
						objetos= (Collection)query.execute(parametro1,parametro2,parametro3);
				}
			}
			objetosRetorno.addAll(objetos);
			//	query.closeAll();
		} catch (Exception ex) {
			throw ex;
		}
		return objetosRetorno;
	}
	
	public Transaction getTx() {
		return tx;
	}
}
