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
import java.awt.Toolkit;

public class PartidaEnEspera extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private PanelCliente panel;

	public PartidaEnEspera(PanelCliente p) {
		super(p, true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				abandonar();
			}
		});
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		this.panel = p;
		
		setTitle("Partida en Espera...");
		setResizable(false);
		setBounds(100, 100, 335, 140);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblLaPartidaComenzar = new JLabel("La partida comenzar\u00E1 cuando se supere la ");
		lblLaPartidaComenzar.setHorizontalAlignment(SwingConstants.LEFT);
		lblLaPartidaComenzar.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblLaPartidaComenzar.setBounds(46, 11, 245, 24);
		contentPanel.add(lblLaPartidaComenzar);
		
		JLabel lblCantidadMnimaDe = new JLabel("cantidad m\u00EDnima de jugadores");
		lblCantidadMnimaDe.setHorizontalAlignment(SwingConstants.LEFT);
		lblCantidadMnimaDe.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblCantidadMnimaDe.setBounds(74, 31, 245, 24);
		contentPanel.add(lblCantidadMnimaDe);
		
		JButton btnAbandonar = new JButton("Abandonar");
		btnAbandonar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abandonar();
			}
		});
		btnAbandonar.setBounds(110, 64, 118, 23);
		contentPanel.add(btnAbandonar);
	}
	
	private void abandonar() {
		int opcion = JOptionPane.showConfirmDialog(this, "Desea abandonar la partida?", "Salir", JOptionPane.YES_NO_OPTION);
		if( opcion == JOptionPane.YES_OPTION) {
			panel.abandonar();
			this.dispose();
		} 		
	}
}
