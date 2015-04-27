package cliente.GestionarNotaDebito;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import cliente.LimitadorNroMax;
import cliente.ModeloTabla;
import cliente.Recursos.util.BotonesGenericos;

import common.RootAndIp;
import common.Utils;

public class GUIGestionarNotaDebito extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;			    private JPanel jpGestion = null;
	private JPanel jpDatos = null;			    private JPanel jpBuscador = null;
	private JButton jbImprimir = null;			private JButton jbCambiarPeriodo= null;		
	private JButton jbSalir = null;		   		private JButton jbBorrar = null;
	private JLabel jlFechaMov = null;			private JTextField jtfFecha = null;
	private JLabel jlCliente= null;				private JTextField jtfCliente = null;
	private JLabel jlPeriodo= null;			    private JTextField jtfPeriodo = null;
	private JLabel jlMes = null;				private JComboBox jcbMes;
	private JLabel jlAnio = null;				private JTextField jtfAnio;
	private JLabel jlFecha= null;				
	private JScrollPane jspDatos = null;
	public JTable jtDatos = null;			    private ModeloTabla modTabla = null;
	public String[] titulos = {"ID","Nro. Nota Débito","Fecha","Cliente","Importe","Tipo - Nro. Factura","Anulada"};
	public Object[][] datos;
	private int mesLI;
	private int anioLI;
	private InputMap map = new InputMap();
	
	public GUIGestionarNotaDebito(int mes,int anio,JFrame guiPadre) {
		super(guiPadre);
		mesLI=mes;
		anioLI=anio;
		datos = new Object[0][titulos.length];
		this.setSize(new java.awt.Dimension(740,540));
		this.setLocationRelativeTo(guiPadre);
		this.setResizable(false);
		this.setTitle("Gestión de Notas de Débito");
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
			jpPpal.add(getJBImprimir(), null);
			jpPpal.add(getJPDatos(), null);
			jpPpal.add(getJPBuscador(), null);
			jpPpal.setBackground(Utils.colorFondo);
		}
		return jpPpal;
	}
	
	private JPanel getJPBuscador() {
		if (jpBuscador == null) {
			jpBuscador = new JPanel();
			jlCliente = new JLabel();
			jlCliente.setBounds(new Rectangle(240,30,50,15));
			jlCliente.setText("Cliente:");
			jlFecha = new JLabel();
			jlFecha.setBounds(new Rectangle(15,30,50,15));
			jlFecha.setText("Fecha:");
			jpBuscador.setLayout(null);
			jpBuscador.setBorder(Utils.crearTituloYBorde("Buscar"));
			jpBuscador.setBounds(new java.awt.Rectangle(200,25,515,65));
			jpBuscador.add(jlCliente, null);
			jpBuscador.add(jlFecha, null);
			jpBuscador.add(getJLFechaMov(), null);
			jpBuscador.add(getJTFBuscadorCliente(), null);
			jpBuscador.add(getJTFBuscadorFecha(), null);
			jpBuscador.setBackground(Utils.colorPanel);
		}
		return jpBuscador;
	}
	
	public JTextField getJTFBuscadorCliente() {
		if (jtfCliente == null) {
			jtfCliente = new JTextField();
			jtfCliente.setBounds(new Rectangle(290,30,200,22));
		}
		return jtfCliente;
	}
	
	public JTextField getJTFBuscadorFecha() {
		if (jtfFecha == null) {
			jtfFecha = new JTextField();
			jtfFecha.setBounds(new Rectangle(65,30,100,22));
		}
		return jtfFecha;
	}
	
	private JLabel getJLFechaMov() {
		if (jlFechaMov == null) {
			jlFechaMov = new JLabel("(dd/mm/aaaa)");
			jlFechaMov.setBounds(new java.awt.Rectangle(73,16,80,9));
		}
		return jlFechaMov;
	}
	
	private JPanel getJPDatos() {
		if (jpDatos == null) {
			jpDatos = new JPanel();
			jpDatos.setLayout(null);
			jpDatos.setBounds(new Rectangle(15,110,700,340));
			jpDatos.setBorder(Utils.crearTituloYBorde("Notas de Débito"));
			jpDatos.add(getJSPDatos(), null);
			agregarPeriodoSelec();
			jpDatos.setBackground(Utils.colorPanel);
		}
		return jpDatos;
	}
	
	private JPanel getJPGestion() {
		if (jpGestion == null) {
			jpGestion = new JPanel();
			jpGestion.setLayout(null);
			jpGestion.setBounds(new Rectangle(15,25,140,65));
			jpGestion.setBorder(Utils.crearTituloYBorde("Gestión"));
			jpGestion.add(getJBBorrar(), null);
			jpGestion.setBackground(Utils.colorPanel);
		}
		return jpGestion;
	}
	
	public JButton getJBBorrar() {
		if (jbBorrar == null) {
			jbBorrar = BotonesGenericos.botonEnPanel("ANULAR N. D.",new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/delete.png"));
			jbBorrar.setBounds(new Rectangle(20,25,100,25));
			jbBorrar.setInputMap(0, map);
		}
		return jbBorrar;
	}
	
	public JButton getJBAceptar() {
		if (jbSalir == null) {
			jbSalir = BotonesGenericos.botonSalir();
			jbSalir.setBounds(new java.awt.Rectangle(240,470,100,30));
			jbSalir.setInputMap(0, map);
		}
		return jbSalir;
	}
	
	public JButton getJBImprimir() {
		if (jbImprimir == null) {
			jbImprimir = BotonesGenericos.botonImprimir();
			jbImprimir.setBounds(new java.awt.Rectangle(390,470,100,30));
			jbImprimir.setInputMap(0, map);
		}
		return jbImprimir;
	}
	
	private JScrollPane getJSPDatos() {
		if (jspDatos == null) {
			jspDatos = new JScrollPane();
			jspDatos.setBounds(new Rectangle(10,50,680,280));
			jspDatos.setViewportView(getJTDatos());
		}
		return jspDatos;
	}
	
	public JTable getJTDatos() {
		if (jtDatos == null) {
			modTabla = new ModeloTabla(titulos, datos);
			jtDatos = new JTable(modTabla){
				private static final long serialVersionUID = 1L;
				public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
					Component returnComp = super.prepareRenderer(renderer, row,column);
					String anulada=getValueAt(row,6).toString();
					if(anulada.compareTo("SI")==0){
						returnComp.setBackground(Color.WHITE);
						returnComp.setForeground(Color.RED);
						returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
					}else{
						returnComp.setBackground(Color.WHITE);
						returnComp.setForeground(Color.BLACK);
						returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
					}
					int[] seleccion=this.getSelectedRows();
					for(int j=0;j<seleccion.length;j++){
						if(seleccion[j]==row){
							returnComp.setBackground(new Color(176,196,222));
							returnComp.setForeground(Color.BLACK);
						}
					}
					return returnComp;
				}
			};
			Utils.ocultarColumnaId(jtDatos);
			TableColumn columna1 = jtDatos.getColumn("Nro. Nota Débito");
			columna1.setPreferredWidth(110);
			columna1.setMaxWidth(110);
			columna1.setCellRenderer(Utils.alinearCentro());
			TableColumn columna2 = jtDatos.getColumn("Fecha");
			columna2.setPreferredWidth(75);
			columna2.setMaxWidth(75);
			TableColumn columna4 = jtDatos.getColumn("Importe");
			columna4.setPreferredWidth(70);
			columna4.setCellRenderer(Utils.alinearDerecha());
			TableColumn columna7 = jtDatos.getColumn("Anulada");
			columna7.setPreferredWidth(50);
			columna7.setMaxWidth(50); 
			columna7.setCellRenderer(Utils.alinearCentro());
			TableColumn columna8 = jtDatos.getColumn("Tipo - Nro. Factura");
			columna8.setPreferredWidth(130);
			columna8.setMaxWidth(130); 
			columna8.setCellRenderer(Utils.alinearCentro());
			
		}
		return jtDatos;
	}
	
	public void setActionListeners(ActionListener lis) {
		jbSalir.addActionListener(lis);
		jbBorrar.addActionListener(lis);
		jbImprimir.addActionListener(lis);
		jbCambiarPeriodo.addActionListener(lis);
	}
	
	public void repaint() {
		super.repaint();
	}
	
	public void setKeyListener(KeyListener lis) {
		jtfCliente.addKeyListener(lis);
		jtfFecha.addKeyListener(lis);
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
	
	
	public JButton getJBCambiarPeriodo() {
		if (jbCambiarPeriodo == null) {
			jbCambiarPeriodo = BotonesGenericos.botonGestionCPeriodo();
			jbCambiarPeriodo.setBounds(new java.awt.Rectangle(450,20,115,20));
			jbCambiarPeriodo.setInputMap(0, map);
			
		}
		return jbCambiarPeriodo;
	}
	
	public JTextField getJTFPeriodo() {
		if (jtfPeriodo == null) {
			jtfPeriodo = new JTextField();
			jtfPeriodo.setBounds(new java.awt.Rectangle(85,20,70,20));
			jtfPeriodo.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfPeriodo.setEnabled(false);
		}
		return jtfPeriodo;
	}
	
	public JComboBox getJCBMes() {
		if (jcbMes == null) {
			jcbMes = new JComboBox();
			jcbMes.setBounds(new java.awt.Rectangle(255,20,60,20));
			jcbMes.setBackground(new Color(255,255,255));
			jcbMes.setForeground(java.awt.Color.black);
			jcbMes.addItem("01");
			jcbMes.addItem("02");
			jcbMes.addItem("03");
			jcbMes.addItem("04");
			jcbMes.addItem("05");
			jcbMes.addItem("06");
			jcbMes.addItem("07");
			jcbMes.addItem("08");
			jcbMes.addItem("09");
			jcbMes.addItem("10");
			jcbMes.addItem("11");
			jcbMes.addItem("12");
			jcbMes.setSelectedIndex(mesLI-1);
		}
		return jcbMes;
	}
	
	public JTextField getJTFAnio() {
		if (jtfAnio == null) {
			jtfAnio = new JTextField();
			jtfAnio.setBounds(new java.awt.Rectangle(370,20,60,20));
			jtfAnio.setDocument(new LimitadorNroMax(jtfAnio,4));
			jtfAnio.setText(String.valueOf(anioLI));
		}
		return jtfAnio;
	}
	
	public void agregarPeriodoSelec(){
		jlPeriodo = new JLabel("Período:");
		jlPeriodo.setHorizontalAlignment(JLabel.RIGHT);
		jlPeriodo.setBounds(new Rectangle(20,20,60,20));
		jlMes = new JLabel("Mes:");
		jlMes.setBounds(new Rectangle(200,20,50,20));
		jlMes.setHorizontalAlignment(SwingConstants.RIGHT);
		jlAnio = new JLabel("Año:");
		jlAnio.setBounds(new Rectangle(325,20,40,20));
		jlAnio.setHorizontalAlignment(SwingConstants.RIGHT);
		jpDatos.add(jlPeriodo);
		jpDatos.add(jlMes);
		jpDatos.add(jlAnio);
		jpDatos.add(getJTFPeriodo());
		jpDatos.add(getJCBMes());
		jpDatos.add(getJTFAnio());
		jpDatos.add(getJBCambiarPeriodo());
	}
}

