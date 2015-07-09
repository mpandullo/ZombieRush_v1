package servidor;

import interfaz.Conexion;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import datosSocket.DatosLogin;

public class ConsultasUsuario {
	
	public static DatosLogin login(DatosLogin usuario) {

		Conexion con = null;
		ResultSet rs = null;
		String sql;
		int codUsuario = 0;
		int tipoUsuario = 0;
		int preguntaSeguridad = 0;
		String nickUsuario = null, contraseña = null;
		String nombre = null, correo = null, respuestaSeguridad = null;

		try {
			con = new Conexion();
			sql = "SELECT cod_usuario,nick_usuario,contraseña,tipo_usuario,"
					+ "nom_usuario,correo,pregunta_seguridad,respuesta_seguridad FROM usuario "
					+ "WHERE nick_usuario='" + usuario.getUsuario() + "'";
			//System.out.println(sql);
			rs = con.obtenerRegistros(sql);

			if (rs.next()) {

				codUsuario = rs.getInt("cod_usuario");
				nickUsuario = rs.getString("nick_usuario");
				contraseña = rs.getString("contraseña");
				tipoUsuario = rs.getInt("tipo_usuario");
				nombre = rs.getString("nom_usuario");
				correo = rs.getString("correo");
				preguntaSeguridad = rs.getInt("pregunta_seguridad");
				respuestaSeguridad = rs.getString("respuesta_seguridad");
			}

			if (nickUsuario == null || contraseña == null) {
				usuario.setIdUsuario(-2);
				return usuario;
			}

			if (contraseña.equals(usuario.getPassword())) {
				usuario.setTipoUsuario(tipoUsuario);
				usuario.setNombre(nombre);
				usuario.setCorreo(correo);
				usuario.setPreguntaSeguridad(preguntaSeguridad);
				usuario.setRespuestaSeguridad(respuestaSeguridad);
			} else {
				usuario.setIdUsuario(-1);
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
	
	public void cargarTablaEstadisticas(JTable tabla){
		
		Conexion con = null;
		ResultSet rs = null;
		String sql;
		DefaultTableModel modeloE = new DefaultTableModel(null,getColumnasEstadisticas());
		
		try {
			con = new Conexion();
			sql = "SELECT par.nom_partida,his.puntos_usu_x_partida FROM historial_partidas his "
					+ "INNER JOIN partida par ON his.cod_partida= par.cod_partida "
					+ "INNER JOIN usuario usu ON his.cod_usuario= usu.cod_usuario "
					+ "WHERE his.cod_usuario= "+usuario.getIdUsuario()+" ;";
			//System.out.println(sql);
			rs = con.obtenerRegistros(sql);
			Object[] fila = new Object[2];
			while (rs.next()) {
				
				for(int i=0; i< 2; i++){
					
					fila[i]= rs.getObject(i+1);
				}
				modeloE.addRow(fila);
			}
			
			tablaEstadisticas.setModel(modeloE);
			
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
	
	/*public void recuperarDatosEstadisticas(){
		
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
	}*/
	
}