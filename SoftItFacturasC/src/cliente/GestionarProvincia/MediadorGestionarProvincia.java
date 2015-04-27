package cliente.GestionarProvincia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cliente.ClienteConection;
import cliente.GestionarLocalidad.MediadorAltaLocalidad;
import cliente.GestionarLocalidad.MediadorModificarLocalidad;

import common.Utils;
import common.dto.ProvinciaDTO;
import common.interfaces.IControlProvincia;

public class MediadorGestionarProvincia implements ActionListener, KeyListener, ListSelectionListener {
	
	private GUIGestionarProvincia guiProvincia = null;
	protected IControlProvincia controlProvincia;
	public  ProvinciaDTO miProv;
	private MediadorAltaLocalidad medAltaLocalidad = null;
	private MediadorModificarLocalidad medModLocalidad = null;
	private boolean flag=false;
	
	public MediadorGestionarProvincia(JFrame guiPadre) {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlProvincia = clienteConexion.getIControlProvincia();
		this.guiProvincia = new GUIGestionarProvincia(guiPadre);
		this.guiProvincia.setActionListeners(this);
		cargarDatos();
		this.guiProvincia.setListSelectionListener(this);
		this.guiProvincia.setKeyListener(this);
		this.flag=true;
		Utils.show(guiProvincia);
	}
	
	public MediadorGestionarProvincia(MediadorAltaLocalidad medAltaLoc,JDialog guiPadre) {
		medAltaLocalidad = medAltaLoc;
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlProvincia = clienteConexion.getIControlProvincia();
		this.guiProvincia = new GUIGestionarProvincia(guiPadre);
		this.guiProvincia.setActionListeners(this);
		cargarDatos();
		this.guiProvincia.setListSelectionListener(this);
		this.guiProvincia.setKeyListener(this);
		Utils.show(guiProvincia);
	}
	
	public MediadorGestionarProvincia(MediadorModificarLocalidad medModLoc,JDialog guiPadre) {
		medModLocalidad = medModLoc;
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlProvincia = clienteConexion.getIControlProvincia();
		this.guiProvincia = new GUIGestionarProvincia(guiPadre);
		this.guiProvincia.setActionListeners(this);
		cargarDatos();
		this.guiProvincia.setListSelectionListener(this);
		this.guiProvincia.setKeyListener(this);
		Utils.show(guiProvincia);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == guiProvincia.getJBCargar()) {
			try {
				new MediadorAltaProvincia(this,guiProvincia);
			} catch (Exception ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
			}
		} else if (source == guiProvincia.getJBMod()) {
			modificar();
		} else if (source == guiProvincia.getJBCancelar()) {
			guiProvincia.dispose();
		} else if (source == guiProvincia.getJBBorrar()) {
			eliminar();
		} else if (source == guiProvincia.getJBAceptar()) {
			if(flag){
				this.guiProvincia.dispose();
			}
			else{
				if (cargarFilaSeleccionada()) {
					if (medModLocalidad != null) {
						medModLocalidad.prov = miProv;
						medModLocalidad.actualizarProvincia();
						this.guiProvincia.dispose();
					} else if (medAltaLocalidad != null) {
						medAltaLocalidad.prov = miProv;
						medAltaLocalidad.actualizarProvincia();
						this.guiProvincia.dispose();
					} 
				}
			}
		}
	}
	
	public boolean cargarFilaSeleccionada() {
		boolean result=false;
		try{
			if (guiProvincia.jtDatos.getSelectedRow() == -1) {
				Utils.advertenciaUsr(guiProvincia,"Debe seleccionar una Provincia.");
				result = false;
			}else{
				Long id = (Long)guiProvincia.datos[guiProvincia.jtDatos.getSelectedRow()][0];
				miProv = this.controlProvincia.buscarProvincia(id);
				result = true;
			}
		} catch (Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error CargarFilaSeleccionada");
		}
		return result;
	}
	
	public void cargarDatos() {
		try {
			Vector provincias = this.controlProvincia.obtenerProvincias();
			guiProvincia.datos = new Object[provincias.size()][guiProvincia.titulos.length];
			int i = 0;
			for (int j = 0; j < provincias.size(); j++) {
				Object[] temp = {((ProvinciaDTO) provincias.elementAt(j)).getId(),((ProvinciaDTO) provincias.elementAt(j)).getNombre()};
				guiProvincia.datos[i] = temp;
				i++;
			}
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error CargarDatos");
		}
		guiProvincia.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guiProvincia.actualizarTabla();
	}
	
	private void actualizarTabla() {
		try {
			Vector provincias = this.controlProvincia.obtenerProvinciasFiltro(guiProvincia.getJTFBuscador().getText());
			guiProvincia.datos = new Object[provincias.size()][guiProvincia.titulos.length];
			for (int j = 0; j < provincias.size(); j++) {
				Object[] temp = {((ProvinciaDTO) provincias.elementAt(j)).getId(),((ProvinciaDTO) provincias.elementAt(j)).getNombre()};
				guiProvincia.datos[j] = temp;
			}
		} catch(Exception ex) {
			Utils.manejoErrores(ex,this.getClass(),"Error ActualizarTabla");
		}
		guiProvincia.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guiProvincia.actualizarTabla();
	}
	
	private void modificar() {
		try {
			if (this.controlProvincia.obtenerProvincias().isEmpty()){
				Utils.advertenciaUsr(guiProvincia,"No hay Provincias guardadas.");
			} else if (guiProvincia.jtDatos.getSelectedRow() == -1){
				Utils.advertenciaUsr(guiProvincia,"Para poder Modificar una Provincia debe ser previamente seleccionada.");
			} else {
				Long id = (Long)guiProvincia.datos[guiProvincia.jtDatos.getSelectedRow()][0];
				ProvinciaDTO pr = (ProvinciaDTO)this.controlProvincia.buscarProvincia(id);
				new MediadorModificarProvincia(this, pr,guiProvincia);
			}
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Modificar");
		}
	}
	
	private void eliminar() {
		try{
			if ( this.controlProvincia.obtenerProvincias().isEmpty()){
				Utils.advertenciaUsr(guiProvincia,"No hay Provincies guardadas.");
			} else {
				if (guiProvincia.jtDatos.getSelectedRow() == -1){
					Utils.advertenciaUsr(guiProvincia,"Para poder Eliminar una Provincia debe seleccionarla previamente.");
				} else {
					Long id = (Long)guiProvincia.datos[guiProvincia.jtDatos.getSelectedRow()][0];
					String nombre = (String)guiProvincia.datos[guiProvincia.jtDatos.getSelectedRow()][1];
					if (controlProvincia.provinciaAsociada(id)) {
						Utils.advertenciaUsr(guiProvincia,"La Provincia no puede ser borrada.");
					}else{
						int prueba = Utils.aceptarCancelarAccion(guiProvincia,"Esta seguro que desea Eliminar la Provincia \n"+ nombre);
						if (prueba == 0)
							this.controlProvincia.eliminarProvincia(id);
					}     
					cargarDatos();
				}
			}
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Eliminar");
		}
	}
	
	public GUIGestionarProvincia getGUI() {
		return guiProvincia;
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
			actualizarTabla();
	}
	
	public void keyTyped(KeyEvent e) {
	}
	public void keyPressed(KeyEvent e) {
	}
	public void valueChanged(ListSelectionEvent arg0) { }
}




