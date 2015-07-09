package servidor;

import java.net.Socket;

public class Jugador {
	
	private int usuarioId;
	private String nombre;
	private int puntos;
	private int tipo;
	private boolean fueZombie = false;
	
	private Socket socket;
	
	public Jugador(UsuarioNormal usuario) {
		this.usuarioId = usuario.getIdUsuario();
		this.nombre = usuario.getUsuario();
		this.socket = usuario.getSocket();
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
	
	public Socket getSocket() {
		return this.socket;
	}

}
