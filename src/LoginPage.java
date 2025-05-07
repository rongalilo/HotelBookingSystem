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
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 144, 255)); // Dodger Blue
        headerPanel.setPreferredSize(new Dimension(800, 120));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 35));
        add(headerPanel, BorderLayout.NORTH);

        titleLabel = new JLabel("Login to SuGO Hotel Booking");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
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
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 20));
        formPanel.add(loginButton, gbc);

        gbc.gridy++;
        signupButton = new JButton("Don't have an account? Sign Up");
        signupButton.setFont(new Font("Arial", Font.PLAIN, 16));
        signupButton.setForeground(new Color(30, 144, 255));
        signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
            SignupPage signup = new SignupPage(); // Reference to SignupPage (assuming it's implemented)
            signup.setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPage login = new LoginPage();
            login.setVisible(true);
        });
    }
}