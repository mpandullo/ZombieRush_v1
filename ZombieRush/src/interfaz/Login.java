package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import servidor.ConsultasUsuario;
import cliente.JuegoAdmin;
import cliente.JuegoCliente;
import cliente.SocketsCliente;
import cliente.UsuarioAdmin;
import cliente.UsuarioNormal;
import datosSocket.DatosLogin;

@SuppressWarnings("serial")
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	private Semaphore semLogin = null;
	private Semaphore semUP = null;
	private SocketsCliente clientSocket = null;

	private DatosLogin datosLogin;

	// Constructor
	public Login(Semaphore semLogin, Semaphore semUP) {

		this.semLogin = semLogin;
		this.semUP = semUP;

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
				try {
					login();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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

	private void login() throws InterruptedException, IOException {
		
		String usr = this.txtUsuario.getText().trim();
		String pass = this.txtPassword.getText().trim();
		if( usr.isEmpty() || pass.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Los campos no pueden estar vacios", "error", JOptionPane.ERROR_MESSAGE);
			vaciarCampos();
			return;
		}
		this.datosLogin = new DatosLogin(this.txtUsuario.getText(),
				this.txtPassword.getText());
		this.clientSocket.enviarObjeto(datosLogin);
		
		semLogin.acquire();
		semLogin.acquire();
		int valor = datosLogin.getIdUsuario();
		
		// VERRRRR
		//ConsultasUsuario.cargarTablaPrincipal();

		switch (valor) {
		case -1:
			JOptionPane.showMessageDialog(this,
					"Usuario y/o contraseña inválidos", "Error",
					JOptionPane.ERROR_MESSAGE);
			this.vaciarCampos();
			break;

		case -2:
			JOptionPane.showMessageDialog(this, "Usuario inexistente", "Error",
					JOptionPane.ERROR_MESSAGE);
			this.vaciarCampos();
			break;

		default:
			if (datosLogin.getTipoUsuario() == 1) {
				UsuarioNormal usuario = new UsuarioNormal(datosLogin);
				this.setVisible(false);
				JuegoCliente juego = new JuegoCliente(this, usuario);
				this.clientSocket.setJuegoCliente(juego);
				this.clientSocket.setJuegoClientThread(juego);
			} else {
				UsuarioAdmin usuario = new UsuarioAdmin(datosLogin);
				this.setVisible(false);
				JuegoAdmin juego = new JuegoAdmin(this, usuario);
			}

			break;
		}
		semLogin.release();
	}

	public void setDatosLogin(DatosLogin datos) {
		this.datosLogin = datos;
	}

	public void setClientSocket(SocketsCliente clientSocket) {
		this.clientSocket = clientSocket;
	}

	public SocketsCliente getClientSocket() {
		return clientSocket;
	}

	public Semaphore getSemUP() {
		return semUP;
	}
}
