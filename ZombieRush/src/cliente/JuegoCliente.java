package cliente;

import interfaz.Login;
import interfaz.PanelCliente;
import interfaz.VentanaJuego;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import datosSocket.DatosAbandonarPartida;
import datosSocket.DatosMovimiento;
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
	private boolean iniciada = false;

	private DatosUnirsePartida datosUP;
	
	private VentanaJuego ventana;

	public JuegoCliente(Login login, UsuarioNormal usuario) {
		this.usuario = usuario;
		this.panel = new PanelCliente(login, this, usuario);
		this.clientSocket = login.getClientSocket();
		this.semUP = login.getSemUP();
		this.datosUP = new DatosUnirsePartida();
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
		
		this.datosUP.setPartidaId(this.partidasId[id]);
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

			if (this.datosUP.getEstadoPartida() != -1)
				panel.enEspera();
		}
		this.semUP.release();
	}

	public void abandonarPartida() throws IOException {
		DatosAbandonarPartida datosAP = new DatosAbandonarPartida();
		datosAP.setPartidaId(this.partidaIniciada);
		datosAP.setUsuarioId(this.usuario.getIdUsuario());
		this.clientSocket.enviarObjeto(datosAP);
		this.panel.setEnabled(true);
	}
	
	public void actualizarTablero(DatosPartidaEnJuego datos) {
		System.out.println("llegue a actualizar tablero");
		for (int i = 0; i < datos.getJugadores().size(); i++) {
			System.out.println(datos.getJugadores().get(i).getNombre() + " " + datos.getJugadores().get(i).getTipo());
		}
		if (!this.iniciada) {			
			this.datosUP.setEstadoPartida(1);
			this.ventana = new VentanaJuego(panel, this);
			this.ventana.actualizarTablero(datos);
			this.panel.getEspera().dispose();
			this.panel.setEnabled(false);
			this.ventana.setVisible(true);
			this.iniciada = true;
		} else {			
			/*for (int i = 0; i < datos.getMatriz().length; i++) {
				for (int j = 0; j < datos.getMatriz()[0].length; j++) {
					System.out.print(datos.getMatriz()[i][j] + " ");
				}
				System.out.println();
			}*/
			this.ventana.actualizarTablero(datos);
		}
	}
	
	public void mover(char m) {
		DatosMovimiento datos = new DatosMovimiento(this.partidaIniciada, this.usuario.getIdUsuario(), m);
		try {
			this.clientSocket.enviarObjeto(datos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}