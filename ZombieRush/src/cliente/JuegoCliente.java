package cliente;

import interfaz.Login;
import interfaz.PanelCliente;
import datosSocket.DatosPartida;
import datosSocket.DatosPartidas;

public class JuegoCliente {
	
	private UsuarioNormal usuario;
	private PanelCliente panel;
	private int[] partidasId;
	private int partidaIniciada;
	
	public JuegoCliente(Login login, UsuarioNormal usuario) {
		this.usuario = usuario;
		this.panel = new PanelCliente(login, this, usuario);
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
	
	public DatosPartida unirsePartida(int id) {
		DatosPartida partida = SocketCliente.unirse(this.partidasId[id]);
		this.partidaIniciada = this.partidasId[id];
		
		return partida;
	}
	
	public void abandonarPartida() {
		SocketCliente.abandonar(partidaIniciada);
	}
}
