import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class resi1 extends JFrame {

    private JTextField firstNameField, lastNameField, ageField, mobileField, emailField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private boolean isBlue = true;

    private JFrame loginFrame;

    public resi1(JFrame loginFrame) {
        this.loginFrame = loginFrame;
        setTitle("resi1 Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 5, 5));
        panel.setBackground(new Color(173, 216, 230)); // Set panel background to blue mixed with white

        // Custom font for labels and text fields
        Font h1Font = new Font("SansSerif", Font.BOLD, 24);
        Font italicFont = new Font("SansSerif", Font.ITALIC, 24); // H1 size in italic

        JLabel firstNameLabel = createLabel("First Name:", h1Font);
        firstNameField = createTextField(italicFont, Color.WHITE); // Changed text color to white

        JLabel lastNameLabel = createLabel("Last Name:", h1Font);
        lastNameField = createTextField(italicFont, Color.WHITE); // Changed text color to white

        JLabel ageLabel = createLabel("Age:", h1Font);
        ageField = createTextField(italicFont, Color.WHITE); // Changed text color to white

        JLabel mobileLabel = createLabel("Mobile No:", h1Font);
        mobileField = createTextField(italicFont, Color.WHITE); // Changed text color to white

        JLabel emailLabel = createLabel("Email ID:", h1Font);
        emailField = createTextField(italicFont, Color.WHITE); // Changed text color to white

        JLabel passwordLabel = createLabel("Password:", h1Font);
        passwordField = new JPasswordField();
        passwordField.setBackground(Color.WHITE); // Set background color of password field to white
        passwordField.setForeground(Color.BLACK); // Set font color of password field to black
        passwordField.setFont(italicFont);
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(mobileLabel);
        panel.add(mobileField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        submitButton = new JButton("Submit");
        submitButton.setFont(h1Font); // Make button text larger
        updateButtonColor();
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
                isBlue = !isBlue; // Toggle button color between blue and white
                updateButtonColor();
            }

            private void register() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'register'");
            }
        });
        panel.add(new JLabel()); // Placeholder
        panel.add(submitButton);

        getContentPane().add(panel);
        setVisible(true);
    }

    private void updateButtonColor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateButtonColor'");
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(font);
        return label;
    }

    private JTextField createTextField(Font font, Color textColor) { // Add textColor parameter
        JTextField textField = new JTextField();
        textField.setBackground(Color.WHITE); // Set background color of text fields to white
        textField.setForeground(textColor); // Set font color
        return textField;
    }
}
