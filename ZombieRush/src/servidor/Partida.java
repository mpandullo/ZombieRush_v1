package servidor;

import java.util.List;

import datosSocket.DatosPartida;

public class Partida {

	private int partidaId;
	private String nombre;
	private int minJugadores;
	private int maxJugadores;
	private int cantJugadores;
	private int estado = 0; // 0 en espera - 1 activo
	
	private Tablero tablero = new Tablero();

	// Getters And Setters
	public int getPartidaId() {
		return partidaId;
	}

	public void setPartidaId(int partidaId) {
		this.partidaId = partidaId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getMinJugadores() {
		return minJugadores;
	}

	public void setMinJugadores(int minJugadores) {
		this.minJugadores = minJugadores;
	}

	public int getMaxJugadores() {
		return maxJugadores;
	}

	public void setMaxJugadores(int maxJugadores) {
		this.maxJugadores = maxJugadores;
	}

	public int getCantJugadores() {
		return cantJugadores;
	}

	public void setCantJugadores(int cantJugadores) {
		this.cantJugadores = cantJugadores;
	}

	// Metodos
	public DatosPartida agregarJugador(DatosPartida datos) {
		if (this.cantJugadores != this.maxJugadores) {
			this.tablero.agregarJugador(new Jugador(datos));
			this.cantJugadores++;
		} else {
			datos.setUsuarioId(-1);
		}

		if (this.cantJugadores >= this.minJugadores && this.estado == 0) {
			datos.setEstado(1);
			this.estado = 1;
			this.iniciar();
		} else
			datos.setEstado(this.estado);

		return datos;
	}
	
	public void iniciar() {
		List<Jugador> jugadores = this.tablero.getJugadores();
		boolean flag = true;
		
		for (int i = 0; i < jugadores.size(); i++) {
			if (!jugadores.get(i).getFueZombie()) {
				flag = false;
				jugadores.get(i).setFueZombie(true);
			}
		}
		
		if (true) {
			for (int i = 0; i < jugadores.size(); i++) {
				jugadores.get(i).setFueZombie(false);
			}
			jugadores.get(0).setFueZombie(true);
		}
		
		
		
		//Enviar señal de inicio de partida a todos los clientes de la partida
	}
	
	

}
