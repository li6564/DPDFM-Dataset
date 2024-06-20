/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty;
/**
 * Store a portion of a javadoc item
 *
 * @author Chris Seguin
 * @created April 15, 1999
 */
public class JavaDocComponent {
    // Instance Variable
    private java.lang.String type;

    private java.lang.String description;

    private int longestLength = 0;

    private boolean printed;

    private boolean required;

    /**
     * Create an instance of this java doc object
     */
    public JavaDocComponent() {
        type = "";
        description = "";
        printed = false;
        required = false;
    }

    /**
     * Set the type
     *
     * @param newType
     * 		the new type
     */
    public void setType(java.lang.String newType) {
        if (newType != null) {
            type = newType;
            setLongestLength(type.length() + 2);
        }
    }

    /**
     * Set the description
     *
     * @param newDescription
     * 		the new description
     */
    public void setDescription(java.lang.String newDescription) {
        if (newDescription != null) {
            description = newDescription;
        }
    }

    /**
     * Set the longestLength
     *
     * @param newLongestLength
     * 		the new longestLength
     */
    public void setLongestLength(int newLongestLength) {
        longestLength = newLongestLength;
    }

    /**
     * Note that this node is required
     *
     * @param req
     * 		true if it is required
     */
    public void setRequired(boolean req) {
        required = req;
    }

    /**
     * Return the type
     *
     * @return the type
     */
    public java.lang.String getType() {
        return type;
    }

    /**
     * Return the description
     *
     * @return the description
     */
    public java.lang.String getDescription() {
        return description;
    }

    /**
     * Return the longestLength
     *
     * @return the longestLength
     */
    public int getLongestLength() {
        return longestLength;
    }

    /**
     * Return whether this node has been printed
     *
     * @return true if it was printed
     */
    public boolean isPrinted() {
        return printed;
    }

    /**
     * Return whether this node is required
     *
     * @return true if it is required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Print this tag
     *
     * @param printData
     * 		printData
     */
    public void print(org.acm.seguin.pretty.PrintData printData) {
        // We are now printing it
        setPrinted(true);
        // Start the line
        if (!printData.isCurrentSingle()) {
            printData.indent();
            printData.appendComment(" *", org.acm.seguin.pretty.PrintData.JAVADOC_COMMENT);
        }
        if (printData.isSpaceBeforeAt() && (!isDescription())) {
            printData.appendComment(" ", org.acm.seguin.pretty.PrintData.JAVADOC_COMMENT);
        }
        // Print the type
        if (!isDescription()) {
            printData.appendComment(getType(), org.acm.seguin.pretty.PrintData.JAVADOC_COMMENT);
        }
        // Pad extra spaces after the ID
        if ((!isDescription()) && printData.isJavadocLinedUp()) {
            int extra = getLongestLength() - getType().length();
            for (int ndx = 0; ndx < extra; ndx++) {
                printData.appendComment(" ", org.acm.seguin.pretty.PrintData.JAVADOC_COMMENT);
            }
        }
        // Pad any extra spaces after the stars and before the text
        if (printData.isReformatComments() || (!isDescription())) {
            for (int i = 0; i < printData.getJavadocIndent(); ++i) {
                printData.appendComment(" ", org.acm.seguin.pretty.PrintData.JAVADOC_COMMENT);
            }
        }
        // Print the description
        printDescription(printData);
        if (!printData.isCurrentSingle()) {
            printData.newline();
        }
    }

    /**
     * Note that this node has been printed
     *
     * @param prn
     * 		Description of Parameter
     */
    protected void setPrinted(boolean prn) {
        printed = prn;
    }

    /**
     * Print the drescription
     *
     * @param printData
     * 		the print data
     */
    protected void printDescription(org.acm.seguin.pretty.PrintData printData) {
        // if (getDescription().indexOf("\n") >= 0) {
        // leaveDescription(printData);
        // }
        // else {
        wordwrapDescription(printData, getType().length() == 0);
        // }
    }

    /**
     * Print the drescription
     *
     * @param printData
     * 		the print data
     * @param isDescription
     * 		is this a description of an entire block of code
     */
    protected void wordwrapDescription(org.acm.seguin.pretty.PrintData printData, boolean isDescription) {
        org.acm.seguin.pretty.JavadocDescriptionPrinter jdp = new org.acm.seguin.pretty.JavadocDescriptionPrinter(printData, getDescription(), isDescription ? printData.getJavadocIndent() : 6);
        jdp.run();
    }

    /**
     * Print the drescription
     *
     * @param printData
     * 		the print data
     */
    protected void leaveDescription(org.acm.seguin.pretty.PrintData printData) {
        java.lang.StringBuffer sb = new java.lang.StringBuffer(printData.getJavadocIndent());
        for (int i = 0; i < printData.getJavadocIndent(); ++i) {
            sb.append(" ");
        }
        java.lang.String indent = sb.toString();
        java.util.StringTokenizer tok = new java.util.StringTokenizer(getDescription(), "\n\r");
        boolean first = true;
        while (tok.hasMoreTokens()) {
            java.lang.String nextToken = tok.nextToken();
            if (!first) {
                printData.indent();
                printData.appendComment(" *", org.acm.seguin.pretty.PrintData.JAVADOC_COMMENT);
                printData.appendComment(indent, org.acm.seguin.pretty.PrintData.JAVADOC_COMMENT);
            }
            printData.appendComment(nextToken, org.acm.seguin.pretty.PrintData.JAVADOC_COMMENT);
            first = false;
        } 
    }

    /**
     * returns true if this is a description
     *
     * @return true if it is a description
     */
    boolean isDescription() {
        return getType().length() == 0;
    }
}