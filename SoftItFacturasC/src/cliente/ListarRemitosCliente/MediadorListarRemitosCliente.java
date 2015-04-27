package cliente.ListarRemitosCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cliente.ClienteConection;
import cliente.GestionarFacturaCliente.MediadorFacturarCliente;
import cliente.GestionarMovimientoCaja.MediadorAltaMovimientoCaja;
import cliente.Principal.GUIReport;

import common.Utils;
import common.dto.ClienteDTO;
import common.dto.ComercioDTO;
import common.dto.FacturaClienteDTO;
import common.dto.ItemFacturaDTO;
import common.dto.MovimientoCajaDTO;
import common.interfaces.IControlCliente;
import common.interfaces.IControlComercio;
import common.interfaces.IControlFacturaCliente;

import constantes.NRO_REPORTE;

public class MediadorListarRemitosCliente implements ActionListener, KeyListener, ListSelectionListener {
	
	private GUIListarRemitosCliente guiTodasFactCte = null;
	protected IControlFacturaCliente controlFactCte;
	private FacturaClienteDTO miFactCte;
	private IControlComercio controlComercio;
	private IControlCliente controlCliente;
	private FacturaClienteDTO fact;
	private MediadorFacturarCliente medFecturarCliente;
	private boolean flag=false;
	private MediadorAltaMovimientoCaja medAltaMovCaja;
	private boolean listarSinFact=false;
	private int mesLI;
	private int anioLI;
	
	public MediadorListarRemitosCliente(int mes, int anio,JFrame guiPadre) {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlFactCte = clienteConexion.getIControlFCliente();
		this.controlComercio = clienteConexion.getIControlComercio();
		this.controlCliente = clienteConexion.getIControlCliente();
		mesLI=mes;
		anioLI=anio;
		listarSinFact=false;
		this.guiTodasFactCte = new GUIListarRemitosCliente(mesLI,anioLI,guiPadre);
		this.guiTodasFactCte.setActionListeners(this);
		actualizarDatosTabla();
		this.guiTodasFactCte.setListSelectionListener(this);
		this.guiTodasFactCte.setKeyListener(this);
		this.flag=true;
		Utils.show(guiTodasFactCte);
	}
	
	public MediadorListarRemitosCliente(MediadorFacturarCliente medFC,int mes, int anio,String cliente,JFrame guiPadre) {
		this.medFecturarCliente = medFC;
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlFactCte = clienteConexion.getIControlFCliente();
		this.controlComercio = clienteConexion.getIControlComercio();
		this.controlCliente = clienteConexion.getIControlCliente();
		mesLI=mes;
		anioLI=anio;
		listarSinFact=true;
		this.guiTodasFactCte = new GUIListarRemitosCliente(mesLI,anioLI,guiPadre);
		this.guiTodasFactCte.setActionListeners(this);
		guiTodasFactCte.getJTFCliente().setText(cliente);
		actualizarDatosTabla();
		this.guiTodasFactCte.setListSelectionListener(this);
		this.guiTodasFactCte.setKeyListener(this);
		Utils.show(guiTodasFactCte);
	}
	
	public MediadorListarRemitosCliente(MediadorAltaMovimientoCaja medAMC,int mes, int anio,String cliente,JDialog guiPadre) {
		this.medAltaMovCaja = medAMC;
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlFactCte = clienteConexion.getIControlFCliente();
		this.controlComercio = clienteConexion.getIControlComercio();
		this.controlCliente = clienteConexion.getIControlCliente();
		mesLI=mes;
		anioLI=anio;
		listarSinFact=true;
		this.guiTodasFactCte = new GUIListarRemitosCliente(mesLI,anioLI,guiPadre);
		this.guiTodasFactCte.setActionListeners(this);
		guiTodasFactCte.getJTFCliente().setText(cliente);
		actualizarDatosTabla();
		this.guiTodasFactCte.setListSelectionListener(this);
		this.guiTodasFactCte.setKeyListener(this);
		Utils.show(guiTodasFactCte);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == guiTodasFactCte.getJBImprimir()) {
			try {
				if (guiTodasFactCte.jtDatos.getSelectedRow() == -1){
					Utils.advertenciaUsr(guiTodasFactCte,"Para poder Imprimir un Remito debe ser previamente seleccionado.");
				}else{
					Long id = (Long)guiTodasFactCte.datos[guiTodasFactCte.jtDatos.getSelectedRow()][0];
					miFactCte = this.controlFactCte.buscarFacturaCliente(id);
					Vector productos=new Vector();
					Vector cantProd=new Vector();
					Vector prUnit=new Vector();
					Vector prTotal=new Vector();
					Vector items=miFactCte.getItems();
					for(Iterator j=items.iterator();j.hasNext();){
						ItemFacturaDTO pr= (ItemFacturaDTO) j.next();
						productos.add(pr.getProducto());
						cantProd.add(String.valueOf(pr.getCantidad()));
						prUnit.add(Utils.ordenarDosDecimales(pr.getPrUnit()));
						prTotal.add(Utils.ordenarDosDecimales(pr.getPrTotal()));
					}	
					ClienteDTO cte=controlCliente.buscarCliente(miFactCte.getCliente().getId());
					ComercioDTO dist=controlComercio.obtenerComercio();
					new GUIReport(guiTodasFactCte,NRO_REPORTE.remitoCliente,productos,cantProd,prUnit,prTotal, Utils.nroFact(miFactCte.getNroFactura()),miFactCte.getFechaImpresion(),
							dist, cte,"","","","","","",miFactCte.getImporteTotal());
				}
			}
			catch(Exception ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
			}
		}else if (source == guiTodasFactCte.getJBAnularFactura()){
			anularFactura();	
		}else if (source == guiTodasFactCte.getJBCambiarPeriodo()){
			String anioB = guiTodasFactCte.getJTFAnio().getText();
			if(anioB.length()==0){
				Utils.advertenciaUsr(guiTodasFactCte,"Por favor ingrese el Año.");
			}else if(anioB.length()!=4){
				Utils.advertenciaUsr(guiTodasFactCte,"El año debe ser un número de 4 dígitos.");
			}else{
				anioLI= Integer.parseInt(anioB);
				mesLI = guiTodasFactCte.getJCBMes().getSelectedIndex()+1; //para que el numero del indice de con el mes sumo 1
				actualizarDatosTabla();
			} 		   
		}else if (source == guiTodasFactCte.getJBFiltrar()){
			actualizarDatosTabla();
		}else if (source == guiTodasFactCte.getJBSalir()){
			if(flag){
				this.guiTodasFactCte.dispose();
			}
			else{
				if (cargarFilaSeleccionada()) {
					if (medFecturarCliente != null) {
						if(fact.isAnulada())
							Utils.advertenciaUsr(guiTodasFactCte,"No es posible generar un remito de una factura anulada.");
						else{
							medFecturarCliente.actualizarRemito(fact);
							this.guiTodasFactCte.dispose();
						}
					}
					if (medAltaMovCaja != null) {
						medAltaMovCaja.factura = fact;
						medAltaMovCaja.tipoFact = fact.getTipoFactura();
						medAltaMovCaja.actualizarFactura();
						this.guiTodasFactCte.dispose();
					}
				}
			}
		}else { 
			guiTodasFactCte.dispose();
		}
	}
	
	public boolean cargarFilaSeleccionada() {
		boolean result=false;
		try{
			if (guiTodasFactCte.jtDatos.getSelectedRow() == -1) {
				Utils.advertenciaUsr(guiTodasFactCte,"Debe seleccionar un Remito.");
				result = false;
			}else{
				Long id = (Long)guiTodasFactCte.datos[guiTodasFactCte.jtDatos.getSelectedRow()][0];
				fact = this.controlFactCte.buscarFacturaCliente(id);
				result = true;
			}
		} catch (Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error CargarFilaSeleccionada");
		}
		return result;
	}
	
	public void actualizarDatosTabla() {
		try {
			Vector facturas = this.controlFactCte.obtenerFacturaClientesPeriodoFiltros(listarSinFact,"RemitoCliente",mesLI,anioLI, guiTodasFactCte.getJTFFecha().getText(),guiTodasFactCte.getJTFNro().getText(),guiTodasFactCte.getJTFCliente().getText());
			guiTodasFactCte.datos = new Object[facturas.size()][guiTodasFactCte.titulos.length];
			guiTodasFactCte.getJTFPeriodo().setText(mesLI+" - "+anioLI);
			for (int j = 0; j < facturas.size(); j++) {
				FacturaClienteDTO r=(FacturaClienteDTO)facturas.elementAt(j);
				String compr = "";
				Vector movs = r.getComprobantesPago();
				String impAbonado=Utils.ordenarDosDecimales(r.getImporteAbonado());
				Date fpago=r.getFechaPago();
				if(controlFactCte.existeFacturaDeRemito(String.valueOf(r.getNroFactura()))){
					FacturaClienteDTO fcteRem=controlFactCte.buscarFacturaDeRemito(String.valueOf(r.getNroFactura()));
					fpago=fcteRem.getFechaPago();
					impAbonado=Utils.ordenarDosDecimales(fcteRem.getImporteAbonado());
					movs = fcteRem.getComprobantesPago();
				}
				
				for(Iterator it= movs.iterator(); it.hasNext();){
					MovimientoCajaDTO mc = (MovimientoCajaDTO) it.next();
					compr +=mc.getCodigo()+"-";
				}
				if(compr.length()>1)
					compr=compr.substring(0,compr.length()-1);
				String anulada="";
				if(r.isAnulada())
					anulada="SI";
				Object[] temp = {r.getId(),common.Utils.getStrUtilDate(r.getFechaImpresion()),Utils.nroFact(r.getNroFactura()),r.getCliente().getNombre(),String.valueOf(r.getImporteTotal()),impAbonado,common.Utils.getStrUtilDate(fpago),compr,anulada};
				guiTodasFactCte.datos[j] = temp;
			}
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error ActualizarFecha");
		}
		guiTodasFactCte.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guiTodasFactCte.actualizarTabla();
	}
	
	private void anularFactura() {
		try{
			if (guiTodasFactCte.jtDatos.getSelectedRow() == -1){
				Utils.advertenciaUsr(guiTodasFactCte,"Para poder anular un Remito debe seleccionarlo previamente.");
			} else {
				Long id = (Long)guiTodasFactCte.datos[guiTodasFactCte.jtDatos.getSelectedRow()][0];
				String nroFactura = (String)guiTodasFactCte.datos[guiTodasFactCte.jtDatos.getSelectedRow()][2];
				String val[] =nroFactura.split("-");
				String nroFacturaB=new Long(val[0])+val[1];
				if (controlFactCte.facturaAsociada(id)) {
					Utils.advertenciaUsr(guiTodasFactCte,"El Remito no puede ser borrado porque registra pagos.");
				}else if(controlFactCte.existeFacturaDeRemito(nroFacturaB)){
					Utils.advertenciaUsr(guiTodasFactCte,"El Remito no puede ser borrado porque se ha efectuado su factura correspondiente.");
				}else{
					int prueba = Utils.aceptarCancelarAccion(guiTodasFactCte,"Esta seguro que desea Anular el Remito Nro: \n"+ nroFactura);
					if (prueba == 0){
						this.controlFactCte.anularFacturaCliente(id);
						actualizarDatosTabla();
					}    
				}     
				
			}
			
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error anularFactura");
		}
	}
	
	public GUIListarRemitosCliente getGUI() {
		return guiTodasFactCte;
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
	
	
}




