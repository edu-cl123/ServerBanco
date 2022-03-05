package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexionBD.Conexion;
import conexionBD.UtilidadXml;
import modelos.Cuenta;
import modelos.Transaccion;

public class TransaccionDAO extends Transaccion {


    //SENTENCIAS

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final static String GUARDAR_TRANSACCION = "INSERT INTO transaccion (cuenta_id,importe,tipo)" + "VALUES (?,?,?)";
    private final static String LS_TRANSAC_CUENTA = "SELECT * FROM transaccion WHERE cuenta_id=?";
    private final static String LS_TODAS_TRANSAC = "SELECT * FROM transaccion";


    public TransaccionDAO() {
        super();
    }

    public TransaccionDAO(Cuenta cuenta, float importe, boolean tipo) {
        super(cuenta, importe, tipo);
    }

    /*
     * AÑADIR UN USUARIO
     */
    public synchronized int guardarTrans() {
        int result = 0;
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));
        if (con != null) {

            try {
                PreparedStatement q = con.prepareStatement(GUARDAR_TRANSACCION);
                q.setInt(1, this.id_cuenta.getId());
                q.setFloat(2, this.importe);
                q.setBoolean(3, this.tipo);
                result = q.executeUpdate();
                q.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    public synchronized static List<Transaccion> ListarTransCuenta(Cuenta id_cuenta) {
        List<Transaccion> result = new ArrayList<>();
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));
        if (con != null) {

            try {
                PreparedStatement q = con.prepareStatement(LS_TRANSAC_CUENTA);
                q.setInt(1, id_cuenta.getId());
                ResultSet rs = q.executeQuery();
                while (rs.next()) {
                    Transaccion tr = new Transaccion();

                    tr.setId(rs.getInt("id"));
                    tr.setId_cuenta(CuentaDAO.getCuentaPorId(rs.getInt("cuenta_id")));
                    tr.setImporte(rs.getInt("importe"));
                    tr.setTipo(rs.getBoolean("tipo"));
                    result.add(tr);
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

    public synchronized static List<Transaccion> ListarTodasTransac() {
        List<Transaccion> result = new ArrayList<>();
        Connection con = Conexion.getConexion(UtilidadXml.loadFile("conexion.xml"));
        if (con != null) {

            try {
                PreparedStatement q = con.prepareStatement(LS_TODAS_TRANSAC);
                ResultSet rs = q.executeQuery();
                while (rs.next()) {
                    Transaccion tr = new Transaccion();

                    tr.setId(rs.getInt("id"));
                    tr.setId_cuenta(CuentaDAO.getCuentaPorId(rs.getInt("cuenta_id")));
                    tr.setImporte(rs.getInt("importe"));
                    tr.setTipo(rs.getBoolean("tipo"));
                    result.add(tr);
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
}
