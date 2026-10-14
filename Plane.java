
public class Plane {
	public int planeID;
	public String model;
	public int totalCapacity;
	public MaintananceStatus maintananceStatus;
	
	public enum MaintananceStatus {
		AVAILABLE, IN_MAINTANANCE, IN_FLIGHT
	}
}
