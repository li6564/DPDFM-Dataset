/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Panel of radio buttons containing the parameters
 *
 * @author Chris Seguin
 */
class ParameterPanel extends javax.swing.JPanel {
    private org.acm.seguin.summary.MethodSummary methodSummary;

    private java.util.LinkedList children;

    /**
     * Constructor for the ParameterPanel object
     *
     * @param initType
     * 		The type
     */
    public ParameterPanel(org.acm.seguin.summary.MethodSummary init) {
        methodSummary = init;
        children = new java.util.LinkedList();
        javax.swing.ButtonGroup buttonGroup = new javax.swing.ButtonGroup();
        java.util.Iterator iter = methodSummary.getParameters();
        int count = 0;
        while (iter.hasNext()) {
            org.acm.seguin.summary.ParameterSummary next = ((org.acm.seguin.summary.ParameterSummary) (iter.next()));
            org.acm.seguin.uml.refactor.ParameterRadioButton tcb = new org.acm.seguin.uml.refactor.ParameterRadioButton(next);
            children.add(tcb);
            buttonGroup.add(tcb);
            tcb.setSelected(count == 0);
            count++;
        } 
        int columns = (count / 10) + 1;
        setLayout(new java.awt.GridLayout((count / columns) + 1, columns));
        iter = children.iterator();
        while (iter.hasNext()) {
            add(((javax.swing.JComponent) (iter.next())));
        } 
    }

    /**
     * Gets the selected parameter
     *
     * @return The selected parameter
     */
    public org.acm.seguin.summary.ParameterSummary get() {
        java.util.Iterator iter = children.iterator();
        while (iter.hasNext()) {
            org.acm.seguin.uml.refactor.ParameterRadioButton prb = ((org.acm.seguin.uml.refactor.ParameterRadioButton) (iter.next()));
            if (prb.isSelected()) {
                return prb.getParameterSummary();
            }
        } 
        return null;
    }
}