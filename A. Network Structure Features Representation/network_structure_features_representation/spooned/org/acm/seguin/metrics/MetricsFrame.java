/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.metrics;
/**
 * Base class for metrics frame
 *
 * @author Chris Seguin
 * @created July 26, 1999
 */
public abstract class MetricsFrame extends javax.swing.table.AbstractTableModel {
    /* <Instance Variables> */
    /**
     * Description of the Field
     */
    protected java.lang.String[] descriptions = null;

    /**
     * Description of the Field
     */
    protected java.lang.String[] values = null;

    /**
     * Get the number of rows in the table
     *
     * @return The number of rows
     */
    public int getRowCount() {
        return descriptions.length;
    }

    /**
     * Get the number of columns in the table
     *
     * @return the number of columns
     */
    public int getColumnCount() {
        return 2;
    }

    /**
     * Get the value at a particular spot in the table
     *
     * @param row
     * 		The row index
     * @param column
     * 		The column index
     * @return The value at that location
     */
    public java.lang.Object getValueAt(int row, int column) {
        if (column == 0) {
            return descriptions[row];
        } else {
            return values[row];
        }
    }

    /**
     * Returns the title of this frame
     *
     * @return The Title value
     */
    protected abstract java.lang.String getTitle();

    /* </Instance Variables> */
    /**
     * Create the frame
     */
    protected void createFrame() {
        javax.swing.JTable table = new javax.swing.JTable(this);
        table.setDefaultRenderer(table.getColumnClass(0), new org.acm.seguin.metrics.MetricsTableCellRenderer());
        javax.swing.JFrame frame = new javax.swing.JFrame(getTitle());
        frame.getContentPane().add(table);
        java.awt.Dimension minimum = table.getPreferredSize();
        frame.setSize(java.lang.Math.max(minimum.width, 300), minimum.height + 30);
        frame.setVisible(true);
    }
}