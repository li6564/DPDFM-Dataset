/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary;
/**
 * Summarizes a directory structure
 *
 * @author Chris Seguin
 * @created June 6, 1999
 */
public class SummaryTraversal extends org.acm.seguin.io.DirectoryTreeTraversal {
    private java.lang.String root;

    private java.lang.String blockDirectories;

    private org.acm.seguin.summary.load.LoadStatus status;

    private static org.acm.seguin.summary.FrameworkLoader framework = null;

    /**
     * Traverses a directory tree structure and generates a summary of the
     *  classes.
     *
     * @param init
     * 		the initial directory
     */
    public SummaryTraversal(java.lang.String init) {
        this(init, new org.acm.seguin.summary.load.SwingLoadStatus());
    }

    /**
     * Traverses a directory tree structure and generates a summary of the
     *  classes.
     *
     * @param init
     * 		the initial directory
     * @param initStatus
     * 		Description of Parameter
     */
    public SummaryTraversal(java.lang.String init, org.acm.seguin.summary.load.LoadStatus initStatus) {
        super(init);
        root = init;
        status = initStatus;
        if (org.acm.seguin.summary.SummaryTraversal.framework == null) {
            org.acm.seguin.summary.SummaryTraversal.framework = new org.acm.seguin.summary.FrameworkFileSummaryLoader(status);
        }
        try {
            org.acm.seguin.util.FileSettings umlBundle = org.acm.seguin.util.FileSettings.getSettings("Refactory", "uml");
            umlBundle.setContinuallyReload(true);
            blockDirectories = umlBundle.getString("skip.dir");
            if (blockDirectories == null) {
                blockDirectories = "";
            } else {
                blockDirectories = blockDirectories.trim();
                if (blockDirectories == null) {
                    blockDirectories = "";
                }
            }
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            blockDirectories = "";
        }
    }

    /**
     * Method that starts the traversal to generate the summaries.
     */
    public void go() {
        org.acm.seguin.summary.SummaryTraversal.framework.run();
        java.io.File temp = new java.io.File(root);
        java.lang.String dir = null;
        try {
            dir = temp.getCanonicalPath();
        } catch (java.io.IOException ioe) {
            dir = temp.getPath();
        }
        status.setRoot(dir);
        org.acm.seguin.summary.FileSummary.removeDeletedSummaries();
        super.go();
        status.done();
    }

    /**
     * Determines if this file should be handled by this traversal
     *
     * @param currentFile
     * 		the current file
     * @return true if the file should be handled
     */
    protected boolean isTarget(java.io.File currentFile) {
        java.lang.String name = currentFile.getName();
        int dot = name.indexOf(".");
        int java = name.indexOf(".java");
        return (dot == java) && name.endsWith(".java");
    }

    /**
     * Are we allowed to traverse this directory?
     *
     * @param currentDirectory
     * 		the directory that we are about to enter
     * @return true if we are allowed to enter it
     */
    protected boolean isAllowed(java.io.File currentDirectory) {
        if ((blockDirectories == null) || (blockDirectories.length() == 0)) {
            return true;
        }
        java.util.StringTokenizer tok = new java.util.StringTokenizer(blockDirectories, java.io.File.pathSeparator);
        while (tok.hasMoreTokens()) {
            java.lang.String next = tok.nextToken();
            if (currentDirectory.getName().indexOf(next) >= 0) {
                return false;
            }
        } 
        return true;
    }

    /**
     * Visits the current file
     *
     * @param currentFile
     * 		the current file
     */
    protected void visit(java.io.File currentFile) {
        try {
            status.setCurrentFile(currentFile.getPath());
            org.acm.seguin.summary.FileSummary.getFileSummary(currentFile);
            java.lang.Thread.currentThread().yield();
        } catch (java.lang.Throwable oops) {
            java.lang.System.out.println("\nError loading:  " + currentFile.getName());
            oops.printStackTrace(java.lang.System.out);
        }
    }

    /**
     * Sets the framework loader
     *
     * @param value
     * 		The new FrameworkLoader value
     */
    public static void setFrameworkLoader(org.acm.seguin.summary.FrameworkLoader value) {
        org.acm.seguin.summary.SummaryTraversal.framework = value;
    }

    /**
     * Main program
     *
     * @param args
     * 		the command line arguments
     */
    public static void main(java.lang.String[] args) {
        if (args.length == 0) {
            new org.acm.seguin.summary.SummaryTraversal(java.lang.System.getProperty("user.dir")).go();
        } else {
            new org.acm.seguin.summary.SummaryTraversal(args[0]).go();
        }
        org.acm.seguin.summary.SummaryTraversal.debug();
        java.lang.System.exit(0);
    }

    /**
     * Print everything for debugging purposes
     */
    public static void debug() {
        // Now print everything
        org.acm.seguin.summary.PrintVisitor printer = new org.acm.seguin.summary.PrintVisitor();
        java.util.Iterator iter = org.acm.seguin.summary.PackageSummary.getAllPackages();
        while (iter.hasNext()) {
            org.acm.seguin.summary.PackageSummary next = ((org.acm.seguin.summary.PackageSummary) (iter.next()));
            next.accept(printer, "");
        } 
    }
}