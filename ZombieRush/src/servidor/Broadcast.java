package servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Broadcast {
	
	private Semaphore semOutStream = null;
	
	public Broadcast(Semaphore semOutStream){
		this.semOutStream = semOutStream;
	}

	public void broadcastMsg(Object obj, List<Usuario> listaUsuarios)
			throws IOException {

		for (Usuario usuario : listaUsuarios) {
			ObjectOutputStream outStream = new ObjectOutputStream(usuario.getSocket.getOutputStream());
			
			semOutStream.acquire();
			outStream.writeObject(obj);
			outStream.flush();
			semOutStream.release();
		}

	}
}
