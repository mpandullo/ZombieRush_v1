package servidor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Semaphore;

import datosSocket.DatosPartidaEnJuego;

public class Broadcast {

	private Semaphore semOutStream = null;

	public Broadcast(Semaphore semOutStream) {
		this.semOutStream = semOutStream;
	}

	public void broadcastMsgNormal(Object obj, List<UsuarioNormal> listaUsuarios)
			throws IOException, InterruptedException {

		for (Usuario usuario : listaUsuarios) {

			semOutStream.acquire();
			if (obj.getClass().getSimpleName().compareTo("DatosPartidaEnJuego") == 0) {
				DatosPartidaEnJuego datos = (DatosPartidaEnJuego) obj;
				for (int i = 0; i < datos.getMatriz().length; i++) {
					for (int j = 0; j < datos.getMatriz()[0].length; j++) {
						if (datos.getMatriz()[i][j] == 0)
							System.out.print(datos.getMatriz()[i][j] + " ");
						else
							System.out.print(datos.getMatriz()[i][j]);
					}
					System.out.println();
				}
			}
			usuario.getOutStream().writeObject(obj);
			usuario.getOutStream().flush();
			semOutStream.release();
		}
	}

	public void broadcastMsgAdmin(Object obj, List<UsuarioAdmin> listaUsuarios)
			throws IOException, InterruptedException {

		for (Usuario usuario : listaUsuarios) {
			semOutStream.acquire();
			usuario.getOutStream().writeObject(obj);
			usuario.getOutStream().flush();
			semOutStream.release();
		}

	}
}
