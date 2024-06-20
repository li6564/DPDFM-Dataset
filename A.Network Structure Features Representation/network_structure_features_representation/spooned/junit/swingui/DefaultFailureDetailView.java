package junit.swingui;
/**
 * A view that shows a stack trace of a failure
 */
class DefaultFailureDetailView implements junit.runner.FailureDetailView {
    javax.swing.JTextArea fTextArea;

    /**
     * Returns the component used to present the trace
     */
    public java.awt.Component getComponent() {
        if (fTextArea == null) {
            fTextArea = new javax.swing.JTextArea();
            fTextArea.setRows(5);
            fTextArea.setTabSize(0);
            fTextArea.setEditable(false);
        }
        return fTextArea;
    }

    /**
     * Shows a TestFailure
     */
    public void showFailure(junit.framework.TestFailure failure) {
        fTextArea.setText(junit.runner.BaseTestRunner.getFilteredTrace(failure.thrownException()));
        fTextArea.select(0, 0);
    }

    public void clear() {
        fTextArea.setText("");
    }
}