import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class TestTicket {

    private Airport jfk;
    private Airport lax;
    private Pilot captain;
    private Pilot firstOfficer;
    private Flight flight;
    private Passenger passenger;
    private Plane plane;

    @BeforeEach
    public void setUp() {
        jfk = new Airport(1, "JFK", "New York", "USA", "EST");
        lax = new Airport(2, "LAX", "Los Angeles", "USA", "PST");

        captain = new Pilot(1, "Alice Smith", "LIC-1", Pilot.Rank.CAPTAIN);
        firstOfficer = new Pilot(2, "Bob Jones", "LIC-2", Pilot.Rank.FIRST_OFFICER);

        LocalDateTime dep = LocalDateTime.of(2026, 6, 1, 10, 0);
        LocalDateTime arr = LocalDateTime.of(2026, 6, 1, 14, 0);

        flight = new Flight(100, jfk, lax, dep, arr, Flight.Status.SCHEDULED, captain, firstOfficer);
        
        passenger = new Passenger(1, "John Doe", "john@example.com", "AB12345", "555-0100");

        plane = new Plane(10, "Boeing 737", 150, Plane.MaintananceStatus.AVAILABLE);
        plane.flights = new ArrayList<>();
    }

    @Test
    public void testTicketConstructorAndDefaultStatus() {
        BigDecimal price = new BigDecimal("250.00");
        Ticket ticket = new Ticket(1000, flight, passenger, 12, price);

        assertEquals(1000, ticket.ticketID);
        assertEquals(flight, ticket.flight);
        assertEquals(passenger, ticket.passenger);
        assertEquals(12, ticket.seatNumber);
        assertEquals(price, ticket.price);
        assertEquals(Ticket.Status.UNCONFIRMED, ticket.status); // Default status
        
        // Verify automatic registration to passenger's tickets list
        assertEquals(1, passenger.tickets.size());
        assertEquals(ticket, passenger.tickets.get(0));
    }

    @Test
    public void testCancelTicket() {
        Ticket ticket = new Ticket(1001, flight, passenger, 15, new BigDecimal("300.00"));
        ticket.cancel();
        assertEquals(Ticket.Status.CANCELLED, ticket.status);
    }

    @Test
    public void testUpdateStatus() {
        Ticket ticket = new Ticket(1002, flight, passenger, 20, new BigDecimal("150.00"));
        
        ticket.updateStatus(Ticket.Status.CONFIRMED);
        assertEquals(Ticket.Status.CONFIRMED, ticket.status);

        ticket.updateStatus(Ticket.Status.CHECKED_IN);
        assertEquals(Ticket.Status.CHECKED_IN, ticket.status);
    }

    @Test
    public void testChangeSeatWithoutPlane() {
        Ticket ticket = new Ticket(1003, flight, passenger, 5, new BigDecimal("200.00"));
        // Flight has no plane assigned yet (plane is null), so changeSeat should fail
        boolean result = ticket.changeSeat(10);
        assertFalse(result);
    }

    @Test
    public void testChangeSeatWithPlane() {
        flight.ChangePlane(plane); // Assign plane with capacity 150
        Ticket ticket = new Ticket(1004, flight, passenger, 5, new BigDecimal("200.00"));

        // Valid seat change within capacity
        boolean result = ticket.changeSeat(25);
        assertTrue(result);

        // Invalid seat change exceeding total capacity (150)
        boolean outOfBounds = ticket.changeSeat(200);
        assertFalse(outOfBounds);
    }
    
    @Test
    public void testChangeSeatAlreadyTaken() {
        flight.ChangePlane(plane); // Assign plane with capacity 150

        // Create first ticket occupying seat 30
        Ticket ticket1 = new Ticket(1004, flight, passenger, 30, new BigDecimal("200.00"));

        // Create a second passenger and ticket occupying seat 10
        Passenger passenger2 = new Passenger(2, "Jane Smith", "jane@example.com", "CD98765", "555-0200");
        passenger2.tickets = new ArrayList<>();
        Ticket ticket2 = new Ticket(1005, flight, passenger2, 10, new BigDecimal("200.00"));

        // Try to change ticket2's seat to 30, which is already taken by ticket1
        boolean result = ticket2.changeSeat(30);
        
        // Should return false because the seat is occupied
        assertFalse(result);
    }
}