/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary;
/**
 * Summarize a type declaration. This object is meant to store the name and
 *  package of some type. This will be used in variable summaries, as well as
 *  for return values and exceptions.
 *
 * @author Chris Seguin
 * @created May 11, 1999
 */
public class TypeDeclSummary extends org.acm.seguin.summary.Summary {
    // Instance Variables
    private java.lang.String typeName;

    private java.lang.String packageName;

    private boolean primitive;

    private int arrayCount;

    /**
     * Creates a type declaration summary.
     *
     * @param parentSummary
     * 		the parent summary
     */
    public TypeDeclSummary(org.acm.seguin.summary.Summary parentSummary) {
        // Initialize the parent class
        super(parentSummary);
        // Remember the type name
        typeName = "void".intern();
        // The package name doesn't apply
        packageName = null;
        // This is a primitive value
        primitive = true;
        // This isn't an array (yet)
        arrayCount = 0;
    }

    /**
     * Creates a type declaration summary from an ASTName object.
     *
     * @param parentSummary
     * 		the parent summary
     * @param nameNode
     * 		the ASTName object
     */
    public TypeDeclSummary(org.acm.seguin.summary.Summary parentSummary, org.acm.seguin.parser.ast.ASTName nameNode) {
        // Initialize the parent class
        super(parentSummary);
        // Local Variables
        int numChildren = nameNode.getNameSize();
        // Determine the name type
        typeName = nameNode.getNamePart(numChildren - 1).intern();
        // Extract the package
        if (numChildren > 1) {
            java.lang.StringBuffer buffer = new java.lang.StringBuffer(nameNode.getNamePart(0));
            for (int ndx = 1; ndx < (numChildren - 1); ndx++) {
                buffer.append(".");
                buffer.append(nameNode.getNamePart(ndx));
            }
            packageName = buffer.toString().intern();
        } else {
            packageName = null;
        }
        // This isn't a primitive value
        primitive = false;
        // This isn't an array (yet)
        arrayCount = 0;
    }

    /**
     * Creates a type declaration summary from an ASTPrimitiveType object.
     *
     * @param parentSummary
     * 		the parent summary
     * @param primitiveType
     * 		the ASTPrimitiveType object
     */
    public TypeDeclSummary(org.acm.seguin.summary.Summary parentSummary, org.acm.seguin.parser.ast.ASTPrimitiveType primitiveType) {
        // Initialize the parent class
        super(parentSummary);
        // Remember the type name
        typeName = primitiveType.getName().intern();
        // The package name doesn't apply
        packageName = null;
        // This is a primitive value
        primitive = true;
        // This isn't an array (yet)
        arrayCount = 0;
    }

    /**
     * Creates a type declaration summary from an ASTName object.
     *
     * @param parentSummary
     * 		the parent summary
     * @param initPackage
     * 		the package name
     * @param initType
     * 		the type name
     */
    public TypeDeclSummary(org.acm.seguin.summary.Summary parentSummary, java.lang.String initPackage, java.lang.String initType) {
        super(parentSummary);
        typeName = initType;
        packageName = initPackage;
        primitive = false;
        arrayCount = 0;
    }

    /**
     * Set the array count
     *
     * @param count
     * 		the number of "[]" pairs
     */
    public void setArrayCount(int count) {
        if (count >= 0) {
            arrayCount = count;
        }
    }

    /**
     * Return the number of "[]" pairs
     *
     * @return the array count
     */
    public int getArrayCount() {
        return arrayCount;
    }

    /**
     * Is this an array?
     *
     * @return true if this is an array
     */
    public boolean isArray() {
        return arrayCount > 0;
    }

    /**
     * Get the package name
     *
     * @return a string containing the name of the package
     */
    public java.lang.String getPackage() {
        return packageName;
    }

    /**
     * Get the name of the type
     *
     * @return a string containing the name of the type
     */
    public java.lang.String getType() {
        return typeName;
    }

    /**
     * Check if this is a primitive node
     *
     * @return true if this is a primitive value
     */
    public boolean isPrimitive() {
        return primitive;
    }

    /**
     * Get long name
     *
     * @return the long version of the name (type + package)
     */
    public java.lang.String getLongName() {
        if (packageName == null) {
            return typeName;
        } else {
            return (packageName + ".") + typeName;
        }
    }

    /**
     * Convert this into a string
     *
     * @return a string representation of the type
     */
    public java.lang.String toString() {
        // Add the package and type names
        if (!isArray()) {
            return getLongName();
        }
        // Start with the long name
        java.lang.StringBuffer buffer = new java.lang.StringBuffer(getLongName());
        // Append the array counts
        for (int ndx = 0; ndx < arrayCount; ndx++) {
            buffer.append("[]");
        }
        // Return the result
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
        if (other instanceof org.acm.seguin.summary.TypeDeclSummary) {
            org.acm.seguin.summary.TypeDeclSummary tds = ((org.acm.seguin.summary.TypeDeclSummary) (other));
            boolean sameType = ((typeName == null) && (tds.typeName == null)) || ((typeName != null) && typeName.equals(tds.typeName));
            boolean samePackage = ((packageName == null) && (tds.packageName == null)) || ((packageName != null) && packageName.equals(tds.packageName));
            boolean samePrimitive = primitive == tds.primitive;
            boolean sameArray = arrayCount == tds.arrayCount;
            return ((sameType && samePackage) && samePrimitive) && sameArray;
        }
        return super.equals(other);
    }

    public boolean isSame(org.acm.seguin.summary.TypeDeclSummary other) {
        if (primitive) {
            if (!other.primitive) {
                return false;
            }
            return typeName.equals(other.typeName);
        }
        org.acm.seguin.summary.TypeSummary type1 = org.acm.seguin.summary.query.GetTypeSummary.query(this);
        org.acm.seguin.summary.TypeSummary type2 = org.acm.seguin.summary.query.GetTypeSummary.query(other);
        return type1 == type2;
    }

    /**
     * Factory method. Creates a type decl summary object from the type node.
     *
     * @param parentSummary
     * 		the parent summary
     * @param typeNode
     * 		the AST node containing the type
     * @return the new node
     */
    public static org.acm.seguin.summary.TypeDeclSummary getTypeDeclSummary(org.acm.seguin.summary.Summary parentSummary, org.acm.seguin.parser.ast.ASTType typeNode) {
        // Local Variables
        org.acm.seguin.summary.TypeDeclSummary result;
        org.acm.seguin.parser.Node typeChild = typeNode.jjtGetChild(0);
        if (typeChild instanceof org.acm.seguin.parser.ast.ASTPrimitiveType) {
            result = new org.acm.seguin.summary.TypeDeclSummary(parentSummary, ((org.acm.seguin.parser.ast.ASTPrimitiveType) (typeChild)));
        } else {
            // if (typeChild instanceof ASTName)
            result = new org.acm.seguin.summary.TypeDeclSummary(parentSummary, ((org.acm.seguin.parser.ast.ASTName) (typeChild)));
        }
        result.setArrayCount(typeNode.getArrayCount());
        return result;
    }

    /**
     * Factory method. Creates a type decl summary object from the type node.
     *
     * @param parentSummary
     * 		the parent summary
     * @param typeNode
     * 		the AST node containing the result type
     * @return the new node
     */
    public static org.acm.seguin.summary.TypeDeclSummary getTypeDeclSummary(org.acm.seguin.summary.Summary parentSummary, org.acm.seguin.parser.ast.ASTResultType typeNode) {
        if (typeNode.hasAnyChildren()) {
            return org.acm.seguin.summary.TypeDeclSummary.getTypeDeclSummary(parentSummary, ((org.acm.seguin.parser.ast.ASTType) (typeNode.jjtGetChild(0))));
        } else {
            return new org.acm.seguin.summary.TypeDeclSummary(parentSummary);
        }
    }

    public java.lang.String getName() {
        return toString();
    }
}