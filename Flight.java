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
}
