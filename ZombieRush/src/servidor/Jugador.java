package servidor;

import datosSocket.DatosPartida;

public class Jugador {
	
	private int usuarioId;
	private String nombre;
	private int puntos;
	private int tipo;
	private boolean fueZombie = false;
	
	public Jugador(DatosPartida datos) {
		this.usuarioId = datos.getUsuarioId();
		this.nombre = datos.getNombre();
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public boolean getFueZombie() {
		return fueZombie;
	}

	public void setFueZombie(boolean fueZombie) {
		this.fueZombie = fueZombie;
	}

}
