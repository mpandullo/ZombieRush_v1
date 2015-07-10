package servidor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import datosSocket.DatosCrearPartida;
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
	private static int cantZombies = 1;
	
	private List<UsuarioNormal> usuarios = new ArrayList<UsuarioNormal>();
	private Tablero tablero = new Tablero();
	
	private Queue<DatosMovimiento> cola = new LinkedList<DatosMovimiento>();
	
	private PartidaThread partidaThread = new PartidaThread(this);
	private Thread partidaRun = new Thread(partidaThread);
	private Broadcast broadcast;
	
	public DatosCrearPartida crearPartida(DatosCrearPartida datos, Broadcast broadcast) {
		ConsultasUsuario.crearPartida(datos);
		int id = ConsultasUsuario.obtenerIdPartida(datos.getNombre());
		System.out.println("Id de la partida:" + id);
		if (id > 0) {
			this.partidaId = id;
			this.nombre = datos.getNombre();
			this.minJugadores = datos.getCantMin();
			this.maxJugadores = datos.getCantMax();
			this.cantJugadores = 0;
			this.broadcast = broadcast;
			datos.setUsuarioId(1);
			
			return datos;
		} else {
			datos.setUsuarioId(-1);
			return datos;
		}
	}

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
	
	public Broadcast getBroadcast() {
		return this.broadcast;
	}

	public List<UsuarioNormal> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioNormal> usuarios) {
		this.usuarios = usuarios;
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
		
		if (flag) {
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
	
	public void iniciarPartida() {
		List<Jugador> jugadores = this.tablero.getJugadores();
		boolean flag = true;
		
		for (int i = 0; i < jugadores.size(); i++) {
			if (!jugadores.get(i).getFueZombie()) {
				flag = false;
				jugadores.get(i).setFueZombie(true);
			}
		}
		
		if (flag) {
			for (int i = 0; i < jugadores.size(); i++) {
				jugadores.get(i).setFueZombie(false);
			}
			jugadores.get(0).setFueZombie(true);
		}
		
		this.partidaRun.start();
	}
	
	public static void incremetarZombie() {
		cantZombies++;
	}
	
	private void pararPartida() {
		if (this.cantZombies == this.cantJugadores-1) {
			this.partidaRun.stop();
		}
		
		if (this.estado == 1) {
			
		}
	}

}
