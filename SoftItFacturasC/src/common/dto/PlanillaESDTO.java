package common.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.Vector;

public class PlanillaESDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private int nroPlanilla;
	private Date fechaP;
	private double saldo;
	private Vector movimientosCaja = new Vector();
	private String periodo;
	
	public PlanillaESDTO(){
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getFecha() {
		return fechaP;
	}
	
	public void setFecha(Date fecha) {
		this.fechaP = fecha;
	}
	
	public int getNroPlanilla() {
		return nroPlanilla;
	}
	
	public void setNroPlanilla(int nroPlanilla) {
		this.nroPlanilla = nroPlanilla;
	}
	
	public double getSaldo() {
		return saldo;
	}
	
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public Vector getMovimientosCaja() {
		return movimientosCaja;
	}
	
	public void setMovimientosCaja(Vector movimientosCaja) {
		this.movimientosCaja = movimientosCaja;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
}
