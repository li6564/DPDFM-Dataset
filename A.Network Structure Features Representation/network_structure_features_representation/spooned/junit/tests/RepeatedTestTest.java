package junit.tests;
/**
 * Testing the RepeatedTest support.
 */
public class RepeatedTestTest extends junit.framework.TestCase {
    private junit.framework.TestSuite fSuite;

    public static class SuccessTest extends junit.framework.TestCase {
        public SuccessTest(java.lang.String name) {
            super(name);
        }

        public void success() {
        }
    }

    public RepeatedTestTest(java.lang.String name) {
        super(name);
        fSuite = new junit.framework.TestSuite();
        fSuite.addTest(new junit.tests.RepeatedTestTest.SuccessTest("success"));
        fSuite.addTest(new junit.tests.RepeatedTestTest.SuccessTest("success"));
    }

    public void testRepeatedOnce() {
        junit.framework.Test test = new junit.extensions.RepeatedTest(fSuite, 1);
        junit.framework.Assert.assertEquals(2, test.countTestCases());
        junit.framework.TestResult result = new junit.framework.TestResult();
        test.run(result);
        junit.framework.Assert.assertEquals(2, result.runCount());
    }

    public void testRepeatedMoreThanOnce() {
        junit.framework.Test test = new junit.extensions.RepeatedTest(fSuite, 3);
        junit.framework.Assert.assertEquals(6, test.countTestCases());
        junit.framework.TestResult result = new junit.framework.TestResult();
        test.run(result);
        junit.framework.Assert.assertEquals(6, result.runCount());
    }

    public void testRepeatedZero() {
        junit.framework.Test test = new junit.extensions.RepeatedTest(fSuite, 0);
        junit.framework.Assert.assertEquals(0, test.countTestCases());
        junit.framework.TestResult result = new junit.framework.TestResult();
        test.run(result);
        junit.framework.Assert.assertEquals(0, result.runCount());
    }

    public void testRepeatedNegative() {
        try {
            new junit.extensions.RepeatedTest(fSuite, -1);
        } catch (java.lang.IllegalArgumentException e) {
            return;
        }
        junit.framework.Assert.fail("Should throw an IllegalArgumentException");
    }
}