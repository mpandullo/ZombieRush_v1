package cliente;

import java.util.concurrent.Semaphore;

import interfaz.Login;

public class Main {

	public static void main(String[] args) {
		Semaphore semLogin = new Semaphore(1);
		Semaphore semUP = new Semaphore(1);
		
		Login login = new Login(semLogin, semUP);
		SocketsCliente clientSocket = new SocketsCliente(login, semLogin, semUP);
		login.setClientSocket(clientSocket);		
		clientSocket.lanzarConexion();		
		login.setVisible(true);		
		

	}

}
