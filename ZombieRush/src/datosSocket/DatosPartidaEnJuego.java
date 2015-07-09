package datosSocket;

import java.io.Serializable;
import java.util.List;

import servidor.Jugador;

public class DatosPartidaEnJuego implements Serializable {

	private int[][] matriz;
	private List<Jugador> jugadores;

	public DatosPartidaEnJuego(int[][] matriz, List<Jugador> jugadores) {
		this.matriz = matriz;
		this.jugadores = jugadores;
	}

	public int[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(int[][] matriz) {
		this.matriz = matriz;
	}

	public List<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<Jugador> jugadores) {
		this.jugadores = jugadores;
	}
}
