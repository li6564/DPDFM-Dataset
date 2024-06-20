package junit.swingui;
/**
 * A tree model for a Test.
 */
class TestTreeModel implements javax.swing.tree.TreeModel {
    private junit.framework.Test fRoot;

    private java.util.Vector fModelListeners = new java.util.Vector();

    private java.util.Hashtable fFailures = new java.util.Hashtable();

    private java.util.Hashtable fErrors = new java.util.Hashtable();

    private java.util.Hashtable fRunTests = new java.util.Hashtable();

    /**
     * Constructs a tree model with the given test as its root.
     */
    public TestTreeModel(junit.framework.Test root) {
        super();
        java.lang.System.err.println(this.fFailures.getClass());
        fRoot = root;
    }

    /**
     * adds a TreeModelListener
     */
    public void addTreeModelListener(javax.swing.event.TreeModelListener l) {
        if (!fModelListeners.contains(l))
            fModelListeners.addElement(l);

    }

    /**
     * Removes a TestModelListener
     */
    public void removeTreeModelListener(javax.swing.event.TreeModelListener l) {
        fModelListeners.removeElement(l);
    }

    /**
     * Finds the path to a test. Returns the index of the test in its
     * parent test suite.
     */
    public int findTest(junit.framework.Test target, junit.framework.Test node, java.util.Vector path) {
        if (target.equals(node))
            return 0;

        junit.framework.TestSuite suite = isTestSuite(node);
        for (int i = 0; i < getChildCount(node); i++) {
            junit.framework.Test t = suite.testAt(i);
            int index = findTest(target, t, path);
            if (index >= 0) {
                path.insertElementAt(node, 0);
                if (path.size() == 1)
                    return i;

                return index;
            }
        }
        return -1;
    }

    /**
     * Fires a node changed event
     */
    public void fireNodeChanged(javax.swing.tree.TreePath path, int index) {
        int[] indices = new int[]{ index };
        java.lang.Object[] changedChildren = new java.lang.Object[]{ getChild(path.getLastPathComponent(), index) };
        javax.swing.event.TreeModelEvent event = new javax.swing.event.TreeModelEvent(this, path, indices, changedChildren);
        java.util.Enumeration e = fModelListeners.elements();
        while (e.hasMoreElements()) {
            javax.swing.event.TreeModelListener l = ((javax.swing.event.TreeModelListener) (e.nextElement()));
            l.treeNodesChanged(event);
        } 
    }

    /**
     * Gets the test at the given index
     */
    public java.lang.Object getChild(java.lang.Object parent, int index) {
        junit.framework.TestSuite suite = isTestSuite(parent);
        if (suite != null)
            return suite.testAt(index);

        return null;
    }

    /**
     * Gets the number of tests.
     */
    public int getChildCount(java.lang.Object parent) {
        junit.framework.TestSuite suite = isTestSuite(parent);
        if (suite != null)
            return suite.testCount();

        return 0;
    }

    /**
     * Gets the index of a test in a test suite
     */
    public int getIndexOfChild(java.lang.Object parent, java.lang.Object child) {
        junit.framework.TestSuite suite = isTestSuite(parent);
        if (suite != null) {
            int i = 0;
            for (java.util.Enumeration e = suite.tests(); e.hasMoreElements(); i++) {
                if (child.equals(((junit.framework.Test) (e.nextElement()))))
                    return i;

            }
        }
        return -1;
    }

    /**
     * Returns the root of the tree
     */
    public java.lang.Object getRoot() {
        return fRoot;
    }

    /**
     * Tests if the test is a leaf.
     */
    public boolean isLeaf(java.lang.Object node) {
        return isTestSuite(node) == null;
    }

    /**
     * Tests if the node is a TestSuite.
     */
    junit.framework.TestSuite isTestSuite(java.lang.Object node) {
        if (node instanceof junit.framework.TestSuite)
            return ((junit.framework.TestSuite) (node));

        if (node instanceof junit.extensions.TestDecorator) {
            junit.framework.Test baseTest = ((junit.extensions.TestDecorator) (node)).getTest();
            return isTestSuite(baseTest);
        }
        return null;
    }

    /**
     * Called when the value of the model object was changed in the view
     */
    public void valueForPathChanged(javax.swing.tree.TreePath path, java.lang.Object newValue) {
        // we don't support direct editing of the model
        java.lang.System.out.println("TreeModel.valueForPathChanged: not implemented");
    }

    /**
     * Remembers a test failure
     */
    void addFailure(junit.framework.Test t) {
        fFailures.put(t, t);
    }

    /**
     * Remembers a test error
     */
    void addError(junit.framework.Test t) {
        fErrors.put(t, t);
    }

    /**
     * Remembers that a test was run
     */
    void addRunTest(junit.framework.Test t) {
        fRunTests.put(t, t);
    }

    /**
     * Returns whether a test was run
     */
    boolean wasRun(junit.framework.Test t) {
        return fRunTests.get(t) != null;
    }

    /**
     * Tests whether a test was an error
     */
    boolean isError(junit.framework.Test t) {
        return (fErrors != null) && (fErrors.get(t) != null);
    }

    /**
     * Tests whether a test was a failure
     */
    boolean isFailure(junit.framework.Test t) {
        return (fFailures != null) && (fFailures.get(t) != null);
    }

    /**
     * Resets the test results
     */
    void resetResults() {
        fFailures = new java.util.Hashtable();
        fRunTests = new java.util.Hashtable();
        fErrors = new java.util.Hashtable();
    }
}