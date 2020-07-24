import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses({HealthTest.class, AuthTest.class, TestSuite.class})
@RunWith(Suite.class)
public class TestLauncher {

}
