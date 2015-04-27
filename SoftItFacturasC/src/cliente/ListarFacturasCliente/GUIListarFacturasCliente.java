package cliente.ListarFacturasCliente;

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

public class GUIListarFacturasCliente extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;			   
	private JPanel jpDatos = null;			    private JPanel jpBuscador = null;
	private JButton jbImprimir = null;			private JButton jbAceptar = null;		
	private JButton jBFiltrar = null;			private JButton jbCambiarPeriodo= null;	
	private JButton jbAnular = null;			private JButton jbImprimirTabla = null;
	private JLabel fecha = null;				private JTextField jtfFecha = null;
	private JLabel nro = null; 					private JTextField jtfNro = null;
	private JLabel cliente = null;				private JTextField jtfCliente = null;
	private JLabel jlPeriodo= null;			    private JTextField jtfPeriodo = null;
	private JLabel jlMes = null;				private JComboBox jcbMes;
	private JLabel jlAnio = null;				private JTextField jtfAnio;
	private JTextField jtfNombre = null;		

	public JScrollPane jspDatos = null;
	public JTable jtDatos = null;				private ModeloTabla modTabla = null;
	public String[] titulos = {"ID","Fecha","Tipo Fact.","Nro de Factura - Remito Nro","Cliente","Importe Total", "Importe Abonado", "Fecha de Pago","Cod. Movs. Caja","Anulada"};
	public Object[][] datos;
	private int mesLI;							private int anioLI;
	private InputMap map = new InputMap();
	
	
	public GUIListarFacturasCliente(int mes,int anio,JFrame guiPadre) {
		super(guiPadre);
		mesLI=mes;
		anioLI=anio;
		datos = new Object[0][titulos.length];
		this.setSize(new java.awt.Dimension(740,560));
		this.setLocationRelativeTo(guiPadre);
		this.setResizable(false);
		this.setTitle("Facturas Cliente Existentes");
		this.setContentPane(getJPPpal());
		this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}
	
	public GUIListarFacturasCliente(int mes,int anio,JDialog guiPadre) {
		super(guiPadre);
		mesLI=mes;
		anioLI=anio;
		datos = new Object[0][titulos.length];
		this.setSize(new java.awt.Dimension(740,560));
		this.setLocationRelativeTo(guiPadre);
		this.setResizable(false);
		this.setTitle("Facturas Cliente Existentes");
		this.setContentPane(getJPPpal());
		this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}
	
	public JPanel getJPPpal() {
		if (jpPpal == null) {
			jpPpal = new JPanel();
			jpPpal.setLayout(null);
			jpPpal.add(getJBImprimir(), null);
			jpPpal.add(getJBSalir(), null);
			jpPpal.add(getJPDatos(), null);
			jpPpal.add(getJPBuscador(), null);
			jpPpal.setBackground(Utils.colorFondo);
		}
		return jpPpal;
	}
	
	private JPanel getJPBuscador() {
		if (jpBuscador == null) {
			JLabel jlForm = new JLabel();
			jlForm.setBounds(new Rectangle(80,45,80,9));
			jlForm.setText("(dd/mm/aaaa)");
			jpBuscador = new JPanel();
			jpBuscador.setLayout(null);
			jpBuscador.setBorder(Utils.crearTituloYBorde("Buscar"));
			jpBuscador.setBounds(new Rectangle(15,10,700,60));
			jpBuscador.add(jlForm, null);
			jpBuscador.add(getJLNroP(), null);
			jpBuscador.add(getJLCliente(), null);
			jpBuscador.add(getJTFNro(), null);
			jpBuscador.add(getJLFecha(), null);
			jpBuscador.add(getJTFFecha(), null);
			jpBuscador.add(getJTFCliente(), null);
			jpBuscador.add(getJBFiltrar(), null);
			jpBuscador.setBackground(Utils.colorPanel);
		}
		return jpBuscador;
	}
	
	private JLabel getJLFecha() {
		if (fecha == null) {
			fecha = new JLabel("Fecha:");
			fecha.setHorizontalAlignment(JLabel.RIGHT);
			fecha.setBounds(new java.awt.Rectangle(24,25,40,15));
		}
		return fecha;
	}
	
	private JLabel getJLNroP() {
		if (nro == null) {
			nro = new JLabel("Nro de Factura:");
			nro.setHorizontalAlignment(JLabel.RIGHT);
			nro.setBounds(new Rectangle(170,25,100,15));
		}
		return nro;
	}
	
	private JLabel getJLCliente() {
		if (cliente == null) {
			cliente = new JLabel("Nombre del Cliente:");
			cliente.setHorizontalAlignment(JLabel.RIGHT);
			cliente.setBounds(new Rectangle(380,25,120,15));
		}
		return cliente;
	}
	
	public JTextField getJTFFecha() {
		if (jtfFecha == null) {
			jtfFecha = new JTextField();
			jtfFecha.setBounds(new Rectangle(80,22,80,22));
		}
		return jtfFecha;
	}
	
	public JTextField getJTFNro() {
		if (jtfNro == null) {
			jtfNro = new JTextField();
			jtfNro.setBounds(new Rectangle(280,22,80,22));
		}
		return jtfNro;
	}
	
	public JTextField getJTFCliente() {
		if (jtfCliente == null) {
			jtfCliente = new JTextField();
			jtfCliente.setBounds(new Rectangle(510,22,140,22));
		}
		return jtfCliente;
	}
	
	public JButton getJBFiltrar() {
		if (jBFiltrar == null) {
			jBFiltrar= new JButton();
			jBFiltrar.setBounds(new java.awt.Rectangle(660,17,32,32));
			jBFiltrar.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/filtro.png"));
			jBFiltrar.setBorder(Utils.crearRebordeBoton());
			jBFiltrar.setInputMap(0, map);
		} 
		return jBFiltrar;
	}
	
	public JButton getJBAnularFactura() {
		if (jbAnular == null) {
			jbAnular = BotonesGenericos.botonEnPanel("ANULAR FACTURA",new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/delete.png"));;
			jbAnular.setBounds(new java.awt.Rectangle(10,350,150,25));
			jbAnular.setInputMap(0, map);
		}
		return jbAnular;
	}
	
	public JButton getJBImprimirTabla() {
		if (jbImprimirTabla == null) {
			jbImprimirTabla = BotonesGenericos.botonEnPanel("IMPRIMIR LISTADO",new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/printer.png"));;
			jbImprimirTabla.setBounds(new java.awt.Rectangle(540,350,150,25));
			jbImprimirTabla.setInputMap(0, map);
		}
		return jbImprimirTabla;
	}
	
	private JPanel getJPDatos() {
		if (jpDatos == null) {
			jpDatos = new JPanel();
			jpDatos.setLayout(null);
			jpDatos.setBounds(new Rectangle(15,80,700,390));
			jpDatos.setBorder(Utils.crearTituloYBorde("Facturas Cliente"));
			jpDatos.add(getJSPDatos(), null);
			jpDatos.add(getJBAnularFactura(),null);
			jpDatos.add(getJBImprimirTabla(),null);
			agregarPeriodoSelec();
			jpDatos.setBackground(Utils.colorPanel);
		}
		return jpDatos;
	}
	
	
	public JButton getJBImprimir() {
		if (jbImprimir == null) {
			jbImprimir = BotonesGenericos.botonImprimir();
			jbImprimir.setBounds(new java.awt.Rectangle(390,490,100,30));
			jbImprimir.setInputMap(0, map);
		}
		return jbImprimir;
	}
	
	public JButton getJBSalir() {
		if (jbAceptar  == null) {
			jbAceptar = BotonesGenericos.botonAceptar();
			jbAceptar.setBounds(new Rectangle(240,490,100,30));
			jbAceptar.setInputMap(0, map);
		}
		return jbAceptar;
	}
	
	private JScrollPane getJSPDatos() {
		if (jspDatos == null) {
			jspDatos = new JScrollPane();
			jspDatos.setBounds(new Rectangle(10,50,680,290));
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
					String anulada=getValueAt(row,9).toString();
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
			TableColumn columna1 = jtDatos.getColumn("Fecha");
			columna1.setMaxWidth(75); 
			columna1.setCellRenderer(Utils.alinearCentro());
			TableColumn columna2 = jtDatos.getColumn("Tipo Fact.");
			columna2.setPreferredWidth(60); 
			columna2.setMaxWidth(60);
			columna2.setCellRenderer(Utils.alinearCentro());
			TableColumn columna3 = jtDatos.getColumn("Nro de Factura - Remito Nro");
			columna3.setPreferredWidth(110); 
			columna3.setMaxWidth(200);
			TableColumn columna4 = jtDatos.getColumn("Importe Total");
			columna4.setMaxWidth(80); 
			columna4.setCellRenderer(Utils.alinearDerecha());
			TableColumn columna5 = jtDatos.getColumn("Importe Abonado");
			columna5.setPreferredWidth(100);
			columna5.setMaxWidth(100);
			columna5.setCellRenderer(Utils.alinearDerecha());
			TableColumn columna6 = jtDatos.getColumn("Fecha de Pago");
			columna6.setPreferredWidth(90);
			columna6.setMaxWidth(90); 
			columna6.setCellRenderer(Utils.alinearCentro());
			TableColumn columna8 = jtDatos.getColumn("Anulada");
			columna8.setPreferredWidth(50);
			columna8.setMaxWidth(50); 
			columna8.setCellRenderer(Utils.alinearCentro());
		}
		return jtDatos;
	}
	
	public JTextField getJTFBuscador() {
		if (jtfNombre == null) {
			jtfNombre = new JTextField();
			jtfNombre.setBounds(new Rectangle(10,45,110,22));
		}
		return jtfNombre;
	}
	
	public void setActionListeners(ActionListener lis) {
		jbImprimir.addActionListener(lis);
		jbAceptar.addActionListener(lis);
		jbAnular.addActionListener(lis);
		jbImprimirTabla.addActionListener(lis);
		jbCambiarPeriodo.addActionListener(lis);
		jBFiltrar.addActionListener(lis);
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
			jbCambiarPeriodo  = BotonesGenericos.botonGestionCPeriodo();
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
	
	public void setKeyListener(KeyListener lis) {
	    /*    jtfFecha.addKeyListener(lis);
	        jtfNro.addKeyListener(lis);
	        jtfCliente.addKeyListener(lis);*/
	    }
	
}

