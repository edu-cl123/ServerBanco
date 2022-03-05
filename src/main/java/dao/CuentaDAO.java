package dao;

import conexionBD.Conexion;
import conexionBD.UtilidadXml;
import modelos.Cuenta;
import modelos.Transaccion;
import modelos.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CuentaDAO extends Cuenta {

    // SENTENCIAS

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final static String CREAR_CUENTA = "INSERT INTO cuenta (id,saldo,usuario_id)" + "VALUES (?,?,?)";

    private final static String BORRAR_CUENTA = "DELETE FROM cuenta WHERE id=?";

    private final static String INGRESAR_RETIRAR = "UPDATE cuenta SET saldo=? WHERE id=?";

    private final static String CUENTA_X_ID = "SELECT * FROM cuenta WHERE id=?";
    private final static String CUENTA_X_ID_USER = "SELECT * FROM cuenta WHERE usuario_id=?";

    Connection con;

    public CuentaDAO() {
        super();
    }


    public CuentaDAO(float saldo, Usuario id_usuario, List<Transaccion> lista_Transacciones) {
        super(saldo, id_usuario, lista_Transacciones);

    }

    /*
     * AÑADIR UN USUARIO
     */
    public synchronized static Cuenta anadir(Cuenta cuenta) {
        Cuenta result = new Cuenta();
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));
        if (con != null) {

            try {
                PreparedStatement q = con.prepareStatement(CREAR_CUENTA);
                q.setInt(1, cuenta.getId());
                q.setFloat(2, cuenta.getSaldo());
                q.setString(3, cuenta.getId_usuario().getId_card());
                q.executeUpdate();
                result = cuenta;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    /*
     * BORRAR UN CUENTA.
     *
     * Recibe un String id_card
     * Devuelve un entero.
     */
    public synchronized static int eliminar(int id) {
        int rs = 0;
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));
        if (con != null) {
            try {
                PreparedStatement q = con.prepareStatement(BORRAR_CUENTA);
                q.setInt(1, id);
                rs = q.executeUpdate();
                q.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return rs;
    }

    public synchronized static Cuenta Insertar_Retirar(Cuenta cuentaToUpdate, boolean opcion, float cantidad) {

        //INGRESAR -> true , RETIRAR -> false
        Cuenta result = new Cuenta();
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));
        if (con != null) {
            if (opcion) {
                float ingresar = CuentaDAO.getCuentaPorId(cuentaToUpdate.getId()).getSaldo() + cantidad;
                try {
                    PreparedStatement q = con.prepareStatement(INGRESAR_RETIRAR);
                    q.setFloat(1, ingresar);
                    q.setInt(2, cuentaToUpdate.getId());
                    q.executeUpdate();
                    result = cuentaToUpdate;
                    result.setSaldo(ingresar);
                    q.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //TRANSACCION DE INGRESAR DINERO

                TransaccionDAO tr = new TransaccionDAO(CuentaDAO.getCuentaPorId(cuentaToUpdate.getId()), cantidad, opcion);
                tr.guardarTrans();
            } else {

                float ingresar = CuentaDAO.getCuentaPorId(cuentaToUpdate.getId()).getSaldo() - cantidad;

                try {
                    PreparedStatement q = con.prepareStatement(INGRESAR_RETIRAR);
                    q.setFloat(1, ingresar);
                    q.setInt(2, cuentaToUpdate.getId());
                    q.executeUpdate();
                    result = cuentaToUpdate;
                    result.setSaldo(ingresar);
                    q.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //TRANSACCION DE RETIRAR DINERO
                TransaccionDAO tr = new TransaccionDAO(CuentaDAO.getCuentaPorId(cuentaToUpdate.getId()), cantidad, opcion);
                tr.guardarTrans();
            }
        }
        return result;
    }

    /*
     * OBTENER UN USUARIO POR ID_CARD
     *
     * Recibe un String id_card
     * Devuelve un UsuarioDAO
     */
    public synchronized static Cuenta getCuentaPorId(int id) {
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));
        Cuenta result = new Cuenta();

        if (con != null) {
            try {
                PreparedStatement q = con.prepareStatement(CUENTA_X_ID);
                q.setInt(1, id);
                ResultSet rs = q.executeQuery();
                while (rs.next()) {
                    result.setId(rs.getInt("id"));
                    result.setSaldo(rs.getFloat("saldo"));
                    result.setId_usuario(UsuarioDAO.getUsuarioPorId_card(rs.getString("usuario_id")));
                }
                rs.close();
                q.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }


    public synchronized static Cuenta getCuentaById_User(String id_user) {
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));
        Cuenta result = new Cuenta();

        if (con != null) {
            try {
                PreparedStatement q = con.prepareStatement(CUENTA_X_ID_USER);
                q.setString(1, id_user);
                ResultSet rs = q.executeQuery();
                while (rs.next()) {
                    result.setId(rs.getInt("id"));
                    result.setSaldo(rs.getFloat("saldo"));
                    result.setId_usuario(UsuarioDAO.getUsuarioPorId_card(rs.getString("usuario_id")));
                }
                rs.close();
                q.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
