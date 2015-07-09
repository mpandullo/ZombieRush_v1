package cliente;

import java.util.concurrent.Semaphore;

import interfaz.Login;

public class Main {

	public static void main(String[] args) {
		Semaphore semLogin = new Semaphore(1);
		
		Login login = new Login(semLogin);
		SocketsCliente socketsClient = new SocketsCliente(login, semLogin);
		socketsClient.lanzarConexion();
		
		login.setVisible(true);		
		

	}

}
