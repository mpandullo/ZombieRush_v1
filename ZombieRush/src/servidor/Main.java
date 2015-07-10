package servidor;

import java.util.concurrent.Semaphore;

import interfazServer.PanelServer;

public class Main {

	public static void main(String[] args) {
		Semaphore semOutStream = new Semaphore(1);
		PanelServer panelServer = null;
		Broadcast broadcast = new Broadcast(semOutStream);
		JuegoServer juego = JuegoServer.getInstance();
		juego.setBroadcast(broadcast);
		
		panelServer = new PanelServer(juego, semOutStream);
		panelServer.setVisible(true);
	}

}
