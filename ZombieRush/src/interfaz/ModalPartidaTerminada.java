package interfaz;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import cliente.JuegoCliente;
import datosSocket.DatosContinuarPartida;

public class ModalPartidaTerminada extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JuegoCliente juego;
	private VentanaJuego ventana;
	private Integer cuenta = 0;

	public ModalPartidaTerminada(VentanaJuego ventana, JuegoCliente juego) {
		
		super(ventana, true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.ventana = ventana;
		this.juego = juego;
		
	
		setTitle("Partida Terminada");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ModalPartidaTerminada.class.getResource("/img/icon ZR.png")));
		
		setBounds(100, 100, 450, 183);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblPartidaTerminada = new JLabel("Partida Terminada");
		lblPartidaTerminada.setHorizontalAlignment(SwingConstants.CENTER);
		lblPartidaTerminada.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPartidaTerminada.setBounds(134, 11, 147, 29);
		contentPanel.add(lblPartidaTerminada);
		
		JLabel lblGanador = new JLabel("Ganador: ");
		lblGanador.setBounds(90, 51, 87, 14);
		contentPanel.add(lblGanador);
		
		JLabel lblDeseaSeguirJugando = new JLabel("Desea seguir jugando?");
		lblDeseaSeguirJugando.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeseaSeguirJugando.setBounds(134, 76, 160, 22);
		contentPanel.add(lblDeseaSeguirJugando);
		
		JButton btnNewButton = new JButton("Si");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				continuar();
			}
		});
		btnNewButton.setBounds(120, 109, 89, 23);
		contentPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("No");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abandonar();
			}
		});
		btnNewButton_1.setBounds(221, 109, 89, 23);
		contentPanel.add(btnNewButton_1);
		
		setLocationRelativeTo(null);
	}
	
	public void abandonar() {
		try {
			this.juego.abandonarPartida();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.ventana.dispose();
		this.dispose();
	}
	
	public void continuar() {
		try {
			this.juego.getClientSocket().enviarObjeto(new DatosContinuarPartida(this.juego.getPartidaIniciada()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.ventana.bloquearTeclado();
		this.juego.setContinuar(false);
		this.dispose();		
	}
}
