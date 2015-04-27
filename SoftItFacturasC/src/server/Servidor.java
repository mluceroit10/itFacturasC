package server;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Servidor {
	
	private String name = "";
	private String ip = "";
	
	//Controles
	
	private ControlCheque controlCheque =null;
	private ControlCliente controlCliente = null;
	private ControlComercio controlComercio =null;
	private ControlFacturaCliente controlFCliente =null;
	private ControlLocalidad controlLocalidad = null;
	private ControlMovimientoCaja controlMovimientoCaja = null;
	private ControlNotaDebito controlNotaDebito =null;
	private ControlPlanillaES controlPlanillaES = null;
	private ControlProvincia controlProvincia = null;
	
	private String classPath = "";
	
	public void iniciar() throws Exception {
		Registry Naming =LocateRegistry.createRegistry(1099);
		System.setProperty("java.rmi.server.codebase", "file:" + this.classPath);
		System.out.println("Iniciando servidor !!!");
		
		
		this.ip=(InetAddress.getLocalHost().getHostAddress()).toString();
		System.out.print("ipServidor  "+ip);
		System.out.println("Ip: " + this.ip);
		
		this.name = "rmi://" + this.ip + "/IControlCheque";
		Naming.rebind(this.name, this.controlCheque);
		System.out.println("Nombre: " + this.name);
		
		this.name = "rmi://" + this.ip + "/IControlLocalidad";
		Naming.rebind(this.name, this.controlLocalidad);
		System.out.println("Nombre: " + this.name);
		
		this.name = "rmi://" + this.ip + "/IControlMovimientoCaja";
		Naming.rebind(this.name, this.controlMovimientoCaja);
		System.out.println("Nombre: " + this.name);
		
		this.name = "rmi://" + this.ip + "/IControlCliente";
		Naming.rebind(this.name, this.controlCliente);
		System.out.println("Nombre: " + this.name);
		
		this.name = "rmi://" + this.ip + "/IControlComercio";
		Naming.rebind(this.name, this.controlComercio);
		System.out.println("Nombre: " + this.name);
		
		this.name = "rmi://" + this.ip + "/IControlFacturaCliente";
		Naming.rebind(this.name, this.controlFCliente);
		System.out.println("Nombre: " + this.name);
		
		this.name = "rmi://" + this.ip + "/IControlNotaDebito";
		Naming.rebind(this.name, this.controlNotaDebito);
		System.out.println("Nombre: " + this.name);
		
		this.name = "rmi://" + this.ip + "/IControlProvincia";
		Naming.rebind(this.name, this.controlProvincia);
		System.out.println("Nombre: " + this.name);
		
		this.name = "rmi://" + this.ip + "/IControlPlanillaES";
		Naming.rebind(this.name, this.controlPlanillaES);
		System.out.println("Nombre: " + this.name);
		
	/*	GUIServidorIniciado serv=new GUIServidorIniciado(ip);
		serv.show();*/
		System.out.println("Servidor OK !!!");
		
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	
	public void setControlCheque(ControlCheque controlCheque) {
		this.controlCheque = controlCheque;
	}
	
	public void setControlCliente(ControlCliente controlCliente) {
		this.controlCliente = controlCliente;
	}
	
	public void setControlComercio(ControlComercio controlComercio) {
		this.controlComercio = controlComercio;
	}
	
	public void setControlFCliente(ControlFacturaCliente controlFCliente) {
		this.controlFCliente = controlFCliente;
	}
	
	public void setControlLocalidad(ControlLocalidad controlLocalidad) {
		this.controlLocalidad = controlLocalidad;
	}
	
	public void setControlMovimientoCaja(ControlMovimientoCaja controlMovimientoCaja) {
		this.controlMovimientoCaja = controlMovimientoCaja;
	}
	
	public void setControlNotaDebito(ControlNotaDebito controlNotaDebito) {
		this.controlNotaDebito = controlNotaDebito;
	}
	
	public void setControlPlanillaES(ControlPlanillaES controlPlanillaES) {
		this.controlPlanillaES = controlPlanillaES;
	}
	
	public void setControlProvincia(ControlProvincia controlProvincia) {
		this.controlProvincia = controlProvincia;
	}
	
}

