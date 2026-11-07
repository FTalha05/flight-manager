import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    TestAirport.class,
    TestPilot.class,
    TestPlane.class
})
public class SuiteInfrastructure {

}