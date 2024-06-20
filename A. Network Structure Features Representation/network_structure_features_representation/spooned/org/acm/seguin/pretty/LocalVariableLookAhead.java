/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty;
/**
 * Helps determine the size of a local variable for spacing purposes
 *
 * @author Chris Seguin
 */
class LocalVariableLookAhead {
    private org.acm.seguin.pretty.FieldSize size;

    /**
     * Constructor for the LocalVariableLookAhead object
     */
    public LocalVariableLookAhead() {
        size = new org.acm.seguin.pretty.FieldSize();
    }

    /**
     * Main processing method for the LocalVariableLookAhead object
     *
     * @param body
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public org.acm.seguin.pretty.FieldSize run(org.acm.seguin.parser.ast.SimpleNode body) {
        int last = body.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (body.jjtGetChild(ndx)));
            if ((child instanceof org.acm.seguin.parser.ast.ASTBlockStatement) && (child.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTLocalVariableDeclaration)) {
                org.acm.seguin.parser.ast.ASTLocalVariableDeclaration localVariable = ((org.acm.seguin.parser.ast.ASTLocalVariableDeclaration) (child.jjtGetChild(0)));
                int equalsLength = computeEqualsLength(localVariable);
                size.setMinimumEquals(equalsLength);
            }
        }
        return size;
    }

    /**
     * Compute the length of the equals
     *
     * @param localVariable
     * 		the local variable
     * @return the length
     */
    public int computeEqualsLength(org.acm.seguin.parser.ast.ASTLocalVariableDeclaration localVariable) {
        int modifierLength = computeModifierLength(localVariable);
        int typeLength = computeTypeLength(localVariable);
        int nameLength = computeNameLength(localVariable);
        int equalsLength = (modifierLength + typeLength) + nameLength;
        return equalsLength;
    }

    /**
     * Compute the length of the type
     *
     * @param localVariable
     * 		the local variable
     * @return the type length
     */
    public int computeTypeLength(org.acm.seguin.parser.ast.SimpleNode localVariable) {
        org.acm.seguin.parser.ast.ASTType typeNode = ((org.acm.seguin.parser.ast.ASTType) (localVariable.jjtGetChild(0)));
        int typeLength = 2 * typeNode.getArrayCount();
        if (typeNode.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTPrimitiveType) {
            org.acm.seguin.parser.ast.ASTPrimitiveType primitive = ((org.acm.seguin.parser.ast.ASTPrimitiveType) (typeNode.jjtGetChild(0)));
            typeLength += primitive.getName().length();
        } else {
            org.acm.seguin.parser.ast.ASTName name = ((org.acm.seguin.parser.ast.ASTName) (typeNode.jjtGetChild(0)));
            typeLength += name.getName().length();
        }
        size.setTypeLength(typeLength);
        return typeLength;
    }

    /**
     * Compute the length of the modifier
     *
     * @param localVariable
     * 		the local variable
     * @return the length of the modifier
     */
    private int computeModifierLength(org.acm.seguin.parser.ast.ASTLocalVariableDeclaration localVariable) {
        int modifierLength = (localVariable.isUsingFinal()) ? 6 : 0;
        size.setModifierLength(modifierLength);
        return modifierLength;
    }

    /**
     * Compute the length of the name
     *
     * @param localVariable
     * 		the local variable
     * @return the length
     */
    private int computeNameLength(org.acm.seguin.parser.ast.ASTLocalVariableDeclaration localVariable) {
        org.acm.seguin.parser.ast.ASTVariableDeclaratorId id = ((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (localVariable.jjtGetChild(1).jjtGetChild(0)));
        int nameLength = id.getName().length();
        size.setNameLength(nameLength);
        return nameLength;
    }
}