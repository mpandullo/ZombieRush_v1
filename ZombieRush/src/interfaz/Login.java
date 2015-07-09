package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import cliente.JuegoAdmin;
import cliente.JuegoCliente;
import cliente.SocketCliente;
import cliente.UsuarioAdmin;
import cliente.UsuarioNormal;
import datosSocket.DatosLogin;

@SuppressWarnings("serial")
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	
	// Main
	public static void main(String[] args) {		
		Login login = new Login();
	}

	// Constructor
	public Login() {

		setTitle("Zombie Rush");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(105, 73, 77, 14);
		contentPane.add(lblUsuario);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(105, 113, 77, 14);
		contentPane.add(lblContrasea);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(181, 70, 86, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(181, 110, 86, 20);
		contentPane.add(txtPassword);

		// Boton entrar
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				login();
			}
		});
		btnEntrar.setBounds(48, 183, 89, 23);
		contentPane.add(btnEntrar);

		// Boton Salir
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnSalir.setBounds(310, 183, 89, 23);
		contentPane.add(btnSalir);

		// Boton Registrarse
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaRegistro();
			}
		});
		btnRegistrarse.setBounds(166, 183, 112, 23);
		contentPane.add(btnRegistrarse);
		
		this.setVisible(true);
	}

	/* METODOS */
	// Abrimos la ventana de registro
	private void abrirVentanaRegistro() {
		try {
			ModalRegistro ventanaRZR = new ModalRegistro(this);
			ventanaRZR.setVisible(true);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public void vaciarCampos() {
		this.txtPassword.setText("");
		this.txtUsuario.setText("");
	}
	
	public void setUsuario(String usuario) {
		this.txtUsuario.setText(usuario);
	}
	
	private void login() {		
		// Cambiar el metodo q obtiene la pass
		DatosLogin datosLogin = new DatosLogin(this.txtUsuario.getText(), this.txtPassword.getText());
		datosLogin = SocketCliente.login(datosLogin);
		
		int valor = datosLogin.getIdUsuario();
		
		switch (valor) {			
		case -1:
			JOptionPane.showMessageDialog(this, "Usuario y/o contraseña inválidos", "Error", JOptionPane.ERROR_MESSAGE);
			this.vaciarCampos();
			break;
			
		case -2:
			JOptionPane.showMessageDialog(this, "Usuario inexistente", "Error", JOptionPane.ERROR_MESSAGE);
			this.vaciarCampos();
			break;

		default:
			if (datosLogin.getTipoUsuario() == 0) {
				UsuarioNormal usuario = new UsuarioNormal(datosLogin);
				this.setVisible(false);
				JuegoCliente juego = new JuegoCliente(this, usuario);				
			} else {
				UsuarioAdmin usuario = new UsuarioAdmin(datosLogin);
				this.setVisible(false);
				JuegoAdmin juego = new JuegoAdmin(this, usuario);
			}
			
			break;
		}
	}
}
