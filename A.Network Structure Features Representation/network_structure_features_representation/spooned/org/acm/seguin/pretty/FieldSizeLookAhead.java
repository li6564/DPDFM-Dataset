/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty;
/**
 * Helps determine the size of a field for spacing purposes
 *
 * @author Chris Seguin
 */
class FieldSizeLookAhead {
    private org.acm.seguin.pretty.FieldSize fieldSize;

    private int code;

    /**
     * Constructor for the FieldSizeLookAhead object
     *
     * @param init
     * 		Description of Parameter
     */
    public FieldSizeLookAhead(int init) {
        fieldSize = new org.acm.seguin.pretty.FieldSize();
        code = init;
    }

    /**
     * Main processing method for the FieldSizeLookAhead object
     *
     * @param body
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public org.acm.seguin.pretty.FieldSize run(org.acm.seguin.parser.ast.SimpleNode body) {
        int last = body.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (body.jjtGetChild(ndx)));
            if (child.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTFieldDeclaration) {
                org.acm.seguin.parser.ast.ASTFieldDeclaration field = ((org.acm.seguin.parser.ast.ASTFieldDeclaration) (child.jjtGetChild(0)));
                if ((code != org.acm.seguin.pretty.PrintData.DFS_NOT_WITH_JAVADOC) || (!isJavadocAttached(field))) {
                    int equalsLength = computeEqualsLength(field);
                    fieldSize.setMinimumEquals(equalsLength);
                }
            }
        }
        return fieldSize;
    }

    /**
     * Compute the size of the modifiers, type, and name
     *
     * @param field
     * 		the field in question
     * @return the size of the modifiers, type, and name
     */
    public int computeEqualsLength(org.acm.seguin.parser.ast.ASTFieldDeclaration field) {
        int modifierLength = computeModifierLength(field);
        int typeLength = computeTypeLength(field);
        int nameLength = computeNameLength(field);
        int equalsLength = (modifierLength + typeLength) + nameLength;
        return equalsLength;
    }

    /**
     * Computes the length of the field declaration type
     *
     * @param field
     * 		the field
     * @return the number
     */
    public int computeTypeLength(org.acm.seguin.parser.ast.ASTFieldDeclaration field) {
        org.acm.seguin.parser.ast.ASTType typeNode = ((org.acm.seguin.parser.ast.ASTType) (field.jjtGetChild(0)));
        int typeLength = 2 * typeNode.getArrayCount();
        if (typeNode.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTPrimitiveType) {
            org.acm.seguin.parser.ast.ASTPrimitiveType primitive = ((org.acm.seguin.parser.ast.ASTPrimitiveType) (typeNode.jjtGetChild(0)));
            typeLength += primitive.getName().length();
        } else {
            org.acm.seguin.parser.ast.ASTName name = ((org.acm.seguin.parser.ast.ASTName) (typeNode.jjtGetChild(0)));
            typeLength += name.getName().length();
        }
        fieldSize.setTypeLength(typeLength);
        return typeLength;
    }

    /**
     * Gets the JavadocAttached attribute of the FieldSizeLookAhead object
     *
     * @param node
     * 		Description of Parameter
     * @return The JavadocAttached value
     */
    private boolean isJavadocAttached(org.acm.seguin.parser.ast.ASTFieldDeclaration node) {
        return ((((((hasJavadoc(node.getSpecial("static")) || hasJavadoc(node.getSpecial("transient"))) || hasJavadoc(node.getSpecial("volatile"))) || hasJavadoc(node.getSpecial("final"))) || hasJavadoc(node.getSpecial("public"))) || hasJavadoc(node.getSpecial("protected"))) || hasJavadoc(node.getSpecial("private"))) || hasJavadoc(getInitialToken(((org.acm.seguin.parser.ast.ASTType) (node.jjtGetChild(0)))));
    }

    /**
     * Check the initial token, and removes it from the object.
     *
     * @param top
     * 		the type
     * @return the initial token
     */
    private org.acm.seguin.parser.Token getInitialToken(org.acm.seguin.parser.ast.ASTType top) {
        if (top.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTPrimitiveType) {
            org.acm.seguin.parser.ast.ASTPrimitiveType primitiveType = ((org.acm.seguin.parser.ast.ASTPrimitiveType) (top.jjtGetChild(0)));
            return primitiveType.getSpecial("primitive");
        } else {
            org.acm.seguin.parser.ast.ASTName name = ((org.acm.seguin.parser.ast.ASTName) (top.jjtGetChild(0)));
            return name.getSpecial("id0");
        }
    }

    /**
     * Description of the Method
     *
     * @param field
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private int computeNameLength(org.acm.seguin.parser.ast.ASTFieldDeclaration field) {
        org.acm.seguin.parser.ast.ASTVariableDeclaratorId id = ((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (field.jjtGetChild(1).jjtGetChild(0)));
        int nameLength = id.getName().length();
        fieldSize.setNameLength(nameLength);
        return nameLength;
    }

    /**
     * Description of the Method
     *
     * @param field
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private int computeModifierLength(org.acm.seguin.parser.ast.ASTFieldDeclaration field) {
        int fieldLength = field.getModifiersString().length();
        fieldSize.setModifierLength(fieldLength);
        return fieldLength;
    }

    /**
     * Description of the Method
     *
     * @param tok
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private boolean hasJavadoc(org.acm.seguin.parser.Token tok) {
        org.acm.seguin.parser.Token current = tok;
        while (current != null) {
            if (current.kind == org.acm.seguin.parser.JavaParserConstants.FORMAL_COMMENT) {
                return true;
            }
            current = current.specialToken;
        } 
        return false;
    }
}