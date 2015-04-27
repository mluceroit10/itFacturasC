package common.dto;

import java.io.Serializable;
import java.sql.Date;

public class ChequeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date fechaEmision;
	private Date fechaVto;
	private String banco;
	private Long numero;
	private String locPlaza;
	private double importe;
	private String quienEntrega;
	private String estado;
	private String aQuienRemite;
	private String periodo;
	
	public ChequeDTO(){
	}
	
	public String getAQuienRemite() {
		return aQuienRemite;
	}

	public void setAQuienRemite(String quienRemite) {
		aQuienRemite = quienRemite;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaVto() {
		return fechaVto;
	}

	public void setFechaVto(Date fechaVto) {
		this.fechaVto = fechaVto;
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

	public String getLocPlaza() {
		return locPlaza;
	}

	public void setLocPlaza(String locPlaza) {
		this.locPlaza = locPlaza;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getQuienEntrega() {
		return quienEntrega;
	}

	public void setQuienEntrega(String quienEntrega) {
		this.quienEntrega = quienEntrega;
	}
	
	
	
}
