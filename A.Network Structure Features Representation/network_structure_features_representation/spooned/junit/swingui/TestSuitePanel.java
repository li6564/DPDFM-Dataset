package junit.swingui;
/**
 * A Panel showing a test suite as a tree.
 */
class TestSuitePanel extends javax.swing.JPanel implements junit.framework.TestListener {
    private javax.swing.JTree fTree;

    private javax.swing.JScrollPane fScrollTree;

    private junit.swingui.TestTreeModel fModel;

    static class TestTreeCellRenderer extends javax.swing.tree.DefaultTreeCellRenderer {
        private javax.swing.Icon fErrorIcon;

        private javax.swing.Icon fOkIcon;

        private javax.swing.Icon fFailureIcon;

        TestTreeCellRenderer() {
            super();
            loadIcons();
        }

        void loadIcons() {
            fErrorIcon = junit.swingui.TestRunner.getIconResource(getClass(), "icons/error.gif");
            fOkIcon = junit.swingui.TestRunner.getIconResource(getClass(), "icons/ok.gif");
            fFailureIcon = junit.swingui.TestRunner.getIconResource(getClass(), "icons/failure.gif");
        }

        java.lang.String stripParenthesis(java.lang.Object o) {
            java.lang.String text = o.toString();
            int pos = text.indexOf('(');
            if (pos < 1)
                return text;

            return text.substring(0, pos);
        }

        public java.awt.Component getTreeCellRendererComponent(javax.swing.JTree tree, java.lang.Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            java.awt.Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            javax.swing.tree.TreeModel model = tree.getModel();
            if (model instanceof junit.swingui.TestTreeModel) {
                junit.swingui.TestTreeModel testModel = ((junit.swingui.TestTreeModel) (model));
                junit.framework.Test t = ((junit.framework.Test) (value));
                java.lang.String s = "";
                if (testModel.isFailure(t)) {
                    if (fFailureIcon != null)
                        setIcon(fFailureIcon);

                    s = " - Failed";
                } else if (testModel.isError(t)) {
                    if (fErrorIcon != null)
                        setIcon(fErrorIcon);

                    s = " - Error";
                } else if (testModel.wasRun(t)) {
                    if (fOkIcon != null)
                        setIcon(fOkIcon);

                    s = " - Passed";
                }
                if (c instanceof javax.swing.JComponent)
                    ((javax.swing.JComponent) (c)).setToolTipText(getText() + s);

            }
            setText(stripParenthesis(value));
            return c;
        }
    }

    public TestSuitePanel() {
        super(new java.awt.BorderLayout());
        setPreferredSize(new java.awt.Dimension(300, 100));
        fTree = new javax.swing.JTree();
        fTree.setModel(null);
        fTree.setRowHeight(20);
        javax.swing.ToolTipManager.sharedInstance().registerComponent(fTree);
        fTree.putClientProperty("JTree.lineStyle", "Angled");
        fScrollTree = new javax.swing.JScrollPane(fTree);
        add(fScrollTree, java.awt.BorderLayout.CENTER);
    }

    public void addError(final junit.framework.Test test, final java.lang.Throwable t) {
        fModel.addError(test);
        fireTestChanged(test, true);
    }

    public void addFailure(final junit.framework.Test test, final junit.framework.AssertionFailedError t) {
        fModel.addFailure(test);
        fireTestChanged(test, true);
    }

    /**
     * A test ended.
     */
    public void endTest(junit.framework.Test test) {
        fModel.addRunTest(test);
        fireTestChanged(test, false);
    }

    /**
     * A test started.
     */
    public void startTest(junit.framework.Test test) {
    }

    /**
     * Returns the selected test or null if multiple or none is selected
     */
    public junit.framework.Test getSelectedTest() {
        javax.swing.tree.TreePath[] paths = fTree.getSelectionPaths();
        if ((paths != null) && (paths.length == 1))
            return ((junit.framework.Test) (paths[0].getLastPathComponent()));

        return null;
    }

    /**
     * Returns the Tree
     */
    public javax.swing.JTree getTree() {
        return fTree;
    }

    /**
     * Shows the test hierarchy starting at the given test
     */
    public void showTestTree(junit.framework.Test root) {
        fModel = new junit.swingui.TestTreeModel(root);
        fTree.setModel(fModel);
        fTree.setCellRenderer(new junit.swingui.TestSuitePanel.TestTreeCellRenderer());
    }

    private void fireTestChanged(final junit.framework.Test test, final boolean expand) {
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            public void run() {
                java.util.Vector vpath = new java.util.Vector();
                int index = fModel.findTest(test, ((junit.framework.Test) (fModel.getRoot())), vpath);
                if (index >= 0) {
                    java.lang.Object[] path = new java.lang.Object[vpath.size()];
                    vpath.copyInto(path);
                    javax.swing.tree.TreePath treePath = new javax.swing.tree.TreePath(path);
                    fModel.fireNodeChanged(treePath, index);
                    if (expand) {
                        java.lang.Object[] fullPath = new java.lang.Object[vpath.size() + 1];
                        vpath.copyInto(fullPath);
                        fullPath[vpath.size()] = fModel.getChild(treePath.getLastPathComponent(), index);
                        javax.swing.tree.TreePath fullTreePath = new javax.swing.tree.TreePath(fullPath);
                        fTree.scrollPathToVisible(fullTreePath);
                    }
                }
            }
        });
    }
}