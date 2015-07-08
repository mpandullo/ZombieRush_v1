package cliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketsCliente {
	Socket socketId;
	ObjectOutputStream outStream;
	
	public SocketsCliente(){
		try {
			this.socketId = new Socket("localhost", 9999);
			this.outStream = new ObjectOutputStream(socketId.getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public boolean lanzarConexion(){
		if(this.socketId.isConnected()){
			ClientSendReceiveThread sendReceive = new ClientSendReceiveThread(socketId);
			Thread sendReceiveThread = new Thread(sendReceive);
			sendReceiveThread.start();
			return true;
		}
		else
			return false;
	}
	
	// Metodo para enviar objetos de mensaje al socket
	public void enviarObjeto(Object obj) throws IOException{
		outStream.writeObject(obj);
	}
	
}
