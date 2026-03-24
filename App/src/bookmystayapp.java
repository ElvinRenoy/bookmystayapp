// Version: 10.1

import java.util.*;

// Reservation
class Reservation {
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
}

// Inventory Service
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void increment(String type) {
        inventory.put(type, getAvailability(type) + 1);
    }

    public void decrement(String type) {
        inventory.put(type, getAvailability(type) - 1);
    }
}

// Booking Service (handles allocation tracking)
class BookingService {
    private Map<String, String> reservationToRoomId = new HashMap<>();
    private Map<String, String> reservationToRoomType = new HashMap<>();

    // Store confirmed booking
    public void confirmBooking(String reservationId, String roomType, String roomId) {
        reservationToRoomId.put(reservationId, roomId);
        reservationToRoomType.put(reservationId, roomType);
    }

    public boolean exists(String reservationId) {
        return reservationToRoomId.containsKey(reservationId);
    }

    public String getRoomId(String reservationId) {
        return reservationToRoomId.get(reservationId);
    }

    public String getRoomType(String reservationId) {
        return reservationToRoomType.get(reservationId);
    }

    public void remove(String reservationId) {
        reservationToRoomId.remove(reservationId);
        reservationToRoomType.remove(reservationId);
    }
}

// Cancellation Service (Rollback logic)
class CancellationService {

    private RoomInventory inventory;
    private BookingService bookingService;

    // Stack for rollback tracking
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory, BookingService bookingService) {
        this.inventory = inventory;
        this.bookingService = bookingService;
    }

    public void cancelReservation(String reservationId) {

        // Validate existence
        if (!bookingService.exists(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found -> " + reservationId);
            return;
        }

        // Get details
        String roomId = bookingService.getRoomId(reservationId);
        String roomType = bookingService.getRoomType(reservationId);

        // Push to rollback stack (LIFO)
        rollbackStack.push(roomId);

        // Restore inventory
        inventory.increment(roomType);

        // Remove booking
        bookingService.remove(reservationId);

        System.out.println("Cancellation Successful!");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Released Room ID: " + roomId);
        System.out.println("Inventory Restored for: " + roomType);
        System.out.println("-----------------------------");
    }

    // View rollback stack
    public void showRollbackHistory() {
        System.out.println("\nRollback Stack (Recent First):");
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}

// Main Class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        // Setup inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single Room", 1);

        // Setup booking service
        BookingService bookingService = new BookingService();

        // Simulate confirmed booking
        bookingService.confirmBooking("RES-101", "Single Room", "SINGLE-1");
        inventory.decrement("Single Room");

        // Cancellation service
        CancellationService cancelService =
                new CancellationService(inventory, bookingService);

        // Perform cancellation
        cancelService.cancelReservation("RES-101");

        // Try invalid cancellation
        cancelService.cancelReservation("RES-999");

        // Show rollback history
        cancelService.showRollbackHistory();
    }
}