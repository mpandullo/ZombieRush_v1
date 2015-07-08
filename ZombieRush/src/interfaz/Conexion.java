package interfaz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

	private Connection con;
	private Statement db; 
	
	public Conexion() throws SQLException {
		abrirConexion();
	}
	
	private void abrirConexion() throws SQLException {

		try {
			Class.forName("com.sybase.jdbc3.jdbc.SybDriver");
			String url = "jdbc:sybase:Tds:localhost:2638";
			con = DriverManager.getConnection(url, "DBA","SQL");
			db = con.createStatement();
			}catch(ClassNotFoundException ex) {
				ex.printStackTrace();
		}
	}
	
	public void cerrarConexion() {
		try {
			if (db != null) {
				db.close();
			}
			if (con != null) {
			con.close();
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
			}

		}
	
	public boolean ejecutarQuery(String sql) {

		try {
			db.execute(sql);
			return true;
		}catch (SQLException ex) {
			ex.printStackTrace();
			return false;
			}
	}
	
	public ResultSet obtenerRegistros(String sql) {

		try {

			Statement dbTmp = con.createStatement();
			return dbTmp.executeQuery(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public void cerrarRs(ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
		} catch (SQLException ex) {

		ex.printStackTrace();
			}
		}

	}
}
