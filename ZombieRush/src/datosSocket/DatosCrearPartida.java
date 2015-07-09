package datosSocket;

public class DatosCrearPartida {
	private int cantMin;
	private int cantMax;
	private String nombre;
	private int usuarioId;

	public DatosCrearPartida(int cantMin, int cantMax, String nombre, int id) {
		this.cantMin = cantMin;
		this.cantMax = cantMax;
		this.nombre = nombre;
		this.usuarioId = id;
	}

	public int getCantMin() {
		return cantMin;
	}

	public void setCantMin(int cantMin) {
		this.cantMin = cantMin;
	}

	public int getCantMax() {
		return cantMax;
	}

	public void setCantMax(int cantMax) {
		this.cantMax = cantMax;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}
