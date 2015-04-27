package cliente.Principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import cliente.GestionarCheque.MediadorGestionarCheque;
import cliente.GestionarCliente.MediadorGestionarCliente;
import cliente.GestionarComercio.MediadorModificarComercio;
import cliente.GestionarFacturaCliente.MediadorFacturarCliente;
import cliente.GestionarLocalidad.MediadorGestionarLocalidad;
import cliente.GestionarMovimientoCaja.MediadorGestionarMovimientoCaja;
import cliente.GestionarNotaDebito.MediadorAltaNotaDebitoCliente;
import cliente.GestionarNotaDebito.MediadorGestionarNotaDebito;
import cliente.GestionarPlanillaES.MediadorGestionarPlanillaES;
import cliente.GestionarProvincia.MediadorGestionarProvincia;
import cliente.GestionarRemitoCliente.MediadorRemitoCliente;
import cliente.ListarDeudaClientes.MediadorMostrarDeudaClientes;
import cliente.ListarFacturasCliente.MediadorListarFacturasCliente;
import cliente.ListarRemitosCliente.MediadorListarRemitosCliente;
import cliente.backupDataBase.MediadorBackup;

import common.RootAndIp;
import common.Utils;

public class MediadorPrincipal implements ActionListener{
	
	private GUIPrincipal guiPrincipal;  
	private Date hoy= new Date();
	
	public MediadorPrincipal() throws Exception{ 
		this.guiPrincipal = new GUIPrincipal();  
		this.guiPrincipal.setActionListeners(this);
		this.guiPrincipal.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		int mesL=Utils.getMes(hoy);
		int anioL=Utils.getAnio(hoy);
		Object source=e.getSource();
		if(this.guiPrincipal.getArchivoInfoComercio()==source){
			try{
				new MediadorModificarComercio(guiPrincipal);
			} catch (Exception p){}
		}else if (this.guiPrincipal.getInfoProg()== source) {
			try{
				new MediadorInfo(this, guiPrincipal);
			} catch (Exception p){}	
		}else if(this.guiPrincipal.getArchivoSalir()==source){
			int prueba = JOptionPane.showConfirmDialog(guiPrincipal,"Esta seguro que desea Salir","ATENCION!!!", 0,3,new ImageIcon(RootAndIp.getExtras()+"/iconos/deseaSalir.gif"));
			if( prueba==0 )
				System.exit(0);
		}else if (this.guiPrincipal.getGestionarProvincia()== source) {
			try{
				new MediadorGestionarProvincia(guiPrincipal);
			} catch (Exception p){}
		}else if (this.guiPrincipal.getGestionarLocalidad()== source) {
			try{
				new MediadorGestionarLocalidad(guiPrincipal);
			} catch (Exception p){}
		}else if(this.guiPrincipal.getJButtonGestionarMC()==source){
			try{
				new MediadorGestionarMovimientoCaja(mesL,anioL,guiPrincipal);
			}catch (Exception p){
				p.printStackTrace();
			}	
		}else if(this.guiPrincipal.getJButtonPlanillaES()==source){
			try{
				new MediadorGestionarPlanillaES(anioL,guiPrincipal);
			}catch (Exception p){
				p.printStackTrace();
			}			
		}else if(this.guiPrincipal.getJButtonClientes()==source){
			try{
				new MediadorGestionarCliente(guiPrincipal);
			}catch (Exception p){
				p.printStackTrace();
			}
		}else if(this.guiPrincipal.getJButtonFacturaCliente()==source){
			try{
				MediadorFacturarCliente msprod = new MediadorFacturarCliente(guiPrincipal);
				msprod.show();
			}catch (Exception p){
				p.printStackTrace();
			}	
		}else if(this.guiPrincipal.getJButtonTodasFactCliente()==source){
			try{
				new MediadorListarFacturasCliente(mesL,anioL,guiPrincipal);
			}catch (Exception p){
				p.printStackTrace();
			}		
		}else if(this.guiPrincipal.getJButtonRemitoCliente()==source){
			try{
				MediadorRemitoCliente mrc = new MediadorRemitoCliente(guiPrincipal);
				mrc.show();
			}catch (Exception p){
				p.printStackTrace();
			}	
		}else if(this.guiPrincipal.getJButtonTodosRemitosCliente()==source){
			try{
				new MediadorListarRemitosCliente(mesL,anioL,guiPrincipal);
			}catch (Exception p){
				p.printStackTrace();
			}	
		}else if (this.guiPrincipal.getJButtonTodasNDebitoCliente()== source) {
			try{
				new MediadorGestionarNotaDebito(mesL,anioL,guiPrincipal);
			} catch (Exception p){}		
		}else if (this.guiPrincipal.getJButtonNDebitoCliente()== source) {
			try{
				new MediadorAltaNotaDebitoCliente(guiPrincipal);
			} catch (Exception p){}	
		}else if(this.guiPrincipal.getJButtonDeudasClientes()==source){
			try{
				MediadorMostrarDeudaClientes mmdc=new MediadorMostrarDeudaClientes(guiPrincipal);
				mmdc.show();
			}catch (Exception p){
				p.printStackTrace();
			}	
		}else if (this.guiPrincipal.getJButtonCheques()== source) {
			try{
				new MediadorGestionarCheque(mesL,anioL,guiPrincipal);
			} catch (Exception p){}	
		}else if(this.guiPrincipal.getBaseDatos()==source){
			try{
				new MediadorBackup(guiPrincipal);
			}catch (Exception p){
				p.printStackTrace();
			}
		}else
			if(this.guiPrincipal.getJButtonSalir()== source){
				int prueba = JOptionPane.showConfirmDialog(guiPrincipal,"Esta seguro que desea Salir","ATENCION!!!", 0,3,new ImageIcon(RootAndIp.getExtras()+"/iconos/deseaSalir.gif"));
				if( prueba==0 ){
					System.exit(0);
				}    
			}   
	}	
	
	public void show() {
		guiPrincipal.show();
	}
}
