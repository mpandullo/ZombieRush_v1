package interfaz;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import cliente.SocketsCliente;
import datosSocket.DatosRegistro;
import java.awt.Toolkit;

public class ModalRegistro extends JDialog {

	private JPanel contentPane;
	
	private JTextField txtUsuario;
	private JTextField txtNombre;
	private JTextField txtCorreo;
	private JTextField txtRepCorreo;
	private JTextField txtRespSecreta;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConf;	
	private JComboBox comboPregSeguridad;
	
	private Login login;
	private SocketsCliente socket;
	private DatosRegistro datosRegistro;
	
	private Semaphore semReg = null;
	
	 private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * @wbp.parser.constructor
	 */
	public ModalRegistro(Login l, SocketsCliente socket, Semaphore semReg) {
		super(l, true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ModalRegistro.class.getResource("/img/icon ZR.png")));
		this.login = l;
		this.socket = socket;
		this.semReg = semReg;
		carga();		
	}
	
	// Constructor para paneladmin
	public ModalRegistro(PanelAdmin p) {
		super(p, true);
		carga();		
	}

	private void carga() {
		setTitle("Registro");
		setBounds(100, 100, 483, 444);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblUsuario = new JLabel("Nombre de Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuario.setBounds(40, 132, 129, 20);
		contentPane.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		txtUsuario.setToolTipText("Nombre completo");
		txtUsuario.setBounds(179, 132, 230, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);		

		txtNombre = new JTextField();
		txtNombre.setToolTipText("Nombre completo");
		txtNombre.setHorizontalAlignment(SwingConstants.LEFT);
		txtNombre.setColumns(10);
		txtNombre.setBounds(179, 158, 230, 20);
		contentPane.add(txtNombre);

		txtCorreo = new JTextField();
		txtCorreo.setToolTipText("Nombre completo");
		txtCorreo.setHorizontalAlignment(SwingConstants.LEFT);
		txtCorreo.setColumns(10);
		txtCorreo.setBounds(179, 184, 230, 20);
		contentPane.add(txtCorreo);

		txtRepCorreo = new JTextField();
		txtRepCorreo.setToolTipText("Nombre completo");
		txtRepCorreo.setHorizontalAlignment(SwingConstants.LEFT);
		txtRepCorreo.setColumns(10);
		txtRepCorreo.setBounds(179, 210, 230, 20);
		contentPane.add(txtRepCorreo);

		txtRespSecreta = new JTextField();
		txtRespSecreta.setToolTipText("Nombre completo");
		txtRespSecreta.setHorizontalAlignment(SwingConstants.LEFT);
		txtRespSecreta.setColumns(10);
		txtRespSecreta.setBounds(179, 324, 230, 20);
		contentPane.add(txtRespSecreta);

		comboPregSeguridad = new JComboBox();
		comboPregSeguridad.setModel(new DefaultComboBoxModel(new String[] {
				"El nombre de tu madre?", "El nombre de tu primer mascota?",
				"Modelo de tu primer auto?" }));
		comboPregSeguridad.setBounds(179, 293, 230, 20);
		contentPane.add(comboPregSeguridad);

		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					registro();
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRegistrarse.setBounds(137, 371, 102, 23);
		contentPane.add(btnRegistrarse);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancelar.setBounds(265, 371, 102, 23);
		contentPane.add(btnCancelar);

		passwordField = new JPasswordField();
		passwordField.setBounds(179, 237, 230, 20);
		contentPane.add(passwordField);

		passwordFieldConf = new JPasswordField();
		passwordFieldConf.setBounds(179, 262, 230, 20);
		contentPane.add(passwordFieldConf);
		
		JLabel lblNombreCompleto = new JLabel("Nombre Completo:");
		lblNombreCompleto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombreCompleto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombreCompleto.setBounds(40, 158, 129, 20);
		contentPane.add(lblNombreCompleto);
		
		JLabel lblCorreo = new JLabel("Correo:");
		lblCorreo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCorreo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCorreo.setBounds(40, 184, 129, 20);
		contentPane.add(lblCorreo);
		
		JLabel lblRepetirCorreo = new JLabel("Repetir Correo:");
		lblRepetirCorreo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRepetirCorreo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRepetirCorreo.setBounds(40, 210, 129, 20);
		contentPane.add(lblRepetirCorreo);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassword.setBounds(40, 237, 129, 20);
		contentPane.add(lblPassword);
		
		JLabel lblRepetirPassword = new JLabel("Repetir Password:");
		lblRepetirPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRepetirPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRepetirPassword.setBounds(40, 262, 129, 20);
		contentPane.add(lblRepetirPassword);
		
		JLabel lblPreguntaSeguridad = new JLabel("Pregunta Seguridad:");
		lblPreguntaSeguridad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPreguntaSeguridad.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPreguntaSeguridad.setBounds(40, 293, 129, 20);
		contentPane.add(lblPreguntaSeguridad);
		
		JLabel lblRespuestaSeguridad = new JLabel("Respuesta Seguridad:");
		lblRespuestaSeguridad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRespuestaSeguridad.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRespuestaSeguridad.setBounds(40, 324, 129, 20);
		contentPane.add(lblRespuestaSeguridad);
		
		JLabel lblImagen = new JLabel("New label");
		lblImagen.setIcon(new ImageIcon(ModalRegistro.class.getResource("/img/registro.png")));
		lblImagen.setBounds(179, 8, 119, 113);
		contentPane.add(lblImagen);
	}
	
	public void registro() throws IOException, InterruptedException {
		String usuario = this.txtUsuario.getText().trim();
		String nombre = this.txtNombre.getText().trim();
		String password = this.passwordField.getText();
		String repPassword = this.passwordFieldConf.getText();
		String correo = this.txtCorreo.getText().trim();
		String repCorreo = this.txtRepCorreo.getText().trim();
		int pregunta = this.comboPregSeguridad.getSelectedIndex();
		String respuesta = this.txtRespSecreta.getText().trim();
		
		boolean valida = true;
		
		if (usuario.isEmpty() || nombre.isEmpty() || password.length() == 0 || correo.isEmpty() || respuesta.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
			valida = false;
		} else {
			if (usuario.length() < 6 || password.length() < 6) {
				JOptionPane.showMessageDialog(this, "El usuario y contraseña deben tener al menos 6 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
				valida = false;
			} else {
				if (password.compareTo(repPassword) != 0) {
					JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
					valida = false;
				} else {
					if (!validarCorreo(correo)) {
						JOptionPane.showMessageDialog(this, "Ingrese una direccion de correo válida", "Error", JOptionPane.ERROR_MESSAGE);
						valida = false;
					} else {
						if (correo.compareTo(repCorreo) != 0) {
							JOptionPane.showMessageDialog(this, "Las direcciones de correo no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
							valida = false;
						}
					}
				}
			}
		}		
		
		if (valida) {
			datosRegistro = new DatosRegistro(usuario, nombre, correo, password, pregunta, respuesta);
			
			this.socket.enviarObjeto(datosRegistro);
			
			this.semReg.acquire();
			this.semReg.acquire();
			
			int valor = datosRegistro.getPregunta();
			if (valor != -1) {
				JOptionPane.showMessageDialog(this, "Registro Exitoso", "Registro", JOptionPane.INFORMATION_MESSAGE);
				this.login.setUsuario(usuario);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Nombre de usuario no disponible", "Error", JOptionPane.ERROR_MESSAGE);
			}			
		}		
		
		this.semReg.release();
	}
	
	private boolean validarCorreo(String correo) {

        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
 
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches(); 
    }	
	
	public void setDatosRegistro(DatosRegistro datos) {
		this.datosRegistro = datos;
	}
}

