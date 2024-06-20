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
public class PrintAdapter implements java.awt.event.ActionListener {
    private org.acm.seguin.uml.UMLPackage currentPackage;

    /**
     * Constructor for the PrintAdapter object
     *
     * @param panel
     * 		the current package
     */
    public PrintAdapter(org.acm.seguin.uml.UMLPackage panel) {
        currentPackage = panel;
    }

    /**
     * The action performed
     *
     * @param ev
     * 		the action event
     */
    public void actionPerformed(java.awt.event.ActionEvent ev) {
        java.lang.Thread pt = new org.acm.seguin.uml.print.PrintingThread(currentPackage);
        pt.start();
    }
}