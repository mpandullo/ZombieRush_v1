package servidor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import datosSocket.DatosAbandonarPartida;
import datosSocket.DatosCrearPartida;
import datosSocket.DatosMovimiento;
import datosSocket.DatosPartidaEnJuego;
import datosSocket.DatosPartidaTerminada;
import datosSocket.DatosUnirsePartida;

public class Partida {

	private int partidaId = 0;
	private String nombre;
	private int minJugadores = 0;
	private int maxJugadores = 0;
	private int cantJugadores = 0;
	private int estado = 0; // 0 en espera - 1 activo
	private int cantZombies = 1;
	
	private List<UsuarioNormal> usuarios = new ArrayList<UsuarioNormal>();
	private Tablero tablero = new Tablero(this);
	
	private Queue<DatosMovimiento> cola = new LinkedList<DatosMovimiento>();
	
	private PartidaThread partidaThread;
	private Thread partidaRun ;
	private Broadcast broadcast;
	
	public Partida() {
		this.partidaThread = new PartidaThread(this);
		this.partidaRun = new Thread(partidaThread);
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
		return this.usuarios;
	}

	public void setUsuarios(List<UsuarioNormal> usuarios) {
		this.usuarios = usuarios;
	}

	public int getCantZombies() {
		return cantZombies;
	}

	public void setCantZombies(int cantZombies) {
		this.cantZombies = cantZombies;
	}

	public Queue<DatosMovimiento> getCola() {
		return cola;
	}

	// Metodos
	public DatosCrearPartida crearPartida(DatosCrearPartida datos, Broadcast broadcast) {
		ConsultasUsuario.crearPartida(datos);
		int id = ConsultasUsuario.obtenerIdPartida(datos.getNombre());
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
	
	public DatosUnirsePartida agregarUsuario(UsuarioNormal usuario) {
		
		DatosUnirsePartida datos = new DatosUnirsePartida();
		System.out.println("cantidad Jugadores: " + this.cantJugadores + " - maximo: " + this.maxJugadores);
		
		if (this.cantJugadores != this.maxJugadores) {
			this.tablero.agregarJugador(new Jugador(usuario));
			this.cantJugadores++;
			ConsultasUsuario.agregarUsuario(this.partidaId, this.cantJugadores);
		} else {
			datos.setEstadoPartida(-1);
			return datos;
		}
		
		this.usuarios.add(usuario);

		System.out.println("aca llega " + this.usuarios.get(0).getUsuario());
		if (this.cantJugadores >= this.minJugadores && this.estado == 0) {
			this.estado = 1;
			this.iniciarPartida();
		} 		
		
		datos.setEstadoPartida(estado);
		datos.setNombrePartida(this.nombre);
		datos.setTipoJugador(0);
		
		return datos;
	}
	
	public void eliminarUsuario(DatosAbandonarPartida datos) {
		for (int i = 0; i < this.usuarios.size(); i++) {
			if (datos.getUsuarioId() == this.usuarios.get(i).getIdUsuario()) {
				this.usuarios.remove(i);
				this.tablero.eliminarJugador(datos.getUsuarioId());
				this.cantJugadores--;
			}
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
		
		DatosPartidaEnJuego datosJuego = new DatosPartidaEnJuego(this.tablero.getMapa(), this.tablero.getJugadores());
		
		return datosJuego;
	}
	
	public void iniciarPartida() {
		List<Jugador> jugadores = this.tablero.getJugadores();
		boolean flag = true;
		
		for (int i = 0; i < jugadores.size() && flag; i++) {
			if (!jugadores.get(i).getFueZombie()) {
				flag = false;
				jugadores.get(i).setFueZombie(true);
				jugadores.get(i).setTipo(1);
			}
		}
		
		if (flag) {
			for (int i = 0; i < jugadores.size(); i++) {
				jugadores.get(i).setFueZombie(false);
			}
			jugadores.get(0).setFueZombie(true);
			jugadores.get(0).setTipo(1);
		}
		
		this.partidaThread.setEnJuego(true);
		this.partidaRun.start();
	}
	
	public void incremetarZombie() {
		cantZombies++;
	}
	
	public void continuarPartida() {
		
		this.cantJugadores++;
		
		if (this.cantJugadores >= this.minJugadores) {
			System.out.println("continuar partida");
			this.tablero.reiniciar();
			
			// iniciar
			List<Jugador> jugadores = this.tablero.getJugadores();
			boolean flag = true;
			
			for (int i = 0; i < jugadores.size() && flag; i++) {
				if (!jugadores.get(i).getFueZombie()) {
					flag = false;
					jugadores.get(i).setFueZombie(true);
					jugadores.get(i).setTipo(1);
				}
			}
			
			if (flag) {
				for (int i = 0; i < jugadores.size(); i++) {
					jugadores.get(i).setFueZombie(false);
				}
				jugadores.get(0).setFueZombie(true);
				jugadores.get(0).setTipo(1);
			}
			
			this.partidaThread.setEnJuego(true);
			this.partidaThread.run();
		} 		
	}

}
