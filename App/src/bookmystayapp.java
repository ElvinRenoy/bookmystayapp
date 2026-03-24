// Version: 12.1

import java.io.*;
import java.util.*;

// Reservation (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

// Inventory (Serializable)
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void display() {
        System.out.println("=== Inventory ===");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}

// Booking History (Serializable)
class BookingHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Reservation> reservations = new ArrayList<>();

    public void add(Reservation r) {
        reservations.add(r);
    }

    public List<Reservation> getAll() {
        return reservations;
    }

    public void display() {
        System.out.println("=== Booking History ===");
        for (Reservation r : reservations) {
            r.display();
        }
    }
}

// Wrapper for full system state
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    RoomInventory inventory;
    BookingHistory history;

    public SystemState(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // Save state
    public static void save(SystemState state) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load state
    public static SystemState load() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.");
            return (SystemState) in.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state. Starting with safe defaults.");
        }
        return null;
    }
}

// Main Class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        // Try to restore state
        SystemState state = PersistenceService.load();

        RoomInventory inventory;
        BookingHistory history;

        if (state != null) {
            inventory = state.inventory;
            history = state.history;
        } else {
            // Fresh start
            inventory = new RoomInventory();
            history = new BookingHistory();

            inventory.addRoom("Single Room", 5);
            inventory.addRoom("Double Room", 3);

            history.add(new Reservation("RES-101", "Alice", "Single Room"));
        }

        // Display current state
        inventory.display();
        history.display();

        // Simulate new booking
        history.add(new Reservation("RES-102", "Bob", "Double Room"));

        // Save state before shutdown
        SystemState newState = new SystemState(inventory, history);
        PersistenceService.save(newState);
    }
}