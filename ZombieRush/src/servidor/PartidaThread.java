package servidor;

import datosSocket.DatosPartidaEnJuego;

public class PartidaThread extends Thread {
	
	// Al lanzarse la partida debemos ejecutar el partidaThread.start()
	private JuegoServer juegoServer = null;
	private Partida partida = null;
	private DatosPartidaEnJuego datosPartidaEnJuego = null;
	private boolean enJuego = false;
	private Broadcast broadcast = null;
	
	public PartidaThread(JuegoServer juegoServer, Partida partida) {
		this.juegoServer = juegoServer;
		this.partida = partida;
	}
	
	
	@Override
	public void run() {
		try {
			
			// Tengo que ver como hago la carga inicial de datosPartidaEnJuego
			broadcast.broadcastMsg(datosPartidaEnJuego, listaUsuarios);
			
			while(enJuego){
				// Espero 5 segundos para volver a procesar
				sleep(5000);
				
				// Proceso todos los movimientos y hago el broadcast
				this.datosPartidaEnJuego = this.partida.procesarMovimientos();
				broadcast.broadcastMsg(datosPartidaEnJuego, listaUsuarios);
								
			}
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
