import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestPassenger {

    private Passenger passenger;
    private Airport jfk;
    private Airport lax;
    private Pilot captain;
    private Pilot firstOfficer;
    private Flight flight;

    // Variables for capturing console output.
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        passenger = new Passenger(1, "Jane Doe", "jane.doe@example.com", "XY987654", "555-9876");

        jfk = new Airport(1, "JFK", "New York", "USA", "EST");
        lax = new Airport(2, "LAX", "Los Angeles", "USA", "PST");

        captain = new Pilot(1, "Alice Smith", "LIC-1", Pilot.Rank.CAPTAIN);
        firstOfficer = new Pilot(2, "Bob Jones", "LIC-2", Pilot.Rank.FIRST_OFFICER);

        LocalDateTime dep = LocalDateTime.of(2026, 6, 1, 10, 0);
        LocalDateTime arr = LocalDateTime.of(2026, 6, 1, 14, 0);
        flight = new Flight(100, jfk, lax, dep, arr, Flight.Status.SCHEDULED, captain, firstOfficer);

        // Redirect standard output to capture displayBookingHistory print statements
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        // Restore original standard output after test execution
        System.setOut(originalOut);
    }

    @Test
    public void testPassengerConstructor() {
        assertEquals(1, passenger.passengerID);
        assertEquals("Jane Doe", passenger.name);
        assertEquals("jane.doe@example.com", passenger.email);
        assertEquals("XY987654", passenger.passportNumber);
        assertEquals("555-9876", passenger.phone);
        assertNotNull(passenger.tickets);
        assertTrue(passenger.tickets.isEmpty());
    }

    @Test
    public void testDisplayBookingHistory() {
        // Create a ticket which automatically registers itself to passenger.tickets
        Ticket ticket = new Ticket(5000, flight, passenger, 14, new BigDecimal("299.99"));

        // Call displayBookingHistory, which prints to System.out
        passenger.displayBookingHistory();

        String output = outContent.toString();

        // Verify that expected information fields appear in the console output
        assertTrue(output.contains("Ticket ID: 5000"));
        assertTrue(output.contains("Flight ID: 100"));
        assertTrue(output.contains("Price: 299.99"));
        assertTrue(output.contains("JFK to LAX"));
    }
}