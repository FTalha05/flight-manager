import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class TestAirport {
	
	@Test
	public void testAirportConstructor() {
		Airport airport = new Airport(100, "Airport A", "London", "UK", "GMT");
		
		assertEquals(100, airport.airportID);
		assertEquals("Airport A", airport.name);
		assertEquals("London", airport.city);
		assertEquals("UK", airport.country);
		assertEquals("GMT", airport.timezone);
	}
	
	@Test
	public void testAddFlight() {
		Airport airport1 = new Airport(100, "Airport A", "London", "UK", "GMT");
		
		Airport airport2 = new Airport(101, "Airport B", "NYC", "USA", "GMT-4");

		Pilot pilot1 = new Pilot(1, "Alice", "LIC1", Pilot.Rank.CAPTAIN);
		Pilot pilot2 = new Pilot(2, "Bob", "LIC2", Pilot.Rank.FIRST_OFFICER);
		
		LocalDateTime dep = LocalDateTime.of(2026, 6, 1, 10, 0);
		LocalDateTime arr = LocalDateTime.of(2026, 6, 1, 18, 0);
		
		// Creating a flight should automatically add it to starting and destination airports
		Flight flight = new Flight(10, airport1, airport2, dep, arr, Flight.Status.SCHEDULED, pilot1, pilot2);
		assertEquals(1, airport1.flights.size());
		assertEquals(1, airport2.flights.size());
	}
	
	@Test
	public void testGetDeparturesAndArrivals() {
		Airport airport1 = new Airport(100, "Airport A", "London", "UK", "GMT");
		
		Airport airport2 = new Airport(101, "Airport B", "NYC", "USA", "GMT-4");

		Pilot pilot1 = new Pilot(1, "Alice", "LIC1", Pilot.Rank.CAPTAIN);
		Pilot pilot2 = new Pilot(2, "Bob", "LIC2", Pilot.Rank.FIRST_OFFICER);
		
		LocalDateTime dep = LocalDateTime.of(2026, 6, 1, 12, 0);
		LocalDateTime arr = LocalDateTime.of(2026, 6, 1, 20, 0);
		
		Flight flight = new Flight(20, airport1, airport2, dep, arr, Flight.Status.SCHEDULED, pilot1, pilot2);
		
		// Test departures from airport1
		ArrayList<LocalDateTime> departures = airport1.getDepartures();
		assertEquals(1, departures.size());
		assertEquals(dep, departures.get(0));
		
		// Test arrivals at airport2
		ArrayList<LocalDateTime> arrivals = airport2.getArrivals();
		assertEquals(1, arrivals.size());
		assertEquals(arr, arrivals.get(0));
	}
}
