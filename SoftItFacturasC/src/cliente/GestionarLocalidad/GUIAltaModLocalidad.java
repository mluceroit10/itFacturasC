package cliente.GestionarLocalidad;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import cliente.LimitadorNroMax;
import cliente.Recursos.Botones.ButtonType;
import cliente.Recursos.Botones.GlossyButton;
import cliente.Recursos.util.BotonesGenericos;
import cliente.Recursos.util.Theme;

import common.RootAndIp;
import common.Utils;
import common.dto.LocalidadDTO;

public class GUIAltaModLocalidad extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;			    private JPanel jpDatos = null;
	private JButton jbAceptar = null;		    private JButton jbCancelar = null;
	private JButton jbProvincia = null;
	private JLabel jlNombre = null;			   	private JTextField jtfNombre = null;
	private JLabel jlCodPostal = null;			private JTextField jtfCodPostal = null;
	private JLabel jlProvincia = null;		    private JTextField jtfProvincia = null;
	private LocalidadDTO loc = null;
	private InputMap map = new InputMap();
	
	public GUIAltaModLocalidad(JDialog guiPadre) {
		super(guiPadre);
		this.setSize(new java.awt.Dimension(340,241));
		this.setTitle("Nueva Localidad");
		this.setLocationRelativeTo(guiPadre);
		this.setResizable(false);
		this.setContentPane(getJPPpal());
		this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}
	
	public GUIAltaModLocalidad(LocalidadDTO p,JDialog guiPadre) {
		super(guiPadre);
		this.loc = p;
		this.setSize(new java.awt.Dimension(340,241));
		this.setTitle("Modificar Localidad");
		this.setLocationRelativeTo(guiPadre);
		this.setResizable(false);
		this.setContentPane(getJPPpal());
		this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}
	
	private JPanel getJPPpal() {
		if (jpPpal == null) {
			jpPpal = new JPanel();
			jpPpal.setLayout(null);
			jpPpal.add(getJPDatos(),null);
			jpPpal.add(getJBAceptar(), null);
			jpPpal.add(getJBCancelar(), null);
			jpPpal.setBackground(Utils.colorFondo);
		}
		return jpPpal;
	}
	
	private JPanel getJPDatos() {
		if (jpDatos == null) {
			jlNombre = new JLabel("Nombre  (*)    ");
			jlNombre.setBounds(new Rectangle(10,30,100,15));
			jlNombre.setHorizontalAlignment(SwingConstants.RIGHT);
			jlCodPostal = new JLabel("Cod. Postal    ");
			jlCodPostal.setBounds(new Rectangle(10,62,100,15));
			jlCodPostal.setHorizontalAlignment(SwingConstants.RIGHT);
			jlProvincia = new JLabel("Provincia(*)   ");
			jlProvincia.setBounds(new Rectangle(10,94,100,15));
			jlProvincia.setHorizontalAlignment(SwingConstants.RIGHT);
			jpDatos = new JPanel();
			jpDatos.setLayout(null);
			jpDatos.setSize(new java.awt.Dimension(300,130));
			jpDatos.setBorder(Utils.crearTituloYBorde("Datos de la Localidad"));
			jpDatos.setBounds(new Rectangle(15,15,300,136));
			jpDatos.add(jlNombre, null);
			jpDatos.add(jlCodPostal, null);
			jpDatos.add(jlProvincia, null);
			jpDatos.add(getJTFNombre(), null);
			jpDatos.add(getJTFCodPostal(), null);
			jpDatos.add(getJTFProvincia(), null);
			jpDatos.add(getJBProvincia(), null);
			if (loc!=null) {
				jtfNombre.setText(loc.getNombre());
				jtfCodPostal.setText(String.valueOf(loc.getCodPostal()));
				jtfProvincia.setText(loc.getProvincia().getNombre());
			}
			jpDatos.setBackground(Utils.colorPanel);
		}
		return jpDatos;
	}
	
	public JTextField getJTFNombre() {
		if (jtfNombre == null) {
			jtfNombre = new JTextField();
			jtfNombre.setBounds(new Rectangle(107,30,180,22));
		}
		return jtfNombre;
	}
	
	public JTextField getJTFCodPostal() {
		if (jtfCodPostal == null) {
			jtfCodPostal = new JTextField();
			jtfCodPostal.setBounds(new Rectangle(107,62,180,22));
			jtfCodPostal.setDocument(new LimitadorNroMax(jtfCodPostal,6));
		}
		return jtfCodPostal;
	}
	
	public JTextField getJTFProvincia() {
		if (jtfProvincia == null) {
			jtfProvincia = new JTextField();
			jtfProvincia.setBounds(new java.awt.Rectangle(107,94,95,22));
			jtfProvincia.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfProvincia.setEnabled(false);
		}
		return jtfProvincia;
	}
	
	public JButton getJBProvincia() {
		if (jbProvincia == null) {
			jbProvincia = BotonesGenericos.botonGestionBuscar();
			jbProvincia.setBounds(new Rectangle(210,94,80,22));
			jbProvincia.setName("Buscar");
			jbProvincia.setInputMap(0, map);
		}
		return jbProvincia;
	}
	
	public JButton getJBAceptar() {
		if (jbAceptar == null) {
			jbAceptar = BotonesGenericos.botonAceptar();
			jbAceptar.setBounds(new Rectangle(50,171,100,30));
			jbAceptar.setInputMap(0, map);
		}
		return jbAceptar;
	}
	
	public JButton getJBCancelar() {
		if (jbCancelar == null) {
			jbCancelar = BotonesGenericos.botonCancelar();
			jbCancelar.setBounds(new Rectangle(180,171,100,30));
			jbCancelar.setInputMap(0, map);
		}
		return jbCancelar;
	}
	
	public void setActionListeners(ActionListener lis) {
		jbAceptar.addActionListener(lis);
		jbCancelar.addActionListener(lis);
		jbProvincia.addActionListener(lis);
	}
	
	public void setProvincia(String string) {
		jtfProvincia.setText(string);
	}
}