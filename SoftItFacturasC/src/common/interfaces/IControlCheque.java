package common.interfaces;

import java.rmi.Remote;
import java.util.Vector;

import persistencia.domain.Cheque;
import server.ManipuladorPersistencia;

import common.dto.ChequeDTO;

public interface IControlCheque extends Remote {
	
	public boolean agregarCheque(ChequeDTO ch)throws Exception;
	public void eliminarCheque(Long id)throws Exception;
	public void modificarCheque(Long id,ChequeDTO modificado)throws Exception;
	public Vector obtenerChequesPeriodo(int mesLI,int anioLI, String estado)throws Exception;
	public Vector obtenerChequesFiltro(int mesLI,int anioLI,String numero, String para, String banco, String estado)throws Exception;
	public Vector obtenerChequesVencidos(int dia,int mes,int anio)throws Exception;
	public boolean existeChequeNumero(Long num)throws Exception;
	public ChequeDTO buscarCheque(Long id) throws Exception;
	public boolean puedoEditar(ChequeDTO dto,ChequeDTO modificado)throws Exception;
	public Cheque buscarChequePersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception;
	public void cambiarEstado(Long id,String estado,String remitidoA) throws Exception;
	
} 
