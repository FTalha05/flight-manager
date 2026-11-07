import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TestFlight {

    private Airport jfk;
    private Airport lax;
    private Pilot captain;
    private Pilot firstOfficer;
    private Flight flight;

    @BeforeEach
    public void setUp() {
        jfk = new Airport(1, "JFK", "New York", "USA", "EST");
        lax = new Airport(2, "LAX", "Los Angeles", "USA", "PST");

        captain = new Pilot(1, "Alice Smith", "LIC-1", Pilot.Rank.CAPTAIN);
        firstOfficer = new Pilot(2, "Bob Jones", "LIC-2", Pilot.Rank.FIRST_OFFICER);

        LocalDateTime dep = LocalDateTime.of(2026, 6, 1, 10, 0);
        LocalDateTime arr = LocalDateTime.of(2026, 6, 1, 14, 0);

        flight = new Flight(100, jfk, lax, dep, arr, Flight.Status.SCHEDULED, captain, firstOfficer);
    }

    @Test
    public void testFlightConstructor() {
        assertEquals(100, flight.flightID);
        assertEquals(jfk, flight.startingLocation);
        assertEquals(lax, flight.destination);
        assertEquals(Flight.Status.SCHEDULED, flight.status);
        assertEquals(captain, flight.firstPilot);
        assertEquals(firstOfficer, flight.secondPilot);
        assertNull(flight.plane); // Plane should start as null per constructor logic
    }

    @Test
    public void testDelayFlight() {
        LocalDateTime originalDep = flight.departure;
        LocalDateTime originalArr = flight.arrival;

        flight.delayFlight(45); // Delay by 45 minutes

        assertEquals(originalDep.plusMinutes(45), flight.departure);
        assertEquals(originalArr.plusMinutes(45), flight.arrival);
    }

    @Test
    public void testFlightStatusUpdates() {
        flight.updateStatus(Flight.Status.IN_AIR);
        assertEquals(Flight.Status.IN_AIR, flight.status);

        flight.cancelFlight();
        assertEquals(Flight.Status.CANCELLED, flight.status);
    }

    @Test
    public void testChangePlane() {
        Plane plane = new Plane(10, "Airbus A320", 200, Plane.MaintananceStatus.AVAILABLE);
        plane.flights = new ArrayList<>();

        boolean success = flight.ChangePlane(plane);
        assertTrue(success);
        assertEquals(plane, flight.plane);
        assertEquals(1, plane.flights.size());
    }
}