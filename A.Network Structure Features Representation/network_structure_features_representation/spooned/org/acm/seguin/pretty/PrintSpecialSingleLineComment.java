/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty;
/**
 * Consume a single line comment
 *
 * @author Chris Seguin
 * @created October 14, 1999
 * @date April 10, 1999
 */
public class PrintSpecialSingleLineComment extends org.acm.seguin.pretty.PrintSpecial {
    /**
     * Determines if this print special can handle the current object
     *
     * @param spec
     * 		Description of Parameter
     * @return true if this one should process the input
     */
    public boolean isAcceptable(org.acm.seguin.pretty.SpecialTokenData spec) {
        return spec.getTokenType() == org.acm.seguin.parser.JavaParserConstants.SINGLE_LINE_COMMENT;
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
        // Make sure we are indented
        if ((!printData.isLineIndented()) && (spec.getSpecialToken().beginColumn > 2)) {
            printData.indent();
        }
        if (spec.getSpecialToken().beginColumn < 2) {
            printData.reset();
        }
        // Print the comment
        printData.appendComment(spec.getTokenImage().trim(), org.acm.seguin.pretty.PrintData.SINGLE_LINE_COMMENT);
        return true;
    }
}