package servidor;

import java.util.ArrayList;
import java.util.List;

import datosSocket.DatosMovimiento;
import datosSocket.DatosUnirsePartida;

public class JuegoServer {
		
	// Singleton
	private static JuegoServer INSTANCE = null;

	private JuegoServer() {
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

	public DatosUnirsePartida unirsePartida(DatosUnirsePartida partida) {
		int p = 0;
		int u = 0;
		
		for (int i = 0; i < partidas.size(); i++) {
			if (partidas.get(i).getPartidaId() == partida.getPartidaId()) {
				p = i;
			}
		}
		
		for (int i = 0; i <usuarios.size(); i++) {
			if (usuarios.get(i).getIdUsuario() == partida.getUsuarioId()) {
				u = i;
			}
		}
		
		return partidas.get(p).agregarUsuario(usuarios.get(u));
	}
	
	public void encolarMovimiento(DatosMovimiento dato) {
		for (int i = 0; i < this.partidas.size(); i++) {
			if (this.partidas.get(i).getPartidaId() == dato.getPartidaId())
				this.partidas.get(i).encolarMovimiento(dato);
		}
	}
}
