package cliente;

import interfaz.Login;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import datosSocket.DatosLogin;

public class ClientSendReceiveThread extends Thread {
	Socket socketId = null;
	Object obj = null;
	ObjectOutputStream outStream = null;
	ObjectInputStream inStream = null;
	Login login = null;
	Semaphore semLogin = null;

	public ClientSendReceiveThread(Socket socketId,
			ObjectOutputStream outStream, ObjectInputStream inStream,
			Login login, Semaphore semLogin) {
		this.socketId = socketId;
		this.inStream = inStream;
		this.outStream = outStream;
		this.login = login;
		this.semLogin = semLogin;
	}

	public void run() {
		try {

			while (true) {
				
				//Adquiero semaforo para bloquear el metodo login de la class Login
				semLogin.acquire();
				
				//Leo objeto del socket
				obj = inStream.readObject();
				
				switch (obj.getClass().getSimpleName()) {

				// Devolucion de login de usuario desde el server
				case "DatosLogin":
					// debe devolver DatosLogin a la clase Login
					login.setDatosLogin((DatosLogin) obj);
					semLogin.release();
					break;

				default:

					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
