// Version: 4.1

import java.util.HashMap;
import java.util.Map;

// Abstract Room
abstract class Room {
    private String roomType;
    private int beds;
    private double price;
    private double size;

    public Room(String roomType, int beds, double price, double size) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
        this.size = size;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: $" + price);
        System.out.println("Size: " + size + " sq.ft");
    }
}

// Concrete Rooms
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 100.0, 200.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 180.0, 350.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 300.0, 600.0);
    }
}

// Inventory (Read-only access used here)
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoom(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Read-only method
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return inventory;
    }
}

// Search Service (Read-only)
class RoomSearchService {

    public void searchAvailableRooms(Room[] rooms, RoomInventory inventory) {
        System.out.println("=== Available Rooms ===");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());

            // Filter only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println("----------------------");
            }
        }
    }
}

// Main Class
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        // Create Room Objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        Room[] rooms = {single, doubleRoom, suite};

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single Room", 5);
        inventory.addRoom("Double Room", 0); // unavailable
        inventory.addRoom("Suite Room", 2);

        // Perform Search (Read-only)
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(rooms, inventory);
    }
}