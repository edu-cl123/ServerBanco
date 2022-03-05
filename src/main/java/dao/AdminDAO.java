package dao;

import conexionBD.Conexion;
import conexionBD.UtilidadXml;
import modelos.Admin;
import modelos.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDAO extends Admin {

    //SENTENCIAS SQL

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final static String ANADIR = "INSERT INTO admin (id,pin) VALUES (?,?)";

    private final static String BORRAR = "DELETE FROM admin WHERE id=?";

    private final static String LISTAR_TODOS = "SELECT * FROM admin";

    private final static String OBTERNER_X_ID = "SELECT * FROM admin WHERE id=?";

    private final static String COMPROBAR_PIN = "SELECT * FROM admin WHERE (id=?) AND (pin=?)";

    public AdminDAO() {
        super();
    }

    public AdminDAO(int id, int pin) {
        super(id, pin);

    }

    /*
     * AÑADIR UN ADMIN
     *
     */
    public synchronized int anadir() {
        int result = 0;
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));
        if (con != null) {

            try {
                PreparedStatement q = con.prepareStatement(ANADIR);

                q.setInt(1, this.id);
                q.setInt(2, this.pin);

                result = q.executeUpdate();

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    /*
     * ELIMINAR UN ADMIN POR ID
     *
     */
    public synchronized int eliminar(int id) {
        int rs = 0;
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));
        if (con != null) {
            try {
                PreparedStatement q = con.prepareStatement(BORRAR);
                q.setInt(1, id);
                rs = q.executeUpdate();
                this.id = -1;
                this.pin = -1;
                this.lista_usuarios = new ArrayList<Usuario>();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return rs;
    }

    /*
     *
     *
     */
    public synchronized static Admin getAdminPorId(int id) {
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));
        Admin result = new Admin();

        if (con != null) {
            try {
                PreparedStatement q = con.prepareStatement(OBTERNER_X_ID);
                q.setInt(1, id);
                ResultSet rs = q.executeQuery();
                while (rs.next()) {
                    result.setId(rs.getInt("id"));
                    result.setPin(rs.getInt("pin"));


                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }

    /*
     * Comprobar si el PIN introducido coincide con el pin del admin de la base de datos.
     *
     */
    public synchronized static Boolean comprobarPin(int id, int pin) {
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));

        AdminDAO a = new AdminDAO();
        AdminDAO b = new AdminDAO(id, pin);
        boolean result = false;
        if (con != null) {
            try {
                PreparedStatement q = con.prepareStatement(COMPROBAR_PIN);
                q.setInt(1, id);
                q.setInt(2, pin);
                ResultSet rs = q.executeQuery();

                while (rs.next()) {
                    a.setId(rs.getInt("id"));
                    a.setPin(rs.getInt("pin"));
                }

                if (a.equals(b)) {
                    result = true;
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }

}
