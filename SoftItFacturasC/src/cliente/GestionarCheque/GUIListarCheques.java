package cliente.GestionarCheque;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.InputMap;
import javax.swing.JButton;
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

import cliente.ModeloTabla;
import cliente.Recursos.util.BotonesGenericos;

import com.toedter.calendar.JDateChooser;
import common.Utils;

public class GUIListarCheques extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;				private JPanel jpDatos = null;
	private JButton jbSalir = null;				public JButton jbImprimir;
	public JScrollPane jspDatos = null;
	public JTable jtDatos = null;			    private ModeloTabla modTabla = null;
	public String[] titulos = {"Vencido","Número","Fecha Emisión","Banco","Loc-Plaza","Quien Entrega","Fecha Vencimiento","Importe"};
	public Object[][] datos;
	public Font a = new Font(Utils.tipoLetra,Font.BOLD,18);
	public JLabel leyenda=null;				
	private InputMap map = new InputMap();
	private JTextField jtfDatos=null;
	private JDateChooser jDCFechaVencimiento=null;
	private JButton jbCambiarBusq=null;
	public Vector localidades= new Vector();
	private JTextField jtfTotal;
	
	public GUIListarCheques(JDialog guiPadre) { 
		super(guiPadre);
		this.setSize(new java.awt.Dimension(700,525));
		this.setTitle("Cheques a vencer");
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
			jpPpal.setSize(new java.awt.Dimension(700,525));
			jpPpal.add(getJPDatos(),null);
			jpPpal.add(getJBSalir(),null);
			jpPpal.setBackground(Utils.colorFondo);
		}
		return jpPpal;
	}
	
	public JButton getJBSalir() {
		if (jbSalir == null) {
			jbSalir = BotonesGenericos.botonSalir();
			jbSalir.setBounds(new java.awt.Rectangle(300,455,100,30));
			jbSalir.setName("Salir");
			jbSalir.setInputMap(0, map);
		}
		return jbSalir;
	}
	
	private JPanel getJPDatos() {
		if (jpDatos == null) {
			jpDatos = new JPanel();
			jpDatos.setLayout(null);
			jpDatos.setBounds(new Rectangle(15,15,670,420));
			jpDatos.setBorder(Utils.crearTituloYBorde("Datos"));
			jpDatos.add(getJSPDatos(), null);
			jpDatos.setBackground(Utils.colorPanel);
			JLabel fecha= new JLabel();
			fecha.setBounds(25,30,130,20);
			fecha.setText("Fecha de Vencimiento:");
			JLabel total= new JLabel();
			total.setBounds(520,380,70,20);
			total.setText("Total:");
			jpDatos.add(fecha,null);
			jpDatos.add(getJTFDatos(),null);
			jpDatos.add(total,null);
			jpDatos.add(getJTFTotal(),null);
			jpDatos.add(getJDCNuevaFechaVencimiento(),null);
			jpDatos.add(getJBCambiarBusqueda(),null);
		}
		return jpDatos;
	} 
	
	private JScrollPane getJSPDatos() {
		if (jspDatos == null) {
			jspDatos = new JScrollPane();
			jspDatos.setBounds(new Rectangle(10,70,650,300));
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
					String vencido=getValueAt(row,0).toString();
					if(vencido.compareTo("No")==0){
						returnComp.setBackground(Color.WHITE);
						returnComp.setForeground(Color.BLUE);
						returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
					}else if(vencido.compareTo("Si")==0){
						returnComp.setBackground(Color.WHITE);
						returnComp.setForeground(Color.RED);
						returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
					}
					int[] seleccion=this.getSelectedRows();
					for(int j=0;j<seleccion.length;j++){
						if(seleccion[j]==row){
							returnComp.setBackground(new Color(176,196,222));
							if(vencido.compareTo("No")==0){
								returnComp.setForeground(Color.BLUE);
							}else if(vencido.compareTo("Si")==0){
								returnComp.setForeground(Color.RED);
							}	
						}
					}
					return returnComp;
				}
			}; 
			
			TableColumn columna1 = jtDatos.getColumn("Número");
			columna1.setMaxWidth(80); 
			columna1.setPreferredWidth(80);
			columna1.setCellRenderer(Utils.alinearCentro());
			TableColumn columna2 = jtDatos.getColumn("Fecha Emisión");
			columna2.setMaxWidth(100); 
			columna2.setPreferredWidth(100);
			columna2.setCellRenderer(Utils.alinearCentro());
			TableColumn columna3 = jtDatos.getColumn("Fecha Vencimiento");
			columna3.setPreferredWidth(110);
			columna3.setMaxWidth(110);
			columna3.setCellRenderer(Utils.alinearCentro());
			TableColumn columna5 = jtDatos.getColumn("Importe");
			columna5.setPreferredWidth(100);
			columna5.setCellRenderer(Utils.alinearDerecha());	
			
		}
		return jtDatos;
	} 
	
	public void setActionListeners(ActionListener lis) {
		jbSalir.addActionListener(lis);
		jbCambiarBusq.addActionListener(lis);
	}
	
	public void setListSelectionListener(ListSelectionListener lis) {
		jtDatos.getSelectionModel().addListSelectionListener(lis);
	}
	
	public JButton getJBCambiarBusqueda() {
		if (jbCambiarBusq == null) {
			jbCambiarBusq = new JButton();
			jbCambiarBusq.setBounds(new java.awt.Rectangle(500,30,150,20));
			jbCambiarBusq.setBorder(Utils.crearRebordeBoton());
			jbCambiarBusq.setText("Cambiar Búsqueda");
			jbCambiarBusq.setName("cambiarFecha");
			jbCambiarBusq.setInputMap(0, map);
		}
		return jbCambiarBusq;
	}
	
	public JTextField getJTFDatos() {
		if (jtfDatos == null) {
			jtfDatos = new JTextField();
			jtfDatos.setBounds(new Rectangle(165,30,80,20));
			jtfDatos.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfDatos.setEnabled(false);
		}
		return jtfDatos;
	}
	
	public JTextField getJTFTotal() {
		if (jtfTotal == null) {
			jtfTotal = new JTextField();
			jtfTotal.setBounds(new Rectangle(560,380,100,20));
			jtfTotal.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfTotal.setHorizontalAlignment(SwingConstants.RIGHT);
			jtfTotal.setEnabled(false);
		}
		return jtfTotal;
	}
	
	public JDateChooser getJDCNuevaFechaVencimiento(){
		if (jDCFechaVencimiento == null) {
			jDCFechaVencimiento = new JDateChooser("dd - MMMMM - yyyy",false);
			jDCFechaVencimiento.setBounds(new java.awt.Rectangle(310,30,170,20));
		}
		return jDCFechaVencimiento;
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