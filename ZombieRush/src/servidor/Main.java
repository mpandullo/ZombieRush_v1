package servidor;

import java.util.concurrent.Semaphore;

import interfazServer.PanelServer;

public class Main {

	public static void main(String[] args) {
		Semaphore semOutStream = new Semaphore(1);
		PanelServer panelServer = null;
		Broadcast broadcast = new Broadcast(semOutStream);
		JuegoServer juego = JuegoServer.getInstance(broadcast);
		
		panelServer = new PanelServer(juego, semOutStream);
		panelServer.setVisible(true);
		
		// Ejecutamos todas las sarasas iniciales: 
		// conexion a base de datos, sockets, etc...
		
		

	}

}
