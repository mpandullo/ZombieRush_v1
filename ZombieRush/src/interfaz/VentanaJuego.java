package interfaz;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import cliente.JuegoCliente;
import cliente.UsuarioNormal;
import datosSocket.DatosPartida;

public class VentanaJuego extends JDialog {

	private JPanel contentPane;
	
	private TableroJuego tablero = new TableroJuego();
	
	private UsuarioNormal usuario;
	private DatosPartida partida;
	private JuegoCliente juego;
	
	// Constructor
	public VentanaJuego(PanelCliente p, DatosPartida partida, UsuarioNormal usuario, JuegoCliente juego) {
		
		super(p);
		this.partida = partida;
		this.usuario = usuario;
		this.juego = juego;
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				cerrar();
			}
		});
		
		setTitle(partida.getNombre());
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
		
		JLabel lblNombreJugador = new JLabel(usuario.getUsuario());
		lblNombreJugador.setBounds(10, 10, 161, 14);
		contentPane.add(lblNombreJugador);
		
		JLabel lblPuntos = new JLabel("Puntos: " + partida.getPuntos());
		lblPuntos.setBounds(546, 9, 64, 14);
		contentPane.add(lblPuntos);
		
		JLabel lblTiempoDePartida = new JLabel("Tiempo de Partida: 12:01");
		lblTiempoDePartida.setBounds(10, 489, 200, 14);
		contentPane.add(lblTiempoDePartida);
		
		JLabel lblTiempoDeTurno = new JLabel("Tiempo de Turno: 5");
		lblTiempoDeTurno.setBounds(495, 489, 115, 14);
		contentPane.add(lblTiempoDeTurno);
		
		// Boton Arriba
		ImageIcon iconUp = new ImageIcon(VentanaJuego.class.getResource("/img/arriba.png"));
		ImageIcon iconUpHover = new ImageIcon(VentanaJuego.class.getResource("/img/arribaHover.png"));
		ImageIcon iconUpActive = new ImageIcon(VentanaJuego.class.getResource("/img/arribaActive.png"));
		JButton btnUp = new JButton("");
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
		JButton btnDown = new JButton("");
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
		JButton btnLeft = new JButton("");
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
		JButton btnRight = new JButton("");
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
			this.dispose();
		} 
	}
	
	// Movimientos
	private void izquierda() {
		//Envio por socket el movimiento
	}
	
	private void derecha() {
		//Envio por socket el movimiento
	}
	
	private void arriba() {
		//Envio por socket el movimiento
	}
	
	private void abajo() {
		//Envio por socket el movimiento
	}
}

