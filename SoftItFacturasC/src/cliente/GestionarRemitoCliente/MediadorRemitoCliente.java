package cliente.GestionarRemitoCliente;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cliente.ClienteConection;
import cliente.GestionarCliente.MediadorGestionarCliente;
import cliente.Principal.GUIReport;

import common.Utils;
import common.dto.ClienteDTO;
import common.dto.ComercioDTO;
import common.dto.FacturaClienteDTO;
import common.dto.ItemFacturaDTO;
import common.interfaces.IControlComercio;
import common.interfaces.IControlFacturaCliente;

import constantes.NRO_REPORTE;

public class MediadorRemitoCliente implements ActionListener,ListSelectionListener {
	
	private GUIRemitoCliente guiRemitoCte = null;
	private IControlFacturaCliente controlFactCliente;
	private IControlComercio controlComercio;
	private MediadorGestionarCliente medGestionarCliente;
	public ClienteDTO cliente;
	public Vector productos = new Vector();
	public Vector cantProd = new Vector();
	public Vector precioUnit = new Vector();
	public Vector precioTotalIt = new Vector();
	private double importeTotal=0; 
	private ComercioDTO dist=null;
	String loc="";
	JFrame guiPadreCtrl;
	public FacturaClienteDTO remito=null;
	
	public MediadorRemitoCliente(JFrame guiPadre) throws Exception {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlFactCliente = clienteConexion.getIControlFCliente();
		this.controlComercio = clienteConexion.getIControlComercio();
		dist=controlComercio.obtenerComercio();
		guiPadreCtrl=guiPadre;
		if(dist==null){ 
			Utils.advertenciaUsr(guiRemitoCte,"Debe completar los datos de la Comercio para Facturar.");
		}else if(dist.getNroRemito()==null){
			Utils.advertenciaUsr(guiRemitoCte,"Debe completar en los datos de la Comercio la numeración de los comprobantes para Facturar.");
		}else{
			Long nroFacturaObt=new Long(0);
			nroFacturaObt=dist.getNroRemito();
			guiRemitoCte = new GUIRemitoCliente(guiPadre);
			guiRemitoCte.nroRemito=nroFacturaObt;
			guiRemitoCte.setActionListeners(this);
		}
	}
	
	public void show() {
		if(dist!=null){
			guiRemitoCte.actualizarNroRemito();
			Utils.show(guiRemitoCte);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if ((((Component)e.getSource()).getName().compareTo("ConfirmarRem")) == 0) {
			try {
				java.util.Date fu=guiRemitoCte.getJDateChooserFecha().getDate();
				Date fecha= Utils.crearFecha(fu);
				if (this.productos.size()==0){
					Utils.advertenciaUsr(guiRemitoCte,"Debe agregar algún Producto para poder generar el Remito.");
				} else if(guiRemitoCte.getJTFNombreC().getText().length()==0 ){
					Utils.advertenciaUsr(guiRemitoCte,"Debe seleccionar el Cliente.");
				} else{
					FacturaClienteDTO fc= new FacturaClienteDTO();
					fc.setAnulada(false);
					fc.setCliente(cliente);
					fc.setFechaImpresion(fecha);
					fc.setImporteTotal(importeTotal);
					fc.setNroFactura(guiRemitoCte.nroRemito);
					fc.setTipoFactura("RemitoCliente");
					fc.setCondVenta("");
					fc.setIva("");
					fc.setRemitoNro("");
					fc.setIngrBrutos("");
					Vector items= new Vector();
					for(int k=0;k<productos.size();k++){
						ItemFacturaDTO itNew = new ItemFacturaDTO();
						itNew.setFactura(fc);
						double cantpr=Double.parseDouble((String)cantProd.elementAt(k));
						itNew.setCantidad(cantpr);
						itNew.setProducto((String)productos.elementAt(k));
						itNew.setPrUnit(Double.parseDouble((String)precioUnit.elementAt(k)));
						double prTotIt=Double.parseDouble((String)precioTotalIt.elementAt(k));
						itNew.setPrTotal(prTotIt);
						items.add(itNew);
					}
					fc.setItems(items);
					this.controlFactCliente.agregarFacturaClienteTotal(fc,"Remito",remito,0);
					this.guiRemitoCte.dispose();
					new GUIReport(guiRemitoCte,NRO_REPORTE.remitoCliente,productos,cantProd,precioUnit,precioTotalIt,Utils.nroFact(guiRemitoCte.nroRemito),fecha,
							dist, cliente,"","","","","","",importeTotal);
				}
			} catch(Exception ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error Confirmar Remito");
			}
		}else if ((((Component)e.getSource()).getName().compareTo("BuscarC")) == 0) {
			buscarCliente();
		}else if ((((Component)e.getSource()).getName().compareTo("AgregarProd")) == 0) {
			try{
				String cant = guiRemitoCte.getJTFCantidad().getText();
				String desc = guiRemitoCte.getJTFProducto().getText();
				String importe = guiRemitoCte.getJTFImporte().getText();
				if(desc.length()==0){
					Utils.advertenciaUsr(guiRemitoCte,"Debe ingresar una descripcion.");
				}else if(importe.length()==0){
					Utils.advertenciaUsr(guiRemitoCte,"Debe ingresar un importe unit.");	
				}else if(importe.length()!=0 && !Utils.esDouble(importe)){
					Utils.advertenciaUsr(guiRemitoCte,"El importe ingresado no es un número correcto.");	                			
				}else{	
					if(cant.length()==0)
						cant="1";
					double c=Double.parseDouble(cant);
					productos.add(desc);
					cantProd.add(String.valueOf(c));
					double importeProd=Double.parseDouble(importe);
					precioUnit.add(Utils.ordenarDosDecimales(importeProd));
					double prTotal = Utils.redondear(importeProd*c,2);
					precioTotalIt.add(Utils.ordenarDosDecimales(prTotal));
					guiRemitoCte.getJTFCantidad().setText("1");
					guiRemitoCte.getJTFProducto().setText("");
					guiRemitoCte.getJTFImporte().setText("");
					cargarDatos();
				}
			}catch(Exception ex){
				Utils.manejoErrores(ex,this.getClass(),"Error AgregarProducto");
			}
		}else if ((((Component)e.getSource()).getName().compareTo("EliminarP")) == 0) {
			if (guiRemitoCte.tabla.getSelectedRow() == -1){
				Utils.advertenciaUsr(guiRemitoCte,"Para poder Eliminar un Producto del Remito debe seleccionarlo previamente.");
			} else {
				int posProd = guiRemitoCte.tabla.getSelectedRow();
				int prueba=Utils.aceptarCancelarAccion(guiRemitoCte,"Esta seguro que desea eliminar el Item Número "+ (posProd+1)+" de la Factura.");
				if (prueba == 0){
					productos.removeElementAt(posProd);
					cantProd.removeElementAt(posProd);
					precioUnit.removeElementAt(posProd);
					precioTotalIt.removeElementAt(posProd);
					cargarDatos();
				}
			}
		}else if ((((Component)e.getSource()).getName().compareTo("Cancelar")) == 0) {
			guiRemitoCte.dispose();
		}
	}
	
	private void buscarCliente() {
		if (medGestionarCliente== null) {
			medGestionarCliente= new MediadorGestionarCliente(this,guiRemitoCte);
		} else {
			if (!medGestionarCliente.getGUI().isVisible()) {
				medGestionarCliente.getGUI().setVisible(true);
			}
		}
		if (cliente != null){
			this.guiRemitoCte.getJtCuit().setText(cliente.getCuit());
			this.guiRemitoCte.getJTFNombreC().setText(cliente.getNombre());
			this.guiRemitoCte.getJTFProducto().setEnabled(true);
			this.guiRemitoCte.getJTFImporte().setEnabled(true);
			this.guiRemitoCte.getJTFCantidad().setEnabled(true);
			this.guiRemitoCte.getJBAgregarProd().setEnabled(true);
			this.cargarCliente(cliente);
		}
	}
	
	private void cargarCliente(ClienteDTO c) {
		this.cliente = c;
	}
	
	public void actualizarCliente() {
		this.guiRemitoCte.getJtCuit().setText(cliente.getCuit());
		this.guiRemitoCte.getJTFNombreC().setText(cliente.getNombre());
		this.guiRemitoCte.getJTFProducto().setEnabled(true);
		this.guiRemitoCte.getJTFImporte().setEnabled(true);
		this.guiRemitoCte.getJTFCantidad().setEnabled(true);
		this.guiRemitoCte.getJBAgregarProd().setEnabled(true);
	}
	
	public void cargarDatos() {
		importeTotal=0;
		try {
			guiRemitoCte.datos = new Object[productos.size()][guiRemitoCte.titulos.length];
			int i = 0;
			for (int j = 0; j < productos.size(); j++) {
				String prod= (String) productos.elementAt(j);
				String prUnit= (String)precioUnit.elementAt(j); 
				String cantidad=(String)cantProd.elementAt(j);
				String precioTotal=(String)precioTotalIt.elementAt(j);
				double prTotal=Double.parseDouble(precioTotal);
				importeTotal = Utils.redondear(importeTotal+prTotal,2);
				Object[] temp = {cantidad,prod, prUnit,precioTotal};
				guiRemitoCte.datos[i] = temp;
				i++;
			}
			if(productos.size()>0){
				this.guiRemitoCte.getJBEliminarProd().setEnabled(true);
				this.guiRemitoCte.getJBConfirmarRemito().setEnabled(true);
			}else{
				this.guiRemitoCte.getJBEliminarProd().setEnabled(false);	
				this.guiRemitoCte.getJBConfirmarRemito().setEnabled(false);
			}	
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error CargarDatos");
		}
		guiRemitoCte.tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guiRemitoCte.actualizarTabla();
		guiRemitoCte.getJTFITotal().setText(Utils.ordenarDosDecimales(importeTotal));
	}
	
	public void valueChanged(ListSelectionEvent arg0) {
	}
	
	public void keyTyped(KeyEvent arg0) {
	}
	
	public void keyPressed(KeyEvent arg0) {
	}
	
}
