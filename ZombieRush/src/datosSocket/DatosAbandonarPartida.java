package datosSocket;

import java.io.Serializable;

public class DatosAbandonarPartida implements Serializable {
	private int partidaId;
	private int usuarioId;

	public DatosAbandonarPartida() {

	}

	public int getPartidaId() {
		return partidaId;
	}

	public void setPartidaId(int partidaId) {
		this.partidaId = partidaId;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}
