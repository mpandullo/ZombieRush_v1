package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import cliente.JuegoCliente;
import cliente.UsuarioNormal;

public class PanelCliente extends JDialog {

	private JPanel contentPane;
	private JTable table;
	
	private UsuarioNormal usuario;
	private Login login;
	private JuegoCliente juego;
	
	private PartidaEnEspera espera;
	private ModalIniciandoPartida iniciar;
	
	// Constructor
	public PanelCliente(Login login, JuegoCliente juego, UsuarioNormal usuario) {
		
		super(login);
		this.login = login;		
		this.usuario = usuario;
		this.juego = juego;
		
		this.setVisible(true);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				salir();
			}
		});
		
		setResizable(false);
		setTitle("Zombie Rush");
		setBounds(100, 100, 457, 232);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		
		JMenu mnArchivo = new JMenu(this.usuario.getUsuario());
		menuBar.add(mnArchivo);
		
		JMenuItem mnRegistrarme = new JMenuItem("Perfil");
		mnRegistrarme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				perfil();
			}
		});
		mnArchivo.add(mnRegistrarme);
		
		JMenuItem mntmEstadisticas = new JMenuItem("Estadisticas");
		mntmEstadisticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirStats();
			}
		});
		mnArchivo.add(mntmEstadisticas);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				salir();
			}
		});
		
		JMenuItem mntmCerrarSesin = new JMenuItem("Cerrar Sesi\u00F3n");
		mntmCerrarSesin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cerrarSesion();
			}
		});
		mnArchivo.add(mntmCerrarSesin);
		
			
		mnArchivo.add(mntmSalir);
		
		JMenu mnEstadisticas = new JMenu("Ranking");
		menuBar.add(mnEstadisticas);
		
		JMenuItem mntmVer = new JMenuItem("Ver");
		mntmVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirRanking();
			}
		});
		mnEstadisticas.add(mntmVer);
		
		JMenu mnSalir = new JMenu("Ayuda");
		menuBar.add(mnSalir);
		
		JMenuItem mntmInstrucciones = new JMenuItem("Instrucciones");
		mnSalir.add(mntmInstrucciones);
		
		JMenuItem mntmAcerdaDe = new JMenuItem("acerca de Zombie Rush");
		mnSalir.add(mntmAcerdaDe);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Unirse a partida
		JButton btnUnirse = new JButton("Unirse");
		btnUnirse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				unirse();
			}
		});
		btnUnirse.setBounds(182, 149, 89, 23);
		contentPane.add(btnUnirse);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 449, 139);
		contentPane.add(scrollPane);
		
		table = new javax.swing.JTable(){
		    public boolean isCellEditable(int rowIndex, int colIndex) {
		        return false; //Deshabilitamos la edicion de celdas
		    }
		};
		scrollPane.setViewportView(table);
		table.setShowVerticalLines(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarDatosTabla();
			}
		});
		btnActualizar.setBounds(336, 149, 113, 23);
		contentPane.add(btnActualizar);
		// Cargamos los datos de la tabla
		this.cargarDatosTabla();
		table.getColumnModel().getColumn(0).setPreferredWidth(116);
	}
	
	//Metodos
	private void cerrarSesion() {
		int respuesta = JOptionPane.showConfirmDialog(this, "Desea cerrar la Sesión", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION ) {
			this.dispose();
			login.vaciarCampos();
			login.setVisible(true);
		}		
	}
	
	private void cargarDatosTabla() {
		String[][] datosTabla = juego.obtenerPartidas();
	
		this.table.setModel(new DefaultTableModel(datosTabla
				, new String[] {
					"Nombre", "Participantes", "Estado"
				}
			));
	}
	
	public void unirse() {
		int partida = this.table.getSelectedRow();
		
		if (partida == -1) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una partida", "", JOptionPane.WARNING_MESSAGE);
		} else {
			try {
				juego.unirsePartida(partida, this);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
	
	public void mensajeErrorUnirse() {
		JOptionPane.showMessageDialog(this, "No puede unirse a esta partida", "Unirse", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void enEspera() {
		espera = new PartidaEnEspera(this);
		espera.setVisible(true);
	}
	
	public void modalIniciando() {
		this.iniciar = new ModalIniciandoPartida(this);
		this.iniciar.setVisible(true);
	}
	
	public PartidaEnEspera getEspera() {
		return this.espera;
	}
	
	public ModalIniciandoPartida getInciando() {
		return this.iniciar;
	}
	
	public void abandonar() {
		try {
			this.juego.abandonarPartida();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	// Salir
	private void salir() {
		int opcion = JOptionPane.showConfirmDialog(this, "seguro que desea salir?", "Seleccionar una Opción",JOptionPane.YES_NO_OPTION);
		if( opcion == JOptionPane.YES_OPTION) {
			System.exit(0);
		} 
	}
	
	// Abrir Perfil
	private void perfil() {
		ModalPerfil perfil = new ModalPerfil(this, this.usuario);
		perfil.setVisible(true);
	}
		
	// Abrir Estadisticas
	private void abrirStats() {

	}
	
	// Abrir Ranking
	private void abrirRanking() {

	}
}