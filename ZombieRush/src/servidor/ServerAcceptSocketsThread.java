package servidor;

import interfazServer.PanelServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class ServerAcceptSocketsThread extends Thread {
	ServerSocket serverSocket = null;
	JuegoServer juego = null;
	PanelServer panelServer = null;
	Semaphore semOutStream = null;

	public ServerAcceptSocketsThread(ServerSocket serverSocket,
			JuegoServer juego, PanelServer panelServer, Semaphore semOutStream) {
		this.serverSocket = serverSocket;
		this.juego = juego;
		this.panelServer = panelServer;
		this.semOutStream = semOutStream;
	}

	@Override
	public void run() {
		try {

			while (true) {
				Socket clientSocket = serverSocket.accept();
				
				this.panelServer.escribirLog("Conexion recibida desde: "
						+ clientSocket.getInetAddress() + " en el puerto: "
						+ clientSocket.getPort());
				/*
				System.out.println("Conexion recibida desde: "
						+ clientSocket.getInetAddress() + " en el puerto: "
						+ clientSocket.getPort());*/

				// creo un Thread para enviar y recibir desde el cliente
				
				ServerSendReceiveThread sendReceive = new ServerSendReceiveThread(
						clientSocket, juego, semOutStream);

				Thread threadSendReceive = new Thread(sendReceive);
				threadSendReceive.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}