package cliente;

import interfaz.Login;
import interfaz.PanelCliente;
import interfaz.VentanaJuego;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import datosSocket.DatosAbandonarPartida;
import datosSocket.DatosPartidaEnJuego;
import datosSocket.DatosPartidas;
import datosSocket.DatosUnirsePartida;

public class JuegoCliente {

	private UsuarioNormal usuario;
	private PanelCliente panel;
	private int[] partidasId;
	private int partidaIniciada;
	private SocketsCliente clientSocket = null;
	private Semaphore semUP = null;

	private DatosUnirsePartida datosUP = new DatosUnirsePartida();
	
	private VentanaJuego ventana;

	public JuegoCliente(Login login, UsuarioNormal usuario) {
		this.usuario = usuario;
		this.panel = new PanelCliente(login, this, usuario);
		this.clientSocket = login.getClientSocket();
		this.semUP = login.getSemUP();
	}

	// Getters and Setter
	public UsuarioNormal getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioNormal usuario) {
		this.usuario = usuario;
	}

	public PanelCliente getPanel() {
		return panel;
	}

	public void setPanel(PanelCliente panel) {
		this.panel = panel;
	}

	public int[] getPartidasId() {
		return partidasId;
	}

	public void setPartidasId(int[] partidasId) {
		this.partidasId = partidasId;
	}

	public int getPartidaIniciada() {
		return partidaIniciada;
	}

	public void setPartidaIniciada(int partidaIniciada) {
		this.partidaIniciada = partidaIniciada;
	}

	public DatosUnirsePartida getDatosUP() {
		return datosUP;
	}

	public void setDatosUP(DatosUnirsePartida datosUP) {
		this.datosUP = datosUP;
	}

	public String[][] obtenerPartidas() {
		DatosPartidas datos = Datos.getDatosPartidas();
		String aux[][] = datos.getPartidas();
		String[][] lista = new String[aux.length][3];
		this.partidasId = new int[aux.length];

		for (int i = 0; i < aux.length; i++) {
			this.partidasId[i] = Integer.parseInt(aux[i][0]);
			System.out.println(partidasId[i]);
			lista[i][0] = aux[i][1];
			lista[i][1] = aux[i][5] + "/" + aux[i][4];
			lista[i][2] = aux[i][2];
		}

		return lista;
	}

	public void unirsePartida(int id, PanelCliente panel) throws IOException, InterruptedException {
		
		this.panel = panel;
		
		this.datosUP.setPartidaId(id);
		this.datosUP.setUsuarioId(usuario.getIdUsuario());
		
		// Enviamos los datos al server
		this.clientSocket.enviarObjeto(datosUP);
		System.out.println("llegue a enviar objeto datosUP");
		// Ponemos un semaforo para esperar la respuesta
		this.semUP.acquire();
		this.semUP.acquire();
		
		System.out.println("leyendo la respuesta de unirse");
		if (this.datosUP.getEstadoPartida() == -1)
			panel.mensajeErrorUnirse();
		else {
			this.partidaIniciada = this.partidasId[id];

			if (this.datosUP.getEstadoPartida() == 0)
				panel.enEspera();
			else
				panel.enEspera(); // cambiar por uniendose...
		}
		this.semUP.release();
	}
			

	public void abandonarPartida() throws IOException {
		DatosAbandonarPartida datosAP = new DatosAbandonarPartida();
		datosAP.setPartidaId(this.partidaIniciada);
		datosAP.setUsuarioId(this.usuario.getIdUsuario());
		this.clientSocket.enviarObjeto(datosAP);
	}
	
	public void actualizarTablero(DatosPartidaEnJuego datos) {
		System.out.println("llegue a actualizar tablero");
		if (this.getDatosUP().getEstadoPartida() == 0) {
			
			this.datosUP.setEstadoPartida(1);
			this.ventana = new VentanaJuego(panel, this);
			this.ventana.actualizarTablero(datos);
			this.panel.getEspera().dispose();
			this.ventana.setVisible(true);
		} else {
			this.panel.getEspera().dispose(); // Cambiar por cerrar uniendose...
			this.ventana.actualizarTablero(datos);
		}
	}
}
