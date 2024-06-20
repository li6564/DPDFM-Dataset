/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * Holds a list of packages for the list box
 *
 * @author Chris Seguin
 * @created August 11, 1999
 */
public class PackageSummaryListModel extends javax.swing.AbstractListModel {
    // Instance Variables
    private java.util.TreeMap orderedMap;

    private org.acm.seguin.summary.PackageSummary[] array;

    private boolean ready;

    /**
     * Constructor for the PackageSummaryListModel object
     */
    public PackageSummaryListModel() {
        orderedMap = new java.util.TreeMap();
        ready = false;
        array = null;
    }

    /**
     * Return the requested item
     *
     * @param index
     * 		the index of the item required
     * @return The object
     */
    public java.lang.Object getElementAt(int index) {
        if (!ready) {
            prepare();
        }
        return array[index];
    }

    /**
     * Return the number of items
     *
     * @return The size
     */
    public int getSize() {
        return orderedMap.size();
    }

    /**
     * Adds a package summary
     *
     * @param summary
     * 		the new summary
     */
    public void add(org.acm.seguin.summary.PackageSummary summary) {
        orderedMap.put(summary.getName(), summary);
        ready = false;
    }

    /**
     * Prepare to handle the incoming requests
     */
    private void prepare() {
        // Check that we have enough array
        if ((array == null) || (array.length < orderedMap.size())) {
            array = new org.acm.seguin.summary.PackageSummary[orderedMap.size() + 5];
        }
        // Load the array
        java.util.Iterator iter = orderedMap.values().iterator();
        int ndx = 0;
        while (iter.hasNext()) {
            array[ndx] = ((org.acm.seguin.summary.PackageSummary) (iter.next()));
            ndx++;
        } 
        // Finish
        ready = true;
    }
}