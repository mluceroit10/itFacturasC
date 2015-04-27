package persistencia.domain;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import persistencia.OidGenerator;

public class PlanillaES { 
	
	private Long id;
	private int nroPlanilla;
	private Date fechaP;
	private double saldo;
	private Set movimientosCaja = new HashSet();
	private String periodo;
	
	public PlanillaES(){
		id=OidGenerator.getNewId();
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
	
	public Set getMovimientosCaja() {
		return movimientosCaja;
	}
	
	public void setMovimientosCaja(Set movimientosCaja) {
		this.movimientosCaja = movimientosCaja;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
}
