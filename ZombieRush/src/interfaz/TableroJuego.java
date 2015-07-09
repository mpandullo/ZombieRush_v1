package interfaz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TableroJuego extends JPanel  {

	private Image background;
	private Image zombie;
	private Image humano;
	
	private int[][] matriz;	
	private int[] jugadores;
	
	// 0 -> Humano / 1 -> Zombie
	private int tipoPersonaje = 0;
	private int id = 4;
	private int[] pos = new int[2];

	public TableroJuego(int[][] matriz, int[] jugadores, int tipo) {
		this.matriz = matriz;
		this.jugadores = jugadores;
		this.tipoPersonaje = tipo;
		
		setBackground(Color.WHITE);
		setDoubleBuffered(true);
		
		background = new ImageIcon(this.getClass().getResource(	"../img/background.png")).getImage();		
		humano = new ImageIcon(this.getClass().getResource("../img/humano.png")).getImage();
		zombie = new ImageIcon(this.getClass().getResource("../img/zombie.png")).getImage();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				if (matriz[i][j] == id) {
					pos[0] = j;
					pos[1] = i;
				}
			}
		}		
		
		pos[0] *= 30;
		pos[1] *= 30;
		
		if( this.tipoPersonaje == 1) {
			g2.drawImage(background, 0, 0, null);
		} else {
			// Limitamos la vista para el humano segun sus coordenadas
			int bdistx1 = pos[0] - 30;
			int bdistx2 = pos[0] + 60;
			int bdisty1 = pos[1] - 30;
			int bdisty2 = pos[1] + 60;		
			g2.drawImage(background, bdistx1, bdisty1, bdistx2, bdisty2, bdistx1, bdisty1, bdistx2, bdisty2, null);
		}
		
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				if (matriz[i][j] > 0) {
					g2.drawImage(humano, j*30, i*30, null);
				}
			}
		}
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	
	public void actualizar(int[][] matriz, int[] jugadores, int tipo) {
		this.matriz = matriz;
		this.jugadores = jugadores;
		this.tipoPersonaje = tipo;
		this.repaint();
	}
}

