package cliente;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import interfaz.Login;
import interfaz.PanelCliente;
import interfaz.VentanaJuego;
import datosSocket.DatosPartidas;
import datosSocket.DatosUnirsePartida;

public class JuegoCliente {

	private UsuarioNormal usuario;
	private PanelCliente panel;
	private int[] partidasId;
	private int partidaIniciada;
	private SocketsCliente clientSocket = null;
	private Semaphore semUP = null;

	private DatosUnirsePartida datosUP;

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
		DatosPartidas datos = SocketCliente.obternerPartidas();
		String aux[][] = datos.getPartidas();
		String[][] lista = new String[aux.length][3];
		this.partidasId = new int[aux.length];

		for (int i = 0; i < aux.length; i++) {
			this.partidasId[i] = Integer.parseInt(aux[i][0]);
			lista[i][0] = aux[i][1];
			lista[i][1] = aux[i][5] + "/" + aux[i][4];
			lista[i][2] = aux[i][2];
		}

		return lista;
	}

	public void unirsePartida(int id, PanelCliente panel) throws IOException, InterruptedException {

		// Enviamos los datos al server
		this.clientSocket.enviarObjeto(datosUP);

		// Ponemos un semaforo para esperar la respuesta
		this.semUP.acquire();
		
		if (this.datosUP.getEstadoPartida() == -1)
			panel.mensajeErrorUnirse();
		else {
			this.partidaIniciada = this.partidasId[id];
			if (this.datosUP.getEstadoPartida() == 0)
				panel.enEspera();
			else {
				VentanaJuego ventana = new VentanaJuego(panel, this);
				ventana.setVisible(true);
			}
		}
		this.semUP.release();
	}

	public void abandonarPartida() {
		SocketCliente.abandonar(partidaIniciada);
	}
}
