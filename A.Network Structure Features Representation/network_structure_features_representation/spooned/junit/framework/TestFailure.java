package junit.framework;
/**
 * A <code>TestFailure</code> collects a failed test together with
 * the caught exception.
 *
 * @see TestResult
 */
public class TestFailure extends java.lang.Object {
    protected junit.framework.Test fFailedTest;

    protected java.lang.Throwable fThrownException;

    /**
     * Constructs a TestFailure with the given test and exception.
     */
    public TestFailure(junit.framework.Test failedTest, java.lang.Throwable thrownException) {
        fFailedTest = failedTest;
        fThrownException = thrownException;
    }

    /**
     * Gets the failed test.
     */
    public junit.framework.Test failedTest() {
        return fFailedTest;
    }

    /**
     * Gets the thrown exception.
     */
    public java.lang.Throwable thrownException() {
        return fThrownException;
    }

    /**
     * Returns a short description of the failure.
     */
    public java.lang.String toString() {
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        buffer.append((fFailedTest + ": ") + fThrownException.getMessage());
        return buffer.toString();
    }
}