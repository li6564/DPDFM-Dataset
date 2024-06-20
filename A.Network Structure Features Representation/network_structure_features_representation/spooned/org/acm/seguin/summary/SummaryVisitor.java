/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary;
/**
 * All items that want to visit a summary tree should implement this
 *  interface.
 *
 * @author Chris Seguin
 * @created May 12, 1999
 */
public interface SummaryVisitor {
    /**
     * Visit a summary node. This is the default method.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.Summary node, java.lang.Object data);

    /**
     * Visit a package summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.PackageSummary node, java.lang.Object data);

    /**
     * Visit a file summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.FileSummary node, java.lang.Object data);

    /**
     * Visit a import summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.ImportSummary node, java.lang.Object data);

    /**
     * Visit a type summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.TypeSummary node, java.lang.Object data);

    /**
     * Visit a method summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.MethodSummary node, java.lang.Object data);

    /**
     * Visit a field summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.FieldSummary node, java.lang.Object data);

    /**
     * Visit a parameter summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.ParameterSummary node, java.lang.Object data);

    /**
     * Visit a local variable summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.LocalVariableSummary node, java.lang.Object data);

    /**
     * Visit a variable summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.VariableSummary node, java.lang.Object data);

    /**
     * Visit a type declaration summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.TypeDeclSummary node, java.lang.Object data);

    /**
     * Visit a message send summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.MessageSendSummary node, java.lang.Object data);

    /**
     * Visit a field access summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.FieldAccessSummary node, java.lang.Object data);
}