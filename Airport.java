import java.time.LocalDateTime;
import java.util.ArrayList;

public class Airport {
	public int airportID;
	public String name;
	public String city;
	public String country;
	public String timezone;
	public ArrayList<Flight> flights; // Required for retrieving departure and arrival times.
	
	public Airport(int airportID, String name, String city, String country, String timezone) {
		this.airportID = airportID;
		this.name = name;
		this.city = city;
		this.country = country;
		this.timezone = timezone;
	}
	
	public void addFlight(Flight flight) {
		flights.add(flight);
	}
	
	public void removeFlight(Flight flight) {
		flights.remove(flight);
	}
	
	public ArrayList<LocalDateTime> getDepartures() {
		ArrayList<LocalDateTime> departures = new ArrayList<LocalDateTime>();
		for (Flight flight : flights) {
			departures.add(flight.departure);
		}
		return departures;
	}
	
	public ArrayList<LocalDateTime> getArrivals() {
		ArrayList<LocalDateTime> arrivals = new ArrayList<LocalDateTime>();
		for (Flight flight : flights) {
			arrivals.add(flight.arrival);
		}
		return arrivals;
	}
}
