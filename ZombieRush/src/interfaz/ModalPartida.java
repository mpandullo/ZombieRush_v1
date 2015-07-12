package interfaz;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import cliente.JuegoAdmin;
import java.awt.Toolkit;

public class ModalPartida extends JDialog {

	private JPanel contentPane;
	private JTextField textNombre;
	private JTextField textMinJug;
	private JTextField textMaxJug;
	
	private JuegoAdmin juego;
	private PanelAdmin panel;
	
	// Constructor
	public ModalPartida(PanelAdmin p, JuegoAdmin juego) {	
		// Le decimos al contructor q es una clase hija de PanelAdmin
		super(p, true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ModalPartida.class.getResource("/img/icon ZR.png")));
		this.panel = p;
		this.juego = juego;
		
		setTitle("Crear Partida");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 421, 237);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JLabel lblCrearUnaNueva = new JLabel("Crear una nueva Partida");
		lblCrearUnaNueva.setFont(new Font("Verdana", Font.BOLD, 16));
		
		JLabel lblNombreDePartida = new JLabel("Nombre de Partida:");
		lblNombreDePartida.setFont(new Font("Verdana", Font.PLAIN, 11));
		
		textNombre = new JTextField();
		textNombre.setColumns(10);
		
		JLabel lblNMnimoDe = new JLabel("N\u00B0 m\u00EDnimo de Jugadores:");
		lblNMnimoDe.setFont(new Font("Verdana", Font.PLAIN, 11));
		
		textMinJug = new JTextField();
		textMinJug.setColumns(10);
		
		JLabel lblNMximoDe = new JLabel("N\u00B0 m\u00E1ximo de Jugadores:");
		lblNMximoDe.setFont(new Font("Verdana", Font.PLAIN, 11));
		
		textMaxJug = new JTextField();
		textMaxJug.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					agregarPartida();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNombreDePartida, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNMnimoDe, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNMximoDe, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(textMaxJug, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
										.addComponent(textMinJug, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
										.addComponent(textNombre, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)))
								.addComponent(lblCrearUnaNueva, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(117)
							.addComponent(btnAceptar)
							.addGap(18)
							.addComponent(btnCancelar)))
					.addContainerGap(46, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(12)
					.addComponent(lblCrearUnaNueva, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNombreDePartida, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(textNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNMnimoDe, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(textMinJug, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNMximoDe, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(textMaxJug, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnAceptar)
						.addComponent(btnCancelar))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void agregarPartida() throws IOException {
		if(Integer.parseInt(this.textMinJug.getText()) > Integer.parseInt(this.textMaxJug.getText()))
			JOptionPane.showMessageDialog(this, "La cantidad minima de participantes no puede ser mayor que la cantidad maxima de participantes", "Error", JOptionPane.ERROR_MESSAGE);
		else{	
			int respuesta = this.juego.agregarPartida(this.textNombre.getText(), this.textMinJug.getText(), this.textMaxJug.getText());
			if (respuesta == -2) {
				JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
			}
		
			if (respuesta == -1) {
				JOptionPane.showMessageDialog(this, "Inserte un número válido", "Error", JOptionPane.ERROR_MESSAGE);
			}
		
			if (respuesta == 1) {
				JOptionPane.showMessageDialog(this, "Partida Creada", "Nueva Partida", JOptionPane.INFORMATION_MESSAGE);
				this.panel.cargarPartidas();
				this.dispose();
			}
		}
	}
}