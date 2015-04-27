package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import persistencia.domain.Cliente;
import persistencia.domain.FacturaCliente;
import persistencia.domain.Localidad;
import persistencia.domain.MovimientoCaja;
import persistencia.domain.NotaDebito;

import common.Utils;
import common.dto.ClienteDTO;
import common.dto.FacturaClienteDTO;
import common.dto.LocalidadDTO;
import common.dto.MovimientoCajaDTO;
import common.dto.NotaDebitoDTO;
import common.dto.ProvinciaDTO;
import common.interfaces.IControlCliente;
public class ControlCliente extends UnicastRemoteObject implements IControlCliente{
	
	private static final long serialVersionUID = 1L;
	public ControlCliente() throws RemoteException{   }
	
	public boolean agregarCliente(ClienteDTO p)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ControlLocalidad cLoc = new ControlLocalidad();
		Cliente lnew= Assemblers.getCliente(p);
		if (this.existeClienteNombre(p.getNombre()))
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
	
	public void eliminarCliente(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Cliente cliente=null;
		boolean asoc=clienteAsociado(id);
		try {
			mp.initPersistencia();
			cliente = this.buscarClientePersistentePorId(mp,id);
			if(!asoc)
				mp.borrar(cliente);
			else
				cliente.setEliminado(true);
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public void modificarCliente(Long id,ClienteDTO modificado)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try{
			ControlLocalidad cLoc = new ControlLocalidad();
			mp.initPersistencia();
			Cliente cliente = this.buscarClientePersistentePorId(mp,id);
			Localidad loc = cLoc.buscarLocalidadPersistentePorId(mp,modificado.getLocalidad().getId());
			cliente.setCodigo(modificado.getCodigo());
			cliente.setNombre(modificado.getNombre());
			cliente.setTelefono(modificado.getTelefono());
			cliente.setDireccion(modificado.getDireccion());
			cliente.setCuit(modificado.getCuit());
			cliente.setIngBrutosCl(modificado.getIngBrutosCl());
			cliente.setIvaCl(modificado.getIvaCl());
			cliente.setLocalidad(loc);
			cliente.setDeuda(modificado.getDeuda());
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public Vector obtenerClientes()throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector Clientes2 = new Vector();
		try {
			mp.initPersistencia();
			Vector clientes=null;
			String filtro=" eliminado=="+false;
			clientes= mp.getObjectsOrdered(Cliente.class,filtro,"nombre ascending");
			for(int i=0; i<clientes.size();i++){
				Cliente b = (Cliente)clientes.elementAt(i);
				ClienteDTO a= Assemblers.getClienteDTO(b);
				LocalidadDTO loc= Assemblers.getLocalidadDTO(b.getLocalidad());
				a.setLocalidad(loc);
				Clientes2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return Clientes2;
	}
	
	public Vector obtenerClientesFiltro(String nombre,String codigo)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector Clientes2 = new Vector();
		try {
			mp.initPersistencia();
			Vector clientes=null;
			String filtro="eliminado=="+false+" && nombre.toLowerCase().indexOf(\""+nombre.toLowerCase()+"\")>= 0";
			clientes= mp.getObjectsOrdered(Cliente.class,filtro,"nombre ascending");
			for(int i=0; i<clientes.size();i++){
				Cliente b = (Cliente)clientes.elementAt(i);
				if (Utils.comienza(String.valueOf(b.getCodigo()), codigo)){
					ClienteDTO a= Assemblers.getClienteDTO(b);
					LocalidadDTO loc= Assemblers.getLocalidadDTO(b.getLocalidad());
					a.setLocalidad(loc);
					Clientes2.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return Clientes2;
	}
	
	public Vector obtenerClientesAIDeLocalidadYDeuda(Long idLoc,String todos)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector Clientes2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "eliminado=="+false+" && localidad.id == "+idLoc;
			Vector Clientes= mp.getObjectsOrdered(Cliente.class,filtro,"nombre ascending");
			for(int i=0; i<Clientes.size();i++){
				Cliente b = (Cliente)Clientes.elementAt(i);
				String filtroFC = " anulada==false && cliente.id=="+b.getId()+" && (tipoFactura==\"FacturaCliente-C\" || tipoFactura==\"RemitoCliente\")";
				Vector facturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtroFC,"fechaImpresion descending");
				boolean agregar = false;
				if(facturaClientes.size()>0){
					FacturaCliente ufc = (FacturaCliente)facturaClientes.elementAt(0);
					if(todos.compareTo("Todos")==0) agregar=true;
					if(todos.compareTo("Inactivos")==0){
						Date hoy= new Date();
						long fechaFact = ufc.getFechaImpresion().getTime();
						long hoyT = hoy.getTime();
						long tiempo = hoyT - fechaFact; //tiempo en milisegundos
//						Muestro el resultado en días
						long t=tiempo/(1000*60*60*24);
						if(t>15) agregar=true;
					}
					if(agregar){	
						if(todos.compareTo("Todos")==0){
							if(b.getDeuda()!=0){
								Clientes2.add(b.getCodigo());
								Clientes2.add(b.getNombre());
								Clientes2.add(Utils.getStrUtilDate(ufc.getFechaImpresion()));
								Clientes2.add(Utils.ordenarDosDecimales(b.getDeuda()));
							}
						}else{
							Clientes2.add(b.getCodigo());
							Clientes2.add(b.getNombre());
							Clientes2.add(Utils.getStrUtilDate(ufc.getFechaImpresion()));
							Clientes2.add(Utils.ordenarDosDecimales(b.getDeuda()));
						}
					}
					
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return Clientes2;
	}
	
	public boolean existeClienteNombre(String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "nombre == \""+nombre+"\"";
			Collection ClienteCol= mp.getObjects(Cliente.class,filtro);
			if (ClienteCol.size()==1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
	
	public boolean existeClienteCodigo(Long codigo)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "codigo == "+codigo;
			Collection ClienteCol= mp.getObjects(Cliente.class,filtro);
			if (ClienteCol.size()==1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
	
	public ClienteDTO buscarCliente(Long id) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ClienteDTO a = new ClienteDTO();
		try {
			mp.initPersistencia();
			String filtro = "id == "+id;
			Collection ClienteCol= mp.getObjects(Cliente.class,filtro);
			if (ClienteCol.size()>=1){
				Cliente b = (Cliente)(ClienteCol.toArray())[0];
				
				a= Assemblers.getClienteDTO(b);
				LocalidadDTO loc= Assemblers.getLocalidadDTO(b.getLocalidad());
				a.setLocalidad(loc);
				ProvinciaDTO prov= Assemblers.getProvinciaDTO(b.getLocalidad().getProvincia());
				loc.setProvincia(prov);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
	
	public Cliente buscarClientePersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		Cliente obj = new Cliente();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(Cliente.class,filtro);
		if (col.size()>=1){
			obj = (Cliente)(col.toArray())[0];
		}
		return obj;
	}
	
	public boolean puedoEditarNombre(ClienteDTO dto,ClienteDTO modificado)throws Exception{
		try{
			if (dto.getNombre().equals(modificado.getNombre())){
				return true;
			} else {
				if(!this.existeClienteNombre(modificado.getNombre()))
					return true;
				else
					return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean puedoEditarCodigo(ClienteDTO dto,ClienteDTO modificado)throws Exception{
		try{
			if(dto.getCodigo()!=null){
				if (dto.getCodigo().equals(modificado.getCodigo())){
					return true;
				} else {
					if(!this.existeClienteCodigo(modificado.getCodigo()))
						return true;
					else
						return false;
				}
			}else{
				if(!this.existeClienteCodigo(modificado.getCodigo()))
					return true;
				else
					return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Vector obtenerMovimientosCajaDeFactura(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector movs = new Vector();
		try {
			mp.initPersistencia();
			String filtro="factura.id=="+id;
			Vector movscaja= mp.getObjectsOrdered(MovimientoCaja.class,filtro,"fechaMC ascending");
			for(int i=0; i<movscaja.size();i++){
				MovimientoCaja b = (MovimientoCaja)movscaja.elementAt(i);
				MovimientoCajaDTO a= Assemblers.getMovimientoCajaDTO(b);
				movs.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return movs;
	}
	
	public Vector obtenerNotasDeDebitoDeFactura(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector nDebito = new Vector();
		try {
			mp.initPersistencia();
			String filtro=" anulada==false && idFacturaNC=="+id;
			Vector notas= mp.getObjectsOrdered(NotaDebito.class,filtro,"fecha ascending");
			for(int i=0; i<notas.size();i++){
				NotaDebito b = (NotaDebito)notas.elementAt(i);
				NotaDebitoDTO a= Assemblers.getNotaDebitoDTO(b);
				nDebito.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return nDebito;
	}
	
	public boolean clienteAsociado(Long id) throws Exception {
		boolean estaAsoc = false;
		ControlFacturaCliente ctrlFCte = new ControlFacturaCliente();
		try {
			estaAsoc = ctrlFCte.existenFacturasDeCliente(id);
		} catch(Exception e) {
			e.printStackTrace();
			estaAsoc = true;
		}
		return estaAsoc;
	}
	
	public Vector obtenerFacturasDeCliente(int mesLI,int anioLI,String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector FacturaClientes2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = " anulada==false && periodo==\""+mesLI+"-"+anioLI+"\" && (tipoFactura==\"FacturaCliente-C\" || tipoFactura==\"RemitoCliente\") && cliente.nombre==\""+nombre+"\"";
			Vector FacturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"fechaImpresion ascending");
			for(int i=0; i<FacturaClientes.size();i++){
				FacturaCliente b = (FacturaCliente)FacturaClientes.elementAt(i);
				FacturaClienteDTO a= Assemblers.getFacturaClienteDTO(b); 
				ClienteDTO cte= Assemblers.getClienteDTO(b.getCliente());
				a.setCliente(cte);
				a.setComprobantesPago(Assemblers.getVectorMovimientoCaja( b.getComprobantesPago()));
				FacturaClientes2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return FacturaClientes2;
	}	
	
	//retorna la factura cte sin los datos de los pagos efectuados
	public FacturaClienteDTO obtenerUltimaFacturasCliente(Long idCte)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		FacturaClienteDTO a = null;
		try {
			mp.initPersistencia();
			String filtro = " anulada==false && (tipoFactura==\"FacturaCliente-C\" || tipoFactura==\"RemitoCliente\") && cliente.id=="+idCte;
			Vector FacturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"fechaImpresion descending");
			if(FacturaClientes.size()>0){
				FacturaCliente b = (FacturaCliente)FacturaClientes.elementAt(0);
				a= Assemblers.getFacturaClienteDTO(b); 
				ClienteDTO cte= Assemblers.getClienteDTO(b.getCliente());
				a.setCliente(cte);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return a;
	}	
	
	public Vector obtenerClientesCriterios(Long idLoc,Long idZona, Long idVend)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector Clientes2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro=" eliminado=="+false;
			Vector clientes=null;
			if(idLoc != null){
				filtro+=" && localidad.id=="+idLoc;
			}
			if(idZona != null){
				filtro+=" && zona.id=="+idZona;
			}
			if(idVend != null){
				filtro+=" && vendedor.id=="+idVend;
			}
			clientes= mp.getObjectsOrdered(Cliente.class,filtro,"localidad.nombre ascending, zona.nombre ascending, nombre ascending");
			for(int i=0; i<clientes.size();i++){
				Cliente b = (Cliente)clientes.elementAt(i);
				ClienteDTO a= Assemblers.getClienteDTO(b);
				LocalidadDTO local= Assemblers.getLocalidadDTO(b.getLocalidad());
				a.setLocalidad(local);
				Clientes2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return Clientes2;
	}
	
	public Long obtenerNroCliente()throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		long cod=0;
		try{
			mp.initPersistencia();
			Vector socioCol= mp.getAllOrdered(Cliente.class,"codigo descending");
			if(socioCol.size()==0)cod=1;
			else{
				Cliente c = (Cliente)socioCol.elementAt(0);
				cod=c.getCodigo().longValue()+1;
			}
			mp.commit();
			return new Long(cod);
		} finally {
			mp.rollback();
		}
	}
}
