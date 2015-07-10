package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import datosSocket.DatosLogin;

public class UsuarioNormal extends Usuario {

	private int puntosAcumulados;

	// Constructor
	public UsuarioNormal(DatosLogin datos, Socket socket,
			ObjectOutputStream outStream, ObjectInputStream inStream) {
		super(datos, socket, outStream, inStream);
		this.puntosAcumulados = datos.getPuntosAcumulados();
	}

	// Getters and Setters
	public int getPuntosAcumulados() {
		return puntosAcumulados;
	}

	public void setPuntosAcumulados(int puntosAcumulados) {
		this.puntosAcumulados = puntosAcumulados;
	}
}
