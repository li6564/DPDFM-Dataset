package org.acm.seguin.pretty;
/**
 * Stores a queue of lines to be printed
 *
 * @author Chris Seguin
 */
public class LineQueue {
    /**
     * The current line number
     */
    protected int lineNumber;

    private java.util.Vector list;

    private java.io.PrintWriter output;

    private int absoluteSpace;

    private int incrementalSpace;

    private boolean ownline;

    private boolean sharedIncremental;

    private boolean ownlineCode;

    private java.lang.StringBuffer buffer;

    private java.lang.String endOfLine;

    /**
     * Constructor for the LineQueue object
     *
     * @param init
     * 		Description of Parameter
     */
    public LineQueue(java.io.PrintWriter init) {
        output = init;
        list = new java.util.Vector();
        buffer = new java.lang.StringBuffer();
        org.acm.seguin.util.FileSettings bundle = org.acm.seguin.util.FileSettings.getSettings("Refactory", "pretty");
        try {
            absoluteSpace = bundle.getInteger("singleline.comment.absoluteindent");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            absoluteSpace = 0;
        }
        try {
            incrementalSpace = bundle.getInteger("singleline.comment.incrementalindent");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            incrementalSpace = 0;
        }
        try {
            ownline = bundle.getBoolean("singleline.comment.ownline");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            ownline = true;
        }
        try {
            java.lang.String temp = bundle.getString("singleline.comment.indentstyle.shared");
            sharedIncremental = temp.equalsIgnoreCase("incremental");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            sharedIncremental = true;
        }
        try {
            java.lang.String temp = bundle.getString("singleline.comment.indentstyle.ownline");
            ownlineCode = temp.equalsIgnoreCase("code");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            ownlineCode = true;
        }
        try {
            java.lang.String temp = bundle.getString("end.line");
            if (temp.equalsIgnoreCase("CRNL")) {
                endOfLine = "\r\n";
            } else if (temp.equalsIgnoreCase("CR")) {
                endOfLine = "\r";
            } else {
                endOfLine = "\n";
            }
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            endOfLine = "\n";
        }
        lineNumber = 1;
    }

    /**
     * Sets the AbsoluteCommentSpacing attribute of the LineQueue object
     *
     * @param value
     * 		The new AbsoluteCommentSpacing value
     */
    public void setAbsoluteCommentSpacing(int value) {
        absoluteSpace = value;
    }

    /**
     * Sets the IncrementalCommentSpacing attribute of the LineQueue object
     *
     * @param value
     * 		The new IncrementalCommentSpacing value
     */
    public void setIncrementalCommentSpacing(int value) {
        incrementalSpace = value;
    }

    /**
     * Sets the Ownline attribute of the LineQueue object
     *
     * @param value
     * 		The new Ownline value
     */
    public void setOwnline(boolean value) {
        ownline = value;
    }

    /**
     * Sets the SharedIncremental attribute of the LineQueue object
     *
     * @param value
     * 		The new SharedIncremental value
     */
    public void setSharedIncremental(boolean value) {
        sharedIncremental = value;
    }

    /**
     * Sets the OwnlineCode attribute of the LineQueue object
     *
     * @param value
     * 		The new OwnlineCode value
     */
    public void setOwnlineCode(boolean value) {
        ownlineCode = value;
    }

    /**
     * Returns the current line
     *
     * @return the line number
     */
    public int getCurrentLine() {
        return lineNumber;
    }

    /**
     * Description of the Method
     *
     * @param value
     * 		Description of Parameter
     */
    public void println(java.lang.String value) {
        if (value.length() > 0) {
            flush();
        }
        list.addElement(value);
    }

    /**
     * Appends a comment to the file
     *
     * @param comment
     * 		the comment to append
     * @param prefix
     * 		the prefix to the line
     */
    public void appendSingleLineComment(java.lang.String comment, java.lang.String prefix) {
        if (list.size() > 0) {
            if (ownline) {
                list.insertElementAt(makeLine(prefix, comment), 1);
                flushFirstLine();
            } else {
                updateLine(comment, prefix);
            }
        } else {
            println(makeLine(prefix, comment));
        }
        flushFirstLine();
    }

    /**
     * Appends a comment to the file
     *
     * @param comment
     * 		the comment to append
     * @param prefix
     * 		the prefix to the line
     */
    public void appendCategoryComment(java.lang.String comment, java.lang.String prefix) {
        if (list.size() > 0) {
            java.lang.String first = ((java.lang.String) (list.elementAt(0)));
            if (first.length() > 0) {
                list.insertElementAt(prefix + comment, 1);
                flushFirstLine();
            } else {
                list.insertElementAt(prefix + comment, 0);
            }
        } else {
            println(prefix + comment);
        }
        flushFirstLine();
    }

    /**
     * Flushes the first line in the cache
     */
    public void flushFirstLine() {
        if (list.size() > 0) {
            writeln(((java.lang.String) (list.elementAt(0))));
            list.removeElementAt(0);
        }
    }

    /**
     * Flushes all the lines in the buffer
     */
    public void flush() {
        int last = list.size();
        for (int ndx = 0; ndx < last; ndx++) {
            writeln(((java.lang.String) (list.elementAt(ndx))));
        }
        list.removeAllElements();
        output.flush();
    }

    /**
     * Gets the Output attribute of the LineQueue object
     *
     * @return The Output value
     */
    protected java.io.PrintWriter getOutput() {
        return output;
    }

    /**
     * Writes a single line to the output stream
     *
     * @param value
     * 		Description of Parameter
     */
    protected void writeln(java.lang.String value) {
        buffer.setLength(0);
        buffer.append(value);
        int length = value.length();
        while ((length > 0) && java.lang.Character.isWhitespace(buffer.charAt(length - 1))) {
            length--;
            buffer.setLength(length);
        } 
        output.print(buffer.toString());
        output.print(endOfLine);
        // Next line
        lineNumber++;
    }

    /**
     * Updates the line
     *
     * @param comment
     * 		the comment to update
     * @param prefix
     * 		Description of Parameter
     */
    private void updateLine(java.lang.String comment, java.lang.String prefix) {
        java.lang.String first = ((java.lang.String) (list.elementAt(0)));
        if (first.length() == 0) {
            list.setElementAt(makeLine(prefix, comment), 0);
            return;
        }
        if (!sharedIncremental) {
            list.setElementAt(makeLine(first, comment), 0);
            return;
        }
        java.lang.StringBuffer buffer = new java.lang.StringBuffer(first);
        for (int ndx = 0; ndx < incrementalSpace; ndx++) {
            buffer.append(" ");
        }
        buffer.append(comment);
        list.setElementAt(buffer.toString(), 0);
    }

    /**
     * Creates a line
     *
     * @param prefix
     * 		the prefix of the comment
     * @param comment
     * 		the comment
     * @return a string with the correct length
     */
    private java.lang.String makeLine(java.lang.String prefix, java.lang.String comment) {
        if (prefix.length() == 0) {
            return comment;
        }
        java.lang.String trim = prefix.trim();
        boolean hasNoCode = (trim == null) || (trim.length() == 0);
        boolean indent = (hasNoCode && (!ownlineCode)) || ((!hasNoCode) && (!sharedIncremental));
        if (indent) {
            java.lang.StringBuffer buffer;
            if (!hasNoCode) {
                buffer = new java.lang.StringBuffer(prefix);
            } else {
                buffer = new java.lang.StringBuffer();
            }
            while (buffer.length() < absoluteSpace) {
                buffer.append(" ");
            } 
            buffer.append(comment);
            return buffer.toString();
        } else {
            return prefix + comment;
        }
    }
}