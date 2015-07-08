package interfaz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TableroJuego extends JPanel  {

	private Image background;
	private Image personaje;
	private int x, y;
	
	// Modificar para probar los tipos de personaje(zombie/humano)
	private String tipoPersonaje = "zombie";

	public TableroJuego() {
		setBackground(Color.WHITE);
		setDoubleBuffered(true);
		background = new ImageIcon(this.getClass().getResource(
				"../img/background.png")).getImage();
		if(this.tipoPersonaje == "humano")
			personaje = new ImageIcon(this.getClass().getResource("../img/humano.png")).getImage();
		else
			personaje = new ImageIcon(this.getClass().getResource("../img/zombie.png")).getImage();
			
		Random r = new Random();
		
		int[] posX = {30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 360, 390, 420, 450, 480, 510, 540};
		int[] posY = {30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 360, 390};
		
		x = posX[r.nextInt(posX.length)];
		y = posY[r.nextInt(posY.length)];
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if( this.tipoPersonaje == "zombie") {
			g2.drawImage(background, 0, 0, null);
		} else {
			// Limitamos la vista para el humano segun sus coordenadas
			int bdistx1 = x - 30;
			int bdistx2 = x + 60;
			int bdisty1 = y - 30;
			int bdisty2 = y + 60;		
			g2.drawImage(background, bdistx1, bdisty1, bdistx2, bdisty2, bdistx1, bdisty1, bdistx2, bdisty2, null);
		}
		
		g2.drawImage(personaje, x, y, null);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	
	public void derecha() {
		x += 30;
		if(x > 560)
			x -= 30;
		repaint();
	}
	
	public void arriba() {
		y -= 30;
		if(y < 30)
			y += 30;
		repaint();
	}
	
	public void abajo() {
		y += 30;
		if( y > 400 )
			y -= 30;
		repaint();
	}
	
	public void izquierda() {
		x -= 30;
		if( x < 30 )
			x += 30;
		repaint();
	}
}

