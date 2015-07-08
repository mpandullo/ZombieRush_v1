package datosSocket;

import java.io.Serializable;

public class DatosPartidas implements Serializable {
	
	private String[][] partidas;
	
	public DatosPartidas(String[][] partidas) {
		this.partidas = partidas;
	}
	
	public String[][] getPartidas() {
		return this.partidas;
	}

}
