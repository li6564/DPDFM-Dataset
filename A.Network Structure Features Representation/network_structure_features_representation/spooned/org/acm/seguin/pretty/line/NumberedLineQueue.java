package org.acm.seguin.pretty.line;
class NumberedLineQueue extends org.acm.seguin.pretty.LineQueue {
    NumberedLineQueue(java.io.PrintWriter output) {
        super(output);
    }

    /**
     * Writes the line to the output stream
     */
    protected void writeln(java.lang.String value) {
        java.io.PrintWriter out = getOutput();
        // Insert initial spaces
        if (lineNumber < 10) {
            out.print(("    " + lineNumber) + "  ");
        } else if (lineNumber < 100) {
            out.print(("   " + lineNumber) + "  ");
        } else if (lineNumber < 1000) {
            out.print(("  " + lineNumber) + "  ");
        } else if (lineNumber < 10000) {
            out.print((" " + lineNumber) + "  ");
        } else if (lineNumber < 100000) {
            out.print(lineNumber + "  ");
        }
        // Print the line
        super.writeln(value);
    }
}