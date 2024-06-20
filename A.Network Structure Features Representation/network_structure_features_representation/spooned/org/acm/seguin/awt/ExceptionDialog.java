/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.awt;
/**
 * The exception dialog box. Displays the message in one scroll pane and the
 *  stack trace in another.
 *
 * @author Chris Seguin
 */
public class ExceptionDialog extends javax.swing.JDialog implements java.awt.event.ActionListener {
    /**
     * Constructor for the ExceptionDialog object
     *
     * @param thrown
     * 		Description of Parameter
     */
    public ExceptionDialog(java.lang.Throwable thrown) {
        this(thrown, null, true);
    }

    /**
     * Constructor for the ExceptionDialog object
     *
     * @param thrown
     * 		Description of Parameter
     * @param parent
     * 		Description of Parameter
     */
    public ExceptionDialog(java.lang.Throwable thrown, java.awt.Frame parent) {
        this(thrown, parent, true);
    }

    /**
     * Constructor for the ExceptionDialog object
     *
     * @param thrown
     * 		Description of Parameter
     * @param parent
     * 		Description of Parameter
     * @param modal
     * 		Description of Parameter
     */
    public ExceptionDialog(java.lang.Throwable thrown, java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Exception thrown");
        setResizable(false);
        getContentPane().setLayout(null);
        javax.swing.JLabel sizer = new javax.swing.JLabel("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        java.awt.Dimension size = sizer.getPreferredSize();
        int scrollHeight = 6 * size.height;
        java.awt.Dimension scrollSize = new java.awt.Dimension(size.width, scrollHeight);
        javax.swing.JScrollPane messagePane = new javax.swing.JScrollPane(createPanel(untab(thrown.getMessage())));
        messagePane.setPreferredSize(scrollSize);
        messagePane.setSize(scrollSize);
        messagePane.setLocation(10, 10);
        getContentPane().add(messagePane);
        javax.swing.JScrollPane stackPane = new javax.swing.JScrollPane(createStackTrace(thrown));
        stackPane.setPreferredSize(scrollSize);
        stackPane.setSize(scrollSize);
        stackPane.setLocation(10, 20 + scrollHeight);
        getContentPane().add(stackPane);
        javax.swing.JButton ok = new javax.swing.JButton("OK");
        java.awt.Dimension okSize = ok.getPreferredSize();
        ok.setSize(okSize);
        ok.setLocation(((20 + size.width) - okSize.width) / 2, 30 + (scrollHeight * 2));
        getContentPane().add(ok);
        ok.addActionListener(this);
        int wide = 20 + size.width;
        int high = 90 + (scrollHeight * 2);
        setSize(wide, high);
        org.acm.seguin.awt.CenterDialog.center(this);
    }

    /**
     * The user has pressed the OK button
     *
     * @param evt
     * 		the OK button event
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    /**
     * Creates a panel based on a string
     *
     * @param value
     * 		the string
     * @return the panel
     */
    private javax.swing.JPanel createPanel(java.lang.String value) {
        java.util.LinkedList list = new java.util.LinkedList();
        java.util.StringTokenizer tok = new java.util.StringTokenizer(value, "\n\r");
        while (tok.hasMoreTokens()) {
            list.add(tok.nextToken());
        } 
        javax.swing.JPanel result = new javax.swing.JPanel();
        result.setLayout(new java.awt.GridLayout(list.size(), 1));
        java.util.Iterator iter = list.iterator();
        while (iter.hasNext()) {
            javax.swing.JLabel label = new javax.swing.JLabel(((java.lang.String) (iter.next())));
            result.add(label);
        } 
        return result;
    }

    /**
     * Creates a panel containing the stack trace of the exception
     *
     * @param thrown
     * 		the exception
     * @return the panel
     */
    private javax.swing.JPanel createStackTrace(java.lang.Throwable thrown) {
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        java.io.PrintWriter output = new java.io.PrintWriter(stringWriter);
        thrown.printStackTrace(output);
        output.close();
        return createPanel(untab(stringWriter.toString()));
    }

    /**
     * Description of the Method
     *
     * @param value
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private java.lang.String untab(java.lang.String value) {
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        int last = value.length();
        for (int ndx = 0; ndx < last; ndx++) {
            if (value.charAt(ndx) == '\t') {
                buffer.append("        ");
            } else {
                buffer.append(value.charAt(ndx));
            }
        }
        return buffer.toString();
    }

    /**
     * The main program for the ExceptionDialog class. This program is used to
     *  test this dialog box.
     *
     * @param args
     * 		The command line arguments
     */
    public static void main(java.lang.String[] args) {
        new org.acm.seguin.awt.ExceptionDialog(new java.lang.Exception("Here is\na six line\nerror\nmesage\nfor you to\nread.\n \nDo you enjoy?")).setVisible(true);
    }
}