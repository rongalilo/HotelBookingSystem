// Deluxe Room Class
// Implements room pricing for deluxe rooms with additional luxury surcharge.
public class DeluxeRoom extends Room {
    private static final double BASE_RATE = 1800.0; // Base price per night
    private static final double LUXURY_SURCHARGE = 500.0; // Extra surcharge per night

    public DeluxeRoom(int capacity) {
        super("Deluxe", capacity);
    }

    @Override
    public double calculatePrice(int nights) {
        return (BASE_RATE + LUXURY_SURCHARGE) * nights; // Deluxe pricing calculation
    }
}