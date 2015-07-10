package servidor;

import interfaz.Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
		String sql,sql2;
		ResultSet rs;
		String nombre = datosRegistro.getNombre();
		String correo = datosRegistro.getCorreo();
		String usuario = datosRegistro.getUsuario();
		char[] password = datosRegistro.getPassword();
		String respSecreta = datosRegistro.getRespuesta();
		int cuenta = 0;
		
		try {
			con = new Conexion();
			
			sql = "INSERT INTO usuario (nom_usuario, contraseña, fecha_mod, usuario_mod, nick_usuario, "
					+ "correo, pregunta_seguridad, partidas_jugadas, total_puntos, fecha_creacion,partidas_ganadas,tipo_usuario,respuesta_seguridad)"
					+ " VALUES ('"+nombre+"','"+password.toString()
					+"',getdate(),'"+usuario+"','"+usuario+"','"+correo+"',0,0,0,"+"getdate()"+",0,1,'"+respSecreta+"');"
			  		+"INSERT INTO ranking (cod_usuario, puntos) VALUES ('"+usuario+"',0);";
					
			sql2 = "SELECT COUNT(*) cuenta FROM usuario WHERE nick_usuario='"+datosRegistro.getUsuario()+"'";
			
			//System.out.println(sql2);
			//System.out.println();
			rs= con.obtenerRegistros(sql2);
			if (rs.next()) {
				cuenta= rs.getInt("cuenta");
			}
			
			//System.out.println(sql);
			if(cuenta>0)
				return false;
			else
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
	
	//PARA EL PERFIL SE PODRIA USAR EL METODO ANTERIOR
	
	
	//CARGAR LOS 3 DATOS DE ARRIBA
	public static void recuperarDatosEstadisticas(){
		
		Conexion con=null;
		ResultSet rs = null;
		String sql;
		
		try {
			con = new Conexion();
			sql = "SELECT partidas_ganadas,total_puntos,cod_ranking FROM usuario usu "+
					"INNER JOIN ranking ran ON usu.cod_usuario= ran.cod_usuario "+
					"WHERE usu.cod_usuario="+Login.getCodUsuario();
			//System.out.println(sql);
			
			rs = con.obtenerRegistros(sql);
			if (rs.next()) {
				juegosGanados= rs.getInt("partidas_ganadas");
				puntosAcumulados=rs.getInt("total_puntos");
				rankingGral=rs.getInt("cod_ranking");	
			}
		}catch (SQLException e) {
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
	
	//CARGAR LOS DATOS DE LA TABLA
	public static void cargarTablaEstadisticas(JTable tablaE){
		
		Conexion con = null;
		ResultSet rs = null;
		String sql;
		
		DefaultTableModel modeloE = new DefaultTableModel(null,getColumnasEstadisticas());
		try {
			con = new Conexion();
			sql = "SELECT par.nom_partida,his.puntos_usu_x_partida FROM historial_partidas his "
					+ "INNER JOIN partida par ON his.cod_partida= par.cod_partida "
					+ "INNER JOIN usuario usu ON his.cod_usuario= usu.cod_usuario "
					+ "WHERE his.cod_usuario= "+Login.getCodUsuario()+" ;";
			//System.out.println(sql);
			rs = con.obtenerRegistros(sql);
			Object[] fila = new Object[2];
			while (rs.next()) {
				
				for(int i=0; i< 2; i++){
					
					fila[i]= rs.getObject(i+1);
				}
				modeloE.addRow(fila);
			}
			tablaE.setModel(modeloE);
			
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
	
	private String[] getColumnasEstadisticas()
    {
          String columna[]=new String[]{"Partida", "Puntos"};
          return columna;
    }
	
	//CARGAR TABLA DE RANKINGS
	
	public void cargarTablaRanking(JTable tabla){
		
		Conexion con = null;
		ResultSet rs = null;
		String sql;
		tabla.setShowVerticalLines(false);
		DefaultTableModel modeloR = new DefaultTableModel(null,getColumnasRanking());
		
		
		try {
			con = new Conexion();
			sql = "SELECT cod_ranking, usu.nick_usuario, ran.puntos FROM ranking ran INNER JOIN usuario usu "
					+ "ON usu.cod_usuario= ran.cod_usuario ORDER BY cod_ranking";
			//System.out.println(sql);
			rs = con.obtenerRegistros(sql);
			Object[] fila = new Object[3];
			while (rs.next()) {
				
				for(int i=0; i< 3; i++){
					
					fila[i]= rs.getObject(i+1);
				}
				modeloR.addRow(fila);
			}
			tabla.setModel(modeloR);
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
	
	private String[] getColumnasRanking()
    {
          String columna[]=new String[]{"Posicion","Usuario", "Puntos"};
          return columna;
    }
	
	public static DatosPartida insertarPartida(DatosPartida datosPartida) {
		
		String estadoIni="Pendiente";
		
		Conexion con = null;
		String sql;
		
		try {
			con = new Conexion();
			sql = "INSERT INTO partida (nom_partida, estado, min_jugadores, max_jugadores, cant_jugadores, "
					+ "puntos, tiempo_de_juego, fecha_mod, usuario_mod) VALUES ('"+nombrePartida+"','"+estadoIni
					+"',"+minimoJugador+","+maximoJugador+",0,0,null,getdate(),'"+Consultas.getTxt_Usuario().getText()+"');";
			//System.out.println(sql);
			if (con.ejecutarQuery(sql)== true) {
				JOptionPane.showMessageDialog(this, "Partida Insertada exitosamente!");	
				
				this.dispose();
				
			}
			else
				JOptionPane.showMessageDialog(this, "Error al Insertar Registro, Revise los Datos");
		// TODO si el registro es exitoso abrimos dialogo y cerramos
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
	
	public static int crearPartida(DatosCrearPartida datosCrearPartida) {
		
		Conexion con = null;
		String sql,sql2;
		
		String nombre= datosCrearPartida.getNombre();
		int min= datosCrearPartida.getCantMin();
		int max= datosCrearPartida.getCantMax();
		String estadoIni= "En Espera";
		int usuario= datosCrearPartida.getUsuarioId();
		ResultSet rs;
		int cod_partida=0;
		
		try {
			con = new Conexion();
			sql = "INSERT INTO partida (nom_partida, estado, min_jugadores, max_jugadores, cant_jugadores, "
					+ "puntos, tiempo_de_juego, fecha_mod, usuario_mod) VALUES ('"+nombre+"','"+estadoIni
					+"',"+min+","+max+",0,0,null,getdate(),'"+usuario+"');";
			//System.out.println(sql);
			
			sql2= "select top 1 cod_partida cod_partida from partida order by fecha_mod desc";
			
			rs = con.obtenerRegistros(sql2);
			if (rs.next()) {
				cod_partida= rs.getInt("cod_partida");
			}
		
			if (con.ejecutarQuery(sql)== true)
				return cod_partida;
			else
				return -1;
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
		return -1;
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
	
	private static String[] getColumnasPrincipal()
    {
          String columna[]=new String[]{"","Partida","Participantes", "Estado"};
          return columna;
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
}