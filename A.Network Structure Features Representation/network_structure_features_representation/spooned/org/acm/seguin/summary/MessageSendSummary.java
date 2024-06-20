/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.summary;
/**
 * Summarize a message send summary.
 *
 * @author Chris Seguin
 * @created June 23, 1999
 */
public class MessageSendSummary extends org.acm.seguin.summary.Summary {
    // Instance Variables
    private java.lang.String objectName;

    private java.lang.String packageName;

    private java.lang.String messageName;

    private java.lang.String first;

    /**
     * Creates a message send summary from an ASTName object.
     *
     * @param parentSummary
     * 		the parent summary
     * @param nameNode
     * 		the ASTName object
     */
    public MessageSendSummary(org.acm.seguin.summary.Summary parentSummary, org.acm.seguin.parser.ast.ASTName nameNode) {
        // Initialize the parent class
        super(parentSummary);
        // Initialize the variables
        messageName = null;
        objectName = null;
        packageName = null;
        // Local Variables
        int numChildren = nameNode.getNameSize();
        // Determine the name of the message
        messageName = nameNode.getNamePart(numChildren - 1).intern();
        // Determine the name of the object (or class)
        if (numChildren > 1) {
            first = nameNode.getNamePart(0);
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
        } else {
            first = null;
        }
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
     * Get the name of the type
     *
     * @return a string containing the name of the type
     */
    public java.lang.String getMessageName() {
        return messageName;
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
     * Gets the Name attribute of the MessageSendSummary object
     *
     * @return The Name value
     */
    public java.lang.String getName() {
        return toString();
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
        buffer.append(messageName);
        buffer.append("()");
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
        if (other instanceof org.acm.seguin.summary.MessageSendSummary) {
            org.acm.seguin.summary.MessageSendSummary mss = ((org.acm.seguin.summary.MessageSendSummary) (other));
            boolean sameObject = ((objectName == null) && (mss.objectName == null)) || ((objectName != null) && objectName.equals(mss.objectName));
            boolean samePackage = ((packageName == null) && (mss.packageName == null)) || ((packageName != null) && packageName.equals(mss.packageName));
            boolean sameMessage = ((messageName == null) && (mss.messageName == null)) || ((messageName != null) && messageName.equals(mss.messageName));
            return (sameObject && samePackage) && sameMessage;
        }
        return super.equals(other);
    }

    public java.lang.String getFirstObject() {
        return first;
    }
}