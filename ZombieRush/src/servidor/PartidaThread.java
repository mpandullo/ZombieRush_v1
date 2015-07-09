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

	@Override
	public void run() {
		try {

			// Tengo que ver como hago la carga inicial de datosPartidaEnJuego
			this.datosPartidaEnJuego = this.partida.procesarMovimientos();
			broadcast.broadcastMsgNormal(datosPartidaEnJuego,
					partida.getUsuarios());

			while (enJuego) {
				// Espero 5 segundos para volver a procesar
				sleep(5000);

				// Proceso todos los movimientos y hago el broadcast
				this.datosPartidaEnJuego = this.partida.procesarMovimientos();
				broadcast.broadcastMsgNormal(datosPartidaEnJuego,
						partida.getUsuarios());

			}

		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

	}
}
