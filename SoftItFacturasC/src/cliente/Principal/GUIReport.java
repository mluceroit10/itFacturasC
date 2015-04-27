package cliente.Principal;

import java.sql.Date;
import java.util.Vector;

import javax.swing.JDialog;

import cliente.GestionarMovimientoCaja.GUIAltaModMovimientoCaja;

import reports.JasperReports;

import common.Utils;
import common.dto.ClienteDTO;
import common.dto.ComercioDTO;
import common.dto.FacturaClienteDTO;
import common.dto.MovimientoCajaDTO;

import constantes.NRO_REPORTE;
import dori.jasper.engine.JRException;
import dori.jasper.engine.JasperPrint;
import dori.jasper.view.JRViewer;

public class GUIReport extends JDialog{
	private static final long serialVersionUID = 1L;
	
	public GUIReport(JDialog parent,int codRep,ComercioDTO comercio,Vector datos,String titulo) throws Exception{
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		String mensaje="";
		if(codRep==NRO_REPORTE.listarTodosClientes){
			mensaje="No existen clientes para efectuar la impresión";
			report = JasperReports.listarTodosClientes(comercio,datos, titulo);
		}
		if(datos.size()>0){
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error Reportes 12 13");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
		}else{
			Utils.advertenciaUsr(parent,mensaje);
		}
	}
	
	public GUIReport(JDialog parent,int codRep,String nombre,String cuit,String ingBrutos,String tel,String direccion,String nLoc) throws Exception{
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		if(codRep==NRO_REPORTE.tarjetaComercio){
			report = JasperReports.tarjetaComercio(nombre, cuit,ingBrutos, tel, direccion, nLoc);
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error Reportes 15");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
		}	
	}
	
	public GUIReport(JDialog parent,int codRep,Vector productos,Vector cantProd, Vector prUnit,Vector prTotal,String nroComprob, Date fechaFact,
			ComercioDTO dist, ClienteDTO cte, String iva, String condVta,String observ,String remitoNro,String ingrBrutos,String tipoFact,double iTotal){
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		//ML ojo ver los archivos xml!
		JasperPrint report=null;
		if(codRep==NRO_REPORTE.remitoCliente)
			report = JasperReports.remitoCliente(productos,cantProd,prUnit,prTotal,nroComprob,fechaFact,dist,cte,iTotal);
		if(codRep==NRO_REPORTE.facturarCliente)
			report = JasperReports.facturarCliente(productos,cantProd,prUnit,prTotal,fechaFact,dist,cte,iva,condVta,remitoNro,ingrBrutos,tipoFact,iTotal);
		if(codRep==NRO_REPORTE.notaDebito)
			report = JasperReports.notaDebito(productos,cantProd,prUnit,prTotal,fechaFact,dist,cte,iva,observ,ingrBrutos,tipoFact,iTotal);
		if(codRep==NRO_REPORTE.DetalleNotaDebitoCte)
			report = JasperReports.DetalleNotaDebitoCte(productos,cantProd,prUnit,prTotal,nroComprob,fechaFact,dist,cte,iva,observ,ingrBrutos,iTotal);
		JRViewer jrv=null;
		try {
			jrv = new JRViewer(report);
		} catch (JRException ex) {
			Utils.manejoErrores(ex,this.getClass(),"Error Reportes 4 5");
		}
		this.getContentPane().add(jrv);
		Utils.show(this);
	}
	
	//Solo para pasar NDeb y Fact por el mismo comprobante
	public GUIReport(JDialog parent,int codRep,Vector productos,Vector cantProd, Vector prUnit,Vector prTotal,String nroComprob, Date fechaFact,
			ComercioDTO dist, ClienteDTO cte, String iva, String condVta_observ,String ingrBrutos,String tipoFact,double iTotal){
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		//ML ojo ver los archivos xml!
		JasperPrint report=null;
		if(codRep==NRO_REPORTE.facturarCliente || codRep==NRO_REPORTE.notaDebito)
			report = JasperReports.facturar_nDebito_Cliente(nroComprob,productos,cantProd,prUnit,prTotal,fechaFact,dist,cte,iva,condVta_observ,ingrBrutos,tipoFact,iTotal);
		/*if()
			report = JasperReports.notaDebito(productos,cantProd,prUnit,prTotal,fechaFact,dist,cte,iva,observ,ingrBrutos,tipoFact,iTotal); */
		JRViewer jrv=null;
		try {
			jrv = new JRViewer(report);
		} catch (JRException ex) {
			Utils.manejoErrores(ex,this.getClass(),"Error Reportes 4 5");
		}
		this.getContentPane().add(jrv);
		Utils.show(this);
	}
	
	public GUIReport(JDialog parent,int codRep,Vector productos,Vector cantProd, Vector prUnit,Vector prTotal,FacturaClienteDTO factura,String nroComprob, Date fechaFact,
			ComercioDTO dist, ClienteDTO cte, double iTotal){
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		if(codRep==NRO_REPORTE.detallarCompraFactCliente) 
			report = JasperReports.detallarCompraFactCliente(productos,cantProd,prUnit,prTotal,factura,nroComprob,fechaFact,dist,cte,iTotal);
		JRViewer jrv=null;
		try {
			jrv = new JRViewer(report);
		} catch (JRException ex) {
			Utils.manejoErrores(ex,this.getClass(),"Error Reportes 3");
		}
		this.getContentPane().add(jrv);
		Utils.show(this);
	}
	
	public GUIReport(JDialog parent,int codRep,ComercioDTO comercio,Vector entradas, Vector salidas,int nroPlanilla, Date fecha, double saldoAnt, double saldoActual) throws Exception {
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		if(codRep==NRO_REPORTE.generarBalance){
			report = JasperReports.generarBalance(comercio,entradas,salidas,nroPlanilla, fecha, saldoAnt,saldoActual);
		}
		JRViewer jrv=null;
		try {
			jrv = new JRViewer(report);
		} catch (JRException ex) {
			Utils.manejoErrores(ex,this.getClass(),"Error Reportes 7");
		}
		this.getContentPane().add(jrv);
		Utils.show(this);
	}
	
	public GUIReport(JDialog parent,int codRep,ComercioDTO dist, String titulo, Vector detalleItImpr,	Vector fecha, Vector debe, Vector haber,Vector saldo,String leyenda) {
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		String mensaje="No se puede efectuar la impresión";
		if(codRep==NRO_REPORTE.detallarCuentaCliente)
			report = JasperReports.detallarCuentaCliente(dist, titulo, detalleItImpr, fecha, debe, haber, saldo, leyenda);
		if(detalleItImpr.size()>0){
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error Reportes 14");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
		}else{
			Utils.advertenciaUsr(parent,mensaje);
		}
	}	
	
	public GUIReport(JDialog parent,int codRep,ComercioDTO dist, String titulo, Vector codigosCte, Vector clientes,Vector fechasUF,Vector saldoFavor,Vector adeudado) {
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		String mensaje="No se puede efectuar la impresión";
		if(codRep==NRO_REPORTE.listarDeudaClientes)
			report = JasperReports.listarDeudaClientes(dist, titulo,codigosCte, clientes,fechasUF,saldoFavor,adeudado);
		if(clientes.size()>0){
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error Reportes 16");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
		}else{
			Utils.advertenciaUsr(parent,mensaje);
		}
	}	
	
	public GUIReport(JDialog parent,int codRep,ComercioDTO dist, String titulo, String periodo, Object[][] datos, String total) {
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		String mensaje="No se puede efectuar la impresión";
		if(codRep==NRO_REPORTE.mostrarLibroFacturas)
			report = JasperReports.mostrarLibroFacturas(dist, titulo, periodo,datos, total);
		if(datos.length>0){
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error Reportes 18");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
		}else{
			Utils.advertenciaUsr(parent,mensaje);
		}
	}
	
	public GUIReport(JDialog parent,int codRep,ComercioDTO comercio, int nroPlanilla,Vector detalleFact, Date fecha) {
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		if(codRep==NRO_REPORTE.balanceDetallePagos){
			report = JasperReports.balanceDetallePagos(comercio,detalleFact,nroPlanilla, fecha);
		}
		if(detalleFact.size()>0){
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error Reportes 7");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
		}else{
			Utils.advertenciaUsr(parent,"No existen facturas para efectuar la impresión");
		}
	}

	public GUIReport(JDialog parent, int codRep, Long nroRecibo, MovimientoCajaDTO mimovDTO, ComercioDTO comercio) {
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		//ML ojo ver los archivos xml!
		JasperPrint report=null;
		if(codRep==NRO_REPORTE.reciboCliente)
			report = JasperReports.reciboCliente(nroRecibo, mimovDTO,comercio);
		JRViewer jrv=null;
		try {
			jrv = new JRViewer(report);
		} catch (JRException ex) {
			Utils.manejoErrores(ex,this.getClass(),"Error Reportes 14");
		}
		this.getContentPane().add(jrv);
		Utils.show(this);
	}
}
