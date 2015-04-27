package persistencia.domain;
import java.sql.Date;

import persistencia.OidGenerator;

public class MovimientoCaja {
	
	private Long id;
	private Date fechaMC;
	private int codigo; //unico  
	private String descripcion;
	private int tipoMovimiento;
	private String formaPago;
	private Long nroCheque;
	private double importe;
	private boolean conFactura;
	private String tipoFactura;
	private Factura factura;
	private PlanillaES planilla;
	private String periodo;
	private Long nroRecibo;
	
	public MovimientoCaja(){
		id=OidGenerator.getNewId();	
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Date getFecha() {
		return fechaMC;
	}
	
	public void setFecha(Date fecha) {
		this.fechaMC = fecha;
	}
	
	public String getFormaPago() {
		return formaPago;
	}
	
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public double getImporte() {
		return importe;
	}
	
	public void setImporte(double importe) {
		this.importe = importe;
	}
	
	public Long getNroCheque() {
		return nroCheque;
	}
	
	public void setNroCheque(Long nroCheque) {
		this.nroCheque = nroCheque;
	}
	
	public int getTipoMovimiento() {
		return tipoMovimiento;
	}
	
	public void setTipoMovimiento(int tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
	
	public Factura getFactura() {
		return factura;
	}
	
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	
	public boolean isConFactura() {
		return conFactura;
	}
	
	public void setConFactura(boolean conFactura) {
		this.conFactura = conFactura;
	}
	
	public String getTipoFactura() {
		return tipoFactura;
	}
	
	public void setTipoFactura(String tipoFactura) {
		this.tipoFactura = tipoFactura;
	}
	
	public PlanillaES getPlanilla() {
		return planilla;
	}
	
	public void setPlanilla(PlanillaES planilla) {
		this.planilla = planilla;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public Long getNroRecibo() {
		return nroRecibo;
	}

	public void setNroRecibo(Long nroRecibo) {
		this.nroRecibo = nroRecibo;
	}
	
}
