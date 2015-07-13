package datosSocket;

import java.io.Serializable;

public class DatosDesloguearse implements Serializable {

	private int usuarioId;
	private int tipo;

	public DatosDesloguearse(int usuarioId, int tipo) {
		this.usuarioId = usuarioId;
		this.tipo = tipo;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

}
