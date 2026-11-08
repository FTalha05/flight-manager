import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * HW2 - Part 1: basis-path (white-box) tests for Ticket.changeSeat(int newSeat).
 *
 * Decision nodes (each if / loop condition is one node):
 *   D1  plane == null
 *   D2  plane.totalCapacity < newSeat
 *   D3  loop: is there another ticket in flight.tickets ?
 *   D4  otherTicket.seatNumber == newSeat && otherTicket.status != CANCELLED
 *
 * V(G) = 4 decisions + 1 = 5  ->  5 basis paths, one test each.
 *
 *   BP1  D1=F D2=F D3=T D4=F D3=F -> return true     (baseline path)
 *   BP2  D1=T                     -> return false    (no plane)
 *   BP3  D1=F D2=T                -> return false    (seat above capacity)
 *   BP4  D1=F D2=F D3=T D4=T      -> return false    (seat held by an active ticket)
 *   BP5  D1=F D2=F D3=F           -> return true     (loop never runs)
 */
public class TestBasisPathChangeSeat {

    private Flight flight;
    private Passenger passenger;
    private Plane plane;     // capacity 150, NOT yet assigned to the flight
    private Ticket ticket;   // seat 5

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
        ticket = new Ticket(1000, flight, passenger, 5, new BigDecimal("200.00"));
    }

    // BP1: plane assigned, seat in range, the only ticket on the flight is our own -> seat changes
    @Test
    public void bp1_freeSeat_returnsTrueAndUpdatesSeat() {
        flight.ChangePlane(plane);
        assertTrue(ticket.changeSeat(25));
        assertEquals(25, ticket.seatNumber);
    }

    // BP2: flight has no plane -> false, seat unchanged
    @Test
    public void bp2_noPlane_returnsFalseAndKeepsSeat() {
        assertFalse(ticket.changeSeat(25));
        assertEquals(5, ticket.seatNumber);
    }

    // BP3: seat above capacity (151 > 150) -> false, seat unchanged
    @Test
    public void bp3_seatAboveCapacity_returnsFalseAndKeepsSeat() {
        flight.ChangePlane(plane);
        assertFalse(ticket.changeSeat(151));
        assertEquals(5, ticket.seatNumber);
    }

    // BP4: a second passenger asks for seat 5, which is held by an ACTIVE ticket (the first ticket in
    // flight.tickets, so the loop stops at its very first iteration) -> false, seat unchanged
    @Test
    public void bp4_seatHeldByActiveTicket_returnsFalseAndKeepsSeat() {
        flight.ChangePlane(plane);
        Passenger other = new Passenger(2, "Other", "other@mail.com", "PASS123", "555-9900");
        Ticket second = new Ticket(1001, flight, other, 25, new BigDecimal("200.00"));
        assertFalse(second.changeSeat(5));
        assertEquals(25, second.seatNumber);
    }

    // BP5 (white-box): the loop body never runs. Through normal use our own ticket is always in the list,
    // so the test empties flight.tickets to reach the zero-iteration path.
    @Test
    public void bp5_noTicketsToCompare_returnsTrue() {
        flight.ChangePlane(plane);
        flight.tickets.clear();
        assertTrue(ticket.changeSeat(25));
        assertEquals(25, ticket.seatNumber);
    }

    // EXTRA (not a new basis path - same path as BP1): covers the second half of the && in D4.
    // Seat 25 belongs to a CANCELLED ticket, so it may be re-used.
    @Test
    public void extra_seatHeldByCancelledTicket_returnsTrue() {
        flight.ChangePlane(plane);
        Passenger other = new Passenger(2, "Other", "other@mail.com", "PASS123", "555-9900");
        Ticket otherTicket = new Ticket(1001, flight, other, 25, new BigDecimal("200.00"));
        otherTicket.cancel();
        assertTrue(ticket.changeSeat(25));
    }
}
