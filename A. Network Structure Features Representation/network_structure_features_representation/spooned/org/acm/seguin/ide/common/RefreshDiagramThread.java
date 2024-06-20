/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 */
class RefreshDiagramThread extends java.lang.Thread {
    /**
     * Constructor for the RefreshDiagramThread object
     *
     * @param init
     * 		Description of Parameter
     */
    /**
     * Main processing method for the RefreshDiagramThread object
     */
    public void run() {
        org.acm.seguin.ide.common.SummaryLoaderThread.waitForLoading();
    }

    private RefreshDiagramThread(java.util.Enumeration init) {
    }
}