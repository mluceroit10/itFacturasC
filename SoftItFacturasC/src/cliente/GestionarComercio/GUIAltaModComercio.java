package cliente.GestionarComercio;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import cliente.LimitadorNroGuion;
import cliente.LimitadorNroMax;
import cliente.Recursos.util.BotonesGenericos;

import com.toedter.calendar.JDateChooser;

import common.RootAndIp;
import common.Utils;
import common.dto.ComercioDTO;

public class GUIAltaModComercio extends JDialog {
	private static final long serialVersionUID = 1L;
    private JPanel jpPpal = null;               private JPanel jpDatosDistrib = null; 
    private JPanel jpNros=null;			
    private JButton jbCancelar = null;          private JButton jbAceptar = null;
    private JButton jbLocalidad = null;         private JButton jbImprTarjeta = null;  
    private JButton jbModNroFacts;
    private JLabel jlNombre = null;		    	private JTextField jtfNombre = null;
    private JLabel jlDireccion = null;	        private JTextField jtfCalle = null;  
    private JLabel jlCuit = null;           	private JTextField jtfCuit = null;
    private JLabel jlTel = null;				private JTextField jtfTel = null;
    private JLabel jlIngBrutos = null;			private JTextField jtfIngBrutos = null;
    private JLabel jlLocalidad = null;			private JTextField jtfLocalidad = null;
    private JLabel jlInicioAct = null;			private JDateChooser jDataCFecha = null;		       	    
    private JLabel jlNroFactC = null;		private JTextField jtfNroFactC = null;
    private JLabel jlNroRemito = null;		private JTextField jtfNroRemito = null;
    private JLabel jlNroNDebito = null;		private JTextField jtfNroNDebito = null;
    private JLabel jlNroRecibo = null;		private JTextField jtfNroRecibo = null;
    private ComercioDTO dist;
	private InputMap map = new InputMap();
	
    public GUIAltaModComercio(JFrame guiPadre) {
        super(guiPadre);
        this.setSize(770,430);
    	this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setContentPane(getPanelPpal());
        this.setTitle("Datos Comercio");
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }

    public GUIAltaModComercio(ComercioDTO d,JFrame guiPadre) {
        super(guiPadre);
        this.dist = d;
        this.setSize(770,430);
    	this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setContentPane(getPanelPpal());
        this.setTitle("Datos Comercio");
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }

    private JPanel getPanelPpal() {
        if (jpPpal == null) {
            jpPpal = new JPanel();
            jpPpal.setLayout(null);
            jpPpal.setSize(770, 430);
            jpPpal.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, null));
            jpPpal.setName("Gestión Cliente");
            jpPpal.add(getCancelar(), null);
            jpPpal.add(getAceptar(), null);
            jpPpal.add(getJPDatosPers(), null);
            jpPpal.add(getJButtonImprimirTarjeta(), null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }


    private JPanel getJPDatosPers() {
    	if (jpDatosDistrib == null) {
    		jpDatosDistrib = new JPanel();
    		jpDatosDistrib.setLayout(null);
    		jpDatosDistrib.setBorder(Utils.crearTituloYBorde("Datos Comercio"));
    		jpDatosDistrib.setBounds(new java.awt.Rectangle(15,15,745,325));
    		jlNombre = new JLabel();
    		jlNombre.setBounds(new java.awt.Rectangle(12,30,80,15));
    		jlNombre.setText("Nombre (*)");
    		jlNombre.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlCuit = new JLabel();
    		jlCuit.setBounds(new java.awt.Rectangle(12,62,80,15));
    		jlCuit.setText("Cuit (*)");
    		jlCuit.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlIngBrutos = new JLabel();
    		jlIngBrutos.setBounds(new Rectangle(225,62,80,15));
    		jlIngBrutos.setText("Ingr. Brutos");
    		jlIngBrutos.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlInicioAct = new JLabel();
    		jlInicioAct.setBounds(new java.awt.Rectangle(425,62,120,15));
    		jlInicioAct.setText("Inicio de Actividades");  
    		jlInicioAct.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlTel = new JLabel();
    		jlTel.setBounds(new java.awt.Rectangle(12,126,80,15));
    		jlTel.setText("Teléfono");
    		jlTel.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlDireccion = new JLabel();
    		jlDireccion.setBounds(new java.awt.Rectangle(10,94,80,15));
    		jlDireccion.setText("Dirección (*)");
    		jlDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlLocalidad = new JLabel();
    		jlLocalidad.setBounds(new java.awt.Rectangle(430,94,76,15));
    		jlLocalidad.setText("Localidad (*)");
    		jlLocalidad.setHorizontalAlignment(SwingConstants.RIGHT);
    		jpNros = new JPanel();
    		jpNros.setLayout(null);
    		jpNros.setBorder(Utils.crearTituloYBorde("Numeración Comprobantes"));
    		jpNros.setBounds(new java.awt.Rectangle(200,190,340,120));
    		jpNros.setBackground(Utils.colorPanel);
  		
    		jlNroFactC = new JLabel();
    		jlNroFactC.setBounds(new java.awt.Rectangle(12,25,150,16));
    		jlNroFactC.setText("Próximo Nro. Factura C (*)");
    		jlNroFactC.setHorizontalAlignment(SwingConstants.RIGHT);
    		
    		jlNroRecibo = new JLabel();
    		jlNroRecibo.setBounds(new java.awt.Rectangle(12,75,150,15));
    		jlNroRecibo.setText("Próximo Nro. Recibo (*)");
    		jlNroRecibo.setHorizontalAlignment(SwingConstants.RIGHT);
    		
    		jlNroNDebito = new JLabel();
    		jlNroNDebito.setBounds(new java.awt.Rectangle(12,50,150,15));
    		jlNroNDebito.setText("Próximo Nro. N. Debito (*)");
    		jlNroNDebito.setHorizontalAlignment(SwingConstants.RIGHT);
    		
    		jpDatosDistrib.add(jlCuit, null);
    		jpDatosDistrib.add(jlIngBrutos, null);
    		jpDatosDistrib.add(jlInicioAct, null);
    		jpDatosDistrib.add(jlNombre, null);
    		jpDatosDistrib.add(jlTel, null);
    		jpDatosDistrib.add(jlDireccion, null);
    		jpDatosDistrib.add(jlLocalidad, null);
    		jpNros.add(jlNroFactC, null);
    		jpNros.add(jlNroRecibo, null);
    		jpNros.add(jlNroNDebito, null);
    		jpDatosDistrib.add(jpNros, null);
    		
    		jpDatosDistrib.add(getTelefono(), null);
    		jpDatosDistrib.add(getIngrBrutos(), null);
    		jpDatosDistrib.add(getCuit(), null);
    		jpDatosDistrib.add(getNombre(), null);
    		jpDatosDistrib.add(getLocalidad(), null);
    		jpDatosDistrib.add(getDireccion(), null);
    		jpDatosDistrib.add(getJButtonLocalidad(), null);
    		jpDatosDistrib.add(getJDateChooserFecha(), null);
    		jpNros.add(getProximoNroFactC(), null);
    		jpNros.add(getProximoNroRecibo(), null);
    		jpNros.add(getProximoNroNDebito(), null);
    		jpDatosDistrib.add(getJButtonModificarNrosFactura(),null);
    		if (dist!=null) {
    			jtfNombre.setText(dist.getNombre());
    			jtfCuit.setText(dist.getCuit());
    			jtfIngBrutos.setText(dist.getCategoria());
    			jtfTel.setText(dist.getTelefono());
    			jtfCalle.setText(dist.getDireccion());
    			jtfLocalidad.setText(dist.getLocalidad().getNombre());
    			jDataCFecha.setDate(dist.getInicioAct());
    			jtfNroFactC.setText(String.valueOf(dist.getNroFactC()));
    			jtfNroRecibo.setText(String.valueOf(dist.getNroRecibo()));
    			jtfNroNDebito.setText(String.valueOf(dist.getNroNotaDebito()));
    		}
    		jpDatosDistrib.setBackground(Utils.colorPanel);
    	}
    	return jpDatosDistrib;
    }
    
    public JTextField getTelefono() {
        if (jtfTel == null) {
        	jtfTel = new JTextField();
        	jtfTel.setBounds(new java.awt.Rectangle(100,126,315,22));
        }
        return jtfTel;
    }
    
    public JDateChooser getJDateChooserFecha() {
		if (jDataCFecha == null) {
			jDataCFecha = new JDateChooser("dd - MMMMM - yyyy",false);
			jDataCFecha.setBounds(new java.awt.Rectangle(549,62,180,22));
		}
		return jDataCFecha;
	}
    
    public JTextField getCuit() {
    	if (jtfCuit == null) {
    		jtfCuit = new JTextField();
    		jtfCuit.setBounds(new java.awt.Rectangle(100,62,120,22));
    		jtfCuit.setDocument(new LimitadorNroGuion(jtfCuit));
    	}
    	return jtfCuit;
    }
    
    public JTextField getIngrBrutos() {
    	if (jtfIngBrutos == null) {
    		jtfIngBrutos = new JTextField();
    		jtfIngBrutos.setBounds(new java.awt.Rectangle(315,62,100,22));
    	}
    	return jtfIngBrutos;
    }
    
    public JTextField getNombre() {
        if (jtfNombre == null) {
            jtfNombre = new JTextField();
            jtfNombre.setBounds(new java.awt.Rectangle(100,30,630,22));
        }
        return jtfNombre;
    }
    
    public JTextField getDireccion() {
        if (jtfCalle == null) {
        	jtfCalle = new JTextField();
        	jtfCalle.setBounds(new java.awt.Rectangle(100,94,315,22));
        }
        return jtfCalle;
    }
        
    public JTextField getLocalidad() {
    	if (jtfLocalidad == null) {
    		jtfLocalidad = new JTextField();
    		jtfLocalidad.setBounds(new java.awt.Rectangle(515,94,128,22));
    		jtfLocalidad.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfLocalidad.setEnabled(false);
    	}
    	return jtfLocalidad;
    }

    public JButton getJButtonLocalidad() {
        if (jbLocalidad == null) {
            jbLocalidad = BotonesGenericos.botonGestionBuscar(); 
            jbLocalidad.setBounds(new java.awt.Rectangle(650,94,80,22));
            jbLocalidad.setName("Buscar");
            jbLocalidad.setInputMap(0, map);
        }
        return jbLocalidad;
    }
    
    public JTextField getProximoNroFactC() {
    	if (jtfNroFactC == null) {
    		jtfNroFactC = new JTextField();
    		jtfNroFactC.setBounds(new Rectangle(170,25,150,20));
    		jtfNroFactC.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfNroFactC.setEnabled(false);
    		jtfNroFactC.setDocument(new LimitadorNroMax(jtfNroFactC,12));
    	}
    	return jtfNroFactC;
    }
    
    public JTextField getProximoNroRecibo() {
    	if (jtfNroRecibo == null) {
    		jtfNroRecibo = new JTextField();
    		jtfNroRecibo.setBounds(new Rectangle(170,75,150,20));
    		jtfNroRecibo.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfNroRecibo.setEnabled(false);
    		jtfNroRecibo.setDocument(new LimitadorNroMax(jtfNroRecibo,12));
    	}
    	return jtfNroRecibo;
    }
    
    public JTextField getProximoNroRemito() {
    	if (jtfNroRemito == null) {
    		jtfNroRemito = new JTextField();
    		jtfNroRemito.setBounds(new Rectangle(170,100,150,20));
    		jtfNroRemito.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfNroRemito.setEnabled(false);
    		jtfNroRemito.setDocument(new LimitadorNroMax(jtfNroRemito,12));
    	}
    	return jtfNroRemito;
    }
    
    public JTextField getProximoNroNDebito() {
    	if (jtfNroNDebito == null) {
    		jtfNroNDebito = new JTextField();
    		jtfNroNDebito.setBounds(new Rectangle(170,50,150,20));
    		jtfNroNDebito.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfNroNDebito.setEnabled(false);
    		jtfNroNDebito.setDocument(new LimitadorNroMax(jtfNroNDebito,12));
    	}
    	return jtfNroNDebito;
    }
       
    public JButton getCancelar() {
    	if (jbCancelar == null) {
    		jbCancelar = BotonesGenericos.botonCancelar();
    		jbCancelar.setBounds(new java.awt.Rectangle(405,360,100,30));
    		jbCancelar.setName("Cancelar");
    		jbCancelar.setInputMap(0, map);
    	}
    	return jbCancelar;
    }

    public JButton getAceptar() {
        if (jbAceptar == null) {
        	jbAceptar = BotonesGenericos.botonAceptar();
            jbAceptar.setBounds(new java.awt.Rectangle(255,360,100,30));
            jbAceptar.setName("Aceptar");
            jbAceptar.setInputMap(0, map);
        }
        return jbAceptar;
    }

    public JButton getJButtonModificarNrosFactura() {
        if (jbModNroFacts == null) {
        	jbModNroFacts = BotonesGenericos.botonEnPanel("Modificar Números",new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/edit.png"));
        	jbModNroFacts.setBounds(new java.awt.Rectangle(260,160,200,25));
        	jbModNroFacts.setName("ModNroFact");
        	jbModNroFacts.setInputMap(0, map);
        }
        return jbModNroFacts;
    }
    
    public JButton getJButtonImprimirTarjeta() {
        if (jbImprTarjeta == null) {
        	jbImprTarjeta = BotonesGenericos.botonImprimir();
        	jbImprTarjeta.setBounds(new Rectangle(30,360,100,30));
        	jbImprTarjeta.setName("Imprimir");
        	jbImprTarjeta.setInputMap(0, map);
        }
        return jbImprTarjeta;
    }
    
    public void setActionListeners(ActionListener lis){
        this.jbCancelar.addActionListener(lis);
        this.jbAceptar.addActionListener(lis);
        this.jbLocalidad.addActionListener(lis);
        this.jbImprTarjeta.addActionListener(lis);
        this.jbModNroFacts.addActionListener(lis);
    }

    public void setLocalidad(String string) {
        jtfLocalidad.setText(string);
    }
    
}
