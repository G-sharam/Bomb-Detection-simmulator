import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class simulator extends JFrame {

    private JTextField explosionField;
    private JTextField longitudeField;
    private JTextField latitudeField;
    private JTextField healthField;
    private JButton detectButton;
    private Font h1Font;
    private double explosionLevel;
    private double longitude;
    private double latitude;

    public simulator() {
        setTitle("Bomb Sensor Simulator");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 5, 5));

        Color mixedColor = new Color(173, 216, 230);
        panel.setBackground(mixedColor);

        h1Font = new Font("SansSerif", Font.BOLD, 24);

        // Create and configure labels and text fields
        JLabel explosionLabel = new JLabel("Explosion Level:");
        configureLabel(explosionLabel);

        explosionField = createTextField();

        JLabel longitudeLabel = new JLabel("Longitude:");
        configureLabel(longitudeLabel);

        longitudeField = createTextField();

        JLabel latitudeLabel = new JLabel("Latitude:");
        configureLabel(latitudeLabel);

        latitudeField = createTextField();

        JLabel healthLabel = new JLabel("Health Status:");
        configureLabel(healthLabel);

        healthField = createTextField();

        detectButton = new JButton("Detect Explosion");
        detectButton.setFont(h1Font);
        detectButton.setBackground(mixedColor);
        detectButton.setForeground(Color.BLACK);
        detectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detectExplosion();
            }

            private void detectExplosion() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'detectExplosion'");
            }
        });

        // Add components to panel
        panel.add(explosionLabel);
        panel.add(explosionField);
        panel.add(longitudeLabel);
        panel.add(longitudeField);
        panel.add(latitudeLabel);
        panel.add(latitudeField);
        panel.add(healthLabel);
        panel.add(healthField);
        panel.add(new JLabel()); // Placeholder
        panel.add(detectButton);

        // Add panel to frame
        getContentPane().add(panel);
        setVisible(true);

        // Initialize data and start timer
        initializeStaticData();
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateHealthStatus();
            }
        });
        timer.start();
    }

    private void configureLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(h1Font);
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        textField.setFont(h1Font);
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    private void initializeStaticData() {
        explosionLevel = Math.random() * 100;
        longitude = 72.6697 + (Math.random() * 0.1);
        latitude = 26.2389 + (Math.random() * 0.1);

        explosionField.setText(String.format("%.2f", explosionLevel));
        longitudeField.setText(String.format("%.4f", longitude));
        latitudeField.setText(String.format("%.4f", latitude));
    }

    private void updateHealthStatus() {
        int healthStatus = (Math.random() > 0.5) ? 1 : 0;
        healthField.setText(healthStatus == 1 ? "Good" : "Bad");
        sendData(healthStatus);
    }

    private void sendData(int healthStatus) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");
            String message = String.format("%.2f,%.4f,%.4f,%d", explosionLevel, longitude, latitude, healthStatus);
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 12345);
            socket.send(packet);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new simulator();
            }
        });
    }
}
