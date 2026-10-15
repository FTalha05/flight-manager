
public class Pilot {
	public int pilotID;
	public String name;
	public String licenseNumber;
	public Rank rank;
	public enum Rank {
		CAPTAIN, FIRST_OFFICER
	}
	
	public Pilot(int pilotID, String name, String licenseNumber, Rank rank) {
		this.pilotID = pilotID;
		this.name = name;
		this.licenseNumber = licenseNumber;
		this.rank = rank;
	}
	
	public void UpdateRank(Rank newRank) {
		this.rank = newRank;
	}
}
