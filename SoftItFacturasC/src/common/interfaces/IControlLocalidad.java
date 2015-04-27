package common.interfaces;

import java.rmi.Remote;
import java.util.Vector;

import persistencia.domain.Localidad;
import server.ManipuladorPersistencia;

import common.dto.LocalidadDTO;

public interface IControlLocalidad  extends Remote {
	
	public boolean agregarLocalidad(LocalidadDTO loc)throws Exception;
	public void eliminarLocalidad(Long id)throws Exception;
	public void modificarLocalidad(Long id,LocalidadDTO modificado)throws Exception;
	public Vector obtenerLocalidades()throws Exception;
	public Vector obtenerLocalidadesFiltro(String nombre)throws Exception;
	public boolean existeLocalidadNombre(String loc)throws Exception;
	public LocalidadDTO buscarLocalidad(Long id) throws Exception;
	public boolean puedoEditar(LocalidadDTO dto,LocalidadDTO modificado)throws Exception;
	public boolean localidadAsociada(Long id) throws Exception;
	public Localidad buscarLocalidadPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception;
	
} 
