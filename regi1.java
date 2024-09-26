import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class regi1 extends JFrame {

    private JTextField firstNameField, lastNameField, ageField, mobileField, emailField;
    private JPasswordField passwordField;
    private JButton submitButton, backButton;
    private boolean isBlue = true;

    public regi1() {
        setTitle("Registration Form");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 5, 5));
        panel.setBackground(new Color(173, 216, 230)); // Set panel background to blue mixed with white

        // Custom font for labels and text fields
        Font h1Font = new Font("SansSerif", Font.BOLD, 24);
        Font italicFont = new Font("SansSerif", Font.ITALIC, 18); // H1 size in italigaurc

        JLabel firstNameLabel = createLabel("First Name:", h1Font);
        firstNameField = createTextField(italicFont, Color.BLACK); // Set text color to black

        JLabel lastNameLabel = createLabel("Last Name:", h1Font);
        lastNameField = createTextField(italicFont, Color.BLACK); // Set text color to black

        JLabel ageLabel = createLabel("Age:", h1Font);
        ageField = createTextField(italicFont, Color.BLACK); // Set text color to black

        JLabel mobileLabel = createLabel("Mobile No:", h1Font);
        mobileField = createTextField(italicFont, Color.BLACK); // Set text color to black

        JLabel emailLabel = createLabel("Email ID:", h1Font);
        emailField = createTextField(italicFont, Color.BLACK); // Set text color to black

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
                if (validateFields()) {
                    register();
                    isBlue = !isBlue; // Toggle button color between blue and white
                    updateButtonColor();
                } else {
                    JOptionPane.showMessageDialog(regi1.this, "Please ensure all fields are correctly filled!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(new JLabel()); // Placeholder
        panel.add(submitButton);

        backButton = new JButton("Back to Login");
        backButton.setFont(h1Font);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new login1();
                dispose(); // Close the current registration frame
            }
        });
        panel.add(new JLabel()); // Placeholder
        panel.add(backButton);

        getContentPane().add(panel);
        setVisible(true);
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
        textField.setForeground(textColor); // Set font color of text fields
        textField.setFont(font);
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    private void register() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String age = ageField.getText();
        String mobile = mobileField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        // Saving the registration data to a file
        try (FileWriter writer = new FileWriter("userDetails.txt", true)) {
            writer.write(email + "," + password + "\n"); // Save only email and password for simplicity
            writer.flush();
            JOptionPane.showMessageDialog(this, "Registration Successful!");
            clearFields();
            new login1();
            dispose();
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while saving registration data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        ageField.setText("");
        mobileField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }

    private boolean validateFields() {
        String ageText = ageField.getText();
        String mobileText = mobileField.getText();
        String emailText = emailField.getText();

        // Validate all fields are filled
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || 
            ageText.isEmpty() || mobileText.isEmpty() || emailText.isEmpty() ||
            passwordField.getPassword().length == 0) {
            return false;
        }

        // Validate age is an integer and has at most 2 digits
        try {
            int age = Integer.parseInt(ageText);
            if (age < 0 || age > 99) {
                JOptionPane.showMessageDialog(this, "Age must be a positive integer with at most 2 digits.", "Invalid Age", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age must be a valid integer.", "Invalid Age", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate mobile number is exactly 10 digits
        if (!mobileText.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Mobile number must be exactly 10 digits.", "Invalid Mobile Number", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate email contains @ sign
        if (!emailText.contains("@")) {
            JOptionPane.showMessageDialog(this, "Email ID must contain '@' sign.", "Invalid Email ID", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void updateButtonColor() {
        if (isBlue) {
            submitButton.setBackground(new Color(0, 191, 255)); // Set button color to blue
        } else {
            submitButton.setBackground(Color.WHITE); // Set button color to white
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new regi1());
    }
}


//lambda task - his lambda expression is passed as an argument to specifying the task to be execute
    