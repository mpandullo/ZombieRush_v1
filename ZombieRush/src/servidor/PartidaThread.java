package servidor;

import java.io.IOException;

import datosSocket.DatosPartidaEnJuego;
import datosSocket.DatosPartidaTerminada;

public class PartidaThread extends Thread {

	// Al lanzarse la partida debemos ejecutar el partidaThread.start()
	private Partida partida = null;
	private DatosPartidaEnJuego datosPartidaEnJuego = null;
	private boolean enJuego = false;
	private Broadcast broadcast = null;

	public PartidaThread(Partida partida) {
		this.partida = partida;
		this.broadcast = partida.getBroadcast();
	}

	public void setEnJuego(boolean enJuego) {
		this.enJuego = enJuego;
	}

	@Override
	public void run() {
		try {
			sleep(2000);
			while (enJuego) {		
				
				// Proceso todos los movimientos y hago el broadcast
				this.datosPartidaEnJuego = this.partida.procesarMovimientos();
				
				if (this.partida.getCantJugadores()-1 == this.partida.getCantZombies()) {
					this.partida.getBroadcast().broadcastMsgNormal(new DatosPartidaTerminada(), this.partida.getUsuarios());
					this.partida.setCantJugadores(0);
					this.partida.setCantZombies(1);
					this.partida.getCola().clear();
					this.enJuego = false;	
				}
				
				if (this.enJuego) {
					this.partida.getBroadcast().broadcastMsgNormal(this.datosPartidaEnJuego, this.partida.getUsuarios());
					// Espero 5 segundos para volver a procesar
					sleep(2000);
				}
			}

		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

	}
}