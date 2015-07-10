package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import datosSocket.DatosLogin;

public class Usuario {

	private int idUsuario;
	private int tipoUsuario;
	private String usuario;
	private String password;
	private String nombre;
	private String correo;
	private int preguntaSeguridad;
	private String respuestaSeguridad;

	private Socket socket;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;

	// Constructor
	public Usuario(DatosLogin datos, Socket socket,
			ObjectOutputStream outStream, ObjectInputStream inStream) {
		this.idUsuario = datos.getIdUsuario();
		this.tipoUsuario = datos.getTipoUsuario();
		this.usuario = datos.getUsuario();
		this.password = datos.getPassword();
		this.nombre = datos.getNombre();
		this.correo = datos.getCorreo();
		this.preguntaSeguridad = datos.getPreguntaSeguridad();
		this.respuestaSeguridad = datos.getRespuestaSeguridad();
		this.socket = socket;
		this.outStream = outStream;
		this.inStream = inStream;
	}

	// Getters and Setters
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(int tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public int getPreguntaSeguridad() {
		return preguntaSeguridad;
	}

	public void setPreguntaSeguridad(int preguntaSeguridad) {
		this.preguntaSeguridad = preguntaSeguridad;
	}

	public String getRespuestaSeguridad() {
		return respuestaSeguridad;
	}

	public void setRespuestaSeguridad(String respuestaSeguridad) {
		this.respuestaSeguridad = respuestaSeguridad;
	}

	public Socket getSocket() {
		return this.socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ObjectOutputStream getOutStream() {
		return outStream;
	}

	public ObjectInputStream getInStream() {
		return inStream;
	}

	// Metodos
	public static DatosLogin login(DatosLogin datos, Socket socket,
			ObjectOutputStream outStream, ObjectInputStream inStream) {
		String usuario = datos.getUsuario();
		String password = datos.getPassword();

		ConsultasUsuario.login(datos);
		if (datos.getIdUsuario() == -2)
			return datos;

		if (usuario.compareTo(datos.getUsuario()) == 0
				&& password.compareTo(datos.getPassword()) == 0) {
			if (datos.getTipoUsuario() == 1) {
				JuegoServer.getInstance().agregarUsuario(
						new UsuarioNormal(datos, socket, outStream, inStream));
			} else {
				JuegoServer.getInstance().agregarUsuario(
						new UsuarioAdmin(datos, socket, outStream, inStream));
			}
		} else {
			datos.setIdUsuario(-1);
		}
		// datos.setIdUsuario(2);
		// datos.setTipoUsuario(1);
		return datos;
	}

}
