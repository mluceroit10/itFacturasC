package cliente.ListarFacturasCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cliente.ClienteConection;
import cliente.GestionarMovimientoCaja.MediadorAltaMovimientoCaja;
import cliente.GestionarNotaDebito.MediadorAltaNotaDebitoCliente;
import cliente.Principal.GUIReport;

import common.Utils;
import common.dto.ClienteDTO;
import common.dto.ComercioDTO;
import common.dto.FacturaClienteDTO;
import common.dto.FacturaDTO;
import common.dto.ItemFacturaDTO;
import common.dto.MovimientoCajaDTO;
import common.dto.NotaDebitoDTO;
import common.interfaces.IControlCliente;
import common.interfaces.IControlComercio;
import common.interfaces.IControlFacturaCliente;

import constantes.NRO_REPORTE;

public class MediadorListarFacturasCliente implements ActionListener, KeyListener, ListSelectionListener {
	
	private GUIListarFacturasCliente guiTodasFactCte = null;
	protected IControlFacturaCliente controlFactCte;
	private FacturaClienteDTO miFactCte;
	private IControlComercio controlComercio;
	private IControlCliente controlCliente;
	private boolean flag=false;
	private MediadorAltaMovimientoCaja medAltaMovCaja;
	private MediadorAltaNotaDebitoCliente medAltaNotaDebito;
	private FacturaClienteDTO fact;
	private int mesLI;
	private int anioLI;
	
	public MediadorListarFacturasCliente(int mes, int anio,JFrame guiPadre) {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlCliente = clienteConexion.getIControlCliente();
		this.controlComercio = clienteConexion.getIControlComercio();
		this.controlFactCte = clienteConexion.getIControlFCliente();
		mesLI=mes;
		anioLI=anio;
		this.guiTodasFactCte = new GUIListarFacturasCliente(mesLI,anioLI,guiPadre);
		this.guiTodasFactCte.setActionListeners(this);
		actualizarDatosTabla();
		this.guiTodasFactCte.setListSelectionListener(this);
		this.flag=true;
		Utils.show(guiTodasFactCte);
	}
	
	public MediadorListarFacturasCliente(MediadorAltaMovimientoCaja medAMC,int mes, int anio,String cliente,JDialog guiPadre) {
		this.medAltaMovCaja = medAMC;
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlCliente = clienteConexion.getIControlCliente();
		this.controlComercio = clienteConexion.getIControlComercio();
		this.controlFactCte = clienteConexion.getIControlFCliente();
		mesLI=mes;
		anioLI=anio;
		this.guiTodasFactCte = new GUIListarFacturasCliente(mesLI,anioLI,guiPadre);
		this.guiTodasFactCte.setActionListeners(this);
		guiTodasFactCte.getJTFCliente().setText(cliente);
		actualizarDatosTabla();
		this.guiTodasFactCte.setListSelectionListener(this);
		this.guiTodasFactCte.setKeyListener(this);
		Utils.show(guiTodasFactCte);
	}
	
	public MediadorListarFacturasCliente(MediadorAltaNotaDebitoCliente medANC,int mes, int anio,JDialog guiPadre) {
		this.medAltaNotaDebito = medANC;
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlCliente = clienteConexion.getIControlCliente();
		this.controlComercio = clienteConexion.getIControlComercio();
		this.controlFactCte = clienteConexion.getIControlFCliente();
		mesLI=mes;
		anioLI=anio;
		this.guiTodasFactCte = new GUIListarFacturasCliente(mesLI,anioLI,guiPadre);
		this.guiTodasFactCte.setActionListeners(this);
		actualizarDatosTabla();
		this.guiTodasFactCte.setListSelectionListener(this);
		Utils.show(guiTodasFactCte);
	}
	
	public boolean cargarFilaSeleccionada() {
		boolean result=false;
		try{
			if (guiTodasFactCte.jtDatos.getSelectedRow() == -1) {
				Utils.advertenciaUsr(guiTodasFactCte,"Debe Seleccionar una Factura.");
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
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == guiTodasFactCte.getJBImprimir()) {
			try {
				if (guiTodasFactCte.jtDatos.getSelectedRow() == -1){
					Utils.advertenciaUsr(guiTodasFactCte,"Para poder Imprimir una Factura debe ser previamente seleccionada.");
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
					ComercioDTO dist=controlComercio.obtenerComercio();
					ClienteDTO cte=controlCliente.buscarCliente(miFactCte.getCliente().getId());
					new GUIReport(guiTodasFactCte,NRO_REPORTE.detallarCompraFactCliente,productos,cantProd,prUnit,prTotal,miFactCte, Utils.nroFact(miFactCte.getNroFactura()),miFactCte.getFechaImpresion(),
							dist, cte,miFactCte.getImporteTotal());  
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
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
		}else if (source == guiTodasFactCte.getJBImprimirTabla()){	
			imprimirLibroFacturas();
			
		}else if (source == guiTodasFactCte.getJBSalir()){
			if(flag){
				this.guiTodasFactCte.dispose();
			}
			else{
				if (cargarFilaSeleccionada()) {
					if (medAltaMovCaja != null) {
						medAltaMovCaja.factura = fact;
						medAltaMovCaja.tipoFact = fact.getTipoFactura();
						medAltaMovCaja.actualizarFactura();
						this.guiTodasFactCte.dispose();
					}
					if (medAltaNotaDebito!= null) {
						medAltaNotaDebito.actualizarFacturaCliente(fact);
						this.guiTodasFactCte.dispose();
					}
				}
			}	
		}else { 
			guiTodasFactCte.dispose();
		}
	}
	
	private void imprimirLibroFacturas() {
		try{
			String titulo="Libro Iva del mes: "+mesLI+" año: "+anioLI;
			ComercioDTO dist=controlComercio.obtenerComercio();
			Vector facts = new Vector();
    		facts = controlFactCte.obtenerFacturasyNotasDebito(mesLI, anioLI); 
    		//.obtenerFacturasyNotasCreditoLibroIva(mesLI,anioLI);
    		Object[][] datos = new Object[facts.size()][9];
    		int i = 0;
    		double totalTotal=0;
    		if(facts!=null){
    			for (int j = 0; j < facts.size(); j++) {
    				FacturaDTO fact = (FacturaDTO) facts.elementAt(j);
    				if(fact.getTipoFactura().compareTo("FacturaCliente-C")==0){
    					FacturaClienteDTO fc=(FacturaClienteDTO) fact;
    					String cte=fc.getCliente().getNombre();
        				String categ=fc.getIva();
        				String cuit=fc.getCliente().getCuit();
        				String tipo="C";
        				String nroFact=Utils.nroFact(fc.getNroFactura());
        				String pv = nroFact.substring(0,4);
        				String nro=  nroFact.substring(5,13);
        				String total="";
        				if(fc.isAnulada()){
        					cte="ANULADA";
        					categ="";
        					cuit="00-00000000-0";
        					total="0";
        				}else{
        					total=Utils.ordenarDosDecimales(fc.getImporteTotal());
            				totalTotal = Utils.redondear(fc.getImporteTotal()+totalTotal,2);
        				}
        				Object[] temp = {Utils.getStrUtilDate(fc.getFechaImpresion()),"Fact",tipo,pv,nro,cte,categ,cuit,total};		
        				datos[j] = temp;
    				}else if(fact.getTipoFactura().compareTo("NotaDebito")==0){
    					NotaDebitoDTO nc=(NotaDebitoDTO) fact;
    					String cte=nc.getCliente().getNombre();
        				String categ=nc.getCliente().getIvaCl();
        				String cuit=nc.getCliente().getCuit();
        				String nroFact=Utils.nroFact(nc.getNroFactura());
        				String pv = nroFact.substring(0,4);
        				String nro=  nroFact.substring(5,13);
        				String total="";
        				if(nc.isAnulada()){
        					cte="ANULADA";
        					categ="";
        					cuit="00-00000000-0";
            				total="0";
        				}else{
        					total=Utils.ordenarDosDecimales(nc.getImporteTotal());
            				totalTotal = Utils.redondear(nc.getImporteTotal()+totalTotal,2);
        				}
    					Object[] temp = {Utils.getStrUtilDate(nc.getFecha()),"N Deb","",pv,nro,cte,categ,cuit,total};
    					datos[j] = temp;
    				}
    			}
    		}
			
			new GUIReport(guiTodasFactCte,NRO_REPORTE.mostrarLibroFacturas,dist,titulo,mesLI+"/"+anioLI,datos,Utils.ordenarDosDecimales(totalTotal));

		} catch(Exception ex) {
			Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
		}
	}

	public void actualizarDatosTabla() {
		try {
			Vector facturas = this.controlFactCte.obtenerFacturaClientesPeriodoFiltros(false,"FacturaCliente-C",mesLI,anioLI, guiTodasFactCte.getJTFFecha().getText(),guiTodasFactCte.getJTFNro().getText(),guiTodasFactCte.getJTFCliente().getText());
			guiTodasFactCte.getJTFPeriodo().setText(mesLI+" - "+anioLI);
			guiTodasFactCte.datos = new Object[facturas.size()][guiTodasFactCte.titulos.length];
			for (int j = 0; j < facturas.size(); j++) {
				FacturaClienteDTO r=(FacturaClienteDTO)facturas.elementAt(j);
				String compr = "";
				String tipoF="";
				if(r.getTipoFactura().compareTo("FacturaCliente-C")==0) tipoF="C";
				Vector movs = r.getComprobantesPago();
				for(Iterator it= movs.iterator(); it.hasNext();){
					MovimientoCajaDTO mc = (MovimientoCajaDTO) it.next();
					compr +=mc.getCodigo()+"-";
				}
				if(compr.length()>1)
					compr=compr.substring(0,compr.length()-1);
				String remNro="";
				if(r.getRemitoNro()!=null && r.getRemitoNro().compareTo("")!=0)
					remNro=" - "+Utils.nroFact(new Long(r.getRemitoNro()));
				String anulada="";
				if(r.isAnulada())
					anulada="SI";
				Object[] temp = {r.getId(),common.Utils.getStrUtilDate(r.getFechaImpresion()),tipoF,Utils.nroFact(r.getNroFactura())+remNro,r.getCliente().getNombre(),String.valueOf(r.getImporteTotal()),String.valueOf(r.getImporteAbonado()),common.Utils.getStrUtilDate(r.getFechaPago()),compr,anulada};
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
				Utils.advertenciaUsr(guiTodasFactCte,"Para poder anular una Factura debe seleccionarla previamente.");
			} else {
				Long id = (Long)guiTodasFactCte.datos[guiTodasFactCte.jtDatos.getSelectedRow()][0];
				String nroFactura = (String)guiTodasFactCte.datos[guiTodasFactCte.jtDatos.getSelectedRow()][3];
				if (controlFactCte.facturaAsociada(id)) {
					Utils.advertenciaUsr(guiTodasFactCte,"La Factura no puede ser borrada porque registra pagos.");
				}else if(nroFactura.indexOf("-",5)!= -1){
					Utils.advertenciaUsr(guiTodasFactCte,"La Factura no puede ser borrada por ser una Factura de Remito.");
				}else{
					int prueba = Utils.aceptarCancelarAccion(guiTodasFactCte,"Esta seguro que desea Anular la Factura Nro: \n"+ nroFactura);
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
	
	public GUIListarFacturasCliente getGUI() {
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




