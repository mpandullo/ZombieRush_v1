package cliente;

import interfaz.Login;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import datosSocket.DatosLogin;
import datosSocket.DatosPartidaEnJuego;
import datosSocket.DatosPartidaTerminada;
import datosSocket.DatosPartidas;
import datosSocket.DatosRegistro;
import datosSocket.DatosUnirsePartida;

public class ClientSendReceiveThread extends Thread {
	Socket socketId = null;
	Object obj = null;
	ObjectOutputStream outStream = null;
	ObjectInputStream inStream = null;
	Login login = null;
	Semaphore semLogin = null;
	Semaphore semUP = null;
	Semaphore semReg = null;
	JuegoCliente juegoCliente = null;

	public ClientSendReceiveThread(Socket socketId,
			ObjectOutputStream outStream, ObjectInputStream inStream,
			Login login, Semaphore semLogin, Semaphore semUP, Semaphore semReg,
			JuegoCliente juegoCliente) throws IOException {
		this.socketId = socketId;
		this.inStream = inStream;
		this.outStream = outStream;
		this.login = login;
		this.semLogin = semLogin;
		this.semUP = semUP;
		this.semReg = semReg;
		this.juegoCliente = juegoCliente;
	}

	
	public void setJuego(JuegoCliente juego) {
		this.juegoCliente = juego;
	}

	public void run() {
		try {
			while (true) {
				
				// Leo objeto del socket
				System.out.println("llegue thread");
				obj = inStream.readObject();
				System.out.println("llegue lei desde cliente");
				System.out.println(obj.getClass().getSimpleName());
				switch (obj.getClass().getSimpleName()) {

				// Devolucion de login de usuario desde el server
				case "DatosLogin":	
					// debe devolver DatosLogin a la clase Login
					login.setDatosLogin((DatosLogin) obj);
					semLogin.release();
					System.out.println("llegue login");
					//sleep(5000);
					
					break;

				case "DatosRegistro":					
					// debe devolver DatosLogin a la clase Login
					login.setDatosRegistro((DatosRegistro) obj); 
					semReg.release();
					System.out.println("llegue a registro");
					//sleep(5000);
					
					break;	
				
				case "DatosUnirsePartida":
					DatosUnirsePartida datos = (DatosUnirsePartida) obj;
					juegoCliente.setDatosUP(datos);
					semUP.release();
					System.out.println("llegue unirse partida");
					break;
					
				case "DatosPartidaTerminada":
					DatosPartidaTerminada datosPT = (DatosPartidaTerminada) obj;
					juegoCliente.terminarPartida(datosPT);
					break;

				case "DatosPartidaEnJuego":
					System.out.println(this.juegoCliente.getUsuario().getNombre() + " recibiendo mapa");
					juegoCliente.actualizarTablero((DatosPartidaEnJuego) obj);
					break;

				case "DatosPartidas":
					Datos.setDatosPartidas((DatosPartidas) obj);
					System.out.println("llegue partidas");
					break;

				case "DatosCrearPartida":
					// Ver a quien se lo paso para verificar si la partida se
					// creo o no
					break;

				default:

					break;
				
				}				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
