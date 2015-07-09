package datosSocket;

import java.io.Serializable;
import java.util.List;

import servidor.Jugador;

public class DatosUnirsePartida implements Serializable {

	private int usuarioId;
	private int partidaId;
	private int estadoPartida;
	private String nombrePartida;
	private int tipoJugador;

	private int matriz[][];
	private List<Jugador> jugadores;

	public DatosUnirsePartida() {

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

	public List<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<Jugador> jugadores) {
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
