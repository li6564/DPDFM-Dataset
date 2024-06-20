/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary.query;
/**
 * Searches the set of summaries for all the classes that extend a particular
 *  class.
 *
 * @author Chris Seguin
 */
public class ChildClassSearcher extends org.acm.seguin.summary.TraversalVisitor {
    /**
     * Visit a file summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result public Object visit(FileSummary node, Object data)
    { if (node.getFile() == null) { return data; } return
    super.visit(node, data); }
     */
    /**
     * Visit a file summary.
     *
     *  Visit a type summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result public Object visit(FileSummary node, Object data)
    { if (node.getFile() == null) { return data; } return
    super.visit(node, data); }
     */
    public java.lang.Object visit(org.acm.seguin.summary.TypeSummary node, java.lang.Object data) {
        org.acm.seguin.summary.query.SearchData search = ((org.acm.seguin.summary.query.SearchData) (data));
        org.acm.seguin.summary.TypeDeclSummary parentDecl = node.getParentClass();
        if (parentDecl == null) {
            return data;
        }
        org.acm.seguin.summary.TypeSummary parentTypeSummary = org.acm.seguin.summary.query.GetTypeSummary.query(parentDecl);
        if (parentTypeSummary == search.getParentType()) {
            search.addChild(node);
        }
        // Return some value
        return data;
    }

    /**
     * Generates a list of classes that extend type
     *
     * @param type
     * 		the desired parent class
     * @return an iterator of type summaries
     */
    public static java.util.Iterator query(org.acm.seguin.summary.TypeSummary type) {
        org.acm.seguin.summary.query.SearchData search = new org.acm.seguin.summary.query.SearchData(type);
        new org.acm.seguin.summary.query.ChildClassSearcher().visit(search);
        return search.getChildren();
    }
}