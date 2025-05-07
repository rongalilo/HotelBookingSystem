import javax.swing.*;
import java.awt.*;

public class BookingForm extends JFrame {
    private BookingManager bookingManager;

    public BookingForm(BookingManager manager) {
        this.bookingManager = manager;
        setTitle("Hotel Booking - Booking Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Header
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Form
        add(createFormPanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 35));
        header.setBackground(new Color(30, 144, 255));
        JLabel title = new JLabel("Complete Your Booking");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        header.add(title);
        return header;
    }

    private JPanel createFormPanel() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Room Type
        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Room Type:"), gbc);
        JComboBox<String> roomTypeBox = new JComboBox<>(new String[]{"Standard", "Deluxe"});
        gbc.gridx = 1;
        form.add(roomTypeBox, gbc);

        // Number of Nights
        gbc.gridx = 0;
        gbc.gridy++;
        form.add(new JLabel("Number of Nights:"), gbc);
        JSpinner nightsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        gbc.gridx = 1;
        form.add(nightsSpinner, gbc);

        // Number of Guests
        gbc.gridx = 0;
        gbc.gridy++;
        form.add(new JLabel("Number of Guests:"), gbc);
        JSpinner guestSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        gbc.gridx = 1;
        form.add(guestSpinner, gbc);

        // Confirm Button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton confirmButton = new JButton("Confirm Booking");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 24));
        confirmButton.addActionListener(e -> {
            String details = String.format("Room: %s, Nights: %d, Guests: %d",
                    roomTypeBox.getSelectedItem(), nightsSpinner.getValue(), guestSpinner.getValue());
            bookingManager.addBooking(details);
            new PaymentPage(bookingManager, details);
            dispose();
        });
        form.add(confirmButton, gbc);

        return form;
    }
}
