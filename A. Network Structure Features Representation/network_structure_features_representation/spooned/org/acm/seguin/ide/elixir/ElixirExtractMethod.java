/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Publics License for more
details about use and distribution of this software.
 */
package org.acm.seguin.ide.elixir;
/**
 * ExtractMethod for the elixir editor.
 *
 * @author Chris Seguin
 * @date May 31, 1999
 */
public class ElixirExtractMethod extends org.acm.seguin.uml.refactor.ExtractMethodDialog {
    private org.acm.seguin.ide.elixir.BasicViewManager bvm;

    /**
     * Create an ElixirPrettyPrinter object
     */
    public ElixirExtractMethod() throws org.acm.seguin.refactor.RefactoringException {
        super(org.acm.seguin.ide.elixir.FrameManager.current().getFrame());
    }

    /**
     * Remove \r from buffer
     *
     * @param input
     * 		Description of Parameter
     * @return a string containing the results
     */
    public java.lang.String removeCR(java.lang.String input) {
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        int last = input.length();
        for (int ndx = 0; ndx < last; ndx++) {
            char ch = input.charAt(ndx);
            if (ch == '\r') {
                // Do nothing
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    /**
     * Sets the string in the IDE
     *
     * @param value
     * 		The new file contained in a string
     */
    protected void setStringInIDE(java.lang.String value) {
        bvm.setContentsString(value);
    }

    /**
     * Gets the initial string from the IDE
     *
     * @return The file in string format
     */
    protected java.lang.String getStringFromIDE() {
        org.acm.seguin.ide.elixir.FrameManager fm = org.acm.seguin.ide.elixir.FrameManager.current();
        bvm = ((org.acm.seguin.ide.elixir.BasicViewManager) (fm.getViewSite().getCurrentViewManager()));
        if (bvm == null) {
            return null;
        }
        return bvm.getContentsString();
    }

    /**
     * Gets the SelectionFromIDE attribute of the ElixirExtractMethod object
     *
     * @return The SelectionFromIDE value
     */
    protected java.lang.String getSelectionFromIDE() {
        try {
            java.lang.Object view = bvm.getView();
            java.lang.System.out.println("View is a :  " + view.getClass().getName());
            javax.swing.JPanel panel = ((javax.swing.JPanel) (view));
            java.awt.Component editor = searchPanels(panel, " ");
            if (editor instanceof javax.swing.text.JTextComponent) {
                javax.swing.text.JTextComponent comp = ((javax.swing.text.JTextComponent) (editor));
                return comp.getSelectedText();
            }
            java.lang.System.out.println("Not a text component");
            return null;
        } catch (java.lang.Throwable thrown) {
            thrown.printStackTrace(java.lang.System.out);
        }
        return null;
    }

    /**
     * Useful program that searches through the different panels
     *  to find the text panel that we can get the selected code
     *  from.
     *
     * @param jPanel
     * 		Description of Parameter
     * @param prefix
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private java.awt.Component searchPanels(java.awt.Container jPanel, java.lang.String prefix) {
        int last = jPanel.getComponentCount();
        for (int ndx = 0; ndx < last; ndx++) {
            java.awt.Component next = jPanel.getComponent(ndx);
            java.lang.System.out.println((((prefix + ":") + ndx) + "  ") + next.getClass().getName());
            if (next instanceof org.acm.seguin.ide.elixir.LineEditorPane) {
                return next;
            }
            if (next instanceof java.awt.Container) {
                java.awt.Component result = searchPanels(((java.awt.Container) (next)), (prefix + ":") + ndx);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * Reformats the current source code
     */
    public static void extractMethod() {
        try {
            java.lang.System.out.println("extract method #1");
            org.acm.seguin.ide.elixir.ElixirExtractMethod eem = new org.acm.seguin.ide.elixir.ElixirExtractMethod();
            java.lang.System.out.println("extract method #2");
            eem.show();
            java.lang.System.out.println("extract method #3");
        } catch (org.acm.seguin.refactor.RefactoringException re) {
            javax.swing.JOptionPane.showMessageDialog(null, re.getMessage(), "Refactoring Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}