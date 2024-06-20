/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.util;
/**
 * Traverses a directory structure and backups all java files found
 *
 * @author Chris Seguin
 * @created October 31, 1999
 * @date May 12, 1999
 */
public class BackupTraversal extends org.acm.seguin.io.DirectoryTreeTraversal {
    // Instance Variables
    private java.lang.String dest;

    /**
     * Traverses a directory tree structure
     *
     * @param init
     * 		the initial directory
     * @param out
     * 		the output directory
     */
    public BackupTraversal(java.lang.String init, java.lang.String out) {
        super(init);
        if (out.charAt(out.length() - 1) != java.io.File.separatorChar) {
            dest = out + java.io.File.separator;
        } else {
            dest = out;
        }
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
        java.lang.String lowercase = filename.toLowerCase();
        if (!lowercase.endsWith(".java")) {
            return false;
        }
        java.lang.String classname = lowercase.substring(0, lowercase.length() - 5) + ".class";
        java.io.File classFile = new java.io.File(currentFile.getParentFile(), classname);
        return classFile.exists();
    }

    /**
     * Visits the current file
     *
     * @param currentFile
     * 		the current file
     */
    protected void visit(java.io.File currentFile) {
        java.lang.String destString = getDestination(currentFile);
        java.io.File destFile = new java.io.File(destString);
        new org.acm.seguin.io.FileCopy(currentFile, destFile).run();
    }

    /**
     * Program called when we arrive at a directory
     *
     * @param current
     * 		the current directory
     */
    protected void arriveAtDir(java.io.File current) {
        java.lang.String currentPath = current.getPath();
        java.lang.String base = "";
        if (currentPath.startsWith("./") || currentPath.startsWith(".\\")) {
            base = currentPath.substring(2);
        } else {
            base = currentPath;
        }
        createDir((dest + "src/") + base);
        createDir((dest + "test/src/") + base);
    }

    /**
     * Returns the destination file from the current file
     *
     * @param current
     * 		the current file
     * @return the destination file
     */
    private java.lang.String getDestination(java.io.File current) {
        java.lang.String prefix = "src/";
        if (current.getName().startsWith("Test")) {
            prefix = "test/src/";
        }
        java.lang.String currentPath = current.getPath();
        if (currentPath.startsWith("./") || currentPath.startsWith(".\\")) {
            return (dest + prefix) + currentPath.substring(2);
        } else {
            return (dest + prefix) + currentPath;
        }
    }

    /**
     * The main program
     *
     * @param args
     * 		Description of Parameter
     */
    public static void main(java.lang.String[] args) {
        // Make sure everything is installed properly
        new org.acm.seguin.tools.install.RefactoryInstaller(false).run();
        if (args.length != 2) {
            java.lang.System.out.println("Syntax:  java BackupTraversal source dest");
            return;
        }
        new org.acm.seguin.util.BackupTraversal(args[0], args[1]).go();
    }

    /**
     * Creates a named directory if it does not exist
     */
    private void createDir(java.lang.String destDir) {
        java.io.File destDirFile = new java.io.File(destDir);
        if (destDirFile.exists()) {
            // Nothing to do
        } else {
            destDirFile.mkdirs();
        }
    }

    /**
     * Creates a named directory if it does not exist
     */
    private void deleteDir(java.lang.String destDir) {
        java.io.File destDirFile = new java.io.File(destDir);
        java.lang.String[] children = destDirFile.list();
        if (children.length == 0) {
            destDirFile.delete();
        }
    }

    /**
     * Program called when we arrive at a directory
     *
     * @param currentFile
     * 		the current file
     */
    protected void leaveDir(java.io.File current) {
        java.lang.String currentPath = current.getPath();
        java.lang.String base = "";
        if (currentPath.startsWith("./") || currentPath.startsWith(".\\")) {
            base = currentPath.substring(2);
        } else {
            base = currentPath;
        }
        deleteDir((dest + "src/") + base);
        deleteDir((dest + "test/src/") + base);
    }
}