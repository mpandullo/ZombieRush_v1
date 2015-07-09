package servidor;

import interfaz.Login;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import datosSocket.DatosLogin;
import datosSocket.DatosRegistro;

public class ConsultasUsuario {
	
	//LOGIN INICIAL
	public static DatosLogin login(DatosLogin usuario) {

		Conexion con = null;
		ResultSet rs = null;
		String sql;
		int codUsuario = 0;
		int tipoUsuario = 0;
		int preguntaSeguridad = 0;
		String nickUsuario = null, contrase�a = null;
		String nombre = null, correo = null, respuestaSeguridad = null;

		try {
			con = new Conexion();
			sql = "SELECT cod_usuario,nick_usuario,contrase�a,tipo_usuario,"
					+ "nom_usuario,correo,pregunta_seguridad,respuesta_seguridad FROM usuario "
					+ "WHERE nick_usuario='" + usuario.getUsuario() + "'";
			//System.out.println(sql);
			rs = con.obtenerRegistros(sql);

			if (rs.next()) {

				usuario.(rs.getInt("cod_usuario"));
				nickUsuario = rs.getString("nick_usuario");
				contrase�a = rs.getString("contrase�a");
				usuario.setTipoUsuario(rs.getInt("tipo_usuario"));
				usuario.setNombre(rs.getString("nom_usuario"));
				usuario.setCorreo(rs.getString("correo"));
				usuario.setPreguntaSeguridad(rs.getInt("pregunta_seguridad"));
				usuario.setRespuestaSeguridad(rs.getString("respuesta_seguridad"));
			}

			if (nickUsuario == null || contrase�a == null) {
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
		String sql;
		
		try {
			con = new Conexion();
			
			sql = "INSERT INTO usuario (nom_usuario, contrase�a, fecha_mod, usuario_mod, nick_usuario, "
					+ "correo, pregunta_seguridad, partidas_jugadas, total_puntos, fecha_creacion) VALUES ('"+nombre+"','"+password
					+"',getdate(),'"+usuario+"','"+usuario+"','"+correo+"',0,0,0,"+"getdate()"+");"
			  		+"INSERT INTO ranking (cod_usuario, puntos) VALUES ('"+usuario+"',0);";
					
			//System.out.println(sql);
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
}
}