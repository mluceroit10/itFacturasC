package server;

import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import common.Utils;

public class GUIServidorIniciado extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private String ipCorr="";
	
	public GUIServidorIniciado(String ip){
		ipCorr=ip;
		this.setSize(new java.awt.Dimension(400,200));
		this.setResizable(false);
		this.setLocation(10,10);
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
			label0.setBounds(new Rectangle(0,30,400,30));
			label0.setText("El servidor ha iniciado correctamente");
			label0.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 18));
			label0.setHorizontalAlignment(SwingConstants.CENTER);
			label0.setForeground(Utils.colorNegro);
			JLabel label1= new JLabel();
			label1.setBounds(new Rectangle(0,65,400,30));
			label1.setText("En el ip: "+ipCorr);
			label1.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 18));
			label1.setHorizontalAlignment(SwingConstants.CENTER);
			label1.setForeground(Utils.colorNegro);
			String leyenda="Habilitado para ejecución en red";
			if(ipCorr.compareTo("127.0.0.1")==0) 
				leyenda="Habilitado para ejecución local";
			JLabel label2= new JLabel();
			label2.setBounds(new Rectangle(0,100,400,20));
			label2.setText(leyenda);
			label2.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 15));
			label2.setHorizontalAlignment(SwingConstants.CENTER);
			label2.setForeground(Utils.colorNegro);
			JLabel label3= new JLabel();
			label3.setBounds(new Rectangle(0,130,400,20));
			label3.setText("Ya se puede ejecutar el programa cliente");
			label3.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 15));
			label3.setHorizontalAlignment(SwingConstants.CENTER);
			label3.setForeground(Utils.colorNegro);
			jContentPane.add(label0);
			jContentPane.add(label1);
			jContentPane.add(label2);
			jContentPane.add(label3);
		}
		return jContentPane;
	}
	
}

