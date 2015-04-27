package cliente.GestionarCheque;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import cliente.LimitadorNroMax;
import cliente.LimitadorPrecio;
import cliente.Recursos.util.BotonesGenericos;

import com.toedter.calendar.JDateChooser;
import common.Utils;
import common.dto.ChequeDTO;

public class GUIAltaModCheque extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;			    private JPanel jpDatos = null;  
	private JButton jbAceptar = null;	    	private JButton jbCancelar = null;
	private JLabel jlNroCheque = null;		    private JTextField jtfNroCheque = null;
	private JLabel jlFechaEm = null;			private JDateChooser jDCFechaEm = null;
	private JLabel jlFechaVto = null; 			private JDateChooser jDCFechaVto = null;
	private JLabel jlBanco = null; 				private JTextField jtfBanco = null;
	private JLabel jlLocPlaza = null; 			private JTextField jtfLocPlaza = null;
	private JLabel jlPara = null;				private JTextField jtfPara = null;
	private JLabel jlImporte = null;			private JTextField jtfImporte = null;
	private ChequeDTO chDTO = null;
	Date hoy=new Date();
	private InputMap map = new InputMap();
	
	
	public GUIAltaModCheque(JDialog guiPadre) {
		super(guiPadre);
		this.setSize(new java.awt.Dimension(630,272));
		this.setTitle("Nuevo Cheque");
		this.setLocationRelativeTo(guiPadre);
		this.setResizable(false);
		this.setContentPane(getJPPpal());
		this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}
	
	public GUIAltaModCheque(ChequeDTO lp,JDialog guiPadre) {
		super(guiPadre);
		this.chDTO = lp;
		this.setSize(new java.awt.Dimension(630,272));
		this.setTitle("Modificar Cheque");
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
			jpPpal.setSize(new java.awt.Dimension(630,272));
			jpPpal.add(getJPDatos(),null);
			jpPpal.add(getJBAceptar(), null);
			jpPpal.add(getJBCancelar(), null);
			jpPpal.setBackground(Utils.colorFondo);
		}
		return jpPpal;
	}
	
	public JPanel getJPDatos() {
		if (jpDatos == null) {
			jlNroCheque = new JLabel("Número (*)");
			jlNroCheque.setBounds(new Rectangle(10,30,100,15));//cada 32
			jlNroCheque.setHorizontalAlignment(SwingConstants.RIGHT);
			jlImporte = new JLabel();
			jlImporte.setBounds(new java.awt.Rectangle(310,30,100,15));
			jlImporte.setText("Importe (*) ");
			jlImporte.setHorizontalAlignment(SwingConstants.RIGHT);
			jlBanco = new JLabel();
			jlBanco.setBounds(new java.awt.Rectangle(10,62,100,15));
			jlBanco.setText("Banco(*) ");
			jlBanco.setHorizontalAlignment(SwingConstants.RIGHT);
			jlLocPlaza = new JLabel();
			jlLocPlaza.setBounds(new java.awt.Rectangle(310,62,100,15));
			jlLocPlaza.setText("Loc /Plaza (*) ");
			jlLocPlaza.setHorizontalAlignment(SwingConstants.RIGHT);
			
			jlFechaEm = new JLabel();
			jlFechaEm.setBounds(new java.awt.Rectangle(10,94,100,15));
			jlFechaEm.setText("Fecha Emisión(*) ");
			jlFechaEm.setHorizontalAlignment(SwingConstants.RIGHT);
			jlFechaVto = new JLabel();
			jlFechaVto.setBounds(new java.awt.Rectangle(310,94,100,15));
			jlFechaVto.setText("Vencimiento (*) ");
			jlFechaVto.setHorizontalAlignment(SwingConstants.RIGHT);
			jlPara = new JLabel();
			jlPara.setBounds(new java.awt.Rectangle(10,126,100,15));
			jlPara.setText("Quien Entrega (*) ");
			jlPara.setHorizontalAlignment(SwingConstants.RIGHT);
			jpDatos = new JPanel();
			jpDatos.setLayout(null);
			jpDatos.setBorder(Utils.crearTituloYBorde("Datos del Cheque"));
			jpDatos.setBounds(new java.awt.Rectangle(15,15,600,167));
			jpDatos.add(jlNroCheque, null);
			jpDatos.add(jlImporte, null);
			jpDatos.add(jlBanco, null);
			jpDatos.add(jlLocPlaza, null);
			jpDatos.add(jlPara, null);
			jpDatos.add(jlFechaEm, null);
			jpDatos.add(jlFechaVto, null);
			jpDatos.add(getJTFNroCheque(), null);
			jpDatos.add(getJTFImporte(), null);
			jpDatos.add(getJTFBanco(), null);
			jpDatos.add(getJTFLocPlaza(), null);
			jpDatos.add(getJTFPara(), null);
			jpDatos.add(getJDateChooserFechaEm(), null);
			jpDatos.add(getJDateChooserFechaVto(), null);
			if (chDTO!=null) {
				jtfNroCheque.setText(String.valueOf(chDTO.getNumero()));
				jtfImporte.setText(String.valueOf(chDTO.getImporte()));
				jtfBanco.setText(chDTO.getBanco());
				jtfLocPlaza.setText(chDTO.getLocPlaza());
				jtfPara.setText(chDTO.getQuienEntrega());
				jDCFechaEm.setDate(chDTO.getFechaEmision());
				jDCFechaVto.setDate(chDTO.getFechaVto());
			}
			jpDatos.setBackground(Utils.colorPanel);
		}
		return jpDatos;
	}
	
	public JTextField getJTFNroCheque() {
		if (jtfNroCheque == null) {
			jtfNroCheque = new JTextField();
			jtfNroCheque.setBounds(new java.awt.Rectangle(120,30,150,22));
			jtfNroCheque.setDocument(new LimitadorNroMax(jtfNroCheque,8));
		}
		return jtfNroCheque;
	}
	
	public JTextField getJTFImporte() {
		if (jtfImporte == null) {
			jtfImporte = new JTextField();
			jtfImporte.setBounds(new java.awt.Rectangle(415,30,170,22));
			jtfImporte.setDocument(new LimitadorPrecio(jtfImporte));
		}
		return jtfImporte;
	}
	
	public JTextField getJTFBanco() {
		if (jtfBanco == null) {
			jtfBanco = new JTextField();
			jtfBanco.setBounds(new java.awt.Rectangle(120,62,150,22));
		}
		return jtfBanco;
	}
	
	public JTextField getJTFLocPlaza() {
		if (jtfLocPlaza == null) {
			jtfLocPlaza = new JTextField();
			jtfLocPlaza.setBounds(new java.awt.Rectangle(415,62,170,22));
		}
		return jtfLocPlaza;
	}
	
	public JTextField getJTFPara() {
		if (jtfPara == null) {
			jtfPara = new JTextField();
			jtfPara.setBounds(new java.awt.Rectangle(120,126,465,22));
			jtfPara.setBackground(new Color(255,255,255));
			jtfPara.setForeground(java.awt.Color.black);
		}
		return jtfPara;
	}
	
	public JDateChooser getJDateChooserFechaEm() {
		if (jDCFechaEm == null) {
			jDCFechaEm = new JDateChooser("dd - MMMMM - yyyy",false);
			jDCFechaEm.setBounds(new java.awt.Rectangle(120,94,170,22));
		}
		return jDCFechaEm;
	}
	
	public JDateChooser getJDateChooserFechaVto() {
		if (jDCFechaVto == null) {
			jDCFechaVto = new JDateChooser("dd - MMMMM - yyyy",false);
			jDCFechaVto.setBounds(new java.awt.Rectangle(415,94,170,22));
		}
		return jDCFechaVto;
	}
	
	public JButton getJBAceptar() {
		if (jbAceptar == null) {
			jbAceptar = BotonesGenericos.botonAceptar();
			jbAceptar.setBounds(new java.awt.Rectangle(180,202,100,30));
			jbAceptar.setInputMap(0, map);
		}
		return jbAceptar;
	}
	
	public JButton getJBCancelar() {
		if (jbCancelar == null) {
			jbCancelar = BotonesGenericos.botonCancelar();
			jbCancelar.setBounds(new java.awt.Rectangle(330,202,100,30));
			jbCancelar.setInputMap(0, map);
		}
		return jbCancelar;
	}
	
	public void setActionListeners(ActionListener lis) {
		jbAceptar.addActionListener(lis);
		jbCancelar.addActionListener(lis);
	}
	
}