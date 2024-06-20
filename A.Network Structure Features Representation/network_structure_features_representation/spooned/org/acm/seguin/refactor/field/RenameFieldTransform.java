/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.field;
/**
 * A transform that renames a specific field
 *
 * @author Chris Seguin
 */
public class RenameFieldTransform extends org.acm.seguin.refactor.TransformAST {
    private org.acm.seguin.summary.FieldSummary oldField;

    private java.lang.String newName;

    /**
     * Constructor for the RemoveFieldTransform object
     *
     * @param oldName
     * 		Description of Parameter
     * @param newName
     * 		Description of Parameter
     */
    public RenameFieldTransform(org.acm.seguin.summary.FieldSummary oldField, java.lang.String newName) {
        this.oldField = oldField;
        this.newName = newName;
    }

    /**
     * Updates the root
     *
     * @param root
     * 		the root node
     */
    public void update(org.acm.seguin.parser.ast.SimpleNode root) {
        org.acm.seguin.refactor.field.RenameFieldVisitor rfv = new org.acm.seguin.refactor.field.RenameFieldVisitor();
        rfv.visit(root, new org.acm.seguin.refactor.field.RenameFieldData(oldField, newName));
    }
}