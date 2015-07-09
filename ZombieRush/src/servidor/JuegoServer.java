package servidor;

import java.util.ArrayList;
import java.util.List;

import datosSocket.DatosPartida;

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

	
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private List<Partida> partidas = new ArrayList<Partida>();
	
	// Metodos
	public void agregarUsuario(Usuario usuario) {
		usuarios.add(usuario);
	}
	
	public DatosPartida unirsePartida(DatosPartida partida) {
		for (int i = 0; i < partidas.size(); i++) {
			if (partidas.get(i).getPartidaId() == partida.getPartidaId()) {
				partida = partidas.get(i).agregarJugador(partida);
			}
		}
		
		return partida;
	}
}
