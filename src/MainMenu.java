import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Main Menu Class
// Provides navigation options for the user.
public class MainMenu extends JFrame {
    private JButton bookButton, viewButton, historyButton, exitButton;
    private BookingManager bookingManager;

    public MainMenu(BookingManager manager) {
        this.bookingManager = manager; // Assign BookingManager instance
        setTitle("SuGO - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 144, 255)); // Dodger Blue
        headerPanel.setPreferredSize(new Dimension(800, 80));
        add(headerPanel, BorderLayout.NORTH);

        JLabel logoLabel = new JLabel("SuGO");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 32));
        logoLabel.setForeground(Color.WHITE);
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        add(buttonPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        bookButton = new JButton("Book a Room");
        buttonPanel.add(bookButton, gbc);

        gbc.gridy++;
        viewButton = new JButton("View Room Types");
        buttonPanel.add(viewButton, gbc);

        gbc.gridy++;
        historyButton = new JButton("View Booking History");
        buttonPanel.add(historyButton, gbc);

        gbc.gridy++;
        exitButton = new JButton("Exit");
        buttonPanel.add(exitButton, gbc);

        // Button Actions
        bookButton.addActionListener(e -> {
            BookingForm bookingForm = new BookingForm(bookingManager);
            bookingForm.setVisible(true);
            dispose(); // Close current MainMenu window
        });

        viewButton.addActionListener(e -> {
            String roomInfo = "<html><b>Available Room Types:</b><br>"
                    + "- Standard Room: ₱1000/night<br>"
                    + "- Deluxe Room: ₱2300/night</html>";
            JOptionPane.showMessageDialog(this, roomInfo, "Room Types", JOptionPane.INFORMATION_MESSAGE);
        });

        historyButton.addActionListener(e -> bookingManager.showBookingHistory());

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to exit?", 
                "Exit Confirmation", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0); // Exit the program
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        BookingManager manager = new BookingManager(); // Initialize BookingManager
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu(manager);
            menu.setVisible(true);
        });
    }
}