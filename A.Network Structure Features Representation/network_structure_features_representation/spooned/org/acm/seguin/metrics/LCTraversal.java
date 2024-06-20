/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.metrics;
/**
 * Counts the number of lines in a file
 *
 * @author Chris Seguin
 * @created June 30, 1999
 */
public class LCTraversal extends org.acm.seguin.io.DirectoryTreeTraversal {
    // Instance Variables
    private long total;

    private int fileCount;

    /**
     * Traverses a directory tree structure
     *
     * @param init
     * 		the initial directory
     */
    public LCTraversal(java.lang.String init) {
        super(init);
        total = 0;
        fileCount = 0;
    }

    /**
     * Starts the tree traversal
     */
    public void go() {
        super.go();
        long count = total;
        if (count < 10) {
            java.lang.System.out.print("      " + count);
        } else if (count < 100) {
            java.lang.System.out.print("     " + count);
        } else if (count < 1000) {
            java.lang.System.out.print("    " + count);
        } else if (count < 10000) {
            java.lang.System.out.print("   " + count);
        } else if (count < 100000) {
            java.lang.System.out.print("  " + count);
        } else {
            java.lang.System.out.print(" " + count);
        }
        java.lang.System.out.println((" total lines in " + fileCount) + " files");
        double top = count;
        double bottom = fileCount;
        java.lang.System.out.println("Average:  " + (top / bottom));
    }

    /**
     * Determines if this file should be handled by this traversal
     *
     * @param currentFile
     * 		the current file
     * @return true if the file should be handled
     */
    protected boolean isTarget(java.io.File currentFile) {
        java.lang.String filename = currentFile.getName().toLowerCase();
        return ((filename.indexOf(".java") >= 0) || (filename.indexOf(".h") >= 0)) || (filename.indexOf(".cpp") >= 0);
    }

    /**
     * Visits the current file
     *
     * @param currentFile
     * 		the current file
     */
    protected void visit(java.io.File currentFile) {
        int count = new org.acm.seguin.metrics.LineCounter(currentFile).printMessage();
        total += count;
        fileCount++;
    }

    /**
     * Main program
     *
     * @param args
     * 		Command line arguments
     */
    public static void main(java.lang.String[] args) {
        // Make sure everything is installed properly
        new org.acm.seguin.tools.install.RefactoryInstaller(false).run();
        if (args.length == 0) {
            java.lang.System.out.println("Syntax:  java org.acm.seguin.metrics.LCTraversal <directory>");
            return;
        }
        new org.acm.seguin.metrics.LCTraversal(args[0]).go();
    }
}