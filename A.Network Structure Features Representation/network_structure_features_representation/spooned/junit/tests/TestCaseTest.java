package junit.tests;
/**
 * A test case testing the testing framework.
 */
public class TestCaseTest extends junit.framework.TestCase {
    static class TornDown extends junit.framework.TestCase {
        boolean fTornDown = false;

        TornDown(java.lang.String name) {
            super(name);
        }

        protected void tearDown() {
            fTornDown = true;
        }

        protected void runTest() {
            throw new java.lang.Error();
        }
    }

    public TestCaseTest(java.lang.String name) {
        super(name);
    }

    public void testCaseToString() {
        // This test wins the award for twisted snake tail eating while
        // writing self tests. And you thought those weird anonymous
        // inner classes were bad...
        junit.framework.Assert.assertEquals("testCaseToString(junit.tests.TestCaseTest)", toString());
    }

    public void testError() {
        junit.framework.TestCase error = new junit.framework.TestCase("error") {
            protected void runTest() {
                throw new java.lang.Error();
            }
        };
        verifyError(error);
    }

    public void testRunAndTearDownFails() {
        junit.tests.TestCaseTest.TornDown fails = new junit.tests.TestCaseTest.TornDown("fails") {
            protected void tearDown() {
                super.tearDown();
                throw new java.lang.Error();
            }

            protected void runTest() {
                throw new java.lang.Error();
            }
        };
        verifyError(fails);
        junit.framework.Assert.assertTrue(fails.fTornDown);
    }

    public void testSetupFails() {
        junit.framework.TestCase fails = new junit.framework.TestCase("success") {
            protected void setUp() {
                throw new java.lang.Error();
            }

            protected void runTest() {
            }
        };
        verifyError(fails);
    }

    public void testSuccess() {
        junit.framework.TestCase success = new junit.framework.TestCase("success") {
            protected void runTest() {
            }
        };
        verifySuccess(success);
    }

    public void testFailure() {
        junit.framework.TestCase failure = new junit.framework.TestCase("failure") {
            protected void runTest() {
                junit.framework.Assert.fail();
            }
        };
        verifyFailure(failure);
    }

    public void testTearDownAfterError() {
        junit.tests.TestCaseTest.TornDown fails = new junit.tests.TestCaseTest.TornDown("fails");
        verifyError(fails);
        junit.framework.Assert.assertTrue(fails.fTornDown);
    }

    public void testTearDownFails() {
        junit.framework.TestCase fails = new junit.framework.TestCase("success") {
            protected void tearDown() {
                throw new java.lang.Error();
            }

            protected void runTest() {
            }
        };
        verifyError(fails);
    }

    public void testTearDownSetupFails() {
        junit.tests.TestCaseTest.TornDown fails = new junit.tests.TestCaseTest.TornDown("fails") {
            protected void setUp() {
                throw new java.lang.Error();
            }
        };
        verifyError(fails);
        junit.framework.Assert.assertTrue(!fails.fTornDown);
    }

    public void testWasRun() {
        junit.tests.WasRun test = new junit.tests.WasRun("");
        test.run();
        junit.framework.Assert.assertTrue(test.fWasRun);
    }

    void verifyError(junit.framework.TestCase test) {
        junit.framework.TestResult result = test.run();
        junit.framework.Assert.assertTrue(result.runCount() == 1);
        junit.framework.Assert.assertTrue(result.failureCount() == 0);
        junit.framework.Assert.assertTrue(result.errorCount() == 1);
    }

    void verifyFailure(junit.framework.TestCase test) {
        junit.framework.TestResult result = test.run();
        junit.framework.Assert.assertTrue(result.runCount() == 1);
        junit.framework.Assert.assertTrue(result.failureCount() == 1);
        junit.framework.Assert.assertTrue(result.errorCount() == 0);
    }

    void verifySuccess(junit.framework.TestCase test) {
        junit.framework.TestResult result = test.run();
        junit.framework.Assert.assertTrue(result.runCount() == 1);
        junit.framework.Assert.assertTrue(result.failureCount() == 0);
        junit.framework.Assert.assertTrue(result.errorCount() == 0);
    }
}