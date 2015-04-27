package cliente;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.RootAndIp;
import common.interfaces.IControlCheque;
import common.interfaces.IControlCliente;
import common.interfaces.IControlComercio;
import common.interfaces.IControlFacturaCliente;
import common.interfaces.IControlLocalidad;
import common.interfaces.IControlMovimientoCaja;
import common.interfaces.IControlNotaDebito;
import common.interfaces.IControlPlanillaES;
import common.interfaces.IControlProvincia;



public class ClienteConection {
	private String maquina = RootAndIp.getMaquina();
	private String hostServer =RootAndIp.getIp();
	private IControlCheque iControlCheque =null;
	private IControlCliente iControlCliente = null;
	private IControlComercio iControlComercio =null;
	private IControlFacturaCliente iControlFCliente =null;
	private IControlLocalidad iControlLocalidad = null;
	private IControlMovimientoCaja iControlMovimientoCaja = null;
	private IControlNotaDebito iControlNotaDebito =null;
	private IControlPlanillaES iControlPlanillaES = null;
	private IControlProvincia iControlProvincia = null;
	
	public ClienteConection (){
	}
	
	public void iniciar() throws Exception {
		if(maquina.compareTo("servidor")==0)  //alternativa 1
			hostServer = (InetAddress.getLocalHost().getHostAddress()).toString();
		//alternativa 2
		Registry Naming =LocateRegistry.getRegistry(hostServer,1099);
		String nombreServer = "";
		
		nombreServer = "rmi://"+this.hostServer+"/IControlCheque";
		IControlCheque iControlCheque= (IControlCheque)Naming.lookup(nombreServer);
		this.iControlCheque = iControlCheque;
		
		nombreServer = "rmi://"+this.hostServer+"/IControlCliente";
		IControlCliente iControlCliente= (IControlCliente)Naming.lookup(nombreServer);
		this.iControlCliente = iControlCliente;
		
		nombreServer = "rmi://"+this.hostServer+"/IControlComercio";
		IControlComercio iControlComercio= (IControlComercio)Naming.lookup(nombreServer);
		this.iControlComercio = iControlComercio;
		
		nombreServer = "rmi://"+this.hostServer+"/IControlFacturaCliente";
		IControlFacturaCliente iControlFCliente= (IControlFacturaCliente)Naming.lookup(nombreServer);
		this.iControlFCliente = iControlFCliente;
		
		nombreServer = "rmi://"+this.hostServer+"/IControlLocalidad";
		IControlLocalidad iControlLocalidad= (IControlLocalidad)Naming.lookup(nombreServer);
		this.iControlLocalidad = iControlLocalidad;    
		
		nombreServer = "rmi://"+this.hostServer+"/IControlMovimientoCaja";
		IControlMovimientoCaja iControlMovimientoCaja= (IControlMovimientoCaja)Naming.lookup(nombreServer);
		this.iControlMovimientoCaja = iControlMovimientoCaja;
		
		nombreServer = "rmi://"+this.hostServer+"/IControlNotaDebito";
		IControlNotaDebito iControlNotaDebito= (IControlNotaDebito)Naming.lookup(nombreServer);
		this.iControlNotaDebito = iControlNotaDebito;
		
		nombreServer = "rmi://"+this.hostServer+"/IControlPlanillaES";
		IControlPlanillaES iControlPlanillaES= (IControlPlanillaES)Naming.lookup(nombreServer);
		this.iControlPlanillaES = iControlPlanillaES;
		
		nombreServer = "rmi://"+this.hostServer+"/IControlProvincia";
		IControlProvincia iControlProvincia= (IControlProvincia)Naming.lookup(nombreServer);
		this.iControlProvincia = iControlProvincia;
		
	}
	
	public String getHostServer() {
		return hostServer;
	}
	
	public IControlCheque getIControlCheque() {
		return iControlCheque;
	}
	
	public IControlCliente getIControlCliente() {
		return iControlCliente;
	}
	
	public IControlComercio getIControlComercio() {
		return iControlComercio;
	}
	
	public IControlFacturaCliente getIControlFCliente() {
		return iControlFCliente;
	}
	
	public IControlLocalidad getIControlLocalidad() {
		return iControlLocalidad;
	}
	
	public IControlMovimientoCaja getIControlMovimientoCaja() {
		return iControlMovimientoCaja;
	}
	
	public IControlNotaDebito getIControlNotaDebito() {
		return iControlNotaDebito;
	}
	
	public IControlPlanillaES getIControlPlanillaES() {
		return iControlPlanillaES;
	}
	
	public IControlProvincia getIControlProvincia() {
		return iControlProvincia;
	}
	
	
	
}
