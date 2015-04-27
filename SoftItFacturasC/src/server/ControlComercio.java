package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Vector;

import persistencia.domain.Comercio;
import persistencia.domain.Localidad;

import common.dto.ComercioDTO;
import common.dto.LocalidadDTO;
import common.interfaces.IControlComercio;
public class ControlComercio extends UnicastRemoteObject implements IControlComercio{
	
	private static final long serialVersionUID = 1L;
	public ControlComercio() throws RemoteException{   }
	
	public boolean agregarComercio(ComercioDTO p)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ControlLocalidad cLoc = new ControlLocalidad();
		Comercio lnew= Assemblers.getComercio(p);
		if (this.existeComercioNombre(p.getNombre()))
			return false;
		else{
			try{
				mp.initPersistencia();
				Localidad loc = cLoc.buscarLocalidadPersistentePorId(mp,p.getLocalidad().getId());
				mp.hacerPersistente(lnew);
				lnew.setLocalidad(loc);
				mp.commit();
			} finally {
				mp.rollback();
			}
			return true;
		}
	}
	
	public void eliminarComercio(Comercio p)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			String filtro = "nombre == \""+p.getNombre()+"\"";
			Vector ComercioCol=mp.getObjects(Comercio.class,filtro);
			if (ComercioCol.size()==1){
				Comercio comercio = (Comercio) ComercioCol.elementAt(0);
				mp.borrar(comercio);
				mp.commit();
			}
		} catch(Exception e) {
			e.printStackTrace();
			mp.rollback();
		}
	}
	
	public void modificarComercio(Long id,ComercioDTO modificado)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try{
			ControlLocalidad cLoc = new ControlLocalidad();
			mp.initPersistencia();
			Comercio comercio = this.buscarComercioPersistentePorId(mp,id);
			Localidad loc = cLoc.buscarLocalidadPersistentePorId(mp,modificado.getLocalidad().getId());
			comercio.setNombre(modificado.getNombre());
			comercio.setTelefono(modificado.getTelefono());
			comercio.setDireccion(modificado.getDireccion());
			comercio.setCuit(modificado.getCuit());
			comercio.setCategoria(modificado.getCategoria());
			comercio.setInicioAct(modificado.getInicioAct());
			comercio.setLocalidad(loc);
			comercio.setNroFactC(modificado.getNroFactC());
			comercio.setNroRemito(modificado.getNroRemito());
			comercio.setNroNotaDebito(modificado.getNroNotaDebito());
			comercio.setNroRecibo(modificado.getNroRecibo());
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public ComercioDTO obtenerComercio()throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ComercioDTO dR = null;
		try {
			mp.initPersistencia();
			Vector comercios= mp.getAll(Comercio.class);
			if(comercios.size()>0){
				Comercio b = (Comercio)comercios.elementAt(0);
				ComercioDTO a = Assemblers.getComercioDTO(b);
				LocalidadDTO loc = Assemblers.getLocalidadDTO(b.getLocalidad());
				a.setLocalidad(loc);
				dR=a;
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return dR;
	}
	
	public Comercio buscarComercioPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		Comercio obj = new Comercio();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(Comercio.class,filtro);
		if (col.size()>=1){
			obj = (Comercio)(col.toArray())[0];
		}
		return obj;
	}
	
	public boolean existeComercioNombre(String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "nombre == \""+nombre+"\"";
			Collection ComercioCol= mp.getObjects(Comercio.class,filtro);
			if (ComercioCol.size()==1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
	
	public Comercio buscarUnicaComercioPersistencia(ManipuladorPersistencia mp) throws Exception {
		Comercio b = new Comercio();
		Collection ComercioCol= mp.getAll(Comercio.class);
		if (ComercioCol.size()>=1){
			b = (Comercio)(ComercioCol.toArray())[0];
		}
		return b;
	}
	
	public boolean puedoEditar(ComercioDTO dto,ComercioDTO modificado)throws Exception{
		try{
			if (dto.getNombre().equals(modificado.getNombre())){
				return true;
			} else {
				if(!this.existeComercioNombre(modificado.getNombre()))
					return true;
				else
					return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
