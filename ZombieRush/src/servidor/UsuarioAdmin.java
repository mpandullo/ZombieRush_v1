package servidor;

import java.net.Socket;

import datosSocket.DatosLogin;

public class UsuarioAdmin extends Usuario {

		public UsuarioAdmin(DatosLogin datos, Socket socket) {
			super(datos, socket);
		}
}
