package cliente.GestionarNotaDebito;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cliente.ClienteConection;
import cliente.ListarFacturasCliente.MediadorListarFacturasCliente;
import cliente.Principal.GUIReport;

import common.Utils;
import common.dto.ClienteDTO;
import common.dto.ComercioDTO;
import common.dto.FacturaClienteDTO;
import common.dto.ItemFacturaDTO;
import common.dto.NotaDebitoDTO;
import common.interfaces.IControlComercio;
import common.interfaces.IControlNotaDebito;

import constantes.NRO_REPORTE;

public class MediadorAltaNotaDebitoCliente implements ActionListener,ListSelectionListener {
	
	private GUIAltaNotaDebitoCliente guiAltaNotaDebito = null;
	private IControlNotaDebito controlNotaDebito;
	private IControlComercio controlComercio;
	public ClienteDTO cliente=null;
	public Vector productos = new Vector();
	public Vector cantProd = new Vector();
	public Vector precioUnit = new Vector();
	public Vector precioTotalIt = new Vector();
	private double importeTotal=0;
	private Vector todosProductos;
	private ComercioDTO dist=null;
	String tipo=""; // no se cargan datos aca
	String loc="";
	private FacturaClienteDTO facturaCte; //es la factura de la cual genero la nota de Debito.
	private String tipoFactNC;
	private Long nroFacturaNC;
	private Long idFacturaNC;
	public MediadorAltaNotaDebitoCliente(JFrame guiPadre) throws Exception {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlNotaDebito = clienteConexion.getIControlNotaDebito();
		this.controlComercio = clienteConexion.getIControlComercio();
		dist=controlComercio.obtenerComercio();
		if(dist==null){ 
			Utils.advertenciaUsr(guiAltaNotaDebito,"Debe completar los datos de la Comercio para generar Notas de Débito.");
		}else{	
			guiAltaNotaDebito = new GUIAltaNotaDebitoCliente(guiPadre);
			guiAltaNotaDebito.setActionListeners(this);
			Utils.show(guiAltaNotaDebito);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if ((((Component)e.getSource()).getName().compareTo("ConfirmarFact")) == 0) {
			try {
				java.util.Date fu=guiAltaNotaDebito.getJDateChooserFecha().getDate();
				String ivaCte= guiAltaNotaDebito.getJCTipoIva().getText();
				String nroFact=guiAltaNotaDebito.getJTFNroNotaDebito().getText();
				String ibCte=guiAltaNotaDebito.getJTFIngrBrutos().getText();
				String observ = guiAltaNotaDebito.getJTFObservaciones().getText();
				Date fecha= Utils.crearFecha(fu);
				if (this.productos.size()==0){
					Utils.advertenciaUsr(guiAltaNotaDebito,"Debe agregar algún Producto para poder generar la Nota de Débito.");
				} else if(guiAltaNotaDebito.getJTFNombreC().getText().length()==0 ){
					Utils.advertenciaUsr(guiAltaNotaDebito,"Debe seleccionar el Cliente.");
				} else{
					NotaDebitoDTO nc= new NotaDebitoDTO();
					nc.setAnulada(false);
					nc.setTipoFactura("NotaDebito");
					nc.setCliente(cliente);
					nc.setFecha(fecha);
					nc.setTipoFacturaNC(this.tipoFactNC);
					nc.setNroFacturaNC(this.nroFacturaNC);
					nc.setIdFacturaNC(this.idFacturaNC);
					nc.setImporteTotal(Utils.redondear(importeTotal,2));
					nc.setNroFactura(guiAltaNotaDebito.nroNotaDeb);
					nc.setObservaciones(observ);
					Vector items= new Vector();
					for(int k=0;k<productos.size();k++){
						ItemFacturaDTO itNew = new ItemFacturaDTO();
						itNew.setFactura(nc);
						double cantpr=Double.parseDouble((String)cantProd.elementAt(k));
						itNew.setCantidad(cantpr);
						itNew.setProducto((String)productos.elementAt(k));
						itNew.setPrUnit(Double.parseDouble((String)precioUnit.elementAt(k)));
						double prTotIt=Double.parseDouble((String)precioTotalIt.elementAt(k));
						itNew.setPrTotal(prTotIt);
						items.add(itNew);
					}
					nc.setItems(items);
					if(cliente!=null){
						this.controlNotaDebito.agregarNotaDebitoCliente(nc);
					/*	new GUIReport(guiAltaNotaDebito,NRO_REPORTE.notaDebito,productos,cantProd,precioUnit,precioTotalIt,"",fecha,
								dist, cliente,ivaCte,"",observ,"",ibCte,tipo,nc.getImporteTotal());*/
						
						new GUIReport(guiAltaNotaDebito,NRO_REPORTE.notaDebito,productos,cantProd,precioUnit,precioTotalIt,nroFact,fecha,
								dist, cliente,ivaCte,observ,ibCte,tipo,nc.getImporteTotal());
					
					}
					this.guiAltaNotaDebito.dispose();
				}
			} catch(Exception ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error ConfirmarFactura");
			}
		}else if ((((Component)e.getSource()).getName().compareTo("BuscarFact")) == 0) {
			try {
				buscarFacturaCliente();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}else if ((((Component)e.getSource()).getName().compareTo("AgregarProd")) == 0) {
			try{
				String cant = guiAltaNotaDebito.getJTFCantidad().getText();
				String desc = guiAltaNotaDebito.getJTFProducto().getText();
				String importe = guiAltaNotaDebito.getJTFImporte().getText();
				if(desc.length()==0){
					Utils.advertenciaUsr(guiAltaNotaDebito,"Debe ingresar una descripcion.");
				}else if(importe.length()==0){
					Utils.advertenciaUsr(guiAltaNotaDebito,"Debe ingresar un importe unit.");	
				}else if(importe.length()!=0 && !Utils.esDouble(importe)){
					Utils.advertenciaUsr(guiAltaNotaDebito,"El importe ingresado no es un número correcto.");	                			
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
					guiAltaNotaDebito.getJTFCantidad().setText("1");
					guiAltaNotaDebito.getJTFImporte().setText("");
					guiAltaNotaDebito.getJTFProducto().setText("");
					cargarDatos();
				}
			}catch(Exception ex){
				Utils.manejoErrores(ex,this.getClass(),"Error AgregarProducto");
			}
		}else if ((((Component)e.getSource()).getName().compareTo("EliminarP")) == 0) {
			if (guiAltaNotaDebito.tabla.getSelectedRow() == -1){
				Utils.advertenciaUsr(guiAltaNotaDebito,"Para poder Eliminar un Producto de la Factura debe seleccionarlo previamente.");
			} else {
				int posProd = guiAltaNotaDebito.tabla.getSelectedRow();
				int prueba=Utils.aceptarCancelarAccion(guiAltaNotaDebito,"Esta seguro que desea eliminar el Item Número "+ (posProd+1)+" de la Factura.");
				if (prueba == 0){
					productos.removeElementAt(posProd);
					cantProd.removeElementAt(posProd);
					precioUnit.removeElementAt(posProd);
					precioTotalIt.removeElementAt(posProd);
					cargarDatos();
				}
			}
		}else if ((((Component)e.getSource()).getName().compareTo("Cancelar")) == 0) {
			guiAltaNotaDebito.dispose();
		}
	}
	
	private void buscarFacturaCliente() throws Exception {
		java.util.Date hoy= new java.util.Date();
		int mesL=Utils.getMes(hoy);
		int anioL=Utils.getAnio(hoy);
		new MediadorListarFacturasCliente(this,mesL,anioL,guiAltaNotaDebito);
		if (facturaCte != null){
			this.cargarFacturaCliente(facturaCte);
		}
	}
	
	private void cargarFacturaCliente(FacturaClienteDTO c) {
		this.facturaCte = c;
	}
	
	public void actualizarFacturaCliente(FacturaClienteDTO fact) {
		facturaCte=fact;
		String nro="";
		nro=Utils.nroFact(dist.getNroNotaDebito());
		if(fact.getTipoFactura().compareTo("FacturaCliente-C")==0){
			tipoFactNC="C";
		}	
		nroFacturaNC=fact.getNroFactura();
		idFacturaNC=fact.getId();
		this.guiAltaNotaDebito.nroNotaDeb = dist.getNroNotaDebito();
		this.guiAltaNotaDebito.getJTFNroNotaDebito().setText(nro);
		this.guiAltaNotaDebito.getJtCuit().setText(fact.getCliente().getCuit());
		this.guiAltaNotaDebito.getJTFNombreC().setText(fact.getCliente().getNombre());
		this.guiAltaNotaDebito.getJTFIngrBrutos().setText(fact.getCliente().getIngBrutosCl());
		this.guiAltaNotaDebito.getJCTipoIva().setText(fact.getCliente().getIvaCl());
		cliente=fact.getCliente();
		todosProductos = new Vector();
		productos = new Vector();
		cantProd = new Vector();
		precioUnit = new Vector();
		precioTotalIt = new Vector();
		for(Iterator it=fact.getItems().iterator();it.hasNext();){
			ItemFacturaDTO item=(ItemFacturaDTO)it.next();
			todosProductos.add(item.getProducto());
		}
		cargarDatos();
		this.guiAltaNotaDebito.getJBAgregarProd().setEnabled(true);
		this.guiAltaNotaDebito.getJTFProducto().setEnabled(true);
		this.guiAltaNotaDebito.getJTFImporte().setEnabled(true);
		this.guiAltaNotaDebito.getJTFCantidad().setEnabled(true);
	}
	
	public void cargarDatos() {
		importeTotal=0;
		try {
			guiAltaNotaDebito.datos = new Object[productos.size()][guiAltaNotaDebito.titulos.length];
			int i = 0;
			for (int j = 0; j < productos.size(); j++) {
				String prod= (String) productos.elementAt(j);
				String prUnit= (String)precioUnit.elementAt(j); 
				String cantidad=(String)cantProd.elementAt(j);
				String precioTotal=(String)precioTotalIt.elementAt(j);
				double prTotal=Double.parseDouble(precioTotal);
				importeTotal = Utils.redondear(importeTotal+prTotal,2);
				Object[] temp = {cantidad,prod, prUnit, precioTotal};
				guiAltaNotaDebito.datos[i] = temp;
				i++;
			}
			if(productos.size()>0){
				this.guiAltaNotaDebito.getJBEliminarProd().setEnabled(true);
				this.guiAltaNotaDebito.getJBConfirmaFact().setEnabled(true);
			}else{
				this.guiAltaNotaDebito.getJBEliminarProd().setEnabled(false);	
				this.guiAltaNotaDebito.getJBConfirmaFact().setEnabled(false);
			}	
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error CargarDatos");
		}
		guiAltaNotaDebito.tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guiAltaNotaDebito.actualizarTabla();
		double totalGral=Utils.redondear(importeTotal,2);
		guiAltaNotaDebito.getJTFITotal().setText(Utils.ordenarDosDecimales(totalGral));
	}
	
	public void valueChanged(ListSelectionEvent arg0) {
	}
	
	public void keyTyped(KeyEvent arg0) {
	}
	
	public void keyPressed(KeyEvent arg0) {
	}
	
}
