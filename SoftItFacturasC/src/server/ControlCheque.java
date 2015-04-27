package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Vector;

import persistencia.domain.Cheque;

import common.Utils;
import common.dto.ChequeDTO;
import common.interfaces.IControlCheque;
public class ControlCheque extends UnicastRemoteObject implements IControlCheque{
	
	private static final long serialVersionUID = 1L;
	public ControlCheque() throws RemoteException{   }
	
	public boolean agregarCheque(ChequeDTO ch)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Cheque cnew= Assemblers.getCheque(ch);
		if (this.existeChequeNumero(ch.getNumero()))
			return false;
		else{
			try{
				mp.initPersistencia();
				mp.hacerPersistente(cnew);
				cnew.setPeriodo(Utils.getMes(ch.getFechaEmision())+"-"+Utils.getAnio(ch.getFechaEmision()));
				mp.commit();
			} finally {
				mp.rollback();
			}
			return true;
		}
	}
	
	public void eliminarCheque(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			Cheque ch = this.buscarChequePersistentePorId(mp,id);
			mp.borrar(ch);
			mp.commit();
		} finally{
			mp.rollback();
		}
	}
	
	public void modificarCheque(Long id,ChequeDTO modificado)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try{
			mp.initPersistencia();
			Cheque cheque = this.buscarChequePersistentePorId(mp,id);
			cheque.setFechaEmision(modificado.getFechaEmision());
			cheque.setFechaVto(modificado.getFechaVto());
			cheque.setBanco(modificado.getBanco());
			cheque.setNumero(modificado.getNumero());
			cheque.setImporte(modificado.getImporte());
			cheque.setLocPlaza(modificado.getLocPlaza());
			cheque.setQuienEntrega(modificado.getQuienEntrega());
			cheque.setEstado(modificado.getEstado());
			cheque.setAQuienRemite(modificado.getAQuienRemite()); 
			cheque.setPeriodo(Utils.getMes(modificado.getFechaEmision())+"-"+Utils.getAnio(modificado.getFechaEmision()));
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public Vector obtenerChequesPeriodo(int mesLI,int anioLI, String estado)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector cheques2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro="periodo==\""+mesLI+"-"+anioLI+"\"";
			if(estado.compareTo("Todos")!=0)
				filtro +=" && estado==\""+estado+"\"";
			Vector cheques= mp.getObjectsOrdered(Cheque.class,filtro,"numero descending"); 
			for(int i=0; i<cheques.size();i++){
				Cheque b = (Cheque)cheques.elementAt(i);
				ChequeDTO a= Assemblers.getChequeDTO(b);
				cheques2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return cheques2;
	}
	
	public Vector obtenerChequesFiltro(int mesLI,int anioLI, String numero, String nombre,String banco, String estado)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector cheques2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro="periodo==\""+mesLI+"-"+anioLI+"\" && quienEntrega.toLowerCase().indexOf(\""+nombre.toLowerCase()+"\")>= 0  && banco.toLowerCase().indexOf(\""+banco.toLowerCase()+"\")>= 0";
			if(estado.compareTo("Todos")!=0)
				filtro +=" && estado==\""+estado+"\"";
			Vector cheques= mp.getObjectsOrdered(Cheque.class,filtro,"numero descending");
			for(int i=0; i<cheques.size();i++){
				Cheque b = (Cheque)cheques.elementAt(i);
				if(Utils.comienza(String.valueOf(b.getNumero()),numero)){
					ChequeDTO a= Assemblers.getChequeDTO(b);
					cheques2.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return cheques2;
	}
	
	public Vector obtenerChequesVencidos(int dia,int mes,int anio)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector cheques2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "estado==\"En Cartera\" && (fechaVto < fechaHoy || (fechaVto >= fechaHoy && fechaVto <= fechaSem))";
			
			java.util.Date diaBusq= new java.util.Date(anio-1900,mes-1,dia);
			java.sql.Date fechaHoy = new java.sql.Date((diaBusq).getTime());
			java.sql.Date fechaSem = new java.sql.Date((diaBusq).getTime()+(3600000*24*10)); //10 diasdesp.
			String parametros = "java.sql.Date fechaHoy, java.sql.Date fechaSem";
			
			Vector cheques= mp.getObjetosPorClase(Cheque.class,filtro,"fechaVto ascending","",parametros,fechaHoy,fechaSem,null,false);
			for(int i=0; i<cheques.size();i++){
				Cheque b = (Cheque)cheques.elementAt(i);
				ChequeDTO a= Assemblers.getChequeDTO(b);
				cheques2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return cheques2;
	}
	
	public boolean existeChequeNumero(Long num)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "numero == "+num;
			Collection localidadCol= mp.getObjects(Cheque.class,filtro);
			if (localidadCol.size()==1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
	
	public ChequeDTO buscarCheque(Long id) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ChequeDTO a = null;
		try {
			mp.initPersistencia();
			String filtro = "id == "+id;
			Collection cheques= mp.getObjects(Cheque.class,filtro);
			if (cheques.size()>=1){
				Cheque b = (Cheque)(cheques.toArray())[0];
				a= Assemblers.getChequeDTO(b);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
	
	public Cheque buscarChequePersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		Cheque obj = new Cheque();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(Cheque.class,filtro);
		if (col.size()>=1){
			obj = (Cheque)(col.toArray())[0];
		}
		return obj;
	}
	
	public boolean puedoEditar(ChequeDTO dto,ChequeDTO modificado)throws Exception{
		try{
			if (dto.getNumero().equals(modificado.getNumero())){
				return true;
			} else {
				if(!this.existeChequeNumero(modificado.getNumero()))
					return true;
				else
					return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void cambiarEstado(Long id,String estado,String remitidoA) throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			Cheque cheque = this.buscarChequePersistentePorId(mp,id);
			cheque.setEstado(estado);
			cheque.setAQuienRemite(remitidoA); 
			mp.commit();
		} catch(Exception e) {
			e.printStackTrace();
			mp.rollback();
		}
	}
		
}
