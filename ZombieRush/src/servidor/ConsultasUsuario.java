package servidor;

import java.sql.ResultSet;
import java.sql.SQLException;

import datosSocket.DatosCrearPartida;
import datosSocket.DatosLogin;
import datosSocket.DatosPartidas;
import datosSocket.DatosRegistro;

public class ConsultasUsuario {
	
	//LOGIN INICIAL
	public static DatosLogin login(DatosLogin usuario) {

		Conexion con = null;
		ResultSet rs = null;
		String sql;

		try {
			con = new Conexion();
			sql = "SELECT cod_usuario,nick_usuario,contraseña,tipo_usuario,"
					+ "nom_usuario,correo,pregunta_seguridad,respuesta_seguridad FROM usuario "
					+ "WHERE nick_usuario='" + usuario.getUsuario() + "'";
			//System.out.println(sql);
			rs = con.obtenerRegistros(sql);
			boolean flag = true;
			
			if (rs.next()) {
				flag = false;
				usuario.setIdUsuario(rs.getInt("cod_usuario"));
				usuario.setUsuario(rs.getString("nick_usuario"));
				usuario.setPassword(rs.getString("contraseña")); 
				usuario.setTipoUsuario(rs.getInt("tipo_usuario"));
				usuario.setNombre(rs.getString("nom_usuario"));
				usuario.setCorreo(rs.getString("correo"));
				usuario.setPreguntaSeguridad(rs.getInt("pregunta_seguridad"));
				usuario.setRespuestaSeguridad(rs.getString("respuesta_seguridad"));
			}

			if (flag) {
				usuario.setIdUsuario(-2);
				return usuario;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.cerrarRs(rs);
				if (con != null)
					con.cerrarConexion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return usuario;
	}
	
	//REGISTRO
	public static boolean registrarse(DatosRegistro datosRegistro) {
		
		Conexion con = null;
		String sql, sql2;
		String nombre = datosRegistro.getNombre();
		String correo = datosRegistro.getCorreo();
		String usuario = datosRegistro.getUsuario();
		String password = datosRegistro.getPassword();
		int pregunta = datosRegistro.getPregunta();
		String respSecreta = datosRegistro.getRespuesta();
		
		ResultSet rs = null;
		
		try {
			con = new Conexion();
			
			sql = "INSERT INTO usuario (nom_usuario, contraseña, fecha_mod, usuario_mod, nick_usuario, "
					+ "correo, pregunta_seguridad, partidas_jugadas, total_puntos, fecha_creacion,partidas_ganadas,tipo_usuario,respuesta_seguridad)"
					+ " VALUES ('"+nombre+"','"+password
					+"',getdate(),'"+usuario+"','"+usuario+"','"+correo+"','"+pregunta+"',0,0,"+"getdate()"+",0,1,'"+respSecreta+"');"
			  		+"INSERT INTO ranking (cod_usuario, puntos) VALUES ('"+usuario+"',0);";
			
			sql2 = "SELECT nick_usuario FROM usuario WHERE nick_usuario='" + usuario + "'";
			
			rs = con.obtenerRegistros(sql2);
			
			if (rs.next()) {
				return false;
			}
			
			return con.ejecutarQuery(sql);
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try {
				if (con != null)
					con.cerrarConexion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static void crearPartida(DatosCrearPartida datosCrearPartida) {
		
		Conexion con = null;
		String sql;
		
		String nombre= datosCrearPartida.getNombre();
		int min= datosCrearPartida.getCantMin();
		int max= datosCrearPartida.getCantMax();
		String estadoIni= "En Espera";
		int usuario= datosCrearPartida.getUsuarioId();
		
		try {
			con = new Conexion();
			sql = "INSERT INTO partida (nom_partida, estado, min_jugadores, max_jugadores, cant_jugadores, "
					+ "puntos, tiempo_de_juego, fecha_mod, usuario_mod) VALUES ('"+nombre+"','"+estadoIni
					+"',"+min+","+max+",0,0,null,getdate(),'"+usuario+"');";
			//System.out.println(sql);
			
			con.ejecutarQuery(sql);
			
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try {
				if (con != null)
					con.cerrarConexion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static DatosPartidas cargarTablaPrincipal(){
		
		Conexion con = null;
		ResultSet rs = null;
		String sql;
		String[][] registro = new String[10][8];
		
		DatosPartidas datos = null;
		
		try {
			con = new Conexion();
			sql = "select * FROM partida";
			//System.out.println(sql);
			rs = con.obtenerRegistros(sql);
		
			int k = 0;
			while(rs.next()) {
				registro[k][0] = rs.getString("cod_partida");
				registro[k][1] = rs.getString("nom_partida");
				registro[k][2] = rs.getString("estado");
				registro[k][3] = rs.getString("min_jugadores");
				registro[k][4] = rs.getString("max_jugadores");
				registro[k][5] = rs.getString("cant_jugadores");
				registro[k][6] = rs.getString("puntos");
				registro[k][7] = rs.getString("fecha_mod");
				k++;
			}
			
			String[][] aux = new String[k][8];
			
			for (int i = 0; i < aux.length; i++) {
				for (int j = 0; j < registro[0].length; j++) {
					aux[i][j] = registro[i][j];
				}
			}
			datos = new DatosPartidas(aux);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.cerrarRs(rs);
				if (con != null)
					con.cerrarConexion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return datos;
	}
		
	public static boolean unirseAPartida(int codPartida,int codUsuario){
		
		Conexion con = null;
		String sql;
		
		try {
			con = new Conexion();
			sql = "INSERT INTO historial_partidas (cod_partida, cod_usuario, puntos_usu_x_partida,fecha_mod) VALUES ("+codPartida+
					","+codUsuario+",0,getdate()); "
							+ "UPDATE usuario SET partidas_jugadas= partidas_jugadas+1 WHERE cod_usuario= "+codUsuario+"; "
							+ "UPDATE partida SET cant_jugadores = cant_jugadores+1 WHERE cod_partida= "+codPartida+";";
					
			//System.out.println(sql);
			if (con.ejecutarQuery(sql)== true)
				return true;
			else
				return false;
	
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try {
				if (con != null)
					con.cerrarConexion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static int obtenerIdPartida(String nombre){
		
		Conexion con = null;
		ResultSet rs = null;
		String sql;
		int id = -1;
		
		try {
			con = new Conexion();
			sql = "select cod_partida FROM partida where nom_partida = '"+nombre+"'";
			//System.out.println(sql);
			rs = con.obtenerRegistros(sql);
			
			if(rs.next()) {
				id = rs.getInt("cod_partida");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.cerrarRs(rs);
				if (con != null)
					con.cerrarConexion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return id;
	}
	
	public static void vaciarPartidas() {

		Conexion con = null;
		ResultSet rs = null;
		String sql;

		try {
			con = new Conexion();
			sql = "DELETE FROM partida";
			//System.out.println(sql);
			con.ejecutarQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.cerrarRs(rs);
				if (con != null)
					con.cerrarConexion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void agregarUsuario(int idPartida, int cantidad) {
		Conexion con = null;
		String sql;
		
		try {
			
			con = new Conexion();
			sql = "UPDATE partida SET cant_jugadores = '"+cantidad+"' WHERE cod_partida = '"+idPartida+"';";
			
			con.ejecutarQuery(sql);
			
			
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.cerrarConexion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}