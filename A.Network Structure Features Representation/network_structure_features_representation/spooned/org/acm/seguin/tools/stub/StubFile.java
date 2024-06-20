/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.tools.stub;
/**
 * Holds a refactoring. Default version just pretty prints the file.
 *
 * @author Chris Seguin
 * @created May 12, 1999
 */
public class StubFile {
    // Instance Variables
    private org.acm.seguin.parser.factory.ParserFactory factory;

    private java.io.OutputStream out;

    private java.lang.String name;

    private java.io.File outputFile;

    private static boolean creating = false;

    /**
     * Refactors java code.
     *
     * @param init
     * 		Description of Parameter
     * @param file
     * 		Description of Parameter
     */
    public StubFile(java.lang.String init, java.io.File file) {
        factory = null;
        out = null;
        name = init;
        outputFile = file;
        org.acm.seguin.tools.stub.StubFile.creating = true;
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
     * Return the factory that gets the abstract syntax trees
     *
     * @return the parser factory
     */
    public org.acm.seguin.parser.factory.ParserFactory getParserFactory() {
        return factory;
    }

    /**
     * Create the stub for this file
     *
     * @param inputFile
     * 		the input file
     */
    public void apply(java.io.File inputFile) {
        setParserFactory(new org.acm.seguin.parser.factory.FileParserFactory(inputFile));
        apply();
    }

    /**
     * Create the stub for this file
     *
     * @param inputStream
     * 		the input stream
     * @param filename
     * 		the name of the file contained by the input stream
     */
    public void apply(java.io.InputStream inputStream, java.lang.String filename) {
        setParserFactory(new org.acm.seguin.parser.factory.InputStreamParserFactory(inputStream, filename));
        apply();
    }

    /**
     * Close the file and note that we have completed this operation
     */
    public void done() {
        if (out != null) {
            try {
                out.close();
            } catch (java.io.IOException ioe) {
            }
        }
        org.acm.seguin.tools.stub.StubFile.creating = false;
        org.acm.seguin.tools.stub.StubFile.resume();
    }

    /**
     * Create the output stream
     *
     * @param file
     * 		the name of the file
     * @return the output stream
     */
    protected java.io.OutputStream getOutputStream(java.io.File file) {
        if (out != null) {
            return out;
        }
        if (name != null) {
            java.lang.String home = java.lang.System.getProperty("user.home");
            java.io.File directory = new java.io.File((home + java.io.File.separator) + ".Refactory");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            try {
                java.io.File outFile = new java.io.File(directory, name + ".stub");
                // System.out.println("Creating output stream:  " + outFile.getPath());
                out = new java.io.FileOutputStream(outFile.getPath(), true);
            } catch (java.io.IOException ioe) {
                out = java.lang.System.out;
            }
        } else {
            try {
                // System.out.println("Creating output stream:  " + outputFile.getPath());
                out = new java.io.FileOutputStream(outputFile.getPath(), true);
            } catch (java.io.IOException ioe) {
                out = java.lang.System.out;
            }
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
    private org.acm.seguin.pretty.PrintData getPrintData(java.io.File input) {
        // Create the new stream
        return new org.acm.seguin.pretty.PrintData(getOutputStream(input));
    }

    /**
     * Create the stub for this file
     */
    private void apply() {
        // Create the visitor
        org.acm.seguin.tools.stub.StubPrintVisitor printer = new org.acm.seguin.tools.stub.StubPrintVisitor();
        // Create the appropriate print data
        org.acm.seguin.pretty.PrintData data = getPrintData(null);
        org.acm.seguin.parser.ast.SimpleNode root = factory.getAbstractSyntaxTree(false);
        if (root != null) {
            printer.visit(root, data);
        }
        // Flush the output stream
        data.flush();
        try {
            out.write("\n\n|\n".getBytes());
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace(java.lang.System.out);
        }
    }

    /**
     * Wait while this is being created
     */
    public static synchronized void waitForCreation() {
        if (org.acm.seguin.tools.stub.StubFile.creating) {
            try {
                org.acm.seguin.tools.stub.StubFile.class.wait();
            } catch (java.lang.InterruptedException ie) {
            }
        }
    }

    /**
     * Resume all processing
     */
    private static synchronized void resume() {
        org.acm.seguin.tools.stub.StubFile.class.notifyAll();
    }
}