package datosSocket;

import java.io.Serializable;

public class DatosPartida implements Serializable {

	private int partidaId;
	private int usuarioId;
	private int estado;
	private String nombre;
	private int puntos;
	private int accion;
	
	public DatosPartida(int partidaId, int estado, String nombre, int puntos, int accion) {
		this.partidaId = partidaId;
		this.estado = estado;
		this.nombre = nombre;
		this.puntos = puntos;
		this.accion = accion;
	}
	
	public int getPartidaId() {
		return partidaId;
	}

	public void setPartidaId(int id) {
		this.partidaId = id;
	}
	
	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int id) {
		this.usuarioId = id;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
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

	public int getAccion() {
		return accion;
	}

	public void setAccion(int accion) {
		this.accion = accion;
	}

}
