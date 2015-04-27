package common.dto;

import java.io.Serializable;
import java.util.Vector;

public class FacturaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long nroFactura;
	private Vector items=new Vector();
	private String tipoFactura; 
	private double importeTotal; 
	private boolean anulada;
	private Vector comprobantesPago=new Vector();
	private String periodo;
	private ClienteDTO cliente=null;
	
	public FacturaDTO(){
	}
	
	public ClienteDTO getCliente() {
		return cliente;
	}
	
	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}
	
	public Vector getComprobantesPago() {
		return comprobantesPago;
	}
	
	public void setComprobantesPago(Vector comprobantesPago) {
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
	
	public Vector getItems() {
		return items;
	}
	
	public void setItems(Vector items) {
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
