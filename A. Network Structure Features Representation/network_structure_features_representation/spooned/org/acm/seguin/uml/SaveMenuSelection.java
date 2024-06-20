/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * Saves this panel
 *
 * @author Chris Seguin
 * @created August 13, 1999
 */
public class SaveMenuSelection implements java.awt.event.ActionListener {
    org.acm.seguin.io.Saveable panel;

    /**
     * Constructor for the SaveMenuSelection object
     *
     * @param init
     * 		Description of Parameter
     */
    public SaveMenuSelection(org.acm.seguin.io.Saveable init) {
        panel = init;
    }

    /**
     * Saves this panel
     *
     * @param evt
     * 		The triggering event
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        try {
            panel.save();
        } catch (java.io.IOException ioe) {
            org.acm.seguin.awt.ExceptionPrinter.print(ioe);
        }
    }
}