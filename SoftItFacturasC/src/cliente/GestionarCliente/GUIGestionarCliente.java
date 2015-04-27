package cliente.GestionarCliente;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import cliente.LimitadorNroMax;
import cliente.ModeloTabla;
import cliente.Recursos.util.BotonesGenericos;

import common.RootAndIp;
import common.Utils;

public class GUIGestionarCliente extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;		    private JPanel jpDatos = null;
	private JPanel jpGestion = null;	    private JPanel jpBuscador = null;
	private JButton jbIngresar = null;		private JButton jbModif = null;
	private JButton jbEliminar = null;    	private JButton jbAceptar = null;
	private JButton jbCancelar = null;		private JButton jbImprimir = null;	 
	private JButton jbECuenta = null;		
	private JLabel jlNombre = null;			private JTextField jtfNombre = null;
	private JLabel jlCodigo = null;			private JTextField jtfCodigo = null;
	private ModeloTabla modTabla = null;
	public JScrollPane jspDatos = null;
	public final String[] titulos ={"ID","Código","Nombre","Cuit","Iva","Teléfono","Dirección", "Localidad"};
	public Object[][] datos;
	public JTable tabla;
	private InputMap map = new InputMap();	
	
	public GUIGestionarCliente(JFrame guiPadre) {
		super(guiPadre);
		datos = new Object[0][titulos.length];
		this.setSize(740,540);
		this.setLocationRelativeTo(guiPadre);
		this.setResizable(false);
		this.setTitle("Gestión de Clientes");
		this.setContentPane(getJPPpal());
		this.getContentPane().setName("GUIGestionarCliente");
		this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}
	
	public GUIGestionarCliente(JDialog guiPadre) {
		super(guiPadre);
		datos = new Object[0][titulos.length];
		this.setSize(740,540);
		this.setLocationRelativeTo(guiPadre);
		this.setResizable(false);
		this.setTitle("Gestión de Clientes");
		this.setContentPane(getJPPpal());
		this.getContentPane().setName("GUIGestionarCliente");
		this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}
	
	public JPanel getJPPpal() {
		if (jpPpal == null) {
			jpPpal = new JPanel();
			jpPpal.setLayout(null);
			jpPpal.add(getJPGestion(), null);
			jpPpal.add(getJPDatos(), null);
			jpPpal.add(getJPBuscador(), null);
			jpPpal.add(getJBAceptar(), null);
			jpPpal.add(getJBCancelar(), null);
			jpPpal.setBackground(Utils.colorFondo);
		}
		return jpPpal;
	}
	
	private JPanel getJPBuscador() {
		if (jpBuscador == null) {
			jpBuscador = new JPanel();
			jlNombre = new JLabel();
			jlNombre.setBounds(new Rectangle(15,25,60,15));
			jlNombre.setText("Nombre:");
			jlCodigo = new JLabel();
			jlCodigo.setBounds(new Rectangle(15,55,60,15));
			jlCodigo.setText("Código:");
			jpBuscador.setLayout(null);
			jpBuscador.setBorder(Utils.crearTituloYBorde("Buscar"));
			jpBuscador.setBounds(new java.awt.Rectangle(420,15,295,85));
			jpBuscador.add(jlNombre, null);
			jpBuscador.add(jlCodigo, null);
			jpBuscador.add(getJTFBuscadorNom(), null);
			jpBuscador.add(getJTFBuscadorCod(), null);
			jpBuscador.setBackground(Utils.colorPanel);
		}
		return jpBuscador;
	}
	
	private JPanel getJPGestion() {
		if (jpGestion == null) {
			jpGestion = new JPanel();
			jpGestion.setLayout(null);
			jpGestion.setBounds(new java.awt.Rectangle(15,15,380,85));
			jpGestion.setBorder(Utils.crearTituloYBorde("Gestión"));
			jpGestion.add(getJBIngresar(), null);
			jpGestion.add(getJBModificar(), null);
			jpGestion.add(getJBEliminar(), null);
			jpGestion.add(getJBImprimir(), null);
			jpGestion.add(getJBECuenta(), null);
			jpGestion.setBackground(Utils.colorPanel);
		}
		return jpGestion;
	}
	
	private JPanel getJPDatos() {
		if (jpDatos == null) {
			jpDatos = new JPanel();
			jpDatos.setLayout(null);
			jpDatos.setBounds(new Rectangle(15,110,700,340));
			jpDatos.setBorder(Utils.crearTituloYBorde("Listado de Clientes"));
			jpDatos.add(getJSPDatos(), null);
			jpDatos.setBackground(Utils.colorPanel);
		}
		return jpDatos;
	}
	
	private JScrollPane getJSPDatos() {
		if (jspDatos == null) {
			jspDatos = new JScrollPane();
			jspDatos.setBounds(new Rectangle(10,20,680,310));
			jspDatos.setViewportView(getJTDatos());
		}
		return jspDatos;
	}
	
	public JTable getJTDatos() {
		if (tabla == null) {
			modTabla = new ModeloTabla(titulos, datos);
			tabla = new JTable(modTabla);
			Utils.ocultarColumnaId(tabla);
			TableColumn columna0 = tabla.getColumn("Código");
			columna0.setCellRenderer( Utils.alinearDerecha());
			TableColumn columna1 = tabla.getColumn("Cuit");
			columna1.setPreferredWidth(100);
			columna1.setMaxWidth(100);
			TableColumn columna2 = tabla.getColumn("Iva");
			columna2.setPreferredWidth(60);
			columna2.setMaxWidth(60);
		}
		return tabla;
	}
	
	public JButton getJBIngresar() {
		if (jbIngresar == null) {
			jbIngresar = BotonesGenericos.botonGestionAgregar();
			jbIngresar.setName("Alta");
			jbIngresar.setBounds(new java.awt.Rectangle(20,23,100,25));
			jbIngresar.setInputMap(0, map);
		}
		return jbIngresar;
	}
	
	public JButton getJBModificar() {
		if (jbModif == null) {
			jbModif = BotonesGenericos.botonGestionModificar();
			jbModif.setName("Modificar");
			jbModif.setBounds(new java.awt.Rectangle(140,23,100,25));
			jbModif.setInputMap(0, map);
		}
		return jbModif;
	}
	
	public JTextField getJTFBuscadorNom() {
		if (jtfNombre == null) {
			jtfNombre = new JTextField();
			jtfNombre.setBounds(new Rectangle(75,25,200,20));
		}
		return jtfNombre;
	}
	
	public JTextField getJTFBuscadorCod() {
		if (jtfCodigo == null) {
			jtfCodigo = new JTextField();
			jtfCodigo.setBounds(new Rectangle(75,55,200,20));
			jtfCodigo.setDocument(new LimitadorNroMax(jtfCodigo,13));
		}
		return jtfCodigo;
	}
	
	public JButton getJBEliminar() {
		if (jbEliminar == null) {
			jbEliminar = BotonesGenericos.botonGestionEliminar();
			jbEliminar.setName("Baja");
			jbEliminar.setBounds(new java.awt.Rectangle(260,23,100,25));
			jbEliminar.setInputMap(0, map);
		}
		return jbEliminar;
	}
	
	public JButton getJBImprimir() {
		if (jbImprimir == null) {
			jbImprimir = BotonesGenericos.botonGestionImprimir();
			jbImprimir.setName("Imprimir");
			jbImprimir.setBounds(new java.awt.Rectangle(80,55,100,25));
			jbImprimir.setInputMap(0, map);
		}
		return jbImprimir;
	}
	
	public JButton getJBECuenta() {
		if (jbECuenta == null) {
			jbECuenta = BotonesGenericos.botonEnPanel("E. CUENTA",new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/cuenta.png"));
			jbECuenta.setName("Cuenta");
			jbECuenta.setBounds(new java.awt.Rectangle(200,55,100,25));
			jbECuenta.setInputMap(0, map);
		}
		return jbECuenta;
	}
	
	public JButton getJBAceptar() {
		if (jbAceptar == null) {
			jbAceptar = BotonesGenericos.botonAceptar();
			jbAceptar.setName("Aceptar");
			jbAceptar.setBounds(new Rectangle(240,470,100,30));
			jbAceptar.setInputMap(0, map);
		}
		return jbAceptar;
	}
	
	public JButton getJBCancelar() {
		if (jbCancelar == null) {
			jbCancelar = BotonesGenericos.botonCancelar();
			jbCancelar.setName("Cancelar");
			jbCancelar.setBounds(new Rectangle(390,470,100,30));
			jbCancelar.setInputMap(0, map);
		}
		return jbCancelar;
	}
	
	public void setKeyListener(KeyListener lis) {
		jtfNombre.addKeyListener(lis);
		jtfCodigo.addKeyListener(lis);
	}
	
	public void setListSelectionListener(ListSelectionListener lis) {
		tabla.getSelectionModel().addListSelectionListener(lis);
	}
	
	public void setActionListeners (ActionListener lis) {
		jbIngresar.addActionListener(lis);
		jbModif.addActionListener(lis);
		jbEliminar.addActionListener(lis);
		jbImprimir.addActionListener(lis);
		jbAceptar.addActionListener(lis);
		jbCancelar.addActionListener(lis);
		jbECuenta.addActionListener(lis);
	}
	
	public void repaint() {
		super.repaint();
	}
	
	public void actualizarTabla(){
		jpPpal.remove(getJPDatos());
		jpDatos = null;
		tabla = null;
		modTabla = null;
		jspDatos = null;
		jpPpal.add(getJPDatos(), null);
	}
}
