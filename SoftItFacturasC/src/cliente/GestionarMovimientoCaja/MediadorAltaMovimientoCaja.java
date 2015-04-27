package cliente.GestionarMovimientoCaja;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JDialog;

import cliente.ClienteConection;
import cliente.ListarFacturasCliente.MediadorListarFacturasCliente;
import cliente.ListarRemitosCliente.MediadorListarRemitosCliente;
import cliente.Principal.GUIReport;

import common.Utils;
import common.dto.ComercioDTO;
import common.dto.FacturaDTO;
import common.dto.MovimientoCajaDTO;
import common.interfaces.IControlCliente;
import common.interfaces.IControlComercio;
import common.interfaces.IControlFacturaCliente;
import common.interfaces.IControlMovimientoCaja;
import constantes.NRO_REPORTE;

public class MediadorAltaMovimientoCaja implements ActionListener {
	
	private GUIAltaModMovimientoCaja guiMovimientoCaja = null;
	private MediadorGestionarMovimientoCaja mgMovimientoCaja;
	public IControlMovimientoCaja controlMovimientoCaja;
	public IControlCliente controlCte;
	public IControlFacturaCliente controlFactCte;
	private IControlComercio controlComercio;
	public String tipoFact;
	public FacturaDTO factura;
	public int codProv=0;
	private MediadorListarFacturasCliente medListarFactCte;
	private MediadorListarRemitosCliente medListarRemitosCte;
	private java.util.Date hoy=new java.util.Date();
	private ComercioDTO dist=null;
	
	public MediadorAltaMovimientoCaja(MediadorGestionarMovimientoCaja oldMGMovimientoCaja,JDialog guiPadre) throws Exception {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlMovimientoCaja = clienteConexion.getIControlMovimientoCaja();
		this.controlCte = clienteConexion.getIControlCliente();
		this.controlFactCte = clienteConexion.getIControlFCliente();
		this.controlComercio = clienteConexion.getIControlComercio();
		int nro= this.controlMovimientoCaja.obtenerNroMovCaja();
		guiMovimientoCaja = new GUIAltaModMovimientoCaja(nro,guiPadre);
		guiMovimientoCaja.setActionListeners(this);
		mgMovimientoCaja = oldMGMovimientoCaja;
		Utils.show(guiMovimientoCaja);
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String tipoFactB = guiMovimientoCaja.getJCTipoFact().getSelectedItem().toString();
		if (source == guiMovimientoCaja.getJBAceptar()) {
			String codigo = guiMovimientoCaja.getJTFCodigo().getText();
			String tipoMov = guiMovimientoCaja.getJCTipoMov().getSelectedItem().toString();
			java.util.Date fu= guiMovimientoCaja.getJDateChooserFecha().getDate();
			Date fecha= Utils.crearFecha(fu);
			String importe = guiMovimientoCaja.getJTFImporte().getText();
			String formaPago = guiMovimientoCaja.getJCBFormaPago().getSelectedItem().toString();
			String nroCheque =  guiMovimientoCaja.getJTFNroCheque().getText();
			String desc = guiMovimientoCaja.getJTFDescr().getText();
			String conFact = guiMovimientoCaja.getJCConFact().getSelectedItem().toString();
			String tipoFactura = guiMovimientoCaja.getJCTipoFact().getSelectedItem().toString();
			String nroFact = guiMovimientoCaja.getJTFFactura().getText();
			try{
				if (codigo.length()==0){
					Utils.advertenciaUsr(guiMovimientoCaja,"Por favor ingrese el Código del Movimiento de Caja.");
				}else if (this.controlMovimientoCaja.existeMovimientoCaja(Integer.parseInt(codigo))){
					Utils.advertenciaUsr(guiMovimientoCaja,"El Movimiento de Caja con ese Código ya existe.");   
				}else if (tipoMov.length()==0 || importe.length()==0  || desc.length()==0 || formaPago.length()==0 || conFact.length()==0){
					Utils.advertenciaUsr(guiMovimientoCaja,"Alguno de los campos obligatorios esta vacio.");
				}else if((conFact.compareTo("SI")==0) && (nroFact.length()==0)  ){
					Utils.advertenciaUsr(guiMovimientoCaja,"Debe cargar cargar los datos de la Factura.");
				}else {
					MovimientoCajaDTO mimovDTO = new MovimientoCajaDTO();
					mimovDTO.setCodigo(Integer.parseInt(codigo));
					mimovDTO.setFecha(fecha);
					mimovDTO.setImporte(Double.parseDouble(importe));
					mimovDTO.setDescripcion(desc);
					mimovDTO.setFormaPago(formaPago);
					if(formaPago.compareTo("CHEQUE")==0)
						mimovDTO.setNroCheque(new Long(nroCheque));
					if(conFact.compareTo("SI")==0){
						mimovDTO.setConFactura(true);
						mimovDTO.setTipoFactura(tipoFactura);
						mimovDTO.setFactura(factura);
					}else{
						mimovDTO.setConFactura(false);
					}
					if(tipoMov.compareTo("ENTRADA")==0)
						mimovDTO.setTipoMovimiento(1);
					else
						mimovDTO.setTipoMovimiento(0);
					Long nroRecibo=this.controlMovimientoCaja.agregarMovimientoCaja_Fact(mimovDTO);
					guiMovimientoCaja.dispose();
					if(conFact.compareTo("SI")==0){ //Se que solo se pueden efectuar pagos parciales de comprobantes C
						new GUIReport(guiMovimientoCaja,NRO_REPORTE.reciboCliente,nroRecibo,mimovDTO,controlComercio.obtenerComercio());
					}
					mgMovimientoCaja.actualizarDatosTabla();
				}
			} catch(Exception ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
			}
		}else if ((source == guiMovimientoCaja.getJBBuscarFact()) && (tipoFactB.compareTo("Factura Cliente-Tipo C")==0)) {
			buscarFacturaCliente(Utils.getMes(hoy),Utils.getAnio(hoy));
		}else if ((source == guiMovimientoCaja.getJBBuscarFact()) && (tipoFactB.compareTo("Remito Cliente")==0)) {
			buscarRemitoCliente(Utils.getMes(hoy),Utils.getAnio(hoy));  //discr tipo Remito  
		} else if (source == guiMovimientoCaja.getJBCancelar()) {
			((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
		}else{
			if(guiMovimientoCaja.getJCBFormaPago()==source){
				if(guiMovimientoCaja.getJCBFormaPago().getSelectedItem().toString().compareTo("CHEQUE")==0){
					guiMovimientoCaja.getJTFNroCheque().setEnabled(true);
				}else{
					guiMovimientoCaja.getJTFNroCheque().setEnabled(false);
				}
			}
			if(guiMovimientoCaja.getJCConFact()==source){
				if(guiMovimientoCaja.getJCConFact().getSelectedItem().toString().compareTo("SI")==0){
					guiMovimientoCaja.getJBBuscarFact().setEnabled(true);
					guiMovimientoCaja.getJCTipoFact().setEnabled(true);
					guiMovimientoCaja.getJTFTextoBusq().setEnabled(true);
					guiMovimientoCaja.getJCTipoMov().setSelectedItem("ENTRADA"); //Lo seteo aca en entrada porq tengo un solo tipo de factura
				}else{
					guiMovimientoCaja.getJBBuscarFact().setEnabled(false);
					guiMovimientoCaja.getJCTipoFact().setEnabled(false);
					guiMovimientoCaja.getJTFTextoBusq().setEnabled(false);
				}
			}
			if(guiMovimientoCaja.getJCTipoFact()==source){
				if(tipoFactB.equals("Factura Cliente-Tipo C")){
					if(tipoFact!=null && !tipoFact.equals("FacturaCliente-C"))
						guiMovimientoCaja.setFactura("");
					guiMovimientoCaja.getJCTipoMov().setSelectedItem("ENTRADA");
				}else if(tipoFactB.equals("Remito Cliente")){
					if(tipoFact!=null && !tipoFact.equals("RemitoCliente"))
						guiMovimientoCaja.setFactura("");
					guiMovimientoCaja.getJCTipoMov().setSelectedItem("ENTRADA");		
				}
			}
		}
	}
	
	private void buscarFacturaCliente(int mesL,int anioL) {
		String textoBusqueda=guiMovimientoCaja.getJTFTextoBusq().getText();
		if (medListarFactCte== null) {
			medListarFactCte= new MediadorListarFacturasCliente(this,mesL,anioL,textoBusqueda,guiMovimientoCaja);
		} else {
			medListarFactCte= new MediadorListarFacturasCliente(this,mesL,anioL,textoBusqueda,guiMovimientoCaja);
		}
		if (factura != null){
			this.guiMovimientoCaja.getJTFFactura().setText(Utils.nroFact(factura.getNroFactura()));
			this.cargarFactura(factura);
		}
	}
	
	private void buscarRemitoCliente(int mesL,int anioL) {
		String textoBusqueda=guiMovimientoCaja.getJTFTextoBusq().getText();
		if (medListarRemitosCte== null) {
			medListarRemitosCte= new MediadorListarRemitosCliente(this,mesL,anioL,textoBusqueda,guiMovimientoCaja);
		} else {
			medListarRemitosCte= new MediadorListarRemitosCliente(this,mesL,anioL,textoBusqueda,guiMovimientoCaja);
		}
		if (factura != null){
			this.guiMovimientoCaja.getJTFFactura().setText(Utils.nroFact(factura.getNroFactura()));
			this.cargarFactura(factura);
		}
	}
	
	public void actualizarFactura() {
		guiMovimientoCaja.setFactura(Utils.nroFact(factura.getNroFactura()));
	}
	
	private void cargarFactura(FacturaDTO fact) {
		this.factura = fact;
	}
}

