package datosSocket;

import java.io.Serializable;

public class DatosPartidaTerminada implements Serializable {
	
	private int ganador = 0;

	public int getGanador() {
		return ganador;
	}

	public void setGanador(int ganador) {
		this.ganador = ganador;
	}
}
