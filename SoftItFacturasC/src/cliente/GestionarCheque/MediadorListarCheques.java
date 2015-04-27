package cliente.GestionarCheque;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cliente.ClienteConection;

import common.Utils;
import common.dto.ChequeDTO;
import common.dto.LocalidadDTO;
import common.interfaces.IControlCheque;

public class MediadorListarCheques implements ActionListener,ListSelectionListener {
	
	private IControlCheque controlCheque;
	private GUIListarCheques guiListarCheques=null;
	Vector todasFacturasCte= new Vector();
	Vector clientes= new Vector();
	Vector fechasUF = new Vector();
	Vector saldoFavor = new Vector();
	Vector adeudado= new Vector();
	Vector idLocs= new Vector();
	LocalidadDTO loc = null;
	
	public MediadorListarCheques(JDialog guiPadre) throws Exception { 
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlCheque = clienteConexion.getIControlCheque();
		guiListarCheques = new GUIListarCheques(guiPadre);
		guiListarCheques.setActionListeners(this);
		guiListarCheques.setListSelectionListener(this);
		cargarDatos();
		Utils.show(guiListarCheques);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == guiListarCheques.getJBCambiarBusqueda() ) {
			cargarDatos();
		}else if (source == guiListarCheques.getJBSalir()) {
			guiListarCheques.dispose();
		}
	}
	
	public void cargarDatos() {
		try {
			java.util.Date fVto= guiListarCheques.getJDCNuevaFechaVencimiento().getDate();
			int dia=Utils.getDia(fVto);
			int mes=Utils.getMes(fVto);
			int anio=Utils.getAnio(fVto);
			Vector cheques = new Vector();
			double importeTotal=0;
			guiListarCheques.getJTFDatos().setText(Utils.getStrUtilDate(fVto));
			cheques = this.controlCheque.obtenerChequesVencidos(dia,mes,anio); //Todos Inactivos
			guiListarCheques.datos = new Object[cheques.size()][guiListarCheques.titulos.length];
			for (int j = 0; j < cheques.size(); j++) {
				ChequeDTO ch =(ChequeDTO) cheques.elementAt(j);
				importeTotal=Utils.redondear((importeTotal+ch.getImporte()),2);
				String vencido="No";
				if(ch.getFechaVto().before(fVto))
					vencido="Si";
				Object[] temp = { vencido,Utils.nroCheque(ch.getNumero()),common.Utils.getStrUtilDate(ch.getFechaEmision()),
						ch.getBanco(),ch.getLocPlaza(),ch.getQuienEntrega(),common.Utils.getStrUtilDate(ch.getFechaVto()),Utils.ordenarDosDecimales(ch.getImporte())};
				guiListarCheques.datos[j] = temp;
			}
			guiListarCheques.getJTFTotal().setText(Utils.ordenarDosDecimales(importeTotal));
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error CargarDatos");
		}
		guiListarCheques.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guiListarCheques.actualizarTabla();
	}
	
	public void valueChanged(ListSelectionEvent arg0) {
	}
	
}
