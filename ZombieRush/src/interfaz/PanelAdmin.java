package interfaz;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import cliente.JuegoAdmin;
import cliente.UsuarioAdmin;
import datosSocket.DatosDesloguearse;

public class PanelAdmin extends JFrame {
	private JTable tablaUsuarios;
	private JTable tablaPartidas;
	
	private UsuarioAdmin usuario;
	private JuegoAdmin juego;
	private Login login;
	
	private ModalPartida mp;
	private ModalEditarPartida mep;
	private ModalRegistro mu;
	private ModalPerfil meu;
	
	JComboBox comboJuegos = new JComboBox();;

	// Constructor
	public PanelAdmin(Login login, JuegoAdmin juego, UsuarioAdmin usuario) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PanelAdmin.class.getResource("/img/admin.jpg")));
		
		setVisible(true);
		
		this.login = login;
		this.usuario = usuario;
		this.juego = juego;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				salir();
			}
		});
		setTitle("Zombie Rush - Panel Administrador");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 773, 405);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 767, 379);
		getContentPane().add(tabbedPane);

		// Panel de Usuarios
		JPanel panelUsuarios = new JPanel();
		tabbedPane.addTab("Usuarios", null, panelUsuarios, null);
		panelUsuarios.setLayout(null);

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//crearModalRegistro();
			}
		});
		btnAgregar.setBounds(238, 316, 89, 23);
		panelUsuarios.add(btnAgregar);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//editarUsuario();
			}
		});
		btnEditar.setBounds(337, 316, 89, 23);
		panelUsuarios.add(btnEditar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eliminarUsuario();
			}
		});
		btnEliminar.setBounds(436, 316, 89, 23);
		panelUsuarios.add(btnEliminar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 762, 300);
		panelUsuarios.add(scrollPane);

		// Tabla Usuarios
		tablaUsuarios = new JTable();
		tablaUsuarios.setFont(new Font("Verdana", Font.PLAIN, 11));
		//cargarUsuarios();
		tablaUsuarios.setModel(new DefaultTableModel(new Object[][] {
			{ "Pepe24", new Integer(5), new Integer(48),"10/11/2014 10:23:34", "06/06/2015 14:22:41" },
			{ "Lola", new Integer(2), new Integer(4),"14/03/2015 15:35:55", "21/05/2015 23:58:14" }, },
			new String[] { "Nombre de Usuario", "Partidas Jugadas","Cantidad de Puntos", "Creado", "Ultimo Acceso" }) {
			Class[] columnTypes = new Class[] { String.class, Integer.class,Integer.class, Object.class, Object.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tablaUsuarios.getColumnModel().getColumn(0).setResizable(false);
		tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(150);
		tablaUsuarios.getColumnModel().getColumn(0).setMinWidth(150);
		tablaUsuarios.getColumnModel().getColumn(0).setMaxWidth(150);
		tablaUsuarios.getColumnModel().getColumn(1).setResizable(false);
		tablaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(110);
		tablaUsuarios.getColumnModel().getColumn(1).setMinWidth(110);
		tablaUsuarios.getColumnModel().getColumn(1).setMaxWidth(110);
		tablaUsuarios.getColumnModel().getColumn(2).setResizable(false);
		tablaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(120);
		tablaUsuarios.getColumnModel().getColumn(2).setMinWidth(120);
		tablaUsuarios.getColumnModel().getColumn(2).setMaxWidth(120);
		tablaUsuarios.getColumnModel().getColumn(3).setResizable(false);
		tablaUsuarios.getColumnModel().getColumn(3).setPreferredWidth(140);
		tablaUsuarios.getColumnModel().getColumn(3).setMinWidth(140);
		tablaUsuarios.getColumnModel().getColumn(3).setMaxWidth(140);
		tablaUsuarios.getColumnModel().getColumn(4).setResizable(false);
		tablaUsuarios.getColumnModel().getColumn(4).setPreferredWidth(110);
		tablaUsuarios.getColumnModel().getColumn(4).setMinWidth(110);
		scrollPane.setViewportView(tablaUsuarios);

		// Panel Partidas
		JPanel panelPartidas = new JPanel();
		tabbedPane.addTab("Partidas", null, panelPartidas, null);
		panelPartidas.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 762, 300);
		panelPartidas.add(scrollPane_1);

		JButton btnAgregar_1 = new JButton("Agregar");
		btnAgregar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				crearModalPartida();
			}
		});
		btnAgregar_1.setBounds(340, 316, 89, 23);
		panelPartidas.add(btnAgregar_1);

		// Tabla Partidas
		tablaPartidas = new javax.swing.JTable(){
		    public boolean isCellEditable(int rowIndex, int colIndex) {
		        return false; //Deshabilitamos la edicion de celdas
		    }
		};
		
		this.cargarPartidas();
		
		tablaPartidas.getColumnModel().getColumn(0).setPreferredWidth(160);
		tablaPartidas.getColumnModel().getColumn(0).setMinWidth(160);
		tablaPartidas.getColumnModel().getColumn(0).setMaxWidth(160);
		tablaPartidas.getColumnModel().getColumn(1).setPreferredWidth(70);
		tablaPartidas.getColumnModel().getColumn(1).setMinWidth(70);
		tablaPartidas.getColumnModel().getColumn(1).setMaxWidth(70);
		tablaPartidas.getColumnModel().getColumn(2).setPreferredWidth(85);
		tablaPartidas.getColumnModel().getColumn(2).setMinWidth(85);
		tablaPartidas.getColumnModel().getColumn(2).setMaxWidth(85);
		tablaPartidas.getColumnModel().getColumn(3).setPreferredWidth(85);
		tablaPartidas.getColumnModel().getColumn(3).setMinWidth(85);
		tablaPartidas.getColumnModel().getColumn(3).setMaxWidth(85);
		tablaPartidas.getColumnModel().getColumn(4).setPreferredWidth(120);
		tablaPartidas.getColumnModel().getColumn(4).setMinWidth(120);
		tablaPartidas.getColumnModel().getColumn(4).setMaxWidth(120);
		tablaPartidas.getColumnModel().getColumn(5).setPreferredWidth(45);
		tablaPartidas.getColumnModel().getColumn(5).setMinWidth(45);
		tablaPartidas.getColumnModel().getColumn(5).setMaxWidth(45);
		
		scrollPane_1.setViewportView(tablaPartidas);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarPartidas();
			}
		});
		btnActualizar.setBounds(635, 316, 117, 23);
		panelPartidas.add(btnActualizar);
		
		// Panel Estadisticas
		JPanel panelEstadisticas = new JPanel();
		tabbedPane.addTab("Estadisticas", null, panelEstadisticas, null);
		panelEstadisticas.setLayout(null);

		comboJuegos.setFont(new Font("Verdana", Font.PLAIN, 12));
		comboJuegos.setBounds(168, 14, 163, 20);
		panelEstadisticas.add(comboJuegos);

		JLabel lblSeleccioneUnJuego = new JLabel("Seleccione un Juego:");
		lblSeleccioneUnJuego.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblSeleccioneUnJuego.setBounds(10, 14, 148, 17);
		panelEstadisticas.add(lblSeleccioneUnJuego);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 42, 750, 300);
		panelEstadisticas.add(scrollPane_2);

		JTextArea txtrEstadoActivoCantidad = new JTextArea();
		txtrEstadoActivoCantidad
				.setText("Estado: activo\r\nCantidad m\u00EDnima de Jugadores: 5\r\nCantidad m\u00E1xima de Jugadores: 10\r\nCantidad de Jugadores: 8\r\n\r\nTiempo de Juego(mm:ss): 20:00\r\n\r\nPuntos Acumulados: 124\r\nPuntos por Usuario:\r\nPepe24: 29\r\nLola: 5\r\n.\r\n.\r\n.\r\n\r\nMovimientos:\r\nPepe24: derecha\r\nLola: abajo\r\n.\r\n.\r\n.\r\n");
		scrollPane_2.setViewportView(txtrEstadoActivoCantidad);
	}
	
	public void cargarPartidas() {
		String[][] partidas = this.juego.obtenerPartidas();
		String[] combo = new String[partidas.length];
		
		tablaPartidas.setModel(new DefaultTableModel(partidas , new String[] { "Nombre Partida",
				"Estado", "Min Jugadores", "Max Jugadores",
				"Cantidad Jugadores", "Puntos", "Tiempo de Juego(mm:ss)" 
		}));
		
		for (int i = 0; i < partidas.length; i++) {
			combo[i] = partidas[i][0];
		}
		
		comboJuegos.setModel(new DefaultComboBoxModel(combo));
	}
	
	public void crearModalPartida() {
		ModalPartida mp = new ModalPartida(this, this.juego);
		mp.setVisible(true);
	}
	
	public void cargarUsuarios(){
		
	}
	
	public void eliminarUsuario(){
		
	}
	
	
	// Salir
	private void salir() {
		int opcion = JOptionPane.showConfirmDialog(this, "Desea salir?", "Salir", JOptionPane.YES_NO_OPTION);
		if( opcion == JOptionPane.YES_OPTION) {
			try {
				this.juego.getClientSocket().enviarObjeto(new DatosDesloguearse(this.usuario.getIdUsuario(), 0));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	}
}