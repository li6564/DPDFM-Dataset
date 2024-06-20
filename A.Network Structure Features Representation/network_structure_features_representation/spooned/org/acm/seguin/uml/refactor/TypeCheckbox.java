/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Holds a type summary in addition to being a checkbox
 *
 * @author Chris Seguin
 */
class TypeCheckbox extends javax.swing.JCheckBox {
    private org.acm.seguin.summary.TypeSummary type;

    /**
     * Constructor for the TypeCheckbox object
     *
     * @param init
     * 		Description of Parameter
     */
    public TypeCheckbox(org.acm.seguin.summary.TypeSummary init) {
        super(" ");
        type = init;
        setText(getFullName());
        setSize(getPreferredSize());
        setSelected(true);
    }

    /**
     * Gets the TypeSummary attribute of the TypeCheckbox object
     *
     * @return The TypeSummary value
     */
    public org.acm.seguin.summary.TypeSummary getTypeSummary() {
        return type;
    }

    /**
     * Gets the FullName attribute of the TypeCheckbox object
     *
     * @return The FullName value
     */
    java.lang.String getFullName() {
        java.lang.StringBuffer buf = new java.lang.StringBuffer(type.getName());
        org.acm.seguin.summary.Summary current = type.getParent();
        while (current != null) {
            if (current instanceof org.acm.seguin.summary.TypeSummary) {
                buf.insert(0, ".");
                buf.insert(0, ((org.acm.seguin.summary.TypeSummary) (current)).getName());
            } else if (current instanceof org.acm.seguin.summary.PackageSummary) {
                java.lang.String temp = ((org.acm.seguin.summary.PackageSummary) (current)).getName();
                if ((temp != null) && (temp.length() > 0)) {
                    buf.insert(0, ".");
                    buf.insert(0, temp);
                }
            }
            current = current.getParent();
        } 
        return buf.toString();
    }
}