package junit.tests;
/**
 * A test case testing the extensions to the testing framework.
 */
public class ExtensionTest extends junit.framework.TestCase {
    static class TornDown extends junit.extensions.TestSetup {
        boolean fTornDown = false;

        TornDown(junit.framework.Test test) {
            super(test);
        }

        protected void tearDown() {
            fTornDown = true;
        }
    }

    public ExtensionTest(java.lang.String name) {
        super(name);
    }

    public void testRunningErrorInTestSetup() {
        junit.framework.TestCase test = new junit.framework.TestCase("failure") {
            public void runTest() {
                junit.framework.Assert.fail();
            }
        };
        junit.extensions.TestSetup wrapper = new junit.extensions.TestSetup(test);
        junit.framework.TestResult result = new junit.framework.TestResult();
        wrapper.run(result);
        junit.framework.Assert.assertTrue(!result.wasSuccessful());
    }

    public void testRunningErrorsInTestSetup() {
        junit.framework.TestCase failure = new junit.framework.TestCase("failure") {
            public void runTest() {
                junit.framework.Assert.fail();
            }
        };
        junit.framework.TestCase error = new junit.framework.TestCase("error") {
            public void runTest() {
                throw new java.lang.Error();
            }
        };
        junit.framework.TestSuite suite = new junit.framework.TestSuite();
        suite.addTest(failure);
        suite.addTest(error);
        junit.extensions.TestSetup wrapper = new junit.extensions.TestSetup(suite);
        junit.framework.TestResult result = new junit.framework.TestResult();
        wrapper.run(result);
        junit.framework.Assert.assertEquals(1, result.failureCount());
        junit.framework.Assert.assertEquals(1, result.errorCount());
    }

    public void testSetupErrorDontTearDown() {
        junit.tests.WasRun test = new junit.tests.WasRun("");
        junit.tests.ExtensionTest.TornDown wrapper = new junit.tests.ExtensionTest.TornDown(test) {
            public void setUp() {
                junit.framework.Assert.fail();
            }
        };
        junit.framework.TestResult result = new junit.framework.TestResult();
        wrapper.run(result);
        junit.framework.Assert.assertTrue(!wrapper.fTornDown);
    }

    public void testSetupErrorInTestSetup() {
        junit.tests.WasRun test = new junit.tests.WasRun("");
        junit.extensions.TestSetup wrapper = new junit.extensions.TestSetup(test) {
            public void setUp() {
                junit.framework.Assert.fail();
            }
        };
        junit.framework.TestResult result = new junit.framework.TestResult();
        wrapper.run(result);
        junit.framework.Assert.assertTrue(!test.fWasRun);
        junit.framework.Assert.assertTrue(!result.wasSuccessful());
    }
}