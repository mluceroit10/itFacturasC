package cliente.GestionarCheque;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JDialog;

import cliente.ClienteConection;

import common.Utils;
import common.dto.ChequeDTO;
import common.interfaces.IControlCheque;

public class MediadorCambiarEstadoCheque implements ActionListener {
	
	private GUICambiarEstadoCheque guiCheque = null;
	private MediadorGestionarCheque mgCheque;
	public IControlCheque controlCheque;
	public ChequeDTO cheque;
	
	public MediadorCambiarEstadoCheque(MediadorGestionarCheque oldMGCheque,ChequeDTO ch,JDialog guiPadre) throws Exception {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlCheque = clienteConexion.getIControlCheque();
		guiCheque = new GUICambiarEstadoCheque(ch,guiPadre);
		guiCheque.setActionListeners(this);
		mgCheque = oldMGCheque;
		cheque=ch;
		Utils.show(guiCheque);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == guiCheque.getJBAceptar()) {
			String nuevoEstado =  guiCheque.getJCBEstados().getSelectedItem().toString();
			String remitido =  guiCheque.getJTFRemitido().getText();
			try{
				this.controlCheque.cambiarEstado(cheque.getId(), nuevoEstado,remitido);
				guiCheque.dispose();
				mgCheque.actualizarDatosTabla();
			} catch(Exception ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
			}
		} else if (source == guiCheque.getJCBEstados()) {
			if(guiCheque.getJCBEstados().getSelectedItem().toString().compareTo("Remitido")==0){
				guiCheque.getJTFRemitido().setEnabled(true);
			}else{
				guiCheque.getJTFRemitido().setText("");
				guiCheque.getJTFRemitido().setEnabled(false);
			}
		} else if (source == guiCheque.getJBCancelar()) {
			((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
		}
	}
	
}

