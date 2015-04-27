package cliente.GestionarCliente;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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

public class GUICuentaCliente extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;				private JPanel jpCompromisos = null;
	private JButton jbSalir = null;
	public JScrollPane jspDatos = null;
	public JTable jtDatos = null;			    private ModeloTabla modTabla = null;
	public String[] titulos = {"Detalle","Fecha","Debe","Haber","Saldo"};
	public Object[][] datos;
	public JButton jbImprimir;
	private Vector detalleItM= new Vector();
	private Vector fechaM = new Vector();
	private Vector debeM= new Vector();
	private Vector haberM= new Vector();
	private Vector saldoM= new Vector();
	private double saldoActual=0;
	public JLabel leyenda=null;
	private JCheckBox jCheckSelectAll = null;
	private InputMap map = new InputMap();
	private JLabel jlPeriodo= null;			    private JTextField jtfPeriodo = null;
	private JButton jbCambiarPeriodo= null;		
	private JLabel jlMes = null;				private JComboBox jcbMes;
	private JLabel jlAnio = null;				private JTextField jtfAnio;
	private int mesLI;
	private int anioLI;
	
	public GUICuentaCliente(int mes,int anio,Vector detalleIt, Vector fecha2, Vector debe, Vector haber, Vector saldo, String cliente,JDialog guiPadre, double saldoAct) {
		super(guiPadre);
		mesLI=mes;
		anioLI=anio;
		detalleItM=detalleIt;
		fechaM=fecha2;
		debeM=debe;
		haberM=haber;
		saldoM=saldo;
		saldoActual=saldoAct;
		this.setSize(new java.awt.Dimension(700,500));
		this.setTitle("Estado de Cuenta del Cliente: "+cliente);
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
			jpPpal.add(getJPCompromisos(),null);
			jpPpal.add(getJBImprimir(),null);
			jpPpal.add(getJBSalir(),null);
			jpPpal.setBackground(Utils.colorFondo);
		}
		return jpPpal;
	}
	
	public JButton getJBImprimir() {
		if (jbImprimir == null) {
			jbImprimir = BotonesGenericos.botonImprimir();
			jbImprimir.setBounds(new java.awt.Rectangle(220,430,100,30));
			jbImprimir.setName("Imprimir");
			jbImprimir.setInputMap(0, map);
		}
		return jbImprimir;
	}
	
	public JButton getJBSalir() {
		if (jbSalir == null) {
			jbSalir = BotonesGenericos.botonSalir();
			jbSalir.setBounds(new java.awt.Rectangle(370,430,100,30));
			jbSalir.setName("Salir");
			jbSalir.setInputMap(0, map);
		}
		return jbSalir;
	}
	
	private JPanel getJPCompromisos() {
		if (jpCompromisos == null) {
			jpCompromisos = new JPanel();
			jpCompromisos.setLayout(null);
			jpCompromisos.setBounds(new Rectangle(15,15,670,395));
			jpCompromisos.setBorder(Utils.crearTituloYBordeDestacado(" Movimientos de la Cuenta "));
			agregarPeriodoSelec();
			leyenda= new JLabel();
			leyenda.setBounds(new java.awt.Rectangle(370,366,289,20));
			leyenda.setText("Ante un importe negativo el cliente registra deuda");
			
			JLabel lSaldo = new JLabel();
			lSaldo.setBounds(new java.awt.Rectangle(20,340,111,20));
			java.util.Date hoy= new java.util.Date();
			lSaldo.setText("SALDO ACTUAL");
			JLabel lFechaact = new JLabel();
			lFechaact.setBounds(new java.awt.Rectangle(295,340,80,20));
			lFechaact.setText(Utils.getStrUtilDate(hoy));
			JLabel lSaldoAct = new JLabel();
			lSaldoAct.setBounds(new java.awt.Rectangle(550,340,90,20));
			lSaldoAct.setText(Utils.ordenarDosDecimales(saldoActual));
			lSaldoAct.setHorizontalAlignment(SwingConstants.RIGHT);
			if(saldoActual>=0){
				lSaldo.setForeground(Color.BLUE);
				lSaldo.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
				lFechaact.setForeground(Color.BLUE);
				lFechaact.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
				lSaldoAct.setForeground(Color.BLUE);
				lSaldoAct.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			}else{
				lSaldo.setForeground(Color.RED);
				lSaldo.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
				lFechaact.setForeground(Color.RED);
				lFechaact.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
				lSaldoAct.setForeground(Color.RED);
				lSaldoAct.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			}
			
			//saldoActual
			jpCompromisos.add(leyenda, null);
			jpCompromisos.add(lSaldo, null);
			jpCompromisos.add(lFechaact, null);
			jpCompromisos.add(lSaldoAct, null);
			jpCompromisos.add(getJSPDatos(), null);
			jpCompromisos.setBackground(Utils.colorPanel);
			jpCompromisos.add(getJCheckSelectAll(), null);
		}
		return jpCompromisos;
	} 
	
	private JScrollPane getJSPDatos() {
		if (jspDatos == null) {
			jspDatos = new JScrollPane();
			jspDatos.setBounds(new Rectangle(10,70,650,260));
			jspDatos.setViewportView(getJTDatos());
		}
		return jspDatos;
	}
	
	public JTable getJTDatos() {
		if (jtDatos == null) {
			datos= new Object[detalleItM.size()][titulos.length];
			for(int i=0; i<detalleItM.size();i++){
				Object[] temp = {detalleItM.elementAt(i),fechaM.elementAt(i),
						debeM.elementAt(i),haberM.elementAt(i),saldoM.elementAt(i)};
				datos[i] = temp;
			}		
			modTabla = new ModeloTabla(titulos, datos);
			jtDatos = new JTable(modTabla){
				private static final long serialVersionUID = 1L;
				public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
					Component returnComp = super.prepareRenderer(renderer, row,column);
					String detalle=getValueAt(row,0).toString();
					if(detalle.compareTo("SALDO ACTUAL")==0){
						float impRestante= Float.parseFloat(getValueAt(row,4).toString());
						if(impRestante<0){
							returnComp.setBackground(Color.WHITE);
							returnComp.setForeground(Color.RED);
							returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
						}else{
							returnComp.setBackground(Color.WHITE);
							returnComp.setForeground(Color.BLUE);
							returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
						}
					}else{
						returnComp.setBackground(Color.WHITE);
						returnComp.setForeground(Color.BLACK);
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
			TableColumn columna1 = jtDatos.getColumn("Fecha");
			columna1.setPreferredWidth(80);
			columna1.setMaxWidth(80);
			columna1.setCellRenderer(Utils.alinearCentro());
			TableColumn columna2 = jtDatos.getColumn("Debe");
			columna2.setPreferredWidth(90);
			columna2.setMaxWidth(90);
			columna2.setCellRenderer(Utils.alinearDerecha());
			TableColumn columna3 = jtDatos.getColumn("Haber");
			columna3.setPreferredWidth(90);
			columna3.setMaxWidth(90);
			columna3.setCellRenderer(Utils.alinearDerecha());
			TableColumn columna4 = jtDatos.getColumn("Saldo");
			columna4.setPreferredWidth(90);
			columna4.setMaxWidth(90);
			columna4.setCellRenderer(Utils.alinearDerecha());
		}
		return jtDatos;
	} 
	
	public void setActionListeners(ActionListener lis) {
		jbSalir.addActionListener(lis);
		jbImprimir.addActionListener(lis);
		jCheckSelectAll.addActionListener(lis);
		jbCambiarPeriodo.addActionListener(lis);
	}
	
	public void setListSelectionListener(ListSelectionListener lis) {
		jtDatos.getSelectionModel().addListSelectionListener(lis);
	}
	
	public JCheckBox getJCheckSelectAll() {
		if (jCheckSelectAll == null) {
			jCheckSelectAll = new JCheckBox();
			jCheckSelectAll.setBounds(new java.awt.Rectangle(10,368,130,17));
			jCheckSelectAll.setText("Seleccionar Todo");
			jCheckSelectAll.setName("SelectAll");
			jCheckSelectAll.setBackground(Utils.colorPanel);
		}
		return jCheckSelectAll;
	}
	public JButton getJBCambiarPeriodo() {
		if (jbCambiarPeriodo == null) {
			jbCambiarPeriodo = BotonesGenericos.botonGestionCPeriodo();
			jbCambiarPeriodo.setBounds(new java.awt.Rectangle(450,30,115,20));
			jbCambiarPeriodo.setInputMap(0, map);
			
		}
		return jbCambiarPeriodo;
	}
	
	public JTextField getJTFPeriodo() {
		if (jtfPeriodo == null) {
			jtfPeriodo = new JTextField();
			jtfPeriodo.setBounds(new java.awt.Rectangle(85,30,70,20));
			jtfPeriodo.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfPeriodo.setEnabled(false);
		}
		return jtfPeriodo;
	}
	
	public JComboBox getJCBMes() {
		if (jcbMes == null) {
			jcbMes = new JComboBox();
			jcbMes.setBounds(new java.awt.Rectangle(255,30,60,20));
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
			jtfAnio.setBounds(new java.awt.Rectangle(370,30,60,20));
			jtfAnio.setDocument(new LimitadorNroMax(jtfAnio,4));
			jtfAnio.setText(String.valueOf(anioLI));
		}
		return jtfAnio;
	}
	
	public void agregarPeriodoSelec(){
		jlPeriodo = new JLabel("Período:");
		jlPeriodo.setHorizontalAlignment(JLabel.RIGHT);
		jlPeriodo.setBounds(new Rectangle(20,30,60,20));
		jlMes = new JLabel("Mes:");
		jlMes.setBounds(new Rectangle(200,30,50,20));
		jlMes.setHorizontalAlignment(SwingConstants.RIGHT);
		jlAnio = new JLabel("Año:");
		jlAnio.setBounds(new Rectangle(325,30,40,20));
		jlAnio.setHorizontalAlignment(SwingConstants.RIGHT);
		jpCompromisos.add(jlPeriodo);
		jpCompromisos.add(jlMes);
		jpCompromisos.add(jlAnio);
		jpCompromisos.add(getJTFPeriodo());
		jpCompromisos.add(getJCBMes());
		jpCompromisos.add(getJTFAnio());
		jpCompromisos.add(getJBCambiarPeriodo());
	}
	
	public void actualizarTabla(){
		jpPpal.remove(getJPCompromisos());
		jpCompromisos = null;
		jtDatos = null;
		modTabla = null;
		jspDatos = null;
		jpPpal.add(getJPCompromisos(), null);
	}
}