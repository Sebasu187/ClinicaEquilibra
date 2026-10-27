package clinica;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            BaseDatos.crear();
            SwingUtilities.invokeLater(() -> {
                Formulario formulario = new Formulario();
                formulario.setVisible(true);
            });
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexion: " + e.getMessage());
        }
    }
}
