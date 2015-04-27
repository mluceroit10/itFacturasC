package cliente.GestionarProvincia;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

import cliente.ModeloTabla;
import cliente.Recursos.util.BotonesGenericos;

import common.Utils;

public class GUIGestionarProvincia extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;			    private JPanel jpGestion = null;
	private JPanel jpDatos = null;			    private JPanel jpBuscador = null;
	private JButton jbCargar = null;		    private JButton jbModif = null;
	private JButton jbAceptar = null;			private JButton jbCancelar = null;
	private JButton jbBorrar = null;
	private JLabel jlNombre = null;				private JTextField jtfNombre = null;
	public JScrollPane jspDatos = null;
	public JTable jtDatos = null;			    private ModeloTabla modTabla = null;
	public String[] titulos = {"ID","Nombre"};
	public Object[][] datos;
	private InputMap map = new InputMap();
	
	public GUIGestionarProvincia(JFrame guiPadre) {
		super(guiPadre);
		datos = new Object[0][titulos.length];
		this.setSize(new java.awt.Dimension(580,395));
		this.setLocationRelativeTo(guiPadre);
		this.setResizable(false);
		this.setTitle("Gestión de Provincias");
		this.setContentPane(getJPPpal());
		this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}
	
	public GUIGestionarProvincia(JDialog guiPadre) {
		super(guiPadre);
		datos = new Object[0][titulos.length];
		this.setSize(new java.awt.Dimension(580,395));
		this.setLocationRelativeTo(guiPadre);
		this.setResizable(false);
		this.setTitle("Gestión de Provincias");
		this.setContentPane(getJPPpal());
		this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}
	
	public JPanel getJPPpal() {
		if (jpPpal == null) {
			jpPpal = new JPanel();
			jpPpal.setLayout(null);
			jpPpal.add(getJPGestion(), null);
			jpPpal.add(getJBAceptar(), null);
			jpPpal.add(getJBCancelar(), null);
			jpPpal.add(getJPDatos(), null);
			jpPpal.add(getJPBuscador(), null);
			jpPpal.setBackground(Utils.colorFondo);
		}
		return jpPpal;
	}
	
	private JPanel getJPBuscador() {
		if (jpBuscador == null) {
			jlNombre = new JLabel();
			jlNombre.setBounds(new Rectangle(10,22,60,15));
			jlNombre.setText("Nombre:");
			jpBuscador = new JPanel();
			jpBuscador.setLayout(null);
			jpBuscador.setBorder(Utils.crearTituloYBorde("Buscar"));
			jpBuscador.setBounds(new Rectangle(10,215,140,90));
			
			jpBuscador.add(jlNombre, null);
			jpBuscador.add(getJTFBuscador(), null);
			jpBuscador.setBackground(Utils.colorPanel);
		}
		return jpBuscador;
	}
	
	private JPanel getJPDatos() {
		if (jpDatos == null) {
			jpDatos = new JPanel();
			jpDatos.setLayout(null);
			jpDatos.setBounds(new Rectangle(160,15,400,290));
			jpDatos.setBorder(Utils.crearTituloYBorde("Provincia"));
			jpDatos.add(getJSPDatos(), null);
			jpDatos.setBackground(Utils.colorPanel);
		}
		return jpDatos;
	}
	
	private JPanel getJPGestion() {
		if (jpGestion == null) {
			jpGestion = new JPanel();
			jpGestion.setLayout(null);
			jpGestion.setBounds(new Rectangle(10,15,140,175));
			jpGestion.setBorder(Utils.crearTituloYBorde("Gestión"));
			jpGestion.add(getJBCargar(), null);
			jpGestion.add(getJBMod(), null);
			jpGestion.add(getJBBorrar(), null);
			jpGestion.setBackground(Utils.colorPanel);
		}
		return jpGestion;
	}
	
	public JButton getJBCargar() {
		if (jbCargar == null) {
			jbCargar = BotonesGenericos.botonGestionAgregar();
			jbCargar.setBounds(new Rectangle(20,25,100,25));
			jbCargar.setInputMap(0, map);
		}
		return jbCargar;
	}
	
	public JButton getJBMod() {
		if (jbModif == null) {
			jbModif = BotonesGenericos.botonGestionModificar();
			jbModif.setBounds(new Rectangle(20,75,100,25));
			jbModif.setInputMap(0, map);
		}
		return jbModif;
	}
	
	public JButton getJBBorrar() {
		if (jbBorrar == null) {
			jbBorrar = BotonesGenericos.botonGestionEliminar();
			jbBorrar.setBounds(new Rectangle(20,125,100,25));
			jbBorrar.setInputMap(0, map);
		}
		return jbBorrar;
	}
	
	public JButton getJBAceptar() {
		if (jbAceptar == null) {
			jbAceptar = BotonesGenericos.botonAceptar();
			jbAceptar.setBounds(new Rectangle(155,325,100,30));
			jbAceptar.setInputMap(0, map);
		}
		return jbAceptar;
	}
	
	public JButton getJBCancelar() {
		if (jbCancelar == null) {
			jbCancelar = BotonesGenericos.botonCancelar();
			jbCancelar.setBounds(new Rectangle(305,325,100,30));
			jbCancelar.setInputMap(0, map);
		}
		return jbCancelar;
	}
	
	private JScrollPane getJSPDatos() {
		if (jspDatos == null) {
			jspDatos = new JScrollPane();
			jspDatos.setBounds(new Rectangle(5,20,390,265));
			jspDatos.setViewportView(getJTDatos());
		}
		return jspDatos;
	}
	
	public JTable getJTDatos() {
		if (jtDatos == null) {
			modTabla = new ModeloTabla(titulos, datos);
			jtDatos = new JTable(modTabla);
			Utils.ocultarColumnaId(jtDatos);
		}
		return jtDatos;
	}
	
	public JTextField getJTFBuscador() {
		if (jtfNombre == null) {
			jtfNombre = new JTextField();
			jtfNombre.setBounds(new Rectangle(10,50,110,22));
		}
		return jtfNombre;
	}
	
	public void setActionListeners(ActionListener lis) {
		jbCancelar.addActionListener(lis);
		jbAceptar.addActionListener(lis);
		jbModif.addActionListener(lis);
		jbCargar.addActionListener(lis);
		jbBorrar.addActionListener(lis);
	}
	
	public void setKeyListener(KeyListener lis) {
		jtfNombre.addKeyListener(lis);
	}
	
	public void setListSelectionListener(ListSelectionListener lis) {
		jtDatos.getSelectionModel().addListSelectionListener(lis);
	}
	
	public void actualizarTabla(){
		jpPpal.remove(getJPDatos());
		jpDatos = null;
		jtDatos = null;
		modTabla = null;
		jspDatos = null;
		jpPpal.add(getJPDatos(), null);
	}
}    