package servidor;

import java.io.IOException;

import datosSocket.DatosPartidaEnJuego;

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
			

			while (enJuego) {
				// Proceso todos los movimientos y hago el broadcast
				this.datosPartidaEnJuego = this.partida.procesarMovimientos();
				broadcast.broadcastMsgNormal(datosPartidaEnJuego,
						partida.getUsuarios());
				
				// Espero 5 segundos para volver a procesar
				sleep(5000);
			}

		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

	}
}
