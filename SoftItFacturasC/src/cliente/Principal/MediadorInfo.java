package cliente.Principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

import common.Utils;

public class MediadorInfo implements ActionListener{
	private GUIInfo guiInf;
	
	public MediadorInfo(MediadorPrincipal oldMPpal,JFrame guiPrincipal) throws Exception { 
		super();
		this.guiInf= new GUIInfo(guiPrincipal);
		this.guiInf.setActionListeners(this);
		Utils.show(guiInf);
	}  
	
	public void actionPerformed(ActionEvent e) {
		URL uri=null;
		try {
			uri = new URL("http://www.it10coop.com.ar");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		open(uri);
	}
	
	private static void open(URL url) {
		String osName = System.getProperty("os.name");
		try {
			if (osName.startsWith("Windows")) {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
			} else if (osName.startsWith("Mac OS X")) {
				Runtime.getRuntime().exec("open " + url);
			} else {
				System.out.println("Please open a browser and go to "+ url);
			}
		} catch (IOException e) {
			System.out.println("Failed to start a browser to open the url " + url);
			e.printStackTrace();
		}
	}
	
}
