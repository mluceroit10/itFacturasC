package cliente.GestionarCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cliente.ClienteConection;
import cliente.Principal.GUIReport;

import common.Utils;
import common.dto.ClienteDTO;
import common.dto.ComercioDTO;
import common.dto.FacturaClienteDTO;
import common.dto.MovimientoCajaDTO;
import common.dto.NotaDebitoDTO;
import common.interfaces.IControlCliente;
import common.interfaces.IControlComercio;
import common.interfaces.IControlFacturaCliente;

import constantes.NRO_REPORTE;

public class MediadorCuentaCliente implements ActionListener,ListSelectionListener {
	
	public IControlCliente controlCliente;
	public IControlFacturaCliente controlFactCte;
	private IControlComercio controlComercio;
	private GUICuentaCliente guiCuentaCliente=null;
	public ClienteDTO cliente=null;
	Vector todasFacturasCte= new Vector();
	Vector detalleIt= new Vector();
	Vector fecha = new Vector();
	Vector debe= new Vector();
	Vector haber= new Vector();
	Vector saldo= new Vector();
	Vector detalleItImpr= new Vector();
	Vector fechaImpr = new Vector();
	Vector debeImpr= new Vector();
	Vector haberImpr= new Vector();
	Vector saldoImpr= new Vector();
	double saldoI=0;
	private Date hoy = new Date();
	private String periodoAct="";
	private int mesL;
	private int anioL;
	private double saldoCte=0;
	
	public MediadorCuentaCliente(MediadorGestionarCliente mgc, ClienteDTO cte,JDialog guiPadre) throws Exception {
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlCliente = clienteConexion.getIControlCliente();
		this.controlComercio = clienteConexion.getIControlComercio();
		this.controlFactCte = clienteConexion.getIControlFCliente();
		cliente = cte;
		mesL=Utils.getMes(hoy);
		anioL=Utils.getAnio(hoy);
		periodoAct=mesL+" - "+anioL;
		saldoCte=-cliente.getDeuda();
		guiCuentaCliente = new GUICuentaCliente(mesL,anioL,detalleIt,fecha,debe,haber,saldo,cliente.getNombre(),guiPadre,saldoCte);
		guiCuentaCliente.setActionListeners(this);
		actualizarDatosTabla();
		guiCuentaCliente.setListSelectionListener(this);
		Utils.show(guiCuentaCliente);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == guiCuentaCliente.getJBImprimir()) {
			try{
				if(cargarFilasSeleccionadas()){
					String titulo="Estado de Cuenta del Cliente:"+cliente.getNombre();
					ComercioDTO dist=controlComercio.obtenerComercio();
					String leyenda="Ante saldo negativo (-) el cliente registra deuda, de lo contrario el importe especificado es a favor del cliente";
					new GUIReport(guiCuentaCliente, NRO_REPORTE.detallarCuentaCliente, dist,titulo,detalleItImpr, fechaImpr,debeImpr,haberImpr,saldoImpr,leyenda);
				}
			} catch(Exception ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
			}
		}else if (source == guiCuentaCliente.getJCheckSelectAll() ) {
			if(guiCuentaCliente.getJCheckSelectAll().isSelected()){
				guiCuentaCliente.jtDatos.selectAll();
			}else{
				guiCuentaCliente.jtDatos.clearSelection();
			}	
		}else if (source == guiCuentaCliente.getJBCambiarPeriodo()){
			String anioB = guiCuentaCliente.getJTFAnio().getText();
			if(anioB.length()==0){
				Utils.advertenciaUsr(guiCuentaCliente,"Por favor ingrese el Año.");
			}else if(anioB.length()!=4){
				Utils.advertenciaUsr(guiCuentaCliente,"El año debe ser un número de 4 dígitos.");
			}else{
				anioL= Integer.parseInt(anioB);
				mesL = guiCuentaCliente.getJCBMes().getSelectedIndex()+1; //para que el numero del indice de con el mes sumo 1
				try {
					actualizarDatosTabla();
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					Utils.manejoErrores(ex,this.getClass(),"Error Al actualizar la tabla");
				}
			} 		
		}else if (source == guiCuentaCliente.getJBSalir()) {
			guiCuentaCliente.dispose();
		}
	}
	
	public boolean cargarFilasSeleccionadas() {
		boolean result=false;
		try{
			if (guiCuentaCliente.jtDatos.getSelectedRow() == -1) {
				Utils.advertenciaUsr(guiCuentaCliente,"Debe Seleccionar uno o más Movimientos.");
				result=false;
			}else{
				result=true;
				detalleItImpr= new Vector();
				fechaImpr = new Vector();
				debeImpr= new Vector();
				haberImpr= new Vector();
				saldoImpr= new Vector();
				int primerMov=guiCuentaCliente.jtDatos.getSelectedRow();
				int cantMovs = guiCuentaCliente.jtDatos.getSelectedRowCount();
				for(int i=primerMov;i<(primerMov+cantMovs);i++){
					detalleItImpr.add(detalleIt.elementAt(i));
					fechaImpr.add(fecha.elementAt(i));
					debeImpr.add(debe.elementAt(i));
					haberImpr.add(haber.elementAt(i));
					saldoImpr.add(saldo.elementAt(i));
				}
			}
		} catch (Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error CargarFilasSeleccionadas");
		}
		return result;
	}
	
	public void actualizarDatosTabla() throws Exception{
		todasFacturasCte=controlCliente.obtenerFacturasDeCliente(mesL,anioL,cliente.getNombre());
		guiCuentaCliente.getJTFPeriodo().setText(mesL+" - "+anioL);
		detalleIt.clear();
		fecha.clear();
		debe.clear();
		haber.clear();
		saldo.clear();
		boolean actual=false;
		if(periodoAct.compareTo(mesL+" - "+anioL)==0){
			actual=true;
			saldoI=calcularSaldoInicial(todasFacturasCte);
			detalleIt.add("SALDO ");
			fecha.add(" ");
			debe.add(" ");
			haber.add(" ");
			saldo.add(Utils.ordenarDosDecimales(saldoI));
		}
		for(int i=0;i<todasFacturasCte.size();i++){
			FacturaClienteDTO fc = (FacturaClienteDTO) todasFacturasCte.elementAt(i);
			boolean mostrarDatos=true;
			if(fc.getTipoFactura().compareTo("RemitoCliente")==0){
				if(controlFactCte.existeFacturaDeRemito(String.valueOf(fc.getNroFactura())))
					mostrarDatos=false;
			}
			if(mostrarDatos){
				String remNro="";
				if(fc.getRemitoNro()!=null && fc.getRemitoNro().compareTo("")!=0)
					remNro=" Rem Nro: "+Utils.nroFact(new Long(fc.getRemitoNro()));
				detalleIt.add(fc.getTipoFactura()+" Nro:"+Utils.nroFact(fc.getNroFactura())+remNro);
				fecha.add(Utils.getStrUtilDate(fc.getFechaImpresion()));
				saldoI =Utils.redondear(saldoI-fc.getImporteTotal(),2);
				debe.add(Utils.ordenarDosDecimales(fc.getImporteTotal()));
				haber.add(" ");
				if(actual)	saldo.add(Utils.ordenarDosDecimales(saldoI));
				else saldo.add(" ");
				
				Vector notasDebito=controlCliente.obtenerNotasDeDebitoDeFactura(fc.getId());
				for(int j=0;j<notasDebito.size();j++){
					NotaDebitoDTO nc=(NotaDebitoDTO)notasDebito.elementAt(j);
					detalleIt.add(" - Nota de Debito "+Utils.nroFact(nc.getNroFactura()));
					fecha.add(Utils.getStrUtilDate(nc.getFecha()));
					haber.add(" ");
					debe.add(Utils.ordenarDosDecimales(nc.getImporteTotal()));
					saldoI =Utils.redondear(saldoI-nc.getImporteTotal(),2);
					if(actual) saldo.add(Utils.ordenarDosDecimales(saldoI));
					else saldo.add(" ");
				}	
				
				Vector movs= fc.getComprobantesPago();
				if(!movs.isEmpty()){
					Vector movim=controlCliente.obtenerMovimientosCajaDeFactura(fc.getId());
					for(int j=0;j<movim.size();j++){
						MovimientoCajaDTO mc=(MovimientoCajaDTO)movim.elementAt(j);
						String detalle=" - Registra pago "+mc.getFormaPago();
						if(mc.getFormaPago().compareTo("CHEQUE")==0) detalle += " nro: "+ mc.getNroCheque();
						detalleIt.add(detalle);
						fecha.add(Utils.getStrUtilDate(mc.getFecha()));
						haber.add(Utils.ordenarDosDecimales(mc.getImporte()));
						debe.add(" ");
						saldoI =Utils.redondear(saldoI+mc.getImporte(),2);
						if(actual) saldo.add(Utils.ordenarDosDecimales(saldoI));
						else saldo.add(" ");
					}	
				}
				detalleIt.add(" ");
				fecha.add(" ");
				debe.add(" ");
				haber.add(" ");
				saldo.add(" ");
			}
		}
		
		if(actual){
			detalleIt.add("SALDO ACTUAL");
			java.util.Date hoy = new Date();
			fecha.add(Utils.getStrUtilDate(hoy));
			debe.add(" ");
			haber.add(" ");
			saldo.add(Utils.ordenarDosDecimales(saldoI));
		}else{
			detalleIt.add("DETALLE DEL MES");
			fecha.add(mesL+" - "+anioL);
			debe.add(" ");
			haber.add(" ");
			saldo.add(" ");
		}
		guiCuentaCliente.actualizarTabla();
	}
	
	public void valueChanged(ListSelectionEvent arg0) {
	}
	
	private double calcularSaldoInicial(Vector todasFcte){
		double saldoIni=saldoCte;
		try {
			for(int i=0;i<todasFcte.size();i++){
				FacturaClienteDTO fc = (FacturaClienteDTO) todasFcte.elementAt(i);
				boolean mostrarDatos=true;
				if(fc.getTipoFactura().compareTo("RemitoCliente")==0){
					if(controlFactCte.existeFacturaDeRemito(String.valueOf(fc.getNroFactura())))
						mostrarDatos=false;
				}
				if(mostrarDatos){
					saldoIni =Utils.redondear(saldoIni+fc.getImporteTotal(),2);
					Vector notasDebito=controlCliente.obtenerNotasDeDebitoDeFactura(fc.getId());
					for(int j=0;j<notasDebito.size();j++){
						NotaDebitoDTO nc=(NotaDebitoDTO)notasDebito.elementAt(j);
						saldoIni =Utils.redondear(saldoIni+nc.getImporteTotal(),2);
					}	
					
					Vector movs= fc.getComprobantesPago();
					if(!movs.isEmpty()){
						Vector movim=controlCliente.obtenerMovimientosCajaDeFactura(fc.getId());
						for(int j=0;j<movim.size();j++){
							MovimientoCajaDTO mc=(MovimientoCajaDTO)movim.elementAt(j);
							saldoIni =Utils.redondear(saldoIni-mc.getImporte(),2);
						}	
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return saldoIni;
	}
}
