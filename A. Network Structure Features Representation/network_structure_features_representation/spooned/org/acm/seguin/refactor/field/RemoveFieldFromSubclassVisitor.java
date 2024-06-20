/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.refactor.field;
/**
 * Removes the field from all subclasses of a particular class.
 *
 * @author Chris Seguin
 */
public class RemoveFieldFromSubclassVisitor extends org.acm.seguin.summary.TraversalVisitor {
    private org.acm.seguin.summary.FieldSummary target;

    private org.acm.seguin.summary.TypeSummary ancestor;

    private org.acm.seguin.refactor.ComplexTransform complex;

    private org.acm.seguin.summary.TypeSummary notHere;

    /**
     * Constructor for the RemoveFieldFromSubclassVisitor object
     *
     * @param type
     * 		the ancestor type
     * @param init
     * 		the field
     * @param notThisOne
     * 		a type to skip
     * @param transform
     * 		Description of Parameter
     */
    public RemoveFieldFromSubclassVisitor(org.acm.seguin.summary.TypeSummary type, org.acm.seguin.summary.FieldSummary init, org.acm.seguin.summary.TypeSummary notThisOne, org.acm.seguin.refactor.ComplexTransform transform) {
        target = init;
        ancestor = type;
        notHere = notThisOne;
        complex = transform;
    }

    /**
     * Visits a file summary node and updates it if necessary
     *
     * @param fileSummary
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.summary.FileSummary fileSummary, java.lang.Object data) {
        complex.clear();
        super.visit(fileSummary, data);
        if (complex.hasAnyChanges()) {
            complex.apply(fileSummary.getFile(), fileSummary.getFile());
        }
        return data;
    }

    /**
     * Visits a type summary and updates it
     *
     * @param typeSummary
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.summary.TypeSummary typeSummary, java.lang.Object data) {
        if ((typeSummary != notHere) && org.acm.seguin.summary.query.Ancestor.query(typeSummary, ancestor)) {
            java.util.Iterator iter = typeSummary.getFields();
            if (iter != null) {
                while (iter.hasNext()) {
                    visit(((org.acm.seguin.summary.FieldSummary) (iter.next())), data);
                } 
            }
        }
        return data;
    }

    /**
     * Visits the field summary and determines if it should be removed.
     *
     * @param fieldSummary
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.summary.FieldSummary fieldSummary, java.lang.Object data) {
        if (fieldSummary.getName().equals(target.getName())) {
            org.acm.seguin.summary.TypeDeclSummary current = fieldSummary.getTypeDecl();
            org.acm.seguin.summary.TypeDeclSummary targetDecl = target.getTypeDecl();
            if (org.acm.seguin.summary.query.GetTypeSummary.query(current) == org.acm.seguin.summary.query.GetTypeSummary.query(targetDecl)) {
                complex.add(new org.acm.seguin.refactor.field.RemoveFieldTransform(target.getName()));
            }
        }
        return data;
    }
}