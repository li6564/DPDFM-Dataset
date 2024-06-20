/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty;
/**
 * Prints a description from a java doc comment with HTML tags formatted.
 *
 * @author Chris Seguin
 * @created July 23, 1999
 */
/* </Methods> */
public class JavadocDescriptionPrinter {
    /* <Instance Variables> */
    private org.acm.seguin.pretty.PrintData printData;

    private java.lang.StringBuffer buffer;

    private int indent;

    private int mode;

    private boolean newline;

    private int owedLines;

    /* </Instance Variables> */
    /* <Class Variables> */
    private static int NORMAL = 0;

    private static int PARA = 1;

    private static int LIST = 2;

    private static int END_LIST = 3;

    private static int TABLE = 4;

    private static int END_TAG = 5;

    private static int LINE_BREAK = 6;

    private static int PREFORMATTED = 7;

    /* </Class Variables> */
    /* <Constructors> */
    /**
     * Constructor for the JavadocDescriptionPrinter object
     *
     * @param data
     * 		Description of Parameter
     * @param description
     * 		Description of Parameter
     */
    public JavadocDescriptionPrinter(org.acm.seguin.pretty.PrintData data, java.lang.String description) {
        printData = data;
        buffer = new java.lang.StringBuffer(description);
        indent = printData.getJavadocIndent();
        newline = false;
        mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.NORMAL;
    }

    /**
     * Constructor for the JavadocDescriptionPrinter object
     *
     * @param data
     * 		Description of Parameter
     * @param description
     * 		Description of Parameter
     * @param initIndent
     * 		Description of Parameter
     */
    public JavadocDescriptionPrinter(org.acm.seguin.pretty.PrintData data, java.lang.String description, int initIndent) {
        printData = data;
        buffer = new java.lang.StringBuffer(description);
        indent = initIndent;
    }

    /* </Constructors> */
    /* <Methods> */
    /**
     * This is the main program.
     */
    public void run() {
        if (printData.isReformatComments()) {
            int MIN = printData.getJavadocWordWrapMinimum();
            int MAX = printData.getJavadocWordWrapMaximum();
            org.acm.seguin.pretty.JavadocTokenizer tok = new org.acm.seguin.pretty.JavadocTokenizer(buffer.toString());
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.NORMAL;
            boolean first = true;
            while (tok.hasNext()) {
                org.acm.seguin.parser.Token nextToken = tok.next();
                first = printToken(nextToken, MIN, MAX, first);
            } 
        } else {
            maintainCurrentFormat();
        }
    }

    /**
     * Indents the line and inserts the required "*"
     */
    protected void indent() {
        if (printData.isCurrentSingle())
            return;

        newline = true;
        printData.indent();
        printData.appendComment(" *", org.acm.seguin.pretty.PrintData.JAVADOC_COMMENT);
        if (printData.isReformatComments() && (mode != org.acm.seguin.pretty.JavadocDescriptionPrinter.PREFORMATTED)) {
            for (int ndx = 0; ndx < indent; ndx++) {
                printData.space();
            }
        }
    }

    /**
     * Certain tags require that we insert a new line after them.
     *
     * @param token
     * 		the tag that we are considering
     * @return true if we just printed a space or a newline
     */
    protected boolean startMode(java.lang.String token) {
        if (startsWith(token, "<PRE") || startsWith(token, "<CODE")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.PREFORMATTED;
        } else if (startsWith(token, "</PRE") || startsWith(token, "</CODE")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.NORMAL;
        } else if (startsWith(token, "<P")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.PARA;
        } else if (startsWith(token, "<BR")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.LINE_BREAK;
        } else if (startsWith(token, "<UL")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.LIST;
            indent();
            indent += 2;
            return true;
        } else if (startsWith(token, "<OL")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.LIST;
            indent();
            indent += 2;
            return true;
        } else if (startsWith(token, "</UL")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.END_LIST;
            indent -= 2;
            indent();
            return true;
        } else if (startsWith(token, "</OL")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.END_LIST;
            indent -= 2;
            indent();
            return true;
        } else if (startsWith(token, "<LI")) {
            indent();
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.END_TAG;
            return true;
        } else if (startsWith(token, "<TABLE")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.TABLE;
            indent();
            indent += 2;
            return true;
        } else if (startsWith(token, "<TR")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.TABLE;
            indent();
            indent += 2;
            return true;
        } else if (startsWith(token, "<TD")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.TABLE;
            indent();
            indent += 2;
            return true;
        } else if (startsWith(token, "<TH")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.TABLE;
            indent();
            indent += 2;
            return true;
        } else if (startsWith(token, "</TABLE")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.TABLE;
            indent -= 2;
            indent();
            return true;
        } else if (startsWith(token, "</TR")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.TABLE;
            indent -= 2;
            indent();
            return true;
        } else if (startsWith(token, "</TD")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.TABLE;
            indent -= 2;
            indent();
            return true;
        } else if (startsWith(token, "</TH")) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.TABLE;
            indent -= 2;
            indent();
            return true;
        } else if (startsWith(token, "</") && (!newline)) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.END_TAG;
        }
        return false;
    }

    /**
     * Detects the end of the tag marker
     *
     * @param token
     * 		the token
     * @return Description of the Returned Value
     */
    protected boolean endMode(java.lang.String token) {
        if (mode == org.acm.seguin.pretty.JavadocDescriptionPrinter.END_TAG) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.NORMAL;
            printData.space();
            return true;
        }
        if (mode == org.acm.seguin.pretty.JavadocDescriptionPrinter.PARA) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.NORMAL;
            indent();
            indent();
            return true;
        }
        if (mode == org.acm.seguin.pretty.JavadocDescriptionPrinter.LINE_BREAK) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.NORMAL;
            indent();
            return true;
        }
        if (mode == org.acm.seguin.pretty.JavadocDescriptionPrinter.LIST) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.NORMAL;
        }
        if (mode == org.acm.seguin.pretty.JavadocDescriptionPrinter.END_LIST) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.NORMAL;
            indent();
            return true;
        }
        if (mode == org.acm.seguin.pretty.JavadocDescriptionPrinter.TABLE) {
            mode = org.acm.seguin.pretty.JavadocDescriptionPrinter.NORMAL;
            indent();
            return true;
        }
        return false;
    }

    /**
     * Checks to see if this tag is the same as what we want and ignores case
     *  troubles
     *
     * @param have
     * 		the token that we have
     * @param want
     * 		the token that we are interested in
     * @return true if what we have is the same as what we want
     */
    protected boolean startsWith(java.lang.String have, java.lang.String want) {
        return have.toUpperCase().startsWith(want);
    }

    /**
     * Description of the Method
     *
     * @param nextToken
     * 		Description of Parameter
     * @param MIN
     * 		Description of Parameter
     * @param MAX
     * 		Description of Parameter
     * @param isFirst
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private boolean printToken(org.acm.seguin.parser.Token nextToken, int MIN, int MAX, boolean isFirst) {
        if (nextToken.kind == org.acm.seguin.pretty.JavadocTokenizer.WORD) {
            newline = false;
            int length = nextToken.image.length();
            if (((printData.getLineLength() > MIN) && ((printData.getLineLength() + length) > MAX)) && (mode != org.acm.seguin.pretty.JavadocDescriptionPrinter.PREFORMATTED)) {
                indent();
                newline = true;
            }
            if (nextToken.image.charAt(0) == '<') {
                newline = startMode(nextToken.image.toUpperCase());
            } else {
                newline = false;
            }
            printData.appendComment(nextToken.image, org.acm.seguin.pretty.PrintData.JAVADOC_COMMENT);
            if (nextToken.image.charAt(nextToken.image.length() - 1) == '>') {
                newline = endMode(nextToken.image) || newline;
            }
            return newline;
        } else {
            if (mode != org.acm.seguin.pretty.JavadocDescriptionPrinter.PREFORMATTED) {
                if (!isFirst) {
                    printData.space();
                    return true;
                }
            } else if (nextToken.kind == org.acm.seguin.pretty.JavadocTokenizer.SPACE) {
                printData.appendComment(nextToken.image, org.acm.seguin.pretty.PrintData.JAVADOC_COMMENT);
            } else {
                indent();
            }
            return isFirst;
        }
    }

    /**
     * Maintains the current format
     */
    private void maintainCurrentFormat() {
        org.acm.seguin.pretty.JavadocTokenizer tok = new org.acm.seguin.pretty.JavadocTokenizer(buffer.toString());
        owedLines = 0;
        org.acm.seguin.parser.Token last = null;
        org.acm.seguin.parser.Token current = tok.next();
        while (current.kind != org.acm.seguin.pretty.JavadocTokenizer.WORD) {
            last = current;
            if (!tok.hasNext()) {
                return;
            }
            current = tok.next();
        } 
        if ((last != null) && (last.kind != org.acm.seguin.pretty.JavadocTokenizer.NEWLINE)) {
            mcfOutputToken(last, printData);
        }
        mcfOutputToken(current, printData);
        while (tok.hasNext()) {
            org.acm.seguin.parser.Token nextToken = tok.next();
            mcfOutputToken(nextToken, printData);
        } 
    }

    /**
     * Description of the Method
     *
     * @param nextToken
     * 		Description of Parameter
     * @param printData
     * 		Description of Parameter
     */
    private void mcfOutputToken(org.acm.seguin.parser.Token nextToken, org.acm.seguin.pretty.PrintData printData) {
        if (nextToken.kind == org.acm.seguin.pretty.JavadocTokenizer.NEWLINE) {
            owedLines++;
        } else {
            while (owedLines > 0) {
                indent();
                owedLines--;
            } 
            printData.appendComment(nextToken.image, org.acm.seguin.pretty.PrintData.JAVADOC_COMMENT);
        }
    }
}