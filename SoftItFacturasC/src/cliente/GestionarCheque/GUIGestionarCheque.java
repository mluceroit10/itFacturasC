package cliente.GestionarCheque;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

import common.Utils;

public class GUIGestionarCheque extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;			    private JPanel jpGestion = null;
	private JPanel jpDatos = null;			    private JPanel jpBuscador = null;
	private JButton jbIngresar = null;		private JButton jbModif = null;
	private JButton jbEliminar = null;    	private JButton jbSalir = null;
	private JButton jbVerificar = null;		private JButton jbPagado = null;
	private JLabel jlCodigo= null;				private JTextField jtfCodigo = null;
	private JLabel jlFecha= null;				private JTextField jtfFecha = null;
	private JLabel jlBanco= null;				private JTextField jtfBanco = null;
	private JLabel jlEstado= null;				private JTextField jtfEstado = null;
	private JLabel jlPeriodo= null;			    private JTextField jtfPeriodo = null;
	private JButton jbCambiarPeriodo= null;		
	private JComboBox jcbEstados;
	private JLabel jlMes = null;				private JComboBox jcbMes;
	private JLabel jlAnio = null;				private JTextField jtfAnio;
	private JScrollPane jspDatos = null;
	public JTable jtDatos = null;			    private ModeloTabla modTabla = null;
	public String[] titulos = {"ID","Número","Fecha Emisión","Importe","Banco","Loc-Plaza","Fecha Vencimiento","QuienEntrega","Estado"};
	public Object[][] datos;
	private int mesLI;
	private int anioLI;
	private InputMap map = new InputMap();
	
	public GUIGestionarCheque(int mes,int anio,JFrame guiPadre) {
		super(guiPadre);
		mesLI=mes;
		anioLI=anio;
		datos = new Object[0][titulos.length];
		this.setSize(new java.awt.Dimension(740,540));
		this.setLocationRelativeTo(guiPadre);
		this.setResizable(false);
		this.setTitle("Gestión de Cheques");
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
			jpPpal.add(getJPDatos(), null);
			jpPpal.add(getJPBuscador(), null);
			jpPpal.setBackground(Utils.colorFondo);
		}
		return jpPpal;
	}
	
	private JPanel getJPBuscador() {
		if (jpBuscador == null) {
			jpBuscador = new JPanel();
			jlCodigo = new JLabel();
			jlCodigo.setBounds(new Rectangle(15,30,60,15));
			jlCodigo.setText("Número:");
			jlFecha = new JLabel();
			jlFecha.setBounds(new Rectangle(15,65,100,15));
			jlFecha.setText("Quien Entrega:");
			jlBanco = new JLabel();
			jlBanco.setBounds(new Rectangle(150,30,60,15));
			jlBanco.setText("Banco:");
			jpBuscador.setLayout(null);
			jpBuscador.setBorder(Utils.crearTituloYBorde("Buscar"));
			jpBuscador.setBounds(new java.awt.Rectangle(370,25,342,110));
			jpBuscador.add(jlCodigo, null);
			jpBuscador.add(jlFecha, null);
			jpBuscador.add(jlBanco, null);
			jpBuscador.add(getJTFBuscadorCodigo(), null);
			jpBuscador.add(getJTFBuscadorFecha(), null);
			jpBuscador.add(getJTFBanco(), null);
			jpBuscador.setBackground(Utils.colorPanel);
		}
		return jpBuscador;
	}
	
	public JTextField getJTFBuscadorCodigo() {
		if (jtfCodigo == null) {
			jtfCodigo = new JTextField();
			jtfCodigo.setBounds(new Rectangle(65,30,60,22));
			jtfCodigo.setDocument(new LimitadorNroMax(jtfCodigo));
		}
		return jtfCodigo;
	}
	
	public JTextField getJTFBuscadorFecha() {
		if (jtfFecha == null) {
			jtfFecha = new JTextField();
			jtfFecha.setBounds(new Rectangle(110,65,215,22));
		}
		return jtfFecha;
	}
	
	public JTextField getJTFBanco() {
		if (jtfBanco == null) {
			jtfBanco = new JTextField();
			jtfBanco.setBounds(new Rectangle(200,30,123,22));
		}
		return jtfBanco;
	}
	
	private JPanel getJPDatos() {
		if (jpDatos == null) {
			jpDatos = new JPanel();
			jpDatos.setLayout(null);
			jpDatos.setBounds(new Rectangle(15,150,700,300));
			jpDatos.setBorder(Utils.crearTituloYBorde("Cheques"));
			jpDatos.add(getJSPDatos(), null);
			jpDatos.add(getJCBEstados(),null);
			agregarPeriodoSelec();
			jpDatos.setBackground(Utils.colorPanel);
		}
		return jpDatos;
	}
	
	private JPanel getJPGestion() {
		if (jpGestion == null) {
			jpGestion = new JPanel();
			jpGestion.setLayout(null);
			jpGestion.setBounds(new java.awt.Rectangle(15,25,340,110));
			jpGestion.setBorder(Utils.crearTituloYBorde("Gestión"));
			jpGestion.add(getJBIngresar(), null);
			jpGestion.add(getJBModificar(), null);
			jpGestion.add(getJBEliminar(), null);
			jpGestion.add(getJBVerificar(), null);
			jpGestion.add(getJBPagado(), null);
			jpGestion.setBackground(Utils.colorPanel);
		}
		return jpGestion;
	}
	public JButton getJBIngresar() {
		if (jbIngresar == null) {
			jbIngresar = BotonesGenericos.botonGestionAgregar();
			jbIngresar.setName("Alta");
			jbIngresar.setBounds(new java.awt.Rectangle(10,25,100,25));
			jbIngresar.setInputMap(0, map);
		}
		return jbIngresar;
	}
	
	public JButton getJBModificar() {
		if (jbModif == null) {
			jbModif = BotonesGenericos.botonGestionModificar();
			jbModif.setName("Modificar");
			jbModif.setBounds(new java.awt.Rectangle(120,25,100,25));
			jbModif.setInputMap(0, map);
		}
		return jbModif;
	}
	
	public JButton getJBEliminar() {
		if (jbEliminar == null) {
			jbEliminar = BotonesGenericos.botonGestionEliminar();
			jbEliminar.setName("Baja");
			jbEliminar.setBounds(new java.awt.Rectangle(230,25,100,25));
			jbEliminar.setInputMap(0, map);
		}
		return jbEliminar;
	}
	
	public JButton getJBVerificar() {
		if (jbVerificar == null) {
			jbVerificar = BotonesGenericos.botonEnPanel("VERIFICAR",null);
			jbVerificar.setName("Verificar");
			jbVerificar.setBounds(new java.awt.Rectangle(10,65,100,25));
			jbVerificar.setInputMap(0, map);
		}
		return jbVerificar;
	}
	
	public JButton getJBPagado() {
		if (jbPagado == null) {
			jbPagado = BotonesGenericos.botonEnPanel("CAMBIAR ESTADO CHEQUE",null);
			jbPagado.setName("Pagado");
			jbPagado.setBounds(new java.awt.Rectangle(120,65,210,25));
			jbPagado.setInputMap(0, map);
		}
		return jbPagado;
	}
	
	public JButton getJBAceptar() {
		if (jbSalir == null) {
			jbSalir = BotonesGenericos.botonSalir();
			jbSalir.setBounds(new java.awt.Rectangle(315,470,100,30));
			jbSalir.setInputMap(0, map);
		}
		return jbSalir;
	}
	
	private JScrollPane getJSPDatos() {
		if (jspDatos == null) {
			jspDatos = new JScrollPane();
			jspDatos.setBounds(new Rectangle(10,50,680,240));
			jspDatos.setViewportView(getJTDatos());
		}
		return jspDatos;
	}
	
	public JTable getJTDatos() {
		if (jtDatos == null) {
			modTabla = new ModeloTabla(titulos, datos);
			jtDatos = new JTable(modTabla){
				/*private static final long serialVersionUID = 1L;
				public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
					Component returnComp = super.prepareRenderer(renderer, row,column);
					String pagado=getValueAt(row,6).toString();
					if(pagado.compareTo("Si")==0){
						returnComp.setBackground(Color.WHITE);
						returnComp.setForeground(Color.BLUE);
						returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
					}else if(pagado.compareTo("No")==0){
						returnComp.setBackground(Color.WHITE);
						returnComp.setForeground(Color.RED);
						returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
					}
					int[] seleccion=this.getSelectedRows();
					for(int j=0;j<seleccion.length;j++){
						if(seleccion[j]==row){
							returnComp.setBackground(new Color(176,196,222));
							if(pagado.compareTo("Si")==0){
								returnComp.setForeground(Color.BLUE);
							}else if(pagado.compareTo("No")==0){
								returnComp.setForeground(Color.RED);
							}	
						}
					}
					return returnComp;
				}*/
			}; 
			Utils.ocultarColumnaId(jtDatos);
			TableColumn columna1 = jtDatos.getColumn("Número");
			columna1.setMaxWidth(80); 
			columna1.setPreferredWidth(80);
			columna1.setCellRenderer(Utils.alinearCentro());
			TableColumn columna2 = jtDatos.getColumn("Fecha Emisión");
			columna2.setPreferredWidth(100);
			columna2.setMaxWidth(100);
			columna2.setCellRenderer(Utils.alinearCentro());
			TableColumn columna3 = jtDatos.getColumn("Fecha Vencimiento");
			columna3.setPreferredWidth(110);
			columna3.setMaxWidth(110);
			columna3.setCellRenderer(Utils.alinearCentro());
		}
		return jtDatos;
	}
	
	public void setActionListeners(ActionListener lis) {
		jbSalir.addActionListener(lis);
		jbIngresar.addActionListener(lis);
		jbModif.addActionListener(lis);
		jbEliminar.addActionListener(lis);
		jbVerificar.addActionListener(lis);
		jbCambiarPeriodo.addActionListener(lis);
		jbPagado.addActionListener(lis);
		jcbEstados.addActionListener(lis);
	}
	
	public void repaint() {
		super.repaint();
	}
	
	public void setKeyListener(KeyListener lis) {
		jtfCodigo.addKeyListener(lis);
		jtfFecha.addKeyListener(lis);
		jtfBanco.addKeyListener(lis);
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
			jbCambiarPeriodo = BotonesGenericos.botonGestionCPeriodo();;
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
	
	public JComboBox getJCBEstados() {
		if (jcbEstados == null) {
			jcbEstados = new JComboBox();
			jcbEstados.setBounds(new java.awt.Rectangle(590,20,100,20));
			jcbEstados.setBackground(new Color(255,255,255));
			jcbEstados.setForeground(java.awt.Color.black);
			jcbEstados.addItem("Todos");
			jcbEstados.addItem("En Cartera");
			jcbEstados.addItem("Depositado");
			jcbEstados.addItem("Cobrado");
			jcbEstados.addItem("Remitido");
		/*	jcbEstados.addItem("04");
			jcbEstados.addItem("05");
			jcbEstados.addItem("06");
			jcbEstados.addItem("07");*/
			
			jcbEstados.setSelectedItem("Todos");
		}
		return jcbEstados;
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
		jlMes = new JLabel("Emitido  Mes:");
		jlMes.setBounds(new Rectangle(150,20,100,20));
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

