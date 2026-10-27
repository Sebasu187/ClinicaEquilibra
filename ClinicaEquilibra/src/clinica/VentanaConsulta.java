package clinica;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class VentanaConsulta extends JFrame {
    JTextField buscar;
    JButton boton;
    JTextArea resultado;
    Tabla tabla = new Tabla();

    public VentanaConsulta() {
        setTitle("Consulta de Pacientes");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        buscar = new JTextField(15);
        boton = new JButton("Buscar");
        resultado = new JTextArea();
        resultado.setEditable(false);

        JPanel arriba = new JPanel();
        arriba.add(new JLabel("Nombre:"));
        arriba.add(buscar);
        arriba.add(boton);

        setLayout(new BorderLayout());
        add(arriba, BorderLayout.NORTH);
        add(new JScrollPane(resultado), BorderLayout.CENTER);

        boton.addActionListener(e -> consultar());
    }

    void consultar() {
        String nom = buscar.getText().trim();
        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe un nombre");
            return;
        }
                if (!nom.matches("[\\p{L} ]+")) {
            JOptionPane.showMessageDialog(this, "El nombre no puede tener numeros, puntos ni caracteres especiales");
            return;
        }
        try {
            String datos = tabla.buscar(nom);
            if (datos.isEmpty()) {
                resultado.setText("");
                JOptionPane.showMessageDialog(this, "El paciente no existe");
            } else {
                resultado.setText("Busqueda: " + nom + "\n\n" + datos);
            }
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al consultar: " + ex.getMessage());
        }
    }
}
