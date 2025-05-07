import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.toedter.calendar.JDateChooser; // Date chooser library

public class BookingForm extends JFrame {
    private JComboBox<String> roomTypeComboBox, durationTypeComboBox;
    private JTextField hoursField, guestsField;
    private JDateChooser checkInDateChooser, checkOutDateChooser;
    private JLabel priceLabel;
    private JButton confirmButton, backButton;
    private BookingManager bookingManager;

    public BookingForm(BookingManager bookingManager) {
        this.bookingManager = bookingManager; // Assign BookingManager instance
        setTitle("Book a Room");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 144, 255)); // Dodger Blue
        headerPanel.setPreferredSize(new Dimension(800, 120));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        add(headerPanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Book Your Room");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        add(formPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Room Type
        formPanel.add(new JLabel("Room Type:"), gbc);
        gbc.gridx = 1;
        roomTypeComboBox = new JComboBox<>(new String[]{"Standard Room", "Deluxe Room"});
        roomTypeComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
        roomTypeComboBox.setToolTipText("Choose the type of room you want to book.");
        roomTypeComboBox.addActionListener(e -> updatePrice());
        formPanel.add(roomTypeComboBox, gbc);

        // Duration Type (Night/Hours)
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Duration Type:"), gbc);
        gbc.gridx = 1;
        durationTypeComboBox = new JComboBox<>(new String[]{"Night(s)", "Hour(s)"});
        durationTypeComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
        durationTypeComboBox.setToolTipText("Choose the duration of your stay (Night(s) or Hour(s)).");
        durationTypeComboBox.addActionListener(e -> updatePrice());
        formPanel.add(durationTypeComboBox, gbc);

        // Number of Nights or Hours
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Duration:"), gbc);
        gbc.gridx = 1;
        hoursField = new JTextField();
        hoursField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        hoursField.setToolTipText("Enter the number of hours or nights you plan to stay.");
        hoursField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updatePrice();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updatePrice();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updatePrice();
            }
        });
        formPanel.add(hoursField, gbc);

        // Check-in Date
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Check-in Date:"), gbc);
        gbc.gridx = 1;
        checkInDateChooser = new JDateChooser();
        checkInDateChooser.setFont(new Font("SansSerif", Font.PLAIN, 18));
        checkInDateChooser.setDateFormatString("yyyy-MM-dd");
        formPanel.add(checkInDateChooser, gbc);

        // Check-out Date (Optional)
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Check-out Date:"), gbc);
        gbc.gridx = 1;
        checkOutDateChooser = new JDateChooser();
        checkOutDateChooser.setFont(new Font("SansSerif", Font.PLAIN, 18));
        checkOutDateChooser.setDateFormatString("yyyy-MM-dd");
        formPanel.add(checkOutDateChooser, gbc);

        // Number of Guests
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Number of Guests:"), gbc);
        gbc.gridx = 1;
        guestsField = new JTextField();
        guestsField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        guestsField.setToolTipText("Enter the number of guests staying.");
        guestsField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updatePrice();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updatePrice();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updatePrice();
            }
        });
        formPanel.add(guestsField, gbc);

        // Price Label
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        priceLabel = new JLabel("Total Price: ₱0");
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        priceLabel.setForeground(new Color(34, 139, 34)); // Green
        formPanel.add(priceLabel, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        add(buttonPanel, BorderLayout.SOUTH);

        confirmButton = createStyledButton("Confirm Booking", new Color(34, 139, 34)); // Green
        confirmButton.addActionListener(e -> {
            String roomType = (String) roomTypeComboBox.getSelectedItem();
            String durationType = (String) durationTypeComboBox.getSelectedItem();
            int duration = Integer.parseInt(hoursField.getText());
            int guests = Integer.parseInt(guestsField.getText());
            Date checkInDate = checkInDateChooser.getDate();

            if (checkInDate == null || duration <= 0 || guests <= 0) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields with valid values.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calculate final price using shared helper method
            double totalPrice = calculateTotalPrice(roomType, durationType, duration, guests);

            int maxGuests = roomType.equals("Standard Room") ? 3 : 6;
            int extraGuests = Math.max(0, guests - maxGuests);
            if (extraGuests > 0) {
                int response = JOptionPane.showConfirmDialog(
                        this,
                        "Maximum guests for " + roomType + " is " + maxGuests + ".\n" +
                                "Extra guests: " + extraGuests + " x ₱1,000\n" +
                                "Do you want to proceed?",
                        "Extra Guest Charges",
                        JOptionPane.YES_NO_OPTION
                );
                if (response != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            String bookingDetails = "Room Type: " + roomType + ", Duration: " + duration + " " + durationType +
                    ", Check-in Date: " + new SimpleDateFormat("yyyy-MM-dd").format(checkInDate) +
                    ", Guests: " + guests + ", Total Price: ₱" + String.format("%.2f", totalPrice);

            bookingManager.addBooking(bookingDetails);

            new PaymentPage(bookingManager, bookingDetails).setVisible(true);
            dispose();
        });
        buttonPanel.add(confirmButton);

        backButton = createStyledButton("Back", new Color(220, 20, 60)); // Red
        backButton.addActionListener(e -> {
            MainMenu mainMenu = new MainMenu(bookingManager);
            mainMenu.setVisible(true);
            dispose();
        });
        buttonPanel.add(backButton);

        setVisible(true);
    }

    private void updatePrice() {
        try {
            String roomType = (String) roomTypeComboBox.getSelectedItem();
            String durationType = (String) durationTypeComboBox.getSelectedItem();
            int duration = Integer.parseInt(hoursField.getText());
            int guests = Integer.parseInt(guestsField.getText());
            double totalPrice = calculateTotalPrice(roomType, durationType, duration, guests);
            priceLabel.setText("Total Price: ₱" + String.format("%.2f", totalPrice));
        } catch (NumberFormatException e) {
            priceLabel.setText("Total Price: ₱0");
        }
    }

    private double calculateTotalPrice(String roomType, String durationType, int duration, int guests) {
        int maxGuests = roomType.equals("Standard Room") ? 3 : 6;
        int extraGuests = Math.max(0, guests - maxGuests);
        double pricePerHour = roomType.equals("Standard Room") ? 1000.0 / 24 : 2300.0 / 24;
        double basePrice = durationType.equals("Hour(s)") ? duration * pricePerHour : duration * (pricePerHour * 24);
        return basePrice + (extraGuests * 1000); // Include extra guest charges
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
}
