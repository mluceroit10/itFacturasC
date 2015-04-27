package cliente.GestionarNotaDebito;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cliente.ClienteConection;
import cliente.Principal.GUIReport;

import common.Utils;
import common.dto.ClienteDTO;
import common.dto.ComercioDTO;
import common.dto.ItemFacturaDTO;
import common.dto.NotaDebitoDTO;
import common.interfaces.IControlCliente;
import common.interfaces.IControlComercio;
import common.interfaces.IControlNotaDebito;

import constantes.NRO_REPORTE;

public class MediadorGestionarNotaDebito implements ActionListener, KeyListener, ListSelectionListener {
	
	private GUIGestionarNotaDebito guiNotaDebito = null;
	protected IControlNotaDebito controlNotaDebito;
	protected IControlComercio controlDist;
	protected IControlCliente controlCte;
	private NotaDebitoDTO notaDebito;
	private int mesLI;
	private int anioLI;
	
	public MediadorGestionarNotaDebito(int mes, int anio,JFrame guiPadre) {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		mesLI=mes;
		anioLI=anio;
		this.controlNotaDebito = clienteConexion.getIControlNotaDebito();
		this.controlDist = clienteConexion.getIControlComercio();
		this.controlCte = clienteConexion.getIControlCliente();
		
		this.guiNotaDebito = new GUIGestionarNotaDebito(mesLI,anioLI,guiPadre);
		this.guiNotaDebito.setActionListeners(this);
		actualizarDatosTabla();
		this.guiNotaDebito.setListSelectionListener(this);
		this.guiNotaDebito.setKeyListener(this);
		Utils.show(guiNotaDebito);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == guiNotaDebito.getJBBorrar()) {
			anular();
		} else if (source == guiNotaDebito.getJBImprimir()) {
			try {
				if (guiNotaDebito.jtDatos.getSelectedRow() == -1){
					Utils.advertenciaUsr(guiNotaDebito,"Para poder Imprimir una Nota de Débito debe ser previamente seleccionada.");
				}else{
					Long id = (Long)guiNotaDebito.datos[guiNotaDebito.jtDatos.getSelectedRow()][0];
					notaDebito = this.controlNotaDebito.buscarNotaDebito(id);
					Vector productos=new Vector();
					Vector cantProd=new Vector();
					Vector prUnit=new Vector();
					Vector prTotal=new Vector();
					Vector items=notaDebito.getItems();
					for(Iterator j=items.iterator();j.hasNext();){
						ItemFacturaDTO pr= (ItemFacturaDTO) j.next();
						productos.add(pr.getProducto());
						cantProd.add(String.valueOf(pr.getCantidad()));
						prUnit.add(Utils.ordenarDosDecimales(pr.getPrUnit()));
						prTotal.add(Utils.ordenarDosDecimales(pr.getPrTotal()));
					}	
					ComercioDTO dist=controlDist.obtenerComercio();
					if(notaDebito.getCliente()!=null){
						ClienteDTO cte=controlCte.buscarCliente(notaDebito.getCliente().getId());
						new GUIReport(guiNotaDebito,NRO_REPORTE.DetalleNotaDebitoCte,productos,cantProd,prUnit,prTotal,Utils.nroFact(notaDebito.getNroFactura()),notaDebito.getFecha(),
								dist, cte,cte.getIvaCl(),"",notaDebito.getObservaciones(),"",cte.getIngBrutosCl(),"",notaDebito.getImporteTotal());
					}
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
				Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
			}  
		}else if (source == guiNotaDebito.getJBCambiarPeriodo()){
			String anioB = guiNotaDebito.getJTFAnio().getText();
			if(anioB.length()==0){
				Utils.advertenciaUsr(guiNotaDebito,"Por favor ingrese el Año.");
			}else if(anioB.length()!=4){
				Utils.advertenciaUsr(guiNotaDebito,"El año debe ser un número de 4 dígitos.");
			}else{
				anioLI= Integer.parseInt(anioB);
				mesLI = guiNotaDebito.getJCBMes().getSelectedIndex()+1; //para que el numero del indice de con el mes sumo 1
				actualizarDatosTabla();
			} 		
		} else if (source == guiNotaDebito.getJBAceptar()) {
			guiNotaDebito.dispose();
		}	
	}
	
	
	public boolean cargarFilaSeleccionada() {
		boolean result=false;
		try{
			if (guiNotaDebito.jtDatos.getSelectedRow() == -1) {
				Utils.advertenciaUsr(guiNotaDebito,"Debe Seleccionar una Nota de Débito.");
				result = false;
			}else{
				Long id = (Long)guiNotaDebito.datos[guiNotaDebito.jtDatos.getSelectedRow()][0];
				notaDebito = this.controlNotaDebito.buscarNotaDebito(id);
				result = true;
			}
		} catch (Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error CargarFilaSeleccionada");
		}
		return result;
	} 
	
	public void actualizarDatosTabla() {
		try {
			Vector movsCaja = this.controlNotaDebito.obtenerNotasDeDebitoFiltros(mesLI,anioLI,guiNotaDebito.getJTFBuscadorFecha().getText(),guiNotaDebito.getJTFBuscadorCliente().getText());
			guiNotaDebito.getJTFPeriodo().setText(mesLI+" - "+anioLI);
			guiNotaDebito.datos = new Object[movsCaja.size()][guiNotaDebito.titulos.length];
			for (int j = 0; j < movsCaja.size(); j++) {
				NotaDebitoDTO nc=(NotaDebitoDTO)movsCaja.elementAt(j);
				String anulada="";
				if(nc.isAnulada())
					anulada="SI";
				String aNombre="";
				if(nc.getCliente()!=null)
					aNombre=nc.getCliente().getNombre();
				Object[] temp = {nc.getId(),Utils.nroFact(nc.getNroFactura()),Utils.getStrUtilDate(nc.getFecha()),aNombre,Utils.ordenarDosDecimales(nc.getImporteTotal()),nc.getTipoFacturaNC()+" - "+Utils.nroFact(nc.getNroFacturaNC()),anulada};
				guiNotaDebito.datos[j] = temp;	
			}
		} catch(Exception ex) {
			Utils.manejoErrores(ex,this.getClass(),"Error ActualizarTablaFechaMov");
		}
		guiNotaDebito.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guiNotaDebito.actualizarTabla();
	}
	
	private void anular() {
		try{
			if ( this.controlNotaDebito.obtenerNotasDeDebito(mesLI,anioLI).isEmpty()){
				Utils.advertenciaUsr(guiNotaDebito,"No hay Notas de Débito guardadas.");
			} else {
				if (guiNotaDebito.jtDatos.getSelectedRow() == -1){
					Utils.advertenciaUsr(guiNotaDebito,"Para poder Anular una Nota de Débito debe seleccionarla previamente.");
				} else {
					Long id = (Long)guiNotaDebito.datos[guiNotaDebito.jtDatos.getSelectedRow()][0];
					String cod = (String)guiNotaDebito.datos[guiNotaDebito.jtDatos.getSelectedRow()][1];
					int prueba = Utils.aceptarCancelarAccion(guiNotaDebito,"Esta seguro que desea Anular la Nota de Débito \n"+ cod);
					if (prueba == 0){
						this.controlNotaDebito.anularNotaDebito(id);
					}    
					actualizarDatosTabla();
				}
			}
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Anular");
		}
	}
	
	public GUIGestionarNotaDebito getGUI() {
		return guiNotaDebito;
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
			actualizarDatosTabla();
	}
	
	public void keyTyped(KeyEvent e) { }
	
	public void keyPressed(KeyEvent e) { }
	
	public void valueChanged(ListSelectionEvent arg0) { }
}




