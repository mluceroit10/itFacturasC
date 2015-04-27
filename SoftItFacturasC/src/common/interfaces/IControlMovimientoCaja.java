package common.interfaces;

import java.rmi.Remote;
import java.util.Vector;

import common.dto.MovimientoCajaDTO;

public interface IControlMovimientoCaja  extends Remote {
	
	public Long agregarMovimientoCaja_Fact(MovimientoCajaDTO mc)throws Exception;
	public void eliminarMovimientoCaja(Long id)throws Exception;
	public Vector obtenerMovimientosCaja()throws Exception;
	public Vector obtenerMovimientosCajaPeriodo(int mesLI,int anioLI)throws Exception;
	public Vector obtenerMovimientosCajaFiltros(int mesLI,int anioLI,String cod,String fecha)throws Exception;
	public boolean existeMovimientoCaja(int codigo)throws Exception;
	public MovimientoCajaDTO buscarMovimientoCaja(int codigo) throws Exception;
	public boolean puedoEditar(MovimientoCajaDTO dto,MovimientoCajaDTO modificado)throws Exception;
	public boolean movimientoCajaAsociado(Long id) throws Exception; 
	public int obtenerNroMovCaja()throws Exception;
	
}
