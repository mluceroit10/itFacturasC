package cliente.GestionarRemitoCliente;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

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

import cliente.LimitadorMaxString;
import cliente.LimitadorNroMax;
import cliente.LimitadorPrecio;
import cliente.ModeloTabla;

import com.toedter.calendar.JDateChooser;
import common.Utils;

public class GUIRemitoCliente extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;			private JPanel jpDatosProd = null;
	private JPanel jpDatosRemito = null;	private JPanel jpDatosItems=null;
	private JButton jbAgregarProd = null;  	private JButton jbBuscarC=null;
	private JButton jbEliminarProd=null;	private JButton jbConfirmarRemito=null;
	
	private JLabel jlFechaRemito=null;		private JDateChooser jDataCFecha = null;
	private JLabel jlNombreC=null;			private JTextField jtfNombreC=null;	
	private JLabel jlCuit = null;			private JTextField jtCuit = null;
	private JLabel jlNroRemito = null;
	
	private JLabel jlITotal = null;			private JTextField jtfITotal = null;
	private JLabel jlProducto = null;		private JTextArea jtfProducto = null; 
	private JLabel jlImporte = null;		private JTextField jtfImporte = null;
	private JLabel jlCantidad = null;		private JTextField jtfCantidad = null; 
	
	private JScrollPane jspDatosInsc=null;
	public final String[] titulos ={"Cant.","Producto","Precio Unit.","Precio Total"};
	public Object[][] datos;
	public JTable tabla;					private ModeloTabla modTabla = null;
	public Long nroRemito;
	public Vector codProd= new Vector();
	
	private InputMap map = new InputMap();
	public Vector vendedores= new Vector();
	
	public GUIRemitoCliente(JFrame guiPadre) {
		super(guiPadre);
		this.setSize(new java.awt.Dimension(740,570));
		this.setLocationRelativeTo(guiPadre);
		this.setResizable(false);
		this.setTitle("Remitos Cliente");
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
			jpPpal.add(getJPDatosRemito(), null);
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
			jpDatosProd.setBounds(new java.awt.Rectangle(8,405,716,130));
			jpDatosProd.setBorder(Utils.crearTituloYBorde("Ingreso de Productos"));
			jpDatosProd.setBackground(Utils.colorPanel);
			jlProducto = new JLabel();
			jlProducto.setBounds(new Rectangle(15,15,200,22));
			jlProducto.setText("Producto:");
			jlCantidad = new JLabel();
			jlCantidad.setBounds(new java.awt.Rectangle(30,92,100,22));
			jlCantidad.setText("Ingrese Cantidad:");
			jlImporte = new JLabel();
			jlImporte.setBounds(new java.awt.Rectangle(300,92,100,22));
			jlImporte.setText("Ingrese Importe:");
			jpDatosProd.add(jlImporte, null);
			jpDatosProd.add(jlCantidad, null);
			jpDatosProd.add(jlProducto, null);
			jpDatosProd.add(getJTFProducto(), null);
			jpDatosProd.add(getJTFImporte(), null);
			jpDatosProd.add(getJTFCantidad(), null);
			jpDatosProd.add(getJBAgregarProd(), null);
		}
		return jpDatosProd;
	}
	
	private JPanel getJPDatosRemito() {
		if (jpDatosRemito == null) {
			jlCuit = new JLabel();
			jlCuit.setBounds(new java.awt.Rectangle(490,30,70,22));
			jlCuit.setText("Cuit:");
			jpDatosRemito = new JPanel();
			jpDatosRemito.setLayout(null);
			jpDatosRemito.setBorder(Utils.crearTituloYBorde("Datos Remito"));
			jpDatosRemito.setBounds(new java.awt.Rectangle(8,15,716,125));
			jpDatosRemito.setBackground(Utils.colorPanel);
			jlNombreC = new JLabel();
			jlNombreC.setBounds(new Rectangle(200,30,60,22));
			jlNombreC.setHorizontalAlignment(SwingConstants.RIGHT);
			jlNombreC.setText("Nombre:");
			jlFechaRemito = new JLabel();
			jlFechaRemito.setBounds(new Rectangle(200,65,60,22));
			jlFechaRemito.setHorizontalAlignment(SwingConstants.RIGHT);
			jlFechaRemito.setText("Fecha:");
			jlNroRemito = new JLabel();
			jlNroRemito.setBounds(new Rectangle(490,65,190,22));
			jpDatosRemito.add(jlNombreC, null);
			jpDatosRemito.add(jlCuit, null);
			jpDatosRemito.add(jlFechaRemito, null);
			jpDatosRemito.add(getJBBuscarC(), null);
			jpDatosRemito.add(getJTFNombreC(), null);
			jpDatosRemito.add(getJtCuit(), null);
			jpDatosRemito.add(getJDateChooserFecha(),null);
		}
		return jpDatosRemito;
	}
	
	public JDateChooser getJDateChooserFecha() {
		if (jDataCFecha == null) {
			jDataCFecha = new JDateChooser("dd - MMMMM - yyyy",false);
			jDataCFecha.setBounds(new java.awt.Rectangle(270,65,190,22));
		}
		return jDataCFecha;
	}
	
	public JButton getJBBuscarC() {
		if (jbBuscarC == null) {
			jbBuscarC = new JButton();
			jbBuscarC.setText("Seleccione el Cliente");
			jbBuscarC.setName("BuscarC");
			jbBuscarC.setBorder(Utils.crearRebordeBoton());
			jbBuscarC.setBounds(new java.awt.Rectangle(30,30,150,22));
			jbBuscarC.setInputMap(0, map);
		}
		return jbBuscarC;
	}
	
	public JButton getJBAgregarProd() {
		if (jbAgregarProd == null) {
			jbAgregarProd = new JButton();
			jbAgregarProd.setText("Agregar Producto");
			jbAgregarProd.setName("AgregarProd");
			jbAgregarProd.setBounds(new java.awt.Rectangle(565,92,120,25));
			jbAgregarProd.setBorder(Utils.crearRebordeBoton());
			jbAgregarProd.setEnabled(false);
			jbAgregarProd.setInputMap(0, map);
		}
		return jbAgregarProd;
	}
	
	public JButton getJBEliminarProd() {
		if (jbEliminarProd == null) {
			jbEliminarProd = new JButton();
			jbEliminarProd.setText("Eliminar Producto de Remito");
			jbEliminarProd.setName("EliminarP");
			jbEliminarProd.setBorder(Utils.crearRebordeBoton());
			jbEliminarProd.setBounds(new java.awt.Rectangle(30,175,200,22));
			jbEliminarProd.setEnabled(false);
			jbEliminarProd.setInputMap(0, map);
		}
		return jbEliminarProd;
	}
	
	public JButton getJBConfirmarRemito() {
		if (jbConfirmarRemito == null) {
			jbConfirmarRemito = new JButton();
			jbConfirmarRemito.setText("CONFIRMAR REMITO");
			jbConfirmarRemito.setName("ConfirmarRem");
			jbConfirmarRemito.setBorder(Utils.crearRebordeBoton());
			jbConfirmarRemito.setBounds(new java.awt.Rectangle(280,200,150,30));
			jbConfirmarRemito.setEnabled(false);
			jbConfirmarRemito.setInputMap(0, map);
		}
		return jbConfirmarRemito;
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
			jtfImporte.setBounds(new Rectangle(400,92,90,22));
			jtfImporte.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfImporte.setDocument(new LimitadorPrecio(jtfImporte));
			jtfImporte.setEnabled(false);
		}
		return jtfImporte;
	}
	
	public JTextField getJTFITotal() {
		if (jtfITotal == null) {
			jtfITotal = new JTextField();
			jtfITotal.setBounds(new Rectangle(600,205,100,22));
			jtfITotal.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfITotal.setEnabled(false);
		}
		return jtfITotal;
	}
	
	public JTextField getJTFCantidad() {
		if (jtfCantidad == null) {
			jtfCantidad = new JTextField();
			jtfCantidad.setBounds(new Rectangle(135,92,60,22));
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
			jtfNombreC.setBounds(new Rectangle(270,30,190,22));
			jtfNombreC.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfNombreC.setEnabled(false);
		}
		return jtfNombreC;
	}
	
	public JTextField getJtCuit() {
		if (jtCuit == null) {
			jtCuit = new JTextField();
			jtCuit.setBounds(new java.awt.Rectangle(530,30,150,22));
			jtCuit.setDisabledTextColor(Utils.colorTextoDisabled);
			jtCuit.setEnabled(false);
		}
		return jtCuit;
	}
	
	private JPanel getJPDatosItems() {
		if (jpDatosItems == null) {
			jpDatosItems = new JPanel();
			jpDatosItems.setLayout(null);
			jpDatosItems.setBounds(new Rectangle(8,150,716,245));
			jpDatosItems.setBorder(Utils.crearTituloYBorde("Listado de Productos Comprados"));
			jlITotal = new JLabel();
			jlITotal.setBounds(new java.awt.Rectangle(490,205,100,22));
			jlITotal.setHorizontalAlignment(SwingConstants.RIGHT);
			jlITotal.setText("Importe Total:");
			jpDatosItems.add(jlITotal, null);
			jpDatosItems.add(getJTFITotal(), null);
			jpDatosItems.add(getJSPDatosI(), null);
			jpDatosItems.setBackground(Utils.colorPanel);
			jpDatosItems.add(getJBEliminarProd(), null);
			jpDatosItems.add(getJBConfirmarRemito(), null);
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
	
	public void actualizarNroRemito(){
		jlNroRemito.setText("Nro Remito: "+Utils.nroFact(nroRemito));
		jpDatosRemito.add(jlNroRemito, null);
	}
	
	public void setListSelectionListener(ListSelectionListener lis) {
		tabla.getSelectionModel().addListSelectionListener(lis);
	}
	
	public void setActionListeners(ActionListener lis) {
		jbAgregarProd.addActionListener(lis);
		jbBuscarC.addActionListener(lis);
		jbEliminarProd.addActionListener(lis);
		jbConfirmarRemito.addActionListener(lis);
	}
	
}

