/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common.action;
/**
 * Software that reformats the java source code
 *
 * @author Chris Seguin
 */
public class GenericPrettyPrinter extends org.acm.seguin.pretty.PrettyPrintFromIDE {
    /**
     * Sets the string in the IDE
     *
     * @param value
     * 		The new file contained in a string
     */
    protected void setStringInIDE(java.lang.String value) {
        org.acm.seguin.ide.common.EditorOperations.get().setStringInIDE(value);
    }

    /**
     * Sets the line number
     *
     * @param value
     * 		The new LineNumber value
     */
    protected void setLineNumber(int value) {
        org.acm.seguin.ide.common.EditorOperations.get().setLineNumber(value);
    }

    /**
     * Gets the initial string from the IDE
     *
     * @return The file in string format
     */
    protected java.lang.String getStringFromIDE() {
        return org.acm.seguin.ide.common.EditorOperations.get().getStringFromIDE();
    }

    /**
     * Returns the initial line number
     *
     * @return The LineNumber value
     */
    protected int getLineNumber() {
        return org.acm.seguin.ide.common.EditorOperations.get().getLineNumber();
    }
}