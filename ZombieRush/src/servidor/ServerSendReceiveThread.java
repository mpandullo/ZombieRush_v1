package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import datosSocket.DatosAbandonarPartida;
import datosSocket.DatosCrearPartida;
import datosSocket.DatosLogin;
import datosSocket.DatosMovimiento;
import datosSocket.DatosUnirsePartida;

public class ServerSendReceiveThread extends Thread {
	Socket socketId = null;
	int IdUsuario;
	int cod;
	Object obj;
	Usuario usuario;
	JuegoServer juegoServer;
	private boolean corriendo;
	Semaphore semOutStream = null;

	public ServerSendReceiveThread(Socket socket, JuegoServer juego,
			Semaphore semOutStream) {
		this.socketId = socket;
		this.juegoServer = juego;
		this.corriendo = true;
		this.semOutStream = semOutStream;
	}

	@Override
	public void run() {
		try {
			ObjectInputStream inStream = new ObjectInputStream(
					socketId.getInputStream());
			ObjectOutputStream outStream = new ObjectOutputStream(
					socketId.getOutputStream());

			while (corriendo) {

				obj = inStream.readObject();
				// voy a switchear en base al tipo de clase que sea el
				// objeto/mensaje recibido
				synchronized (this) {
					switch (obj.getClass().getSimpleName()) {

					// Mensaje de login de usuario
					case "DatosLogin":
						DatosLogin datos = Usuario.login((DatosLogin) this.obj,
								this.socketId);
						if (datos.getIdUsuario() > 0)
							this.IdUsuario = datos.getIdUsuario();

						this.semOutStream.acquire();
						outStream.writeObject(datos);
						outStream.flush();
						this.semOutStream.release();

						break;

					case "DatosCrearPartida":
						DatosCrearPartida datosCrearPartida = (DatosCrearPartida) obj;
						this.juegoServer.crearPartida(datosCrearPartida);
						
						this.semOutStream.acquire();
						outStream.writeObject(datosCrearPartida);
						outStream.flush();
						this.semOutStream.release();
						break;

					case "DatosUnirsePartida":
						DatosUnirsePartida datosUnirsePartida = (DatosUnirsePartida) obj;
						this.juegoServer.unirsePartida(datosUnirsePartida);
						
						this.semOutStream.acquire();
						outStream.writeObject(datosUnirsePartida);
						outStream.flush();
						this.semOutStream.release();
						break;
						

					case "DatosAbandonarPartida":
						DatosAbandonarPartida datosAbandonarPartida = (DatosAbandonarPartida) obj;
						//this.juegoServer.abandonarPartida(datosAbandonarPartida);
						break;

					case "DatosMovimiento":
						DatosMovimiento datosMov = (DatosMovimiento) obj;
						this.juegoServer.encolarMovimiento(datosMov);
						break;

					default:
						break;
					}

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
