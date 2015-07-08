package cliente;

import datosSocket.DatosLogin;

public class UsuarioNormal extends Usuario {
	
	private int puntosAcumulados;
	
	// Constructor
	public UsuarioNormal(DatosLogin datos) {
		super(datos);
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
