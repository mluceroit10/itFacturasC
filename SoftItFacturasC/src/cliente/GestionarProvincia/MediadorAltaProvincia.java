package cliente.GestionarProvincia; 

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import cliente.ClienteConection;

import common.Utils;
import common.dto.ProvinciaDTO;
import common.interfaces.IControlProvincia;

public class MediadorAltaProvincia implements ActionListener {
	
	private GUIAltaModProvincia guiProvincia = null;
	private MediadorGestionarProvincia mgProvincia;
	public IControlProvincia controlProvincia;
	
	public MediadorAltaProvincia(MediadorGestionarProvincia oldMGProvincia,JDialog guiPadre) throws Exception {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlProvincia = clienteConexion.getIControlProvincia();
		guiProvincia = new GUIAltaModProvincia(guiPadre);
		guiProvincia.setActionListeners(this);
		mgProvincia = oldMGProvincia;
		Utils.show(guiProvincia);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == guiProvincia.getJBAceptar()) {
			String nombre = Utils.mayuscula(guiProvincia.getJTFNombre().getText());
			try {
				if (nombre.length()==0){
					Utils.advertenciaUsr(guiProvincia,"Alguno de los campos obligatorios esta vacio.");
				}else if (this.controlProvincia.existeProvinciaNombre(nombre)){
					Utils.advertenciaUsr(guiProvincia,"La Provincia ingresada ya existe.");
				}else{
					ProvinciaDTO miProvincia = new ProvinciaDTO();
					miProvincia.setNombre(nombre);
					this.controlProvincia.agregarProvincia(miProvincia);
					guiProvincia.dispose();
					mgProvincia.cargarDatos();
				}
			} catch(Exception ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
			}
		} else if (source == guiProvincia.getJBCancelar()) {
			((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
		}
	}
	
}

