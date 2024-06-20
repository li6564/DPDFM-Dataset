/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary.load;
/**
 * Reports to the user the status of the loading
 *
 * @author Chris Seguin
 */
public interface LoadStatus {
    /**
     * Sets the Root attribute of the LoadStatus object
     *
     * @param name
     * 		The new Root value
     */
    public void setRoot(java.lang.String name);

    /**
     * Sets the CurrentFile attribute of the LoadStatus object
     *
     * @param name
     * 		The new CurrentFile value
     */
    public void setCurrentFile(java.lang.String name);

    /**
     * Completed the loading
     */
    public void done();
}