package servidor;

import java.net.Socket;

import datosSocket.DatosLogin;

public class UsuarioNormal extends Usuario {
	
	private int puntosAcumulados;
	
	// Constructor
	public UsuarioNormal(DatosLogin datos, Socket socket) {
		super(datos, socket);
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
