/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.ide.common;
/**
 * Loads the class diagrams based on a single directory
 *
 * @author Chris Seguin
 */
public class MultipleDirClassDiagramReloader extends org.acm.seguin.ide.common.ClassDiagramReloader {
    private java.util.LinkedList list;

    private boolean necessary;

    /**
     * Constructor for the MultipleDirClassDiagramReloader object
     */
    public MultipleDirClassDiagramReloader() {
        super();
        list = new java.util.LinkedList();
        necessary = false;
    }

    /**
     * Sets the Necessary attribute of the MultipleDirClassDiagramReloader
     *  object
     *
     * @param value
     * 		The new Necessary value
     */
    public void setNecessary(boolean value) {
        necessary = value;
    }

    /**
     * Gets the Necessary attribute of the MultipleDirClassDiagramReloader
     *  object
     *
     * @return The Necessary value
     */
    public boolean isNecessary() {
        return necessary;
    }

    /**
     * Clears all directories in the list
     */
    public void clear() {
        list.clear();
    }

    /**
     * Sets the directory to load the data from
     *
     * @param value
     * 		the directory
     */
    public void addRootDirectory(java.lang.String value) {
        if (!list.contains(value)) {
            list.add(value);
        }
    }

    /**
     * Reload the summary information and update the diagrams
     */
    public void reload() {
        if (!necessary) {
            return;
        }
        // Build a list of directories to load
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        java.util.Iterator iter = list.iterator();
        while (iter.hasNext()) {
            java.lang.String base = ((java.lang.String) (iter.next()));
            buffer.append(base);
            if (iter.hasNext()) {
                buffer.append(java.io.File.pathSeparator);
            }
        } 
        // Load those directories
        java.lang.String baseDirs = buffer.toString();
        if (baseDirs.length() > 0) {
            new org.acm.seguin.ide.common.SummaryLoaderThread(baseDirs).start();
        }
        // Load the diagrams
        reloadDiagrams();
    }
}