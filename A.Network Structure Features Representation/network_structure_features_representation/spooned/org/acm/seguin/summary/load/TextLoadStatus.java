/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary.load;
/**
 * Reports to the user the status of the loading using stdout
 *
 * @author Chris Seguin
 */
public class TextLoadStatus implements org.acm.seguin.summary.load.LoadStatus {
    private boolean inOldRoot = false;

    private char ch = '.';

    /**
     * Sets the Root attribute of the LoadStatus object
     *
     * @param name
     * 		The new Root value
     */
    public void setRoot(java.lang.String name) {
        if (inOldRoot) {
            java.lang.System.out.println(" ");
        }
        if (name.endsWith(".stub")) {
            name = name.substring(0, name.length() - 5);
            ch = '#';
        } else {
            ch = '.';
        }
        java.lang.System.out.println("Loading all the classes in " + name);
    }

    /**
     * Sets the CurrentFile attribute of the LoadStatus object
     *
     * @param name
     * 		The new CurrentFile value
     */
    public void setCurrentFile(java.lang.String name) {
        java.lang.System.out.print(ch);
        inOldRoot = true;
    }

    /**
     * Completed the loading
     */
    public void done() {
        java.lang.System.out.println(" ");
    }
}