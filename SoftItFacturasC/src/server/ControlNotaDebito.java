package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import persistencia.domain.Cliente;
import persistencia.domain.Comercio;
import persistencia.domain.ItemFactura;
import persistencia.domain.NotaDebito;

import common.Utils;
import common.dto.ClienteDTO;
import common.dto.ItemFacturaDTO;
import common.dto.LocalidadDTO;
import common.dto.NotaDebitoDTO;
import common.dto.ProvinciaDTO;
import common.interfaces.IControlNotaDebito;

public class ControlNotaDebito extends UnicastRemoteObject implements IControlNotaDebito{
	
	private static final long serialVersionUID = 1L;	
	public ControlNotaDebito() throws RemoteException{   }
	//loc Rio Cuarto - Moldes	
	public double agregarNotaDebitoCliente(NotaDebitoDTO fc)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ControlCliente cCte = new ControlCliente();
		ControlComercio cDist=new ControlComercio();
		NotaDebito ncnew= Assemblers.getNotaDebito(fc);
		double result=0;
		try{
			mp.initPersistencia();
			Cliente cte = cCte.buscarClientePersistentePorId(mp,fc.getCliente().getId());
			mp.hacerPersistente(ncnew);
			ncnew.setPeriodo(Utils.getMes(fc.getFecha())+"-"+Utils.getAnio(fc.getFecha()));
//			SALDO CLIENTE CASO 6  ( se incrementa la deuda- ) 
			cte.setDeuda(Utils.redondear(cte.getDeuda() + fc.getImporteTotal(),2));
			for(Iterator items=fc.getItems().iterator();items.hasNext();){
				ItemFacturaDTO itF = (ItemFacturaDTO) items.next();
				ItemFactura itnew= Assemblers.getItemFactura(itF);
				mp.hacerPersistente(itnew);
				itnew.setFactura(ncnew);
			}
			Comercio dist=cDist.buscarUnicaComercioPersistencia(mp);
			Long nroFacturaObt= new Long(0);
			long cod=0;
			
			nroFacturaObt=dist.getNroNotaDebito();
			ncnew.setNroFactura(nroFacturaObt);
			cod=nroFacturaObt.longValue()+1;
			dist.setNroNotaDebito(new Long(String.valueOf(cod)));
			
			ncnew.setCliente(cte);
			mp.commit();
		} finally {
			mp.rollback();
		}
		return result;
	}
	
	public void anularNotaDebito(Long idNC)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			String filtro = "id == "+idNC;
			Vector notasDebito=mp.getObjects(NotaDebito.class,filtro);
			NotaDebito nc = (NotaDebito) notasDebito.elementAt(0);
			nc.setAnulada(true);
//			SALDO CLIENTE CASO 7  ( + ) 
			if(nc.getTipoFacturaNC().compareTo("A")==0 || nc.getTipoFactura().compareTo("B")==0){
				nc.getCliente().setDeuda(Utils.redondear(nc.getCliente().getDeuda() - nc.getImporteTotal(),2));
			}
			mp.commit();
		} catch(Exception e) {
			e.printStackTrace();
			mp.rollback();
		}
	}
	
	public boolean existenNotaDebitoDeCliente(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existen = false;
		try {
			mp.initPersistencia();
			String filtro = "cliente.id=="+id;
			Vector notasDebitoCol= mp.getObjectsOrdered(NotaDebito.class,filtro,"fecha ascending");
			if(notasDebitoCol.size()>0)
				existen=true;
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return existen;
	}
	
	public Vector obtenerNotasDeDebito(int mesLI,int anioLI)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector notasDeDebito = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "periodo==\""+mesLI+"-"+anioLI+"\" ";
			Vector notasDebitoCol= mp.getObjectsOrdered(NotaDebito.class,filtro,"fecha ascending");
			for(int i=0; i<notasDebitoCol.size();i++){
				NotaDebito b = (NotaDebito)notasDebitoCol.elementAt(i);
				NotaDebitoDTO a= Assemblers.getNotaDebitoDTO(b);
				ClienteDTO cte=null;
				if(b.getCliente()!=null){
					cte= Assemblers.getClienteDTO(b.getCliente());
					LocalidadDTO loc= Assemblers.getLocalidadDTO(b.getCliente().getLocalidad());
					ProvinciaDTO prv= Assemblers.getProvinciaDTO(b.getCliente().getLocalidad().getProvincia());
					loc.setProvincia(prv);
					cte.setLocalidad(loc);
				}
				a.setCliente(cte);
				notasDeDebito.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return notasDeDebito;
	}
	
	public Vector obtenerNotasDeDebitoFiltros(int mesLI,int anioLI,String fecha,String cliente)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector notasDeDebito = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "((cliente!=null && cliente.nombre.toLowerCase().indexOf(\""+cliente.toLowerCase()+"\")>= 0))  && periodo==\""+mesLI+"-"+anioLI+"\" ";
			Vector notaDebitoCol= mp.getObjectsOrdered(NotaDebito.class,filtro,"fecha ascending");
			for(int i=0; i<notaDebitoCol.size();i++){
				NotaDebito b = (NotaDebito)notaDebitoCol.elementAt(i);
				if( Utils.comienza(common.Utils.getStrUtilDate(b.getFecha()), fecha)){
					NotaDebitoDTO a= Assemblers.getNotaDebitoDTO(b);
					ClienteDTO cte=null;
					if(b.getCliente()!=null){
						cte= Assemblers.getClienteDTO(b.getCliente());
						LocalidadDTO loc= Assemblers.getLocalidadDTO(b.getCliente().getLocalidad());
						ProvinciaDTO prv= Assemblers.getProvinciaDTO(b.getCliente().getLocalidad().getProvincia());
						loc.setProvincia(prv);
						cte.setLocalidad(loc);
					}
					a.setCliente(cte);
					notasDeDebito.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return notasDeDebito;
	}
	
	public Vector obtenerNotaDebitoPeriodo(String tipoNC,int mesLI,int anioLI)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector notasDeDebito = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "tipoNotaDebito==\""+tipoNC+"\"";
			Vector notasDebitoCol= mp.getObjectsOrdered(NotaDebito.class,filtro,"fecha ascending");
			for(int i=0; i<notasDebitoCol.size();i++){
				NotaDebito b = (NotaDebito)notasDebitoCol.elementAt(i);
				if(Utils.getMes(b.getFecha())==mesLI && Utils.getAnio(b.getFecha())==anioLI){
					NotaDebitoDTO a= Assemblers.getNotaDebitoDTO(b);
					ClienteDTO cte=null;
					if(b.getCliente()!=null){
						cte= Assemblers.getClienteDTO(b.getCliente());
						LocalidadDTO loc= Assemblers.getLocalidadDTO(b.getCliente().getLocalidad());
						ProvinciaDTO prv= Assemblers.getProvinciaDTO(b.getCliente().getLocalidad().getProvincia());
						loc.setProvincia(prv);
						cte.setLocalidad(loc);
					}
					a.setCliente(cte);
					notasDeDebito.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return notasDeDebito;
	}
	
	public Vector obtenerNotaDebitoPeriodoFiltros(String tipoNC,int mesLI,int anioLI,String fecha,String numero,String cliente)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector notasDeDebito = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "tipoNotaDebito==\""+tipoNC+"\"  && cliente.nombre.toLowerCase().indexOf(\""+cliente.toLowerCase()+"\")>= 0 ";
			Vector notaDebitoCol= mp.getObjectsOrdered(NotaDebito.class,filtro,"fecha ascending");
			for(int i=0; i<notaDebitoCol.size();i++){
				NotaDebito b = (NotaDebito)notaDebitoCol.elementAt(i);
				if(Utils.getMes(b.getFecha())==mesLI && Utils.getAnio(b.getFecha())==anioLI &&
						(Utils.comienza(String.valueOf(b.getNroFactura()),numero) && 
								Utils.comienza(common.Utils.getStrUtilDate(b.getFecha()), fecha))){
					NotaDebitoDTO a= Assemblers.getNotaDebitoDTO(b);
					ClienteDTO cte=null;
					if(b.getCliente()!=null){
						cte= Assemblers.getClienteDTO(b.getCliente());
						LocalidadDTO loc= Assemblers.getLocalidadDTO(b.getCliente().getLocalidad());
						ProvinciaDTO prv= Assemblers.getProvinciaDTO(b.getCliente().getLocalidad().getProvincia());
						loc.setProvincia(prv);
						cte.setLocalidad(loc);
					}
					a.setCliente(cte);
					notasDeDebito.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return notasDeDebito;
	}
	
	public boolean existeNotaDebitoNroTipo(Long nroF, String tipoF)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "nroNotaDebito == "+nroF+" && tipoNotaDebito==\""+tipoF+"\"";
			Vector notaDebitoCol= mp.getObjects(NotaDebito.class,filtro);
			if (notaDebitoCol.size()>=1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
	
	public NotaDebito buscarNotaDebitoPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		NotaDebito obj = new NotaDebito();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(NotaDebito.class,filtro);
		if (col.size()>=1){
			obj = (NotaDebito)(col.toArray())[0];
		}
		return obj;
	}
	
	public NotaDebitoDTO buscarNotaDebito(Long id) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		NotaDebitoDTO a = new NotaDebitoDTO();
		try {
			mp.initPersistencia();
			String filtro = "id == "+id;
			Vector notaDebitoCol= mp.getObjects(NotaDebito.class,filtro);
			if (notaDebitoCol.size()>=1){
				NotaDebito b = (NotaDebito)(notaDebitoCol.toArray())[0];
				a= Assemblers.getNotaDebitoDTO(b);
				ClienteDTO cte=null;
				if(b.getCliente()!=null){
					cte= Assemblers.getClienteDTO(b.getCliente());
					LocalidadDTO loc= Assemblers.getLocalidadDTO(b.getCliente().getLocalidad());
					ProvinciaDTO prv= Assemblers.getProvinciaDTO(b.getCliente().getLocalidad().getProvincia());
					loc.setProvincia(prv);
					cte.setLocalidad(loc);
				}	
				
				Set elem = b.getItems();
				Vector aelem = new Vector();
				for(Iterator j=elem.iterator();j.hasNext();){
					ItemFactura itv= (ItemFactura) j.next();
					ItemFacturaDTO it= Assemblers.getItemFacturaDTO(itv);
					aelem.add(it);
				}
				a.setItems(aelem);
				a.setCliente(cte);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
	
}
