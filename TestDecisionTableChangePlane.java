import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * HW2 - Part 3: table-based (decision table) tests for Flight.ChangePlane(Plane newPlane).
 *
 * Conditions
 *   C1  Is the new plane free during the flight's time window?
 *   C2  Does the flight already have a plane assigned?
 *
 * +----------------------------------------+------+------+------+------+
 * |                                        |  R1  |  R2  |  R3  |  R4  |
 * +----------------------------------------+------+------+------+------+
 * | C1  new plane free                     |  Y   |  Y   |  N   |  N   |
 * | C2  flight already has a plane         |  N   |  Y   |  N   |  Y   |
 * +----------------------------------------+------+------+------+------+
 * | A1  ChangePlane returns                | true | true | false| false|
 * | A2  flight.plane afterwards            | new  | new  | null | old  |
 * | A3  old plane still lists the flight   |  -   |  No  |  -   | Yes  |
 * | A4  new plane lists the flight         | Yes  | Yes  |  No  |  No  |
 * +----------------------------------------+------+------+------+------+
 *
 * 2 conditions -> 2^2 = 4 rules. One test per rule (column).
 */
public class TestDecisionTableChangePlane {

    private Airport jfk;
    private Airport lax;
    private Pilot captain;
    private Pilot firstOfficer;
    private LocalDateTime dep;
    private LocalDateTime arr;
    private Flight flight;      // 10:00 - 14:00
    private Plane oldPlane;
    private Plane newPlane;

    @BeforeEach
    public void setUp() {
        jfk = new Airport(1, "JFK", "New York", "USA", "EST");
        lax = new Airport(2, "LAX", "Los Angeles", "USA", "PST");
        captain = new Pilot(1, "Alice Smith", "LIC-1", Pilot.Rank.CAPTAIN);
        firstOfficer = new Pilot(2, "Bob Jones", "LIC-2", Pilot.Rank.FIRST_OFFICER);
        dep = LocalDateTime.of(2026, 6, 1, 10, 0);
        arr = LocalDateTime.of(2026, 6, 1, 14, 0);
        flight = new Flight(100, jfk, lax, dep, arr, Flight.Status.SCHEDULED, captain, firstOfficer);
        oldPlane = new Plane(10, "Boeing 737", 150, Plane.MaintananceStatus.AVAILABLE);
        newPlane = new Plane(11, "Airbus A320", 180, Plane.MaintananceStatus.AVAILABLE);
    }

    /** Makes the given plane busy during the flight's window by giving it another, overlapping flight. */
    private void makeBusy(Plane p) {
        Flight other = new Flight(200, jfk, lax, dep.plusMinutes(30), arr.plusMinutes(30),
                Flight.Status.SCHEDULED, captain, firstOfficer);
        p.addFlight(other);
    }

    // R1: C1 = Y, C2 = N
    @Test
    public void rule1_freePlane_flightHadNoPlane() {
        boolean result = flight.ChangePlane(newPlane);
        assertTrue(result);
        assertSame(newPlane, flight.plane);
        assertTrue(newPlane.flights.contains(flight));
    }

    // R2: C1 = Y, C2 = Y
    @Test
    public void rule2_freePlane_flightHadPlane() {
        flight.ChangePlane(oldPlane);
        boolean result = flight.ChangePlane(newPlane);
        assertTrue(result);
        assertSame(newPlane, flight.plane);
        assertFalse(oldPlane.flights.contains(flight));
        assertTrue(newPlane.flights.contains(flight));
    }

    // R3: C1 = N, C2 = N
    @Test
    public void rule3_busyPlane_flightHadNoPlane() {
        makeBusy(newPlane);
        boolean result = flight.ChangePlane(newPlane);
        assertFalse(result);
        assertNull(flight.plane);
        assertFalse(newPlane.flights.contains(flight));
    }

    // R4: C1 = N, C2 = Y
    @Test
    public void rule4_busyPlane_flightHadPlane() {
        flight.ChangePlane(oldPlane);
        makeBusy(newPlane);
        boolean result = flight.ChangePlane(newPlane);
        assertFalse(result);
        assertSame(oldPlane, flight.plane);
        assertTrue(oldPlane.flights.contains(flight));
        assertFalse(newPlane.flights.contains(flight));
    }
}
