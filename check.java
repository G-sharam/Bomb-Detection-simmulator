import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class check extends JFrame {

    private JTextField explosionField;
    private JTextField longitudeField;
    private JTextField latitudeField;
    private JButton healthButton;

    public check() {
        setTitle("Command Simulator");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 5, 5));
        panel.setBackground(new Color(173, 216, 230));

        Font h1Font = new Font("Arial", Font.BOLD, 24);
        Font textFieldFont = new Font("Arial", Font.ITALIC, 24);

        JLabel explosionLabel = new JLabel("Explosion Level:");
        explosionLabel.setForeground(Color.WHITE);
        explosionLabel.setFont(h1Font);

        explosionField = new JTextField();
        explosionField.setEditable(false);
        explosionField.setForeground(Color.BLACK);
        explosionField.setBackground(Color.WHITE);
        explosionField.setFont(textFieldFont);
        explosionField.setBorder(BorderFactory.createCompoundBorder(
                explosionField.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel longitudeLabel = new JLabel("Longitude:");
        longitudeLabel.setForeground(Color.WHITE);
        longitudeLabel.setFont(h1Font);

        longitudeField = new JTextField();
        longitudeField.setEditable(false);
        longitudeField.setForeground(Color.BLACK);
        longitudeField.setBackground(Color.WHITE);
        longitudeField.setFont(textFieldFont);
        longitudeField.setBorder(BorderFactory.createCompoundBorder(
                longitudeField.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel latitudeLabel = new JLabel("Latitude:");
        latitudeLabel.setForeground(Color.WHITE);
        latitudeLabel.setFont(h1Font);

        latitudeField = new JTextField();
        latitudeField.setEditable(false);
        latitudeField.setForeground(Color.BLACK);
        latitudeField.setBackground(Color.WHITE);
        latitudeField.setFont(textFieldFont);
        latitudeField.setBorder(BorderFactory.createCompoundBorder(
                latitudeField.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel healthLabel = new JLabel("Health Status:");
        healthLabel.setForeground(Color.WHITE);
        healthLabel.setFont(h1Font);

        healthButton = new JButton();
        healthButton.setPreferredSize(new Dimension(60, 30));
        healthButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        healthButton.setEnabled(false);

        panel.add(explosionLabel);
        panel.add(explosionField);
        panel.add(longitudeLabel);
        panel.add(longitudeField);
        panel.add(latitudeLabel);
        panel.add(latitudeField);
        panel.add(healthLabel);
        panel.add(healthButton);
        panel.add(new JLabel()); // Placeholder
        panel.add(new JLabel()); // Placeholder

        getContentPane().add(panel);
        setVisible(true);

        new Thread(new UDPServer()).start();
    }

    private class UDPServer implements Runnable {
        @Override
        public void run() {
            try (DatagramSocket socket = new DatagramSocket(12345)) {
                byte[] buffer = new byte[1024];

                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    String[] parts = message.split(",");
                    if (parts.length == 4) {
                        double explosionLevel = Double.parseDouble(parts[0]);
                        double longitude = Double.parseDouble(parts[1]);
                        double latitude = Double.parseDouble(parts[2]);
                        int healthStatus = Integer.parseInt(parts[3]);

                        SwingUtilities.invokeLater(() -> {
                            explosionField.setText(String.format("%.2f", explosionLevel));
                            longitudeField.setText(String.format("%.4f", longitude));
                            latitudeField.setText(String.format("%.4f", latitude));
                            updateHealthStatus(healthStatus);
                        });
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateHealthStatus(int healthStatus) {
        if (healthStatus == 1) {
            healthButton.setBackground(Color.GREEN);
            healthButton.setText("Good");
            healthButton.setToolTipText("Good");
        } else {
            healthButton.setBackground(Color.RED);
            healthButton.setText("Bad");
            healthButton.setToolTipText("Bad");
            JOptionPane.showMessageDialog(null, "Bad Health Detected!", "Health Alert", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new check();
            }
        });
    }
}
