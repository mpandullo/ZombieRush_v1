package servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Broadcast {

	public void broadcastMsg(Object obj, List<Usuario> listaUsuarios)
			throws IOException {

		for (Usuario usuario : listaUsuarios) {
			ObjectOutputStream outStream = new ObjectOutputStream(
					usuario.getSocket.getOutputStream());
			outStream.writeObject(obj);
			outStream.flush();
		}

	}
}
