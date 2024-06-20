/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty;
/**
 * Consume a javadoc comment
 *
 * @author Chris Seguin
 * @created April 10, 1999
 */
public class PrintSpecialJavadocComment extends org.acm.seguin.pretty.PrintSpecial {
    /**
     * Determines if this print special can handle the current object
     *
     * @param spec
     * 		Description of Parameter
     * @return true if this one should process the input
     */
    public boolean isAcceptable(org.acm.seguin.pretty.SpecialTokenData spec) {
        return spec.getTokenType() == org.acm.seguin.parser.JavaParserConstants.FORMAL_COMMENT;
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
        org.acm.seguin.pretty.JavaDocable docable = null;
        if (node instanceof org.acm.seguin.pretty.JavaDocable) {
            docable = ((org.acm.seguin.pretty.JavaDocable) (node));
        } else if (spec.getPrintData().isAllJavadocKept()) {
            docable = new org.acm.seguin.pretty.JavaDocableImpl();
        } else {
            return false;
        }
        // Current components
        org.acm.seguin.pretty.JavaDocComponent jdc = new org.acm.seguin.pretty.JavaDocComponent();
        java.lang.StringBuffer description = new java.lang.StringBuffer();
        // Break into lines
        org.acm.seguin.pretty.JavadocTokenizer tok = new org.acm.seguin.pretty.JavadocTokenizer(spec.getTokenImage());
        while (tok.hasNext()) {
            org.acm.seguin.parser.Token next = tok.next();
            if ((next.image == null) || (next.image.length() == 0)) {
                // Do nothing
            } else if (((next.kind == org.acm.seguin.pretty.JavadocTokenizer.WORD) && (next.image.charAt(0) == '@')) && (next.image.charAt(next.image.length() - 1) != '@')) {
                storeJDCinNode(docable, jdc, description);
                jdc = createJavaDocComponent(next.image, tok);
            } else {
                description.append(next.image);
            }
        } 
        // Last JDC
        if (jdc != null) {
            storeJDCinNode(docable, jdc, description);
        }
        // Finish
        docable.finish();
        docable.printJavaDocComponents(spec.getPrintData());
        return true;
    }

    /**
     * Create new JavaDocComponent
     *
     * @param current
     * 		the current item
     * @param parts
     * 		the tokenizer
     * @return the new JavaDocComponent
     */
    private org.acm.seguin.pretty.JavaDocComponent createJavaDocComponent(java.lang.String current, org.acm.seguin.pretty.JavadocTokenizer parts) {
        org.acm.seguin.pretty.JavaDocComponent jdc;
        // Create the new jdc
        if ((current.equals("@param") || current.equals("@exception")) || current.equals("@throws")) {
            jdc = new org.acm.seguin.pretty.NamedJavaDocComponent();
            jdc.setType(current);
            while (parts.hasNext()) {
                org.acm.seguin.parser.Token next = parts.next();
                if (next.kind == org.acm.seguin.pretty.JavadocTokenizer.WORD) {
                    ((org.acm.seguin.pretty.NamedJavaDocComponent) (jdc)).setID(next.image);
                    return jdc;
                }
            } 
            return null;
        } else {
            jdc = new org.acm.seguin.pretty.NamedJavaDocComponent();
            jdc.setType(current);
        }
        // Return the result
        return jdc;
    }

    /**
     * Store JavaDocComponent in the node
     *
     * @param node
     * 		the next node
     * @param jdc
     * 		the component
     * @param descr
     * 		the description
     */
    private void storeJDCinNode(org.acm.seguin.pretty.JavaDocable node, org.acm.seguin.pretty.JavaDocComponent jdc, java.lang.StringBuffer descr) {
        if (jdc == null) {
            return;
        }
        jdc.setDescription(descr.toString().trim());
        node.addJavaDocComponent(jdc);
        descr.setLength(0);
    }
}