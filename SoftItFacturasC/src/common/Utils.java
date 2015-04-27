package common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.util.Date;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import server.ControlComercio;

import common.dto.ComercioDTO;

public class Utils{
	
	public Utils(){ 
	}
	
	public static final int NROPLANILLAANTERIOR = 0;
	public static final long NROFACTURACLIENTE = 100000001;
	public static final long NROREMITOCLIENTE = 100000001;
	public static final long NROFACTURAPROVEEDOR = 0;
	public static final double SALDOANTERIOR = 0;
	public static Color colorFondo = new Color(240,248,255);
	public static Color colorPanel = new Color(135,206,250); 
	/*public static Color colorFondo = new Color(230,230,250);
	public static Color colorPanel = new Color(205,150,205); */
	public static Color colorNegro = new Color(0,0,0);
	public static Color colorTextoDisabled = new Color(0,0,205); 
	public static String tipoLetra = "Century Gothic";
	public static Border b= javax.swing.BorderFactory.createLineBorder(new Color(104,34,139),2);
	/*private static Toolkit tk = Toolkit.getDefaultToolkit();   
	 private static Dimension screenSize = tk.getScreenSize();
	 public static int anchoMaxVent=screenSize.width;
	 public static int largoMaxVent=screenSize.height-100;*/
	
	
	
	public static TitledBorder crearTituloYBorde(String titulo){
		return javax.swing.BorderFactory.createTitledBorder(Utils.b,titulo, 
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black);
	}
	
	public static TitledBorder crearTituloYBorde16(String titulo){
		return javax.swing.BorderFactory.createTitledBorder(Utils.b,titulo, 
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,new java.awt.Font("Dialog", java.awt.Font.BOLD, 16), java.awt.Color.black);
	}
	
	public static TitledBorder crearTituloYBordeDestacado(String titulo){
		return javax.swing.BorderFactory.createTitledBorder(Utils.b,titulo, 
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,new Font(Utils.tipoLetra,Font.BOLD,18),java.awt.Color.black);
	}
	
	public static Border crearRebordeBoton(){
		return javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED);
	}
	
	public static void alineacion(int tipoAlineacion,JTextPane texto){
		StyledDocument st=texto.getStyledDocument();
		SimpleAttributeSet bSet = new SimpleAttributeSet(); 
		StyleConstants.setAlignment(bSet, tipoAlineacion); 
		st.setParagraphAttributes(0,texto.getText().length(), bSet, false); 
		texto.updateUI();
	}
	/**
	 * Toma un String con formato  dd/mm/yyyy y lo convierte al formato yyyy-dd-mm
	 */
	public static String strToDateFormToDB(String fecha){  
		String dateValue = fecha;
		int index = dateValue.indexOf("/");
		String day = dateValue.substring(0,index);
		int index2 = dateValue.indexOf("/",index+1);
		String month = dateValue.substring(index+1,index2);
		String year = dateValue.substring(index2+1,index2+5);
		dateValue=year+"-"+month+"-"+day;
		return dateValue;
	}
	
	/**
	 * Retorna un java.sql.Date de un string con formato dd/mm/aaaa
	 * @param fecha
	 * @return
	 */
	public static java.sql.Date strToSqlDateDB(String fecha){
		return java.sql.Date.valueOf( strToDateFormToDB(fecha) );
	}
	
	/**
	 *  Retorna un java.util.Date de un string con formato dd/mm/aaaa y la hora hh:mm
	 */
	public static java.util.Date strToUtilDate(String fecha, String hora){
		String f=strToDateFormToDB(fecha)+" "+hora+":00";
		java.sql.Timestamp dh= java.sql.Timestamp.valueOf( f);
		return (java.util.Date)dh;
	}
	
	private static String fechaNula(){
		return "---";
	}
	
	public static java.sql.Date crearFecha(int dia, int mes, int anio){
		java.sql.Date diaCreado= new java.sql.Date(anio-1900,mes-1,dia);
		return diaCreado;
	}
	
	/**
	 *  Retorna el String dd/mm/aaaa para un java.util.Date
	 */
	public static String getStrUtilDate(java.util.Date date){
		if(date==null)
			return fechaNula();
		String strDate="";
		
		if(getDia(date)<10)
			strDate+="0"+getDia(date);
		else
			strDate+=getDia(date);
		if(getMes(date)<10)
			strDate+="/"+"0"+getMes(date);
		else
			strDate+="/"+getMes(date);
		strDate+="/"+getAnio(date);
		return strDate;
	}
	
	/**
	 *  Retorna el String hh:mm para un java.util.Date
	 */
	public static String getHoraUtilDate(java.util.Date date){
		if(date==null)
			return "";
		String strDate="";
		if(getHora(date)<10)
			strDate+="0"+getHora(date);
		else
			strDate+=getHora(date);
		if(getMinutos(date)<10)
			strDate+=":"+"0"+(getMinutos(date));
		else
			strDate+=":"+getMinutos(date);
		return strDate;
	}
	
	public static int getDia(Date fecha){
		return fecha.getDate();
	}
	
	public static int getMes(Date fecha){
		return fecha.getMonth()+1;
	}
	
	public static int getAnio(Date fecha){
		return fecha.getYear()+1900;
	}
	
	public static int getHora(Date fecha){
		return fecha.getHours();
	}
	
	public static int getMinutos(Date fecha){
		return fecha.getMinutes();
	}
	
	public static int getSegundos(Date fecha){
		return fecha.getSeconds();
	}
	
	
	public static String mayuscula(String string){
		String aux, auxDos;
		StringTokenizer tokenizer = new StringTokenizer(string, " ");
		String stringCapitalizado = "";
		while (tokenizer.hasMoreElements()) {
			aux = new String(tokenizer.nextToken());
			auxDos = new String (aux.substring(0,1).toUpperCase());
			stringCapitalizado += auxDos+aux.substring(1,aux.length()).toLowerCase()+" ";
		}
		return stringCapitalizado.trim();
	}
	
	/**
	 *  Este metodo nos dice si "entrada" es una subcadena de "baseDeDatos",
	 *  por ejemplo:
	 *      entrada="laura"  baseDeDatos="laura Contreras" =>    TRUE
	 *      entrada="laura beatriz"  baseDeDatos="contreras laura beatriz" =>    TRUE
	 *  Este metodo es keyInsensitive (no importan mayusculas o minusculas)
	 */
	public static boolean comienza(String baseDeDatos, String entrada) {
		/*  ENTRADA DE DATOS    */
		String[] cadena;
		StringTokenizer tokenizerBD = new StringTokenizer(baseDeDatos, " ");
		int cantTokensBD = tokenizerBD.countTokens();
		StringTokenizer tokenizerEntrada = new StringTokenizer(entrada, " ");
		int cantTokensEntrada = tokenizerEntrada.countTokens();
		if (cantTokensEntrada == 0) {
			return true;
		} else if (cantTokensEntrada == 1) {
			boolean encontrado = false;
			for (int j=0; j<cantTokensBD && !encontrado; j++) {
				if (tokenizerBD.nextToken().toUpperCase().startsWith(entrada.trim().toUpperCase()) ) {
					encontrado = true;
				}
			}
			return encontrado;
		} else {
			boolean encontrado = false;
			cadena = new String[cantTokensBD];
			for (int i=0; i<cantTokensBD; i++) {
				cadena[i] = new String(tokenizerBD.nextToken());
			}
			for (int j=0; j<(cadena.length-1) && !encontrado; j++) {
				String aux = "";
				for (int k=j; k<cadena.length; k++) {
					aux += cadena[k]+" ";
				}  
				encontrado = aux.toUpperCase().startsWith(entrada.trim().toUpperCase());
			}
			return encontrado;
		}
	}
	
	/**
	 * Verifica si el formato de un String es de tipo Double
	 */
	public static boolean esDouble(String string) {
		int cantp=0;
		int pos=0;
		while (pos<string.length()) {
			if(string.charAt(pos)=='.')
				cantp++;
			pos++;
		}
		if(cantp<=1)
			return true;
		else
			return false;
	}
	
	
	private  static String CLAVE = "";
	
	public static void setClave(String string){
		CLAVE = string;
	}
	
	public static String encriptar(String source){
		try{
			Key key = getClave();
			Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			desCipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] cleartext = source.getBytes();
			byte[] ciphertext = desCipher.doFinal(cleartext);
			return getString(ciphertext);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static String generateKey(){
		try{
			KeyGenerator keygen = KeyGenerator.getInstance("DES");
			SecretKey desKey = keygen.generateKey();
			byte[] bytes = desKey.getEncoded();
			return getString( bytes );
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static String decriptar(String source){
		try{
			Key key = getClave();
			Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			byte[] ciphertext = getBytes(source);
			desCipher.init(Cipher.DECRYPT_MODE, key);
			byte[] cleartext = desCipher.doFinal(ciphertext);
			return new String(cleartext);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private static Key getClave(){
		try{
			byte[] bytes = getBytes(CLAVE);
			DESKeySpec pass = new DESKeySpec(bytes);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey s = skf.generateSecret(pass);
			return s;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns true if the specified text is encrypted, false otherwise
	 */
	public static boolean isEncriptado(String text){
		if( text.indexOf( '-' ) == -1 ){
			return false;
		}
		StringTokenizer st = new StringTokenizer( text, "-", false );
		while( st.hasMoreTokens() ){
			String token = st.nextToken();
			if( token.length() > 3 ){
				System.out.println( "text is not encrypted: length of token greater than 3: " + token );
				return false;
			}
			for( int i=0; i<token.length(); i++ ){
				if( !Character.isDigit( token.charAt( i ) ) ){
					System.out.println( "text is not encrypted: token is not a digit" );
					return false;
				}
			}
		}
		return true;
	}
	private static String getString(byte[] bytes){
		StringBuffer sb = new StringBuffer();
		for( int i=0; i<bytes.length; i++ ){
			byte b = bytes[ i ];
			sb.append( ( int )( 0x00FF & b ) );
			if(i+1 <bytes.length){
				sb.append( "-" );
			}
		}
		return sb.toString();
	}
	private static byte[] getBytes(String str){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		StringTokenizer st = new StringTokenizer( str, "-", false );
		while(st.hasMoreTokens()){
			int i = Integer.parseInt( st.nextToken() );
			bos.write( ( byte )i );
		}
		return bos.toByteArray();
	}
	
	public static String ordenarTexto(String texto,int cc){
		StringTokenizer st = new StringTokenizer(texto, "\n");
		String textoFinal="";
		while(st.hasMoreTokens()){
			String c1=st.nextToken();
			String c2="";
			while(c1.length()>cc){
				String aux=c1.substring(0,cc)+"\n";
				int pos= aux.lastIndexOf(" ");
				if (pos==-1){
					c2+=c1.substring(0,cc)+"\n";
					c1=c1.substring(cc,c1.length());
				}else{
					c2+=c1.substring(0,pos)+"\n";
					c1=c1.substring(pos+1,c1.length());
				}
			}
			c2+=c1;
			textoFinal+=c2+"\n";
		}    
		return textoFinal;    
	}
	
	public static String ordenarDosDecimales(double nro){
		String numero;
		nro=redondear(nro,2);
		numero = String.valueOf(nro);
		char valor1=numero.charAt(numero.length()-2);
		if(valor1=='.') numero +="0";
		return numero;    
	}
	
	public static String ordenarTresDecimales(double nro){
		String numero;
		nro=redondear(nro,3);
		numero = String.valueOf(nro);
		char valor1=numero.charAt(numero.length()-2);
		char valor2=numero.charAt(numero.length()-3);
		if(valor1=='.') numero +="00";
		if(valor2=='.') numero +="0";
		return numero;    
	}
	
	public static void main(String[] args){
		System.out.println("numero ");
	}
	
	public static boolean verificarCuit(String cuit) {
		boolean cuitErroneo=false;
		if((cuit.length()!=14 ) && (cuit.length()!=13 )){
			cuitErroneo=true;
		}else{
			if(cuit.charAt(2)!= '-'){
				cuitErroneo=true;
			}else{ 
				if(cuit.charAt(cuit.length()-2)!='-'){
					cuitErroneo=true;
				}	
			}	
		}
		return cuitErroneo;
	}
	
	public static String nroFact(Long nro){
		String nroF="";
		String n=String.valueOf(nro);
		while(n.length()<12){
			n="0"+n;
		}
		nroF=n.substring(0,4)+"-"+n.substring(4,12);
		return nroF;
	}
	
	/*public static String Institucion() throws Exception{
		String nombre="";
		ControlComercio ctrlDist= new ControlComercio();
		ComercioDTO dist=ctrlDist.obtenerComercio();
		if (dist!=null) nombre=(dist.getNombre()).toUpperCase();
		else Utils.advertenciaUsr(null,"Debe completar los datos del Comercio.");
		return "nombre del comercio! cambiar";
	}*/
	
	public static double redondear( double numero, int decimales ) {
		return Math.round(numero*Math.pow(10,decimales))/Math.pow(10,decimales);
	}
	
	public static void manejoErrores(Exception ex, Class c,String metodo){
		if(c!=null)
			System.out.println(c.getCanonicalName()+" "+metodo);
		else
			System.out.println(metodo);
		System.out.println(ex.toString());
		ex.printStackTrace();
	}
	
	public static void advertenciaUsr(Component componentePadre,String texto){
		JOptionPane.showMessageDialog(componentePadre,texto,"ATENCION!!!",JOptionPane.WARNING_MESSAGE);
	}
	
	public static int aceptarCancelarAccion(Component componentePadre,String texto){
		return JOptionPane.showConfirmDialog(componentePadre,texto,"ATENCION!!!",JOptionPane.WARNING_MESSAGE);
	}
	
	public static DefaultTableCellRenderer alinearDerecha(){
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		return rightRenderer;
	}
	
	public static DefaultTableCellRenderer alinearCentro(){
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		return centerRenderer;
	}
	
	public static void ocultarColumnaId(JTable tbl)    {
		tbl.getColumnModel().getColumn(0).setMaxWidth(0);
		tbl.getColumnModel().getColumn(0).setMinWidth(0);
		tbl.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		tbl.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
	}
	
	public static void show(JDialog vent){ 
		vent.show();
	}
	
	public static java.sql.Date crearFecha(java.util.Date dia){
		java.sql.Date diaHr=new java.sql.Date(dia.getYear(),dia.getMonth(),dia.getDate());
		return diaHr;
	}
	
	public static java.sql.Date crearFecha2(java.sql.Date dia){
		java.sql.Date diaHr=new java.sql.Date(dia.getYear(),dia.getMonth(),dia.getDate());
		return diaHr;
	}
	
	public static String nroCheque(Long numero) {
		String n=String.valueOf(numero);
		while(n.length()<8){
			n="0"+n;
		}//105.82
		return n;
	}
	
	public static double ivaProducto(double precio,double iva){
		double ivaR=redondear(precio*(iva/100),2);
		return ivaR;
	}
	
	public static double incrementarIVA(double precio,double iva){
		double importeCIva=(precio*(iva/100))+precio;
		double importeProd=Utils.redondear(importeCIva,2);
		return importeProd;
	}
	
	public static double decrementarIVA(double precio,double iva){
		double precioSinIva= Utils.redondear(precio/(1+(iva/100)),2);
		return precioSinIva;
	}
	
	
}


