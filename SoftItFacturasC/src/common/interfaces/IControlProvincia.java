package common.interfaces;

import java.rmi.Remote;
import java.util.Vector;

import persistencia.domain.Provincia;
import server.ManipuladorPersistencia;

import common.dto.ProvinciaDTO;

public interface IControlProvincia  extends Remote {
	
	public boolean agregarProvincia(ProvinciaDTO pDTO)throws Exception;
	public void eliminarProvincia(Long id)throws Exception;
	public void modificarProvincia(Long id,ProvinciaDTO modificado)throws Exception;
	public Vector obtenerProvincias()throws Exception;
	public Vector obtenerProvinciasFiltro(String nombre)throws Exception;
	public boolean existeProvinciaNombre(String nombre)throws Exception;
	public ProvinciaDTO buscarProvincia(Long id) throws Exception;
	public boolean puedoEditar(ProvinciaDTO dto,ProvinciaDTO modificado)throws Exception;
	public boolean provinciaAsociada(Long id) throws Exception;
	public Provincia buscarProvinciaPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception; 
}
