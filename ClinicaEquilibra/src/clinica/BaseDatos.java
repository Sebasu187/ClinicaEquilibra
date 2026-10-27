package clinica;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatos {

    public static void crear() throws SQLException {
        try (Connection conexion = Conexion.conectarServidor();
             Statement sentencia = conexion.createStatement()) {

            sentencia.executeUpdate("CREATE DATABASE IF NOT EXISTS ClinicaDB");

            sentencia.executeUpdate(
                "CREATE TABLE IF NOT EXISTS ClinicaDB.pacientes (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(100) NOT NULL, " +
                "fecha_nacimiento DATE NOT NULL, " +
                "cedula VARCHAR(15) NOT NULL UNIQUE, " +
                "rh VARCHAR(3) NOT NULL, " +
                "peso DECIMAL(5,2) NOT NULL, " +
                "estatura DECIMAL(3,2) NOT NULL, " +
                "imc DECIMAL(5,2) NOT NULL)"
            );
        }
    }
}
