// Abstract Room Class
// This defines the shared properties and methods for all room types.
public abstract class Room {
    private String roomType; // Type of room (e.g., "Standard", "Deluxe")
    private int capacity;    // Maximum capacity of the room

    public Room(String roomType, int capacity) {
        this.roomType = roomType;
        this.capacity = capacity;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    // Abstract method to calculate the price, must be implemented by subclasses
    public abstract double calculatePrice(int nights);

    @Override
    public String toString() {
        return "Room Type: " + roomType + ", Capacity: " + capacity;
    }
}
