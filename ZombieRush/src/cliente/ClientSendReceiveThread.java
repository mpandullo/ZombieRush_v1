package cliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import datosSocket.DatosLogin;

public class ClientSendReceiveThread extends Thread {
	Socket socketId = null;
	Object obj;

	public ClientSendReceiveThread(Socket socketId) {
		this.socketId = socketId;
	}

	public void run() {
		try {
			ObjectInputStream inStream = new ObjectInputStream(
					socketId.getInputStream());
			ObjectOutputStream outStream = new ObjectOutputStream(
					socketId.getOutputStream());

			while (true) {
				obj = inStream.readObject();
				
				switch (obj.getClass().getSimpleName()) {

				// Devolucion de login de usuario desde el server
				case "DatosLogin":
					// debe devolver DatosLogin a la clase Login
					break;
					
				default :
					
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
