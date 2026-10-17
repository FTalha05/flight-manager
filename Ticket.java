import java.math.BigDecimal;

public class Ticket {
	public int ticketID;
	public Flight flight;
	public Passenger passenger;
	public int seatNumber;
	public BigDecimal price;
	public Status status;
	public enum Status {
		UNCONFIRMED, CONFIRMED, CHECKED_IN, CANCELLED, USED
	}
	
	public Ticket(int ticketID, Flight flight, Passenger passenger, int seatNumber, BigDecimal price) {
		super();
		this.ticketID = ticketID;
		this.flight = flight;
		this.passenger = passenger;
		this.seatNumber = seatNumber;
		this.price = price;
		this.status = Status.UNCONFIRMED;
		
		passenger.tickets.add(this);
	}
	
	public void cancel() {
		status = Status.CANCELLED;
	}
	
	public boolean changeSeat(int newSeat) {
		// Returns true if seat change was successful. Returns false on failure.
		Plane plane = flight.plane;
		if (plane == null) return false;
		if (plane.totalCapacity < newSeat) return false;
		for (Ticket otherTicket : flight.tickets) {
			if (otherTicket.seatNumber == newSeat && otherTicket.status != Status.CANCELLED) {
				return false;
			}
		}
		return true;
	}
	
	public void updateStatus(Status newStatus){
		status = newStatus;
	}
	
}
