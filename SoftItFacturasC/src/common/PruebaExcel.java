package common;

import java.io.IOException;


public class PruebaExcel {
	public static void main(String[] args) {
		//File archivo=new File("c:/archivos_Proyecto_Ciro/excels/Libro1.xls");
		//String resp = Excel.leer("c:/archivos_Proyecto_Ciro/excels/Libro2.xls", "Hoja 1", "A4");
		//System.out.println("es "+resp);
		//Excel.escribir("c:/archivos_Proyecto_Ciro/excels/Libro1.xls", "Hoja 1", 4,2,1.5);
		try {
			//Excel.crearXLSX();
			Excel.leerXLSX();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
