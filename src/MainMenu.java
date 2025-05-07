import javax.swing.*;
import java.awt.*;

// Main Menu Class
// Provides navigation options for the user.
public class MainMenu extends JFrame {
    private JButton bookButton, viewButton, historyButton, exitButton;
    private BookingManager bookingManager;

    public MainMenu(BookingManager bookingManager) {
        this.bookingManager = bookingManager; // Assign BookingManager instance
        setTitle("SuGO - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        headerPanel.setBackground(new Color(30, 144, 255)); // Dodger Blue
        headerPanel.setPreferredSize(new Dimension(800, 100));
        add(headerPanel, BorderLayout.NORTH);

        JLabel logoLabel = new JLabel("Welcome to SuGO Hotel Booking");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 36));
        logoLabel.setForeground(Color.WHITE);
        headerPanel.add(logoLabel);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        add(buttonPanel, BorderLayout.CENTER);

        Dimension buttonSize = new Dimension(250, 50);
        Font buttonFont = new Font("Arial", Font.BOLD, 20);

        bookButton = createStyledButton("Book a Room", buttonSize, buttonFont);
        viewButton = createStyledButton("View Room Types", buttonSize, buttonFont);
        historyButton = createStyledButton("View Booking History", buttonSize, buttonFont);
        exitButton = createStyledButton("Exit", buttonSize, buttonFont);

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(bookButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(viewButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(historyButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalGlue());

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 240, 240)); // Light Gray
        footerPanel.setPreferredSize(new Dimension(800, 50));
        add(footerPanel, BorderLayout.SOUTH);

        JLabel footerLabel = new JLabel("© 2025 SuGO Hotel Booking. All rights reserved.");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        footerLabel.setForeground(Color.DARK_GRAY);
        footerPanel.add(footerLabel);

        // Button Actions
        bookButton.addActionListener(e -> {
            BookingForm bookingForm = new BookingForm(bookingManager);
            bookingForm.setVisible(true);
            dispose();
        });

        viewButton.addActionListener(e -> {
            String roomInfo = "<html><b>Available Room Types:</b><br>"
                    + "- Standard Room: ₱1000/night<br>"
                    + "- Deluxe Room: ₱2300/night</html>";
            JOptionPane.showMessageDialog(this, roomInfo, "Room Types", JOptionPane.INFORMATION_MESSAGE);
        });

        historyButton.addActionListener(e -> {
            BookingHistory bookingHistory = new BookingHistory(bookingManager);
            bookingHistory.setVisible(true);
            dispose();
        });

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    // Helper method to create a styled button
    private JButton createStyledButton(String text, Dimension size, Font font) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBackground(new Color(30, 144, 255));
        button.setForeground(Color.WHITE);
        return button;
    }

    public static void main(String[] args) {
        BookingManager bookingManager = new BookingManager();
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu(bookingManager);
            menu.setVisible(true);
        });
    }
}
