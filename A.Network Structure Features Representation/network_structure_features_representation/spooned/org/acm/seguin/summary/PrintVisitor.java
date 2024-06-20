/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary;
/**
 * Print all the summaries
 *
 * @author Chris Seguin
 * @created May 15, 1999
 */
public class PrintVisitor extends org.acm.seguin.summary.TraversalVisitor {
    /**
     * Visit a summary node. This is the default method.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.Summary node, java.lang.Object data) {
        // Shouldn't have to do anything about one of these nodes
        return data;
    }

    /**
     * Visit a package summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.PackageSummary node, java.lang.Object data) {
        // Print the message
        java.lang.String indent = ((java.lang.String) (data));
        java.lang.System.out.println((indent + "Package:  ") + node.getName());
        // Traverse the children
        super.visit(node, indent + "  ");
        // Doesn't change
        return data;
    }

    /**
     * Visit a file summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.FileSummary node, java.lang.Object data) {
        if (node.getFile() == null) {
            return data;
        }
        // Print the message
        java.lang.String indent = ((java.lang.String) (data));
        java.lang.System.out.println((indent + "File:  ") + node.getName());
        // Traverse the children
        super.visit(node, indent + "  ");
        // Doesn't change
        return data;
    }

    /**
     * Visit a import summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.ImportSummary node, java.lang.Object data) {
        // Print the message
        java.lang.String indent = ((java.lang.String) (data));
        java.lang.String type = node.getType();
        // Print what we have loaded
        java.lang.System.out.print((indent + "Import:  ") + node.getPackage().getName());
        if (type == null) {
            java.lang.System.out.println("  *");
        } else {
            java.lang.System.out.println("  " + type);
        }
        // Doesn't change
        return data;
    }

    /**
     * Visit a type summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.TypeSummary node, java.lang.Object data) {
        // Local Variables
        java.lang.String prefix;
        // Print the message
        java.lang.String indent = ((java.lang.String) (data));
        if (node.isInterface()) {
            java.lang.System.out.println((indent + "Interface:  ") + node.getName());
            prefix = indent + "  Extends:  ";
        } else {
            java.lang.System.out.println((indent + "Class:  ") + node.getName());
            org.acm.seguin.summary.Summary parentClass = node.getParentClass();
            if (parentClass != null) {
                java.lang.System.out.println((indent + "  Extends:  ") + parentClass.toString());
            }
            prefix = indent + "  Implements:  ";
        }
        // The iterator over interfaces
        java.util.Iterator iter = node.getImplementedInterfaces();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.TypeDeclSummary next = ((org.acm.seguin.summary.TypeDeclSummary) (iter.next()));
                java.lang.System.out.println(prefix + next.toString());
            } 
        }
        // Traverse the children
        super.visit(node, indent + "  ");
        // Doesn't change
        return data;
    }

    /**
     * Visit a method summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.MethodSummary node, java.lang.Object data) {
        // Local Variables
        java.lang.String prefix;
        // Print the message
        java.lang.String indent = ((java.lang.String) (data));
        java.lang.System.out.println((indent + "Method:  ") + node.getName());
        prefix = indent + "  Depends:  ";
        // The iterator over dependencies
        java.util.Iterator iter = node.getDependencies();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.Summary next = ((org.acm.seguin.summary.Summary) (iter.next()));
                java.lang.System.out.println(prefix + next.toString());
            } 
        }
        // Doesn't change
        return data;
    }

    /**
     * Visit a field summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.FieldSummary node, java.lang.Object data) {
        // Print the message
        java.lang.String indent = ((java.lang.String) (data));
        java.lang.System.out.println((indent + "Field:  ") + node.getName());
        // Doesn't change
        return data;
    }

    /**
     * Visit a parameter summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.ParameterSummary node, java.lang.Object data) {
        return data;
    }

    /**
     * Visit a local variable summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.LocalVariableSummary node, java.lang.Object data) {
        return data;
    }

    /**
     * Visit a variable summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.VariableSummary node, java.lang.Object data) {
        return data;
    }

    /**
     * Visit a type declaration summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.TypeDeclSummary node, java.lang.Object data) {
        return data;
    }

    /**
     * Visit a message send summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.MessageSendSummary node, java.lang.Object data) {
        return data;
    }

    /**
     * Visit a field access summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.FieldAccessSummary node, java.lang.Object data) {
        return data;
    }
}