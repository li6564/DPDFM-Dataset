/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.summary;
import java.io.IOException;
/**
 * Loads the file summaries for the framework files
 *
 * @author Chris Seguin
 * @created August 23, 1999
 */
public class FrameworkFileSummaryLoader extends org.acm.seguin.summary.FrameworkLoader {
    private java.lang.String directory;

    private boolean loaded;

    private org.acm.seguin.summary.load.LoadStatus status;

    /**
     * Constructor for the FrameworkFileSummaryLoader object
     *
     * @param init
     * 		Description of Parameter
     */
    public FrameworkFileSummaryLoader(org.acm.seguin.summary.load.LoadStatus init) {
        status = init;
        java.lang.String home;
        try {
            org.acm.seguin.util.FileSettings umlBundle = org.acm.seguin.util.FileSettings.getSettings("Refactory", "uml");
            home = umlBundle.getString("stub.dir");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            home = java.lang.System.getProperty("user.home");
        }
        directory = (home + java.io.File.separator) + ".Refactory";
        loaded = false;
    }

    /**
     * Main processing method for the FrameworkFileSummaryLoader object
     */
    public void run() {
        if (loaded) {
            return;
        }
        org.acm.seguin.tools.stub.StubGenerator.waitForLoaded();
        org.acm.seguin.summary.PackageSummary.getPackageSummary("java.lang");
        org.acm.seguin.tools.stub.StubFile.waitForCreation();
        loaded = true;
        java.io.File dir = new java.io.File(directory);
        java.lang.String[] filenames = dir.list();
        if (filenames == null) {
            return;
        }
        for (int ndx = 0; ndx < filenames.length; ndx++) {
            if (filenames[ndx].endsWith(".stub")) {
                loadFile(filenames[ndx]);
            }
        }
    }

    /**
     * Gets the InputReader attribute of the FrameworkFileSummaryLoader object
     *
     * @param filename
     * 		Description of Parameter
     * @return The InputReader value
     * @exception IOException
     * 		Description of Exception
     */
    private java.io.Reader getInputReader(java.lang.String filename) throws java.io.IOException {
        return new java.io.BufferedReader(new java.io.FileReader(new java.io.File(directory, filename)));
    }

    /**
     * Gets the TypeName attribute of the FrameworkFileSummaryLoader object
     *
     * @param summary
     * 		Description of Parameter
     * @return The TypeName value
     */
    private java.lang.String getTypeName(org.acm.seguin.summary.FileSummary summary) {
        if (summary == null) {
            return "No summary";
        }
        java.util.Iterator iter = summary.getTypes();
        if (iter == null) {
            return "No types";
        }
        org.acm.seguin.summary.TypeSummary first = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
        java.lang.String name = first.getName();
        if (name == null) {
            return "No name";
        }
        return name;
    }

    /**
     * Loads a stub file
     *
     * @param filename
     * 		The name of the file to load
     */
    private void loadFile(java.lang.String filename) {
        try {
            status.setRoot(filename);
            java.io.Reader input = getInputReader(filename);
            java.lang.String buffer = loadBuffer(input);
            while (buffer.length() > 0) {
                org.acm.seguin.summary.FileSummary summary = org.acm.seguin.summary.FileSummary.getFileSummary(buffer);
                status.setCurrentFile(getTypeName(summary));
                buffer = loadBuffer(input);
                java.lang.Thread.currentThread().yield();
            } 
        } catch (java.io.IOException ioe) {
            org.acm.seguin.awt.ExceptionPrinter.print(ioe);
        }
    }

    /**
     * Description of the Method
     *
     * @param input
     * 		Description of Parameter
     * @return Description of the Returned Value
     * @exception IOException
     * 		Description of Exception
     */
    private java.lang.String loadBuffer(java.io.Reader input) throws java.io.IOException {
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        int next = input.read();
        while (((next >= 0) && (next != '|')) && input.ready()) {
            buffer.append(((char) (next)));
            next = input.read();
        } 
        return buffer.toString().trim();
    }

    /**
     * The main program for the FrameworkFileSummaryLoader class
     *
     * @param args
     * 		The command line arguments
     */
    public static void main(java.lang.String[] args) {
        new org.acm.seguin.summary.FrameworkFileSummaryLoader(new org.acm.seguin.summary.load.TextLoadStatus()).run();
    }
}