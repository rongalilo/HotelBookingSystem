import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentPage extends JFrame {
    private BookingManager bookingManager;
    private String bookingDetails;

    public PaymentPage(BookingManager manager, String bookingDetails) {
        this.bookingManager = manager;
        this.bookingDetails = bookingDetails;

        setTitle("Payment Options");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Header
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Payment Options
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

        // Payment buttons
        JButton cashButton = createPaymentButton("Cash", e -> handleCashPayment());
        JButton debitButton = createPaymentButton("Debit Card", e -> handleCardPayment("Debit Card"));
        JButton creditButton = createPaymentButton("Credit Card", e -> handleCardPayment("Credit Card"));

        // Add buttons to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(cashButton, gbc);
        gbc.gridy++;
        panel.add(debitButton, gbc);
        gbc.gridy++;
        panel.add(creditButton, gbc);

        return panel;
    }

    /**
     * Creates a payment button with the specified text and action.
     * @param text The button text.
     * @param action The action listener for the button.
     * @return A JButton object.
     */
    private JButton createPaymentButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.addActionListener(action); // Add the provided action listener
        return button;
    }

    private void handleCashPayment() {
        double totalPrice = calculateTotalPrice();
        JOptionPane.showMessageDialog(this,
                "Your cash payment of ₱" + totalPrice + " will be collected at check-in.\nThank you!",
                "Cash Payment",
                JOptionPane.INFORMATION_MESSAGE);
        redirectToMainMenu();
    }

    private void handleCardPayment(String method) {
        double totalPrice = calculateTotalPrice();
        String cardNumber = JOptionPane.showInputDialog(this,
                "Enter your " + method + " Number:\nTotal Price: ₱" + totalPrice,
                method + " Payment",
                JOptionPane.PLAIN_MESSAGE);

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

        generateReceipt(method, cardNumber, totalPrice);
    }

    private void generateReceipt(String paymentMethod, String cardNumber, double totalPrice) {
        String receipt = createReceipt(paymentMethod, cardNumber, totalPrice);

        JTextArea receiptArea = new JTextArea(receipt);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        receiptArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Payment Receipt", JOptionPane.INFORMATION_MESSAGE);

        redirectToMainMenu();
    }

    private String createReceipt(String paymentMethod, String cardNumber, double totalPrice) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = formatter.format(new Date());

        String maskedCard = maskCardNumber(cardNumber);

        return "-------------------------------\n" +
               "           SuGO Hotel          \n" +
               "-------------------------------\n" +
               "Booking Details:\n" + bookingDetails + "\n" +
               "-------------------------------\n" +
               "Total Price: ₱" + totalPrice + "\n" +
               "Payment Method: " + paymentMethod + "\n" +
               "Card Number: " + maskedCard + "\n" +
               "Transaction Date: " + dateTime + "\n" +
               "-------------------------------\n" +
               "Thank you for booking with us!\n" +
               "We look forward to hosting you.\n" +
               "-------------------------------";
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() >= 4) {
            return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
        } else {
            return "Invalid Card Number";
        }
    }

    private double calculateTotalPrice() {
        String[] details = bookingDetails.split(", ");
        String roomType = details[0].split(": ")[1];
        int nights = Integer.parseInt(details[1].split(": ")[1]);

        double pricePerNight = roomType.equals("Standard") ? 1000 : 2300;
        return pricePerNight * nights;
    }

    private void redirectToMainMenu() {
        new MainMenu(bookingManager).setVisible(true);
        dispose();
    }
}