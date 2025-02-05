/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary;
/**
 * Summarize a field access summary.
 *
 * @author Chris Seguin
 * @author Achille Petrilli, mods to distinguish read from write access
 * @created June 23, 1999
 */
public class FieldAccessSummary extends org.acm.seguin.summary.Summary {
    // Instance Variables
    private java.lang.String objectName;

    private java.lang.String packageName;

    private java.lang.String fieldName;

    private boolean isAssignment;

    /**
     * Creates a field access summary from an ASTName object.
     *
     * @param parentSummary
     * 		the parent summary
     * @param nameNode
     * 		the ASTName object
     */
    public FieldAccessSummary(org.acm.seguin.summary.Summary parentSummary, org.acm.seguin.parser.ast.ASTName nameNode) {
        // Initialize the parent class
        super(parentSummary);
        // Initialize the variables
        fieldName = null;
        objectName = null;
        packageName = null;
        isAssignment = checkAssignment(nameNode);
        // Local Variables
        int numChildren = nameNode.getNameSize();
        // Determine the name of the message
        fieldName = nameNode.getNamePart(numChildren - 1).intern();
        // Determine the name of the object (or class)
        if (numChildren > 1) {
            objectName = nameNode.getNamePart(numChildren - 2).intern();
            // Extract the package
            if (numChildren > 2) {
                java.lang.StringBuffer buffer = new java.lang.StringBuffer(nameNode.getNamePart(0));
                for (int ndx = 1; ndx < (numChildren - 2); ndx++) {
                    buffer.append(".");
                    buffer.append(nameNode.getNamePart(ndx));
                }
                packageName = buffer.toString().intern();
            }
        }
    }

    /**
     * Gets the Assignment attribute of the FieldAccessSummary object
     *
     * @return The Assignment value
     */
    public boolean isAssignment() {
        return isAssignment;
    }

    /**
     * Get the package name
     *
     * @return a string containing the name of the package
     */
    public java.lang.String getPackageName() {
        return packageName;
    }

    /**
     * Get the name of the type
     *
     * @return a string containing the name of the type
     */
    public java.lang.String getObjectName() {
        return objectName;
    }

    /**
     * Get the name of the field
     *
     * @return a string containing the name of the field
     */
    public java.lang.String getFieldName() {
        return fieldName;
    }

    /**
     * Gets a type declaration if this reference is to a package and type pair
     *
     * @return the summary
     */
    public org.acm.seguin.summary.TypeDeclSummary getTypeDecl() {
        if (packageName == null) {
            if (objectName != null) {
                org.acm.seguin.summary.TypeDeclSummary result = new org.acm.seguin.summary.TypeDeclSummary(this, packageName, objectName);
                org.acm.seguin.summary.TypeSummary test = org.acm.seguin.summary.query.GetTypeSummary.query(result);
                if (test != null) {
                    return result;
                }
            }
            return null;
        }
        return new org.acm.seguin.summary.TypeDeclSummary(this, packageName, objectName);
    }

    /**
     * Gets the FirstObject attribute of the FieldAccessSummary object
     *
     * @return The FirstObject value
     */
    public java.lang.String getFirstObject() {
        java.lang.String name = getName();
        int index = name.indexOf(".");
        if (index == (-1)) {
            return name;
        } else {
            return name.substring(0, index);
        }
    }

    /**
     * Gets the Name attribute of the FieldAccessSummary object
     *
     * @return The Name value
     */
    public java.lang.String getName() {
        // Start with the long name
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        if (packageName != null) {
            buffer.append(packageName);
            buffer.append(".");
        }
        if (objectName != null) {
            buffer.append(objectName);
            buffer.append(".");
        }
        buffer.append(fieldName);
        return buffer.toString();
    }

    /**
     * Convert this into a string
     *
     * @return a string representation of the type
     */
    public java.lang.String toString() {
        // Start with the long name
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        if (packageName != null) {
            buffer.append(packageName);
            buffer.append(".");
        }
        if (objectName != null) {
            buffer.append(objectName);
            buffer.append(".");
        }
        buffer.append(fieldName);
        if (isAssignment) {
            buffer.append("-W");
        }
        return buffer.toString();
    }

    /**
     * Provide method to visit a node
     *
     * @param visitor
     * 		the visitor
     * @param data
     * 		the data for the visit
     * @return some new data
     */
    public java.lang.Object accept(org.acm.seguin.summary.SummaryVisitor visitor, java.lang.Object data) {
        return visitor.visit(this, data);
    }

    /**
     * Check to see if it is equal
     *
     * @param other
     * 		the other item
     * @return true if they are equal
     */
    public boolean equals(java.lang.Object other) {
        if (other instanceof org.acm.seguin.summary.FieldAccessSummary) {
            org.acm.seguin.summary.FieldAccessSummary fas = ((org.acm.seguin.summary.FieldAccessSummary) (other));
            boolean sameObject = ((objectName == null) && (fas.objectName == null)) || ((objectName != null) && objectName.equals(fas.objectName));
            boolean samePackage = ((packageName == null) && (fas.packageName == null)) || ((packageName != null) && packageName.equals(fas.packageName));
            boolean sameField = ((fieldName == null) && (fas.fieldName == null)) || ((fieldName != null) && fieldName.equals(fas.fieldName));
            return ((sameObject && samePackage) && sameField) && (isAssignment == fas.isAssignment);
        }
        return super.equals(other);
    }

    /**
     * Gets the ChildIndex attribute of the FieldAccessSummary object
     *
     * @param parent
     * 		Description of Parameter
     * @param child
     * 		Description of Parameter
     * @return The ChildIndex value
     */
    private int getChildIndex(org.acm.seguin.parser.ast.SimpleNode parent, org.acm.seguin.parser.ast.SimpleNode child) {
        for (int i = 0; i < parent.jjtGetNumChildren(); i++) {
            if (parent.jjtGetChild(i) == child) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private boolean checkAssignment(org.acm.seguin.parser.ast.SimpleNode node) {
        org.acm.seguin.parser.ast.SimpleNode previous = node;
        for (org.acm.seguin.parser.ast.SimpleNode current = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetParent())); current != null; current = ((org.acm.seguin.parser.ast.SimpleNode) (current.jjtGetParent()))) {
            int nodeIdx = getChildIndex(current, previous);
            if (nodeIdx == (-1)) {
                throw new java.lang.IllegalArgumentException("Child node not found into its parent ???");
            }
            if (current.jjtGetNumChildren() > (nodeIdx + 1)) {
                if ((current instanceof org.acm.seguin.parser.ast.ASTStatementExpression) || (current instanceof org.acm.seguin.parser.ast.ASTExpression)) {
                    if (current.jjtGetChild(nodeIdx + 1) instanceof org.acm.seguin.parser.ast.ASTAssignmentOperator) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            previous = current;
        }
        return false;
    }
}