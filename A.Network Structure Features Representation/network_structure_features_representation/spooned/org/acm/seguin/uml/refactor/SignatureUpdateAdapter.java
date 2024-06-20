/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * This adapter is resposible for keeping the signature in the dialog box
 *  relatively up to date.
 *
 * @author Chris Seguin
 */
class SignatureUpdateAdapter implements javax.swing.event.ListDataListener , java.awt.event.ActionListener , java.awt.event.FocusListener , javax.swing.event.ListSelectionListener , javax.swing.event.DocumentListener {
    private org.acm.seguin.uml.refactor.ExtractMethodDialog emd;

    /**
     * Constructor for the SignatureUpdateAdapter object
     *
     * @param init
     * 		the dialog box it is responsible for
     */
    public SignatureUpdateAdapter(org.acm.seguin.uml.refactor.ExtractMethodDialog init) {
        emd = init;
    }

    /**
     * Description of the Method
     *
     * @param e
     * 		Description of Parameter
     */
    public void intervalAdded(javax.swing.event.ListDataEvent e) {
        emd.update();
    }

    /**
     * Description of the Method
     *
     * @param e
     * 		Description of Parameter
     */
    public void intervalRemoved(javax.swing.event.ListDataEvent e) {
        emd.update();
    }

    /**
     * Description of the Method
     *
     * @param e
     * 		Description of Parameter
     */
    public void contentsChanged(javax.swing.event.ListDataEvent e) {
        emd.update();
    }

    /**
     * Description of the Method
     *
     * @param e
     * 		Description of Parameter
     */
    public void actionPerformed(java.awt.event.ActionEvent e) {
        emd.update();
    }

    /**
     * Description of the Method
     *
     * @param e
     * 		Description of Parameter
     */
    public void focusGained(java.awt.event.FocusEvent e) {
    }

    /**
     * Description of the Method
     *
     * @param e
     * 		Description of Parameter
     */
    public void focusLost(java.awt.event.FocusEvent e) {
        emd.update();
    }

    /**
     * Someone selected something in the list box
     *
     * @param e
     * 		Description of Parameter
     */
    public void valueChanged(javax.swing.event.ListSelectionEvent e) {
        emd.update();
    }

    /**
     * Document listener event
     *
     * @param evt
     * 		Description of Parameter
     */
    public void insertUpdate(javax.swing.event.DocumentEvent evt) {
        emd.update();
    }

    /**
     * Document listener event
     *
     * @param e
     * 		Description of Parameter
     */
    public void removeUpdate(javax.swing.event.DocumentEvent e) {
        emd.update();
    }

    /**
     * Document listener event
     *
     * @param e
     * 		Description of Parameter
     */
    public void changedUpdate(javax.swing.event.DocumentEvent e) {
        emd.update();
    }
}