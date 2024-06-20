/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.pretty;
/**
 * Holds a refactoring. Default version just pretty prints the file.
 *
 * @author Chris Seguin
 * @created July 1, 1999
 * @date May 12, 1999
 */
public class PrettyPrintFile {
    // Instance Variables
    private org.acm.seguin.parser.factory.ParserFactory factory;

    private boolean ask;

    /**
     * Refactors java code.
     */
    public PrettyPrintFile() {
        ask = true;
    }

    /**
     * Sets whether we should ask the user
     *
     * @param way
     * 		the way to set the variable
     */
    public void setAsk(boolean way) {
        ask = way;
    }

    /**
     * Set the parser factory
     *
     * @param factory
     * 		Description of Parameter
     */
    public void setParserFactory(org.acm.seguin.parser.factory.ParserFactory factory) {
        this.factory = factory;
    }

    /**
     * Returns true if this refactoring is applicable
     *
     * @param inputFile
     * 		the input file
     * @return true if this refactoring is applicable
     */
    public boolean isApplicable(java.io.File inputFile) {
        if (!inputFile.canWrite()) {
            return false;
        }
        boolean result = true;
        if (ask) {
            result = org.acm.seguin.awt.Question.isYes("Pretty Printer", ("Do you want to pretty print the file\n" + inputFile.getPath()) + "?");
        }
        // Create a factory if necessary
        if (result) {
            setParserFactory(new org.acm.seguin.parser.factory.FileParserFactory(inputFile));
        }
        return result;
    }

    /**
     * Return the factory that gets the abstract syntax trees
     *
     * @return the parser factory
     */
    public org.acm.seguin.parser.factory.ParserFactory getParserFactory() {
        return factory;
    }

    /**
     * Apply the refactoring
     *
     * @param inputFile
     * 		the input file
     */
    public void apply(java.io.File inputFile) {
        org.acm.seguin.parser.ast.SimpleNode root = factory.getAbstractSyntaxTree(true);
        apply(inputFile, root);
    }

    /**
     * Apply the refactoring
     *
     * @param inputFile
     * 		the input file
     * @param root
     * 		Description of Parameter
     */
    public void apply(java.io.File inputFile, org.acm.seguin.parser.ast.SimpleNode root) {
        if (root != null) {
            org.acm.seguin.util.FileSettings pretty = org.acm.seguin.util.FileSettings.getSettings("Refactory", "pretty");
            pretty.setReloadNow(true);
            // Create the visitor
            org.acm.seguin.pretty.PrettyPrintVisitor printer = new org.acm.seguin.pretty.PrettyPrintVisitor();
            // Create the appropriate print data
            org.acm.seguin.pretty.PrintData data = getPrintData(inputFile);
            if (root instanceof org.acm.seguin.parser.ast.ASTCompilationUnit) {
                printer.visit(((org.acm.seguin.parser.ast.ASTCompilationUnit) (root)), data);
            } else {
                printer.visit(root, data);
            }
            // Flush the output stream
            data.close();
        }
    }

    /**
     * Create the output stream
     *
     * @param file
     * 		the name of the file
     * @return the output stream
     */
    protected java.io.OutputStream getOutputStream(java.io.File file) {
        // Local Variables
        java.io.OutputStream out = null;
        try {
            out = new org.acm.seguin.io.InplaceOutputStream(file);
        } catch (java.io.IOException ioe) {
            out = java.lang.System.out;
        }
        // Return the output stream
        return out;
    }

    /**
     * Return the appropriate print data
     *
     * @param input
     * 		Description of Parameter
     * @return the print data
     */
    protected org.acm.seguin.pretty.PrintData getPrintData(java.io.File input) {
        // Create the new stream
        return new org.acm.seguin.pretty.PrintData(getOutputStream(input));
    }
}