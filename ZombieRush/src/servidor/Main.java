package servidor;

import interfazServer.PanelServer;

public class Main {

	public static void main(String[] args) {
		PanelServer panelServer = null;
		JuegoServer juego = JuegoServer.getInstance();
		
		panelServer = new PanelServer(juego);
		panelServer.setVisible(true);
		
		// Ejecutamos todas las sarasas iniciales: 
		// conexion a base de datos, sockets, etc...
		
		

	}

}
