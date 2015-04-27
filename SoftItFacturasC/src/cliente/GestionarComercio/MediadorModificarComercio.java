package cliente.GestionarComercio;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JDialog;
import javax.swing.JFrame;

import cliente.ClienteConection;
import cliente.GestionarLocalidad.MediadorGestionarLocalidad;
import cliente.Principal.GUIReport;

import common.Utils;
import common.dto.ComercioDTO;
import common.dto.LocalidadDTO;
import common.interfaces.IControlComercio;
import constantes.NRO_REPORTE;

public class MediadorModificarComercio implements ActionListener {
	
	public LocalidadDTO localidad;
	private GUIAltaModComercio guiComercio = null;
	public IControlComercio controlComercio;
	private MediadorGestionarLocalidad mgLoc = null;
	public ComercioDTO dist=null;
	
	public MediadorModificarComercio(JFrame guiPadre) {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
			this.controlComercio = clienteConexion.getIControlComercio();
			dist = controlComercio.obtenerComercio();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		if(dist!=null){
			localidad = dist.getLocalidad();
			guiComercio = new GUIAltaModComercio(dist,guiPadre);
		}else{	
			guiComercio = new GUIAltaModComercio(guiPadre);
		}	
		guiComercio.setActionListeners(this);
		Utils.show(guiComercio);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == guiComercio.getAceptar()) {
			String nombre = Utils.mayuscula(guiComercio.getNombre().getText());
			String tel = guiComercio.getTelefono().getText();
			String cuit = guiComercio.getCuit().getText();
			String ingBru = guiComercio.getIngrBrutos().getText();
			String direccion = Utils.mayuscula(guiComercio.getDireccion().getText());
			String nLoc=guiComercio.getLocalidad().getText();
			java.util.Date fu=guiComercio.getJDateChooserFecha().getDate();
			String nroFactC= guiComercio.getProximoNroFactC().getText();
			String nroRecibo= guiComercio.getProximoNroRecibo().getText();
			String nroNDebito= guiComercio.getProximoNroNDebito().getText();
			
			Date fecha= Utils.crearFecha(fu);
			if (nombre.length()==0 || cuit.length()==0 || direccion.length()==0 ||nLoc.length()==0){
				Utils.advertenciaUsr(guiComercio,"Alguno de los campos obligatorios esta vacio.");
			} else if (nroFactC.length()==0 || nroRecibo.length()==0 || nroNDebito.length()==0){
				Utils.advertenciaUsr(guiComercio,"Presione el boton 'Modificar Números' para ingresar el siguiente numero a utilizar de cada comprobante.");
			} else if (nroFactC.compareTo("0")==0 || nroRecibo.compareTo("0")==0 || nroNDebito.compareTo("0")==0){
				Utils.advertenciaUsr(guiComercio,"No se acepta el cero como primer número de comprobante.");
			} else {
				if(Utils.verificarCuit(cuit)){
					Utils.advertenciaUsr(guiComercio,"El Cuit ingresado no es correcto.");
				}else{
					try{
						ComercioDTO dNew = new ComercioDTO();
						dNew.setNombre(nombre);
						dNew.setCuit(cuit);
						dNew.setTelefono(tel);
						dNew.setDireccion(direccion);
						dNew.setInicioAct(fecha);
						dNew.setCategoria(ingBru);
						dNew.setLocalidad(localidad);
						dNew.setNroFactC(new Long(nroFactC));
						dNew.setNroRemito(new Long(0));
						dNew.setNroRecibo(new Long(nroRecibo));
						dNew.setNroNotaDebito(new Long(nroNDebito));
						
						if (dist!=null){
							controlComercio.modificarComercio(dist.getId(),dNew);
						}else{
							controlComercio.agregarComercio(dNew);
						}
						guiComercio.dispose();
					} catch(Exception ex) {
						Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
					}
				}
			}
		} else if (source == guiComercio.getJButtonLocalidad()) {
			buscarLocalidad();
		} else if (source == guiComercio.getJButtonImprimirTarjeta()) {
			String nombre = Utils.mayuscula(guiComercio.getNombre().getText());
			String tel = guiComercio.getTelefono().getText();
			String cuit = guiComercio.getCuit().getText();
			String ingBru = guiComercio.getIngrBrutos().getText();
			String direccion = Utils.mayuscula(guiComercio.getDireccion().getText());
			String nLoc=guiComercio.getLocalidad().getText();
			try {
				new GUIReport(guiComercio,NRO_REPORTE.tarjetaComercio,nombre, cuit,ingBru, tel, direccion, nLoc);
			} catch (Exception ex) {
				Utils.manejoErrores(ex,this.getClass(),"imprimiendo tarjeta de la distribuidora");
			}    
		} else if (source == guiComercio.getJButtonModificarNrosFactura()) {
			guiComercio.getProximoNroFactC().setEnabled(true);
			guiComercio.getProximoNroRecibo().setEnabled(true);
			guiComercio.getProximoNroNDebito().setEnabled(true); 
		} else if (source == guiComercio.getCancelar()) {    
			((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
		}
	}
	
	private void buscarLocalidad() {
		if (mgLoc == null) {
			mgLoc = new MediadorGestionarLocalidad(this,guiComercio);
		} else {
			if (!mgLoc.getGUI().isVisible()) {
				mgLoc.getGUI().setVisible(true);
			}
		}
		if (localidad != null){
			this.guiComercio.getLocalidad().setText(localidad.getNombre());
			this.cargarLocalidad(localidad);           
		}
	}
	
	private void cargarLocalidad(LocalidadDTO loc) {
		this.localidad = loc;
	}
	
	public void actualizarLocalidad() {
		guiComercio.setLocalidad(localidad.getNombre());
	}
	
}
