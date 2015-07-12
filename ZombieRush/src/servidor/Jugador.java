package servidor;

import java.io.Serializable;

public class Jugador implements Serializable {
	
	private int usuarioId;
	private String nombre;
	private int puntos = 0;
	private int tipo = 0;
	private boolean fueZombie = false;
	
	public Jugador(UsuarioNormal usuario) {
		this.usuarioId = usuario.getIdUsuario();
		this.nombre = usuario.getUsuario();
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
