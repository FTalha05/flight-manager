import java.time.LocalDateTime;
import java.util.ArrayList;

public class Plane {
	public int planeID;
	public String model;
	public int totalCapacity;
	public MaintananceStatus maintananceStatus;
	public ArrayList<Flight> flights;
	public enum MaintananceStatus {
		AVAILABLE, IN_MAINTANANCE, IN_FLIGHT
	}
	
	public Plane(int planeID, String model, int totalCapacity, MaintananceStatus maintananceStatus) {
		this.planeID = planeID;
		this.model = model;
		this.totalCapacity = totalCapacity;
		this.maintananceStatus = maintananceStatus;
	}
	
	public boolean checkAvailability(LocalDateTime start, LocalDateTime end) {
		// Returns true if the plane is available within the given time frame. Returns false if not.
		
		for (Flight flight : flights) {
			// Continue if time frame is before the flight or after the flight.
			if(end.isBefore(flight.departure) || start.isAfter(flight.arrival)) continue;
			// If there's collision, return false.
			else return false;
		}
		
		return true;
	}
	
	public void updateMaintananceStatus(MaintananceStatus newStatus) {
		maintananceStatus = newStatus;
	}
	
	public void removeFlight(Flight flight) {
		flights.remove(flight);
	}
	
	public boolean addFlight(Flight flight) {
		// If available for the flight time, it will be added and return true. If not, returns false.
		boolean isAvailable = checkAvailability(flight.departure, flight.arrival);
		if (isAvailable) {
			flights.add(flight);
			return true;
		} else {
			return false;
		}
	}
	
}
