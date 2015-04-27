package cliente.GestionarNotaDebito;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import cliente.LimitadorMax;
import cliente.LimitadorMaxString;
import cliente.LimitadorNroMax;
import cliente.LimitadorPrecio;
import cliente.ModeloTabla;
import cliente.Recursos.util.BotonesGenericos;

import com.toedter.calendar.JDateChooser;

import common.RootAndIp;
import common.Utils;

public class GUIAltaNotaDebitoCliente extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;				private JPanel jpDatosProd = null;
	private JPanel jpDatosFactura = null;		private JPanel jpDatosItems=null;
	private JButton jbAgregarProd = null;  	 	private JButton jbBuscarFact=null;
	private JButton jbEliminarProd=null;		private JButton jbConfirmarFact=null;
	
	private JLabel jlFechaFactura=null;			private JDateChooser jDataCFecha = null;
	private JLabel jlProducto = null;	     	private JTextArea jtfProducto = null;
	private JLabel jlImporte = null;			private JTextField jtfImporte = null;
	private JLabel jlNroFactura = null;			private JTextField jtfNroFactura = null;
	private JLabel jlNombreC=null;				private JTextField jtfNombreC=null;	
	private JLabel jlCuit = null;				private JTextField jtCuit = null;
	private JLabel jlCantidad = null;			private JTextField jtfCantidad = null;
	private JLabel jlITotal = null;				private JTextField jtfITotal = null;
	private JLabel jlIngrBr = null;				private JTextField jtfIngrBrutos = null;
	private JLabel jlIva = null;				private JTextField jcbTipoIva = null;
	private JLabel jlObservaciones = null;		private JTextField jtfObservaciones = null;
	private JScrollPane jspDatosInsc=null;
	public final String[] titulos ={"Cant.","Producto","Precio Unit.","Precio Total"};
	public Object[][] datos;
	public JTable tabla;					
	public ModeloTabla modTabla = null;
	public Vector codProd= new Vector();
	private InputMap map = new InputMap();
	public Long nroNotaDeb;
		
	public GUIAltaNotaDebitoCliente(JFrame guiPadre) {
		super(guiPadre);
		this.setSize(new java.awt.Dimension(740,570));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("Nota de Débito Cliente");
		this.setContentPane(getJPPpal());
		this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}
	
	public JPanel getJPPpal() {
		if (jpPpal == null) {
			jpPpal = new JPanel();
			jpPpal.setLayout(null);
			jpPpal.add(getJPDatosProducto(), null);
			jpPpal.add(getJPDatosFactura(), null);
			Object[] temp  = {" "," "," "," "};
			datos = new Object[1][titulos.length];
			datos[0]=temp;
			jpPpal.add(getJPDatosItems(), null);
			jpPpal.setBackground(Utils.colorFondo);
		}
		return jpPpal;
	}
	
	private JPanel getJPDatosProducto() {
		if (jpDatosProd == null) {
			jpDatosProd = new JPanel();
			jpDatosProd.setLayout(null);
			jpDatosProd.setBounds(new java.awt.Rectangle(8,405,716,124));
			jpDatosProd.setBorder(Utils.crearTituloYBorde("Ingreso de Productos"));
			jpDatosProd.setBackground(Utils.colorPanel);
			
			jlProducto = new JLabel();
			jlProducto.setBounds(new Rectangle(15,15,200,22));
			jlProducto.setText("Producto:");
			jlCantidad = new JLabel();
			jlCantidad.setBounds(new java.awt.Rectangle(30,88,100,22));
			jlCantidad.setText("Ingrese Cantidad:");
			jlImporte = new JLabel();
			jlImporte.setBounds(new java.awt.Rectangle(300,88,100,22));
			jlImporte.setText("Ingrese Importe:");
			jpDatosProd.add(jlProducto, null);
			jpDatosProd.add(jlImporte, null);
			jpDatosProd.add(jlCantidad, null);
			jpDatosProd.add(getJTFProducto(), null);
			jpDatosProd.add(getJTFImporte(), null);
			jpDatosProd.add(getJTFCantidad(), null);
			jpDatosProd.add(getJBAgregarProd(), null);
		}
		return jpDatosProd;
	}
	
	private JPanel getJPDatosFactura() {
		if (jpDatosFactura == null) {
			jpDatosFactura = new JPanel();
			jpDatosFactura.setLayout(null);
			jpDatosFactura.setBorder(Utils.crearTituloYBorde16("Datos de la Nota de Débito "));
			jpDatosFactura.setBounds(new java.awt.Rectangle(8,15,716,146));
			jpDatosFactura.setBackground(Utils.colorPanel);
			jlNombreC = new JLabel();
			jlNombreC.setBounds(new Rectangle(230,28,60,22));
			jlNombreC.setHorizontalAlignment(SwingConstants.RIGHT);
			jlNombreC.setText("Nombre:");
			jlCuit = new JLabel();
			jlCuit.setBounds(new java.awt.Rectangle(510,28,30,22));
			jlCuit.setHorizontalAlignment(SwingConstants.RIGHT);
			jlCuit.setText("Cuit:");
			jlFechaFactura = new JLabel();
			jlFechaFactura.setBounds(new Rectangle(450,86,90,22));
			jlFechaFactura.setHorizontalAlignment(SwingConstants.RIGHT);
			jlFechaFactura.setText("Fecha:");
			jlIva = new JLabel();
			jlIva.setBounds(new Rectangle(230,57,60,22));
			jlIva.setHorizontalAlignment(SwingConstants.RIGHT);
			jlIva.setText("Iva:");
			jlIngrBr = new JLabel();
			jlIngrBr.setBounds(new Rectangle(450,57,90,22));
			jlIngrBr.setHorizontalAlignment(SwingConstants.RIGHT);
			jlIngrBr.setText("Ing. Brutos:");
			jlNroFactura = new JLabel();
			jlNroFactura.setBounds(new Rectangle(210,86,80,22));
			jlNroFactura.setText("Nro N Débito:");
			jlNroFactura.setHorizontalAlignment(SwingConstants.RIGHT);
			jlObservaciones = new JLabel();
			jlObservaciones.setBounds(new Rectangle(15,115,90,22));
			jlObservaciones.setText("Observaciones:");
			//   jlObservaciones.setHorizontalAlignment(SwingConstants.RIGHT);
			jpDatosFactura.add(jlNombreC, null);
			jpDatosFactura.add(jlCuit, null);
			jpDatosFactura.add(jlFechaFactura, null);
			jpDatosFactura.add(jlIva, null);
			jpDatosFactura.add(jlIngrBr, null);
			jpDatosFactura.add(jlNroFactura, null);
			jpDatosFactura.add(jlObservaciones, null);
			jpDatosFactura.add(getJBBuscarC(), null);
			jpDatosFactura.add(getJTFNombreC(), null);
			jpDatosFactura.add(getJtCuit(), null);
			jpDatosFactura.add(getJDateChooserFecha(),null);
			jpDatosFactura.add(getJCTipoIva(),null);
			jpDatosFactura.add(getJTFNroNotaDebito(),null);
			jpDatosFactura.add(getJTFIngrBrutos(),null);
			jpDatosFactura.add(getJTFObservaciones(),null);
		}
		return jpDatosFactura;
	}
	
	public JTextField getJTFObservaciones() {
		if (jtfObservaciones == null) {
			jtfObservaciones = new JTextField();
			jtfObservaciones.setBounds(new Rectangle(110,115,595,22));
			jtfObservaciones.setDocument(new LimitadorMax(jtfObservaciones,55));
		}
		return jtfObservaciones;
	}
	
	
	public JDateChooser getJDateChooserFecha() {
		if (jDataCFecha == null) {
			jDataCFecha = new JDateChooser("dd - MMMMM - yyyy",false);
			jDataCFecha.setBounds(new java.awt.Rectangle(550,86,150,22));
		}
		return jDataCFecha;
	}
	
	public JButton getJBBuscarC() {
		if (jbBuscarFact == null) {
			jbBuscarFact = BotonesGenericos.botonEnPanel("Seleccione Factura",new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/find.png"));
			jbBuscarFact.setName("BuscarFact");
			jbBuscarFact.setBounds(new java.awt.Rectangle(30,28,150,22));
			jbBuscarFact.setInputMap(0, map);
		}
		return jbBuscarFact;
	}
	
	public JButton getJBAgregarProd() {
		if (jbAgregarProd == null) {
			jbAgregarProd  = BotonesGenericos.botonEnPanel("Agregar Producto",new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/add.png"));
			jbAgregarProd.setName("AgregarProd");
			jbAgregarProd.setBounds(new java.awt.Rectangle(545,88,140,25));
			jbAgregarProd.setEnabled(false);
			jbAgregarProd.setInputMap(0, map);
		}
		return jbAgregarProd;
	}
	
	public JButton getJBEliminarProd() {
		if (jbEliminarProd == null) {
			jbEliminarProd  = BotonesGenericos.botonEnPanel("Eliminar Producto",new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/delete.png"));
			jbEliminarProd.setName("EliminarP");
			jbEliminarProd.setBounds(new java.awt.Rectangle(30,175,200,22));
			jbEliminarProd.setEnabled(false);
			jbEliminarProd.setInputMap(0, map);
		}
		return jbEliminarProd;
	}
	
	public JButton getJBConfirmaFact() {
		if (jbConfirmarFact == null) {
			jbConfirmarFact  = BotonesGenericos.botonEnPanel("CONFIRMAR NOTA DE DEBITO",new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/check.png"));
			jbConfirmarFact.setName("ConfirmarFact");
			jbConfirmarFact.setBounds(new java.awt.Rectangle(245,183,220,30));
			jbConfirmarFact.setEnabled(false);
			jbConfirmarFact.setInputMap(0, map);
		}
		return jbConfirmarFact;
	}
	
	public JTextArea getJTFProducto() {
		if (jtfProducto == null) {
			jtfProducto = new JTextArea();
			jtfProducto.setBounds(new Rectangle(90,20,600,60));
			jtfProducto.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfProducto.setDocument(new LimitadorMaxString(jtfProducto,255));
			jtfProducto.setEnabled(false);
		}
		return jtfProducto;
	}
	
	public JTextField getJTFImporte() {
		if (jtfImporte == null) {
			jtfImporte = new JTextField();
			jtfImporte.setBounds(new Rectangle(400,88,90,22));
			jtfImporte.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfImporte.setDocument(new LimitadorPrecio(jtfImporte));
			jtfImporte.setEnabled(false);
		}
		return jtfImporte;
	}
	
	public JTextField getJTFITotal() {
		if (jtfITotal == null) {
			jtfITotal = new JTextField();
			jtfITotal.setBounds(new Rectangle(600,200,100,22));
			jtfITotal.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfITotal.setEnabled(false);
		}
		return jtfITotal;
	}
	
	public JTextField getJTFCantidad() {
		if (jtfCantidad == null) {
			jtfCantidad = new JTextField();
			jtfCantidad.setBounds(new Rectangle(135,88,60,22));
			jtfCantidad.setDocument(new LimitadorNroMax(jtfCantidad,6));
			jtfCantidad.setText("1");
			jtfCantidad.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfCantidad.setEnabled(false);
		}
		return jtfCantidad;
	}
	
	public JTextField getJTFNombreC() {
		if (jtfNombreC == null) {
			jtfNombreC = new JTextField();
			jtfNombreC.setBounds(new Rectangle(300,28,150,22));
			jtfNombreC.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfNombreC.setEnabled(false);
		}
		return jtfNombreC;
	}
	
	public JTextField getJtCuit() {
		if (jtCuit == null) {
			jtCuit = new JTextField();
			jtCuit.setBounds(new java.awt.Rectangle(550,28,150,22));
			jtCuit.setDisabledTextColor(Utils.colorTextoDisabled);
			jtCuit.setEnabled(false);
		}
		return jtCuit;
	}
	
	public JTextField getJTFNroNotaDebito() {
		if (jtfNroFactura == null) {
			jtfNroFactura = new JTextField();
			jtfNroFactura.setBounds(new Rectangle(300,86,150,22));
			jtfNroFactura.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfNroFactura.setEnabled(false);
		}
		return jtfNroFactura;
	}
	
	public JTextField getJTFIngrBrutos() {
		if (jtfIngrBrutos == null) {
			jtfIngrBrutos = new JTextField();
			jtfIngrBrutos.setBounds(new Rectangle(550,57,150,22));
			jtfIngrBrutos.setEnabled(false);
			jtfIngrBrutos.setDisabledTextColor(Utils.colorTextoDisabled);
		}
		return jtfIngrBrutos;
	}
	
	public JTextField getJCTipoIva() {
		if (jcbTipoIva == null) {
			jcbTipoIva = new JTextField();
			jcbTipoIva.setBounds(new Rectangle(300,57,150,22));
			jcbTipoIva.setEnabled(false);
			jcbTipoIva.setDisabledTextColor(Utils.colorTextoDisabled);
			
		}
		return jcbTipoIva;
	}
	
	private JPanel getJPDatosItems() {
		if (jpDatosItems == null) {
			jpDatosItems = new JPanel();
			jpDatosItems.setLayout(null);
			jpDatosItems.setBounds(new Rectangle(8,170,716,229));
			jpDatosItems.setBorder(Utils.crearTituloYBorde("Listado de Productos Comprados"));
			jlITotal = new JLabel();
			jlITotal.setBounds(new java.awt.Rectangle(490,200,100,15));
			jlITotal.setHorizontalAlignment(SwingConstants.RIGHT);
			jlITotal.setText("Importe Total:");
			jpDatosItems.add(jlITotal, null);
			jpDatosItems.add(getJTFITotal(), null);
			jpDatosItems.add(getJSPDatosI(), null);
			jpDatosItems.setBackground(Utils.colorPanel);
			jpDatosItems.add(getJBEliminarProd(), null);
			jpDatosItems.add(getJBConfirmaFact(), null);
		}
		return jpDatosItems;
	}
	
	private JScrollPane getJSPDatosI() {
		if (jspDatosInsc == null) {
			jspDatosInsc = new JScrollPane();
			jspDatosInsc.setBounds(new Rectangle(10,20,690,150));
			jspDatosInsc.setViewportView(getJTDatosI());
		}
		return jspDatosInsc;
	}
	
	public JTable getJTDatosI() {
		if (tabla == null) {
			modTabla = new ModeloTabla(titulos, datos);
			tabla = new JTable(modTabla);
			TableColumn columna1 = tabla.getColumn("Cant.");
			columna1.setPreferredWidth(60);
			columna1.setMaxWidth(60);
			columna1.setCellRenderer(Utils.alinearDerecha());
			TableColumn columna3 = tabla.getColumn("Precio Unit.");
			columna3.setMaxWidth(100);
			columna3.setCellRenderer(Utils.alinearDerecha());
			TableColumn columna5 = tabla.getColumn("Precio Total");
			columna5.setMaxWidth(100);
			columna5.setCellRenderer(Utils.alinearDerecha());	
		}
		return tabla;
	}
	
	public void actualizarTabla(){
		jpPpal.remove(getJPDatosItems());
		jpDatosItems = null;
		tabla = null;
		modTabla = null;
		jspDatosInsc = null;
		jpPpal.add(getJPDatosItems(), null);
	}
	
	public void setListSelectionListener(ListSelectionListener lis) {
		tabla.getSelectionModel().addListSelectionListener(lis);
	}
	
	public void setActionListeners(ActionListener lis) {
		jbAgregarProd.addActionListener(lis);
		jbBuscarFact.addActionListener(lis);
		jbEliminarProd.addActionListener(lis);
		jbConfirmarFact.addActionListener(lis);
	}
	
	
}

