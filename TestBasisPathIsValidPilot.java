import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * HW2 - Part 1: basis-path (white-box) tests for Pilot.isValidPilot().
 *
 * Decision nodes (each if is one node):
 *   D1  pilotID <= 0
 *   D2  name == null || name.isBlank()
 *   D3  licenseNumber == null || !licenseNumber.startsWith("LIC")
 *   D4  rank == null
 *
 * V(G) = 4 decisions + 1 = 5  ->  5 basis paths, one test each.
 *
 *   BP1  D1=F D2=F D3=F D4=F -> return true    (baseline path)
 *   BP2  D1=T                -> return false
 *   BP3  D1=F D2=T           -> return false
 *   BP4  D1=F D2=F D3=T      -> return false
 *   BP5  D1=F D2=F D3=F D4=T -> return false
 */
public class TestBasisPathIsValidPilot {

    // BP1: every field valid
    @Test
    public void bp1_allFieldsValid_returnsTrue() {
        Pilot pilot = new Pilot(1, "Alice Smith", "LIC-12345", Pilot.Rank.CAPTAIN);
        assertTrue(pilot.isValidPilot());
    }

    // BP2: pilotID <= 0 (boundary value 0)
    @Test
    public void bp2_idNotPositive_returnsFalse() {
        Pilot pilot = new Pilot(0, "Alice Smith", "LIC-12345", Pilot.Rank.CAPTAIN);
        assertFalse(pilot.isValidPilot());
    }

    // BP3: name is blank
    @Test
    public void bp3_blankName_returnsFalse() {
        Pilot pilot = new Pilot(1, "", "LIC-12345", Pilot.Rank.CAPTAIN);
        assertFalse(pilot.isValidPilot());
    }

    // BP4: license does not start with "LIC"
    @Test
    public void bp4_licenseWithoutLicPrefix_returnsFalse() {
        Pilot pilot = new Pilot(1, "Alice Smith", "BAD-123", Pilot.Rank.CAPTAIN);
        assertFalse(pilot.isValidPilot());
    }

    // BP5: rank is missing
    @Test
    public void bp5_rankMissing_returnsFalse() {
        Pilot pilot = new Pilot(1, "Alice Smith", "LIC-12345", null);
        assertFalse(pilot.isValidPilot());
    }

    // EXTRA (same paths as BP3 / BP4): cover the first half of each || condition.
    @Test
    public void extra_nullName_returnsFalse() {
        Pilot pilot = new Pilot(1, null, "LIC-12345", Pilot.Rank.CAPTAIN);
        assertFalse(pilot.isValidPilot());
    }

    @Test
    public void extra_nullLicense_returnsFalse() {
        Pilot pilot = new Pilot(1, "Alice Smith", null, Pilot.Rank.CAPTAIN);
        assertFalse(pilot.isValidPilot());
    }
}
