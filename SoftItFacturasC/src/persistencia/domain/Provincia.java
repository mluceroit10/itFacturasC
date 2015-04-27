package persistencia.domain;

import java.util.HashSet;
import java.util.Set;

import persistencia.OidGenerator;

public class Provincia {
	
	private Long id;
	private String nombre;
	private Set localidades = new HashSet();
	
	public Provincia() {
		id=OidGenerator.getNewId();
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
	
	public Set getLocalidades() {
		return localidades;
	}
	
	public void setLocalidades(Set localidades) {
		this.localidades = localidades;
	}
	
	
	
}
