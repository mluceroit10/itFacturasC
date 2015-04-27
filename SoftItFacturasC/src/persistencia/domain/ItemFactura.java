package persistencia.domain;

import persistencia.OidGenerator;

public class ItemFactura {
	
	private Long id;
	private double cantidad;
	private String producto = null;
	private Factura factura = null;
	private double prUnit;
	private double prTotal;
	
	public ItemFactura(){
		id=OidGenerator.getNewId();	
	}
	
	public Factura getFactura() {
		return factura;
	}
	
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public double getPrUnit() {
		return prUnit;
	}
	
	public void setPrUnit(double prUnit) {
		this.prUnit = prUnit;
	}
	
	public double getPrTotal() {
		return prTotal;
	}
	
	public void setPrTotal(double prTotal) {
		this.prTotal = prTotal;
	}
	
	public double getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getProducto() {
		return producto;
	}
	
	public void setProducto(String producto) {
		this.producto = producto;
	}
	
	
	
	
}
