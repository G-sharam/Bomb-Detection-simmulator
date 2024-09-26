import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class login1 extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public login1() {
        setTitle("Login Page");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 5, 5));
        
        // Set background color (white mixed with blue)
        panel.setBackground(new Color(173, 216, 230)); // Light blue color

        Font h1Font = new Font("SansSerif", Font.BOLD, 24);
        Font inputFont = new Font("SansSerif", Font.ITALIC, 24); // Italic font for input fields
        Font buttonFont = new Font("SansSerif", Font.ITALIC, 24); // Italic font for buttons

        JLabel emailLabel = new JLabel("Email ID:");
        emailLabel.setFont(h1Font);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(inputFont);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(h1Font);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(inputFont);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setFont(buttonFont);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateLogin()) {
                    new simulator(); // Assuming NextPage is the next page after successful login
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(login1.this, "Invalid email or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(new JLabel()); // Placeholder
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setFont(buttonFont);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new regi1();
                dispose(); // Close the current login frame
            }
        });
        panel.add(new JLabel()); // Placeholder
        panel.add(registerButton);

        getContentPane().add(panel);
        setVisible(true);
    }

    private boolean validateLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        try (BufferedReader reader = new BufferedReader(new FileReader("userDetails.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equals(email) && details[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new login1());
    }
}
