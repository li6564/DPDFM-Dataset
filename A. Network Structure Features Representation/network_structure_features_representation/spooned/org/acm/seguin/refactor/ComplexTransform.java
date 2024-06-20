/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.refactor;
/**
 * Base class for a program that reads in an abstract syntax tree, transforms
 *  the code, and rewrites the file to disk.
 *
 * @author Chris Seguin
 */
public class ComplexTransform {
    // Instance Variables
    private java.util.ArrayList transforms;

    private org.acm.seguin.refactor.undo.UndoAction undo;

    /**
     * Constructor for the ComplexTransform object
     *
     * @param init
     * 		the undo action
     */
    public ComplexTransform(org.acm.seguin.refactor.undo.UndoAction init) {
        transforms = new java.util.ArrayList();
        undo = init;
    }

    /**
     * Adds a syntax tree transformation
     *
     * @param value
     * 		Description of Parameter
     */
    public void add(org.acm.seguin.refactor.TransformAST value) {
        if (value != null) {
            transforms.add(value);
        }
    }

    /**
     * Clears all the transforms
     */
    public void clear() {
        transforms.clear();
    }

    /**
     * Is it worth applying the transforms
     *
     * @return true if there is any
     */
    public boolean hasAnyChanges() {
        return transforms.size() > 0;
    }

    /**
     * Given a file, applies a set of transformations to it
     *
     * @param inputFile
     * 		Description of Parameter
     * @param outputFile
     * 		Description of Parameter
     */
    public void apply(java.io.File inputFile, java.io.File outputFile) {
        // Get the abstract syntax tree
        org.acm.seguin.parser.factory.ParserFactory factory = new org.acm.seguin.parser.factory.FileParserFactory(inputFile);
        org.acm.seguin.parser.ast.SimpleNode root = factory.getAbstractSyntaxTree(false);
        // Apply each individual transformation
        int last = transforms.size();
        for (int ndx = 0; ndx < last; ndx++) {
            org.acm.seguin.refactor.TransformAST next = ((org.acm.seguin.refactor.TransformAST) (transforms.get(ndx)));
            next.update(root);
        }
        // Check it out if it is read only
        if (!inputFile.canWrite()) {
            checkOut(inputFile);
        }
        // Print it
        undo.add(inputFile, outputFile);
        createParent(outputFile);
        try {
            new org.acm.seguin.pretty.PrettyPrintFile().apply(outputFile, root);
        } catch (java.lang.Throwable thrown) {
            org.acm.seguin.awt.ExceptionPrinter.print(thrown);
        }
    }

    /**
     * Creates a new file
     *
     * @param file
     * 		Description of Parameter
     */
    public void createFile(java.io.File file) {
        undo.add(null, file);
    }

    /**
     * Removes an old file
     *
     * @param file
     * 		Description of Parameter
     */
    public void removeFile(java.io.File file) {
        undo.add(file, null);
    }

    /**
     * Creates the parent directory if it does not exist
     *
     * @param file
     * 		the file that is about to be created
     */
    private void createParent(java.io.File file) {
        java.io.File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
    }

    /**
     * Checks out the specific file from the repository
     *
     * @param file
     * 		the file to check out
     */
    private void checkOut(java.io.File file) {
        org.acm.seguin.version.VersionControl controller = org.acm.seguin.version.VersionControlFactory.get();
        java.lang.String filename;
        try {
            filename = file.getCanonicalPath();
        } catch (java.io.IOException ioe) {
            filename = file.getPath();
        }
        controller.checkOut(filename);
    }
}