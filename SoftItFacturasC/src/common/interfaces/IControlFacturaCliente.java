package common.interfaces;

import java.rmi.Remote;
import java.sql.Date;
import java.util.Vector;

import persistencia.domain.FacturaCliente;
import server.ManipuladorPersistencia;

import common.dto.FacturaClienteDTO;

public interface IControlFacturaCliente  extends Remote {
	
	public double agregarFacturaClienteTotal(FacturaClienteDTO fc, String tipo, FacturaClienteDTO remito,int nroMC)throws Exception;
	public void anularFacturaCliente(Long idF)throws Exception;
	public boolean existenFacturasDeCliente(Long id)throws Exception;
	public Vector obtenerFacturaClientesPeriodo(boolean listarRemitosSinFact, String tipoF,int mesLI,int anioLI)throws Exception;
	public Vector obtenerFacturaClientesPeriodoFiltros(boolean listarRemitosSinFact,String tipoF,int mesLI,int anioLI,String fecha,String numero,String cliente)throws Exception;
	public Vector obtenerFacturasClienteFechaLoc(Date fecha,Long idLoc)throws Exception;
	public boolean existeFacturaClienteNroTipo(Long nroF, String tipoF)throws Exception;
	public boolean existeFacturaDeRemito(String nroRemito)throws Exception;
	public FacturaClienteDTO buscarFacturaDeRemito(String nroRemito) throws Exception;
	public FacturaClienteDTO buscarFacturaCliente(Long id) throws Exception;
	public FacturaCliente buscarFacturaClientePersistencia(Long nroF, String tipoF) throws Exception;
	public Vector obtenerDatosEstadisticos(int mesL, int anioL, Long idLoc, Long idVend) throws Exception;
	public boolean facturaAsociada(Long id) throws Exception ;
	public FacturaCliente buscarFacturaClientePersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception;
	public Vector obtenerFacturasyNotasDebito(int mesLI, int anioLI)throws Exception;
	
}
