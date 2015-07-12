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

	private int partidaId = 0;
	private String nombre;
	private int minJugadores = 0;
	private int maxJugadores = 0;
	private int cantJugadores = 0;
	private int estado = 0; // 0 en espera - 1 activo
	private static int cantZombies = 1;
	
	private List<UsuarioNormal> usuarios = new ArrayList<UsuarioNormal>();
	private Tablero tablero = new Tablero();
	
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

		if (this.cantJugadores >= this.minJugadores && this.estado == 0) {
			this.estado = 1;
		//	datos.setIniciar(1);
			this.iniciarPartida();
		} 
		
		datos.setEstadoPartida(estado);
		//datos.setMatriz(tablero.getMapa());
		//datos.setJugadores(tablero.getJugadores());
		datos.setNombrePartida(this.nombre);
		datos.setTipoJugador(0);
		
		return datos;
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
		/*for (int i = 0; i <datosJuego.getMatriz().length; i++) {
			for (int j = 0; j < datosJuego.getMatriz()[0].length; j++) {
				System.out.print(datosJuego.getMatriz()[i][j] + " ");
			}
			System.out.println();		
		}*/
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
		
	System.out.println("iniciando partida");
		this.partidaThread.setEnJuego(true);
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
