package servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerAcceptSocketsThread extends Thread {
	ServerSocket serverSocket = null;
	List<Socket> socketsList = new ArrayList<Socket>();
	JuegoServer juego;

	public ServerAcceptSocketsThread(ServerSocket serverSocket,
			List<Socket> socketList, JuegoServer juego) {
		this.serverSocket = serverSocket;
		this.socketsList = socketList;
		this.juego = juego;
	}

	@Override
	public void run() {
		try {
			
			while (true) {
				Socket clientSocket = serverSocket.accept();
				socketsList.add(clientSocket);

				System.out.println("Conexion recibida desde: "
						+ clientSocket.getInetAddress() + " en el puerto: "
						+ clientSocket.getPort());

				// creo un Thread para enviar y recibir desde el cliente
				ServerSendReceiveThread sendReceive = new ServerSendReceiveThread(
						clientSocket, socketsList, juego);

				Thread threadSendReceive = new Thread(sendReceive);
				threadSendReceive.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}