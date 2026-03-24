// Version: 8.1

import java.util.*;

// Reservation (confirmed booking)
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

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType);
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Get all reservations (read-only usage)
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    // Display all bookings
    public void showAllBookings(List<Reservation> reservations) {
        System.out.println("=== Booking History ===");
        for (Reservation r : reservations) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {
        System.out.println("\n=== Booking Summary Report ===");

        Map<String, Integer> countByRoom = new HashMap<>();

        for (Reservation r : reservations) {
            String type = r.getRoomType();
            countByRoom.put(type, countByRoom.getOrDefault(type, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : countByRoom.entrySet()) {
            System.out.println(entry.getKey() + " -> Total Bookings: " + entry.getValue());
        }

        System.out.println("Total Reservations: " + reservations.size());
    }
}

// Main Class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        // Booking History
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("RES-101", "Alice", "Single Room"));
        history.addReservation(new Reservation("RES-102", "Bob", "Double Room"));
        history.addReservation(new Reservation("RES-103", "Charlie", "Single Room"));
        history.addReservation(new Reservation("RES-104", "David", "Suite Room"));

        // Reporting Service
        BookingReportService reportService = new BookingReportService();

        // Display all bookings
        reportService.showAllBookings(history.getAllReservations());

        // Generate summary report
        reportService.generateSummary(history.getAllReservations());
    }
}