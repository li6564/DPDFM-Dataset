package junit.swingui;
/**
 * A view presenting the test failures as a list.
 */
class FailureRunView implements junit.swingui.TestRunView {
    javax.swing.JList fFailureList;

    junit.swingui.TestRunContext fRunContext;

    /**
     * Renders TestFailures in a JList
     */
    static class FailureListCellRenderer extends javax.swing.DefaultListCellRenderer {
        private javax.swing.Icon fFailureIcon;

        private javax.swing.Icon fErrorIcon;

        FailureListCellRenderer() {
            super();
            loadIcons();
        }

        void loadIcons() {
            fFailureIcon = junit.swingui.TestRunner.getIconResource(getClass(), "icons/failure.gif");
            fErrorIcon = junit.swingui.TestRunner.getIconResource(getClass(), "icons/error.gif");
        }

        public java.awt.Component getListCellRendererComponent(javax.swing.JList list, java.lang.Object value, int modelIndex, boolean isSelected, boolean cellHasFocus) {
            junit.framework.TestFailure failure = ((junit.framework.TestFailure) (value));
            java.lang.String text = failure.failedTest().toString();
            java.lang.String msg = failure.thrownException().getMessage();
            if (msg != null)
                text += ":" + junit.runner.BaseTestRunner.truncate(msg);

            if (failure.thrownException() instanceof junit.framework.AssertionFailedError) {
                if (fFailureIcon != null)
                    setIcon(fFailureIcon);

            } else if (fErrorIcon != null)
                setIcon(fErrorIcon);

            java.awt.Component c = super.getListCellRendererComponent(list, text, modelIndex, isSelected, cellHasFocus);
            setText(text);
            setToolTipText(text);
            return c;
        }
    }

    public FailureRunView(junit.swingui.TestRunContext context) {
        fRunContext = context;
        fFailureList = new javax.swing.JList(fRunContext.getFailures());
        fFailureList.setPrototypeCellValue(new junit.framework.TestFailure(new junit.framework.TestCase("dummy") {
            protected void runTest() {
            }
        }, new junit.framework.AssertionFailedError("message")));
        fFailureList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        fFailureList.setCellRenderer(new junit.swingui.FailureRunView.FailureListCellRenderer());
        fFailureList.setToolTipText("Failure - grey X; Error - red X");
        fFailureList.setVisibleRowCount(5);
        fFailureList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                testSelected();
            }
        });
    }

    public junit.framework.Test getSelectedTest() {
        int index = fFailureList.getSelectedIndex();
        if (index == (-1))
            return null;

        javax.swing.ListModel model = fFailureList.getModel();
        junit.framework.TestFailure failure = ((junit.framework.TestFailure) (model.getElementAt(index)));
        return failure.failedTest();
    }

    public void activate() {
        testSelected();
    }

    public void addTab(javax.swing.JTabbedPane pane) {
        javax.swing.JScrollPane sl = new javax.swing.JScrollPane(fFailureList, javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        javax.swing.Icon errorIcon = junit.swingui.TestRunner.getIconResource(getClass(), "icons/error.gif");
        pane.addTab("Failures", errorIcon, sl, "The list of failed tests");
    }

    public void revealFailure(junit.framework.Test failure) {
        fFailureList.setSelectedIndex(0);
    }

    public void aboutToStart(junit.framework.Test suite, junit.framework.TestResult result) {
    }

    public void runFinished(junit.framework.Test suite, junit.framework.TestResult result) {
    }

    protected void testSelected() {
        fRunContext.handleTestSelected(getSelectedTest());
    }
}