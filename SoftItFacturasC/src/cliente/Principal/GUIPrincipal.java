package cliente.Principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import common.RootAndIp;
import common.Utils;

public class GUIPrincipal extends JFrame implements Runnable{
	
	private static final long serialVersionUID = 1L;
	//PANEL PRINCIPAL
	private JPanel jContentPane = null;  
	private JButton jButtonSalir = null;
	//BARRA MENU
	private JMenuBar menuPrinc=null;
	private JMenu archivo=null;
	private JMenu gestionar=null;
	private JMenu baseDatos=null;
	
	private JMenuItem archivoSalir=null;
	private JMenuItem gestionarProvincia=null;  
	private JMenuItem gestionarLocalidad=null;
	private JMenuItem backupBD=null;
	private JMenuItem infoComercio=null;
	private JMenuItem infoProg=null;
	private Border b= javax.swing.BorderFactory.createLineBorder(new Color(0,100,0),2);
	//PANEL ACCESOS RAPIDOS
	private JPanel jPanelAccesosCliente=null;
	private JPanel jPanelAccesosContables=null;
	private JPanel jPanelAccesosRemitoCte=null; 
	private JPanel jPanelAccesosFactCte=null;
	private JPanel jPanelAccesosNotaDebitoCte=null;
	private JPanel jPanelAccesosCheque=null;
	
	private JButton jBGestionClientes=null;
	private JButton jBFacturaCliente=null;
	private JButton jBRemitoCliente=null;
	private JButton jBGestionarMC=null;
	private JButton jBPlanillaES=null;
	private JButton jBTodosRemitosCliente=null;
	private JButton jBTodasFactCliente=null;
	private JButton jBListadoDeudaClientes=null;
	Color colorFuenteMenu = new Color(0,0,128);
	private InputMap map = new InputMap();
	
	private JButton jBNotaDebitoCliente;
	private JButton jBTodasNDebitoCliente;
	private JButton jBCheques;
	
	public GUIPrincipal() throws Exception{
		super();
		this.setSize(new java.awt.Dimension(800,570));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("Sistema Informático ''itFacturasC''");
		this.setContentPane(getJContentPane());
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(RootAndIp.getExtras()+"/iconos/logo2.png"));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
		this.init();
	}
	
	private JPanel getJContentPane() {
		if(jContentPane==null){
			jContentPane = new JPanel();
			jContentPane.setBackground(Utils.colorFondo);
			jContentPane.setLayout(null);
			jContentPane.add(getJPanelAccesosClientes(), null);
			jContentPane.add(getJPanelAccesosContables(), null);
			jContentPane.add(getJPanelAccesosFactCliente(), null);
		/*	jContentPane.add(getJPanelAccesosRemitoCliente(), null); ELIMINO LOS REMITOS PERO QUEDA TODA LA FUNCIONALIDAD DESARROLLADA */
			jContentPane.add(getJPanelAccesosNotaDebitoCliente(),null);
			jContentPane.add(getJPanelAccesosCheques(), null);
			JLabel salir = new JLabel("SALIR");
			salir.setBounds(new java.awt.Rectangle(640,460,100,20));
			salir.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			salir.setHorizontalAlignment(SwingConstants.CENTER);
			jContentPane.add(salir,null);
			jContentPane.add(getJButtonSalir(),null);
			this.setJMenuBar(getJBarraMenu());
		}
		return jContentPane;
	}
	
	private JMenuBar getJBarraMenu(){
		if (menuPrinc==null){
			menuPrinc = new JMenuBar();
			archivo = new JMenu("Información        ");
			archivo.setForeground(colorFuenteMenu);
			archivo.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			gestionar = new JMenu("Gestionar             ");
			gestionar.setForeground(colorFuenteMenu);
			gestionar.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			baseDatos = new JMenu("Datos         ");
			baseDatos.setForeground(colorFuenteMenu);
			baseDatos.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			menuPrinc.add(archivo);
			menuPrinc.add(gestionar);
			menuPrinc.add(baseDatos);
			infoComercio= new JMenuItem("Del Comercio");
			infoComercio.setForeground(colorFuenteMenu);
			infoComercio.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			infoProg = new JMenuItem("Del Programa");
			infoProg.setForeground(colorFuenteMenu);
			infoProg.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			archivoSalir= new JMenuItem("Salir        ");
			archivoSalir.setForeground(colorFuenteMenu);
			archivoSalir.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			gestionarProvincia= new JMenuItem("Provincia");
			gestionarProvincia.setForeground(colorFuenteMenu);
			gestionarProvincia.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			gestionarLocalidad= new JMenuItem("Localidad");
			gestionarLocalidad.setForeground(colorFuenteMenu);
			gestionarLocalidad.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			backupBD= new JMenuItem("BackUP de Base de Datos");
			backupBD.setForeground(colorFuenteMenu);
			backupBD.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 14));
			archivo.add(infoComercio);
			archivo.add(infoProg);
			archivo.add(archivoSalir);
			gestionar.add(gestionarProvincia);
			gestionar.add(gestionarLocalidad);
			baseDatos.add(backupBD);
		}
		return menuPrinc;
	}
	
	public JButton getJButtonDeudasClientes() {
		if (jBListadoDeudaClientes == null) {
			jBListadoDeudaClientes= new JButton();
			jBListadoDeudaClientes.setBounds(new java.awt.Rectangle(249,40,65,65));
			jBListadoDeudaClientes.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/deuda.png"));
			jBListadoDeudaClientes.setBorder(Utils.crearRebordeBoton());
			jBListadoDeudaClientes.setInputMap(0, map);
		}
		return jBListadoDeudaClientes;
	}
	
	public JButton getJButtonSalir() {
		if (jButtonSalir == null) {
			jButtonSalir = new JButton();
			jButtonSalir.setBounds(new java.awt.Rectangle(640,360,100,100));
			jButtonSalir.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/salir.png"));
			jButtonSalir.setBorder(Utils.crearRebordeBoton());
			jButtonSalir.setInputMap(0, map);
		}
		return jButtonSalir;
	}
	
	private JPanel getJPanelAccesosClientes(){
		if (jPanelAccesosCliente==null){
			jPanelAccesosCliente = new JPanel();
			jPanelAccesosCliente.setLayout(null);
			jPanelAccesosCliente.setBackground(Utils.colorPanel);
			jPanelAccesosCliente.setBorder(b);
			jPanelAccesosCliente.setBounds(new java.awt.Rectangle(400,20,375,140));
			JLabel tituloc= new JLabel("CLIENTES");
			tituloc.setBounds(new java.awt.Rectangle(0,5,375,20));
			tituloc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			tituloc.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosCliente.add(tituloc, null);
			jPanelAccesosCliente.add(getJButtonClientes(), null);
			JLabel gestion= new JLabel("GESTION");
			gestion.setBounds(new java.awt.Rectangle(0,115,187,15));
			gestion.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			gestion.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosCliente.add(gestion, null);
			
			jPanelAccesosCliente.add(getJButtonDeudasClientes(), null);
			JLabel deudas= new JLabel("DEUDAS");
			deudas.setBounds(new java.awt.Rectangle(188,115,187,15));
			deudas.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			deudas.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosCliente.add(deudas, null);
		}
		return jPanelAccesosCliente;
	}
	
	public JButton getJButtonClientes() {
		if (jBGestionClientes == null) {
			jBGestionClientes= new JButton();
			jBGestionClientes.setBounds(new java.awt.Rectangle(61,40,65,65));
			jBGestionClientes.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/socios.png"));
			jBGestionClientes.setBorder(Utils.crearRebordeBoton());
			jBGestionClientes.setInputMap(0, map);
		}
		return jBGestionClientes;
	}
	
	private JPanel getJPanelAccesosContables(){
		if (jPanelAccesosContables==null){
			jPanelAccesosContables = new JPanel();
			jPanelAccesosContables.setLayout(null);
			jPanelAccesosContables.setBackground(Utils.colorPanel);
			jPanelAccesosContables.setBorder(b);
			jPanelAccesosContables.setBounds(new java.awt.Rectangle(400,180,375,140));
			JLabel titulosoc= new JLabel("GESTION CONTABLE");
			titulosoc.setBounds(new java.awt.Rectangle(0,5,375,20));
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			titulosoc.setHorizontalAlignment(SwingConstants.CENTER); 
			jPanelAccesosContables.add(titulosoc, null);
			jPanelAccesosContables.add(getJButtonGestionarMC(), null);
			JLabel movCaja= new JLabel("MOVIMIENTO DE CAJA");
			movCaja.setBounds(new java.awt.Rectangle(0,115,187,15));
			movCaja.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			movCaja.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosContables.add(movCaja, null);
			jPanelAccesosContables.add(getJButtonPlanillaES(), null);
			JLabel plES= new JLabel("PLANILLA DE E/S");
			plES.setBounds(new java.awt.Rectangle(188,115,187,15));
			plES.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			plES.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosContables.add(plES, null);
		}
		return jPanelAccesosContables;
	}
	
	public JButton getJButtonGestionarMC() {
		if (jBGestionarMC == null) {
			jBGestionarMC= new JButton();
			jBGestionarMC.setBounds(new java.awt.Rectangle(61,40,65,65));
			jBGestionarMC.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/movCaja.png"));
			jBGestionarMC.setBorder(Utils.crearRebordeBoton());
			jBGestionarMC.setInputMap(0, map);
		}
		return jBGestionarMC;
	}
	
	public JButton getJButtonPlanillaES() {
		if (jBPlanillaES == null) {
			jBPlanillaES= new JButton();
			jBPlanillaES.setBounds(new java.awt.Rectangle(249,40,65,65));
			jBPlanillaES.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/planillaES.png"));
			jBPlanillaES.setBorder(Utils.crearRebordeBoton());
			jBPlanillaES.setInputMap(0, map);
		}
		return jBPlanillaES;
	}
	
	public JButton getJButtonFacturaCliente() {
		if (jBFacturaCliente == null) {
			jBFacturaCliente= new JButton();
			jBFacturaCliente.setBounds(new java.awt.Rectangle(61,40,65,65));
			jBFacturaCliente.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/facturaC.PNG"));
			jBFacturaCliente.setBorder(Utils.crearRebordeBoton());
			jBFacturaCliente.setInputMap(0, map);
		}
		return jBFacturaCliente;
	}
	
	public JButton getJButtonRemitoCliente() {
		if (jBRemitoCliente == null) {
			jBRemitoCliente= new JButton();
			jBRemitoCliente.setBounds(new java.awt.Rectangle(61,25,65,65));
			jBRemitoCliente.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/remito.png"));
			jBRemitoCliente.setBorder(Utils.crearRebordeBoton());
			jBRemitoCliente.setInputMap(0, map);
		}
		return jBRemitoCliente;
	}
	
	public JButton getJButtonNDebitoCliente() {
		if (jBNotaDebitoCliente == null) {
			jBNotaDebitoCliente= new JButton();
			jBNotaDebitoCliente.setBounds(new java.awt.Rectangle(61,40,65,65));
			jBNotaDebitoCliente.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/remito.png"));
			jBNotaDebitoCliente.setBorder(Utils.crearRebordeBoton());
			jBNotaDebitoCliente.setInputMap(0, map);
		}
		return jBNotaDebitoCliente; 
	}
	
	public JButton getJButtonCheques() {
		if (jBCheques == null) {
			jBCheques= new JButton();
			jBCheques.setBounds(new java.awt.Rectangle(61,40,65,65));
			jBCheques.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/cheque.png"));
			jBCheques.setBorder(Utils.crearRebordeBoton());
			jBCheques.setInputMap(0, map);
		}
		return jBCheques;
	}
	
	private JPanel getJPanelAccesosFactCliente(){
		if (jPanelAccesosFactCte==null){
			jPanelAccesosFactCte = new JPanel();
			jPanelAccesosFactCte.setLayout(null);
			jPanelAccesosFactCte.setBackground(Utils.colorPanel);
			jPanelAccesosFactCte.setBorder(b);
			jPanelAccesosFactCte.setBounds(new java.awt.Rectangle(10,20,375,140));
			JLabel titulosoc= new JLabel("FACTURACION - CLIENTE");
			titulosoc.setBounds(new java.awt.Rectangle(0,5,375,20));
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			titulosoc.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosFactCte.add(titulosoc, null);
			jPanelAccesosFactCte.add(getJButtonFacturaCliente(), null);
			JLabel nFact= new JLabel("NUEVA FACTURA");
			nFact.setBounds(new java.awt.Rectangle(0,115,187,15));
			nFact.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			nFact.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosFactCte.add(nFact, null);
			jPanelAccesosFactCte.add(getJButtonTodasFactCliente(), null);
			JLabel factGen= new JLabel("FACTURAS GENERADAS");
			factGen.setBounds(new java.awt.Rectangle(188,115,187,15));
			factGen.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			factGen.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosFactCte.add(factGen, null);
		}
		return jPanelAccesosFactCte;
	}
	
	public JButton getJButtonTodasFactCliente() {
		if (jBTodasFactCliente == null) {
			jBTodasFactCliente= new JButton();
			jBTodasFactCliente.setBounds(new java.awt.Rectangle(249,40,65,65));
			jBTodasFactCliente.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/tFactCte.png"));
			jBTodasFactCliente.setBorder(Utils.crearRebordeBoton());
			jBTodasFactCliente.setInputMap(0, map);
		}
		return jBTodasFactCliente;
	}
	
	private JPanel getJPanelAccesosRemitoCliente(){
		if (jPanelAccesosRemitoCte==null){
			jPanelAccesosRemitoCte = new JPanel();
			jPanelAccesosRemitoCte.setLayout(null);
			jPanelAccesosRemitoCte.setBackground(Utils.colorPanel);
			jPanelAccesosRemitoCte.setBorder(b);
			jPanelAccesosRemitoCte.setBounds(new java.awt.Rectangle(10,260,375,115));
			JLabel titulosoc= new JLabel("REMITOS - CLIENTE");
			titulosoc.setBounds(new java.awt.Rectangle(0,5,375,20));
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			titulosoc.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosRemitoCte.add(titulosoc, null);
			jPanelAccesosRemitoCte.add(getJButtonRemitoCliente(), null);
			JLabel nRem= new JLabel("NUEVO REMITO");
			nRem.setBounds(new java.awt.Rectangle(0,95,187,15));
			nRem.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			nRem.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosRemitoCte.add(nRem, null);
			jPanelAccesosRemitoCte.add(getJButtonTodosRemitosCliente(), null);
			JLabel remGen= new JLabel("REMITOS GENERADOS");
			remGen.setBounds(new java.awt.Rectangle(188,95,187,15));
			remGen.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			remGen.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosRemitoCte.add(remGen, null);
		}
		return jPanelAccesosRemitoCte;
	}
	
	private JPanel getJPanelAccesosNotaDebitoCliente(){
		if (jPanelAccesosNotaDebitoCte==null){
			jPanelAccesosNotaDebitoCte = new JPanel();
			jPanelAccesosNotaDebitoCte.setLayout(null);
			jPanelAccesosNotaDebitoCte.setBackground(Utils.colorPanel);
			jPanelAccesosNotaDebitoCte.setBorder(b);
			jPanelAccesosNotaDebitoCte.setBounds(new java.awt.Rectangle(10,180,375,140));
			JLabel titulosoc= new JLabel("NOTAS DE DEBITO - CLIENTE");
			titulosoc.setBounds(new java.awt.Rectangle(0,5,375,20));
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			titulosoc.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosNotaDebitoCte.add(titulosoc, null);
			jPanelAccesosNotaDebitoCte.add(getJButtonNDebitoCliente(), null);
			JLabel nRem= new JLabel("NUEVA N. DEB.");
			nRem.setBounds(new java.awt.Rectangle(0,115,187,15));
			nRem.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			nRem.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosNotaDebitoCte.add(nRem, null);
			jPanelAccesosNotaDebitoCte.add(getJButtonTodasNDebitoCliente(), null);
			JLabel remGen= new JLabel("N. DEB. GENERADAS");
			remGen.setBounds(new java.awt.Rectangle(188,115,187,15));
			remGen.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			remGen.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosNotaDebitoCte.add(remGen, null);
		}
		return jPanelAccesosNotaDebitoCte;
	}
	
	private JPanel getJPanelAccesosCheques(){
		if (jPanelAccesosCheque==null){
			jPanelAccesosCheque = new JPanel();
			jPanelAccesosCheque.setLayout(null);
			jPanelAccesosCheque.setBackground(Utils.colorPanel);
			jPanelAccesosCheque.setBorder(b);
			jPanelAccesosCheque.setBounds(new java.awt.Rectangle(10,340,187,140));
			JLabel titulosoc= new JLabel("CHEQUES");
			titulosoc.setBounds(new java.awt.Rectangle(0,5,187,20));
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 16));
			titulosoc.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosCheque.add(titulosoc, null);
			jPanelAccesosCheque.add(getJButtonCheques(), null);
			JLabel nRem= new JLabel("GESTION");
			nRem.setBounds(new java.awt.Rectangle(0,115,187,15));
			nRem.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 12));
			nRem.setHorizontalAlignment(SwingConstants.CENTER);
			jPanelAccesosCheque.add(nRem, null);
		}
		return jPanelAccesosCheque;
	}
	
	public JButton getJButtonTodosRemitosCliente() {
		if (jBTodosRemitosCliente == null) {
			jBTodosRemitosCliente= new JButton();
			jBTodosRemitosCliente.setBounds(new java.awt.Rectangle(249,25,65,65));
			jBTodosRemitosCliente.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/tRemitosCte.png"));
			jBTodosRemitosCliente.setBorder(Utils.crearRebordeBoton());
			jBTodosRemitosCliente.setInputMap(0, map);
		}
		return jBTodosRemitosCliente;
	}
	
	public JButton getJButtonTodasNDebitoCliente() {
		if (jBTodasNDebitoCliente == null) {
			jBTodasNDebitoCliente= new JButton();
			jBTodasNDebitoCliente.setBounds(new java.awt.Rectangle(249,40,65,65));
			jBTodasNDebitoCliente.setIcon(new ImageIcon(RootAndIp.getExtras()+"/iconos/tRemitosCte.png"));
			jBTodasNDebitoCliente.setBorder(Utils.crearRebordeBoton());
			jBTodasNDebitoCliente.setInputMap(0, map);
		}
		return jBTodasNDebitoCliente;
	}
	public void setActionListeners(ActionListener lis) {
		this.jButtonSalir.addActionListener(lis);
		this.infoComercio.addActionListener(lis);
		this.infoProg.addActionListener(lis);
		this.archivoSalir.addActionListener(lis);
		this.jBCheques.addActionListener(lis);  
		this.jBNotaDebitoCliente.addActionListener(lis);
		this.jBTodasNDebitoCliente.addActionListener(lis);
		this.gestionarProvincia.addActionListener(lis);
		this.gestionarLocalidad.addActionListener(lis);
		this.jBGestionarMC.addActionListener(lis);
		this.jBPlanillaES.addActionListener(lis);
		this.jBGestionClientes.addActionListener(lis);
		this.jBFacturaCliente.addActionListener(lis);
		this.jBTodasFactCliente.addActionListener(lis);
	/*	this.jBRemitoCliente.addActionListener(lis);
		this.jBTodosRemitosCliente.addActionListener(lis);*/
		this.jBListadoDeudaClientes.addActionListener(lis);
		this.backupBD.addActionListener(lis);
	}
	
	public JMenuItem getArchivoInfoComercio() {
		return infoComercio;
	}
	
	public JMenuItem getArchivoSalir() {
		return archivoSalir;
	}
	
	public JMenuItem getGestionarProvincia() {
		return gestionarProvincia;
	}
	
	public JMenuItem getGestionarLocalidad() {
		return gestionarLocalidad;
	}
	
	public JMenuItem getBaseDatos() {
		return backupBD;
	}
	
	public JMenuItem getInfoProg() {
		return infoProg;
	}
	
	private int d,m,a,hora, minutos, segundos;
	private int dia;
	JLabel labelDia;
	JLabel labelHora;
	Calendar calendario;
	Thread h1;

	public void init() {
		labelDia = new JLabel(" ");
		labelDia.setFont(new Font( Utils.tipoLetra, Font.BOLD, 16 ));
		labelDia.setBounds(new Rectangle(240,380,300,25));
		labelDia.setHorizontalAlignment(SwingConstants.CENTER);
		labelHora = new JLabel(" ");
		labelHora.setFont(new Font( Utils.tipoLetra, Font.BOLD, 16 ));
		labelHora.setBounds(new Rectangle(240,420,300,25));
		labelHora.setHorizontalAlignment(SwingConstants.CENTER);
		this.jContentPane.add(labelDia,null);
		this.jContentPane.add(labelHora,null);
		h1 = new Thread(this);
		h1.start();
	}

	public void calcula () {
		Calendar calendario = GregorianCalendar.getInstance();
		a=calendario.get(Calendar.YEAR);
		m=calendario.get(Calendar.MONTH)+1;
		d=calendario.get(Calendar.DATE);
		dia=calendario.get(Calendar.DAY_OF_WEEK);
		hora = calendario.get(Calendar.HOUR_OF_DAY);
		minutos = calendario.get(Calendar.MINUTE);
		segundos = calendario.get(Calendar.SECOND); 
	}
	
	private String nombreDia(int diaSemana){
		if(diaSemana==1) return "DOMINGO";
		if(diaSemana==2) return "LUNES";
		if(diaSemana==3) return "MARTES";
		if(diaSemana==4) return "MIERCOLES";
		if(diaSemana==5) return "JUEVES";
		if(diaSemana==6) return "VIERNES";
		if(diaSemana==7) return "SABADO";
		return "";
	}
	
	public void run() {
		Thread ct = Thread.currentThread();
		while(ct == h1) {   
			calcula();
			String min=String.valueOf(minutos);
			if(min.length()==1) min ="0"+min;
			String seg=String.valueOf(segundos);
			if(seg.length()==1) seg ="0"+seg;
			labelDia.setText("  DIA: "+nombreDia(dia)+"  "+d+"/"+m+"/"+a);
			labelHora.setText("  HORA: "+hora + ":" + min + ":" + seg);
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException e) {}
		}
	}

	public void stop() {
		h1 = null;
	}
	
}
