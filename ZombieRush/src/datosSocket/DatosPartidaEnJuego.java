package datosSocket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import servidor.Jugador;

public class DatosPartidaEnJuego implements Serializable {

	private int[][] matriz = null;
	private ArrayList<Jugador> jugadores = null;

	public DatosPartidaEnJuego(int[][] matriz, ArrayList<Jugador> jugadores) {
		this.matriz = matriz;
		this.jugadores = jugadores;
	}

	public int[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(int[][] matriz) {
		this.matriz = matriz;
	}

	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}
}
