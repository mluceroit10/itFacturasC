package cliente;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import cliente.Principal.MediadorPrincipal;

import common.RootAndIp;
import common.Utils;

public class Main {    
	
	public static void main(String[] args) throws Exception {
		String conf = "";
		if (args.length > 0) {
			conf = args[0];
		}    
		common.RootAndIp.setConf(conf);  
		JPasswordField jpf = new JPasswordField();
		JLabel titulo = new JLabel ("Ingrese su contraseña");
		int rta=JOptionPane.showConfirmDialog (null, new Object[]{titulo, jpf}, "itFacturasC: Inicio de sesión", JOptionPane.OK_CANCEL_OPTION,3,new ImageIcon(RootAndIp.getExtras()+"/iconos/logo2.png"));
		char p[] = jpf.getPassword();
		String pass = new String(p);
		if(rta==0){
			if(pass.compareTo("it10")==0){
				MediadorPrincipal mp = new MediadorPrincipal();
				mp.show();
			}else{
				Utils.advertenciaUsr(null,"La Contraseña ingresada no es correcta.");
			}
		}
	}  
	
}
