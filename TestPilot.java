import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestPilot {

    @Test
    public void testPilotConstructor() {
        Pilot pilot = new Pilot(1, "Alice Smith", "LIC-98765", Pilot.Rank.CAPTAIN);

        assertNotNull(pilot.name);
        assertNotNull(pilot.licenseNumber);

        assertEquals(1, pilot.pilotID);
        assertEquals("Alice Smith", pilot.name);
        assertEquals("LIC-98765", pilot.licenseNumber);
        assertEquals(Pilot.Rank.CAPTAIN, pilot.rank);
    }

    @Test
    public void testUpdateRank() {
        Pilot pilot = new Pilot(2, "Bob Jones", "LIC-12345", Pilot.Rank.FIRST_OFFICER);
        
        assertEquals(Pilot.Rank.FIRST_OFFICER, pilot.rank);

        pilot.UpdateRank(Pilot.Rank.CAPTAIN);
        assertEquals(Pilot.Rank.CAPTAIN, pilot.rank);
    }

    @Test
    public void testMultiplePilotsIndependence() {
        Pilot captain = new Pilot(1, "Captain Marvel", "CAP-001", Pilot.Rank.CAPTAIN);
        Pilot coPilot = new Pilot(2, "Sidekick Sam", "COP-002", Pilot.Rank.FIRST_OFFICER);

        // Ensure distinct instances do not share state
        assertNotEquals(captain.pilotID, coPilot.pilotID);
        assertNotEquals(captain.licenseNumber, coPilot.licenseNumber);
        assertEquals(Pilot.Rank.CAPTAIN, captain.rank);
        assertEquals(Pilot.Rank.FIRST_OFFICER, coPilot.rank);
    }
}