package junit.tests;
public class TestListenerTest extends junit.framework.TestCase implements junit.framework.TestListener {
    private junit.framework.TestResult fResult;

    private int fStartCount;

    private int fEndCount;

    private int fFailureCount;

    private int fErrorCount;

    public TestListenerTest(java.lang.String name) {
        super(name);
    }

    public void addError(junit.framework.Test test, java.lang.Throwable t) {
        fErrorCount++;
    }

    public void addFailure(junit.framework.Test test, junit.framework.AssertionFailedError t) {
        fFailureCount++;
    }

    public void endTest(junit.framework.Test test) {
        fEndCount++;
    }

    protected void setUp() {
        fResult = new junit.framework.TestResult();
        fResult.addListener(this);
        fStartCount = 0;
        fEndCount = 0;
        fFailureCount = 0;
    }

    public void startTest(junit.framework.Test test) {
        fStartCount++;
    }

    public void testError() {
        junit.framework.TestCase test = new junit.framework.TestCase("noop") {
            public void runTest() {
                throw new java.lang.Error();
            }
        };
        test.run(fResult);
        junit.framework.Assert.assertEquals(1, fErrorCount);
        junit.framework.Assert.assertEquals(1, fEndCount);
    }

    public void testFailure() {
        junit.framework.TestCase test = new junit.framework.TestCase("noop") {
            public void runTest() {
                junit.framework.Assert.fail();
            }
        };
        test.run(fResult);
        junit.framework.Assert.assertEquals(1, fFailureCount);
        junit.framework.Assert.assertEquals(1, fEndCount);
    }

    public void testStartStop() {
        junit.framework.TestCase test = new junit.framework.TestCase("noop") {
            public void runTest() {
            }
        };
        test.run(fResult);
        junit.framework.Assert.assertEquals(1, fStartCount);
        junit.framework.Assert.assertEquals(1, fEndCount);
    }
}