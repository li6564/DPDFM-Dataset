/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.elixir;
/**
 * Stores a leaf node for a UML class diagram
 *
 * @author Chris Seguin
 */
class UMLLeaf implements org.acm.seguin.ide.elixir.TNode {
    private org.acm.seguin.ide.elixir.TNode parent;

    private java.io.File file;

    private org.acm.seguin.ide.elixir.UMLDocManager docManager;

    private java.lang.String packageName;

    /**
     * Constructor for the UMLLeaf object
     *
     * @param parent
     * 		the parent file
     * @param file
     * 		the file
     * @param docManager
     * 		the document manager
     */
    public UMLLeaf(org.acm.seguin.ide.elixir.TNode parent, java.io.File file, org.acm.seguin.ide.elixir.UMLDocManager docManager) {
        this.parent = parent;
        this.file = file;
        this.docManager = docManager;
        loadPackageName();
    }

    /**
     * Sets a new name for the node.
     *
     * @param name
     * 		The new Name value
     */
    public void setName(java.lang.String name) {
        file = new java.io.File(name);
    }

    /**
     * Sets a new parent for the node.
     *
     * @param value
     * 		The new Parent value
     */
    public void setParent(org.acm.seguin.ide.elixir.TParent value) {
        parent = value;
    }

    /**
     * Can we add children to this
     *
     * @return The AllowsChildren value
     */
    public boolean getAllowsChildren() {
        return false;
    }

    /**
     * Return the child from an index
     *
     * @param idx
     * 		Description of Parameter
     * @return The ChildAt value
     */
    public javax.swing.tree.TreeNode getChildAt(int idx) {
        return null;
    }

    /**
     * Count the children
     *
     * @return The ChildCount value
     */
    public int getChildCount() {
        return 0;
    }

    /**
     * Get the full name of the node, usually composed by walking the TreePath
     *  a/b/c etc.
     *
     * @return The FullName value
     */
    public java.lang.String getFullName() {
        try {
            return file.getCanonicalPath();
        } catch (java.io.IOException ioe) {
            return file.getPath();
        }
    }

    /**
     * Get the icon to show in the tree
     *
     * @param expanded
     * 		Description of Parameter
     * @return The Icon value
     */
    public javax.swing.Icon getIcon(boolean expanded) {
        return new org.acm.seguin.ide.common.UMLIcon();
    }

    /**
     * Return the index of a child. This has no children so always returns -1.
     *
     * @param child
     * 		Description of Parameter
     * @return The Index value
     */
    public int getIndex(javax.swing.tree.TreeNode child) {
        return -1;
    }

    /**
     * Get the name to show in the tree
     *
     * @return The Name value
     */
    public java.lang.String getName() {
        return packageName + " Class Diagram";
    }

    /**
     * Gets the Parent attribute of the UMLLeaf object
     *
     * @return The Parent value
     */
    public javax.swing.tree.TreeNode getParent() {
        return parent;
    }

    /**
     * Get the popup menu to show for this node
     *
     * @return The PopupMenu value
     */
    public javax.swing.JPopupMenu getPopupMenu() {
        javax.swing.JPopupMenu result = new javax.swing.JPopupMenu();
        javax.swing.JMenuItem item = new javax.swing.JMenuItem("Open");
        item.addActionListener(new org.acm.seguin.ide.elixir.UMLLeaf.OpenFileAdapter(getFullName()));
        result.add(item);
        return result;
    }

    /**
     * Return the tooltip help to be shown when the mouse is over this node.
     *
     * @return The ToolTipText value
     */
    public java.lang.String getToolTipText() {
        if (packageName.length() > 0) {
            return "The class diagram for the package " + packageName;
        } else {
            return "The class diagram for the top level package";
        }
    }

    /**
     * Return the model which this node belongs to
     *
     * @return The TreeModel value
     */
    public org.acm.seguin.ide.elixir.TModel getTreeModel() {
        return parent.getTreeModel();
    }

    /**
     * Get the TreePath which represents this node
     *
     * @return The TreePath value
     */
    public javax.swing.tree.TreePath getTreePath() {
        return parent.getTreePath().pathByAddingChild(this);
    }

    /**
     * Gets the Leaf attribute of the UMLLeaf object
     *
     * @return The Leaf value
     */
    public boolean isLeaf() {
        return true;
    }

    /**
     * Gets an enumeration of the children
     *
     * @return An empty enumeration
     */
    public java.util.Enumeration children() {
        java.util.Vector result = new java.util.Vector();
        return result.elements();
    }

    /**
     * Perform double-click action. Hopefully this will open the file.
     */
    public void doDoubleClick() {
        org.acm.seguin.ide.elixir.FrameManager.current().open(getFullName());
    }

    /**
     * Notify the TreeModel and hence the TreeModel listeners that this node has
     *  changed
     */
    public void fireChanged() {
    }

    /**
     * Sort the children of this node based on the comparator. Since there are
     *  no children, this does nothing.
     *
     * @param c
     * 		the comparator
     */
    public void sortChildren(org.acm.seguin.ide.elixir.SortUtil.Comparator c) {
    }

    /**
     * Return the name of the node as its String representation
     *
     * @return Gets the string representation of this node
     */
    public java.lang.String toString() {
        return getName();
    }

    /**
     * Loads the package name from the file
     */
    private void loadPackageName() {
        try {
            packageName = "Unknown";
            java.io.BufferedReader input = new java.io.BufferedReader(new java.io.FileReader(file));
            java.lang.String line = input.readLine();
            if (line.charAt(0) == 'V') {
                java.util.StringTokenizer tok = new java.util.StringTokenizer(line, "[:]");
                if (tok.hasMoreTokens()) {
                    // Skip the first - it is the letter v
                    java.lang.String temp = tok.nextToken();
                    if (tok.hasMoreTokens()) {
                        // Skip the second - it is the version (1.1)
                        temp = tok.nextToken();
                        if (tok.hasMoreTokens()) {
                            // Third item is the package name
                            packageName = tok.nextToken();
                        }
                    }
                }
            }
            input.close();
        } catch (java.io.IOException ioe) {
        }
    }

    /**
     * Opens a file when the button is pressed
     *
     * @author Chris Seguin
     */
    private class OpenFileAdapter implements java.awt.event.ActionListener {
        private java.lang.String name;

        /**
         * Constructor for the OpenFileAdapter object
         *
         * @param init
         * 		the name of the file
         */
        public OpenFileAdapter(java.lang.String init) {
            name = init;
        }

        /**
         * Opens the file
         *
         * @param evt
         * 		the action event
         */
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            org.acm.seguin.ide.elixir.FrameManager.current().open(name);
        }
    }
}