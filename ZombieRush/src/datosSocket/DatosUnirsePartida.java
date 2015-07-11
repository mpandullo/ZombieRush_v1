package datosSocket;

import java.io.Serializable;
import java.util.ArrayList;

import servidor.Jugador;

public class DatosUnirsePartida implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int usuarioId = -1;
	private int partidaId = -1;
	private int estadoPartida = -1;
	private String nombrePartida = "";
	private int tipoJugador = -1;

	private int matriz[][] = null;
	private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

	public DatosUnirsePartida() {

	}
	
	public DatosUnirsePartida(DatosUnirsePartida datos) {
		this.usuarioId = datos.getUsuarioId();
		this.partidaId = datos.getPartidaId();
		this.estadoPartida = datos.getEstadoPartida();
		this.nombrePartida = datos.getNombrePartida();
		this.tipoJugador = datos.getTipoJugador();
		this.matriz = datos.getMatriz();
		this.jugadores = datos.getJugadores();
	}
	

	public DatosUnirsePartida(int usuarioId, int partidaId) {
		this.usuarioId = usuarioId;
		this.partidaId = partidaId;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getPartidaId() {
		return partidaId;
	}

	public void setPartidaId(int partidaId) {
		this.partidaId = partidaId;
	}

	public int getEstadoPartida() {
		return estadoPartida;
	}

	public void setEstadoPartida(int estadoPartida) {
		this.estadoPartida = estadoPartida;
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

	public String getNombrePartida() {
		return nombrePartida;
	}

	public void setNombrePartida(String nombrePartida) {
		this.nombrePartida = nombrePartida;
	}

	public int getTipoJugador() {
		return tipoJugador;
	}

	public void setTipoJugador(int tipoJugador) {
		this.tipoJugador = tipoJugador;
	}

}
