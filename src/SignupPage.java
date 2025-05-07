import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class SignupPage extends JFrame {
    private JTextField fullNameField, emailField, usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JCheckBox termsCheckBox;
    private JButton signupButton, backButton;
    private JLabel titleLabel;
    private JPanel headerPanel, formPanel;

    public SignupPage() {
        setTitle("Hotel Booking - Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Header
        headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(30, 144, 255);
                Color color2 = new Color(0, 102, 204);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(800, 120));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 35));
        add(headerPanel, BorderLayout.NORTH);

        titleLabel = new JLabel("Create a New SuGO Account");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Form Panel
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        add(formPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        fullNameField = new JTextField(20);
        formPanel.add(fullNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Email Address:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(20);
        formPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        termsCheckBox = new JCheckBox("I agree to the Terms and Conditions.");
        termsCheckBox.setBackground(Color.WHITE);
        formPanel.add(termsCheckBox, gbc);

        gbc.gridy++;
        signupButton = createStyledButton("Sign Up", new Color(34, 139, 34)); // Green
        formPanel.add(signupButton, gbc);

        gbc.gridy++;
        backButton = createStyledButton("Back to Login", new Color(220, 20, 60)); // Red
        formPanel.add(backButton, gbc);

        // Signup button validation
        signupButton.addActionListener(e -> {
            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (username.length() < 5) {
                JOptionPane.showMessageDialog(this, "Username must be at least 5 characters.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(this, "Password must be at least 8 characters, include uppercase, lowercase, and number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!termsCheckBox.isSelected()) {
                JOptionPane.showMessageDialog(this, "You must agree to the terms and conditions.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Simulated success (we don't actually save)
            JOptionPane.showMessageDialog(this, "Account created successfully!\nUse username: sugoUser\nPassword: SugoPass123");
            LoginPage login = new LoginPage();
            login.setVisible(true);
            dispose();
        });

        backButton.addActionListener(e -> {
            LoginPage login = new LoginPage();
            login.setVisible(true);
            dispose();
        });
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private boolean isValidEmail(String email) {
        return Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email);
    }

    private boolean isValidPassword(String password) {
        return Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", password);
    }
}
