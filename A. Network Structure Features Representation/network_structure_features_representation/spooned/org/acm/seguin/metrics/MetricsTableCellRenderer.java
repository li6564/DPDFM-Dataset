/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.metrics;
/**
 * Metrics table cell renderer
 *
 * @author Chris Seguin
 * @created July 28, 1999
 */
class MetricsTableCellRenderer implements javax.swing.table.TableCellRenderer {
    java.awt.Font headerFont;

    java.awt.Font normalFont;

    /**
     * Constructor for the MetricsTableCellRenderer object
     */
    MetricsTableCellRenderer() {
        headerFont = new java.awt.Font("Serif", java.awt.Font.BOLD, 14);
        normalFont = new java.awt.Font("Serif", java.awt.Font.PLAIN, 14);
    }

    /**
     * Description of the Method
     *
     * @param table
     * 		Description of Parameter
     * @param value
     * 		Description of Parameter
     * @param isSelected
     * 		Description of Parameter
     * @param hasFocus
     * 		Description of Parameter
     * @param row
     * 		Description of Parameter
     * @param column
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        javax.swing.JLabel label = new javax.swing.JLabel(((java.lang.String) (value)));
        label.setBorder(new javax.swing.border.EmptyBorder(4, 10, 4, 10));
        if (row == 0) {
            label.setFont(headerFont);
        } else {
            label.setFont(normalFont);
        }
        if (row == 0) {
            label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        } else if (column == 1) {
            label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        } else {
            label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        }
        return label;
    }
}