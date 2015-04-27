package server;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import persistencia.domain.Cheque;
import persistencia.domain.Cliente;
import persistencia.domain.Comercio;
import persistencia.domain.FacturaCliente;
import persistencia.domain.ItemFactura;
import persistencia.domain.Localidad;
import persistencia.domain.MovimientoCaja;
import persistencia.domain.NotaDebito;
import persistencia.domain.PlanillaES;
import persistencia.domain.Provincia;

import common.dto.ChequeDTO;
import common.dto.ClienteDTO;
import common.dto.ComercioDTO;
import common.dto.FacturaClienteDTO;
import common.dto.ItemFacturaDTO;
import common.dto.LocalidadDTO;
import common.dto.MovimientoCajaDTO;
import common.dto.NotaDebitoDTO;
import common.dto.PlanillaESDTO;
import common.dto.ProvinciaDTO;

public class Assemblers {
	
	public Assemblers() {
	}
	
	/* ItemFactura */
	public static ItemFacturaDTO getItemFacturaDTO(ItemFactura objeto) {
		ItemFacturaDTO objetoDTO = new ItemFacturaDTO();
		objetoDTO.setId(objeto.getId());
		objetoDTO.setCantidad(objeto.getCantidad());
		objetoDTO.setPrUnit(objeto.getPrUnit());
		objetoDTO.setProducto(objeto.getProducto());
		objetoDTO.setPrTotal(objeto.getPrTotal());
		return objetoDTO;
	}
	
	public static ItemFactura getItemFactura(ItemFacturaDTO objetoDTO) {
		ItemFactura objeto = new ItemFactura();
		objeto.setCantidad(objetoDTO.getCantidad());
		objeto.setProducto(objetoDTO.getProducto());
		objeto.setPrUnit(objetoDTO.getPrUnit());
		objeto.setPrTotal(objetoDTO.getPrTotal());
		return objeto;
	}
	
	public static Set getConjuntoItemFactura(Vector v){
		Set aux = new HashSet();
		Iterator it = v.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==ItemFacturaDTO.class) {
				aux.add(getItemFactura((ItemFacturaDTO) obj));
			}
		}
		return aux;
	}
	
	public static Vector getVectorItemFactura(Set s) {
		Vector auxDTO = new Vector();
		auxDTO.clear();
		Iterator it = s.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==ItemFactura.class) {
				auxDTO.add(getItemFacturaDTO((ItemFactura) obj));
			}
		}
		return auxDTO;
	}
	
	
	/* Cheque */
	public static ChequeDTO getChequeDTO(Cheque objeto) {
		ChequeDTO objetoDTO = new ChequeDTO();
		objetoDTO.setId(objeto.getId());
		objetoDTO.setFechaEmision(objeto.getFechaEmision());
		objetoDTO.setFechaVto(objeto.getFechaVto());
		objetoDTO.setBanco(objeto.getBanco());
		objetoDTO.setNumero(objeto.getNumero());
		objetoDTO.setImporte(objeto.getImporte());
		objetoDTO.setLocPlaza(objeto.getLocPlaza());
		objetoDTO.setQuienEntrega(objeto.getQuienEntrega());
		objetoDTO.setEstado(objeto.getEstado());
		objetoDTO.setAQuienRemite(objeto.getAQuienRemite()); 
		return objetoDTO;
	}
	
	public static Cheque getCheque(ChequeDTO objetoDTO) {
		Cheque objeto = new Cheque();
		objeto.setFechaEmision(objetoDTO.getFechaEmision());
		objeto.setFechaVto(objetoDTO.getFechaVto());
		objeto.setBanco(objetoDTO.getBanco());
		objeto.setNumero(objetoDTO.getNumero());
		objeto.setImporte(objetoDTO.getImporte());
		objeto.setLocPlaza(objetoDTO.getLocPlaza());
		objeto.setQuienEntrega(objetoDTO.getQuienEntrega());
		objeto.setEstado(objetoDTO.getEstado());
		objeto.setAQuienRemite(objetoDTO.getAQuienRemite()); 
		return objeto;
	}
	
	public static Set getConjuntoCheque(Vector v){
		Set aux = new HashSet();
		Iterator it = v.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==ChequeDTO.class) {
				aux.add(getCheque((ChequeDTO) obj));
			}
		}
		return aux;
	}
	
	public static Vector getVectorCheque(Set s) {
		Vector auxDTO = new Vector();
		auxDTO.clear();
		Iterator it = s.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==Cheque.class) {
				auxDTO.add(getChequeDTO((Cheque) obj));
			}
		}
		return auxDTO; 
	}
	
	/* Cliente */
	public static ClienteDTO getClienteDTO(Cliente objeto) {
		ClienteDTO objetoDTO = new ClienteDTO();
		objetoDTO.setId(objeto.getId());
		objetoDTO.setNombre(objeto.getNombre());
		objetoDTO.setTelefono(objeto.getTelefono());
		objetoDTO.setDireccion(objeto.getDireccion()); 
		objetoDTO.setCuit(objeto.getCuit());
		objetoDTO.setCodigo(objeto.getCodigo());
		objetoDTO.setIvaCl(objeto.getIvaCl());
		objetoDTO.setIngBrutosCl(objeto.getIngBrutosCl());
		objetoDTO.setEliminado(objeto.isEliminado());
		objetoDTO.setDeuda(objeto.getDeuda());
		return objetoDTO;
	}
	
	public static Cliente getCliente(ClienteDTO objetoDTO) {
		Cliente objeto = new Cliente();
		objeto.setNombre(objetoDTO.getNombre());
		objeto.setTelefono(objetoDTO.getTelefono());
		objeto.setDireccion(objetoDTO.getDireccion());
		objeto.setCuit(objetoDTO.getCuit());
		objeto.setCodigo(objetoDTO.getCodigo());
		objeto.setIvaCl(objetoDTO.getIvaCl());
		objeto.setIngBrutosCl(objetoDTO.getIngBrutosCl());
		objeto.setEliminado(objetoDTO.isEliminado());
		objeto.setDeuda(objetoDTO.getDeuda());
		return objeto;
	}
	
	public static Set getConjuntoCliente(Vector v){
		Set aux = new HashSet();
		Iterator it = v.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==ClienteDTO.class) {
				aux.add(getCliente((ClienteDTO) obj));
			}
		}
		return aux;
	}
	
	public static Vector getVectorCliente(Set s) {
		Vector auxDTO = new Vector();
		auxDTO.clear();
		Iterator it = s.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==Cliente.class) {
				auxDTO.add(getClienteDTO((Cliente) obj));
			}
		}
		return auxDTO;
	}
	
	/* Comercio */
	public static ComercioDTO getComercioDTO(Comercio objeto) {
		ComercioDTO objetoDTO = new ComercioDTO();
		objetoDTO.setId(objeto.getId());
		objetoDTO.setNombre(objeto.getNombre());
		objetoDTO.setTelefono(objeto.getTelefono());
		objetoDTO.setDireccion(objeto.getDireccion()); 
		objetoDTO.setCuit(objeto.getCuit());
		objetoDTO.setCategoria(objeto.getCategoria());
		objetoDTO.setInicioAct(objeto.getInicioAct());
		objetoDTO.setNroFactC(objeto.getNroFactC());
		objetoDTO.setNroRemito(objeto.getNroRemito());
		objetoDTO.setNroNotaDebito(objeto.getNroNotaDebito());
		objetoDTO.setNroRecibo(objeto.getNroRecibo());
		return objetoDTO;
	}
	
	public static Comercio getComercio(ComercioDTO objetoDTO) {
		Comercio objeto = new Comercio();
		objeto.setNombre(objetoDTO.getNombre());
		objeto.setTelefono(objetoDTO.getTelefono());
		objeto.setDireccion(objetoDTO.getDireccion());
		objeto.setCuit(objetoDTO.getCuit());
		objeto.setCategoria(objetoDTO.getCategoria());
		objeto.setInicioAct(objetoDTO.getInicioAct());
		objeto.setNroFactC(objetoDTO.getNroFactC());
		objeto.setNroRemito(objetoDTO.getNroRemito());
		objeto.setNroNotaDebito(objetoDTO.getNroNotaDebito());
		objeto.setNroRecibo(objetoDTO.getNroRecibo());
		return objeto;
	}
	
	public static Set getConjuntoComercio(Vector v){
		Set aux = new HashSet();
		Iterator it = v.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==ComercioDTO.class) {
				aux.add(getComercio((ComercioDTO) obj));
			}
		}
		return aux;
	}
	
	public static Vector getVectorComercio(Set s) {
		Vector auxDTO = new Vector();
		auxDTO.clear();
		Iterator it = s.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==Comercio.class) {
				auxDTO.add(getComercioDTO((Comercio) obj));
			}
		}
		return auxDTO;
	}
	
	/* FacturaCliente */
	public static FacturaClienteDTO getFacturaClienteDTO(FacturaCliente objeto) {
		FacturaClienteDTO objetoDTO = new FacturaClienteDTO();
		objetoDTO.setId(objeto.getId());
		objetoDTO.setNroFactura(objeto.getNroFactura());
		objetoDTO.setTipoFactura(objeto.getTipoFactura());
		objetoDTO.setImporteTotal(objeto.getImporteTotal());
		objetoDTO.setAnulada(objeto.isAnulada());
		objetoDTO.setFechaImpresion(objeto.getFechaImpresion());
		objetoDTO.setFechaPago(objeto.getFechaPago());
		objetoDTO.setImporteAbonado(objeto.getImporteAbonado());
		objetoDTO.setCondVenta(objeto.getCondVenta());
		objetoDTO.setIva(objeto.getIva());
		objetoDTO.setRemitoNro(objeto.getRemitoNro());
		objetoDTO.setIngrBrutos(objeto.getIngrBrutos());
		return objetoDTO;
	}
	
	public static FacturaCliente getFacturaCliente(FacturaClienteDTO objetoDTO) {
		FacturaCliente objeto = new FacturaCliente();
		objeto.setNroFactura(objetoDTO.getNroFactura());
		objeto.setTipoFactura(objetoDTO.getTipoFactura());
		objeto.setImporteTotal(objetoDTO.getImporteTotal());
		objeto.setAnulada(objetoDTO.isAnulada());
		objeto.setFechaImpresion(objetoDTO.getFechaImpresion());
		objeto.setFechaPago(objetoDTO.getFechaPago());
		objeto.setImporteAbonado(objetoDTO.getImporteAbonado());
		objeto.setCondVenta(objetoDTO.getCondVenta());
		objeto.setIva(objetoDTO.getIva());
		objeto.setRemitoNro(objetoDTO.getRemitoNro());
		objeto.setIngrBrutos(objetoDTO.getIngrBrutos());
		return objeto;
	}
	
	public static Set getConjuntoFacturaCliente(Vector v){
		Set aux = new HashSet();
		Iterator it = v.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==FacturaClienteDTO.class) {
				aux.add(getFacturaCliente((FacturaClienteDTO) obj));
			}
		}
		return aux;
	}
	
	public static Vector getVectorFacturaCliente(Set s) {
		Vector auxDTO = new Vector();
		auxDTO.clear();
		Iterator it = s.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==FacturaCliente.class) {
				auxDTO.add(getFacturaClienteDTO((FacturaCliente) obj));
			}
		}
		return auxDTO;
	}
	
	
	/* MovimientoCaja */
	public static MovimientoCajaDTO getMovimientoCajaDTO(MovimientoCaja objeto) {
		MovimientoCajaDTO objetoDTO = new MovimientoCajaDTO();
		objetoDTO.setId(objeto.getId());
		objetoDTO.setFecha(objeto.getFecha());
		objetoDTO.setCodigo(objeto.getCodigo());
		objetoDTO.setDescripcion(objeto.getDescripcion());
		objetoDTO.setTipoMovimiento(objeto.getTipoMovimiento());
		objetoDTO.setFormaPago(objeto.getFormaPago());
		objetoDTO.setNroCheque(objeto.getNroCheque());
		objetoDTO.setConFactura(objeto.isConFactura());
		objetoDTO.setTipoFactura(objeto.getTipoFactura());
		objetoDTO.setImporte(objeto.getImporte());
		objetoDTO.setNroRecibo(objeto.getNroRecibo());
		return objetoDTO;
	}
	
	public static MovimientoCaja getMovimientoCaja(MovimientoCajaDTO objetoDTO) {
		MovimientoCaja objeto = new MovimientoCaja();
		objeto.setFecha(objetoDTO.getFecha());
		objeto.setCodigo(objetoDTO.getCodigo());
		objeto.setDescripcion(objetoDTO.getDescripcion());
		objeto.setTipoMovimiento(objetoDTO.getTipoMovimiento());
		objeto.setFormaPago(objetoDTO.getFormaPago());
		objeto.setNroCheque(objetoDTO.getNroCheque());
		objeto.setConFactura(objetoDTO.isConFactura());
		objeto.setTipoFactura(objetoDTO.getTipoFactura());
		objeto.setImporte(objetoDTO.getImporte());
		objeto.setNroRecibo(objetoDTO.getNroRecibo());
		return objeto;
	}
	
	public static Set getConjuntoMovimientoCaja(Vector v){
		Set aux = new HashSet();
		Iterator it = v.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==MovimientoCajaDTO.class) {
				aux.add(getMovimientoCaja((MovimientoCajaDTO) obj));
			}
		}
		return aux;
	}
	
	public static Vector getVectorMovimientoCaja(Set s) {
		Vector auxDTO = new Vector();
		auxDTO.clear();
		Iterator it = s.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==MovimientoCaja.class) {
				auxDTO.add(getMovimientoCajaDTO((MovimientoCaja) obj));
			}
		}
		return auxDTO;
	}
	
	/* NotaDebito */
	public static NotaDebitoDTO getNotaDebitoDTO(NotaDebito objeto) {
		NotaDebitoDTO objetoDTO = new NotaDebitoDTO();
		objetoDTO.setId(objeto.getId());
		objetoDTO.setNroFactura(objeto.getNroFactura());
		objetoDTO.setImporteTotal(objeto.getImporteTotal());
		objetoDTO.setFecha(objeto.getFecha());
		objetoDTO.setAnulada(objeto.isAnulada());
		objetoDTO.setTipoFactura(objeto.getTipoFactura());
		objetoDTO.setTipoFacturaNC(objeto.getTipoFacturaNC());
		objetoDTO.setNroFacturaNC(objeto.getNroFacturaNC());
		objetoDTO.setIdFacturaNC(objeto.getIdFacturaNC());
		objetoDTO.setObservaciones(objeto.getObservaciones());
		return objetoDTO;
	}
	
	public static NotaDebito getNotaDebito(NotaDebitoDTO objetoDTO) {
		NotaDebito objeto = new NotaDebito();
		objeto.setNroFactura(objetoDTO.getNroFactura());
		objeto.setImporteTotal(objetoDTO.getImporteTotal());
		objeto.setFecha(objetoDTO.getFecha());
		objeto.setAnulada(objetoDTO.isAnulada());
		objeto.setTipoFactura(objetoDTO.getTipoFactura());
		objeto.setTipoFacturaNC(objetoDTO.getTipoFacturaNC());
		objeto.setNroFacturaNC(objetoDTO.getNroFacturaNC());
		objeto.setIdFacturaNC(objetoDTO.getIdFacturaNC());
		objeto.setObservaciones(objetoDTO.getObservaciones());
		return objeto;
	}
	
	public static Set getConjuntoNotaDebito(Vector v){
		Set aux = new HashSet();
		Iterator it = v.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==NotaDebitoDTO.class) {
				aux.add(getNotaDebito((NotaDebitoDTO) obj));
			}
		}
		return aux;
	}
	
	public static Vector getVectorNotaDebito(Set s) {
		Vector auxDTO = new Vector();
		auxDTO.clear();
		Iterator it = s.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==NotaDebito.class) {
				auxDTO.add(getNotaDebitoDTO((NotaDebito) obj));
			}
		}
		return auxDTO;
	}
	
	/* PlanillaES */
	public static PlanillaESDTO getPlanillaESDTO(PlanillaES objeto) {
		PlanillaESDTO objetoDTO = new PlanillaESDTO();
		objetoDTO.setId(objeto.getId());
		objetoDTO.setFecha(objeto.getFecha());
		objetoDTO.setNroPlanilla(objeto.getNroPlanilla());
		objetoDTO.setSaldo(objeto.getSaldo());
		return objetoDTO;
	}
	
	public static PlanillaES getPlanillaES(PlanillaESDTO objetoDTO) {
		PlanillaES objeto = new PlanillaES();
		objeto.setFecha(objetoDTO.getFecha());
		objeto.setNroPlanilla(objetoDTO.getNroPlanilla());
		objeto.setSaldo(objetoDTO.getSaldo());
		return objeto;
	}
	
	public static Set getConjuntoPlanillaES(Vector v){
		Set aux = new HashSet();
		Iterator it = v.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==PlanillaESDTO.class) {
				aux.add(getPlanillaES((PlanillaESDTO) obj));
			}
		}
		return aux;
	}
	
	public static Vector getVectorPlanillaES(Set s) {
		Vector auxDTO = new Vector();
		auxDTO.clear();
		Iterator it = s.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==PlanillaES.class) {
				auxDTO.add(getPlanillaESDTO((PlanillaES) obj));
			}
		}
		return auxDTO;
	}
	
	/* Provincia */
	public static ProvinciaDTO getProvinciaDTO(Provincia objeto) {
		ProvinciaDTO objetoDTO = new ProvinciaDTO();
		objetoDTO.setId(objeto.getId());
		objetoDTO.setNombre(objeto.getNombre());
		return objetoDTO;
	}
	
	public static Provincia getProvincia(ProvinciaDTO objetoDTO) {
		Provincia objeto = new Provincia();
		objeto.setNombre(objetoDTO.getNombre());
		return objeto;
	}
	
	public static Set getConjuntoProvincia(Vector v){
		Set aux = new HashSet();
		Iterator it = v.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==ProvinciaDTO.class) {
				aux.add(getProvincia((ProvinciaDTO) obj));
			}
		}
		return aux;
	}
	
	public static Vector getVectorProvincia(Set s) {
		Vector auxDTO = new Vector();
		auxDTO.clear();
		Iterator it = s.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==Provincia.class) {
				auxDTO.add(getProvinciaDTO((Provincia) obj));
			}
		}
		return auxDTO;
	}
	
	/* Localidad */
	public static LocalidadDTO getLocalidadDTO(Localidad objeto) {
		LocalidadDTO objetoDTO = new LocalidadDTO();
		objetoDTO.setId(objeto.getId());
		objetoDTO.setNombre(objeto.getNombre());
		objetoDTO.setCodPostal(objeto.getCodPostal());
		return objetoDTO;
	}
	
	public static Localidad getLocalidad(LocalidadDTO objetoDTO) {
		Localidad objeto = new Localidad();
		objeto.setCodPostal(objetoDTO.getCodPostal());
		objeto.setNombre(objetoDTO.getNombre());
		return objeto;
	}
	
	public static Set getConjuntoLocalidad(Vector v){
		Set aux = new HashSet();
		Iterator it = v.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==LocalidadDTO.class) {
				aux.add(getLocalidad((LocalidadDTO) obj));
			}
		}
		return aux;
	}
	
	public static Vector getVectorLocalidad (Set s) {
		Vector auxDTO = new Vector();
		auxDTO.clear();
		Iterator it = s.iterator();
		Object obj;
		while(it.hasNext()){
			obj = it.next();
			if (obj.getClass()==Localidad.class) {
				auxDTO.add(getLocalidadDTO((Localidad) obj));
			}
		}
		return auxDTO;
	}
	
}