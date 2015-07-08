package interfaz;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Ranking extends JDialog {

	private JPanel contentPane;
	private JTable table;
	private JLabel lblTopUsuarios;
	private JButton btnAceptar;

	// Constructor
	public Ranking(PanelCliente p) {
		
		super(p, true);
		
		setResizable(false);
		setTitle("Ranking");
		setBounds(100, 100, 238, 485);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 52, 211, 347);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setShowVerticalLines(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{new Integer(1), "Eralesan", "5678"},
				{new Integer(2), "Sartoric", "4567"},
				{new Integer(3), "Marcosos3", "3456"},
				{new Integer(4), "Hart", "2343"},
				{new Integer(5), "Posos", "2245"},
				{new Integer(6), "Terrer", "2190"},
				{new Integer(7), "Arteria23", "1908"},
				{new Integer(8), "Burtit", "1867"},
				{new Integer(9), "Ponterial", "1854"},
				{new Integer(10), "Athenas", "1765"},
				{new Integer(11), "Rarantho", "1733"},
				{new Integer(12), "Moctezuma1", "1645"},
				{new Integer(13), "Hades456", "1600"},
				{new Integer(14), "Cerberus", "1432"},
				{new Integer(15), "Daredevil666", "1321"},
				{new Integer(16), "SuperMario", "1234"},
				{new Integer(17), "EsperanzaDivina", "1167"},
				{new Integer(18), "cartonero_fiel", "1045"},
				{new Integer(19), "Yupanqui", "1000"},
				{new Integer(20), "GatoDeFierro", "432"},
			},
			new String[] {
				"Posicion", "Usuario", "Puntos"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(1).setPreferredWidth(96);
		scrollPane.setViewportView(table);
		
		lblTopUsuarios = new JLabel("Top 20 Usuarios");
		lblTopUsuarios.setFont(new Font("Verdana", Font.BOLD, 16));
		lblTopUsuarios.setBounds(40, 11, 184, 30);
		contentPane.add(lblTopUsuarios);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAceptar.setBounds(70, 410, 89, 23);
		contentPane.add(btnAceptar);
	}
}

