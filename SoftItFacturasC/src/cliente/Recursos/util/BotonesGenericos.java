package cliente.Recursos.util;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;  
import javax.swing.border.MatteBorder;

import common.RootAndIp;

import cliente.Recursos.Botones.ButtonType;
import cliente.Recursos.Botones.GlossyButton;
import cliente.Recursos.Botones.GradientButton;

public class BotonesGenericos{
		
	public static JButton botonAceptar(){
		JButton boton = new GlossyButton("ACEPTAR",ButtonType.BUTTON_ROUNDED,Theme.GLOSSY_INDIGO_THEME,Theme.GLOSSY_NAVYBLUE_THEME,Theme.GLOSSY_BLACK_THEME);
		boton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		boton.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/check.png"));
		boton.setForeground(Color.BLACK);
		return boton;
	}
	
	public static JButton botonCancelar(){
		JButton boton = new GlossyButton("CANCELAR",ButtonType.BUTTON_ROUNDED,Theme.GLOSSY_INDIGO_THEME,Theme.GLOSSY_NAVYBLUE_THEME,Theme.GLOSSY_BLACK_THEME);
		boton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		boton.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/cancel.png"));
		boton.setForeground(Color.BLACK);
		return boton;
	}
	
	public static JButton botonSalir(){
		JButton boton = new GlossyButton("SALIR",ButtonType.BUTTON_ROUNDED,Theme.GLOSSY_INDIGO_THEME,Theme.GLOSSY_NAVYBLUE_THEME,Theme.GLOSSY_BLACK_THEME);
		boton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		boton.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/exit.png"));
		boton.setForeground(Color.BLACK);
		return boton;
	}
	
	public static JButton botonImprimir(){
		JButton boton = new GlossyButton("IMPRIMIR",ButtonType.BUTTON_ROUNDED,Theme.GLOSSY_INDIGO_THEME,Theme.GLOSSY_NAVYBLUE_THEME,Theme.GLOSSY_BLACK_THEME);
		boton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		boton.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/printer.png"));
		boton.setForeground(Color.BLACK);
		return boton;
	}
	
	public static JButton botonGestionAgregar(){
		JButton boton = new GlossyButton("INGRESAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR);
		boton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		boton.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/add.png"));
		return boton;
	} 
	
	public static JButton botonGestionModificar(){
		JButton boton = new GlossyButton("MODIFICAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR);
		boton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		boton.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/edit.png"));
		boton.setForeground(Color.BLACK);
		return boton;
	}
	
	public static JButton botonGestionEliminar(){
		JButton boton = new GlossyButton("ELIMINAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR);
		boton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		boton.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/delete.png"));
		boton.setForeground(Color.BLACK);
		return boton;
	}
	
	public static JButton botonGestionBuscar(){
		JButton boton = new GlossyButton("BUSCAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR);
		boton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		boton.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/find.png"));
		boton.setForeground(Color.BLACK);
		return boton;
	}
	
	public static JButton botonGestionImprimir(){
		JButton boton = new GlossyButton("IMPRIMIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR);
		boton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		boton.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/imgBotones/printer.png"));
		boton.setForeground(Color.BLACK);
		return boton;
	}
	
	public static JButton botonGestionCPeriodo(){
		JButton boton = new GlossyButton("CAMBIAR PERIODO",ButtonType.BUTTON_ROUNDED_RECTANGLUR);
		boton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		boton.setForeground(Color.BLACK);
		return boton;
	}
	
	public static JButton botonEnPanel(String texto,ImageIcon icono ){
		JButton boton = new GlossyButton(texto,ButtonType.BUTTON_ROUNDED_RECTANGLUR);
		boton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		boton.setIcon(icono);
		boton.setForeground(Color.BLACK);
		return boton;
	}
}
