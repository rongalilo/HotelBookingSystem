import javax.swing.*;
import java.awt.*;

// Login Page Class
// Handles user login and redirects to the MainMenu or SignupPage.
public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton;
    private JLabel titleLabel;
    private JPanel headerPanel, formPanel;

    // Simulated database credentials
    private final String REG_USERNAME = "sugoUser"; // Example username
    private final String REG_PASSWORD = "SugoPass123"; // Example password

    public LoginPage() {
        setTitle("Hotel Booking - Login");
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

        titleLabel = new JLabel("Login to SuGO Hotel Booking");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Form Panel
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        add(formPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

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
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = createStyledButton("Login", new Color(34, 139, 34)); // Green
        formPanel.add(loginButton, gbc);

        gbc.gridy++;
        signupButton = createStyledButton("Don't have an account? Sign Up", new Color(30, 144, 255)); // Blue
        signupButton.setForeground(Color.BLUE);
        signupButton.setBorderPainted(false);
        signupButton.setContentAreaFilled(false);
        formPanel.add(signupButton, gbc);

        // Actions
        loginButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if (user.equals(REG_USERNAME) && pass.equals(REG_PASSWORD)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                BookingManager bookingManager = new BookingManager(); // Create BookingManager instance
                MainMenu menu = new MainMenu(bookingManager); // Pass BookingManager to MainMenu
                menu.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.\nHint: Username: sugoUser, Password: SugoPass123", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        signupButton.addActionListener(e -> {
            SignupPage signup = new SignupPage(); // Reference to SignupPage
            signup.setVisible(true);
            dispose();
        });

        setVisible(true);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPage login = new LoginPage();
            login.setVisible(true);
        });
    }
}
