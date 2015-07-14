package servidor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import datosSocket.DatosAbandonarPartida;
import datosSocket.DatosContinuarPartida;
import datosSocket.DatosCrearPartida;
import datosSocket.DatosDesloguearse;
import datosSocket.DatosMovimiento;
import datosSocket.DatosUnirsePartida;

public class JuegoServer {
		
	// Singleton
	private static JuegoServer INSTANCE = null;

	private JuegoServer() {
		ConsultasUsuario.vaciarPartidas();
	}

	private static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new JuegoServer();
		}
	}

	public static JuegoServer getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	} // Fin Singleton

	private Broadcast broadcast;
	
	private List<UsuarioNormal> usuarios = new ArrayList<UsuarioNormal>();
	private List<UsuarioAdmin> usuariosAdmin = new ArrayList<UsuarioAdmin>();
	private List<Partida> partidas = new ArrayList<Partida>();

	// Setters And Getters
	public List<UsuarioNormal> getUsuarios() {
		return usuarios;
	}
	
	public List<UsuarioAdmin> getUsuariosAdmin() {
		return usuariosAdmin;
	}
	
	public void setBroadcast(Broadcast broadcast) {
		this.broadcast = broadcast;
	}
	
	public Broadcast getBroadcast() {
		return this.broadcast;
	}

	// Metodos
	public void agregarUsuario(UsuarioNormal usuario) {
		usuarios.add(usuario);
	}
	
	public void agregarUsuario(UsuarioAdmin usuario) {
		usuariosAdmin.add(usuario);
	}
	
	public DatosCrearPartida crearPartida(DatosCrearPartida datos) throws IOException, InterruptedException {
		Partida partida = new Partida();
		datos = partida.crearPartida(datos, broadcast);
		//if (datos.getUsuarioId() > 0) {
			this.partidas.add(partida);
		//} 
		broadcast.broadcastMsgNormal(ConsultasUsuario.cargarTablaPrincipal(), usuarios);
		broadcast.broadcastMsgAdmin(ConsultasUsuario.cargarTablaPrincipal(), usuariosAdmin);
		System.out.println("llegue a crear partida");
		return datos;
	}

	public DatosUnirsePartida unirsePartida(DatosUnirsePartida partida) throws IOException, InterruptedException {
		int p = 0;
		int u = 0;		
		
		for (int i = 0; i < partidas.size(); i++) {
			if (partidas.get(i).getPartidaId() == partida.getPartidaId()) {
				p = i;
			}
		}
		
		for (int i = 0; i < usuarios.size(); i++) {
			if (usuarios.get(i).getIdUsuario() == partida.getUsuarioId()) {
				u = i;
			}
		}	
		
		System.out.println("usuario " + usuarios.get(u).getUsuario() + " uniendose a " + partidas.get(p).getNombre());
		DatosUnirsePartida d = partidas.get(p).agregarUsuario(usuarios.get(u));
		return d;
	}
	
	public void abandonarPartida(DatosAbandonarPartida datosAP) {
		for (int i = 0; i < this.partidas.size(); i++) {
			if (this.partidas.get(i).getPartidaId() == datosAP.getPartidaId()) {
				this.partidas.get(i).eliminarUsuario(datosAP);
			}
		}
	}
	
	public void encolarMovimiento(DatosMovimiento dato) {
		for (int i = 0; i < this.partidas.size(); i++) {
			if (this.partidas.get(i).getPartidaId() == dato.getPartidaId())
				this.partidas.get(i).encolarMovimiento(dato);
		}
	}
	
	public void continuarPartida(DatosContinuarPartida datos) {
		for (int i = 0; i < this.partidas.size(); i++) {
			if (this.partidas.get(i).getPartidaId() == datos.getPartidaId()) {
				this.partidas.get(i).continuarPartida();
			}
		}
	}
	
	public void eliminarUsuario(DatosDesloguearse datos) throws IOException {
		if (datos.getTipo() == 0) {
			for (int i = 0; i < this.usuariosAdmin.size(); i++) {
				if (this.usuariosAdmin.get(i).getIdUsuario() == datos.getUsuarioId()) {
					this.usuariosAdmin.get(i).getSocket().close();
					this.usuariosAdmin.remove(i);
				}
			}
		} else {
			for (int i = 0; i < this.usuarios.size(); i++) {
				if (this.usuarios.get(i).getIdUsuario() == datos.getUsuarioId()) {
					this.usuarios.get(i).getSocket().close();
					this.usuarios.remove(i);
				}
			}
		}
	}
}
