import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BookingHistory extends JFrame {
    private JPanel headerPanel, historyPanel;
    private JLabel titleLabel;
    private JButton backButton;
    private static ArrayList<String> bookingRecords = new ArrayList<>();
    private BookingManager bookingManager; // Add BookingManager field

    public BookingHistory(BookingManager manager) {
        this.bookingManager = manager; // Assign the BookingManager instance
        setTitle("Booking History");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Header Panel
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 144, 255));
        headerPanel.setPreferredSize(new Dimension(800, 120));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 35));
        add(headerPanel, BorderLayout.NORTH);

        titleLabel = new JLabel("Your Booking History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // History Panel
        historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground(Color.WHITE);

        if (bookingRecords.isEmpty()) {
            JLabel emptyLabel = new JLabel("No bookings found!");
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 24));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            historyPanel.add(emptyLabel);
        } else {
            for (String record : bookingRecords) {
                JLabel recordLabel = new JLabel(record);
                recordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                historyPanel.add(recordLabel);
            }
        }

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(historyPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Back Button
        backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            MainMenu mainMenu = new MainMenu(bookingManager); // Pass bookingManager to MainMenu
            mainMenu.setVisible(true);
            dispose();
        });
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void addBookingRecord(String record) {
        bookingRecords.add(record);
    }

    public static void main(String[] args) {
        BookingManager manager = new BookingManager(); // Create BookingManager instance
        SwingUtilities.invokeLater(() -> new BookingHistory(manager));
    }
}