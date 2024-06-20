/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common;
/**
 * Loads all the class settings based on a particular directory
 *
 * @author Chris Seguin
 */
public class SummaryLoaderThread extends java.lang.Thread {
    private java.lang.String base;

    private static int count = 0;

    /**
     * Constructor for the SummaryLoaderThread object
     *
     * @param init
     * 		The root directory to load
     */
    public SummaryLoaderThread(java.lang.String init) {
        base = init;
    }

    /**
     * Main processing method for the SummaryLoaderThread object
     */
    public void run() {
        synchronized(org.acm.seguin.ide.common.SummaryLoaderThread.class) {
            java.util.StringTokenizer tok = new java.util.StringTokenizer(base, java.io.File.pathSeparator);
            while (tok.hasMoreTokens()) {
                java.lang.String next = tok.nextToken();
                new org.acm.seguin.summary.SummaryTraversal(next).go();
            } 
            java.lang.System.out.println("Completed loading the metadata");
            org.acm.seguin.ide.common.SummaryLoaderThread.count = 0;
        }
    }

    /**
     * This just confirms that you have loaded the summaries
     *  into memory.
     */
    public static synchronized void waitForLoading() {
        org.acm.seguin.ide.common.SummaryLoaderThread.count++;
    }
}