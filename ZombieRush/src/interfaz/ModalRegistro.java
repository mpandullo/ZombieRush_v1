package interfaz;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;

public class ModalRegistro extends JDialog {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JTextField txtNombre;
	private JTextField txtCorreo;
	private JTextField txtRepCorreo;
	private JTextField txtRespSecreta;

	private String nombre;
	private String correo;
	private String usuario;
	private String password;
	private String respSecreta;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConf;
	private JLabel lblNombreCompleto;
	private JLabel lblCorreo;
	private JLabel lblRepetirCorreo;
	private JLabel lblPassword;
	private JLabel lblRepetirPassword;
	private JLabel lblPreguntaSeguridad;
	private JLabel lblRespuestaSeguridad;
	private JLabel lblNewLabel;

	//Constructor
	/**
	 * @wbp.parser.constructor
	 */
	public ModalRegistro(Login l) {
		super(l, true);
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

		JComboBox comboPregSeguridad = new JComboBox();
		comboPregSeguridad.setModel(new DefaultComboBoxModel(new String[] {
				"El nombre de tu madre?", "El nombre de tu primer mascota?",
				"Modelo de tu primer auto?" }));
		comboPregSeguridad.setBounds(179, 293, 230, 20);
		contentPane.add(comboPregSeguridad);

		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
		
		lblNombreCompleto = new JLabel("Nombre Completo:");
		lblNombreCompleto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombreCompleto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombreCompleto.setBounds(40, 158, 129, 20);
		contentPane.add(lblNombreCompleto);
		
		lblCorreo = new JLabel("Correo:");
		lblCorreo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCorreo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCorreo.setBounds(40, 184, 129, 20);
		contentPane.add(lblCorreo);
		
		lblRepetirCorreo = new JLabel("Repetir Correo:");
		lblRepetirCorreo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRepetirCorreo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRepetirCorreo.setBounds(40, 210, 129, 20);
		contentPane.add(lblRepetirCorreo);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassword.setBounds(40, 237, 129, 20);
		contentPane.add(lblPassword);
		
		lblRepetirPassword = new JLabel("Repetir Password:");
		lblRepetirPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRepetirPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRepetirPassword.setBounds(40, 262, 129, 20);
		contentPane.add(lblRepetirPassword);
		
		lblPreguntaSeguridad = new JLabel("Pregunta Seguridad:");
		lblPreguntaSeguridad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPreguntaSeguridad.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPreguntaSeguridad.setBounds(40, 293, 129, 20);
		contentPane.add(lblPreguntaSeguridad);
		
		lblRespuestaSeguridad = new JLabel("Respuesta Seguridad:");
		lblRespuestaSeguridad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRespuestaSeguridad.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRespuestaSeguridad.setBounds(40, 324, 129, 20);
		contentPane.add(lblRespuestaSeguridad);
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(ModalRegistro.class.getResource("/img/registro.png")));
		lblNewLabel.setBounds(179, 8, 119, 113);
		contentPane.add(lblNewLabel);
	}
	
	
}

