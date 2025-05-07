// Standard Room Class
// Implements room pricing for standard rooms.
public class StandardRoom extends Room {
    private static final double BASE_RATE = 1000.0; // Base price per night

    public StandardRoom(int capacity) {
        super("Standard", capacity);
    }

    @Override
    public double calculatePrice(int nights) {
        return BASE_RATE * nights; // Simple calculation for standard rooms
    }
}