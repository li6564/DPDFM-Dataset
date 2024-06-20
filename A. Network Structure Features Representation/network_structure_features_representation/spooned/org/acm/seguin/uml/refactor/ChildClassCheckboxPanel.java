/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Panel of checkboxes with all of the children classes listed
 *
 * @author Chris Seguin
 */
class ChildClassCheckboxPanel extends javax.swing.JPanel {
    private org.acm.seguin.summary.TypeSummary parentType;

    private java.util.LinkedList childrenCheckboxes;

    /**
     * Constructor for the ChildClassCheckboxPanel object
     *
     * @param initType
     * 		The type
     */
    public ChildClassCheckboxPanel(org.acm.seguin.summary.TypeSummary initType) {
        parentType = initType;
        childrenCheckboxes = new java.util.LinkedList();
        java.util.Iterator iter = org.acm.seguin.summary.query.ChildClassSearcher.query(parentType);
        int count = 0;
        while (iter.hasNext()) {
            org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
            org.acm.seguin.uml.refactor.TypeCheckbox tcb = new org.acm.seguin.uml.refactor.TypeCheckbox(next);
            childrenCheckboxes.add(tcb);
            count++;
        } 
        int columns = (count / 10) + 1;
        setLayout(new java.awt.GridLayout((count / columns) + 1, columns));
        iter = childrenCheckboxes.iterator();
        while (iter.hasNext()) {
            add(((org.acm.seguin.uml.refactor.TypeCheckbox) (iter.next())));
        } 
    }

    /**
     * Gets the Checkboxes associated with this child class
     *
     * @return The list of type checkboxes
     */
    public java.util.Iterator getCheckboxes() {
        return childrenCheckboxes.iterator();
    }
}