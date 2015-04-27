package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import persistencia.domain.Cliente;
import persistencia.domain.Comercio;
import persistencia.domain.FacturaCliente;
import persistencia.domain.ItemFactura;
import persistencia.domain.MovimientoCaja;
import persistencia.domain.NotaDebito;

import common.Utils;
import common.dto.ClienteDTO;
import common.dto.FacturaClienteDTO;
import common.dto.ItemFacturaDTO;
import common.dto.LocalidadDTO;
import common.dto.NotaDebitoDTO;
import common.dto.ProvinciaDTO;
import common.interfaces.IControlFacturaCliente;
public class ControlFacturaCliente extends UnicastRemoteObject implements IControlFacturaCliente{
	
	private static final long serialVersionUID = 1L;
	public ControlFacturaCliente() throws RemoteException{   } 
	// C - Remito     //loc Rio Cuarto - Moldes	
	public double agregarFacturaClienteTotal(FacturaClienteDTO fc,String tipo,FacturaClienteDTO remito,int nroMC)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ControlCliente cCte = new ControlCliente();
		ControlComercio cDist=new ControlComercio();
		FacturaCliente lnew= Assemblers.getFacturaCliente(fc);
		double result=0;
		FacturaCliente rem=null;
		if(tipo.compareTo("C")==0){
			if(fc.getRemitoNro()!=null && fc.getRemitoNro().compareTo("")!=0){
				if(existeFacturaClienteNroTipo(new Long(fc.getRemitoNro()),"RemitoCliente")){
					rem=buscarFacturaClientePersistencia(new Long(fc.getRemitoNro()),"RemitoCliente");
				}
			}
		}
		try{
			mp.initPersistencia();
			Cliente cte = cCte.buscarClientePersistentePorId(mp,fc.getCliente().getId());
			mp.hacerPersistente(lnew);
			lnew.setPeriodo(Utils.getMes(fc.getFechaImpresion())+"-"+Utils.getAnio(fc.getFechaImpresion()));
			if(rem==null){ //Aca entra por fac C (hecha de una) o por remito
				// SALDO CLIENTE CASO 1 y 2 y la omision del 5  ( + ) 
				if(fc.getCondVenta().compareTo("CONTADO")!=0)
					cte.setDeuda(Utils.redondear(cte.getDeuda() + fc.getImporteTotal(),2));
			}else{		//Estoy facturando un remito
				rem.setRemitoNro("Facturado");
			} 
			for(Iterator items=fc.getItems().iterator();items.hasNext();){
				ItemFacturaDTO itF = (ItemFacturaDTO) items.next();
				ItemFactura itnew= Assemblers.getItemFactura(itF);	
				mp.hacerPersistente(itnew);
				itnew.setFactura(lnew);
			}
			Comercio dist=cDist.buscarUnicaComercioPersistencia(mp);
			Long nroFacturaObt= new Long(0);
			long cod=0;
			if(tipo.compareTo("C")==0){
				nroFacturaObt=dist.getNroFactC();
				lnew.setNroFactura(nroFacturaObt);
				cod=nroFacturaObt.longValue()+1;
				dist.setNroFactC(new Long(String.valueOf(cod)));
			}
			if(tipo.compareTo("Remito")==0){
				nroFacturaObt=dist.getNroRemito();
				lnew.setNroFactura(nroFacturaObt);
				cod=nroFacturaObt.longValue()+1;
				dist.setNroRemito(new Long(String.valueOf(cod)));
			}
			if(rem!=null){
				Set compr = rem.getComprobantesPago();
				for (Iterator itMC=compr.iterator();itMC.hasNext();){
					MovimientoCaja mc = (MovimientoCaja) itMC.next();
					mc.setFactura(lnew);
				}
				if(rem.getFechaPago()!=null){
					Date fpago = Utils.crearFecha2(rem.getFechaPago());
					lnew.setFechaPago(fpago);
					lnew.setImporteAbonado(rem.getImporteAbonado());
				}
			}
			lnew.setCliente(cte);
			if(remito!=null){
				FacturaCliente remito2 = buscarFacturaClientePersistentePorId(mp,remito.getId());
				remito2.setAnulada(true);
				cte.setDeuda(Utils.redondear(cte.getDeuda() - remito2.getImporteTotal(),2));
			}
			if(fc.getCondVenta().compareTo("CONTADO")==0){
				MovimientoCaja mimovDTO = new MovimientoCaja();
				mimovDTO.setCodigo(nroMC);
				mimovDTO.setFecha(fc.getFechaImpresion());
				mimovDTO.setImporte(fc.getImporteTotal());
				mimovDTO.setDescripcion("pago contado");
				mimovDTO.setFormaPago("EFECTIVO");
				mimovDTO.setConFactura(true);
				mimovDTO.setTipoFactura("Factura Cliente-Tipo C");
				mimovDTO.setTipoMovimiento(1);
				mimovDTO.setFactura(lnew);
				mimovDTO.setPeriodo(Utils.getMes(fc.getFechaImpresion())+"-"+Utils.getAnio(fc.getFechaImpresion()));
				mp.hacerPersistente(mimovDTO);
				lnew.setFechaPago(fc.getFechaImpresion());
				lnew.setImporteAbonado(fc.getImporteTotal());
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return result;
	}
	
	public void anularFacturaCliente(Long idF)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			String filtro = "id == "+idF;
			Vector FacturaClienteCol=mp.getObjects(FacturaCliente.class,filtro);
			FacturaCliente fc = (FacturaCliente) FacturaClienteCol.elementAt(0);
			fc.setAnulada(true);
			// SALDO CLIENTE CASO 3 y 4 ( - ) 
			fc.getCliente().setDeuda(Utils.redondear(fc.getCliente().getDeuda()-fc.getImporteTotal(),2));
			mp.commit();
		} catch(Exception e) {
			e.printStackTrace();
			mp.rollback();
		}
	}
	
	public boolean existenFacturasDeCliente(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existen = false;
		try {
			mp.initPersistencia();
			String filtro = "cliente.id=="+id;
			Vector facturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"fechaImpresion ascending");
			if(facturaClientes.size()>0)
				existen=true;
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return existen;
	}
	
	public Vector obtenerFacturaClientesPeriodo(boolean listarRemitosSinFact,String tipoF,int mesLI,int anioLI)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector FacturaClientes2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "tipoFactura==\""+tipoF+"\" && periodo==\""+mesLI+"-"+anioLI+"\"";
			Vector FacturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"fechaImpresion ascending, nroFactura ascending");
			for(int i=0; i<FacturaClientes.size();i++){
				FacturaCliente b = (FacturaCliente)FacturaClientes.elementAt(i);
				boolean agregar=true;
				if(listarRemitosSinFact){
					if(b.getRemitoNro()!=null && b.getRemitoNro().compareTo("Facturado")==0)
						agregar=false;
					/*//existeFacturaDeRemito
					 String filtroRem = " remitoNro == \"" +b.getNroFactura()+ "\"";
					 Vector facturaClienteRem= mp.getObjects(FacturaCliente.class,filtroRem);
					 if (facturaClienteRem.size()==1)
					 agregar=false;*/
				}	
				if(agregar){
					FacturaClienteDTO a= Assemblers.getFacturaClienteDTO(b);
					ClienteDTO cte= Assemblers.getClienteDTO(b.getCliente());
					LocalidadDTO loc= Assemblers.getLocalidadDTO(b.getCliente().getLocalidad());
					ProvinciaDTO prv= Assemblers.getProvinciaDTO(b.getCliente().getLocalidad().getProvincia());
					loc.setProvincia(prv);
					cte.setLocalidad(loc);
					a.setCliente(cte);
					a.setComprobantesPago(Assemblers.getVectorMovimientoCaja(b.getComprobantesPago()));
					FacturaClientes2.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return FacturaClientes2;
	}
	
	public Vector obtenerFacturaClientesPeriodoFiltros(boolean listarRemitosSinFact,String tipoF,int mesLI,int anioLI,String fecha,String numero,String cliente)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector FacturaClientes2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "tipoFactura==\""+tipoF+"\"  && cliente.nombre.toLowerCase().indexOf(\""+cliente.toLowerCase()+"\")>= 0 && periodo==\""+mesLI+"-"+anioLI+"\"";
			Vector FacturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"fechaImpresion ascending, nroFactura ascending");
			for(int i=0; i<FacturaClientes.size();i++){
				FacturaCliente b = (FacturaCliente)FacturaClientes.elementAt(i);
				boolean agregar=true;
				if(listarRemitosSinFact){
					//existeFacturaDeRemito
					String filtroRem = " remitoNro == \"" +b.getNroFactura()+ "\"";
					Vector facturaClienteRem= mp.getObjects(FacturaCliente.class,filtroRem);
					if (facturaClienteRem.size()==1)
						agregar=false;
				}	
				if(agregar && (Utils.comienza(String.valueOf(b.getNroFactura()),numero) && 
						Utils.comienza(common.Utils.getStrUtilDate(b.getFechaImpresion()), fecha))){
					FacturaClienteDTO a= Assemblers.getFacturaClienteDTO(b);
					ClienteDTO cte= Assemblers.getClienteDTO(b.getCliente());
					LocalidadDTO loc= Assemblers.getLocalidadDTO(b.getCliente().getLocalidad());
					ProvinciaDTO prv= Assemblers.getProvinciaDTO(b.getCliente().getLocalidad().getProvincia());
					loc.setProvincia(prv);
					cte.setLocalidad(loc);
					a.setCliente(cte);
					a.setComprobantesPago(Assemblers.getVectorMovimientoCaja(b.getComprobantesPago()));
					FacturaClientes2.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return FacturaClientes2;
	}
	
	public Vector obtenerFacturasClienteFechaLoc(Date fecha,Long idLoc)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector FacturaClientes2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "anulada==false && cliente.localidad.id=="+idLoc;
			Vector FacturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"tipoFactura ascending");
			for(int i=0; i<FacturaClientes.size();i++){
				FacturaCliente b = (FacturaCliente)FacturaClientes.elementAt(i);
				if(b.getFechaImpresion().equals(fecha)){
					if(b.getRemitoNro().compareTo("")==0){
						FacturaClienteDTO a= Assemblers.getFacturaClienteDTO(b);
						ClienteDTO cte= Assemblers.getClienteDTO(b.getCliente());
						a.setCliente(cte);
						FacturaClientes2.add(a);
					}
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return FacturaClientes2;
	}
	
	public boolean existeFacturaClienteNroTipo(Long nroF, String tipoF)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "nroFactura == "+nroF+" && tipoFactura==\""+tipoF+"\"";
			Vector FacturaClienteCol= mp.getObjects(FacturaCliente.class,filtro);
			if (FacturaClienteCol.size()>=1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
	
	public boolean existeFacturaDeRemito(String nroRemito)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "remitoNro == \"" +nroRemito+ "\"";
			Vector FacturaClienteCol= mp.getObjects(FacturaCliente.class,filtro);
			if (FacturaClienteCol.size()==1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
	
	public FacturaClienteDTO buscarFacturaDeRemito(String nroRemito) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		FacturaClienteDTO a = new FacturaClienteDTO();
		try {
			mp.initPersistencia();
			String filtro = "remitoNro == \"" +nroRemito+ "\"";
			Vector FacturaClienteCol= mp.getObjects(FacturaCliente.class,filtro);
			if (FacturaClienteCol.size()>=1){
				FacturaCliente b = (FacturaCliente)(FacturaClienteCol.toArray())[0];
				
				a= Assemblers.getFacturaClienteDTO(b);
				ClienteDTO cte= Assemblers.getClienteDTO(b.getCliente());
				LocalidadDTO loc= Assemblers.getLocalidadDTO(b.getCliente().getLocalidad());
				ProvinciaDTO prv= Assemblers.getProvinciaDTO(b.getCliente().getLocalidad().getProvincia());
				loc.setProvincia(prv);
				cte.setLocalidad(loc);
				a.setCliente(cte);
				a.setComprobantesPago(Assemblers.getVectorMovimientoCaja(b.getComprobantesPago()));
				
				Set elem = b.getItems();
				Vector aelem = new Vector();
				for(Iterator j=elem.iterator();j.hasNext();){
					ItemFactura itv= (ItemFactura) j.next();
					ItemFacturaDTO it= Assemblers.getItemFacturaDTO(itv);
					aelem.add(it);
				}
				a.setItems(aelem);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
	
	public FacturaCliente buscarFacturaClientePersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		FacturaCliente obj = new FacturaCliente();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(FacturaCliente.class,filtro);
		if (col.size()>=1){
			obj = (FacturaCliente)(col.toArray())[0];
		}
		return obj;
	}
	
	public FacturaClienteDTO buscarFacturaCliente(Long id) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		FacturaClienteDTO a = new FacturaClienteDTO();
		try {
			mp.initPersistencia();
			String filtro = "id == "+id;
			Vector FacturaClienteCol= mp.getObjects(FacturaCliente.class,filtro);
			if (FacturaClienteCol.size()>=1){
				FacturaCliente b = (FacturaCliente)(FacturaClienteCol.toArray())[0];
				
				a= Assemblers.getFacturaClienteDTO(b);
				ClienteDTO cte= Assemblers.getClienteDTO(b.getCliente());
				LocalidadDTO loc= Assemblers.getLocalidadDTO(b.getCliente().getLocalidad());
				ProvinciaDTO prv= Assemblers.getProvinciaDTO(b.getCliente().getLocalidad().getProvincia());
				loc.setProvincia(prv);
				cte.setLocalidad(loc);
				a.setCliente(cte);
				a.setComprobantesPago(Assemblers.getVectorMovimientoCaja(b.getComprobantesPago()));
				
				Set elem = b.getItems();
				Vector aelem = new Vector();
				for(Iterator j=elem.iterator();j.hasNext();){
					ItemFactura itv= (ItemFactura) j.next();
					ItemFacturaDTO it= Assemblers.getItemFacturaDTO(itv);
					aelem.add(it);
				}
				a.setItems(aelem);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
	
	public FacturaCliente buscarFacturaClientePersistencia(Long nroF, String tipoF) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		FacturaCliente b = new FacturaCliente();
		try {
			mp.initPersistencia();
			String filtro = "nroFactura == "+nroF+" && tipoFactura==\""+tipoF+"\"";
			Collection FacturaClienteCol= mp.getObjects(FacturaCliente.class,filtro);
			if (FacturaClienteCol.size()>=1){
				b = (FacturaCliente)(FacturaClienteCol.toArray())[0];
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return b;
	}
	
	public boolean facturaAsociada(Long id) throws Exception {
		boolean estaAsoc = false;
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			String filtro = "conFactura==true && factura.id=="+id;
			Vector movsDeFactura= mp.getObjects(MovimientoCaja.class,filtro);
			if (movsDeFactura.size()!=0){
				estaAsoc = true;
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return estaAsoc;
	}
	
	public Vector obtenerDatosEstadisticos(int mesL, int anioL, Long idLoc, Long idVend) throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector todosElem = new Vector();
		Vector facturas = new Vector();
		Vector notas = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "anulada==false && (tipoFactura==\"FacturaCliente-A\" || tipoFactura==\"FacturaCliente-B\" || tipoFactura==\"RemitoCliente\") && periodo==\""+mesL+"-"+anioL+"\"";
			if(idLoc != null){
				filtro+=" && cliente.localidad.id=="+idLoc;
			}
			if(idVend != null){
				filtro+=" && vendedor.id=="+idVend;
			}
			Vector facturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"fechaImpresion ascending,tipoFactura ascending, nroFactura ascending");
			for(int i=0; i<facturaClientes.size();i++){
				FacturaCliente b = (FacturaCliente)facturaClientes.elementAt(i);
				boolean mostrarDatos=true;
				if(b.getTipoFactura().compareTo("RemitoCliente")==0){
					String filtroREM = "remitoNro == \"" +b.getNroFactura()+ "\"";
					Vector facturaClienteCol= mp.getObjects(FacturaCliente.class,filtroREM);
					if (facturaClienteCol.size()==1)
						mostrarDatos=false;
				}
				if(mostrarDatos){
					FacturaClienteDTO a= Assemblers.getFacturaClienteDTO(b);
					ClienteDTO cte= Assemblers.getClienteDTO(b.getCliente());
					a.setCliente(cte);
					facturas.add(a);
				}	
			}
			String filtro2 = "anulada==false && periodo==\""+mesL+"-"+anioL+"\" && cliente!=null";
			if(idLoc != null){
				filtro2+=" && cliente.localidad.id=="+idLoc;
			}
			if(idVend != null){
				filtro2+=" && vendedor.id=="+idVend;
			}
			Vector notasDebito= mp.getObjectsOrdered(NotaDebito.class,filtro2,"fecha ascending");
			for(int i=0; i<notasDebito.size();i++){
				NotaDebito b = (NotaDebito)notasDebito.elementAt(i);
				NotaDebitoDTO a= Assemblers.getNotaDebitoDTO(b);
				ClienteDTO cte= Assemblers.getClienteDTO(b.getCliente());
				a.setCliente(cte);
				notas.add(a);
			}
			for(int i=1;i<32;i++){
				for(int posF=0; posF<facturas.size();posF++){
					FacturaClienteDTO a=(FacturaClienteDTO) facturas.elementAt(posF);
					if(Utils.getDia(a.getFechaImpresion())==i){
						todosElem.add(a);
					}
				}
				for(int posN=0; posN<notas.size();posN++){
					NotaDebitoDTO a=(NotaDebitoDTO) notas.elementAt(posN);
					if(Utils.getDia(a.getFecha())==i){
						todosElem.add(a);
					}
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return todosElem;
	}

	public Vector obtenerFacturasyNotasDebito(int mesLI, int anioLI)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector todosElem = new Vector();
		Vector facturas = new Vector();
		Vector notas = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "tipoFactura==\"FacturaCliente-C\"";
			Vector facturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"fechaImpresion ascending, nroFactura ascending");
			for(int i=0; i<facturaClientes.size();i++){
				FacturaCliente b = (FacturaCliente)facturaClientes.elementAt(i);
				if(Utils.getMes(b.getFechaImpresion())==mesLI && Utils.getAnio(b.getFechaImpresion())==anioLI){
					FacturaClienteDTO a= Assemblers.getFacturaClienteDTO(b);
					ClienteDTO cte= Assemblers.getClienteDTO(b.getCliente());
					a.setCliente(cte);
					facturas.add(a);
				}
			} 
			String filtroNC=" tipoFacturaNC==\"C\" ";
			Vector notasDeb= mp.getObjectsOrdered(NotaDebito.class,filtroNC,"fecha ascending");
			for(int i=0; i<notasDeb.size();i++){
				NotaDebito b = (NotaDebito)notasDeb.elementAt(i);
				if(Utils.getMes(b.getFecha())==mesLI && Utils.getAnio(b.getFecha())==anioLI){
					NotaDebitoDTO a= Assemblers.getNotaDebitoDTO(b);
					ClienteDTO cte= Assemblers.getClienteDTO(b.getCliente());
					a.setCliente(cte);
					notas.add(a);
				}
			}
			for(int i=1;i<32;i++){
				for(int posF=0; posF<facturas.size();posF++){
					FacturaClienteDTO a=(FacturaClienteDTO) facturas.elementAt(posF);
					if(Utils.getDia(a.getFechaImpresion())==i){
						todosElem.add(a);
					}
				}
				
				for(int posN=0; posN<notas.size();posN++){
					NotaDebitoDTO a=(NotaDebitoDTO) notas.elementAt(posN);
					if(Utils.getDia(a.getFecha())==i){
						todosElem.add(a);
					}
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return todosElem;
	}
}

