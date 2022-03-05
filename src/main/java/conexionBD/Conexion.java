package conexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	private static Connection con;
	 public static Connection conectar(DatosDeConexion c) throws SQLException {
	        Connection conn;
	        if (c == null) {
	            return null;
	        }
	        conn = (Connection) DriverManager.getConnection("jdbc:mysql://" + c.getServer() + "/" + c.getDatabase(), c.getUsername(), c.getPassword());
	        return conn;
	    }
	public static Connection getConexion(DatosDeConexion c) {
		if(con==null) {
			try {
				con = conectar(c);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return con;
	}
}
