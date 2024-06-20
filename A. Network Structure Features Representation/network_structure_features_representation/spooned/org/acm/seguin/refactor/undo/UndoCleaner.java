/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.undo;
/**
 * Clase responsible for cleaning up all the undo files that are left on the
 *  hard disk.
 *
 * @author Chris Seguin
 */
public class UndoCleaner extends org.acm.seguin.io.DirectoryTreeTraversal {
    /**
     * Constructor for the UndoCleaner object
     *
     * @param dir
     * 		Description of Parameter
     */
    public UndoCleaner(java.lang.String dir) {
        super(dir);
        org.acm.seguin.refactor.undo.UndoStack.get().delete();
    }

    /**
     * Determines if this file should be handled by this traversal
     *
     * @param currentFile
     * 		the current file
     * @return true if the file should be handled
     */
    protected boolean isTarget(java.io.File currentFile) {
        java.lang.String filename = currentFile.getName();
        int index = filename.indexOf(".java.");
        if (index < 0) {
            return false;
        }
        try {
            int value = java.lang.Integer.parseInt(filename.substring(index + 6));
            return true;
        } catch (java.lang.NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Visits the current file
     *
     * @param currentFile
     * 		the current file
     */
    protected void visit(java.io.File currentFile) {
        currentFile.delete();
    }

    /**
     * The main program for the UndoCleaner class
     *
     * @param args
     * 		The command line arguments
     */
    public static void main(java.lang.String[] args) {
        if (args.length > 0) {
            new org.acm.seguin.refactor.undo.UndoCleaner(args[0]).go();
        } else {
            java.lang.String dir = java.lang.System.getProperty("user.dir");
            new org.acm.seguin.refactor.undo.UndoCleaner(dir).go();
        }
    }
}