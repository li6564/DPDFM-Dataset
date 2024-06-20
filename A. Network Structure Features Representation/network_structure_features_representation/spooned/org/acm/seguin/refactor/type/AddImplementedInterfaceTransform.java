/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.type;
/**
 * This object will add a new interface to the implements clause of a class
 *  declaration. If no implements clause exists, one will be added.
 *
 * @author Grant Watson
 * @created December 1, 2000
 */
public class AddImplementedInterfaceTransform extends org.acm.seguin.refactor.TransformAST {
    private org.acm.seguin.parser.ast.ASTName m_interfaceName;

    /**
     * Constructor for the AddImplementsTransform object
     *
     * @param interfaceName
     * 		Description of Parameter
     */
    public AddImplementedInterfaceTransform(org.acm.seguin.parser.ast.ASTName interfaceName) {
        m_interfaceName = interfaceName;
    }

    /**
     * Description of the Method
     *
     * @param root
     * 		Description of Parameter
     */
    public void update(org.acm.seguin.parser.ast.SimpleNode root) {
        org.acm.seguin.refactor.type.AddImplementedInterfaceVisitor aiiv = new org.acm.seguin.refactor.type.AddImplementedInterfaceVisitor();
        root.jjtAccept(aiiv, m_interfaceName);
    }
}