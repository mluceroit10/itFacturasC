package cliente.GestionarCheque;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JDialog;

import cliente.ClienteConection;

import common.Utils;
import common.dto.ChequeDTO;
import common.dto.FacturaDTO;
import common.interfaces.IControlCheque;

public class MediadorModificarCheque implements ActionListener {
	
	private GUIAltaModCheque guiCheque = null;
	private MediadorGestionarCheque mgCheque;
	public IControlCheque controlCheque;
	public String tipoFact;
	public FacturaDTO factura;
	public int codProv=0;
	public ChequeDTO cheque;
	
	public MediadorModificarCheque(MediadorGestionarCheque oldMGCheque,ChequeDTO ch,JDialog guiPadre) throws Exception {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlCheque = clienteConexion.getIControlCheque();
		guiCheque = new GUIAltaModCheque(ch,guiPadre);
		guiCheque.setActionListeners(this);
		mgCheque = oldMGCheque;
		cheque=ch;
		Utils.show(guiCheque);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == guiCheque.getJBAceptar()) {
			String numero = guiCheque.getJTFNroCheque().getText();
			java.util.Date fEm= guiCheque.getJDateChooserFechaEm().getDate();
			java.util.Date fVto= guiCheque.getJDateChooserFechaVto().getDate();
			Date fechaE= Utils.crearFecha(fEm);
			Date fechaV= Utils.crearFecha(fVto);
			String importe = guiCheque.getJTFImporte().getText();
			String para =  guiCheque.getJTFPara().getText();
			String banco =  guiCheque.getJTFBanco().getText();
			String loc_plaza =  guiCheque.getJTFLocPlaza().getText();
			try{
				if (numero.length()==0 || importe.length()==0 || para.length()==0  || banco.length()==0 || loc_plaza.length()==0){  //fEm.length()==0 || fVto.length()==0  || 
					Utils.advertenciaUsr(guiCheque,"Alguno de los campos obligatorios esta vacio.");
				}else {
					ChequeDTO michDTO = new ChequeDTO();
					michDTO.setNumero(new Long(numero));
					michDTO.setFechaEmision(fechaE);
					michDTO.setFechaVto(fechaV);
					michDTO.setImporte(Double.parseDouble(importe));
					michDTO.setQuienEntrega(para);
					michDTO.setBanco(banco);
					michDTO.setLocPlaza(loc_plaza);
					michDTO.setEstado(cheque.getEstado());
					if (this.controlCheque.puedoEditar(cheque,michDTO)){
						this.controlCheque.modificarCheque(cheque.getId(), michDTO);
						guiCheque.dispose();
						mgCheque.actualizarDatosTabla();
					} else {
						Utils.advertenciaUsr(guiCheque,"El cheque que desea Ingresar ya existe con ese número.");
					}
				}
			} catch(Exception ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
			}
		} else if (source == guiCheque.getJBCancelar()) {
			((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
		}
	}
	
}

