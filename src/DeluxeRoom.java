// Deluxe Room Class
// Defines properties and pricing for Deluxe Room.
public class DeluxeRoom extends Room {
    private static final double PRICE_PER_NIGHT = 2300; // Price per night for Deluxe Room

    public DeluxeRoom() {
        super("Deluxe Room", 4); // Deluxe Room with a capacity of 4 people
    }

    @Override
    public double calculatePrice(int nights) {
        return nights * PRICE_PER_NIGHT;
    }
}
