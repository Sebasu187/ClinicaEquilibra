package clinica;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Formulario extends JFrame {
    JTextField nombre, fecha, cedula, peso, estatura;
    JComboBox<String> rh;
    JButton guardar, consultar;
    JLabel estado;
    Tabla tabla = new Tabla();

    public Formulario() {
        setTitle("Clinica Equilibra - Registro de Pacientes");
        setSize(450, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        nombre = new JTextField();
        fecha = new JTextField();
        cedula = new JTextField();
        rh = new JComboBox<>(new String[]{"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"});
        peso = new JTextField();
        estatura = new JTextField();

        panel.add(new JLabel("Nombre:"));
        panel.add(nombre);
        panel.add(new JLabel("Fecha nacimiento (aaaa-mm-dd):"));
        panel.add(fecha);
        panel.add(new JLabel("Cedula:"));
        panel.add(cedula);
        panel.add(new JLabel("RH:"));
        panel.add(rh);
        panel.add(new JLabel("Peso (kg):"));
        panel.add(peso);
        panel.add(new JLabel("Estatura (m):"));
        panel.add(estatura);

        guardar = new JButton("Guardar");
        consultar = new JButton("Consultar");
        estado = new JLabel(" ");

        JPanel botones = new JPanel();
        botones.add(guardar);
        botones.add(consultar);

        setLayout(new BorderLayout(5, 5));
        add(estado, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        guardar.addActionListener(e -> cargar());
        consultar.addActionListener(e -> new VentanaConsulta().setVisible(true));
    }

    void cargar() {
        try {
            String nom = nombre.getText().trim();
            String fec = fecha.getText().trim();
            String ced = cedula.getText().trim();
            String tipo = (String) rh.getSelectedItem();

            if (nom.isEmpty() || fec.isEmpty() || ced.isEmpty()
                    || peso.getText().trim().isEmpty() || estatura.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos");
                return;
            }

            if (!nom.matches("[\\p{L} ]+")) {
                JOptionPane.showMessageDialog(this, "El nombre no puede tener numeros, puntos ni caracteres especiales");
                return;
            }
            if (nom.length() < 3 || nom.length() > 100) {
                JOptionPane.showMessageDialog(this, "El nombre debe tener entre 3 y 100 letras");
                return;
            }

            LocalDate nacimiento = LocalDate.parse(fec);
            LocalDate hoy = LocalDate.now();
            if (nacimiento.isAfter(hoy)) {
                JOptionPane.showMessageDialog(this, "La fecha de nacimiento no puede ser en el futuro");
                return;
            }
            if (nacimiento.isBefore(hoy.minusYears(120))) {
                JOptionPane.showMessageDialog(this, "La fecha de nacimiento es demasiado antigua, revisa la fecha");
                return;
            }

            if (!ced.matches("[0-9]{6,10}")) {
                JOptionPane.showMessageDialog(this, "La cedula solo debe tener numeros, entre 6 y 10 digitos");
                return;
            }

            double p = Double.parseDouble(peso.getText().trim().replace(",", "."));
            double e = Double.parseDouble(estatura.getText().trim().replace(",", "."));

            if (!Double.isFinite(p) || p < 1 || p > 400) {
                JOptionPane.showMessageDialog(this, "El peso debe estar entre 1 y 400 kg, ejemplo: 70.5");
                return;
            }
            if (!Double.isFinite(e) || e < 0.3 || e > 2.5) {
                JOptionPane.showMessageDialog(this, "La estatura va en metros, entre 0.30 y 2.50, ejemplo: 1.75");
                return;
            }

            double imc = calcularImc(p, e);
            if (imc < 5 || imc > 150) {
                JOptionPane.showMessageDialog(this, "El peso y la estatura no concuerdan, revisa los datos");
                return;
            }

            tabla.cargar(nom, Date.valueOf(nacimiento), ced, tipo, p, e, imc);

            JOptionPane.showMessageDialog(this, "Paciente guardado. IMC: " + String.format("%.2f", imc));
            nombre.setText("");
            fecha.setText("");
            cedula.setText("");
            peso.setText("");
            estatura.setText("");
            estado.setText("Datos guardados.");
        }
        catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Fecha invalida. Usa el formato aaaa-mm-dd, ejemplo: 2001-03-15");
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Peso y estatura deben ser numeros, ejemplo: 70.5 y 1.75");
        }
        catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(this, "Ya existe un paciente con esa cedula");
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
            }
        }
    }

    double calcularImc(double peso, double estatura) {
        return peso / (estatura * estatura);
    }
}