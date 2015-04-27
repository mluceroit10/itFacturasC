package persistencia.domain;

import persistencia.OidGenerator;

public class Cliente {
	
	private Long id;
	private Long codigo;
	private String cuit;
	private String nombre;
	private String telefono;
	private String direccion; 
	private Localidad localidad;
	private String ivaCl;
	private String ingBrutosCl;
	private boolean eliminado;
	private double deuda;
	
	public Cliente(){
		id=OidGenerator.getNewId();	
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Localidad getLocalidad() {
		return localidad;
	}
	
	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getCuit() {
		return cuit;
	}
	
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getIngBrutosCl() {
		return ingBrutosCl;
	}
	
	public void setIngBrutosCl(String ingBrutosCl) {
		this.ingBrutosCl = ingBrutosCl;
	}
	
	public String getIvaCl() {
		return ivaCl;
	}
	
	public void setIvaCl(String ivaCl) {
		this.ivaCl = ivaCl;
	}
	
	public boolean isEliminado() {
		return eliminado;
	}
	
	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}
	
	public double getDeuda() {
		return deuda;
	}
	
	public void setDeuda(double deuda) {
		this.deuda = deuda;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
}
