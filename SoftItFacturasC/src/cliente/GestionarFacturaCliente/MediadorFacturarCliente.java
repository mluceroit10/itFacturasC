package cliente.GestionarFacturaCliente;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cliente.ClienteConection;
import cliente.GestionarCliente.MediadorGestionarCliente;
import cliente.ListarRemitosCliente.MediadorListarRemitosCliente;
import cliente.Principal.GUIReport;

import common.Utils;
import common.dto.ClienteDTO;
import common.dto.ComercioDTO;
import common.dto.FacturaClienteDTO;
import common.dto.ItemFacturaDTO;
import common.interfaces.IControlComercio;
import common.interfaces.IControlFacturaCliente;
import common.interfaces.IControlMovimientoCaja;

import constantes.NRO_REPORTE;

public class MediadorFacturarCliente implements ActionListener,ListSelectionListener {
	
	private GUIFacturarCliente guiFacturarCte = null;
	private IControlFacturaCliente controlFactCliente;
	private IControlMovimientoCaja controlMC;
	private IControlComercio controlComercio;
	private MediadorGestionarCliente medGestionarCliente;
	public ClienteDTO cliente;
	public Vector productos = new Vector();
	public Vector cantProd = new Vector();
	public Vector precioUnit = new Vector();
	public Vector precioTotalIt = new Vector();
	private double importeTotal=0; 
	private ComercioDTO dist=null;
	String tipo="C";
	private boolean mostrar=false;
	String loc="";
	public FacturaClienteDTO remito;
	private JFrame guiPadreCtrl;
	
	public MediadorFacturarCliente(JFrame guiPadre) throws Exception {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlFactCliente = clienteConexion.getIControlFCliente();
		this.controlMC = clienteConexion.getIControlMovimientoCaja();
		this.controlComercio = clienteConexion.getIControlComercio();
		guiPadreCtrl=guiPadre;
		dist=controlComercio.obtenerComercio();
		if(dist==null){ 
			Utils.advertenciaUsr(guiFacturarCte,"Debe completar los datos de la Comercio para Facturar.");
		}else if(dist.getNroFactC()==null){
			Utils.advertenciaUsr(guiFacturarCte,"Debe completar en los datos de la Comercio la numeración de los comprobantes para Facturar.");
		}else{	
		/*	Object seleccion = JOptionPane.showInputDialog(guiPadreCtrl,"Seleccione el Tipo de Factura", "Selector Tipo de Factura",
					JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Factura C", "Factura de Remito"}, "Factura C");
			if(seleccion!=null){
				String sel=seleccion.toString();
				if(sel.compareTo("Factura de Remito")!=0){ */
					mostrar=true;
					Long nroFacturaObt=new Long(0);
					nroFacturaObt=dist.getNroFactC();
					guiFacturarCte = new GUIFacturarCliente(tipo,guiPadre); 
					guiFacturarCte.nroFactura=nroFacturaObt;
					guiFacturarCte.setActionListeners(this);
			/*	}else{
					buscarRemito();
				}
			}*/
		}
	}
	
	public void show() {
		if(dist!=null && mostrar){
			remito=null;
			guiFacturarCte.actualizarNroFactura();
			Utils.show(guiFacturarCte);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if ((((Component)e.getSource()).getName().compareTo("ConfirmarFact")) == 0) {
			String remitoNro = guiFacturarCte.getJTFRemitoNro().getText();
			boolean correcto=true;
			if(remitoNro!=null && remitoNro.compareTo("")!=0){
				try {
					if(controlFactCliente.existeFacturaDeRemito(remitoNro)){
						correcto=false;
						Utils.advertenciaUsr(guiFacturarCte,"El remito seleccionado ya ha sido facturado.");
					}
				} catch (Exception ex) {
					Utils.manejoErrores(ex,this.getClass(),"Error ConfirmarFacturaDeRemito");
				}
			}
			if(correcto){
				try {
					java.util.Date fu=guiFacturarCte.getJDateChooserFecha().getDate();
					String iva = guiFacturarCte.getJCTipoIva().getText();
					String condVta = guiFacturarCte.getJCCondVta().getSelectedItem().toString();
					
					String ingrBrutos = guiFacturarCte.getJTFIngrBrutos().getText();
					Date fecha= Utils.crearFecha(fu);
					if (this.productos.size()==0){
						Utils.advertenciaUsr(guiFacturarCte,"Debe agregar algún Producto para poder generar la Factura.");
					} else if(guiFacturarCte.getJTFNombreC().getText().length()==0 ){
						Utils.advertenciaUsr(guiFacturarCte,"Debe seleccionar el Cliente.");
					} else if(iva.length()>=0 && iva.compareTo("Seleccione...")==0){
						Utils.advertenciaUsr(guiFacturarCte,"Debe seleccionar el Tipo de Iva.");                	 
					} else{
						FacturaClienteDTO fc= new FacturaClienteDTO();
						fc.setAnulada(false);
						fc.setCliente(cliente);
						fc.setFechaImpresion(fecha);
						fc.setImporteTotal(Utils.redondear(importeTotal,2));
						fc.setNroFactura(guiFacturarCte.nroFactura);
						fc.setTipoFactura("FacturaCliente-"+tipo);
						fc.setCondVenta(condVta);
						fc.setIva(iva);
						fc.setRemitoNro(remitoNro);
						fc.setIngrBrutos(ingrBrutos);
						Vector items= new Vector();
						for(int k=0;k<productos.size();k++){
							ItemFacturaDTO itNew = new ItemFacturaDTO();
							itNew.setFactura(fc);
							double cantpr=Double.parseDouble((String)cantProd.elementAt(k));
							itNew.setCantidad(cantpr);
							String pr=(String)productos.elementAt(k);
							itNew.setProducto(pr);
							itNew.setPrUnit(Double.parseDouble((String)precioUnit.elementAt(k)));
							double prTotIt=Double.parseDouble((String)precioTotalIt.elementAt(k));
							itNew.setPrTotal(prTotIt);
							items.add(itNew);
						}
						fc.setItems(items);
						int nroMC =controlMC.obtenerNroMovCaja();
						this.controlFactCliente.agregarFacturaClienteTotal(fc,tipo,null,nroMC);
						this.guiFacturarCte.dispose();
					/*	new GUIReport(guiFacturarCte,NRO_REPORTE.facturarCliente,productos,cantProd,precioUnit,precioTotalIt,"",fecha,
								dist, cliente,iva,condVta,"",remitoNro,ingrBrutos,tipo,fc.getImporteTotal());*/
						new GUIReport(guiFacturarCte,NRO_REPORTE.facturarCliente,productos,cantProd,precioUnit,precioTotalIt,Utils.nroFact(guiFacturarCte.nroFactura),fecha,
								dist, cliente,iva,"Cond. de venta: "+condVta,ingrBrutos,tipo,fc.getImporteTotal());
					
					}
				} catch(Exception ex) {
					Utils.manejoErrores(ex,this.getClass(),"Error ConfirmarFactura");
				}
			}
		}else if ((((Component)e.getSource()).getName().compareTo("BuscarC")) == 0) {
			buscarCliente();
		}else if ((((Component)e.getSource()).getName().compareTo("AgregarProd")) == 0) {
			try{
				String cant = guiFacturarCte.getJTFCantidad().getText();
				String desc = guiFacturarCte.getJTFProducto().getText();
				String importe = guiFacturarCte.getJTFImporteUnit().getText();
				if(desc.length()==0){
					Utils.advertenciaUsr(guiFacturarCte,"Debe ingresar una descripcion.");
				}else if(importe.length()==0){
					Utils.advertenciaUsr(guiFacturarCte,"Debe ingresar un importe unit.");	
				}else if(importe.length()!=0 && !Utils.esDouble(importe)){
					Utils.advertenciaUsr(guiFacturarCte,"El importe ingresado no es un número correcto.");	                			
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
					guiFacturarCte.getJTFCantidad().setText("1");
					guiFacturarCte.getJTFProducto().setText("");
					guiFacturarCte.getJTFImporteUnit().setText("");
					cargarDatos();
				}
			}catch(Exception ex){
				Utils.manejoErrores(ex,this.getClass(),"Error AgregarProducto");
			}
		}else if ((((Component)e.getSource()).getName().compareTo("EliminarP")) == 0) {
			if (guiFacturarCte.tabla.getSelectedRow() == -1){
				Utils.advertenciaUsr(guiFacturarCte,"Para poder Eliminar un Producto de la Factura debe seleccionarlo previamente.");
			} else {
				int posProd = guiFacturarCte.tabla.getSelectedRow();
				int prueba=Utils.aceptarCancelarAccion(guiFacturarCte,"Esta seguro que desea eliminar el Item Número "+ (posProd+1)+" de la Factura.");
				if (prueba == 0){
					productos.removeElementAt(posProd);
					cantProd.removeElementAt(posProd);
					precioUnit.removeElementAt(posProd);
					precioTotalIt.removeElementAt(posProd);
					cargarDatos();
				}
			}
		}else if ((((Component)e.getSource()).getName().compareTo("Cancelar")) == 0) {
			guiFacturarCte.dispose();
		}
	}
	
	private void buscarCliente() {
		if (medGestionarCliente== null) {
			medGestionarCliente= new MediadorGestionarCliente(this,guiFacturarCte);
		} else {
			if (!medGestionarCliente.getGUI().isVisible()) {
				medGestionarCliente.getGUI().setVisible(true);
			}
		}
		if (cliente != null){
			this.guiFacturarCte.getJtCuit().setText(cliente.getCuit());
			this.guiFacturarCte.getJTFNombreC().setText(cliente.getNombre());
			this.guiFacturarCte.getJTFIngrBrutos().setText(cliente.getIngBrutosCl());
			this.guiFacturarCte.getJCTipoIva().setText(cliente.getIvaCl());
			this.guiFacturarCte.getJTFProducto().setEnabled(true);
			this.guiFacturarCte.getJTFImporteUnit().setEnabled(true);
			this.guiFacturarCte.getJTFCantidad().setEnabled(true);
			this.guiFacturarCte.getJBAgregarProd().setEnabled(true);
			this.cargarCliente(cliente);
		}
	}
	
	private void cargarCliente(ClienteDTO c) {
		this.cliente = c;
	}
	
	public void actualizarCliente() {
		this.guiFacturarCte.getJtCuit().setText(cliente.getCuit());
		this.guiFacturarCte.getJTFNombreC().setText(cliente.getNombre());
		this.guiFacturarCte.getJTFIngrBrutos().setText(cliente.getIngBrutosCl());
		this.guiFacturarCte.getJCTipoIva().setText(cliente.getIvaCl());
		this.guiFacturarCte.getJTFProducto().setEnabled(true);
		this.guiFacturarCte.getJTFImporteUnit().setEnabled(true);
		this.guiFacturarCte.getJTFCantidad().setEnabled(true);
		this.guiFacturarCte.getJBAgregarProd().setEnabled(true);
	}
	
	public void cargarDatos() {
		importeTotal=0;
		try {
			guiFacturarCte.datos = new Object[productos.size()][guiFacturarCte.titulos.length];
			int i = 0;
			for (int j = 0; j < productos.size(); j++) {
				String pr= (String) productos.elementAt(j);
				String prUnit= (String)precioUnit.elementAt(j); 
				String cantidad=(String)cantProd.elementAt(j);
				String precioTotal=(String)precioTotalIt.elementAt(j);
				double prTotal=Double.parseDouble(precioTotal);
				importeTotal = Utils.redondear(importeTotal+prTotal,2);
				Object[] temp = {cantidad, pr, prUnit, precioTotal};
				guiFacturarCte.datos[i] = temp;
				i++;
			}
			if(productos.size()>0){
				this.guiFacturarCte.getJBEliminarProd().setEnabled(true);
				this.guiFacturarCte.getJBConfirmaFact().setEnabled(true);
				if(productos.size()==20)
					this.guiFacturarCte.getJBAgregarProd().setEnabled(false);
				else
					this.guiFacturarCte.getJBAgregarProd().setEnabled(true);
			}else{
				this.guiFacturarCte.getJBEliminarProd().setEnabled(false);	
				this.guiFacturarCte.getJBConfirmaFact().setEnabled(false);
			}	
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error CargarDatos");
		}
		guiFacturarCte.tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guiFacturarCte.actualizarTabla();
		guiFacturarCte.getJTFITotal().setText(Utils.ordenarDosDecimales(importeTotal));
	}
	
	public void valueChanged(ListSelectionEvent arg0) {
	}
	
	public void keyTyped(KeyEvent arg0) {
	}
	
	public void keyPressed(KeyEvent arg0) {
	}
	
	
	//Metodos p remitos
	public void actualizarRemito(FacturaClienteDTO rem) {
		remito=rem;
		tipo="C";
		this.cliente = remito.getCliente();
		Long nroFacturaObt=new Long(0);
		nroFacturaObt=dist.getNroFactC();
		guiFacturarCte = new GUIFacturarCliente(tipo,guiPadreCtrl);
		this.guiFacturarCte.getJtCuit().setText(remito.getCliente().getCuit());
		this.guiFacturarCte.getJTFNombreC().setText(remito.getCliente().getNombre());
		this.guiFacturarCte.getJTFIngrBrutos().setText(remito.getCliente().getIngBrutosCl());
		this.guiFacturarCte.getJCTipoIva().setText(remito.getCliente().getIvaCl());
		this.guiFacturarCte.getJTFRemitoNro().setText(String.valueOf(remito.getNroFactura()));
		Vector items=remito.getItems();
		for(Iterator j=items.iterator();j.hasNext();){
			ItemFacturaDTO pr= (ItemFacturaDTO) j.next();
			productos.add(pr.getProducto());
			cantProd.add(String.valueOf(pr.getCantidad()));
			precioUnit.add(Utils.ordenarDosDecimales(pr.getPrUnit()));
			precioTotalIt.add(Utils.ordenarDosDecimales(pr.getPrTotal()));
		}	
		cargarDatos();
		guiFacturarCte.nroFactura=nroFacturaObt;
		guiFacturarCte.setActionListeners(this);
		mostrar=true;
		
		this.showDeRemito();    	
	}
	
	public void showDeRemito() {
		if(dist!=null && mostrar){
			guiFacturarCte.actualizarNroFactura();
			guiFacturarCte.getJBEliminarProd().setEnabled(false);
			guiFacturarCte.getJBBuscarC().setEnabled(false);
		}
	}
	
	
	private void buscarRemito() throws Exception {
		java.util.Date hoy= new java.util.Date();
		int mesL=Utils.getMes(hoy);
		int anioL=Utils.getAnio(hoy);
		String cliente=JOptionPane.showInputDialog(guiPadreCtrl,"Ingrese dato del cliente");
		new MediadorListarRemitosCliente(this,mesL,anioL,cliente,guiPadreCtrl);
		if (remito != null){
			this.cargarRemito(remito);
		}
	}
	
	private void cargarRemito(FacturaClienteDTO c) {
		this.remito = c;
	}
	//Fin metodos p facts de remitos
}
