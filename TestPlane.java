import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

public class TestPlane {

    private Plane plane;
    private Airport jfk;
    private Airport lax;
    private Pilot captain;
    private Pilot firstOfficer;

    @BeforeEach
    public void setUp() {
        // Initialize a standard plane and initialize its flights list
        plane = new Plane(101, "Boeing 737", 150, Plane.MaintananceStatus.AVAILABLE);

        // Setup supporting objects for creating flights
        jfk = new Airport(1, "JFK", "New York", "USA", "EST");
        lax = new Airport(2, "LAX", "Los Angeles", "USA", "PST");

        captain = new Pilot(1, "Alice Smith", "LIC-1", Pilot.Rank.CAPTAIN);
        firstOfficer = new Pilot(2, "Bob Jones", "LIC-2", Pilot.Rank.FIRST_OFFICER);
    }

    @Test
    public void testPlaneConstructor() {
        assertEquals(101, plane.planeID);
        assertEquals("Boeing 737", plane.model);
        assertEquals(150, plane.totalCapacity);
        assertEquals(Plane.MaintananceStatus.AVAILABLE, plane.maintananceStatus);
        assertNotNull(plane.flights);
    }

    @Test
    public void testUpdateMaintenanceStatus() {
        plane.updateMaintananceStatus(Plane.MaintananceStatus.IN_MAINTANANCE);
        assertEquals(Plane.MaintananceStatus.IN_MAINTANANCE, plane.maintananceStatus);

        plane.updateMaintananceStatus(Plane.MaintananceStatus.AVAILABLE);
        assertEquals(Plane.MaintananceStatus.AVAILABLE, plane.maintananceStatus);
    }

    @Test
    public void testAddFlightAndAvailability() {
        // Create a flight from 10:00 to 13:00 on June 1, 2026
        LocalDateTime dep = LocalDateTime.of(2026, 6, 1, 10, 0);
        LocalDateTime arr = LocalDateTime.of(2026, 6, 1, 13, 0);
        Flight flight = new Flight(500, jfk, lax, dep, arr, Flight.Status.SCHEDULED, captain, firstOfficer);

        // Add the flight to the plane using addFlight (which checks availability first)
        boolean added = plane.addFlight(flight);
        assertTrue(added);
        assertEquals(1, plane.flights.size());

        // Test overlapping time window (should return false)
        LocalDateTime overlapStart = LocalDateTime.of(2026, 6, 1, 11, 0);
        LocalDateTime overlapEnd = LocalDateTime.of(2026, 6, 1, 14, 0);
        assertFalse(plane.checkAvailability(overlapStart, overlapEnd));

        // Test non-overlapping time window after the flight (should return true)
        LocalDateTime safeStart = LocalDateTime.of(2026, 6, 1, 14, 0);
        LocalDateTime safeEnd = LocalDateTime.of(2026, 6, 1, 16, 0);
        assertTrue(plane.checkAvailability(safeStart, safeEnd));
    }

    @ParameterizedTest
    @CsvSource({
        "2026-06-01T08:00, 2026-06-01T09:30, true",  // Completely before
        "2026-06-01T14:00, 2026-06-01T16:00, true",  // Completely after
        "2026-06-01T11:00, 2026-06-01T12:00, false"   // Overlapping collision
    })
    public void testParameterizedAvailability(String startStr, String endStr, boolean expectedAvailable) {
        // Add an existing flight from 10:00 to 13:00
        LocalDateTime dep = LocalDateTime.of(2026, 6, 1, 10, 0);
        LocalDateTime arr = LocalDateTime.of(2026, 6, 1, 13, 0);
        Flight flight = new Flight(500, jfk, lax, dep, arr, Flight.Status.SCHEDULED, captain, firstOfficer);
        plane.addFlight(flight);

        LocalDateTime testStart = LocalDateTime.parse(startStr);
        LocalDateTime testEnd = LocalDateTime.parse(endStr);

        assertEquals(expectedAvailable, plane.checkAvailability(testStart, testEnd));
    }
}