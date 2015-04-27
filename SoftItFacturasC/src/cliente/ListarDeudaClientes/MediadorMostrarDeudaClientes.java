package cliente.ListarDeudaClientes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cliente.ClienteConection;
import cliente.Principal.GUIReport;

import common.Utils;
import common.dto.ComercioDTO;
import common.dto.LocalidadDTO;
import common.interfaces.IControlCliente;
import common.interfaces.IControlComercio;
import common.interfaces.IControlFacturaCliente;
import common.interfaces.IControlLocalidad;
import constantes.NRO_REPORTE;

public class MediadorMostrarDeudaClientes implements ActionListener,ListSelectionListener {
	
	public IControlCliente controlCliente;
	public IControlFacturaCliente controlFactCte;
	private IControlComercio controlComercio;
	private IControlLocalidad controlLocalidad;
	private GUIListarDeudaClientes guiDeudaCtes=null;
	Vector todasFacturasCte= new Vector();
	Vector codigosCtes= new Vector();
	Vector clientes= new Vector();
	Vector fechasUF = new Vector();
	Vector saldoFavor = new Vector();
	Vector adeudado= new Vector();
	Vector idLocs= new Vector();
	LocalidadDTO loc = null;
	
	public MediadorMostrarDeudaClientes(JFrame guiPadre) throws Exception { //Long idLoc,
		ClienteConection clienteConexion = new ClienteConection();
		try{
			clienteConexion.iniciar();
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error Constructor");
		}
		this.controlCliente = clienteConexion.getIControlCliente();
		this.controlComercio = clienteConexion.getIControlComercio();
		this.controlFactCte = clienteConexion.getIControlFCliente();
		this.controlLocalidad = clienteConexion.getIControlLocalidad();
		guiDeudaCtes = new GUIListarDeudaClientes(guiPadre);
		Vector localidades = controlLocalidad.obtenerLocalidades();
		idLocs.add(null);
		for(int i=0;i<localidades.size();i++){
			LocalidadDTO lo=(LocalidadDTO) localidades.elementAt(i);
			guiDeudaCtes.localidades.add(lo.getNombre());
			idLocs.add(lo.getId());
		}
		
		guiDeudaCtes.setActionListeners(this);
		guiDeudaCtes.setListSelectionListener(this);
		cargarDatos();
	}
	
	public void show() {
		guiDeudaCtes.actualizarBusqueda();
		Utils.show(guiDeudaCtes);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == guiDeudaCtes.getJBImprimir()) {
			try{
				if(cargarFilasSeleccionadas()){
					String titulo ="";
					if(guiDeudaCtes.getJTFDatos().getText().compareTo("Todos")==0)
						titulo =" Estado de cuenta de todos los clientes de "+guiDeudaCtes.getJTFDatosLoc().getText();
					else
						titulo =" Estado de cuenta de los clientes inactivos de "+guiDeudaCtes.getJTFDatosLoc().getText();
					ComercioDTO dist=controlComercio.obtenerComercio();
					new GUIReport(guiDeudaCtes,NRO_REPORTE.listarDeudaClientes,dist,titulo,codigosCtes,clientes,fechasUF,saldoFavor,adeudado);
				}
			} catch(Exception ex) {
				Utils.manejoErrores(ex,this.getClass(),"Error ActionPerformed");
			}
		}else if (source == guiDeudaCtes.getJBCambiarBusqueda() ) {
			cargarDatos();
		}else if (source == guiDeudaCtes.getJCheckSelectAll() ) {
			if(guiDeudaCtes.getJCheckSelectAll().isSelected()){
				guiDeudaCtes.jtDatos.selectAll();
			}else{
				guiDeudaCtes.jtDatos.clearSelection();
			}	
		}else if (source == guiDeudaCtes.getJBSalir()) {
			guiDeudaCtes.dispose();
		}
	}
	
	public boolean cargarFilasSeleccionadas() {
		boolean result=false;
		try{
			if (guiDeudaCtes.jtDatos.getSelectedRow() == -1) {
				Utils.advertenciaUsr(guiDeudaCtes,"Debe Seleccionar uno o más Clientes.");
				result=false;
			}else{
				result=true;
				codigosCtes= new Vector();
				clientes= new Vector();
				fechasUF = new Vector();
				saldoFavor = new Vector();
				adeudado= new Vector();
				int[] dattos=guiDeudaCtes.jtDatos.getSelectedRows();
				
				for(int i=0;i<dattos.length;i++){
					codigosCtes.add(guiDeudaCtes.datos[dattos[i]][0]);
					clientes.add(guiDeudaCtes.datos[dattos[i]][1]);
					fechasUF.add(guiDeudaCtes.datos[dattos[i]][2]);
					saldoFavor.add(guiDeudaCtes.datos[dattos[i]][3]);
					adeudado.add(guiDeudaCtes.datos[dattos[i]][4]);
				}
			}
		} catch (Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error CargarFilasSeleccionadas");
		}
		return result;
	}
	
	public void cargarDatos() {
		try {
			Vector clientesDeuda = new Vector();
			guiDeudaCtes.getJTFDatos().setText((String)guiDeudaCtes.getJCBTipoCliente().getSelectedItem());
			guiDeudaCtes.getJTFDatosLoc().setText((String)guiDeudaCtes.getJCLocalidades().getSelectedItem());
			int loc=guiDeudaCtes.getJCLocalidades().getSelectedIndex();
			Long idLoc=(Long)idLocs.elementAt(loc);
			if(idLoc!=null){
				clientesDeuda = this.controlCliente.obtenerClientesAIDeLocalidadYDeuda(idLoc,(String)guiDeudaCtes.getJCBTipoCliente().getSelectedItem()); //Todos Inactivos
				int ctes=clientesDeuda.size()/4;
				guiDeudaCtes.datos = new Object[ctes][guiDeudaCtes.titulos.length];
				int i = 0;
				if(clientesDeuda!=null){
					for (int j = 0; j < clientesDeuda.size(); j=j+4) {
						Long cod = (Long) clientesDeuda.elementAt(j);
						String codigo="";
						if(cod!=null)
							codigo=String.valueOf(cod);
						String cte= (String) clientesDeuda.elementAt(j+1);
						String fecha = (String) clientesDeuda.elementAt(j+2);
						String deuda = (String) clientesDeuda.elementAt(j+3);
						String saldoFavor="";
						String deudaCte="";
						if(deuda.indexOf("-")==-1)// es nro positivo - adeuda
							deudaCte=deuda;
						else					  //es nro negativo - a favor
							saldoFavor=deuda.substring(1);
						Object[] temp = {codigo,cte,fecha,saldoFavor,deudaCte};
						guiDeudaCtes.datos[i] = temp;
						i++;
					}
				}	
			}else{
				guiDeudaCtes.datos = new Object[0][guiDeudaCtes.titulos.length];
			}
		}catch(Exception ex){
			Utils.manejoErrores(ex,this.getClass(),"Error argarDatos");
		}
		guiDeudaCtes.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guiDeudaCtes.actualizarTabla();
	}
	
	public void valueChanged(ListSelectionEvent arg0) {
	}
	
}
