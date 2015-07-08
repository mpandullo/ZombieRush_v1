package cliente;

import datosSocket.DatosLogin;
import datosSocket.DatosPartida;
import datosSocket.DatosPartidas;

public class SocketCliente {
	
	public static DatosLogin login(DatosLogin datos) {
		// Le mando el objeto datos login al servidor
		// El servidor me devuelve un objeto del mismo tipo con los
		// datos correspondientes
		
		if( datos.getUsuario().compareToIgnoreCase("Dario") == 0 && datos.getPassword().compareTo("123456") == 0) {
			datos.setIdUsuario(1);
			datos.setTipoUsuario(1);
			datos.setUsuario("Dario");
			datos.setPassword("123456");
			datos.setNombre("Dario");
			datos.setCorreo("dario@gmail.com");
			datos.setPreguntaSeguridad(0);
			datos.setRespuestaSeguridad("Jose");
			datos.setPuntosAcumulados(30);
		} else {
			datos.setIdUsuario(-1);
		}
	
		return datos;
	}
	
	public static DatosPartidas obternerPartidas() {
		
		String[][] partida = {{"1", "Apocalipsis Now", "En espera", "3", "10", "0", "0", "00:00"}};
		DatosPartidas datos = new DatosPartidas(partida);
		
		return datos;
	}
	
	public static DatosPartida unirse(int id) {
		
		DatosPartida partida = new DatosPartida(id, 0, "Apocalipsis Now", 50);
		
		return partida;
	}
	
	public static void abandonar(int id) {
		// Abandonar partida
	}
}
