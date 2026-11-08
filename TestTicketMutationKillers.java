import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * HW2 - Part 2: extra tests that kill the mutants which SURVIVED the original TestTicket.
 *
 * Mutant M5 changes  plane.totalCapacity < newSeat  into  plane.totalCapacity <= newSeat.
 * TestTicket only tries seats 25 and 200 (capacity 150), so the boundary seat 150 was never tested
 * and the mutant went unnoticed.
 */
public class TestTicketMutationKillers {

    private Flight flight;
    private Passenger passenger;
    private Plane plane;
    private Ticket ticket;

    @BeforeEach
    public void setUp() {
        Airport jfk = new Airport(1, "JFK", "New York", "USA", "EST");
        Airport lax = new Airport(2, "LAX", "Los Angeles", "USA", "PST");
        Pilot captain = new Pilot(1, "Alice Smith", "LIC-1", Pilot.Rank.CAPTAIN);
        Pilot firstOfficer = new Pilot(2, "Bob Jones", "LIC-2", Pilot.Rank.FIRST_OFFICER);
        LocalDateTime dep = LocalDateTime.of(2026, 6, 1, 10, 0);
        LocalDateTime arr = LocalDateTime.of(2026, 6, 1, 14, 0);
        flight = new Flight(100, jfk, lax, dep, arr, Flight.Status.SCHEDULED, captain, firstOfficer);
        passenger = new Passenger(1, "John Doe", "john@example.com", "AB12345", "555-0100");
        plane = new Plane(10, "Boeing 737", 150, Plane.MaintananceStatus.AVAILABLE);
        flight.ChangePlane(plane);
        ticket = new Ticket(1000, flight, passenger, 5, new BigDecimal("200.00"));
    }

    // Kills M5 (< becomes <=): the LAST valid seat (= capacity 150) must be accepted.
    @Test
    public void changeSeat_seatEqualToCapacity_succeeds() {
        assertTrue(ticket.changeSeat(150));
        assertEquals(150, ticket.seatNumber);
    }
}
