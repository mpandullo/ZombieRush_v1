package cliente;

import datosSocket.DatosPartidas;
import interfaz.Login;
import interfaz.PanelAdmin;

public class JuegoAdmin {
	
	private UsuarioAdmin usuario;
	private PanelAdmin panel;
	private int[] partidasId;
	
	public JuegoAdmin(Login login, UsuarioAdmin usuario) {
		this.usuario = usuario;
		this.panel = new PanelAdmin(login, this, usuario);
	}

	public String[][] obtenerPartidas() {
		DatosPartidas datos = Datos.getDatosPartidas();
		String aux[][] = datos.getPartidas();
		String[][] lista = new String[aux.length][7];
		this.partidasId = new int[aux.length];
		
		for (int i = 0; i < aux.length; i++) {
			lista[i][0] = aux[i][1];
			lista[i][1] = aux[i][2];
			lista[i][2] = aux[i][3];
			lista[i][3] = aux[i][4];
			lista[i][4] = aux[i][5];
			lista[i][5] = aux[i][6];
			lista[i][6] = aux[i][7];
			this.partidasId[i] = Integer.parseInt(aux[i][0]);
		}
		
		return lista;
	}
	
	public int agregarPartida(String nombre, String min, String max) {
		nombre = nombre.trim();
		min = min.trim();
		max = max.trim();
		
		int minimo;
		int maximo;
		
		if (nombre.isEmpty() || min.isEmpty() || max.isEmpty()) 
			return -2;
		
		try {
			minimo = Integer.parseInt(min);
			maximo = Integer.parseInt(max);
		} catch (Exception e) {
			return -1;
		}
		
		//Envio la info por socket
		
		return 1;		
	}
}
