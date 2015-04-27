package cliente.backupDataBase;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import cliente.Recursos.util.BotonesGenericos;

import common.RootAndIp;
import common.Utils;

public class GUIBackup extends JDialog{
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton JButtonS = null;
	private JLabel icono = null;
	private JTextPane advertencia = null;
	private JTextPane resultado = null;
	private JTextPane jTextPaneBackup = null;
	private RootAndIp rip= new RootAndIp();
	private InputMap map = new InputMap();
	
	public GUIBackup(JFrame guiPadre){
		super(guiPadre);
		this.setSize(750, 350);
		this.setResizable(false);
		this.setLocationRelativeTo(guiPadre);
		this.setTitle("Backup Base de Datos");
		this.setContentPane(getJContentPane());
		this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			advertencia = new JTextPane();
			advertencia.setText("");
			advertencia.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			advertencia.setAlignmentX(CENTER_ALIGNMENT);
			advertencia.setForeground(new Color(0,0,205));
			advertencia.setBounds(new java.awt.Rectangle(30,10,670,40));
			advertencia.setBackground(Utils.colorPanel);
			advertencia.setDisabledTextColor(Utils.colorTextoDisabled);
			advertencia.setEnabled(false);
			icono= new JLabel();
			icono.setIcon(new ImageIcon(rip.getExtras()+"/iconos/guardar.png"));
			icono.setBounds(new java.awt.Rectangle(30,100,50,50));
			icono.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
			resultado = new JTextPane();
			resultado.setText("");
			resultado.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			resultado.setForeground(new Color(0,0,205)); 
			resultado.setDisabledTextColor(Utils.colorTextoDisabled);
			resultado.setBounds(new java.awt.Rectangle(30,220,670,40));
			resultado.setBackground(Utils.colorPanel);
			resultado.setEnabled(false);
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(Utils.colorFondo);
			jContentPane.add(advertencia, null);
			jContentPane.add(resultado, null);
			jContentPane.add(icono, null);
			jContentPane.add(getJButtonSalir(), null);
			jContentPane.add(getJTextPaneBackup(), null);
		}
		return jContentPane;
	}
	
	public JButton getJButtonSalir() {
		if (JButtonS == null) {
			JButtonS = BotonesGenericos.botonSalir();
			JButtonS.setBounds(new java.awt.Rectangle(320,280,100,30));
			JButtonS.setInputMap(0, map);
		}
		return JButtonS;
	}
	
	public void setActionListeners(ActionListener lis){
		this.JButtonS.addActionListener(lis);
	}
	
	public JTextPane getAdvertencia() {
		return advertencia;
	}
	
	public JTextPane getResultado() {
		return resultado;
	}
	
	public JTextPane getJTextPaneBackup() {
		if (jTextPaneBackup == null) {
			jTextPaneBackup = new JTextPane();
			jTextPaneBackup.setBounds(new java.awt.Rectangle(100,60,600,153));
			jTextPaneBackup.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sabia Ud. que...?", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jTextPaneBackup.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			jTextPaneBackup.setText("El Backup es una copia de seguridad de su Base de Datos; es decir al realizar un Backup se crea automáticamente un archivo con la información almacenada en su Base de Datos en el momento de la ejecución. Este archivo le servirá como respaldo, copia o recuerdo de la información que usted tenga almacenada en el sistema. Pueden realizarse la cantidad de Backups que desee y cada uno de los archivos creados quedará almacenado en un directorio comun y con un nombre que distingue a cada uno.");
			jTextPaneBackup.setDisabledTextColor(Utils.colorNegro);
			jTextPaneBackup.setEnabled(false);
			
		}
		return jTextPaneBackup;
	}
	
}
