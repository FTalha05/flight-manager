import java.time.LocalDateTime;

public class Flight {
	public int flightID;
	public Airport startingLocation, destination;
	public LocalDateTime departure, arrival;
	public Status status;
	public Pilot firstPilot;
	public Pilot secondPilot;
	public Plane plane;
	public enum Status {
		SCHEDULED, DELAYED, IN_AIR, LANDED, CANCELLED
	}
	
	public Flight(int flightID, Airport startingLocation, Airport destination, LocalDateTime departure,
			LocalDateTime arrival, Status status, Pilot firstPilot, Pilot secondPilot) {
		super();
		this.flightID = flightID;
		this.startingLocation = startingLocation;
		this.destination = destination;
		this.departure = departure;
		this.arrival = arrival;
		this.status = status;
		this.firstPilot = firstPilot;
		this.secondPilot = secondPilot;
		this.plane = null; // Plane needs to be added manually to make the sure flight time is available.
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
