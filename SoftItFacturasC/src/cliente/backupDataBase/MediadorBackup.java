package cliente.backupDataBase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import common.RootAndIp;
import common.Utils;

public class MediadorBackup implements ActionListener{
	
	private GUIBackup guiBackup=null;
	private String pathBackup = common.RootAndIp.getArchivos()+ "backup/";
	private String date;
	
	public MediadorBackup(JFrame guiPadre){
		this.guiBackup = new GUIBackup(guiPadre);
		this.guiBackup.setActionListeners(this);
		this.backUpDataBase();
	}
	
	public void backUpDataBase(){
		boolean seHizo=false;
		try{
			this.backup();
			seHizo=true;
		}catch(Exception e){
			e.printStackTrace();
			seHizo=false;
		}
		if(seHizo){
			try{
				this.guiBackup.getAdvertencia().setText("Se realizó correctamente el Backup de su Base de Datos");
				this.guiBackup.getResultado().setText("El archivo se guardo en el directorio "+pathBackup+" \ncon el nombre backupBD_"+date+ ".SQL");
			}catch(Exception e){
				e.printStackTrace();
				seHizo=false;
				JOptionPane.showMessageDialog(new JOptionPane(), "Ocurrió un error en el sistema, mientras se intentaba\nrealizar el Backup de su Base de Datos. Intente nuevamente.");
			}
		}else{
			this.guiBackup.getAdvertencia().setText("Ocurrió un error mientras se intentaba realizar el Backup de su Base de Datos. Intente nuevamente.");
			this.guiBackup.getResultado().setText("No se creó el archivo correspondiente al Backup de la Base de Datos.");
		}
		Utils.show(guiBackup);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if(this.guiBackup.getJButtonSalir()==source){
			this.guiBackup.dispose();
		}
	}
	
	public void backup()throws Exception{
		Runtime rt= Runtime.getRuntime();
		try{
			FileOutputStream archivo= new FileOutputStream(pathBackup+"backup.bat");
			Date fe= new Date();
			String fecha = fe.toLocaleString();
			int p = fecha.indexOf(" ");
			String primerParte = fecha.substring(0,p);
			boolean esConGuion = false; 
			int barra1 = primerParte.indexOf("-");
			if(barra1 >= 0) {
				esConGuion = true;
			}
			String diaMesAnio ="";
			if(!esConGuion){
				barra1 = primerParte.indexOf("/");
				if(barra1 < 0 ){
					barra1 = primerParte.indexOf(".");
				}
				String parte1Barra = primerParte.substring(0,barra1);
				diaMesAnio = parte1Barra +"-"+ primerParte.substring(barra1+1,barra1+3)+"-"+primerParte.substring(barra1+4,primerParte.length());
			}else{
				diaMesAnio  = primerParte.substring(0,11);
			}
			String segundaParte = fecha.substring(p+1,fecha.length());
			int punto1 = segundaParte.indexOf(":");
			String parte1Punto = segundaParte.substring(0,punto1);
			String horaMinSeg = parte1Punto +"-"+ segundaParte.substring(punto1+1,punto1+3)+"-"+segundaParte.substring(punto1+4,segundaParte.length());
			date = diaMesAnio+"_hr_"+horaMinSeg;
			
			String nameURL=RootAndIp.getUrlDb();
			String[] datos=nameURL.split("/");
			String nameDB=datos[datos.length-1];
			// --host=""
			String maq=RootAndIp.getMaquina();
			String linea="";
			if(maq.compareTo("servidor")==0)
				linea= new String("C:/mysql/bin/MYSQLDUMP.EXE --opt --password="+common.RootAndIp.getPassword()+" --user="+common.RootAndIp.getUserName()+" "+nameDB+" jdo_table oid provincia localidad cliente comercio " +
						"factura  factura_cliente nota_debito planilla_es movimiento_caja item_factura cheque >"+pathBackup+"backupBD_"+date+".SQL");
			else if(maq.compareTo("cliente")==0)
				linea= new String("C:/mysql/bin/MYSQLDUMP.EXE --opt --host="+RootAndIp.getIp()+" --password="+common.RootAndIp.getPassword()+" --user="+common.RootAndIp.getUserName()+" "+nameDB+" jdo_table oid provincia localidad cliente comercio " +
						"factura  factura_cliente nota_debito planilla_es movimiento_caja item_factura cheque >"+pathBackup+"backupBD_"+date+".SQL");
			
			archivo.write(linea.getBytes());
			archivo.close();
			rt.exec(pathBackup+"backup.bat");
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}
