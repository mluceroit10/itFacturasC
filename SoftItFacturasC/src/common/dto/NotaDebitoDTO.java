package common.dto;

import java.io.Serializable;
import java.sql.Date;

public class NotaDebitoDTO extends FacturaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date fecha;
	private String tipoFacturaNC;
	private Long nroFacturaNC;
	private Long idFacturaNC;
	private String observaciones;
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Long getIdFacturaNC() {
		return idFacturaNC;
	}
	
	public void setIdFacturaNC(Long idFacturaNC) {
		this.idFacturaNC = idFacturaNC;
	}
	
	public Long getNroFacturaNC() {
		return nroFacturaNC;
	}
	
	public void setNroFacturaNC(Long nroFacturaNC) {
		this.nroFacturaNC = nroFacturaNC;
	}
	
	public String getTipoFacturaNC() {
		return tipoFacturaNC;
	}
	
	public void setTipoFacturaNC(String tipoFacturaNC) {
		this.tipoFacturaNC = tipoFacturaNC;
	}
	
	public String getObservaciones() {
		return observaciones;
	}
	
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}
