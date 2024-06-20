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
public class SwingLoadStatus extends javax.swing.JDialog implements org.acm.seguin.summary.load.LoadStatus {
    private javax.swing.JLabel label;

    private javax.swing.JProgressBar progress;

    private int count;

    private int max;

    private int fivePercent;

    private java.lang.String oldName;

    private org.acm.seguin.tools.install.RefactoryStorage lengths;

    /**
     * Constructor for the SwingLoadStatus object
     */
    public SwingLoadStatus() {
        super(new javax.swing.JFrame(), "Loading source files", false);
        getContentPane().setLayout(new java.awt.GridLayout(2, 1));
        label = new javax.swing.JLabel("Loading:  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        java.awt.Dimension size = label.getPreferredSize();
        label.setSize(size);
        getContentPane().add(label);
        progress = new javax.swing.JProgressBar();
        progress.setSize(size);
        getContentPane().add(progress);
        // setSize(230, 70);
        pack();
        setVisible(true);
        oldName = null;
        lengths = new org.acm.seguin.tools.install.RefactoryStorage();
    }

    /**
     * Sets the Root attribute of the LoadStatus object
     *
     * @param name
     * 		The new Root value
     */
    public void setRoot(java.lang.String name) {
        if (oldName != null) {
            lengths.addKey(oldName + ".count", count);
        }
        if (name.endsWith(".stub")) {
            name = name.substring(0, name.length() - 5);
            progress.setForeground(java.awt.Color.red);
        } else {
            progress.setForeground(java.awt.Color.blue);
        }
        label.setText("Loading:  " + name);
        label.setSize(label.getPreferredSize());
        count = 0;
        progress.setValue(count);
        max = lengths.getValue(name + ".count");
        progress.setMaximum(max);
        fivePercent = max / 20;
        oldName = name;
    }

    /**
     * Sets the CurrentFile attribute of the LoadStatus object
     *
     * @param name
     * 		The new CurrentFile value
     */
    public void setCurrentFile(java.lang.String name) {
        count++;
        if (fivePercent < 1) {
            progress.setValue(count);
        } else if ((count % fivePercent) == 0) {
            progress.setValue(count);
        }
    }

    /**
     * Completed the loading
     */
    public void done() {
        dispose();
        if (oldName != null) {
            lengths.addKey(oldName + ".count", count);
        }
        lengths.store();
    }
}