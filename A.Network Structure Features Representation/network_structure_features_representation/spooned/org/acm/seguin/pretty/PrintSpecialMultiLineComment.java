/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty;
/**
 * Consume a multi line comment
 *
 * @author Chris Seguin
 * @created October 14, 1999
 * @date April 10, 1999
 */
public class PrintSpecialMultiLineComment extends org.acm.seguin.pretty.PrintSpecial {
    /**
     * Determines if this print special can handle the current object
     *
     * @param spec
     * 		Description of Parameter
     * @return true if this one should process the input
     */
    public boolean isAcceptable(org.acm.seguin.pretty.SpecialTokenData spec) {
        return spec.getTokenType() == org.acm.seguin.parser.JavaParserConstants.MULTI_LINE_COMMENT;
    }

    /**
     * Processes the special token
     *
     * @param node
     * 		the type of node this special is being processed for
     * @param spec
     * 		the special token data
     * @return Description of the Returned Value
     */
    public boolean process(org.acm.seguin.parser.Node node, org.acm.seguin.pretty.SpecialTokenData spec) {
        // Get the print data
        org.acm.seguin.pretty.PrintData printData = spec.getPrintData();
        java.lang.String image = spec.getTokenImage();
        int formatCode = printData.getCStyleFormatCode();
        if (formatCode == org.acm.seguin.pretty.PrintData.CSC_LEAVE_UNTOUCHED) {
            transcribe(printData, image);
        } else {
            cleanFormat(printData, image, formatCode, spec.isLast());
        }
        // Changed something
        return true;
    }

    /**
     * Cleanly format the code
     *
     * @param printData
     * 		the print data
     * @param image
     * 		the comment from the original file
     * @param formatCode
     * 		the formatting style
     * @param last
     * 		Description of Parameter
     */
    private void cleanFormat(org.acm.seguin.pretty.PrintData printData, java.lang.String image, int formatCode, boolean last) {
        // Make sure we are indented
        if ((formatCode != org.acm.seguin.pretty.PrintData.CSC_MAINTAIN_STAR) && (!printData.isLineIndented())) {
            printData.indent();
        }
        if (formatCode == org.acm.seguin.pretty.PrintData.CSC_MAINTAIN_STAR) {
            if (!printData.isBufferEmpty()) {
                printData.space();
            } else if (!printData.isLineIndented()) {
                printData.indent();
            }
        }
        // Start the comment
        printData.appendComment("/*", org.acm.seguin.pretty.PrintData.C_STYLE_COMMENT);
        if (formatCode == org.acm.seguin.pretty.PrintData.CSC_MAINTAIN_STAR) {
            // Do nothing
        } else {
            startNewline(printData, true, formatCode);
        }
        // Print the comment
        org.acm.seguin.pretty.JavadocTokenizer tok = new org.acm.seguin.pretty.JavadocTokenizer(image);
        tok.next();
        boolean lastWasNewline = false;
        boolean first = true;
        while (tok.hasNext()) {
            org.acm.seguin.parser.Token token = tok.next();
            if (first && ((formatCode == org.acm.seguin.pretty.PrintData.CSC_ALIGN_STAR) || (formatCode == org.acm.seguin.pretty.PrintData.CSC_ALIGN_BLANK))) {
                while (token.kind != org.acm.seguin.pretty.JavadocTokenizer.WORD) {
                    if (tok.hasNext()) {
                        token = tok.next();
                    } else {
                        break;
                    }
                } 
                first = false;
            }
            // On a newline skip the space so that we realign things
            if ((lastWasNewline && (token.kind == org.acm.seguin.pretty.JavadocTokenizer.SPACE)) && (formatCode != org.acm.seguin.pretty.PrintData.CSC_MAINTAIN_STAR)) {
                token = tok.next();
            }
            if (token.kind == org.acm.seguin.pretty.JavadocTokenizer.NEWLINE) {
                startNewline(printData, tok.hasNext(), formatCode);
                lastWasNewline = true;
            } else {
                printData.appendComment(token.image, org.acm.seguin.pretty.PrintData.C_STYLE_COMMENT);
                lastWasNewline = false;
            }
        } 
        // Finish the comment
        if (lastWasNewline) {
            java.lang.String rest = "/";
            if (formatCode == org.acm.seguin.pretty.PrintData.CSC_ALIGN_BLANK) {
                rest = "*/";
            }
            printData.appendComment(rest, org.acm.seguin.pretty.PrintData.C_STYLE_COMMENT);
        } else {
            if ((formatCode != org.acm.seguin.pretty.PrintData.CSC_MAINTAIN_STAR) && (!printData.isLineIndented())) {
                printData.indent();
            }
            printData.appendComment(" */", org.acm.seguin.pretty.PrintData.C_STYLE_COMMENT);
        }
        // Newline
        if (((formatCode == org.acm.seguin.pretty.PrintData.CSC_ALIGN_STAR) || (formatCode == org.acm.seguin.pretty.PrintData.CSC_ALIGN_BLANK)) && last) {
            printData.newline();
            org.acm.seguin.pretty.SpecialTokenVisitor.surpriseIndent(printData);
        }
    }

    /**
     * Starts a newline
     *
     * @param printData
     * 		The print interface
     * @param more
     * 		Are there more tokens
     * @param formatCode
     * 		the formatting style
     */
    private void startNewline(org.acm.seguin.pretty.PrintData printData, boolean more, int formatCode) {
        printData.indent();
        if (formatCode == org.acm.seguin.pretty.PrintData.CSC_ALIGN_BLANK) {
            printData.appendComment("  ", org.acm.seguin.pretty.PrintData.C_STYLE_COMMENT);
        } else {
            printData.appendComment(" *", org.acm.seguin.pretty.PrintData.C_STYLE_COMMENT);
        }
        if ((formatCode == org.acm.seguin.pretty.PrintData.CSC_MAINTAIN_STAR) || (!more)) {
            // Do nothing
        } else {
            for (int ndx = 0; ndx < printData.getCStyleIndent(); ndx++) {
                printData.appendComment(" ", org.acm.seguin.pretty.PrintData.C_STYLE_COMMENT);
            }
        }
    }

    /**
     * Simply copy the C style comment into the output file
     *
     * @param printData
     * 		the print data
     * @param image
     * 		the comment
     */
    private void transcribe(org.acm.seguin.pretty.PrintData printData, java.lang.String image) {
        java.util.StringTokenizer tok = new java.util.StringTokenizer(image, "\n\r");
        if (!printData.isBufferEmpty()) {
            printData.space();
        } else if (!printData.isLineIndented()) {
            printData.indent();
        }
        while (tok.hasMoreTokens()) {
            printData.appendComment(tok.nextToken(), org.acm.seguin.pretty.PrintData.C_STYLE_COMMENT);
            if (tok.hasMoreTokens()) {
                printData.newline();
            }
        } 
    }
}