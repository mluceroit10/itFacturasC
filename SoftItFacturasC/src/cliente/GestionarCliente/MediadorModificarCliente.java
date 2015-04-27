package cliente.GestionarCliente;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import cliente.ClienteConection;
import cliente.GestionarLocalidad.MediadorGestionarLocalidad;

import common.Utils;
import common.dto.ClienteDTO;
import common.dto.LocalidadDTO;
import common.interfaces.IControlCliente;

public class MediadorModificarCliente implements ActionListener {
	
	private GUIAltaModCliente guiModCliente = null;
	public IControlCliente controlCliente;
	private MediadorGestionarCliente mgCliente;
	private MediadorGestionarLocalidad mgLoc = null;
	public LocalidadDTO localidad;
	public ClienteDTO cte;
	
	public MediadorModificarCliente(MediadorGestionarCliente oldMGCliente, ClienteDTO cl,JDialog guiPadre) {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlCliente = clienteConexion.getIControlCliente();
		guiModCliente = new GUIAltaModCliente(cl,guiPadre);
		guiModCliente.setActionListeners(this);
		mgCliente = oldMGCliente;
		cte = cl;
		localidad = cl.getLocalidad();
		Utils.show(guiModCliente);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == guiModCliente.getAceptar()) {
			String nombre = Utils.mayuscula(guiModCliente.getNombre().getText());
			String tel = guiModCliente.getTelefono().getText();
			String cuit = guiModCliente.getCuit().getText();
			String direccion = Utils.mayuscula(guiModCliente.getDireccion().getText());
			String nLoc=guiModCliente.getLocalidad().getText();
			String ingBrutos="";
			String iva=guiModCliente.getJCBIvaCl().getSelectedItem().toString();
			String codigo = guiModCliente.getCodigo().getText();
			if (nombre.length()==0 || direccion.length()==0 ||nLoc.length()==0 || codigo.length()==0){
				Utils.advertenciaUsr(guiModCliente,"Alguno de los campos obligatorios esta vacio.");
			} else {
				if(cuit.length()!=0 &&  Utils.verificarCuit(cuit)){
					Utils.advertenciaUsr(guiModCliente,"El Cuit ingresado no es correcto.");
				}else{
					try{
						ClienteDTO cliente = new ClienteDTO();
						if(codigo.compareTo("")!=0)
							cliente.setCodigo(new Long(codigo));
						cliente.setNombre(nombre);
						cliente.setCuit(cuit);
						if(cuit.length()>0){
							ingBrutos = guiModCliente.getIngrBrutos().getText();
						}
						cliente.setTelefono(tel);
						cliente.setIngBrutosCl(ingBrutos);
						cliente.setIvaCl(iva);
						cliente.setDireccion(direccion);
						cliente.setLocalidad(localidad);
						cliente.setEliminado(false);
						cliente.setDeuda(cte.getDeuda());
						if (this.controlCliente.puedoEditarNombre(cte,cliente)){
							if(codigo.compareTo("")!=0){
								if(this.controlCliente.puedoEditarCodigo(cte,cliente)){
									this.controlCliente.modificarCliente(cte.getId(), cliente);
									guiModCliente.dispose();
									mgCliente.cargarDatos();
								} else {
									Utils.advertenciaUsr(guiModCliente,"El Cliente que desea ingresar ya existe en el sistema con ese código");
								}
							}else{
								this.controlCliente.modificarCliente(cte.getId(), cliente);
								guiModCliente.dispose();
								mgCliente.cargarDatos();
							}
							
						} else {
							Utils.advertenciaUsr(guiModCliente,"El Cliente que desea ingresar ya existe en el sistema con ese nombre");
						}
					} catch(Exception ex) {
						Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
					}
				}
			}
		} else if (source == guiModCliente.getJButtonLocalidad()) {
			buscarLocalidad();
		} else if (source == guiModCliente.getCancelar()) {    
			((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
		}
	}
	
	private void buscarLocalidad() {
		if (mgLoc == null) {
			mgLoc = new MediadorGestionarLocalidad(this,guiModCliente);
		} else {
			if (!mgLoc.getGUI().isVisible()) {
				mgLoc.getGUI().setVisible(true);
			}
		}
		if (localidad != null){
			this.guiModCliente.getLocalidad().setText(localidad.getNombre());
			this.cargarLocalidad(localidad);           
		}
	}
	
	private void cargarLocalidad(LocalidadDTO loc) {
		this.localidad = loc;
	}
	
	public void actualizarLocalidad() {
		guiModCliente.setLocalidad(localidad.getNombre());
	}
	
}
