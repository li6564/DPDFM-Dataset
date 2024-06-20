/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.ide.cafe;
/**
 * This class runs the pretty printer for Symantec Visual Cafe
 *
 * @author Chris Seguin
 * @created August 23, 2000
 */
public class CafePrettyPrinter extends org.acm.seguin.pretty.PrettyPrintFromIDE implements java.awt.event.ActionListener {
    private org.acm.seguin.ide.cafe.SourceFile sourceFile;

    /**
     * This is invoked when the user selects pretty printer
     *
     * @param evt
     * 		The action event
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        java.lang.System.out.println("Invoking the pretty printer");
        prettyPrintCurrentWindow();
    }

    /**
     * Sets the string in the IDE
     *
     * @param value
     * 		The new file contained in a string
     */
    protected void setStringInIDE(java.lang.String value) {
        if (sourceFile == null) {
            return;
        }
        sourceFile.setText(value);
        sourceFile = null;
    }

    /**
     * Gets the initial string from the IDE
     *
     * @return The file in string format
     */
    protected java.lang.String getStringFromIDE() {
        // Get the data from the window
        org.acm.seguin.ide.cafe.VisualCafe vc = org.acm.seguin.ide.cafe.VisualCafe.getVisualCafe();
        sourceFile = vc.getFrontmostSourceFile();
        return sourceFile.getTextString();
    }

    /**
     * Returns the initial line number
     */
    protected int getLineNumber() {
        return -1;
    }

    /**
     * Sets the line number
     */
    protected void setLineNumber(int value) {
        // Do nothing
    }
}