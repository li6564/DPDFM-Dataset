/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.version;
/**
 * User directed version control
 *
 * @author Chris Seguin
 */
public class UserDirectedVersionControl implements org.acm.seguin.version.VersionControl {
    /**
     * Determines if a file is under version control
     *
     * @param fullFilename
     * 		The full path of the file
     * @return Returns true if the files is under version control
     */
    public boolean contains(java.lang.String fullFilename) {
        return javax.swing.JOptionPane.YES_OPTION == javax.swing.JOptionPane.showConfirmDialog(null, ("Does your source control system contain\n" + fullFilename) + "?", "Contains", javax.swing.JOptionPane.YES_NO_OPTION);
    }

    /**
     * Adds a file to version control
     *
     * @param fullFilename
     * 		the file to add
     */
    public void add(java.lang.String fullFilename) {
        javax.swing.JOptionPane.showMessageDialog(null, ("Please add\n" + fullFilename) + "\nfrom your version control system", "Add", javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Checks in a file
     *
     * @param fullFilename
     * 		the file to check in
     */
    public void checkIn(java.lang.String fullFilename) {
        javax.swing.JOptionPane.showMessageDialog(null, ("Please check in\n" + fullFilename) + "\nto your version control system", "Check in", javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Check out a file
     *
     * @param fullFilename
     * 		the file to check out
     */
    public void checkOut(java.lang.String fullFilename) {
        javax.swing.JOptionPane.showMessageDialog(null, ("Please check out\n" + fullFilename) + "\nfrom your version control system", "Check out", javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
}