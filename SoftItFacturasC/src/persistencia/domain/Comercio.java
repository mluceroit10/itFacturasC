package persistencia.domain;

import java.sql.Date;

import persistencia.OidGenerator;

public class Comercio {
	
	private Long id;
	private String cuit;
	private String nombre;
	private String telefono;
	private String direccion; 
	private Localidad localidad;
	private String categoria;  //cont ingr Burtos
	private Date inicioAct;
	private Long nroFactC;
	private Long nroRemito;
	private Long nroNotaDebito;
	private Long nroRecibo;
	
	public Comercio(){
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
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public Date getInicioAct() {
		return inicioAct;
	}
	
	public void setInicioAct(Date inicioAct) {
		this.inicioAct = inicioAct;
	}
	
	public Long getNroFactC() {
		return nroFactC;
	}
	
	public void setNroFactC(Long nroFactC) {
		this.nroFactC = nroFactC;
	}
	
	public Long getNroRemito() {
		return nroRemito;
	}
	
	public void setNroRemito(Long nroRemito) {
		this.nroRemito = nroRemito;
	}
	
	public Long getNroNotaDebito() {
		return nroNotaDebito;
	}
	
	public void setNroNotaDebito(Long nroNotaDebito) {
		this.nroNotaDebito = nroNotaDebito;
	}

	public Long getNroRecibo() {
		return nroRecibo;
	}

	public void setNroRecibo(Long nroRecibo) {
		this.nroRecibo = nroRecibo;
	}
	
}
