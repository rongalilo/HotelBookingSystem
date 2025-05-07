// Standard Room Class
// Defines properties and pricing for Standard Room.
public class StandardRoom extends Room {
    private static final double PRICE_PER_NIGHT = 1000; // Price per night for Standard Room

    public StandardRoom() {
        super("Standard Room", 2); // Standard Room with a capacity of 2 people
    }

    @Override
    public double calculatePrice(int nights) {
        return nights * PRICE_PER_NIGHT;
    }
}
