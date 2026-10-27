package clinica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    static String servidor = "jdbc:mysql://localhost:3306/";
    static String url = "jdbc:mysql://localhost:3306/ClinicaDB";
    static String usuario = "root";
    static String clave = "2005";

    public static Connection conectarServidor() throws SQLException {
        return DriverManager.getConnection(servidor, usuario, clave);
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, usuario, clave);
    }
}
