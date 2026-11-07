
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
	
	public boolean isValidPilot() {
		if (pilotID <= 0) return false;
		if (name == null || name.isBlank()) return false;
		if (licenseNumber == null || !licenseNumber.startsWith("LIC")) return false;
		if (rank == null) return false;
		return true;
	}
}
