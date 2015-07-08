package servidor;

import datosSocket.DatosLogin;

public class Consultas {
	
	public static DatosLogin login(DatosLogin datos) {
		// Consulta a la base y me devuelve el objeto lleno
		// si existe. Si no existe cardo el id con -2
		
		datos.setIdUsuario(1);
		datos.setTipoUsuario(0);
		datos.setUsuario("Dario");
		datos.setPassword("123456");
		datos.setNombre("Dario");
		datos.setCorreo("dario@gmail.com");
		datos.setPreguntaSeguridad(1);
		datos.setRespuestaSeguridad("Jose");
		datos.setPuntosAcumulados(30);
		
		return datos;
	}

}
