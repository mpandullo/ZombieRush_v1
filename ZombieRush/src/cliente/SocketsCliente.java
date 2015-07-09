package cliente;

import interfaz.Login;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;

public class SocketsCliente {
	Socket socketId = null;
	ObjectOutputStream outStream = null;
	ObjectInputStream inStream = null;
	Login login = null;
	Semaphore semLogin = null;

	public SocketsCliente(Login login, Semaphore semLogin) {
		try {
			this.socketId = new Socket("localhost", 9999);
			this.outStream = new ObjectOutputStream(socketId.getOutputStream());
			this.inStream = new ObjectInputStream(socketId.getInputStream());
			this.login = login;
			this.semLogin = semLogin;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean lanzarConexion() {
		if (this.socketId.isConnected()) {
			ClientSendReceiveThread sendReceive = new ClientSendReceiveThread(
					socketId, outStream, inStream, login, semLogin);
			Thread sendReceiveThread = new Thread(sendReceive);
			sendReceiveThread.start();
			return true;
		} else
			return false;
	}

	// Metodo para enviar objetos de mensaje al socket
	public void enviarObjeto(Object obj) throws IOException {
		outStream.writeObject(obj);
	}

}
