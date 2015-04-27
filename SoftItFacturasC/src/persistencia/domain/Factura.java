package persistencia.domain;

import java.util.HashSet;
import java.util.Set;

import persistencia.OidGenerator;

public class Factura {
	
	private Long id;
	private Long nroFactura;
	private Set items=new HashSet();
	private String tipoFactura; 
	private double importeTotal; 
	private boolean anulada;
	private Set comprobantesPago=new HashSet();
	private String periodo;
	private Cliente cliente=null;
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Factura(){
		id=OidGenerator.getNewId();	
	}
	
	public Set getComprobantesPago() {
		return comprobantesPago;
	}
	
	public void setComprobantesPago(Set comprobantesPago) {
		this.comprobantesPago = comprobantesPago;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public double getImporteTotal() {
		return importeTotal;
	}
	
	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}
	
	public Set getItems() {
		return items;
	}
	
	public void setItems(Set items) {
		this.items = items;
	}
	
	public Long getNroFactura() {
		return nroFactura;
	}
	
	public void setNroFactura(Long nroFactura) {
		this.nroFactura = nroFactura;
	}
	
	public String getTipoFactura() {
		return tipoFactura;
	}
	
	public void setTipoFactura(String tipoFactura) {
		this.tipoFactura = tipoFactura;
	}
	
	public boolean isAnulada() {
		return anulada;
	}
	
	public void setAnulada(boolean anulada) {
		this.anulada = anulada;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
}
