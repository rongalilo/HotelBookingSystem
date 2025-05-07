import javax.swing.*;
import java.util.ArrayList;

// Booking Manager Class
// Handles the booking process and maintains booking history.
public class BookingManager {
    private ArrayList<String> bookingHistory; // List to store booking history

    public BookingManager() {
        bookingHistory = new ArrayList<>();
    }

    // Add a booking to the history
    public void addBooking(String record) {
        bookingHistory.add(record);
    }

    // Display all booking history
    public void showBookingHistory() {
        if (bookingHistory.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "No bookings found!", 
                "Booking History", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder history = new StringBuilder("Booking History:\n");
            for (String record : bookingHistory) {
                history.append(record).append("\n");
            }
            JOptionPane.showMessageDialog(null, 
                history.toString(), 
                "Booking History", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}