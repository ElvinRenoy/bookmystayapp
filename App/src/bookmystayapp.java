// Version: 2.1

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

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public double getSize() {
        return size;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: $" + price);
        System.out.println("Size: " + size + " sq.ft");
    }
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 100.0, 200.0);
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 180.0, 350.0);
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 300.0, 600.0);
    }
}

public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        // Create Room Objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability (simple variables)
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("=== Hotel Room Availability ===\n");

        // Display Single Room
        single.displayDetails();
        System.out.println("Available: " + singleAvailable);
        System.out.println("-------------------------");

        // Display Double Room
        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleAvailable);
        System.out.println("-------------------------");

        // Display Suite Room
        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable);
        System.out.println("-------------------------");
    }
}