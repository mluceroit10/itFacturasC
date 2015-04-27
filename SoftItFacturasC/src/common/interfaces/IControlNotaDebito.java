package common.interfaces;

import java.rmi.Remote;
import java.util.Vector;

import persistencia.domain.NotaDebito;
import server.ManipuladorPersistencia;

import common.dto.NotaDebitoDTO;

public interface IControlNotaDebito  extends Remote {
	
	public double agregarNotaDebitoCliente(NotaDebitoDTO fc)throws Exception;
	public void anularNotaDebito(Long idNC)throws Exception;
	public boolean existenNotaDebitoDeCliente(Long id)throws Exception;
	public Vector obtenerNotasDeDebito(int mesLI,int anioLI)throws Exception;
	public Vector obtenerNotasDeDebitoFiltros(int mesLI,int anioLI,String fecha,String cliente)throws Exception;
	public Vector obtenerNotaDebitoPeriodo(String tipoNC,int mesLI,int anioLI)throws Exception;
	public Vector obtenerNotaDebitoPeriodoFiltros(String tipoNC,int mesLI,int anioLI,String fecha,String numero,String cliente)throws Exception;
	public boolean existeNotaDebitoNroTipo(Long nroF, String tipoF)throws Exception;
	public NotaDebitoDTO buscarNotaDebito(Long id) throws Exception;
	public NotaDebito buscarNotaDebitoPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception;
	
}
