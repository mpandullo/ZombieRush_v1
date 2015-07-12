package datosSocket;

import java.io.Serializable;

public class DatosRegistro implements Serializable {

	private String usuario;
	private String nombre;
	private String correo;
	private String password;
	private int pregunta;
	private String respuesta;

	public DatosRegistro(String usuario, String nombre, String correo,
			String password, int pregunta, String respuesta) {
		this.usuario = usuario;
		this.nombre = nombre;
		this.correo = correo;
		this.password = password;
		this.pregunta = pregunta;
		this.respuesta = respuesta;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPregunta() {
		return pregunta;
	}

	public void setPregunta(int pregunta) {
		this.pregunta = pregunta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

}
