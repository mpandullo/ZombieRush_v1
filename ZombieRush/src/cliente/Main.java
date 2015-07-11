package cliente;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import interfaz.Login;

public class Main {

	public static void main(String[] args) throws IOException {
		Semaphore semLogin = new Semaphore(1);
		Semaphore semUP = new Semaphore(1);
		
		Login login = new Login(semLogin, semUP);
		
		SocketsCliente clientSocket = new SocketsCliente(login, semLogin, semUP);
		login.setClientSocket(clientSocket);		
		try {
			clientSocket.lanzarConexion();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//clientSocket.enviarObjeto(Datos.getDatosPartidas());
		login.setVisible(true);		
	}

}
