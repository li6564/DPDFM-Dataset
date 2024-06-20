package junit.extensions;
/**
 * A Decorator to set up and tear down additional fixture state.
 * Subclass TestSetup and insert it into your tests when you want
 * to set up additional state once before the tests are run.
 */
public class TestSetup extends junit.extensions.TestDecorator {
    public TestSetup(junit.framework.Test test) {
        super(test);
    }

    public void run(final junit.framework.TestResult result) {
        junit.framework.Protectable p = new junit.framework.Protectable() {
            public void protect() throws java.lang.Exception {
                setUp();
                basicRun(result);
                tearDown();
            }
        };
        result.runProtected(this, p);
    }

    /**
     * Sets up the fixture. Override to set up additional fixture
     * state.
     */
    protected void setUp() throws java.lang.Exception {
    }

    /**
     * Tears down the fixture. Override to tear down the additional
     * fixture state.
     */
    protected void tearDown() throws java.lang.Exception {
    }
}