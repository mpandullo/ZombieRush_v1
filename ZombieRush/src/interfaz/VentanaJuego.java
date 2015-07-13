package interfaz;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import cliente.JuegoCliente;
import datosSocket.DatosPartidaEnJuego;
import datosSocket.DatosPartidaTerminada;

public class VentanaJuego extends JDialog {

	private JPanel contentPane;
	
	private TableroJuego tablero;
	private JuegoCliente juego;
	private ModalPartidaTerminada modal;
	
	private JButton btnUp;
	private JButton btnDown;
	private JButton btnLeft;
	private JButton btnRight;
	
	// Constructor
	public VentanaJuego(PanelCliente p, JuegoCliente juego) {
		
		super(p);
		this.juego = juego;
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				cerrar();
			}
		});
		
		this.crearTablero();
		
		setTitle(juego.getDatosUP().getNombrePartida());
		setBounds(100, 100, 625, 627);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		tablero.setBorder(new LineBorder(new Color(67, 78, 84), 2));
		tablero.setBackground(SystemColor.scrollbar);
		
		tablero.setBounds(10, 34, 600, 450);
		contentPane.add(tablero);
		
		JLabel lblNombreJugador = new JLabel(juego.getUsuario().getUsuario());
		lblNombreJugador.setBounds(10, 10, 161, 14);
		contentPane.add(lblNombreJugador);
		
		JLabel lblPuntos = new JLabel("Puntos: 0");
		lblPuntos.setBounds(546, 9, 64, 14);
		contentPane.add(lblPuntos);
		
		// Boton Arriba
		ImageIcon iconUp = new ImageIcon(VentanaJuego.class.getResource("/img/arriba.png"));
		ImageIcon iconUpHover = new ImageIcon(VentanaJuego.class.getResource("/img/arribaHover.png"));
		ImageIcon iconUpActive = new ImageIcon(VentanaJuego.class.getResource("/img/arribaActive.png"));
		btnUp = new JButton("");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				arriba();
			}
		});
		btnUp.setBorderPainted(false);
		btnUp.setContentAreaFilled(false);
		btnUp.setFocusable(false);
		btnUp.setRolloverEnabled(true);
		
		btnUp.setIcon(iconUp);
		btnUp.setRolloverIcon(iconUpHover);
		btnUp.setPressedIcon(iconUpActive);
		
		btnUp.setBounds(286, 495, 36, 38);
		contentPane.add(btnUp);
		
		// Boton Abajo
		ImageIcon iconDown = new ImageIcon(VentanaJuego.class.getResource("/img/abajo.png"));
		ImageIcon iconDownHover = new ImageIcon(VentanaJuego.class.getResource("/img/abajoHover.png"));
		ImageIcon iconDownActive = new ImageIcon(VentanaJuego.class.getResource("/img/abajoActive.png"));
		btnDown = new JButton("");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abajo();
			}
		});
		btnDown.setBorderPainted(false);
		btnDown.setContentAreaFilled(false);
		btnDown.setFocusable(false);
		btnDown.setRolloverEnabled(true);
				
		btnDown.setIcon(iconDown);
		btnDown.setRolloverIcon(iconDownHover);
		btnDown.setPressedIcon(iconDownActive);
				
		btnDown.setBounds(286, 544, 36, 38);
		contentPane.add(btnDown);
		
		// Boton Izquierda
		ImageIcon iconLeft = new ImageIcon(VentanaJuego.class.getResource("/img/izquierda.png"));
		ImageIcon iconLeftHover = new ImageIcon(VentanaJuego.class.getResource("/img/izquierdaHover.png"));
		ImageIcon iconLeftActive = new ImageIcon(VentanaJuego.class.getResource("/img/izquierdaActive.png"));
		btnLeft = new JButton("");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				izquierda();
			}
		});
		btnLeft.setBorderPainted(false);
		btnLeft.setContentAreaFilled(false);
		btnLeft.setFocusable(false);
		btnLeft.setRolloverEnabled(true);
					
		btnLeft.setIcon(iconLeft);
		btnLeft.setRolloverIcon(iconLeftHover);
		btnLeft.setPressedIcon(iconLeftActive);
				
		btnLeft.setBounds(246, 518, 36, 38);
		contentPane.add(btnLeft);
		
		// Boton Derecha
		ImageIcon iconRight = new ImageIcon(VentanaJuego.class.getResource("/img/derecha.png"));
		ImageIcon iconRightHover = new ImageIcon(VentanaJuego.class.getResource("/img/derechaHover.png"));
		ImageIcon iconRightActive = new ImageIcon(VentanaJuego.class.getResource("/img/derechaActive.png"));
		btnRight = new JButton("");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				derecha();
			}
		});
		btnRight.setBorderPainted(false);
		btnRight.setContentAreaFilled(false);
		btnRight.setFocusable(false);
		btnRight.setRolloverEnabled(true);
							
		btnRight.setIcon(iconRight);
		btnRight.setRolloverIcon(iconRightHover);
		btnRight.setPressedIcon(iconRightActive);
						
		btnRight.setBounds(326, 518, 36, 38);
		contentPane.add(btnRight);
		
		
	}
	
	// Cerrar ventana
	private void cerrar() {
		int opcion = JOptionPane.showConfirmDialog(this, "Desea abandonar la partida?\nSe perderán los puntos acumulados.", "Salir", JOptionPane.YES_NO_OPTION);
		if( opcion == JOptionPane.YES_OPTION) {
			try {
				this.juego.abandonarPartida();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.dispose();			
		} 
	}
	
	private void crearTablero(){
		this.tablero = new TableroJuego(juego.getUsuario().getIdUsuario());
	}
	
	// Movimientos
	private void izquierda() {
		this.bloquearTeclado();
		this.juego.mover('l');
	}
	
	private void derecha() {
		this.bloquearTeclado();
		this.juego.mover('r');
	}
	
	private void arriba() {
		this.bloquearTeclado();
		this.juego.mover('u');
	}
	
	private void abajo() {
		this.bloquearTeclado();
		this.juego.mover('d');
	}
	
	public void bloquearTeclado() {
		this.btnDown.setEnabled(false);
		this.btnLeft.setEnabled(false);
		this.btnRight.setEnabled(false);
		this.btnUp.setEnabled(false);
	}
	
	private void activarTeclado() {
		this.btnDown.setEnabled(true);
		this.btnLeft.setEnabled(true);
		this.btnRight.setEnabled(true);
		this.btnUp.setEnabled(true);
	}
	
	public void actualizarTablero(DatosPartidaEnJuego datos) {
		this.tablero.actualizar(datos);
		this.activarTeclado();
	}
	
	public void terminarPartida(DatosPartidaTerminada datos) {
		modal = new ModalPartidaTerminada(this, this.juego);
		modal.setVisible(true);
	}
	
	public void cerrarContinuar() {
		this.juego.setContinuar(false);
		modal.dispose();		
	}
	
}


