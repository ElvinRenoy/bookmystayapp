// Version: 5.1

import java.util.LinkedList;
import java.util.Queue;

// Reservation class (represents booking intent)
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

    public void display() {
        System.out.println("Guest: " + guestName + ", Requested Room: " + roomType);
    }
}

// Booking Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Added booking request:");
        reservation.display();
    }

    // View all queued requests
    public void displayQueue() {
        System.out.println("\n=== Booking Request Queue (FIFO) ===");
        for (Reservation r : queue) {
            r.display();
        }
    }

    // Peek next request (without removing)
    public Reservation peekNext() {
        return queue.peek();
    }
}

// Main Class
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        // Initialize queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate incoming booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display queue (FIFO order preserved)
        bookingQueue.displayQueue();

        // Show next request to be processed
        System.out.println("\nNext request to process:");
        Reservation next = bookingQueue.peekNext();
        if (next != null) {
            next.display();
        }
    }
}