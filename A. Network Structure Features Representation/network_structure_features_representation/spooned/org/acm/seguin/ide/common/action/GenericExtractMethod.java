/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common.action;
import org.acm.seguin.refactor.RefactoringException;
/**
 * JBuilder's method to extract a file
 *
 * @author Chris Seguin
 */
class GenericExtractMethod extends org.acm.seguin.uml.refactor.ExtractMethodDialog {
    /**
     * Constructor for the JBuilderExtractMethod object
     *
     * @exception RefactoringException
     * 		Description of Exception
     */
    public GenericExtractMethod() throws org.acm.seguin.refactor.RefactoringException {
        super(org.acm.seguin.ide.common.EditorOperations.get().getEditorFrame());
    }

    /**
     * Sets the StringInIDE attribute of the JBuilderExtractMethod object
     *
     * @param value
     * 		The new StringInIDE value
     */
    protected void setStringInIDE(java.lang.String value) {
        org.acm.seguin.ide.common.EditorOperations.get().setStringInIDE(value);
    }

    /**
     * Gets the StringFromIDE attribute of the JBuilderExtractMethod object
     *
     * @return The StringFromIDE value
     */
    protected java.lang.String getStringFromIDE() {
        return org.acm.seguin.ide.common.EditorOperations.get().getStringFromIDE();
    }

    /**
     * Gets the SelectionFromIDE attribute of the JBuilderExtractMethod object
     *
     * @return The SelectionFromIDE value
     */
    protected java.lang.String getSelectionFromIDE() {
        return org.acm.seguin.ide.common.EditorOperations.get().getSelectionFromIDE();
    }
}