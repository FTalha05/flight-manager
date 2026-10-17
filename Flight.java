import java.time.LocalDateTime;
import java.util.ArrayList;

public class Flight {
	public int flightID;
	public Airport startingLocation, destination;
	public LocalDateTime departure, arrival;
	public Status status;
	public Pilot firstPilot;
	public Pilot secondPilot;
	public Plane plane;
	public ArrayList<Ticket> tickets;
	public enum Status {
		SCHEDULED, DELAYED, IN_AIR, LANDED, CANCELLED
	}
	
	public Flight(int flightID, Airport startingLocation, Airport destination, LocalDateTime departure,
			LocalDateTime arrival, Status status, Pilot firstPilot, Pilot secondPilot) {
		this.flightID = flightID;
		this.startingLocation = startingLocation;
		this.arrival = arrival;
		this.status = status;
		this.firstPilot = firstPilot;
		this.secondPilot = secondPilot;
		this.plane = null; // Plane needs to be added manually to make the sure flight time is available.
		// Starting location and destination added using methods to also add them to the airport object's data.
		changeStartingLocation(startingLocation);
		changeDestination(destination);
	}
	
	public void changeStartingLocation(Airport airport) {
		if (startingLocation != null) startingLocation.removeFlight(this);
		airport.addFlight(this);
		startingLocation = airport;
	}
	
	public void changeDestination(Airport airport) {
		if (destination != null) destination.removeFlight(this);
		airport.addFlight(this);
		destination = airport;
	}
	
	public void delayFlight(int minutes) {
		departure = departure.plusMinutes(minutes);
		arrival = arrival.plusMinutes(minutes);
	}
	
	public void updateStatus(Status status) {
		this.status = status;
	}
	
	public void cancelFlight() {
		status = Status.CANCELLED;
	}
	
	public boolean ChangePlane(Plane newPlane) {
		// If the plane is available "hasBeenAddedToNewPlane" will be true. False otherwise.
		boolean hasBeenAddedToNewPlane = newPlane.addFlight(this);
		
		if (hasBeenAddedToNewPlane) {
			// If successful, remove flight from the old plane, change planes and return true.
			if (plane != null) plane.removeFlight(this);
			plane = newPlane;
			return true;
		} else {
			// If not, return false.
			return false;
		}
	}
	
}
