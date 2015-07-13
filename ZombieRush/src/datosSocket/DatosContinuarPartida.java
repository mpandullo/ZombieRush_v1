package datosSocket;

import java.io.Serializable;

public class DatosContinuarPartida implements Serializable {

	private int partidaId;
	
	public DatosContinuarPartida(int partida) {
		this.partidaId = partida;
	}

	public int getPartidaId() {
		return partidaId;
	}

	public void setPartidaId(int partidaId) {
		this.partidaId = partidaId;
	}

}
