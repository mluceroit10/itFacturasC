package cliente.GestionarPlanillaES;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cliente.ClienteConection;
import cliente.Principal.GUIReport;

import common.Utils;
import common.dto.ComercioDTO;
import common.dto.MovimientoCajaDTO;
import common.dto.PlanillaESDTO;
import common.interfaces.IControlComercio;
import common.interfaces.IControlMovimientoCaja;
import common.interfaces.IControlPlanillaES;

import constantes.NRO_REPORTE;

public class MediadorGestionarPlanillaES implements ActionListener, KeyListener, ListSelectionListener {
	
	private GUIGestionarPlanillaES guiImprimirPlanillaES = null;
	protected IControlPlanillaES controlPlanillaES;
	protected IControlMovimientoCaja controlMovimientoCaja;
	protected IControlComercio controlComercio;
	private PlanillaESDTO miPESDto;
	private int anioLI;
	
	public MediadorGestionarPlanillaES(int anio,JFrame guiPadre) {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		anioLI=anio;
		this.controlPlanillaES = clienteConexion.getIControlPlanillaES();
		this.controlComercio = clienteConexion.getIControlComercio();
		this.controlMovimientoCaja = clienteConexion.getIControlMovimientoCaja();
		this.guiImprimirPlanillaES = new GUIGestionarPlanillaES(anioLI,guiPadre);
		this.guiImprimirPlanillaES.setActionListeners(this);
		actualizarDatosTabla();
		this.guiImprimirPlanillaES.setListSelectionListener(this);
		this.guiImprimirPlanillaES.setKeyListener(this);
		Utils.show(guiImprimirPlanillaES);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == guiImprimirPlanillaES.getJBImprimir()) {
			try {
				if (guiImprimirPlanillaES.jtDatos.getSelectedRow() == -1){
					Utils.advertenciaUsr(guiImprimirPlanillaES,"Para poder Imprimir una Planilla E/S debe ser previamente seleccionada.");
				}else{
					Long id = (Long)guiImprimirPlanillaES.datos[guiImprimirPlanillaES.jtDatos.getSelectedRow()][0];
					miPESDto = this.controlPlanillaES.buscarPlanillaES(id);
					int nro=miPESDto.getNroPlanilla();
					PlanillaESDTO anterior = new PlanillaESDTO();
					if(nro>(Utils.NROPLANILLAANTERIOR+1)) anterior=this.controlPlanillaES.buscarPlanillaESNroPlanilla(nro-1);
					else anterior.setSaldo(0);
					Vector datosMC_F=catalogarMovimientos(miPESDto.getMovimientosCaja());
					Vector mov=(Vector) datosMC_F.elementAt(0);
					Vector detalleFact=(Vector) datosMC_F.elementAt(1);
					Vector entr = this.entradas(mov);
					Vector sal = this.salidas(miPESDto.getMovimientosCaja());
					ComercioDTO comercio = controlComercio.obtenerComercio();
					new GUIReport(guiImprimirPlanillaES,NRO_REPORTE.generarBalance,comercio,entr,sal,miPESDto.getNroPlanilla(),miPESDto.getFecha(),anterior.getSaldo(),miPESDto.getSaldo());
					int prueba = Utils.aceptarCancelarAccion(guiImprimirPlanillaES,"Desea obtener el detalle de las facturas?");
					if (prueba == 0){
						new GUIReport(guiImprimirPlanillaES,NRO_REPORTE.balanceDetallePagos,comercio,miPESDto.getNroPlanilla(),detalleFact,miPESDto.getFecha());
					}
				}
			} catch(Exception ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
			}
		}else if (source == guiImprimirPlanillaES.getJBCargar()) {
			java.util.Date fe = guiImprimirPlanillaES.getJDateChooserFecha().getDate();
			Date fecha = Utils.crearFecha(fe);
			try {
				PlanillaESDTO ultima=controlPlanillaES.obtenerUltimaPlanilla();
				PlanillaESDTO miplDTO = new PlanillaESDTO();
				miplDTO.setNroPlanilla(ultima.getNroPlanilla()+1);
				miplDTO.setFecha(fecha);
				Vector movTotal=controlPlanillaES.obtenerMovimientosCajaParaPlanilla(ultima.getFecha(),fecha);
				Vector datosMC_F=catalogarMovimientos(movTotal);
				Vector mov=(Vector) datosMC_F.elementAt(0);
				Vector detalleFact=(Vector) datosMC_F.elementAt(1);
				miplDTO.setMovimientosCaja(movTotal);
				miplDTO.setSaldo(generarSaldo(mov,ultima.getSaldo()));
				this.controlPlanillaES.agregarPlanillaESTotal(miplDTO,movTotal);
				Vector entr = this.entradas(mov);
				Vector sal = this.salidas(mov);
				ComercioDTO comercio=controlComercio.obtenerComercio();
				new GUIReport(guiImprimirPlanillaES,NRO_REPORTE.generarBalance,comercio,entr,sal,ultima.getNroPlanilla()+1,fecha,ultima.getSaldo(),miplDTO.getSaldo());
				int prueba = Utils.aceptarCancelarAccion(guiImprimirPlanillaES,"Desea obtener el detalle de las facturas?");
				if (prueba == 0){
					new GUIReport(guiImprimirPlanillaES,NRO_REPORTE.balanceDetallePagos,comercio,ultima.getNroPlanilla()+1,detalleFact,fecha);
				}
				actualizarDatosTabla();
			}
			catch(Exception ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
			}
		}else if (source == guiImprimirPlanillaES.getJBBorrar()) {
			try{
				if ( this.controlPlanillaES.obtenerPlanillasES(anioLI).isEmpty()){
					Utils.advertenciaUsr(guiImprimirPlanillaES,"No hay Planillas guardadas.");
				} else {
					if (guiImprimirPlanillaES.jtDatos.getSelectedRow() == -1){
						Utils.advertenciaUsr(guiImprimirPlanillaES,"Para poder Eliminar una Planilla debe seleccionarla previamente.");
					} else {
						int sel=guiImprimirPlanillaES.jtDatos.getSelectedRow();
						if(sel==(guiImprimirPlanillaES.datos.length -1)){
							Long id = (Long)guiImprimirPlanillaES.datos[guiImprimirPlanillaES.jtDatos.getSelectedRow()][0];
							String numero = (String)guiImprimirPlanillaES.datos[guiImprimirPlanillaES.jtDatos.getSelectedRow()][1];
							int prueba = Utils.aceptarCancelarAccion(guiImprimirPlanillaES,"Esta seguro que desea Eliminar la Planilla Nro: \n" + numero);
							if (prueba == 0){
								this.controlPlanillaES.eliminarPlanillaES(id);
							}
							actualizarDatosTabla();
						}else{
							Utils.advertenciaUsr(guiImprimirPlanillaES,"Solo es posible eliminar el último cierre de caja");
						}
					}
				}
			}catch(Exception ex){
				Utils.manejoErrores(ex,this.getClass(),"Error Eliminar");
			}
		} else if (source == guiImprimirPlanillaES.getJBSalir()) {
			guiImprimirPlanillaES.dispose();	
		}else if (source == guiImprimirPlanillaES.getJBCambiarPeriodo()){
			String anioB = guiImprimirPlanillaES.getJTFAnio().getText();
			if(anioB.length()==0){
				Utils.advertenciaUsr(guiImprimirPlanillaES,"Por favor ingrese el Año.");
			}else if(anioB.length()!=4){
				Utils.advertenciaUsr(guiImprimirPlanillaES,"El año debe ser un número de 4 dígitos.");
			}else{
				anioLI= Integer.parseInt(anioB);
				actualizarDatosTabla();
			}	
		} else { 
			guiImprimirPlanillaES.dispose();
		}
	}
	
	private Vector catalogarMovimientos(Vector movs) throws Exception {
		Vector datos=new Vector();
		Vector fact=new Vector();
		Vector solomov= new Vector();
		double totalFact=0;
		for(int i=0;i<movs.size();i++){
			MovimientoCajaDTO m= (MovimientoCajaDTO)movs.elementAt(i);
			if(m.getTipoMovimiento()==1){ //entrada
				if(m.isConFactura() && 
						(m.getTipoFactura().compareTo("Factura Cliente-Tipo C")==0 || 
								m.getTipoFactura().compareTo("Remito Cliente")==0)){
					MovimientoCajaDTO mov=controlMovimientoCaja.buscarMovimientoCaja(m.getCodigo());
					fact.add(mov);
					totalFact+=m.getImporte();
				} else{
					MovimientoCajaDTO mov=controlMovimientoCaja.buscarMovimientoCaja(m.getCodigo());
					solomov.add(mov);
				}
			}else{
				solomov.add(m);
			}	
		}
		
		MovimientoCajaDTO movFact= new MovimientoCajaDTO();
		movFact.setDescripcion("Ingresos por facturación");
		movFact.setImporte(totalFact);
		movFact.setTipoMovimiento(1);
		movFact.setConFactura(false);
		movFact.setId(null);
		java.util.Date hoy= new java.util.Date();
		movFact.setFecha((new java.sql.Date(hoy.getYear(),hoy.getMonth(),hoy.getDate())));
		solomov.add(0, movFact);
		datos.add(solomov);
		datos.add(fact);
		return datos;
	}
	
	public void actualizarDatosTabla() {
		try {
			Vector planillas = this.controlPlanillaES.obtenerPlanillasESFiltros(anioLI,guiImprimirPlanillaES.getJTFFecha().getText(),guiImprimirPlanillaES.getJTFNro().getText());
			guiImprimirPlanillaES.getJTFPeriodo().setText(String.valueOf(anioLI));
			guiImprimirPlanillaES.datos = new Object[planillas.size()][guiImprimirPlanillaES.titulos.length];
			for (int j = 0; j < planillas.size(); j++) {
				PlanillaESDTO p=(PlanillaESDTO)planillas.elementAt(j);
				Object[] temp = {p.getId(),String.valueOf(p.getNroPlanilla()),common.Utils.getStrUtilDate(p.getFecha())};
				guiImprimirPlanillaES.datos[j] = temp;
			}
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error ActualizarFecha");
		}
		guiImprimirPlanillaES.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guiImprimirPlanillaES.actualizarTabla();
	}
	
	public GUIGestionarPlanillaES getGUI() {
		return guiImprimirPlanillaES;
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
			actualizarDatosTabla();
	}
	
	public void keyTyped(KeyEvent e) {
	}
	
	public void keyPressed(KeyEvent e) {
	}
	
	public void valueChanged(ListSelectionEvent arg0) { }
	
	private double generarSaldo(Vector movs,double saldo){
		double total=saldo;
		for(int i=0;i<movs.size();i++){
			MovimientoCajaDTO m= (MovimientoCajaDTO)movs.elementAt(i);
			if(m.getTipoMovimiento()==1) //entrada
				total += m.getImporte();
			else //salida	
				total -= m.getImporte();
		}
		return total;
	}
	
	private Vector entradas(Vector movs) throws Exception{
		Vector entr=new Vector();
		for(int i=0;i<movs.size();i++){
			MovimientoCajaDTO m= (MovimientoCajaDTO)movs.elementAt(i);
			if(m.getTipoMovimiento()==1){ //entrada
				if(m.getId()!=null){
					MovimientoCajaDTO mov=controlMovimientoCaja.buscarMovimientoCaja(m.getCodigo());
					entr.add(mov);
				}else{
					entr.add(m);
				}
			}	
		}
		return entr;
	}
	
	private Vector salidas(Vector movs) throws Exception{
		Vector sal=new Vector();
		for(int i=0;i<movs.size();i++){
			MovimientoCajaDTO m= (MovimientoCajaDTO)movs.elementAt(i);
			if(m.getTipoMovimiento()!=1){ //salida
				MovimientoCajaDTO mov=controlMovimientoCaja.buscarMovimientoCaja(m.getCodigo());
				sal.add(mov);
			}	
		}
		return sal;
	}
	
}




