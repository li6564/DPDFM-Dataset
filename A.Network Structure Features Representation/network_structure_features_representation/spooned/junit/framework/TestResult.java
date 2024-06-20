package junit.framework;
/**
 * A <code>TestResult</code> collects the results of executing
 * a test case. It is an instance of the Collecting Parameter pattern.
 * The test framework distinguishes between <i>failures</i> and <i>errors</i>.
 * A failure is anticipated and checked for with assertions. Errors are
 * unanticipated problems like an <code>ArrayIndexOutOfBoundsException</code>.
 *
 * @see Test
 */
public class TestResult extends java.lang.Object {
    protected java.util.Vector fFailures;

    protected java.util.Vector fErrors;

    protected java.util.Vector fListeners;

    protected int fRunTests;

    private boolean fStop;

    public TestResult() {
        fFailures = new java.util.Vector();
        fErrors = new java.util.Vector();
        fListeners = new java.util.Vector();
        fRunTests = 0;
        fStop = false;
    }

    /**
     * Adds an error to the list of errors. The passed in exception
     * caused the error.
     */
    public synchronized void addError(junit.framework.Test test, java.lang.Throwable t) {
        fErrors.addElement(new junit.framework.TestFailure(test, t));
        for (java.util.Enumeration e = cloneListeners().elements(); e.hasMoreElements();) {
            ((junit.framework.TestListener) (e.nextElement())).addError(test, t);
        }
    }

    /**
     * Adds a failure to the list of failures. The passed in exception
     * caused the failure.
     */
    public synchronized void addFailure(junit.framework.Test test, junit.framework.AssertionFailedError t) {
        fFailures.addElement(new junit.framework.TestFailure(test, t));
        for (java.util.Enumeration e = cloneListeners().elements(); e.hasMoreElements();) {
            ((junit.framework.TestListener) (e.nextElement())).addFailure(test, t);
        }
    }

    /**
     * Registers a TestListener
     */
    public synchronized void addListener(junit.framework.TestListener listener) {
        fListeners.addElement(listener);
    }

    /**
     * Unregisters a TestListener
     */
    public synchronized void removeListener(junit.framework.TestListener listener) {
        fListeners.removeElement(listener);
    }

    /**
     * Returns a copy of the listeners.
     */
    private synchronized java.util.Vector cloneListeners() {
        return ((java.util.Vector) (fListeners.clone()));
    }

    /**
     * Informs the result that a test was completed.
     */
    public void endTest(junit.framework.Test test) {
        for (java.util.Enumeration e = cloneListeners().elements(); e.hasMoreElements();) {
            ((junit.framework.TestListener) (e.nextElement())).endTest(test);
        }
    }

    /**
     * Gets the number of detected errors.
     */
    public synchronized int errorCount() {
        return fErrors.size();
    }

    /**
     * Returns an Enumeration for the errors
     */
    public synchronized java.util.Enumeration errors() {
        return fErrors.elements();
    }

    /**
     * Gets the number of detected failures.
     */
    public synchronized int failureCount() {
        return fFailures.size();
    }

    /**
     * Returns an Enumeration for the failures
     */
    public synchronized java.util.Enumeration failures() {
        return fFailures.elements();
    }

    /**
     * Runs a TestCase.
     */
    protected void run(final junit.framework.TestCase test) {
        startTest(test);
        junit.framework.Protectable p = new junit.framework.Protectable() {
            public void protect() throws java.lang.Throwable {
                test.runBare();
            }
        };
        runProtected(test, p);
        endTest(test);
    }

    /**
     * Gets the number of run tests.
     */
    public synchronized int runCount() {
        return fRunTests;
    }

    /**
     * Runs a TestCase.
     */
    public void runProtected(final junit.framework.Test test, junit.framework.Protectable p) {
        try {
            p.protect();
        } catch (junit.framework.AssertionFailedError e) {
            addFailure(test, e);
        } catch (java.lang.ThreadDeath e) {
            // don't catch ThreadDeath by accident
            throw e;
        } catch (java.lang.Throwable e) {
            addError(test, e);
        }
    }

    /**
     * Gets the number of run tests.
     *
     * @deprecated use <code>runCount</code> instead
     */
    public synchronized int runTests() {
        return runCount();
    }

    /**
     * Checks whether the test run should stop
     */
    public synchronized boolean shouldStop() {
        return fStop;
    }

    /**
     * Informs the result that a test will be started.
     */
    public void startTest(junit.framework.Test test) {
        final int count = test.countTestCases();
        synchronized(this) {
            fRunTests += count;
        }
        for (java.util.Enumeration e = cloneListeners().elements(); e.hasMoreElements();) {
            ((junit.framework.TestListener) (e.nextElement())).startTest(test);
        }
    }

    /**
     * Marks that the test run should stop.
     */
    public synchronized void stop() {
        fStop = true;
    }

    /**
     * Gets the number of detected errors.
     *
     * @deprecated use <code>errorCount</code> instead
     */
    public synchronized int testErrors() {
        return errorCount();
    }

    /**
     * Gets the number of detected failures.
     *
     * @deprecated use <code>failureCount</code> instead
     */
    public synchronized int testFailures() {
        return failureCount();
    }

    /**
     * Returns whether the entire test was successful or not.
     */
    public synchronized boolean wasSuccessful() {
        return (testFailures() == 0) && (testErrors() == 0);
    }
}