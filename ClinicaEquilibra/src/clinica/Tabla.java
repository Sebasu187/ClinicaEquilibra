package clinica;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tabla {

    public void cargar(String nombre, Date fecha, String cedula, String rh,
                       double peso, double estatura, double imc) throws SQLException {
        String sql = "INSERT INTO pacientes (nombre, fecha_nacimiento, cedula, rh, peso, estatura, imc) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setString(1, nombre);
            consulta.setDate(2, fecha);
            consulta.setString(3, cedula);
            consulta.setString(4, rh);
            consulta.setDouble(5, peso);
            consulta.setDouble(6, estatura);
            consulta.setDouble(7, imc);
            consulta.executeUpdate();
        }
    }

    public String buscar(String nombre) throws SQLException {
        String sql = "SELECT nombre, imc FROM pacientes WHERE nombre LIKE ?";
        String texto = "";
        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setString(1, "%" + nombre + "%");
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                texto = texto + "Nombre: " + rs.getString("nombre") +
                        "   IMC: " + rs.getDouble("imc") + "\n";
            }
        }
        return texto;
    }
}
