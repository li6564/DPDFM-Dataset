/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.tools.stub;
/**
 * Generates a stub set from a file or a directory
 *
 * @author Chris Seguin
 */
public class StubGenerator extends java.lang.Thread {
    private java.lang.String filename;

    private java.lang.String key;

    private java.io.File file;

    /**
     * Constructor for the StubGenerator object
     *
     * @param name
     * 		The name of the zip file
     * @param stubKey
     * 		The key associated with this stub
     */
    public StubGenerator(java.lang.String name, java.lang.String stubKey) {
        filename = name;
        key = stubKey;
        file = null;
    }

    /**
     * Constructor for the StubGenerator object
     *
     * @param name
     * 		The name of the zip file
     * @param output
     * 		Description of Parameter
     */
    public StubGenerator(java.lang.String name, java.io.File output) {
        filename = name;
        key = null;
        file = output;
    }

    /**
     * Main processing method for the StubGenerator object
     */
    public void run() {
        synchronized(org.acm.seguin.tools.stub.StubGenerator.class) {
            java.io.File sourceFile = new java.io.File(filename);
            if (sourceFile.isDirectory()) {
                new org.acm.seguin.tools.stub.StubGenTraversal(filename, key, file).run();
            } else {
                new org.acm.seguin.tools.stub.StubGenFromZip(filename, key, file).run();
            }
        }
    }

    /**
     * Waits until it is appropriate to allow the stub files to be loaded
     */
    public static synchronized void waitForLoaded() {
        // System.out.println("OK to load");
    }

    /**
     * The main program
     *
     * @param args
     * 		the command line arguments
     */
    public static void main(java.lang.String[] args) {
        // Make sure everything is installed properly
        new org.acm.seguin.tools.install.RefactoryInstaller(false).run();
        if (args.length != 2) {
            java.lang.System.out.println("Syntax:  java org.acm.seguin.tools.stub.StubGenerator <name> <file>   ");
            java.lang.System.out.println("   OR    java org.acm.seguin.tools.stub.StubGenerator <name> <dir>   ");
            java.lang.System.out.println("   where <name> is the name of the stub file to generate");
            java.lang.System.out.println("   where <file> is the jar or zip file");
            java.lang.System.out.println("   where <dir> is the directory for one or more source files source file");
            return;
        }
        org.acm.seguin.tools.stub.StubGenerator.generateStubs(args[0], args[1]);
        // Exit
        java.lang.System.exit(1);
    }

    /**
     * Generate a stub for the current file or directory
     *
     * @param filename
     * 		the name of the directory
     * @param stubname
     * 		the name of the stub
     */
    public static void generateStubs(java.lang.String stubname, java.lang.String filename) {
        new org.acm.seguin.tools.stub.StubGenerator(filename, stubname).run();
    }
}