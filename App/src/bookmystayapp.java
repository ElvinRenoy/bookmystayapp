// Version: 6.1

import java.util.*;

// Reservation (from previous use case)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Booking Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // dequeue
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Inventory Service
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        inventory.put(type, getAvailability(type) - 1);
    }
}

// Booking Service (Allocation Logic)
class BookingService {

    private RoomInventory inventory;

    // Track allocated IDs
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map roomType -> assigned room IDs
    private HashMap<String, Set<String>> roomAllocations = new HashMap<>();

    private int idCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Generate unique Room ID
    private String generateRoomId(String roomType) {
        String id;
        do {
            id = roomType.replaceAll(" ", "").toUpperCase() + "-" + idCounter++;
        } while (allocatedRoomIds.contains(id)); // ensure uniqueness

        allocatedRoomIds.add(id);
        return id;
    }

    // Process booking request
    public void processReservation(Reservation r) {
        String type = r.getRoomType();

        if (inventory.getAvailability(type) > 0) {

            // Generate unique ID
            String roomId = generateRoomId(type);

            // Store allocation
            roomAllocations.putIfAbsent(type, new HashSet<>());
            roomAllocations.get(type).add(roomId);

            // Update inventory immediately
            inventory.decrement(type);

            // Confirm booking
            System.out.println("Booking Confirmed!");
            System.out.println("Guest: " + r.getGuestName());
            System.out.println("Room Type: " + type);
            System.out.println("Allocated Room ID: " + roomId);
            System.out.println("------------------------");

        } else {
            System.out.println("Booking Failed (No Availability) for " + r.getGuestName());
        }
    }
}

// Main Class
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        // Setup Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single Room", 2);
        inventory.addRoom("Double Room", 1);

        // Setup Queue
        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail
        queue.addRequest(new Reservation("David", "Double Room"));

        // Booking Service
        BookingService service = new BookingService(inventory);

        // Process all requests FIFO
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            service.processReservation(r);
        }
    }
}