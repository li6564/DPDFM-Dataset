package junit.tests;
/**
 * Test an implementor of junit.framework.Test other than TestCase or TestSuite
 */
public class TestImplementorTest extends junit.framework.TestCase {
    public static class DoubleTestCase implements junit.framework.Test {
        private junit.framework.TestCase fTestCase;

        public DoubleTestCase(junit.framework.TestCase testCase) {
            fTestCase = testCase;
        }

        public int countTestCases() {
            return 2;
        }

        public void run(junit.framework.TestResult result) {
            result.startTest(this);
            junit.framework.Protectable p = new junit.framework.Protectable() {
                public void protect() throws java.lang.Throwable {
                    fTestCase.runBare();
                    fTestCase.runBare();
                }
            };
            result.runProtected(this, p);
            result.endTest(this);
        }
    }

    private junit.tests.TestImplementorTest.DoubleTestCase fTest;

    public TestImplementorTest(java.lang.String name) {
        super(name);
        junit.framework.TestCase testCase = new junit.framework.TestCase("noop") {
            public void runTest() {
            }
        };
        fTest = new junit.tests.TestImplementorTest.DoubleTestCase(testCase);
    }

    public void testSuccessfulRun() {
        junit.framework.TestResult result = new junit.framework.TestResult();
        fTest.run(result);
        junit.framework.Assert.assertEquals(fTest.countTestCases(), result.runCount());
        junit.framework.Assert.assertEquals(0, result.errorCount());
        junit.framework.Assert.assertEquals(0, result.failureCount());
    }
}