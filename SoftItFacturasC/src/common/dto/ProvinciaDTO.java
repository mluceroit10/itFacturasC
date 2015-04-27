package common.dto;

import java.io.Serializable;
import java.util.Vector;

public class ProvinciaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nombre;
	private Vector localidades = new Vector();
	
	public ProvinciaDTO() {
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Vector getLocalidades() {
		return localidades;
	}
	
	public void setLocalidades(Vector localidades) {
		this.localidades = localidades;
	}
	
	
	
}
