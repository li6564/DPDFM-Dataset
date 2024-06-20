/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.print;
/**
 * Handles the print menu option
 *
 * @author Chris Seguin
 * @created August 6, 1999
 */
public class PrintSetupAdapter implements java.awt.event.ActionListener {
    /**
     * The action performed
     *
     * @param ev
     * 		the action event
     */
    public void actionPerformed(java.awt.event.ActionEvent ev) {
        org.acm.seguin.uml.print.UMLPagePrinter.getPageFormat(true);
    }
}