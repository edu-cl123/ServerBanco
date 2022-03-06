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
import java.util.List;


public class UsuarioDAO extends Usuario {

    //SENTENCIAS SQL
    public UsuarioDAO(Usuario user) {
        this.pin = user.getPin();
        this.id_card = user.getId_card();
        this.Admin = user.getId_Admin();
        this.nombre = user.getNombre();
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private final static String ANADIR = "INSERT INTO usuario (id_card,nombre,pin,dni,id_admin)"
            + "VALUES (?,?,?,?,?)";

    private final static String BORRAR = "DELETE FROM usuario WHERE id_card=?";

    private final static String LISTAR_TODOS = "SELECT * FROM usuario";


    private final static String OBTERNER_X_ID_CARD = "SELECT * FROM usuario WHERE id_card=?";

    private final static String COMPROBAR_PIN = "SELECT * FROM usuario WHERE (id_card=?) AND (pin=?)";

    //CONSTRUCTORES
    Connection con;

    public UsuarioDAO() {

    }

    public UsuarioDAO(String id_card, int pin) {
        super(id_card, pin);

    }

    public UsuarioDAO(String id_card, String nombre, int pin, String dni, Admin admin) {
        super(id_card, nombre, pin, dni, admin);

    }

    /*
     * AÑADIR UN USUARIO
     */
    public synchronized static Usuario anadir(Usuario u) {
        Usuario result = new Usuario();
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));
        if (con != null) {

            try {
                PreparedStatement q = con.prepareStatement(ANADIR);
                q.setString(1, u.getId_card());
                q.setString(2, u.getNombre());
                q.setInt(3, u.getPin());
                q.setString(4, u.getDNI());
                q.setInt(5, u.getId_Admin().getId());

                q.executeUpdate();
                result = u;
                q.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    /*
     * BORRAR UN USUARIO.
     *
     * Recibe un String id_card
     * Devuelve un entero.
     */
    public synchronized int eliminar(String id_card) {
        int rs = 0;
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));
        if (con != null) {
            try {
                PreparedStatement q = con.prepareStatement(BORRAR);
                q.setString(1, id_card);
                rs = q.executeUpdate();
                this.id_card = "";
                this.nombre = "";
                this.pin = -1;
                this.DNI = "";
                this.Admin = null;
                q.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return rs;
    }

    /*
     * OBTENER UN USUARIO POR ID_CARD
     *
     * Recibe un String id_card
     * Devuelve un UsuarioDAO
     */
    public synchronized static Usuario getUsuarioPorId_card(String id_card) {
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));
        Usuario result = new Usuario();

        if (con != null) {
            try {
                PreparedStatement q = con.prepareStatement(OBTERNER_X_ID_CARD);
                q.setString(1, id_card);
                ResultSet rs = q.executeQuery();
                while (rs.next()) {
                    result.setId_card(rs.getString("id_card"));
                    result.setNombre(rs.getString("nombre"));
                    result.setPin(rs.getInt("pin"));
                    result.setDNI(rs.getString("DNI"));
                    result.setId_Admin(AdminDAO.getAdminPorId(rs.getInt("id_Admin")));

                }
                q.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }

    /*
     * LISTAR TODOS LOS USUARIOS
     *
     * Devuelve una List de usuarios.
     */
    public synchronized static List<Usuario> ListarTodos() {
        List<Usuario> result = new ArrayList<Usuario>();
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));
        if (con != null) {

            try {
                PreparedStatement q = con.prepareStatement(LISTAR_TODOS);
                ResultSet rs = q.executeQuery();
                while (rs.next()) {
                    Usuario cl = new Usuario();
                    cl.setId_card(rs.getString("id_card"));
                    cl.setNombre(rs.getString("nombre"));
                    cl.setPin(rs.getInt("pin"));
                    cl.setDNI(rs.getString("DNI"));
                    cl.setId_Admin(AdminDAO.getAdminPorId(rs.getInt("id_Admin")));

                    result.add(cl);

                }
                q.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return result;
    }

    /*
     * COMPROBAR SI EL  PIN ES VALIDO
     *
     * Recibe un dos enteros un ID y un PIN.
     * Devuelve un Boolean true si el usuario con ese id tiene ese pin.
     *
     */
    public synchronized static Boolean comprobarPin(String id_card, int pin) {
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));

        UsuarioDAO a = new UsuarioDAO();
        UsuarioDAO b = new UsuarioDAO(id_card, pin);
        boolean result = false;
        if (con != null) {
            try {
                PreparedStatement q = con.prepareStatement(COMPROBAR_PIN);
                q.setString(1, id_card);
                q.setInt(2, pin);
                ResultSet rs = q.executeQuery();

                while (rs.next()) {
                    a.setId_card(rs.getString("id_card"));
                    a.setPin(rs.getInt("pin"));
                }

                if (a.equals(b)) {
                    result = true;
                }
                q.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }

}
