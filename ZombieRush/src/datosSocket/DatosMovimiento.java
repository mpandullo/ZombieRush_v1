package datosSocket;

public class DatosMovimiento {

	private int partidaId;
	private int usuarioId;
	private char movimiento;

	public DatosMovimiento(int partidaId, int usuarioId, char mov) {
		this.partidaId = partidaId;
		this.usuarioId = usuarioId;
		this.movimiento = mov;
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

	public char getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(char movimiento) {
		this.movimiento = movimiento;
	}
}
