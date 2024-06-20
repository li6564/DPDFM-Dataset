package junit.tests;
/**
 * Testing the ActiveTest support
 */
public class ActiveTestTest extends junit.framework.TestCase {
    public static class SuccessTest extends junit.framework.TestCase {
        public SuccessTest(java.lang.String name) {
            super(name);
        }

        public void success() {
        }
    }

    public ActiveTestTest(java.lang.String name) {
        super(name);
    }

    public void testActiveTest() {
        junit.framework.Test test = createActiveTestSuite();
        junit.framework.TestResult result = new junit.framework.TestResult();
        test.run(result);
        junit.framework.Assert.assertEquals(100, result.runCount());
        junit.framework.Assert.assertEquals(0, result.failureCount());
        junit.framework.Assert.assertEquals(0, result.errorCount());
    }

    public void testActiveRepeatedTest() {
        junit.framework.Test test = new junit.extensions.RepeatedTest(createActiveTestSuite(), 5);
        junit.framework.TestResult result = new junit.framework.TestResult();
        test.run(result);
        junit.framework.Assert.assertEquals(500, result.runCount());
        junit.framework.Assert.assertEquals(0, result.failureCount());
        junit.framework.Assert.assertEquals(0, result.errorCount());
    }

    public void testActiveRepeatedTest0() {
        junit.framework.Test test = new junit.extensions.RepeatedTest(createActiveTestSuite(), 0);
        junit.framework.TestResult result = new junit.framework.TestResult();
        test.run(result);
        junit.framework.Assert.assertEquals(0, result.runCount());
        junit.framework.Assert.assertEquals(0, result.failureCount());
        junit.framework.Assert.assertEquals(0, result.errorCount());
    }

    public void testActiveRepeatedTest1() {
        junit.framework.Test test = new junit.extensions.RepeatedTest(createActiveTestSuite(), 1);
        junit.framework.TestResult result = new junit.framework.TestResult();
        test.run(result);
        junit.framework.Assert.assertEquals(100, result.runCount());
        junit.framework.Assert.assertEquals(0, result.failureCount());
        junit.framework.Assert.assertEquals(0, result.errorCount());
    }

    junit.extensions.ActiveTestSuite createActiveTestSuite() {
        junit.extensions.ActiveTestSuite suite = new junit.extensions.ActiveTestSuite();
        for (int i = 0; i < 100; i++)
            suite.addTest(new junit.tests.ActiveTestTest.SuccessTest("success"));

        return suite;
    }
}