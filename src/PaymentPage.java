import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentPage extends JFrame {
    private BookingManager bookingManager;
    private String bookingDetails;
    private double totalPrice; // Store the total price passed from BookingForm

    public PaymentPage(BookingManager manager, String bookingDetails) {
        this.bookingManager = manager;
        this.bookingDetails = bookingDetails;

        // Extract and store the total price from the booking details
        this.totalPrice = extractTotalPrice(bookingDetails);

        setTitle("Payment Options");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Add header and payment options
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createOptionsPanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 35));
        header.setBackground(new Color(34, 139, 34)); // Forest Green
        JLabel title = new JLabel("Complete Your Payment");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        header.add(title);
        return header;
    }

    private JPanel createOptionsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add payment buttons
        JButton cashButton = createPaymentButton("Cash", e -> handleCashPayment());
        JButton debitButton = createPaymentButton("Debit Card", e -> handleCardPayment("Debit Card"));
        JButton creditButton = createPaymentButton("Credit Card", e -> handleCardPayment("Credit Card"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(cashButton, gbc);
        gbc.gridy++;
        panel.add(debitButton, gbc);
        gbc.gridy++;
        panel.add(creditButton, gbc);

        return panel;
    }

    private JButton createPaymentButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBackground(new Color(30, 144, 255)); // Dodger Blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 102, 204)); // Darker Blue
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 144, 255)); // Dodger Blue
            }
        });

        return button;
    }

    private void handleCashPayment() {
        JOptionPane.showMessageDialog(this,
                "Your cash payment of ₱" + String.format("%.2f", totalPrice) + " will be collected at check-in.\nThank you!",
                "Cash Payment",
                JOptionPane.INFORMATION_MESSAGE);
        displayReceipt("Cash", null);
    }

    private void handleCardPayment(String method) {
        String cardNumber = JOptionPane.showInputDialog(this,
                "Enter your " + method + " Number:\nTotal Price: ₱" + String.format("%.2f", totalPrice),
                method + " Payment",
                JOptionPane.PLAIN_MESSAGE);

        // Validate card number input
        if (cardNumber == null || cardNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Payment canceled. Please provide a valid card number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Payment successful using " + method + "!\nGenerating receipt...",
                "Payment Confirmation",
                JOptionPane.INFORMATION_MESSAGE);

        displayReceipt(method, cardNumber);
    }

    private void displayReceipt(String paymentMethod, String cardNumber) {
        // Generate receipt and save it to the booking history
        String receipt = createReceipt(paymentMethod, cardNumber);
        saveReceipt(receipt);

        // Display receipt in a scrollable text pane
        JTextPane receiptPane = new JTextPane();
        receiptPane.setContentType("text/html");
        receiptPane.setText(receipt);
        receiptPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(receiptPane);
        scrollPane.setPreferredSize(new Dimension(600, 500));

        JOptionPane.showMessageDialog(this, scrollPane, "Payment Receipt", JOptionPane.INFORMATION_MESSAGE);

        redirectToMainMenu();
    }

    private void saveReceipt(String receipt) {
        bookingManager.addBooking(receipt); // Save the receipt to booking history
    }

    private String createReceipt(String paymentMethod, String cardNumber) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = formatter.format(new Date());

        String maskedCard = cardNumber != null && cardNumber.length() >= 4
                ? "**** **** **** " + cardNumber.substring(cardNumber.length() - 4)
                : "N/A";

        return "<html>" +
               "<body style='font-family:Arial,sans-serif;'>" +
               "<h1 style='text-align:center;color:#2E8B57;'>SuGO Hotel Receipt</h1>" +
               "<hr>" +
               "<p><b>Booking Details:</b><br>" + bookingDetails.replace(", ", "<br>") + "</p>" +
               "<hr>" +
               "<p><b>Total Price:</b> ₱" + String.format("%.2f", totalPrice) + "</p>" +
               "<p><b>Payment Method:</b> " + paymentMethod + "</p>" +
               "<p><b>Card Number:</b> " + maskedCard + "</p>" +
               "<p><b>Transaction Date:</b> " + dateTime + "</p>" +
               "<hr>" +
               "<p style='text-align:center;'>Thank you for booking with SuGO Hotel!<br>We look forward to hosting you.</p>" +
               "</body>" +
               "</html>";
    }

    private double extractTotalPrice(String bookingDetails) {
        try {
            String[] details = bookingDetails.split(", ");
            String totalPriceString = details[details.length - 1].split(": ")[1].replace("₱", "");
            return Double.parseDouble(totalPriceString);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error extracting total price. Please check booking details.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return 0.0;
        }
    }

    private void redirectToMainMenu() {
        new MainMenu(bookingManager).setVisible(true);
        dispose();
    }
}
