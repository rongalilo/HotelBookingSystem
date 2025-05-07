import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BookingHistory extends JFrame {
    private JPanel headerPanel, historyPanel;
    private JLabel titleLabel;
    private JButton backButton;
    private BookingManager bookingManager; // Add BookingManager field

    public BookingHistory(BookingManager manager) {
        this.bookingManager = manager; // Assign the BookingManager instance
        setTitle("Booking History");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Header Panel with Gradient Background
        headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color startColor = new Color(30, 144, 255); // Dodger Blue
                Color endColor = new Color(0, 102, 204); // Darker Blue
                GradientPaint gradient = new GradientPaint(0, 0, startColor, getWidth(), getHeight(), endColor);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(800, 120));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 35));
        add(headerPanel, BorderLayout.NORTH);

        titleLabel = new JLabel("Your Booking History");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // History Panel with Card Design
        historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground(Color.WHITE);

        ArrayList<String> bookings = bookingManager.getBookingRecords(); // Fetch booking records
        if (bookings.isEmpty()) {
            JLabel emptyLabel = new JLabel("No bookings found!");
            emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyLabel.setForeground(Color.GRAY);
            historyPanel.add(emptyLabel);
        } else {
            for (String record : bookings) {
                JPanel card = createBookingCard(record); // Create a card for each booking
                historyPanel.add(card);
                historyPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between cards
            }
        }

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(historyPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Back Button with Hover Effect
        backButton = createStyledButton("Back to Main Menu", new Color(30, 144, 255));
        backButton.addActionListener(e -> {
            MainMenu mainMenu = new MainMenu(bookingManager); // Pass bookingManager to MainMenu
            mainMenu.setVisible(true);
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Helper method to create a styled button
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    // Helper method to create a booking card
    private JPanel createBookingCard(String bookingDetails) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel bookingLabel = new JLabel("<html>" + bookingDetails.replace("\n", "<br>") + "</html>");
        bookingLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        bookingLabel.setForeground(Color.DARK_GRAY);

        card.add(bookingLabel, BorderLayout.CENTER);

        return card;
    }
}
