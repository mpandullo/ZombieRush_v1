package cliente;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import interfaz.Login;

public class Main {

	public static void main(String[] args) throws IOException {
		Semaphore semLogin = new Semaphore(1);
		Semaphore semUP = new Semaphore(1);
		Semaphore semReg = new Semaphore(1);
		
		Login login = new Login(semLogin, semUP, semReg);
		
		SocketsCliente clientSocket = new SocketsCliente(login, semLogin, semUP, semReg);
		login.setClientSocket(clientSocket);		
		try {
			clientSocket.lanzarConexion();
		} catch (IOException e) {
			e.printStackTrace();
		}
		login.setVisible(true);		
	}

}
