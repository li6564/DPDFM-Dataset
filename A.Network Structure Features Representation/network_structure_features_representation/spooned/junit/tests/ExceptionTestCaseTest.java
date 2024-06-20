package junit.tests;
public class ExceptionTestCaseTest extends junit.framework.TestCase {
    public static class ThrowExceptionTestCase extends junit.extensions.ExceptionTestCase {
        public ThrowExceptionTestCase(java.lang.String name, java.lang.Class exception) {
            super(name, exception);
        }

        public void test() {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    public static class ThrowRuntimeExceptionTestCase extends junit.extensions.ExceptionTestCase {
        public ThrowRuntimeExceptionTestCase(java.lang.String name, java.lang.Class exception) {
            super(name, exception);
        }

        public void test() {
            throw new java.lang.RuntimeException();
        }
    }

    public static class ThrowNoExceptionTestCase extends junit.extensions.ExceptionTestCase {
        public ThrowNoExceptionTestCase(java.lang.String name, java.lang.Class exception) {
            super(name, exception);
        }

        public void test() {
        }
    }

    public ExceptionTestCaseTest(java.lang.String name) {
        super(name);
    }

    public void testExceptionSubclass() {
        junit.extensions.ExceptionTestCase test = new junit.tests.ExceptionTestCaseTest.ThrowExceptionTestCase("test", java.lang.IndexOutOfBoundsException.class);
        junit.framework.TestResult result = test.run();
        junit.framework.Assert.assertEquals(1, result.runCount());
        junit.framework.Assert.assertTrue(result.wasSuccessful());
    }

    public void testExceptionTest() {
        junit.extensions.ExceptionTestCase test = new junit.tests.ExceptionTestCaseTest.ThrowExceptionTestCase("test", java.lang.IndexOutOfBoundsException.class);
        junit.framework.TestResult result = test.run();
        junit.framework.Assert.assertEquals(1, result.runCount());
        junit.framework.Assert.assertTrue(result.wasSuccessful());
    }

    public void testFailure() {
        junit.extensions.ExceptionTestCase test = new junit.tests.ExceptionTestCaseTest.ThrowRuntimeExceptionTestCase("test", java.lang.IndexOutOfBoundsException.class);
        junit.framework.TestResult result = test.run();
        junit.framework.Assert.assertEquals(1, result.runCount());
        junit.framework.Assert.assertEquals(1, result.errorCount());
    }

    public void testNoException() {
        junit.extensions.ExceptionTestCase test = new junit.tests.ExceptionTestCaseTest.ThrowNoExceptionTestCase("test", java.lang.Exception.class);
        junit.framework.TestResult result = test.run();
        junit.framework.Assert.assertEquals(1, result.runCount());
        junit.framework.Assert.assertEquals(1, result.failureCount());
    }

    public void testWrongException() {
        junit.extensions.ExceptionTestCase test = new junit.tests.ExceptionTestCaseTest.ThrowRuntimeExceptionTestCase("test", java.lang.IndexOutOfBoundsException.class);
        junit.framework.TestResult result = test.run();
        junit.framework.Assert.assertEquals(1, result.runCount());
        junit.framework.Assert.assertEquals(1, result.errorCount());
    }
}