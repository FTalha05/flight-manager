import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    TestFlight.class,
    TestTicket.class,
    TestPassenger.class
})
public class SuiteOperations {

}