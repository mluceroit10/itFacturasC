package common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import common.dto.ClienteDTO;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel{
	
	public static void crearXLSX() throws IOException{	//funciono creado el archivo desde cero
		HSSFWorkbook libro= new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet("Mi Hoja");
		for(int i=0;i<10;i++){
			HSSFRow row = hoja.createRow((short)i);
			for(int j=0;j<3;j++){
				HSSFCell celda = row.createCell((short)j);
				HSSFRichTextString miContenido= new HSSFRichTextString("¡¡¡Hola Mundo!!!"+j);
				celda.setCellValue(miContenido);
			}
			
		}
		String nombreArch = "testExcel.xls";
		FileOutputStream fos = new FileOutputStream(nombreArch);
		libro.write(fos);
		fos.close();
	}
	
	public static void leerXLSX()throws IOException{
		HSSFWorkbook archivo = new HSSFWorkbook(new FileInputStream("testExcel.xls"));
		int numHoja=0;
		HSSFSheet hoja = archivo.getSheetAt(numHoja);
		for(int i=0;i<10;i++){
			HSSFRow fila = hoja.getRow(i);
			for(int columna=0;columna<3;columna++){
				HSSFCell dato = fila.getCell(columna);
				//if(dato.getCellType() == HSSFCell.CELL_TYPE_STRING){
				System.out.print(dato.getStringCellValue()+" ");
				//	}
			}
			System.out.println();
		}
	}
	
	
	public static void crearXLSXClientes(Vector clientes,String todos) throws IOException{	//funciono creado el archivo desde cero
		HSSFWorkbook libro= new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet("Clientes");
		
		HSSFRow cabeceraTabla = hoja.createRow((short)0);
		HSSFCell celda1 = cabeceraTabla.createCell((short)0);
		HSSFRichTextString miContenido1= new HSSFRichTextString("CODIGO:");
		celda1.setCellValue(miContenido1);
		
		HSSFCell celda2 = cabeceraTabla.createCell((short)1);
		HSSFRichTextString miContenido2= new HSSFRichTextString("NOMBRE:");
		celda2.setCellValue(miContenido2);
		
		HSSFCell celda3 = cabeceraTabla.createCell((short)2);
		HSSFRichTextString miContenido3= new HSSFRichTextString("DOMICILIO:");
		celda3.setCellValue(miContenido3);
		
		HSSFCell celda4 = cabeceraTabla.createCell((short)3);
		HSSFRichTextString miContenido4= new HSSFRichTextString("LOCALIDAD:");
		celda4.setCellValue(miContenido4);
		
		HSSFCell celda5 = cabeceraTabla.createCell((short)4);
		HSSFRichTextString miContenido5= new HSSFRichTextString("CUIT:");
		celda5.setCellValue(miContenido5);
		
		for(int i=1;i<=clientes.size();i++){
			HSSFRow row = hoja.createRow((short)i);
			ClienteDTO cte = (ClienteDTO) clientes.elementAt(i-1);
			String codigo="";
			if(cte.getCodigo()!=null) codigo=String.valueOf(cte.getCodigo());
			HSSFCell celdai1 = row.createCell((short)0);
			HSSFRichTextString miContenidoi1= new HSSFRichTextString(codigo);
			celdai1.setCellValue(miContenidoi1);
			
			HSSFCell celdai2 = row.createCell((short)1);
			HSSFRichTextString miContenidoi2= new HSSFRichTextString(cte.getNombre());
			celdai2.setCellValue(miContenidoi2);
			
			HSSFCell celdai3 = row.createCell((short)2);
			HSSFRichTextString miContenidoi3= new HSSFRichTextString(cte.getDireccion());
			celdai3.setCellValue(miContenidoi3);
			
			HSSFCell celdai4 = row.createCell((short)3);
			HSSFRichTextString miContenidoi4= new HSSFRichTextString(cte.getLocalidad().getNombre());
			celdai4.setCellValue(miContenidoi4);
			
			HSSFCell celdai5 = row.createCell((short)4);
			HSSFRichTextString miContenidoi5= new HSSFRichTextString(cte.getCuit());
			celdai5.setCellValue(miContenidoi5);
		}
		String nombreArch = RootAndIp.getArchivos()+"excels/LISTADO_"+todos+".xls";
		FileOutputStream fos = new FileOutputStream(nombreArch);
		libro.write(fos);
		fos.close();
	}
	
	public static double incrementarIVA(double precioD){
		double precioCIva=Utils.redondear(precioD*1.21,2);
		return precioCIva;
	}
}


/*HSSFRow row = hoja.createRow((short)0);
 HSSFCell celda1 = row.createCell((short)0);
 HSSFRichTextString miContenido1= new HSSFRichTextString("ART_ID:");
 celda1.setCellValue(miContenido1);
 
 HSSFCell celda2 = row.createCell((short)1);
 HSSFRichTextString miContenido2= new HSSFRichTextString("ART_DESC:");
 celda2.setCellValue(miContenido2);
 
 HSSFCell celda3 = row.createCell((short)2);
 HSSFRichTextString miContenido3= new HSSFRichTextString("ART_PRECIO_DEFAULT:");
 celda3.setCellValue(miContenido3);
 
 HSSFCell celda4 = row.createCell((short)3);
 HSSFRichTextString miContenido4= new HSSFRichTextString("ART_PORC_IVA:");
 celda4.setCellValue(miContenido4);
 
 HSSFCell celda5 = row.createCell((short)4);
 HSSFRichTextString miContenido5= new HSSFRichTextString("ART_STOCK:");
 celda5.setCellValue(miContenido5);*/