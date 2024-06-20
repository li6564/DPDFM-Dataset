package org.acm.seguin.ide.netbeans;
public class NetBeansPrettyPrinter extends org.acm.seguin.pretty.PrettyPrintFromIDE {
    private javax.swing.JEditorPane _editorPane;

    // NOTE: A new line is actually 2 characters long but 1 reflects how the
    // caret positioning works
    private final int NEW_LINE_LENGTH = 1;

    public NetBeansPrettyPrinter(org.acm.seguin.ide.netbeans.EditorCookie editorCookie) {
        super();
        _editorPane = getCurrentEditorPane(editorCookie);
    }

    /**
     *
     * @return the initial line number, -1 if failed
     */
    protected int getLineNumber() {
        java.io.BufferedReader reader = getDocumentTextReader();
        int offset = _editorPane.getCaretPosition();
        int lineNumber = 0;
        int currOffset = 0;
        while (currOffset <= offset) {
            java.lang.String currLine = null;
            try {
                currLine = reader.readLine();
            } catch (java.io.IOException ioe) {
                ioe.printStackTrace();
                return -1;
            }
            currOffset += currLine.length() + NEW_LINE_LENGTH;
            lineNumber++;
        } 
        return lineNumber;
    }

    protected void setLineNumber(int lineNumber) {
        if (lineNumber < 1) {
            throw new java.lang.IllegalArgumentException("lineNumber must be 1 or greater: " + lineNumber);
        }
        int targetOffset = 0;
        int lineCount = 1;
        java.io.BufferedReader reader = getDocumentTextReader();
        java.lang.String currLine = null;
        try {
            currLine = reader.readLine();
            while ((currLine != null) && (lineCount < lineNumber)) {
                targetOffset += currLine.length() + NEW_LINE_LENGTH;
                lineCount++;
                currLine = reader.readLine();
            } 
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        if (currLine == null) {
            if (lineCount < lineNumber) {
                throw new java.lang.IllegalArgumentException("lineNumber is past end of document!: " + lineNumber);
            }
            if (lineCount > 0) {
                // no new line after last line
                targetOffset--;
            }
        }
        _editorPane.setCaretPosition(targetOffset);
    }

    /**
     * Gets the initial string from the IDE
     *
     * @return The file in string format
     */
    protected java.lang.String getStringFromIDE() {
        return _editorPane.getText();
    }

    /**
     * Sets the string in the IDE
     *
     * @param value
     * 		The new file contained in a string
     */
    protected void setStringInIDE(java.lang.String text) {
        _editorPane.setText(text);
    }

    private javax.swing.JEditorPane getCurrentEditorPane(org.acm.seguin.ide.netbeans.EditorCookie cookie) {
        javax.swing.JEditorPane[] panes = cookie.getOpenedPanes();
        java.lang.System.err.println("Panes: " + panes);
        if (panes.length == 1) {
            return panes[0];
        }
        return null;
    }

    private java.io.BufferedReader getDocumentTextReader() {
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.StringReader(_editorPane.getText()));
        return reader;
    }
}