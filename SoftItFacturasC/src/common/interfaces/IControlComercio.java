package common.interfaces;

import java.rmi.Remote;

import persistencia.domain.Comercio;
import server.ManipuladorPersistencia;

import common.dto.ComercioDTO;

public interface IControlComercio  extends Remote {
	
	public boolean agregarComercio(ComercioDTO loc)throws Exception;
	public void modificarComercio(Long id,ComercioDTO modificado)throws Exception;
	public ComercioDTO obtenerComercio()throws Exception;
	public boolean existeComercioNombre(String loc)throws Exception;
	public Comercio buscarUnicaComercioPersistencia(ManipuladorPersistencia mp) throws Exception;
	public Comercio buscarComercioPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception;
	
}
