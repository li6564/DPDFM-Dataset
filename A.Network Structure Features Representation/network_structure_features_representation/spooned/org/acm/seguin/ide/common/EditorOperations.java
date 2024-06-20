/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common;
/**
 * Responsible for containing the editor operations. These are the aspects
 *  that the refactoring tool requires to integrate the pretty printer, the
 *  extract method, and other refactorings with the particular IDE.<P>
 *
 *  The developer of this class must remember that in order to perform a
 *  setStringInIDE, the developer must first call the getStringInIDE and
 *  assure that no one else invokes the getStringInIDE before they can invoke
 *  the setStringInIDE.
 *
 * @author Chris Seguin
 */
public abstract class EditorOperations {
    private static org.acm.seguin.ide.common.EditorOperations singleton = null;

    /**
     * Sets the StringInIDE attribute of the EditorOperations object
     *
     * @param value
     * 		The new StringInIDE value
     */
    public abstract void setStringInIDE(java.lang.String value);

    /**
     * Sets the LineNumber attribute of the EditorOperations object
     *
     * @param value
     * 		The new LineNumber value
     */
    public abstract void setLineNumber(int value);

    /**
     * Gets the StringFromIDE attribute of the EditorOperations object
     *
     * @return The StringFromIDE value
     */
    public abstract java.lang.String getStringFromIDE();

    /**
     * Gets the LineNumber attribute of the EditorOperations object
     *
     * @return The LineNumber value
     */
    public abstract int getLineNumber();

    /**
     * Returns the frame that contains the editor. If this is not available or
     *  you want dialog boxes to be centered on the screen return null from this
     *  operation.
     *
     * @return the frame
     */
    public abstract javax.swing.JFrame getEditorFrame();

    /**
     * Gets the SelectionFromIDE attribute of the EditorOperations object
     *
     * @return The SelectionFromIDE value
     */
    public abstract java.lang.String getSelectionFromIDE();

    /**
     * Returns true if the current file being edited is a java file
     *
     * @return true if the current file is a java file
     */
    public abstract boolean isJavaFile();

    /**
     * Gets the File attribute of the EditorOperations object
     *
     * @return The File value
     */
    public abstract java.io.File getFile();

    /**
     * Get the current editor operations
     *
     * @return The editor operations
     */
    public static org.acm.seguin.ide.common.EditorOperations get() {
        return org.acm.seguin.ide.common.EditorOperations.singleton;
    }

    /**
     * Registers an editor operations
     *
     * @param ops
     * 		the new editor operations object
     */
    public static void register(org.acm.seguin.ide.common.EditorOperations ops) {
        org.acm.seguin.ide.common.EditorOperations.singleton = ops;
    }
}