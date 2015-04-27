package cliente.Principal;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import common.RootAndIp;
import common.Utils;

public class GUIInicio extends JDialog{
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	Color azul = new Color(104,34,139);
	
	public GUIInicio(){
		super();
		this.setSize(new java.awt.Dimension(400,300));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("- Información del Sistema Informático -");
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane= new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(Utils.colorFondo);
			JLabel label0= new JLabel();
			label0.setBounds(new java.awt.Rectangle(0,40,400,30));
			label0.setText("El sistema iniciará en unos segundos");
			label0.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 18));
			label0.setHorizontalAlignment(SwingConstants.CENTER);
			label0.setForeground(Utils.colorNegro);
			JLabel label1= new JLabel();
			label1.setBounds(new java.awt.Rectangle(0,90,400,20));
			label1.setText("Espere que se esta realizando la actualización");
			label1.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.PLAIN, 15));
			label1.setHorizontalAlignment(SwingConstants.CENTER);
			label1.setForeground(Utils.colorNegro);
			JLabel label2= new JLabel();
			label2.setBounds(new java.awt.Rectangle(0,120,400,20));
			label2.setText("de los socios activos del sistema");
			label2.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.PLAIN, 15));
			label2.setHorizontalAlignment(SwingConstants.CENTER);
			label2.setForeground(Utils.colorNegro);
			JLabel label3= new JLabel();
			label3.setBounds(new java.awt.Rectangle(0,150,400,100));
			label3.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/ingresando.gif"));
			label3.setHorizontalAlignment(SwingConstants.CENTER);
			jContentPane.add(label0);
			jContentPane.add(label1);
			jContentPane.add(label2);
			jContentPane.add(label3);
		}
		return jContentPane;
	}
	
}

