import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Passenger {
	public int passengerID;
	public String name;
	public String email;
	public String passportNumber;
	public String phone;
	public ArrayList<Ticket> tickets;
	
	public Passenger(int passengerID, String name, String email, String passportNumber, String phone) {
		super();
		this.passengerID = passengerID;
		this.name = name;
		this.email = email;
		this.passportNumber = passportNumber;
		this.phone = phone;
		tickets = new ArrayList<Ticket>();
	}
	
	public void displayBookingHistory() {
		for (Ticket ticket : tickets) {
			int tID = ticket.ticketID;
			Flight flight = ticket.flight;
			int fID = flight.flightID;
			BigDecimal price = ticket.price;
			String startingAirportName = flight.startingLocation.name, destinationAirportName = flight.destination.name;
			LocalDateTime departure = flight.departure, arrival = flight.arrival;
			System.out.println("Ticket ID: " + tID + " - Flight ID: " + fID + " - Price: " + price
					+ " - " + startingAirportName + " to " + destinationAirportName
					+ " - Departure: " + departure + " - Arrival: " + arrival + "\n");
		}
	}
}
