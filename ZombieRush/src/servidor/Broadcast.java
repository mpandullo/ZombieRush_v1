package servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Broadcast {

	private Semaphore semOutStream = null;

	public Broadcast(Semaphore semOutStream) {
		this.semOutStream = semOutStream;
	}

	public void broadcastMsgNormal(Object obj, List<UsuarioNormal> listaUsuarios)
			throws IOException, InterruptedException {

		for (Usuario usuario : listaUsuarios) {

			semOutStream.acquire();
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
