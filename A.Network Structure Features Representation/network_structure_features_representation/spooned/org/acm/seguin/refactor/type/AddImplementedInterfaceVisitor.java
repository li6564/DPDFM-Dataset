/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.type;
/**
 * Walks the parse tree and updates it
 *
 * @author Grant Watson
 * @created December 1, 2000
 */
public class AddImplementedInterfaceVisitor extends org.acm.seguin.parser.ChildrenVisitor {
    /**
     * Constructor for the AddImplementedInterfaceVisitor object
     */
    public AddImplementedInterfaceVisitor() {
    }

    /**
     * The ASTNameList of an ASTUnmodifiedClassDeclaration holds the names of
     *  interfaces implemented by the class. This method adds an interface name
     *  specified by the data parameter to the ASTNameList . If no ASTNameList is
     *  present, then one is created.
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration node, java.lang.Object data) {
        org.acm.seguin.parser.ast.ASTName interfaceName = ((org.acm.seguin.parser.ast.ASTName) (data));
        org.acm.seguin.parser.ast.ASTNameList nameList = null;
        // Find the ASTNameList or add one
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            if (node.jjtGetChild(i) instanceof org.acm.seguin.parser.ast.ASTNameList) {
                nameList = ((org.acm.seguin.parser.ast.ASTNameList) (node.jjtGetChild(i)));
            }
        }
        if (nameList == null) {
            nameList = new org.acm.seguin.parser.ast.ASTNameList(0);
            node.jjtInsertChild(nameList, node.jjtGetNumChildren() - 1);
        }
        // Determine whether the interface name is already in the list of implemented interfaces
        boolean exists = false;
        for (int i = 0; i < nameList.jjtGetNumChildren(); i++) {
            org.acm.seguin.parser.ast.ASTName astName = ((org.acm.seguin.parser.ast.ASTName) (nameList.jjtGetChild(i)));
            if (astName.equals(interfaceName)) {
                exists = true;
                break;
            }
        }
        // If the interface is not there, then add it.
        if (!exists) {
            nameList.jjtAddChild(interfaceName, nameList.jjtGetNumChildren());
        }
        return null;
    }
}