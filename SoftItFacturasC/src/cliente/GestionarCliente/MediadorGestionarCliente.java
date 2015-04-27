package cliente.GestionarCliente;

import java.awt.Component;
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
import cliente.GestionarFacturaCliente.MediadorFacturarCliente;
import cliente.GestionarRemitoCliente.MediadorRemitoCliente;
import cliente.Principal.GUIReport;

import common.Excel;
import common.Utils;
import common.dto.ClienteDTO;
import common.dto.ComercioDTO;
import common.dto.LocalidadDTO;
import common.interfaces.IControlCliente;
import common.interfaces.IControlComercio;
import constantes.NRO_REPORTE;

public class MediadorGestionarCliente implements ActionListener, ListSelectionListener, KeyListener {
	
	private GUIGestionarCliente guiCliente = null;
	protected IControlCliente controlCliente;
	public ClienteDTO socDto=null;
	private boolean flag=false;
	private MediadorRemitoCliente medRealizRemito = null;
	private ClienteDTO miClienteDto;
	private MediadorFacturarCliente medFecturarCliente;
	private IControlComercio controlComercio;
	
	public MediadorGestionarCliente(JFrame guiPadre) {
		//super();
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlCliente = clienteConexion.getIControlCliente();
		controlComercio = clienteConexion.getIControlComercio();
		this.guiCliente = new GUIGestionarCliente(guiPadre);
		this.guiCliente.setActionListeners(this);
		cargarDatos();
		this.guiCliente.setListSelectionListener(this);
		this.guiCliente.setKeyListener(this);
		this.flag=true;
		Utils.show(guiCliente);
	}
	
	public MediadorGestionarCliente(MediadorFacturarCliente medFC,JDialog guiPadre) {
		this.medFecturarCliente = medFC;
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlCliente = clienteConexion.getIControlCliente();
		controlComercio = clienteConexion.getIControlComercio();
		this.guiCliente = new GUIGestionarCliente(guiPadre);
		this.guiCliente.setActionListeners(this);
		cargarDatos();
		this.guiCliente.setListSelectionListener(this);
		this.guiCliente.setKeyListener(this);
		Utils.show(guiCliente);
	}
	
	public MediadorGestionarCliente(MediadorRemitoCliente medRR,JDialog guiPadre) {
		this.medRealizRemito = medRR;
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlCliente = clienteConexion.getIControlCliente();
		controlComercio = clienteConexion.getIControlComercio();
		this.guiCliente = new GUIGestionarCliente(guiPadre);
		this.guiCliente.setActionListeners(this);
		cargarDatos();
		this.guiCliente.setListSelectionListener(this);
		this.guiCliente.setKeyListener(this);
		Utils.show(guiCliente);
	}
	
	public void cargarDatos() {
		try {
			Vector clientes = new Vector();
			clientes = this.controlCliente.obtenerClientes();
			guiCliente.datos = new Object[clientes.size()][guiCliente.titulos.length];
			if(clientes!=null){
				for (int j = 0; j < clientes.size(); j++) {
					ClienteDTO cte = (ClienteDTO) clientes.elementAt(j);
					Object[] temp = {cte.getId(),cte.getCodigo(),cte.getNombre(),cte.getCuit(),categoria(cte.getIvaCl()),
							cte.getTelefono(),cte.getDireccion(),(((LocalidadDTO)cte.getLocalidad()).getNombre())};
					guiCliente.datos[j] = temp;
				}
			}
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error CargarDatos");
		}
		guiCliente.tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guiCliente.actualizarTabla();
	}
	
	public void actionPerformed(ActionEvent e) {
		if ((((Component)e.getSource()).getName().compareTo("Alta")) == 0) {
			try{
				new MediadorAltaCliente(this,guiCliente);
			} catch (Exception ex){
				Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
			}
		} else if ((((Component)e.getSource()).getName().compareTo("Baja")) == 0){
			eliminar();
		} else if ((((Component)e.getSource()).getName().compareTo("Modificar")) == 0){
			modificar();
		} else if ((((Component)e.getSource()).getName().compareTo("Imprimir")) == 0){
			try{
				Vector clientes ;
				String titulo="";
				clientes = controlCliente.obtenerClientes();
				titulo="Listado de Clientes";
				ComercioDTO comercio = controlComercio.obtenerComercio();
				new GUIReport(guiCliente,NRO_REPORTE.listarTodosClientes,comercio,clientes,titulo);
			} catch (Exception ex){
				Utils.manejoErrores(ex,this.getClass(),"Error Imprimir");
			}
		}else if ((((Component)e.getSource()).getName().compareTo("Aceptar")) == 0){
			if(flag){
				this.guiCliente.dispose();
			}
			else{
				if (cargarFilaSeleccionada()) {
					if (medFecturarCliente != null) {
						medFecturarCliente.cliente = miClienteDto;
						medFecturarCliente.actualizarCliente();
						this.guiCliente.dispose();
					}else if (medRealizRemito != null) {
						medRealizRemito.cliente = miClienteDto;
						medRealizRemito.actualizarCliente();
						this.guiCliente.dispose();
					}
				}
			}
		}else if ((((Component)e.getSource()).getName().compareTo("Cuenta")) == 0){
			obtenerCuentaCliente();
		} else{ 
			if((((Component)e.getSource()).getName().compareTo("Cancelar")) == 0){ 
				guiCliente.dispose();
			} 
			
		}
	}
	
	public boolean cargarFilaSeleccionada() {
		boolean result=false;
		try{
			if (guiCliente.tabla.getSelectedRow() == -1) {
				Utils.advertenciaUsr(guiCliente,"Debe seleccionar un Cliente.");
				result = false;
			}else{
				Long id = (Long)guiCliente.datos[guiCliente.tabla.getSelectedRow()][0];
				miClienteDto = this.controlCliente.buscarCliente(id);
				result = true;
			}
		} catch (Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error CargarFilaSeleccionada");
		}
		return result;
	}
	
	private void eliminar() {
		try{
			if ( this.controlCliente.obtenerClientes().isEmpty()){
				Utils.advertenciaUsr(guiCliente,"No hay Clientes guardados.");
			} else {
				if (guiCliente.tabla.getSelectedRow() == -1){
					Utils.advertenciaUsr(guiCliente,"Para poder Eliminar un Cliente debe seleccionarlo previamente");
				} else {
					Long id = (Long)guiCliente.datos[guiCliente.tabla.getSelectedRow()][0];
					String nombre = (String)guiCliente.datos[guiCliente.tabla.getSelectedRow()][2];
					int prueba = Utils.aceptarCancelarAccion(guiCliente,"Esta seguro que desea Eliminar el Cliente \n"+ nombre);
					if (prueba == 0)
						this.controlCliente.eliminarCliente(id);
					cargarDatos();
				}
			}
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Eliminar");
		}
	}
	
	public void modificar(){
		try {
			if (this.controlCliente.obtenerClientes().isEmpty()){
				Utils.advertenciaUsr(guiCliente,"No hay Clientes guardados en el sistema.");
				
			} else if (guiCliente.tabla.getSelectedRow() == -1){
				Utils.advertenciaUsr(guiCliente,"Para poder Modificar un Cliente debe seleccionarlo previamente.");
			} else {
				Long id = (Long)guiCliente.datos[guiCliente.tabla.getSelectedRow()][0];
				ClienteDTO socDTO = (ClienteDTO)this.controlCliente.buscarCliente(id);
				new MediadorModificarCliente(this, socDTO,guiCliente);
			}
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Modificar");
		}
	}
	
	public void obtenerCuentaCliente(){
		try {
			if (this.controlCliente.obtenerClientes().isEmpty()){
				Utils.advertenciaUsr(guiCliente,"No hay Clientes guardados en el sistema.");
				
			} else if (guiCliente.tabla.getSelectedRow() == -1){
				Utils.advertenciaUsr(guiCliente,"Para poder verificar la Cuenta de un Cliente debe seleccionarlo previamente.");
			} else {
				Long id = (Long)guiCliente.datos[guiCliente.tabla.getSelectedRow()][0];
				ClienteDTO socDTO = (ClienteDTO)this.controlCliente.buscarCliente(id);
				new MediadorCuentaCliente(this,socDTO,guiCliente);
			}
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error E cuenta");
		}
	}
	
	public void valueChanged(ListSelectionEvent e) {
	}
	
	public void keyTyped(KeyEvent e) {
	}
	
	public void keyPressed(KeyEvent e) {
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
			actualizarTabla();
	}
	
	private void actualizarTabla() {
		try {
			Vector clientes = new Vector();
			clientes = this.controlCliente.obtenerClientesFiltro(guiCliente.getJTFBuscadorNom().getText(),guiCliente.getJTFBuscadorCod().getText());
			guiCliente.datos = new Object[clientes.size()][guiCliente.titulos.length];
			for (int j = 0; j < clientes.size(); j++) {
				ClienteDTO cte = (ClienteDTO) clientes.elementAt(j);
				Object[] temp = {cte.getId(),cte.getCodigo(),cte.getNombre(),cte.getCuit(),categoria(cte.getIvaCl()),
						cte.getTelefono(),cte.getDireccion(),(((LocalidadDTO)cte.getLocalidad()).getNombre())};
				guiCliente.datos[j] = temp;
			}
			
		} catch(Exception ex) {
			Utils.manejoErrores(ex,this.getClass(),"Error ActualizarTablaConNombre");
		}
		guiCliente.tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guiCliente.actualizarTabla();
	}
	
	public GUIGestionarCliente getGUI() {
		return guiCliente;
	}
	
	private String categoria(String categ){
		String c="";
		if(categ.compareTo("Resp. Inscripto")==0)
			c = "R. Inscr.";
		if(categ.compareTo("Resp. NO Inscripto")==0)
			c = "R. NO I.";
		if(categ.compareTo("Exento")==0)
			c = "Exento";
		if(categ.compareTo("NO Resp.")==0)
			c = "No Resp.";
		if(categ.compareTo("Consumidor Final")==0)
			c = "C. Final";
		if(categ.compareTo("Monotributo")==0)
			c = "Monotrib.";
		return c;
		
	}
}