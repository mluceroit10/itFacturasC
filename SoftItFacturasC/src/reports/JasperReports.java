package reports;
import java.util.Date;
import java.util.Vector;

import common.Utils;
import common.dto.ClienteDTO;
import common.dto.ComercioDTO;
import common.dto.FacturaClienteDTO;
import common.dto.FacturaDTO;
import common.dto.MovimientoCajaDTO;

import dori.jasper.engine.JRException;
import dori.jasper.engine.JasperCompileManager;
import dori.jasper.engine.JasperExportManager;
import dori.jasper.engine.JasperFillManager;
import dori.jasper.engine.JasperPrint;
import dori.jasper.engine.JasperReport;

public class JasperReports{
	
	public static String PATH_REPORT_XML = common.RootAndIp.getExtras()+"/reports/";
	public static String PATH_REPORT_PDF = common.RootAndIp.getArchivos();
	
	public static JasperPrint detallarCompraFactCliente(Vector productos,Vector cantidades, Vector precioUnit,Vector prTotIt,FacturaClienteDTO factura, String nroFact,Date fechaFact,
			ComercioDTO dist, ClienteDTO cte,double iTotal){ //3
		JasperPrint jasperPrint;
		try{
			String cpost="";
			if(dist.getLocalidad().getCodPostal()!=0) 
				cpost="("+dist.getLocalidad().getCodPostal()+")";
			String dir = dist.getDireccion()+" "+dist.getLocalidad().getNombre()+cpost;
			if(cte.getLocalidad().getCodPostal()!=0) 
				cpost="("+cte.getLocalidad().getCodPostal()+")";
			String dirCte = cte.getDireccion()+" "+cte.getLocalidad().getNombre()+cpost;
			//estado de cuenta pos  neg deuda -  a favor
			String tipoFact="";
			if(factura.getTipoFactura().compareTo("FacturaCliente-C")==0){
				tipoFact="C";
			}
			String remitoNro="";
			if(factura.getRemitoNro().compareTo("")!=0)
				remitoNro=Utils.nroFact(new Long(factura.getRemitoNro()));
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Institucion",dist.getNombre()},{"InstDomicilio",dir},{"InstTel",dist.getTelefono()},
					{"nroFact",nroFact},{"FechaFact",Utils.getStrUtilDate(fechaFact)},
					{"CteNombre",cte.getNombre()},{"CteDomicilio",dirCte},{"CteCuit",cte.getCuit()},
					{"Iva",factura.getIva()},{"CondVta",factura.getCondVenta()},
					{"RemitoNro",remitoNro},{"IngBrutos",factura.getIngrBrutos()},
					{"TotalFact",Utils.ordenarDosDecimales(iTotal)},
					{"tipoFact",tipoFact}
			};
			Object[][] values = new Object[productos.size()][4];;
			for (int i = 0; i < productos.size(); i++) {
				String prod= (String)productos.elementAt(i);
				String cant=(String) cantidades.elementAt(i);
				String prUnit=(String) precioUnit.elementAt(i);
				String prtotal=(String) prTotIt.elementAt(i);
				Object[] temp = {cant,prod,prUnit,prtotal};
				values[i] = temp;
			}
			String[] fieldXml = { "Cant","Producto","PUnit","PTotal"};
			jasperPrint=generarReporte("DetalleCompraFactCliente", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. DetalleCompraFactCliente.");
			return null;
		}
	}
	
	public static JasperPrint remitoCliente(Vector productos,Vector cantidades, Vector precioUnit,Vector prTotIt, String nroRemito,Date fechaFact,
			ComercioDTO dist, ClienteDTO cte,double iTotal){ //4
		JasperPrint jasperPrint;
		try{
			String fecha=Utils.getStrUtilDate(fechaFact);
			String cpost="";
			if(dist.getLocalidad().getCodPostal()!=0) 
				cpost="("+dist.getLocalidad().getCodPostal()+")";
			String dir = dist.getDireccion()+" "+dist.getLocalidad().getNombre()+cpost;
			if(cte.getLocalidad().getCodPostal()!=0) 
				cpost="("+cte.getLocalidad().getCodPostal()+")";
			String dirCte = cte.getDireccion()+" "+cte.getLocalidad().getNombre()+cpost;
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Institucion",dist.getNombre()},{"InstDomicilio",dir},{"InstTel",dist.getTelefono()},
					{"nroRemito",nroRemito},{"FechaFact",fecha},
					{"CteNombre",cte.getNombre()},{"CteDomicilio",dirCte},
					{"TotalFact",Utils.ordenarDosDecimales(iTotal)}
			};
			Object[][] values = new Object[productos.size()][4];;
			for (int i = 0; i < productos.size(); i++) {
				String prod= (String)productos.elementAt(i);
				String cant=(String) cantidades.elementAt(i);
				String prUnit=(String) precioUnit.elementAt(i);
				String prtotal=(String) prTotIt.elementAt(i);
				Object[] temp = {cant,prod,prUnit,prtotal};
				values[i] = temp;
			}
			String[] fieldXml = { "Cant","Producto","PUnit","PTotal"};
			jasperPrint=generarReporte("RemitoCliente", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. RemitoCliente.");
			return null;
		}
	}
	
	public static JasperPrint facturarCliente(Vector productos,Vector cantidades,Vector precioUnit,Vector prTotIt, Date fechaFact,
			ComercioDTO dist, ClienteDTO cte, String iva, String condVta,String remitoNro,String ingrBrutos,String tipoFact,double iTotal){//5
		JasperPrint jasperPrint;
		try{
			if(remitoNro.compareTo("")!=0)
				remitoNro=Utils.nroFact(new Long(remitoNro));
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { 
					{"FechaFact",Utils.getStrUtilDate(fechaFact)},
					{"CteNombre",cte.getNombre()},
					{"CteDomicilio",cte.getDireccion()},{"CteLoc",cte.getLocalidad().getNombre()},{"CteProv",cte.getLocalidad().getProvincia().getNombre()},
					{"CteCuit",cte.getCuit()},
					{"Iva",iva},{"CondVta",condVta},{"RemitoNro",remitoNro},{"IngBrutos",ingrBrutos},
					{"TotalFact",Utils.ordenarDosDecimales(iTotal)}
			};
			Object[][] values = new Object[20][4];;
			for (int i = 0; i < productos.size(); i++) {
				String prod= (String)productos.elementAt(i);
				String cant=(String) cantidades.elementAt(i);
				String prUnit=(String) precioUnit.elementAt(i);
				String prtotal=(String) prTotIt.elementAt(i);
				Object[] temp = {cant,prod,prUnit,prtotal};
				values[i] = temp;
			}
			if(productos.size()<20){
				for (int j = productos.size(); j < 20; j++) {
					Object[] temp = {"","","",""};
					values[j] = temp;
				}
			}
			String[] fieldXml = { "Cant","Producto","PUnit","PTotal"};
			jasperPrint=generarReporte("FacturaCliente", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. FacturaCliente.");
			return null;
		}
	}
	
	public static JasperPrint generarBalance(ComercioDTO comercio,Vector entradas, Vector salidas,int nroPl,Date fecha,double saldoAnt,double saldoNuevo) throws Exception{//7
		JasperPrint jasperPrint;
		try{
			
			double totalE=0;
			double totalS=0;
			
			int capacidad=salidas.size()+2+entradas.size()+3;
			
			Object[][] values = new Object[capacidad][2];
			Object[] tempTE = {"DETALLE DE ENTRADAS","TOTALES"};
			values[0] = tempTE;
			int j=1;
			for (int i = 0; i < entradas.size(); i++) {
				String importeE="";
				MovimientoCajaDTO mE=new MovimientoCajaDTO();
				mE.setDescripcion("");
				mE= (MovimientoCajaDTO)entradas.elementAt(i);
				totalE +=mE.getImporte();
				importeE=Utils.ordenarDosDecimales(mE.getImporte());
				String nroFact="";
				if(mE.isConFactura()) {
					if(((FacturaDTO)mE.getFactura()).getTipoFactura().compareTo("FacturaCliente-A")==0)
						nroFact=" FC tipo A Nº: "+Utils.nroFact(mE.getFactura().getNroFactura());
					if(((FacturaDTO)mE.getFactura()).getTipoFactura().compareTo("FacturaCliente-B")==0)
						nroFact=" FC tipo B Nº: "+Utils.nroFact(mE.getFactura().getNroFactura());
					if(mE.getFactura().getTipoFactura().compareTo("RemitoCliente")==0)
						nroFact=" RC Nº: "+Utils.nroFact(mE.getFactura().getNroFactura());
				}
				mE.setDescripcion(Utils.getStrUtilDate(mE.getFecha()) +" "+ mE.getDescripcion() + nroFact);
				Object[] tempE = {mE.getDescripcion(),importeE};
				values[j] = tempE;
				j++;
			}
			Object[] tempFE = {"TOTAL DE ENTRADAS",Utils.ordenarDosDecimales(Utils.redondear(totalE,2))};
			values[entradas.size()+1] = tempFE;
			Object[] temp = {"",""};
			values[entradas.size()+2] = temp;
			
			Object[] tempTS = {"DETALLE DE SALIDAS","TOTALES"};
			values[entradas.size()+3] = tempTS;
			j=entradas.size()+4;
			for (int i = 0; i < salidas.size(); i++) {
				
				String importeS="";
				
				MovimientoCajaDTO mS=new MovimientoCajaDTO();
				mS.setDescripcion("");
				
				mS= (MovimientoCajaDTO)salidas.elementAt(i);
				totalS +=mS.getImporte();
				importeS=Utils.ordenarDosDecimales(mS.getImporte());
				String nroFact="";
				if(mS.isConFactura()) {
					if(mS.getFactura().getTipoFactura().compareTo("FacturaProveedor")==0)
						nroFact=" FP Nº: "+Utils.nroFact(mS.getFactura().getNroFactura());
				}
				mS.setDescripcion(Utils.getStrUtilDate(mS.getFecha()) +" "+ mS.getDescripcion() + nroFact);
				
				Object[] tempS = {mS.getDescripcion(),importeS};
				values[j] = tempS;
				j++;
				
			}
			Object[] tempFS = {"TOTAL DE SALIDAS",Utils.ordenarDosDecimales(Utils.redondear(totalS,2))};
			values[j] = tempFS;
			
			
			String[] fieldXml = { "Descripcion","Monto"};
			double suma1=saldoAnt+totalE;
			String fe = Utils.getStrUtilDate(fecha);
			String dia=fe.substring(0,2);
			String mes=fe.substring(3,5);
			String anio=fe.substring(6,10);
			Object[][] param = { {"NroPlanilla",String.valueOf(nroPl)},
					{"Dia",dia},{"Mes",mes},{"Anio",anio},
					{"TotalI" ,Utils.ordenarDosDecimales(totalE)},{"TotalE",Utils.ordenarDosDecimales(totalS)},
					{"SaldoAnt",Utils.ordenarDosDecimales(saldoAnt)},{"Suma1",Utils.ordenarDosDecimales(suma1)},
					{"Suma2",Utils.ordenarDosDecimales(saldoNuevo)},{"Institucion",comercio.getNombre().toUpperCase()}};
			jasperPrint=generarReporte("PlanillaDeCaja", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. PlanillaDeCaja.");
			return null;
		}
	} 
	
	public static JasperPrint listarTodosClientes(ComercioDTO comercio, Vector clientes, String titulo) throws Exception{ //12
		JasperPrint jasperPrint;
		try{
			Date fecha= new Date();
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Titulo",titulo},{"Fecha",Utils.getStrUtilDate(fecha)},{"Institucion",comercio.getNombre().toUpperCase()}};
			Object[][] values = new Object[clientes.size()][5];;
			for (int i = 0; i < clientes.size(); i++) {
				ClienteDTO cte= (ClienteDTO)clientes.elementAt(i);
				String nombreCte=cte.getNombre();
				if(cte.getCodigo()!=null)
					nombreCte+= "\r\nNro Cte: "+cte.getCodigo();
				String ib="";
				if(cte.getIngBrutosCl().compareTo("")!=0)ib="\r\nI.B. "+cte.getIngBrutosCl();
				String loc_zona=cte.getLocalidad().getNombre();
				Object[] temp = {nombreCte,cte.getIvaCl()+"\r\n"+cte.getCuit()+ib,
						cte.getDireccion(),loc_zona,cte.getTelefono()};
				values[i] = temp;
			}
			if(clientes.size()==0){
				values = new Object[1][5];;
				Object[] temp ={"","NO se encontraron clientes registrados","","",""};
				values[0] = temp;
			}
			String[] fieldXml = { "Cliente","Cuit","Direccion","Localidad","Telefono"};
			jasperPrint=generarReporte("ListadoClientes", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. ListadoClientes.");
			return null;
		}
	}	
	
	public static JasperPrint detallarCuentaCliente(ComercioDTO dist,String titulo, Vector detalle,Vector fecha, Vector debe, Vector haber, Vector saldo,String leyendaPie){ //14 
		JasperPrint jasperPrint;
		try{
			Date hoy= new Date();
			String fechaHoy=Utils.getStrUtilDate(hoy);
			//estado de cuenta neg deuda - pos a favor
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Institucion",dist.getNombre()},{"Titulo",titulo},{"FechaHoy",fechaHoy},{"LeyendaPie",leyendaPie}};
			Object[][] values = new Object[detalle.size()][5];;
			for (int i = 0; i < detalle.size(); i++) {
				String det = (String)detalle.elementAt(i);
				String fe = (String)fecha.elementAt(i);
				String de = (String)debe.elementAt(i);
				String ha = (String)haber.elementAt(i);
				String sa = (String)saldo.elementAt(i);
				Object[] temp = {det,fe,de,ha,sa};
				values[i] = temp;
			}
			String[] fieldXml = {"DetalleIt","Fecha","Debe","Haber","Saldo"};
			jasperPrint=generarReporte("DetalleCuentaCliente", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. DetalleCuentaCliente.");
			return null;
		}
	}
	
	public static JasperPrint tarjetaComercio(String nombre, String cuit,String ingBrutos, String tel, String dir,String loc){//15 
		JasperPrint jasperPrint;
		try{
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Nombre",nombre},{"Cuit",cuit},{"IngBrutos",ingBrutos},{"Tel",tel},{"Dir",dir},{"Loc",loc}};
			Object[][] values = new Object[1][0];
			String[] fieldXml = {};
			jasperPrint=generarReporte("TarjetaComercio", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. TarjetaComercio.");
			return null;
		}
	}
	
	public static JasperPrint listarDeudaClientes(ComercioDTO dist,String titulo, Vector codigosCtes,Vector cliente, Vector fechasUF,Vector favor, Vector debe){//16 
		JasperPrint jasperPrint;
		try{
			Date hoy= new Date();
			String fechaHoy=Utils.getStrUtilDate(hoy);
			//estado de cuenta neg deuda - pos a favor
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Institucion",dist.getNombre()},{"Titulo",titulo},{"FechaHoy",fechaHoy}};
			Object[][] values = new Object[cliente.size()][5];;
			for (int i = 0; i < cliente.size(); i++) {
				String cod = (String)codigosCtes.elementAt(i);
				String cte = (String)cliente.elementAt(i);
				String fUF = (String)fechasUF.elementAt(i);
				String fav = (String)favor.elementAt(i);
				String deb = (String)debe.elementAt(i);
				Object[] temp = {cod,cte,fUF,fav,deb};
				values[i] = temp;
			}
			String[] fieldXml = {"Codigo","Cliente","FUltFact","Favor","Debe"};
			jasperPrint=generarReporte("ListadoDeudaclientes", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. ListadoDeudaclientes.");
			return null;
		}
	}
	
	public static JasperPrint listarProductosFacturados(ComercioDTO dist, String titulo,int elems, int[] codigos, String[] productos,String[] proveedores, int[] cantidades, double[] kilos) {//17
		JasperPrint jasperPrint;
		try{
			Date hoy= new Date();
			String fechaHoy=Utils.getStrUtilDate(hoy);
			//estado de cuenta neg deuda - pos a favor
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Institucion",dist.getNombre()},{"Titulo",titulo},{"Fecha",fechaHoy}};
			Object[][] values = new Object[elems][5];;
			for (int i = 0; i < elems; i++) {
				String kilo="";
				if(kilos[i]>0) kilo=Utils.ordenarTresDecimales(kilos[i]);
				Object[] temp = {String.valueOf(codigos[i]),productos[i],proveedores[i],String.valueOf(cantidades[i]),kilo};
				values[i] = temp;
			}
			String[] fieldXml = {"Codigo","Prod_Kilos","Proveedor","Cantidad","Kilos"};
			jasperPrint=generarReporte("ListadoProductosFacturados", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. ListadoProductosFacturados.");
			return null;
		}		
	}
	
	public static JasperPrint mostrarLibroFacturas(ComercioDTO dist, String titulo,String periodo, Object[][] datos, String total) {//18
		JasperPrint jasperPrint;
		try{
			//estado de cuenta neg deuda - pos a favor
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			String cpost="";
			if(dist.getLocalidad().getCodPostal()!=0) 
				cpost="("+dist.getLocalidad().getCodPostal()+")";
			String dir = dist.getDireccion()+" - "+dist.getLocalidad().getNombre()+cpost;
			Object[][] param = { {"Institucion",dist.getNombre()},{"InstDireccion",dir},{"InstIva","Resp. Inscripto - CUIT: "+dist.getCuit()},
					{"Titulo",titulo},{"FechaLI",periodo},{"TotalTotal",total}};
			Object[][] values = datos;
			String[] fieldXml = {"Fecha","Tipo","L","PV","Nro","Cliente","Categ","Cuit","Total"};
			jasperPrint=generarReporte("LibroFacturas", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. LibroIva.");
			return null;
		}
	}
	
	public static JasperPrint notaDebito(Vector productos,Vector cantidades, Vector precioUnit,Vector prTotIt, Date fechaFact,
			ComercioDTO dist, ClienteDTO cte, String iva, String observ, String ingrBrutos,String tipoFact,double iTotal){//19
		JasperPrint jasperPrint;
		try{
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { 
					{"FechaFact",Utils.getStrUtilDate(fechaFact)},{"CteNombre",cte.getNombre()},
					{"CteDomicilio",cte.getDireccion()},{"CteLoc",cte.getLocalidad().getNombre()},{"CteProv",cte.getLocalidad().getProvincia().getNombre()},
					{"CteCuit",cte.getCuit()},{"Iva",iva},{"Observaciones",observ},{"IngBrutos",ingrBrutos},
					{"TotalFact",Utils.ordenarDosDecimales(iTotal)}
			};
			Object[][] values = new Object[20][4];;
			for (int i = 0; i < productos.size(); i++) {
				String prod= (String)productos.elementAt(i);
				String cant=(String) cantidades.elementAt(i);
				String prUnit=(String) precioUnit.elementAt(i);
				String prtotal=(String) prTotIt.elementAt(i);
				Object[] temp = {cant,prod,prUnit,prtotal};
				values[i] = temp;
			}
			if(productos.size()<20){
				for (int j = productos.size(); j < 20; j++) {
					Object[] temp = {"","","",""};
					values[j] = temp;
				}
			}
			String[] fieldXml = { "Cant","Producto","PUnit","PTotal"};
			jasperPrint=generarReporte("NotaDebito", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. FacturaCliente.");
			return null;
		}
	}
	
	private static JasperPrint generarReporte(String report, Object[][] param, String[] fieldXml, Object[][] values){
		JasperReport jasperReport;
		JasperPrint jasperPrint; 
		try {
			java.util.HashMap parameters = new java.util.HashMap();
			for (int i = 0; i < param.length; i++) {
				parameters.put(param[i][0], param[i][1]);
			}
			java.util.Hashtable ht = new java.util.Hashtable();
			for (int i = 0; i < fieldXml.length; i++) {
				ht.put(fieldXml[i], new Integer(i));
			}
			DataSourceJasper data = new DataSourceJasper(values, ht);
			String fileXML = PATH_REPORT_XML + report + ".xml";
			String fileJRPRINT = PATH_REPORT_PDF + report + ".pdf";
			// 1-Compilamos el archivo XML y lo cargamos en memoria			
			jasperReport = JasperCompileManager.compileReport(fileXML);
			// 2-Llenamos el reporte con la información y parámetros necesarios 
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,data);
			JasperExportManager.exportReportToPdfFile(jasperPrint,fileJRPRINT);
			return jasperPrint;
		} catch (JRException ex) {
			Utils.manejoErrores(ex,null,"Error en JasperReports. generarReporte.");
			return null;
		}
	}
	
	public static void main(String[] args){
		;
	}
	
	public static JasperPrint DetalleNotaDebitoCte(Vector productos, Vector cantProd, Vector prUnit, Vector prTotal, String nroComp, java.sql.Date fechaFact, 
			ComercioDTO dist, ClienteDTO cte, String iva,String observ,  String ingrBrutos, double total) { //200
		JasperPrint jasperPrint;
		try{
			String cpost="";
			if(dist.getLocalidad().getCodPostal()!=0) 
				cpost="("+dist.getLocalidad().getCodPostal()+")";
			String dir = dist.getDireccion()+" "+dist.getLocalidad().getNombre()+cpost;
			if(cte.getLocalidad().getCodPostal()!=0) 
				cpost="("+cte.getLocalidad().getCodPostal()+")";
			String dirCte = cte.getDireccion()+" "+cte.getLocalidad().getNombre()+cpost;
			//estado de cuenta pos  neg deuda -  a favor
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Institucion",dist.getNombre()},{"InstDomicilio",dir},{"InstTel",dist.getTelefono()},
					{"nroFact",nroComp},{"FechaFact",Utils.getStrUtilDate(fechaFact)},
					{"CteNombre",cte.getNombre()},{"CteDomicilio",dirCte},{"CteCuit",cte.getCuit()},
					{"Iva",iva},{"Observaciones",observ},{"IngBrutos",ingrBrutos},
					{"TotalFact",Utils.ordenarDosDecimales(total)}
			};
			Object[][] values = new Object[productos.size()][4];;
			for (int i = 0; i < productos.size(); i++) {
				String prod= (String)productos.elementAt(i);
				String cant=(String) cantProd.elementAt(i);
				String prUnitP=(String) prUnit.elementAt(i);
				String prTotalP=(String) prTotal.elementAt(i);
				Object[] temp = {cant,prod,prUnitP,prTotalP};
				values[i] = temp;
			}
			String[] fieldXml = { "Cant","Producto","PUnit","PTotal"};
			jasperPrint=generarReporte("DetalleNotaDebitoCliente", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. DetalleCompraFactCliente.");
			return null;
		}
	}
	
	public static JasperPrint reporteDeCompras(ComercioDTO dist, String titulo, Object[][] datos, String fecha){ //23
		JasperPrint jasperPrint;
		try{
			Date hoy=new Date();
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Titulo",titulo},{"Fecha",Utils.getStrUtilDate(hoy)},{"Institucion",dist.getNombre().toUpperCase()},{"FechaUltimaFacturacion",fecha}};
			Object[][] values = datos;
			String[] fieldXml = {"Codigo","Producto","Proveedor","Fecha"};
			jasperPrint=generarReporte("ReporteDeCompras", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. reporteDeCompras.");
			return null;
		}
	}
	
	public static JasperPrint balanceDetallePagos(ComercioDTO comercio,Vector entradas,int nroPlanilla, java.sql.Date fecha) {
		JasperPrint jasperPrint;
		try{
			
			double totalE=0;
			
			Object[][] values = new Object[entradas.size()][2];
			for (int i = 0; i < entradas.size(); i++) {
				String importeE="";
				MovimientoCajaDTO mE=new MovimientoCajaDTO();
				mE.setDescripcion("");
				mE= (MovimientoCajaDTO)entradas.elementAt(i);
				totalE +=mE.getImporte();
				importeE=Utils.ordenarDosDecimales(mE.getImporte());
			/*	String nroFact="";
				if(mE.isConFactura()) {
					if(((FacturaDTO)mE.getFactura()).getTipoFactura().compareTo("FacturaCliente-C")==0)
						nroFact=" FC tipo A Nº: "+Utils.nroFact(mE.getFactura().getNroFactura());
					if(mE.getFactura().getTipoFactura().compareTo("RemitoCliente")==0)
						nroFact=" RC Nº: "+Utils.nroFact(mE.getFactura().getNroFactura());
					}
				String desc = Utils.getStrUtilDate(mE.getFecha()) +" "+ mE.getDescripcion() + nroFact;*/
				Object[] tempE = {Utils.getStrUtilDate(mE.getFecha()),
						Utils.nroFact(mE.getFactura().getNroFactura()),
						mE.getFactura().getCliente().getNombre(),
						mE.getDescripcion(),
						importeE};
				values[i] = tempE;
			}
			String[] fieldXml = { "Fecha","NroFactura","Cliente","Descripcion","Monto"};
			String fe = Utils.getStrUtilDate(fecha);
			String dia=fe.substring(0,2);
			String mes=fe.substring(3,5);
			String anio=fe.substring(6,10);
			Object[][] param = { {"NroPlanilla",String.valueOf(nroPlanilla)},
					{"Dia",dia},{"Mes",mes},{"Anio",anio},
					{"TotalIF" ,Utils.ordenarDosDecimales(Utils.redondear(totalE,2))},
					{"Institucion",comercio.getNombre().toUpperCase()}};
			jasperPrint=generarReporte("FacturasCierreCaja", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. PlanillaDeCaja.");
			return null;
		}
	}
	//Para pasar nDebito y las facturas por el mismo tipo de comprobante para la radio
	
	public static JasperPrint facturar_nDebito_Cliente(String nroComprob,Vector productos,Vector cantidades,Vector precioUnit,Vector prTotIt, Date fechaFact,
			ComercioDTO dist, ClienteDTO cte, String iva, String condVta_observ,String ingrBrutos,String tipoFact,double iTotal){  //tipoFact para Fact es "C" sino ""
		JasperPrint jasperPrint;
		try{
			String cteDir = cte.getDireccion();
			if(cte.getLocalidad()!=null)
				cteDir += " "+cte.getLocalidad().getNombre();
			if(cte.getLocalidad().getProvincia()!=null)
				cteDir += " "+cte.getLocalidad().getProvincia().getNombre();
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { 
					{"FechaFact",Utils.getStrUtilDate(fechaFact)},{"NroFact",nroComprob},{"TipoFact",tipoFact},
					{"CteNombre",cte.getNombre()},{"CteDomicilio",cteDir},{"CteCuit",cte.getCuit()},{"Iva",iva},{"IngBrutos",ingrBrutos},
					{"CondVta",condVta_observ},{"TotalFact",Utils.ordenarDosDecimales(iTotal)}
			};
			Object[][] values = new Object[productos.size()][4];;
			for (int i = 0; i < productos.size(); i++) {
				String prod= (String)productos.elementAt(i);
				String cant=(String) cantidades.elementAt(i);
				String prUnit=(String) precioUnit.elementAt(i);
				String prtotal=(String) prTotIt.elementAt(i);
				Object[] temp = {cant,prod,prUnit,prtotal};
				values[i] = temp;
			}
			/*if(productos.size()<10){
				for (int j = productos.size(); j < 10; j++) {
					Object[] temp = {"","","",""};
					values[j] = temp;
				}
			}*/
			String[] fieldXml = { "Cant","Producto","PUnit","PTotal"};
			jasperPrint=generarReporte("Factura_NDebito", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. FacturaCliente.");
			return null;
		}
	}

	public static JasperPrint reciboCliente(Long nroRecibo,	MovimientoCajaDTO mimovDTO, ComercioDTO comercio) {
		JasperPrint jasperPrint;
		try{
			ClienteDTO cte = mimovDTO.getFactura().getCliente();
			FacturaClienteDTO fact= (FacturaClienteDTO)mimovDTO.getFactura(); 
			String cpost="";
			
			if(comercio.getLocalidad().getCodPostal()!=0) 
				cpost="("+comercio.getLocalidad().getCodPostal()+")";
			String dir = comercio.getDireccion()+" "+comercio.getLocalidad().getNombre()+cpost;
			if(cte.getLocalidad().getCodPostal()!=0) 
				cpost="("+cte.getLocalidad().getCodPostal()+")";
			String dirCte = cte.getDireccion()+" "+cte.getLocalidad().getNombre()+cpost;
			double importeAbonadoConAnterioridad=fact.getImporteAbonado();
			double saldo=Utils.redondear(fact.getImporteTotal()-(fact.getImporteAbonado()+mimovDTO.getImporte()), 2);
			String saldoDesc="";
			if(saldo<0) //si saldo es negativo es a favor del cliente
				saldoDesc="Saldo a favor: $"+Utils.ordenarDosDecimales(saldo)+"\n";
			else if(saldo==0)
				saldoDesc="FACTURA COMPLETAMENTE ABONADA\n";
			else	
				saldoDesc="Saldo a pagar: $"+Utils.ordenarDosDecimales(saldo)+"\n";
			String descripcion= mimovDTO.getDescripcion()+"\nFACTURA Nº: "+Utils.nroFact(fact.getNroFactura())+"\n" +
					"Importe total: $"+Utils.ordenarDosDecimales(fact.getImporteTotal())+"\n"+
				    "Importe abonado con anterioridad: $"+Utils.ordenarDosDecimales(importeAbonadoConAnterioridad)+"\n"+saldoDesc;  //lo que se tenia pagado mas lo q se paga en el comprobante
			
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Institucion",comercio.getNombre()},{"InstDomicilio",dir},{"InstTel",comercio.getTelefono()},
					{"nroRecibo",Utils.nroFact(nroRecibo)},{"FechaRecibo",Utils.getStrUtilDate(mimovDTO.getFecha())},
					{"CteNombre",cte.getNombre()},{"CteDomicilio",dirCte},{"CteCuit",cte.getCuit()},
					{"TotalRecibo",Utils.ordenarDosDecimales(mimovDTO.getImporte())}
			};
			Object[][] values = new Object[1][2];;
				Object[] temp = {descripcion,Utils.ordenarDosDecimales(mimovDTO.getImporte())};
				values[0] = temp;
			String[] fieldXml = { "Descripcion","PTotal"};
			jasperPrint=generarReporte("ReciboCliente", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,null,"Error en JasperReports. DetalleCompraFactCliente.");
			return null;
		}
	}
}