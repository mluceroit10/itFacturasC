package common.dto;

import java.io.Serializable;
import java.sql.Date;

public class FacturaClienteDTO extends FacturaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date fechaImpresion;
	private Date fechaPago;
	private double importeAbonado; 
	private String condVenta;
	private String ivaF;
	private String remitoNro;
	private String ingrBrutos;
	
	public Date getFechaImpresion() {
		return fechaImpresion;
	}
	public void setFechaImpresion(Date fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	public double getImporteAbonado() {
		return importeAbonado;
	}
	public void setImporteAbonado(double importeAbonado) {
		this.importeAbonado = importeAbonado;
	}
	public String getCondVenta() {
		return condVenta;
	}
	public void setCondVenta(String condVenta) {
		this.condVenta = condVenta;
	}
	public String getIva() {
		return ivaF;
	}
	public void setIva(String iva) {
		this.ivaF = iva;
	}
	public String getRemitoNro() {
		return remitoNro;
	}
	public void setRemitoNro(String remitoNro) {
		this.remitoNro = remitoNro;
	}
	public String getIngrBrutos() {
		return ingrBrutos;
	}
	public void setIngrBrutos(String ingrBrutos) {
		this.ingrBrutos = ingrBrutos;
	}
	
}
