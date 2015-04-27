package cliente.GestionarCheque;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cliente.ClienteConection;

import common.Utils;
import common.dto.ChequeDTO;
import common.interfaces.IControlCheque;

public class MediadorGestionarCheque implements ActionListener, KeyListener, ListSelectionListener {
	
	private GUIGestionarCheque guiCheque = null;
	protected IControlCheque controlCheque;
	private int mesLI;
	private int anioLI;
	
	public MediadorGestionarCheque(int mes, int anio,JFrame guiPadre) {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlCheque = clienteConexion.getIControlCheque();
		mesLI=mes;
		anioLI=anio;
		this.guiCheque = new GUIGestionarCheque(mesLI,anioLI,guiPadre);
		this.guiCheque.setActionListeners(this);
		actualizarDatosTabla();
		this.guiCheque.setListSelectionListener(this);
		this.guiCheque.setKeyListener(this);
		Utils.show(guiCheque);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == guiCheque.getJBIngresar()) {
			try {
				new MediadorAltaCheque(this,guiCheque);
			} catch (Exception ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
			}
		} else if (source == guiCheque.getJBModificar()) {
			modificar();
		} else if (source == guiCheque.getJBEliminar()) {
			eliminar();
		} else if (source == guiCheque.getJBVerificar()) {  //verificar vencidos
			verificarVencidos();    
		} else if (source == guiCheque.getJBPagado()) {
			cambiarEstado();	
		}else if (source == guiCheque.getJBCambiarPeriodo()){
			String anioB = guiCheque.getJTFAnio().getText();
			if(anioB.length()==0){
				Utils.advertenciaUsr(guiCheque,"Por favor ingrese el Año.");
			}else if(anioB.length()!=4){
				Utils.advertenciaUsr(guiCheque,"El año debe ser un número de 4 dígitos.");
			}else{
				anioLI= Integer.parseInt(anioB);
				mesLI = guiCheque.getJCBMes().getSelectedIndex()+1; //para que el numero del indice de con el mes sumo 1
				actualizarDatosTabla();
			} 
		} else if (source == guiCheque.getJCBEstados()) {	
			actualizarDatosTabla();
		} else if (source == guiCheque.getJBAceptar()) {
			guiCheque.dispose();
		}	
	}
	
	public void actualizarDatosTabla() {
		try {
			Vector movsCaja = this.controlCheque.obtenerChequesFiltro(mesLI,anioLI,guiCheque.getJTFBuscadorCodigo().getText(),guiCheque.getJTFBuscadorFecha().getText(),guiCheque.getJTFBanco().getText(),guiCheque.getJCBEstados().getSelectedItem().toString());
			guiCheque.getJTFPeriodo().setText(mesLI+" - "+anioLI);
			guiCheque.datos = new Object[movsCaja.size()][guiCheque.titulos.length];
			for (int j = 0; j < movsCaja.size(); j++) {
				ChequeDTO ch=(ChequeDTO)movsCaja.elementAt(j);
				String estado = ch.getEstado();
				if(ch.getEstado().compareTo("Remitido")==0) estado +=" "+ch.getAQuienRemite();
				Object[] temp = {ch.getId(),Utils.nroCheque(ch.getNumero()),common.Utils.getStrUtilDate(ch.getFechaEmision()),Utils.ordenarDosDecimales(ch.getImporte()),ch.getBanco(),ch.getLocPlaza(),common.Utils.getStrUtilDate(ch.getFechaVto()),ch.getQuienEntrega(),estado};
				guiCheque.datos[j] = temp;	
			}
		} catch(Exception ex) {
			Utils.manejoErrores(ex,this.getClass(),"Error ActualizarTablaFechaMov");
		}
		guiCheque.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guiCheque.actualizarTabla();
	}
	
	private void modificar() {
		try {
			if (this.controlCheque.obtenerChequesPeriodo(mesLI,anioLI,guiCheque.getJCBEstados().getSelectedItem().toString()).isEmpty()){
				Utils.advertenciaUsr(guiCheque,"No hay Cheques guardados.");
			} else if (guiCheque.jtDatos.getSelectedRow() == -1){
				Utils.advertenciaUsr(guiCheque,"Para poder Modificar un Cheque debe ser previamente seleccionado.");
			} else {
				Long id = (Long)guiCheque.datos[guiCheque.jtDatos.getSelectedRow()][0];
				ChequeDTO ch = (ChequeDTO)this.controlCheque.buscarCheque(id);
				new MediadorModificarCheque(this, ch,guiCheque);
			}
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Modificar");
		}
	}
	
	private void cambiarEstado() {
		try {
			if (this.controlCheque.obtenerChequesPeriodo(mesLI,anioLI,guiCheque.getJCBEstados().getSelectedItem().toString()).isEmpty()){
				Utils.advertenciaUsr(guiCheque,"No hay Cheques guardados.");
			} else if (guiCheque.jtDatos.getSelectedRow() == -1){
				Utils.advertenciaUsr(guiCheque,"Para poder Modificar un Cheque debe ser previamente seleccionado.");
			} else {
				Long id = (Long)guiCheque.datos[guiCheque.jtDatos.getSelectedRow()][0];
				ChequeDTO ch = (ChequeDTO)this.controlCheque.buscarCheque(id);
				new MediadorCambiarEstadoCheque(this, ch,guiCheque);
			}
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Modificar");
		}
	}
	
	private void eliminar() {
		try{
			if ( this.controlCheque.obtenerChequesPeriodo(mesLI,anioLI,guiCheque.getJCBEstados().getSelectedItem().toString()).isEmpty()){
				Utils.advertenciaUsr(guiCheque,"No hay Cheques guardados.");
			} else {
				if (guiCheque.jtDatos.getSelectedRow() == -1){
					Utils.advertenciaUsr(guiCheque,"Para poder Eliminar un Cheque debe seleccionarlo previamente.");
				} else {
					Long id = (Long)guiCheque.datos[guiCheque.jtDatos.getSelectedRow()][0];
					String cod = (String)guiCheque.datos[guiCheque.jtDatos.getSelectedRow()][1];
					int prueba = Utils.aceptarCancelarAccion(guiCheque,"Esta seguro que desea Eliminar el Cheque Nº: "+ cod);
					if (prueba == 0){
						this.controlCheque.eliminarCheque(id);
					}    
					actualizarDatosTabla();
				}
			}
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Eliminar");
		}
	}
	
	private void verificarVencidos() {
		try {
			new MediadorListarCheques(guiCheque);
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Modificar");
		}
	}
		
	public GUIGestionarCheque getGUI() {
		return guiCheque;
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
