package junit.runner;
/**
 * A view to show a details about a failure
 */
public interface FailureDetailView {
    /**
     * Returns the component used to present the TraceView
     */
    public java.awt.Component getComponent();

    /**
     * Shows details of a TestFailure
     */
    public void showFailure(junit.framework.TestFailure failure);

    /**
     * Clears the view
     */
    public void clear();
}