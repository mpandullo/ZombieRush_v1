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
			sleep(5000);
			while (enJuego) {				
				// Proceso todos los movimientos y hago el broadcast
				System.out.println(this.partida.getUsuarios().get(0)
						.getNombre());
				this.datosPartidaEnJuego = this.partida.procesarMovimientos();
				/*for (int i = 0; i <datosPartidaEnJuego.getMatriz().length; i++) {
					for (int j = 0; j < datosPartidaEnJuego.getMatriz()[0].length; j++) {
						System.out.print(datosPartidaEnJuego.getMatriz()[i][j] + " ");
					}
					System.out.println();		
				}*/
				// broadcast.broadcastMsgNormal(this.datosPartidaEnJuego,partida.getUsuarios());
				this.partida.getBroadcast().broadcastMsgNormal(
						this.datosPartidaEnJuego, this.partida.getUsuarios());
				// Espero 5 segundos para volver a procesar
				sleep(5000);
			}

		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

	}
}