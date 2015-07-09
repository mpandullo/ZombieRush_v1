package cliente;

import datosSocket.DatosPartidas;

public class Datos {
	private static DatosPartidas datosPartidas;

	public static DatosPartidas getDatosPartidas() {
		return datosPartidas;
	}

	public static void setDatosPartidas(DatosPartidas datosPartidas) {
		Datos.datosPartidas = datosPartidas;
	}
	
	
}
