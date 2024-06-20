package junit.swingui;
/**
 * The interface for accessing the Test run context. Test run views
 * should use this interface rather than accessing the TestRunner
 * directly.
 */
public interface TestRunContext {
    /**
     * Handles the selection of a Test.
     */
    public void handleTestSelected(junit.framework.Test test);

    /**
     * Returns the failure model
     */
    public javax.swing.ListModel getFailures();
}