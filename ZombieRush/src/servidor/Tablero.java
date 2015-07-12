package servidor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import datosSocket.DatosMovimiento;

public class Tablero {
	
	private Partida partida;
	
	private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
	
	private int ancho = 20;
	private int alto = 15;
	private int[][] matriz = {{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
							  {-1, 0, 0 ,0 ,0,-1, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0, 0,-1},
							  {-1, 0, 0 ,0 ,0,-1, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0, 0,-1},
							  {-1, 0, 0 ,0 ,0, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0,-1,-1,-1,-1},
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1, 0, 0,-1, 0, 0, 0, 0, 0, 0, 0,-1},
							  {-1, 0, 0,-1, 0, 0,-1, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0, 0,-1},
							  {-1, 0, 0,-1, 0, 0,-1, 0, 0, 0, 0,-1,-1,-1,-1,-1,-1, 0, 0,-1},
							  {-1, 0, 0,-1, 0, 0,-1, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0, 0,-1},
							  {-1, 0, 0,-1, 0, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0, 0,-1},
							  {-1, 0, 0,-1, 0, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0, 0,-1},
							  {-1, 0, 0,-1, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0,-1, 0, 0,-1,-1,-1},
							  {-1, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0,-1},
							  {-1, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0,-1},
							  {-1, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0,-1},
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}};
	
	
	public Tablero(Partida partida) {
		this.partida = partida;
	}
	
	//Getters and Setters
	public ArrayList<Jugador> getJugadores() {
		return this.jugadores;
	}
	
	public int[][] getMapa() {
		return this.matriz;
	}
		
	// Metodos
	public void agregarJugador(Jugador jugador) {
		this.jugadores.add(jugador);
		
		Random r = new Random();
		int x = r.nextInt(alto);
		int y = r.nextInt(ancho);
		
		while(matriz[x][y] != 0) {
			x = r.nextInt(alto);
			y = r.nextInt(ancho);
		}
		
		matriz[x][y] = jugador.getUsuarioId();
	}
	
	public int[] buscar(int id) {
		int[] pos = new int[2];
		
		for (int i = 0; i < alto; i++) {
			for (int j = 0; j < ancho; j++) {
				if (matriz[i][j] == id) {
					pos[0] = i;
					pos[1] = j;
				}					
			}
		}
		
		return pos;
	}
	
	public void eliminarJugador(Jugador jugador) {
		int[] pos = buscar(jugador.getUsuarioId());
		this.jugadores.remove(jugador);
		matriz[pos[0]][pos[1]] = 0;
	}
	
	public void mover(DatosMovimiento m) {
		System.out.println("procesando movimiento " + m.getUsuarioId());
		int[] pos = buscar(m.getUsuarioId());
		
		switch (m.getMovimiento()) {
		case 'u':
			System.out.println("movimiento izquierda");
			if (matriz[pos[0]-1][pos[1]] != -1) {
				if (matriz[pos[0]-1][pos[1]] != 0) {
					convertir(pos, pos[0]-1, pos[1]);
				} else {
					matriz[pos[0]-1][pos[1]] = m.getUsuarioId();
					matriz[pos[0]][pos[1]] = 0;
				}
			}				
			break;
			
		case 'd':
			System.out.println("movimiento derecha");
			if (matriz[pos[0]+1][pos[1]] != -1) {
				if (matriz[pos[0]+1][pos[1]] != 0) {
					convertir(pos, pos[0]+1, pos[1]);
				} else {
					matriz[pos[0]+1][pos[1]] = m.getUsuarioId();
					matriz[pos[0]][pos[1]] = 0;
				}
			}				
			break;
			
		case 'r':
			System.out.println("movimiento arriba");
			if (matriz[pos[0]][pos[1]+1] != -1) {
				if (matriz[pos[0]][pos[1]+1] != 0) {
					convertir(pos, pos[0], pos[1]+1);
				} else {
					matriz[pos[0]][pos[1]+1] = m.getUsuarioId();
					matriz[pos[0]][pos[1]] = 0;
				}
			}				
			break;
			
		case 'l':
			System.out.println("movimiento abajo");
			if (matriz[pos[0]][pos[1]-1] != -1) {
				if (matriz[pos[0]][pos[1]-1] != 0) {
					convertir(pos, pos[0], pos[1]-1);
				} else {
					matriz[pos[0]][pos[1]-1] = m.getUsuarioId();
					matriz[pos[0]][pos[1]] = 0;
				}
			}				
			break;

		default:
			break;
		}
	}
	
	public void convertir(int[] pos, int x, int y) {
		int jugOrigen = 0;
		int jugDestino = 0;
		
		for (int i = 0; i < this.jugadores.size(); i++) {
			if (this.jugadores.get(i).getUsuarioId() == matriz[x][y]) {
				jugDestino = i;
			}
			if (this.jugadores.get(i).getUsuarioId() == matriz[pos[0]][pos[1]]) {
				jugOrigen = i;
			}
		}
		
		if (jugadores.get(jugOrigen).getTipo() == 0 && jugadores.get(jugDestino).getTipo() == 1) {
			jugadores.get(jugOrigen).setTipo(1);
			this.partida.incremetarZombie();			
		}
		
		if (jugadores.get(jugOrigen).getTipo() == 1 && jugadores.get(jugDestino).getTipo() == 0) {
			jugadores.get(jugDestino).setTipo(1);
			this.partida.incremetarZombie();	
		}		
	}
}
