package interfaz;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ModalIniciandoPartida extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private PanelCliente panel;

	public  ModalIniciandoPartida(PanelCliente p) {
		super(p, true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
		
			}
		});
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		this.panel = p;
		
		setTitle("Iniciando");
		setResizable(false);
		setBounds(100, 100, 335, 101);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblLaPartidaComenzar = new JLabel("Inciando partida....");
		lblLaPartidaComenzar.setHorizontalAlignment(SwingConstants.LEFT);
		lblLaPartidaComenzar.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblLaPartidaComenzar.setBounds(74, 21, 245, 24);
		contentPanel.add(lblLaPartidaComenzar);
	}
}