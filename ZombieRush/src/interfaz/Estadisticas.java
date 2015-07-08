package interfaz;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Estadisticas extends JDialog {

	private JPanel contentPane;
	private JTable tablaEstadisticas;
	private JLabel lblNewLabel;
	private JButton btnNewButton;
	private JLabel lblPosicinRankingGeneral;

	// Constructor
	public Estadisticas(PanelCliente p) {
		
		super(p,true);
		
		setTitle("Estadisticas");
		setBounds(100, 100, 405, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblJuegosGanados = new JLabel("Juegos Ganados:");
		lblJuegosGanados.setHorizontalAlignment(SwingConstants.RIGHT);
		lblJuegosGanados.setBounds(88, 150, 126, 19);
		contentPane.add(lblJuegosGanados);
		
		JLabel lblPuntosAcumulados = new JLabel("Puntos Acumulados:");
		lblPuntosAcumulados.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPuntosAcumulados.setBounds(88, 171, 126, 19);
		contentPane.add(lblPuntosAcumulados);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Estadisticas.class.getResource("/img/estadistica.png")));
		lblNewLabel.setBounds(128, 11, 120, 118);
		contentPane.add(lblNewLabel);
		
		btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setBounds(149, 365, 89, 23);
		contentPane.add(btnNewButton);
		
		lblPosicinRankingGeneral = new JLabel("Posici\u00F3n ranking general:");
		lblPosicinRankingGeneral.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPosicinRankingGeneral.setBounds(31, 193, 183, 14);
		contentPane.add(lblPosicinRankingGeneral);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(43, 233, 309, 119);
		contentPane.add(scrollPane_1);
		
		tablaEstadisticas = new JTable();
		scrollPane_1.setViewportView(tablaEstadisticas);
		tablaEstadisticas.setModel(new DefaultTableModel(
			new Object[][] {
				{"Apocalipsis Now", "24"},
				{"Corazones en Sangre", "15"},
			},
			new String[] {
				"Partida", "Puntos"
			}
		));
		
		JLabel label = new JLabel("5");
		label.setBounds(218, 152, 46, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("35");
		label_1.setBounds(218, 173, 46, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("8");
		label_2.setBounds(218, 193, 46, 14);
		contentPane.add(label_2);
		tablaEstadisticas.getColumnModel().getColumn(0).setPreferredWidth(127);
	}
}
