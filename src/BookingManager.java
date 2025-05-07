import java.util.ArrayList;

public class BookingManager {
    private ArrayList<String> bookingRecords;

    public BookingManager() {
        bookingRecords = new ArrayList<>();
    }

    public void addBooking(String bookingDetails) {
        bookingRecords.add(bookingDetails);
    }

    public ArrayList<String> getBookingRecords() {
        return bookingRecords;
    }
}
