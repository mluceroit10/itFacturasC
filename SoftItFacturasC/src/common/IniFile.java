package  common;

/** Para la lectura de un archivo INI **/
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Set;

public class IniFile {
	
	private String nameFile = "";  
	private Properties p = null;   
	
	public IniFile() { 
		p = new Properties();
	}
	
	public IniFile(String nameFile) {
		this.p = new Properties();
		this.nameFile = nameFile;
		this.setNameFile(this.nameFile);
	}
	
	/** Leer el parametro del archivo seteado */
	public String getParameters(String nombreParametro){
		return p.getProperty(nombreParametro).trim();
	}
	
	public void setNameFile(String nameFile) {
		try {
			this.nameFile = nameFile;
			p.load(new FileInputStream(this.nameFile));
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Set keySet(){
		return p.keySet();
	}
	
	public void listar(){
		p.list(System.out);
	}
	
	public static void main(String[] args) {
		// leer el archivo de configuracion
		String archivoIni="conf.ini";
		IniFile iniFile = new IniFile();
		iniFile.setNameFile(archivoIni);
		System.out.println("Archivo Inicio: "+archivoIni);
	}
	
}

