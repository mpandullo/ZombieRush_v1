package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import datosSocket.DatosLogin;
import datosSocket.DatosMovimiento;
import datosSocket.DatosPartida;

public class ServerSendReceiveThread extends Thread {
	Socket socketId = null;
	List<Socket> socketsList = new ArrayList<Socket>();
	int IdUsuario;
	int cod;
	Object obj;
	Usuario usuario;
	JuegoServer juegoServer;

	public ServerSendReceiveThread(Socket socket, List<Socket> socketsList,
			JuegoServer juego) {
		this.socketId = socket;
		this.socketsList = socketsList;
		this.juegoServer = juego;
	}

	@Override
	public void run() {
		try {
			ObjectInputStream inStream = new ObjectInputStream(
					socketId.getInputStream());
			ObjectOutputStream outStream = new ObjectOutputStream(
					socketId.getOutputStream());

			while (true) {

				obj = inStream.readObject();
				// voy a switchear en base al tipo de clase que sea el
				// objeto/mensaje recibido
				switch (obj.getClass().getSimpleName()) {

				// Mensaje de login de usuario
				case "DatosLogin":
					DatosLogin datos = Usuario.login((DatosLogin) this.obj, this.socketId);
					if (datos.getIdUsuario() > 0)
						this.IdUsuario = datos.getIdUsuario();

					outStream.writeObject(datos);
					break;

				case "DatosPartida":
					DatosPartida datosPartida = (DatosPartida) obj;
					datosPartida.setUsuarioId(IdUsuario);
					/* Crear Partida
					if (datosPartida.getAccion() == 0)
						this.juegoServer.crearPartida(datosPartida);
						*/
					
					if (datosPartida.getAccion() == 1)
						this.juegoServer.unirsePartida(datosPartida);
					
					/* Dejar Partida
					if (datosPartida.getAccion() == -1)
						this.juegoServer.dejarPartida(datosPartida);
						*/

				case "DatosMovimiento":
					DatosMovimiento datosMov = (DatosMovimiento) obj;
					this.juegoServer.encolarMovimiento(datosMov);

				default:
					break;
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}