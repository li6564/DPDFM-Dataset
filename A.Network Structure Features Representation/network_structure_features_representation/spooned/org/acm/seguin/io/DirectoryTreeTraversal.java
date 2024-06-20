/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.io;
/**
 * Traverses a directory structure and backups all java files found
 *
 * @author Chris Seguin
 * @date May 12, 1999
 */
public abstract class DirectoryTreeTraversal {
    // Instance Variables
    private java.io.File startingPoint;

    /**
     * Traverses a directory tree structure
     *
     * @param init
     * 		the initial directory
     */
    public DirectoryTreeTraversal(java.lang.String init) {
        startingPoint = new java.io.File(init);
    }

    /**
     * Starts the tree traversal
     */
    public void go() {
        if (startingPoint.exists()) {
            traverse(startingPoint);
        }
    }

    /**
     * Determines if this file should be handled by this traversal
     *
     * @param currentFile
     * 		the current file
     * @return true if the file should be handled
     */
    protected abstract boolean isTarget(java.io.File currentFile);

    /**
     * Gets the Allowed attribute of the DirectoryTreeTraversal object
     *
     * @param currentDirectory
     * 		Description of Parameter
     * @return The Allowed value
     */
    protected boolean isAllowed(java.io.File currentDirectory) {
        return true;
    }

    /**
     * Starts the tree traversal
     *
     * @param current
     * 		Description of Parameter
     */
    protected void traverse(java.io.File current) {
        if (current.isDirectory()) {
            if (isAllowed(current)) {
                arriveAtDir(current);
                java.lang.String[] list = current.list();
                for (int ndx = 0; ndx < list.length; ndx++) {
                    traverse(new java.io.File(current, list[ndx]));
                }
                leaveDir(current);
            }
        } else if (isTarget(current)) {
            visit(current);
        }
    }

    /**
     * Visits the current file
     *
     * @param currentFile
     * 		the current file
     */
    protected abstract void visit(java.io.File currentFile);

    /**
     * Program called when we arrive at a directory
     *
     * @param currentFile
     * 		the current file
     */
    protected void arriveAtDir(java.io.File currentFile) {
    }

    /**
     * Program called when we arrive at a directory
     *
     * @param currentFile
     * 		the current file
     */
    protected void leaveDir(java.io.File currentFile) {
    }
}