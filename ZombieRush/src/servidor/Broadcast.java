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

		semOutStream.acquire();
		for (Usuario usuario : listaUsuarios) {
			usuario.getOutStream().reset();
			usuario.getOutStream().writeUnshared(obj);
			usuario.getOutStream().flush();
		}
		semOutStream.release();
	}

	public void broadcastMsgAdmin(Object obj, List<UsuarioAdmin> listaUsuarios)
			throws IOException, InterruptedException {

		semOutStream.acquire();
		for (Usuario usuario : listaUsuarios) {			
			usuario.getOutStream().reset();
			usuario.getOutStream().writeUnshared(obj);
			usuario.getOutStream().flush();
		}
		semOutStream.release();
	}
}

