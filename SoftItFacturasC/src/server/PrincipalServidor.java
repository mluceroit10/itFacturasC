package server;

import common.RootAndIp;

public class PrincipalServidor {
	
	public PrincipalServidor() {
		super();
	}
	
	public static void main(String[] args) {
		String conf = "";
		if ((args != null) && (args.length > 0)) {  
			conf = args[0];
		}
		common.RootAndIp.setConf(conf);
		
		
		try {
			// directorio donde estan las clases
			String classPath = RootAndIp.getBase();
			
			// el Control que se publicara
			ControlCheque controlCheque = new ControlCheque();
			ControlCliente controlCliente = new ControlCliente();
			ControlComercio controlComercio = new ControlComercio();
			ControlFacturaCliente controlFCliente = new ControlFacturaCliente();
			ControlLocalidad controlLocalidad = new ControlLocalidad();
			ControlMovimientoCaja controlMovimientoCaja = new ControlMovimientoCaja();
			ControlNotaDebito controlNotaDebito = new ControlNotaDebito();
			ControlPlanillaES controlPlanillaES = new ControlPlanillaES();
			ControlProvincia controlProvincia = new ControlProvincia();
			
			// creando, seteando e inicializando el Servidor
			Servidor servidor = new Servidor();
			servidor.setClassPath(classPath);
			//Controles
			servidor.setControlCheque(controlCheque);
			servidor.setControlCliente(controlCliente);
			servidor.setControlLocalidad(controlLocalidad);
			servidor.setControlComercio(controlComercio);
			servidor.setControlFCliente(controlFCliente);
			servidor.setControlMovimientoCaja(controlMovimientoCaja);
			servidor.setControlNotaDebito(controlNotaDebito);
			servidor.setControlProvincia(controlProvincia);
			servidor.setControlPlanillaES(controlPlanillaES);
			servidor.iniciar();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
