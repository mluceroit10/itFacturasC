package common.interfaces;

import java.rmi.Remote;
import java.util.Vector;

import persistencia.domain.Cliente;
import server.ManipuladorPersistencia;

import common.dto.ClienteDTO;
import common.dto.FacturaClienteDTO;

public interface IControlCliente  extends Remote {
	
	public boolean agregarCliente(ClienteDTO cte)throws Exception;
	public void eliminarCliente(Long id)throws Exception;
	public void modificarCliente(Long id,ClienteDTO modificado)throws Exception;
	public Vector obtenerClientes()throws Exception;
	public Vector obtenerClientesFiltro(String nombre,String codigo)throws Exception;
	public boolean existeClienteNombre(String loc)throws Exception;
	public boolean existeClienteCodigo(Long codigo)throws Exception;
	public ClienteDTO buscarCliente(Long id) throws Exception;
	public boolean puedoEditarNombre(ClienteDTO dto,ClienteDTO modificado)throws Exception;
	public boolean puedoEditarCodigo(ClienteDTO dto,ClienteDTO modificado)throws Exception;
	public Cliente buscarClientePersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception;
	public Vector obtenerMovimientosCajaDeFactura(Long id)throws Exception;
	public Vector obtenerNotasDeDebitoDeFactura(Long id)throws Exception;
	public boolean clienteAsociado(Long id) throws Exception;
	public Vector obtenerFacturasDeCliente(int mesLI,int anioLI,String nombre)throws Exception;
	public Vector obtenerClientesCriterios(Long idLoc,Long idZona, Long idVend)throws Exception;
	public FacturaClienteDTO obtenerUltimaFacturasCliente(Long idCte)throws Exception;
	public Vector obtenerClientesAIDeLocalidadYDeuda(Long idLoc,String todos)throws Exception;
	public Long obtenerNroCliente()throws Exception;
}
