// Version: 3.1

import java.util.HashMap;
import java.util.Map;

// Abstract Room class (same domain model as before)
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

// Concrete Room Types
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

// Inventory Class (Centralized Management)
class RoomInventory {
    private HashMap<String, Integer> inventory;

    // Constructor initializes availability
    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Add room type with count
    public void addRoom(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }

    // Display all inventory
    public void displayInventory() {
        System.out.println("=== Centralized Room Inventory ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}

// Main Class
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        // Create Room Objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();

        // Register room types with availability
        inventory.addRoom(single.getRoomType(), 5);
        inventory.addRoom(doubleRoom.getRoomType(), 3);
        inventory.addRoom(suite.getRoomType(), 2);

        // Display Room Details
        System.out.println("=== Room Details ===");
        single.displayDetails();
        System.out.println("------------------");
        doubleRoom.displayDetails();
        System.out.println("------------------");
        suite.displayDetails();
        System.out.println("------------------");

        // Display Inventory
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating availability...");
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        inventory.displayInventory();
    }
}