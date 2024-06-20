package junit.swingui;
/**
 * A hierarchical view of a test run.
 * The contents of a test suite is shown
 * as a tree.
 */
class TestHierarchyRunView implements junit.swingui.TestRunView {
    junit.swingui.TestSuitePanel fTreeBrowser;

    junit.swingui.TestRunContext fTestContext;

    public TestHierarchyRunView(junit.swingui.TestRunContext context) {
        fTestContext = context;
        fTreeBrowser = new junit.swingui.TestSuitePanel();
        fTreeBrowser.getTree().addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                testSelected();
            }
        });
    }

    public void addTab(javax.swing.JTabbedPane pane) {
        javax.swing.Icon treeIcon = junit.swingui.TestRunner.getIconResource(getClass(), "icons/hierarchy.gif");
        pane.addTab("Test Hierarchy", treeIcon, fTreeBrowser, "The test hierarchy");
    }

    public junit.framework.Test getSelectedTest() {
        return fTreeBrowser.getSelectedTest();
    }

    public void activate() {
        testSelected();
    }

    public void revealFailure(junit.framework.Test failure) {
        javax.swing.JTree tree = fTreeBrowser.getTree();
        junit.swingui.TestTreeModel model = ((junit.swingui.TestTreeModel) (tree.getModel()));
        java.util.Vector vpath = new java.util.Vector();
        int index = model.findTest(failure, ((junit.framework.Test) (model.getRoot())), vpath);
        if (index >= 0) {
            java.lang.Object[] path = new java.lang.Object[vpath.size() + 1];
            vpath.copyInto(path);
            java.lang.Object last = path[vpath.size() - 1];
            path[vpath.size()] = model.getChild(last, index);
            javax.swing.tree.TreePath selectionPath = new javax.swing.tree.TreePath(path);
            tree.setSelectionPath(selectionPath);
            tree.makeVisible(selectionPath);
        }
    }

    public void aboutToStart(junit.framework.Test suite, junit.framework.TestResult result) {
        fTreeBrowser.showTestTree(suite);
        result.addListener(fTreeBrowser);
    }

    public void runFinished(junit.framework.Test suite, junit.framework.TestResult result) {
        result.removeListener(fTreeBrowser);
    }

    protected void testSelected() {
        fTestContext.handleTestSelected(getSelectedTest());
    }
}