package org.acm.seguin.ide.cafe;
/**
 * Performs the extract method refactoring for visual cafe
 *
 * @author Chris Seguin
 */
class CafeExtractMethod extends org.acm.seguin.uml.refactor.ExtractMethodDialog implements java.awt.event.ActionListener {
    private org.acm.seguin.ide.cafe.SourceFile sourceFile;

    /**
     * Constructor for the CafeExtractMethod object
     */
    public CafeExtractMethod() throws org.acm.seguin.refactor.RefactoringException {
        super(null);
    }

    /**
     * Sets the StringInIDE attribute of the CafeExtractMethod object
     *
     * @param value
     * 		The new StringInIDE value
     */
    protected void setStringInIDE(java.lang.String value) {
        if (sourceFile == null) {
            return;
        }
        sourceFile.setText(value);
        sourceFile = null;
    }

    /**
     * Gets the StringFromIDE attribute of the CafeExtractMethod object
     *
     * @return The StringFromIDE value
     */
    protected java.lang.String getStringFromIDE() {
        // Get the data from the window
        org.acm.seguin.ide.cafe.VisualCafe vc = org.acm.seguin.ide.cafe.VisualCafe.getVisualCafe();
        if (vc == null) {
            return "";
        }
        sourceFile = vc.getFrontmostSourceFile();
        if (sourceFile == null) {
            return "";
        }
        return sourceFile.getTextString();
    }

    /**
     * Gets the SelectionFromIDE attribute of the CafeExtractMethod object
     *
     * @return The SelectionFromIDE value
     */
    protected java.lang.String getSelectionFromIDE() {
        if (sourceFile == null) {
            org.acm.seguin.ide.cafe.VisualCafe vc = org.acm.seguin.ide.cafe.VisualCafe.getVisualCafe();
            if (vc == null) {
                return "";
            }
            sourceFile = vc.getFrontmostSourceFile();
            if (sourceFile == null) {
                return "";
            }
        }
        org.acm.seguin.ide.cafe.Range range = sourceFile.getSelectionRange();
        return sourceFile.getRangeTextString(range);
    }

    /**
     * What to do when someone selects the extract method refactoring
     *
     * @param e
     * 		the button event
     */
    public void actionPerformed(java.awt.event.ActionEvent e) {
        show();
    }
}