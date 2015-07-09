package servidor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import datosSocket.DatosMovimiento;
import datosSocket.DatosPartidaEnJuego;
import datosSocket.DatosUnirsePartida;

public class Partida {

	private int partidaId;
	private String nombre;
	private int minJugadores;
	private int maxJugadores;
	private int cantJugadores;
	private int estado = 0; // 0 en espera - 1 activo
	
	private List<UsuarioNormal> usuario = new ArrayList<UsuarioNormal>();
	private Tablero tablero = new Tablero();
	
	private Queue<DatosMovimiento> cola = new LinkedList<DatosMovimiento>();

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
	public DatosUnirsePartida agregarUsuario(UsuarioNormal usuario) {
		
		DatosUnirsePartida datos = new DatosUnirsePartida();
		
		if (this.cantJugadores != this.maxJugadores) {
			this.tablero.agregarJugador(new Jugador(usuario));
			this.cantJugadores++;
		} else {
			datos.setEstadoPartida(-1);
			return datos;
		}

		if (this.cantJugadores >= this.minJugadores && this.estado == 0) {
			this.estado = 1;
			this.iniciar();
		} 
		
		datos.setEstadoPartida(estado);
		datos.setMatriz(tablero.getMapa());
		datos.setJugadores(tablero.getJugadores());
		datos.setNombrePartida(this.nombre);
		datos.setTipoJugador(0);
		
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
	}	
	
	public void encolarMovimiento(DatosMovimiento datos) {
		this.cola.add(datos);
	}
	
	public DatosPartidaEnJuego procesarMovimientos() {
		DatosMovimiento movimiento;
		while (!this.cola.isEmpty()) {
			movimiento = cola.poll();
			this.tablero.mover(movimiento);			
		}
		
		return new DatosPartidaEnJuego(this.tablero.getMapa(), this.tablero.getJugadores());
	}

}
