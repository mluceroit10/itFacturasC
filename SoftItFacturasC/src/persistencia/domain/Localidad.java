package persistencia.domain; 

import persistencia.OidGenerator;

public class Localidad {  
	
	private Long id;         
	private String nombre;  
	private int codPostal; 
	private Provincia provincia = null;
	
	public Localidad(){
		id=OidGenerator.getNewId();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getCodPostal() {
		return codPostal;
	}
	
	public void setCodPostal(int codPostal) {
		this.codPostal = codPostal;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Provincia getProvincia() {
		return provincia;
	}
	
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	
}
