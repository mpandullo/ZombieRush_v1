package interfazServer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import servidor.Constantes;
import servidor.JuegoServer;
import servidor.ServerAcceptSocketsThread;
import javax.swing.JTextArea;

public class PanelServer extends JFrame {

	private JPanel contentPane;
	private JTextField txtFieldPuerto;

	private JButton btnIniciar;

	private JuegoServer juegoServer;
	private List<Socket> socketsList = new ArrayList<Socket>();
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// PanelServer frame = new PanelServer();
					// frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PanelServer(JuegoServer juegoServer) {
		setTitle("ZombieRush Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					iniciarSocket();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnIniciar.setBounds(236, 11, 89, 23);
		contentPane.add(btnIniciar);

		JLabel lblPuerto = new JLabel("Puerto:");
		lblPuerto.setBounds(10, 15, 46, 14);
		lblPuerto.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblPuerto);

		txtFieldPuerto = new JTextField();
		txtFieldPuerto.setBounds(66, 12, 86, 20);
		contentPane.add(txtFieldPuerto);
		txtFieldPuerto.setColumns(10);

		JButton btnDetener = new JButton("Detener");
		btnDetener.setBounds(335, 11, 89, 23);
		contentPane.add(btnDetener);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 45, 414, 286);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		this.juegoServer = juegoServer;
	}

	protected void iniciarSocket() throws IOException {
		int puerto = 0;
		String port = this.txtFieldPuerto.getText();
		if (!port.isEmpty())
			puerto = Integer.parseInt(port);
		else
			puerto = Constantes.PUERTO;

		System.out.println(puerto);
		ServerSocket serverSocket = new ServerSocket(puerto);

		ServerAcceptSocketsThread accept = new ServerAcceptSocketsThread(
				serverSocket, this.juegoServer, this);
		Thread threadAccept = new Thread(accept);
		threadAccept.start();

		this.btnIniciar.setEnabled(false);
	}
	
	public void escribirLog(String linea){
		this.textArea.append(linea + "\n");
	}
}
