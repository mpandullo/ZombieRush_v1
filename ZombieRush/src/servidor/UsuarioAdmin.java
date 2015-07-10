package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import datosSocket.DatosLogin;

public class UsuarioAdmin extends Usuario {

	public UsuarioAdmin(DatosLogin datos, Socket socket,
			ObjectOutputStream outStream, ObjectInputStream inStream) {
		super(datos, socket, outStream, inStream);
	}
}
