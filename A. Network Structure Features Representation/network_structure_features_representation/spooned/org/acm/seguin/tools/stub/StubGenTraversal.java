/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.tools.stub;
/**
 * Traverses a directory structure and performs all refactorings on the
 *  files.
 *
 * @author Chris Seguin
 * @created May 12, 1999
 */
class StubGenTraversal extends org.acm.seguin.io.DirectoryTreeTraversal {
    // Instance Variables
    private org.acm.seguin.tools.stub.StubFile sf;

    /**
     * Creates a stub generator
     *
     * @param directory
     * 		the initial directory or file
     * @param key
     * 		the key associated with this stub
     * @param file
     * 		Description of Parameter
     */
    public StubGenTraversal(java.lang.String directory, java.lang.String key, java.io.File file) {
        super(directory);
        sf = new org.acm.seguin.tools.stub.StubFile(key, file);
    }

    /**
     * Main processing method for the StubGenTraversal object
     */
    public void run() {
        go();
        sf.done();
    }

    /**
     * Determines if this file should be handled by this traversal
     *
     * @param currentFile
     * 		the current file
     * @return true if the file should be handled
     */
    protected boolean isTarget(java.io.File currentFile) {
        return currentFile.getName().endsWith(".java");
    }

    /**
     * Visits the current file
     *
     * @param currentFile
     * 		the current file
     */
    protected void visit(java.io.File currentFile) {
        java.lang.System.out.println("Generating a stub for:  " + currentFile.getPath());
        sf.apply(currentFile);
    }
}