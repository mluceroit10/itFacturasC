package common.interfaces;

import java.rmi.Remote;
import java.sql.Date;
import java.util.Vector;

import persistencia.domain.PlanillaES;
import server.ManipuladorPersistencia;

import common.dto.PlanillaESDTO;

public interface IControlPlanillaES  extends Remote {
	
	public void agregarPlanillaESTotal(PlanillaESDTO p,Vector movimientos) throws Exception;
	public void eliminarPlanillaES(Long id)throws Exception;
	public Vector obtenerPlanillasES(int anioLI)throws Exception;
	public Vector obtenerPlanillasESFiltros(int anioLI,String fecha,String nroPl)throws Exception;
	public boolean existePlanillaESNroPlanilla(int numero)throws Exception;
	public PlanillaESDTO buscarPlanillaES(Long id) throws Exception;
	public PlanillaESDTO buscarPlanillaESNroPlanilla(int numero) throws Exception;
	public PlanillaES buscarPlanillaESPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception;
	public PlanillaES buscarPlanillaESNroPlanillaPersistencia(int numero) throws Exception;
	public PlanillaESDTO obtenerUltimaPlanilla()throws Exception;
	public Vector obtenerMovimientosCajaParaPlanilla(Date fechaD, Date fechaH)throws Exception;
	
}
