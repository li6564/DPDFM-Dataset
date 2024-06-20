package junit.framework;
/**
 * A Listener for test progress
 */
public interface TestListener {
    /**
     * An error occurred.
     */
    public void addError(junit.framework.Test test, java.lang.Throwable t);

    /**
     * A failure occurred.
     */
    public void addFailure(junit.framework.Test test, junit.framework.AssertionFailedError t);

    /**
     * A test ended.
     */
    public void endTest(junit.framework.Test test);

    /**
     * A test started.
     */
    public void startTest(junit.framework.Test test);
}