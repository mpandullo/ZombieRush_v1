package servidor;

import datosSocket.DatosLogin;
import datosSocket.DatosPartida;

public class SocketServer {
	
	// Se deben guardar los id de los clientes a los que estoy conectado
	// para saber a quien le debo enviar el mensaje
	
	public DatosLogin login(DatosLogin datos) {
		
		return Usuario.login(datos);
	}
	
	public DatosPartida unirse(DatosPartida partida) {
		return JuegoServer.getInstance().unirsePartida(partida);
	}

}
