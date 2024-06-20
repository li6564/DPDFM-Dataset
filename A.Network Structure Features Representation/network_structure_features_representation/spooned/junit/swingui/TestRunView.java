package junit.swingui;
/**
 * A TestRunView is shown as a page in a tabbed folder.
 * It contributes the page contents and can return
 * the currently selected tests. A TestRunView is
 * notified about the start and finish of a run.
 */
interface TestRunView {
    /**
     * Returns the currently selected Test in the View
     */
    public junit.framework.Test getSelectedTest();

    /**
     * Activates the TestRunView
     */
    public void activate();

    /**
     * Reveals the given failure
     */
    public void revealFailure(junit.framework.Test failure);

    /**
     * Adds the TestRunView to the test run views tab
     */
    public void addTab(javax.swing.JTabbedPane pane);

    /**
     * Informs that the suite is about to start
     */
    public void aboutToStart(junit.framework.Test suite, junit.framework.TestResult result);

    /**
     * Informs that the run of the test suite has finished
     */
    public void runFinished(junit.framework.Test suite, junit.framework.TestResult result);
}