package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Vector;

import persistencia.domain.Localidad;
import persistencia.domain.Provincia;

import common.dto.ClienteDTO;
import common.dto.ComercioDTO;
import common.dto.LocalidadDTO;
import common.dto.ProvinciaDTO;
import common.interfaces.IControlLocalidad;
public class ControlLocalidad extends UnicastRemoteObject implements IControlLocalidad{
	
	private static final long serialVersionUID = 1L;
	public ControlLocalidad() throws RemoteException{   }
	
	public boolean agregarLocalidad(LocalidadDTO p)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ControlProvincia cProv = new ControlProvincia();
		Localidad lnew= Assemblers.getLocalidad(p);
		if (this.existeLocalidadNombre(p.getNombre()))
			return false;
		else{
			try{
				mp.initPersistencia();
				Provincia prov = cProv.buscarProvinciaPersistentePorId(mp,p.getProvincia().getId());
				mp.hacerPersistente(lnew);
				lnew.setProvincia(prov);
				mp.commit();
			} finally {
				mp.rollback();
			}
			return true;
		}
	}
	
	public void eliminarLocalidad(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			Localidad localidad = this.buscarLocalidadPersistentePorId(mp,id);
			mp.borrar(localidad);
			mp.commit();
		} finally{
			mp.rollback();
		}
	}
	
	public void modificarLocalidad(Long id,LocalidadDTO modificado)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try{
			ControlProvincia cProv = new ControlProvincia();
			mp.initPersistencia();
			Provincia prov = cProv.buscarProvinciaPersistentePorId(mp,modificado.getProvincia().getId());
			Localidad localidad = this.buscarLocalidadPersistentePorId(mp,id);
			localidad.setNombre(modificado.getNombre());
			localidad.setCodPostal(modificado.getCodPostal());
			localidad.setProvincia(prov);
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public Vector obtenerLocalidades()throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector localidads2 = new Vector();
		try {
			mp.initPersistencia();
			Vector localidads= mp.getAllOrdered(Localidad.class,"nombre ascending");
			for(int i=0; i<localidads.size();i++){
				Localidad b = (Localidad)localidads.elementAt(i);
				LocalidadDTO a= Assemblers.getLocalidadDTO(b);
				ProvinciaDTO prov= Assemblers.getProvinciaDTO(b.getProvincia());
				a.setProvincia(prov);
				localidads2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return localidads2;
	}
	
	public Vector obtenerLocalidadesFiltro(String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector localidads2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro="nombre.toLowerCase().indexOf(\""+nombre.toLowerCase()+"\")>= 0";
			Vector localidads= mp.getObjectsOrdered(Localidad.class,filtro,"nombre ascending");
			for(int i=0; i<localidads.size();i++){
				Localidad b = (Localidad)localidads.elementAt(i);
				LocalidadDTO a= Assemblers.getLocalidadDTO(b);
				ProvinciaDTO prov= Assemblers.getProvinciaDTO(b.getProvincia());
				a.setProvincia(prov);
				localidads2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return localidads2;
	}
	
	public boolean existeLocalidadNombre(String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "nombre == \""+nombre+"\"";
			Collection localidadCol= mp.getObjects(Localidad.class,filtro);
			if (localidadCol.size()==1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
	
	public LocalidadDTO buscarLocalidad(Long id) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		LocalidadDTO a = new LocalidadDTO();
		try {
			mp.initPersistencia();
			String filtro = "id == "+id;
			Collection localidadCol= mp.getObjects(Localidad.class,filtro);
			if (localidadCol.size()>=1){
				Localidad b = (Localidad)(localidadCol.toArray())[0];
				a= Assemblers.getLocalidadDTO(b);
				ProvinciaDTO prov= Assemblers.getProvinciaDTO(b.getProvincia());
				a.setProvincia(prov);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
	
	public Localidad buscarLocalidadPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		Localidad obj = new Localidad();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(Localidad.class,filtro);
		if (col.size()>=1){
			obj = (Localidad)(col.toArray())[0];
		}
		return obj;
	}
	
	public boolean puedoEditar(LocalidadDTO dto,LocalidadDTO modificado)throws Exception{
		try{
			if (dto.getNombre().equals(modificado.getNombre())){
				return true;
			} else {
				if(!this.existeLocalidadNombre(modificado.getNombre()))
					return true;
				else
					return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean localidadAsociada(Long id) throws Exception {
		boolean estaAsoc = false;
		ControlCliente ctrlCte = new ControlCliente();
		ControlComercio ctrlDist= new ControlComercio();
		try {
			Vector clientes = ctrlCte.obtenerClientes();
			ClienteDTO c;
			for (int i=0; i<clientes.size() && !estaAsoc; i++ ) {
				c = (ClienteDTO)clientes.elementAt(i);
				if ((c.getLocalidad().getId().equals(id))){
					estaAsoc = true;
				}
			}
			ComercioDTO distrib = ctrlDist.obtenerComercio();
			if ((distrib.getLocalidad().getId().equals(id)) ){
				estaAsoc = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
			estaAsoc = true;
		}
		return estaAsoc;
	}
	
}
