import java.math.BigDecimal;

public class Ticket {
	public int ticketID;
	public Flight flight;
	public Passenger passenger;
	public int seatNumber;
	public BigDecimal price;
	public Status status;
	
	public enum Status {
		CONFIRMED, CHECKED_IN, CANCELLED, USED
	}
}
