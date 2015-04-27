package cliente.ListarDeudaClientes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import cliente.ModeloTabla;
import cliente.Recursos.util.BotonesGenericos;

import common.Utils;

public class GUIListarDeudaClientes extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;				private JPanel jpDeudas = null;
	private JButton jbSalir = null;				public JButton jbImprimir = null;
	private JButton jbCambiarBusq=null;
	public JScrollPane jspDatos = null;
	public JTable jtDatos = null;			    private ModeloTabla modTabla = null;
	public String[] titulos = {"Código Cte","Cliente","Fecha Ultima Facturación","Saldo a Favor","Adeudado"};
	public Object[][] datos;
	public Font a = new Font(Utils.tipoLetra,Font.BOLD,18);
	public JLabel leyenda=null;					
	private JCheckBox jCheckSelectAll = null;
	private InputMap map = new InputMap();
	private JTextField jtfDatos=null;			private JTextField jtfDatosLoc=null;
	private JComboBox jCTipoCte=null;			private JComboBox JCLocalidades=null;
	public Vector localidades= new Vector();
	
	public GUIListarDeudaClientes(JFrame guiPadre) {
		super(guiPadre);
		this.setSize(new java.awt.Dimension(700,525));
		this.setTitle("Deudas Pendientes");
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
			jpPpal.add(getJPDeudas(),null);
			jpPpal.add(getJBImprimir(),null);
			jpPpal.add(getJBSalir(),null);
			jpPpal.setBackground(Utils.colorFondo);
		}
		return jpPpal;
	}
	
	public JButton getJBImprimir() {
		if (jbImprimir == null) {
			jbImprimir = BotonesGenericos.botonImprimir();
			jbImprimir.setBounds(new java.awt.Rectangle(220,455,100,30));
			jbImprimir.setName("Imprimir");
			jbImprimir.setInputMap(0, map);
		}
		return jbImprimir;
	}
	
	public JButton getJBSalir() {
		if (jbSalir == null) {
			jbSalir = BotonesGenericos.botonSalir();
			jbSalir.setBounds(new java.awt.Rectangle(370,455,100,30));
			jbSalir.setName("Salir");
			jbSalir.setInputMap(0, map);
		}
		return jbSalir;
	}
	
	private JPanel getJPDeudas() {
		if (jpDeudas == null) {
			jpDeudas = new JPanel();
			jpDeudas.setLayout(null);
			jpDeudas.setBounds(new Rectangle(15,15,670,420));
			jpDeudas.setBorder(Utils.crearTituloYBorde("Datos"));
			jpDeudas.add(getJSPDatos(), null);
			jpDeudas.setBackground(Utils.colorPanel);
			jpDeudas.add(getJCheckSelectAll(), null);
			JLabel cliente= new JLabel();
			cliente.setBounds(25,20,90,20);
			cliente.setText("Clientes");
			jpDeudas.add(cliente,null);
			jpDeudas.add(getJTFDatos(),null);
			jpDeudas.add(getJCBTipoCliente(),null);
			jpDeudas.add(getJBCambiarBusqueda(),null);
			JLabel localidadl= new JLabel();
			localidadl.setBounds(20,50,90,20);
			localidadl.setText("Localidad");
			jpDeudas.add(localidadl,null);
			jpDeudas.add(getJTFDatosLoc(),null);
		}
		return jpDeudas;
	} 
	
	private JScrollPane getJSPDatos() {
		if (jspDatos == null) {
			jspDatos = new JScrollPane();
			jspDatos.setBounds(new Rectangle(10,80,650,300));
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
					String deuda=getValueAt(row,4).toString();
					String favor=getValueAt(row,3).toString();
					if(deuda.compareTo("")!=0){
						float impRestante= Float.parseFloat(deuda);
						if(impRestante>0){
							returnComp.setBackground(Color.WHITE);
							returnComp.setForeground(Color.RED);
							returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
						}else{
							returnComp.setBackground(Color.WHITE);
							returnComp.setForeground(Color.BLACK);
							returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
						}
					}else{
						if(favor.compareTo("")!=0){
							float impRestante= Float.parseFloat(favor);
							if(impRestante>0){
								returnComp.setBackground(Color.WHITE);
								returnComp.setForeground(Color.BLUE);
								returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
							}else{
								returnComp.setBackground(Color.WHITE);
								returnComp.setForeground(Color.BLACK);
								returnComp.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
							}
						}else{
							returnComp.setBackground(Color.WHITE);
							returnComp.setForeground(Color.BLACK);
						}
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
			TableColumn columna3 = jtDatos.getColumn("Fecha Ultima Facturación");
			columna3.setMaxWidth(150); 
			columna3.setPreferredWidth(150);
			columna3.setCellRenderer(Utils.alinearCentro());
			TableColumn columna4 = jtDatos.getColumn("Saldo a Favor");
			columna4.setMaxWidth(150); 
			columna4.setPreferredWidth(150);
			columna4.setCellRenderer(Utils.alinearDerecha());	
			TableColumn columna5 = jtDatos.getColumn("Adeudado");
			columna5.setPreferredWidth(100);
			columna5.setMaxWidth(100);
			columna5.setCellRenderer(Utils.alinearDerecha());	
		}
		return jtDatos;
	} 
	
	public void setActionListeners(ActionListener lis) {
		jbSalir.addActionListener(lis);
		jbImprimir.addActionListener(lis);
		jCheckSelectAll.addActionListener(lis);
		jbCambiarBusq.addActionListener(lis);
	}
	
	public void setListSelectionListener(ListSelectionListener lis) {
		jtDatos.getSelectionModel().addListSelectionListener(lis);
	}
	
	public JCheckBox getJCheckSelectAll() {
		if (jCheckSelectAll == null) {
			jCheckSelectAll = new JCheckBox();
			jCheckSelectAll.setBounds(new java.awt.Rectangle(10,393,130,17));
			jCheckSelectAll.setText("Seleccionar Todo");
			jCheckSelectAll.setName("SelectAll");
			jCheckSelectAll.setBackground(Utils.colorPanel);
		}
		return jCheckSelectAll;
	}
	
	public JButton getJBCambiarBusqueda() {
		if (jbCambiarBusq == null) {
			jbCambiarBusq = BotonesGenericos.botonEnPanel("<html>CAMBIAR<br>BUSQUEDA</html>",null);
			jbCambiarBusq.setBounds(new java.awt.Rectangle(420,20,100,50));
			jbCambiarBusq.setName("cambiartc");
			jbCambiarBusq.setInputMap(0, map);
		}
		return jbCambiarBusq;
	}
	
	public JTextField getJTFDatos() {
		if (jtfDatos == null) {
			jtfDatos = new JTextField();
			jtfDatos.setBounds(new Rectangle(80,20,130,20));
			jtfDatos.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfDatos.setEnabled(false);
		}
		return jtfDatos;
	}
	
	public JComboBox getJCBTipoCliente(){
		if (jCTipoCte == null) {
			jCTipoCte = new JComboBox();
			jCTipoCte.addItem("Todos");
			jCTipoCte.addItem("Inactivos");  //Son clientes a los que hace mas de 15 no se les Factura 
			jCTipoCte.setBackground(new Color(255,255,255));
			jCTipoCte.setForeground(java.awt.Color.black);
			jCTipoCte.setBounds(new java.awt.Rectangle(220,20,100,20));
		}
		return jCTipoCte;
	}
	
	
	public JTextField getJTFDatosLoc() {
		if (jtfDatosLoc == null) {
			jtfDatosLoc = new JTextField();
			jtfDatosLoc.setBounds(new Rectangle(80,50,130,20));
			jtfDatosLoc.setDisabledTextColor(Utils.colorTextoDisabled);
			jtfDatosLoc.setEnabled(false);
		}
		return jtfDatosLoc;
	}
	
	public JComboBox getJCLocalidades(){
		if (JCLocalidades == null) {
			JCLocalidades = new JComboBox();
			int sizeMax=0; 
			JCLocalidades.addItem("Ninguna");
			for(int i=0;i<localidades.size();i++){
				String lpago=(String)localidades.elementAt(i);
				if(lpago.length()>sizeMax)sizeMax=lpago.length();
				JCLocalidades.addItem(lpago);
			}
			JCLocalidades.setBackground(new Color(255,255,255));
			JCLocalidades.setForeground(java.awt.Color.black);
			JCLocalidades.setBounds(new java.awt.Rectangle(220,50,180,20));
		}
		return JCLocalidades;
	}
	
	public void actualizarBusqueda(){
		jpDeudas.add(getJCLocalidades(), null);
	}
	
	public void actualizarTabla(){
		jpPpal.remove(getJPDeudas());
		jpDeudas = null;
		jtDatos = null;
		modTabla = null;
		jspDatos = null;
		jpPpal.add(getJPDeudas(), null);
		actualizarBusqueda();
	}
	
}