/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.tools.stub;
/**
 * Asks the user where to start loading the JDK source code files
 *
 * @author Chris Seguin
 */
public class StubPrompter extends javax.swing.JDialog implements java.awt.event.ActionListener {
    private javax.swing.JTextArea filename;

    private java.io.File result;

    /**
     * Constructor for the StubPrompter object
     *
     * @param frame
     * 		Description of Parameter
     * @param output
     * 		Description of Parameter
     */
    public StubPrompter(javax.swing.JFrame frame, java.io.File output) {
        super(frame, "JDK Summary Generator", true);
        result = output;
        setSize(300, 205);
        getContentPane().setLayout(null);
        javax.swing.JLabel instructions1 = new javax.swing.JLabel("To effectively use this tool it is necessary to have");
        instructions1.setLocation(5, 5);
        instructions1.setSize(instructions1.getPreferredSize());
        getContentPane().add(instructions1);
        int height = instructions1.getPreferredSize().height;
        javax.swing.JLabel instructions2 = new javax.swing.JLabel("some overview of the Java Development Kit's");
        instructions2.setLocation(5, 5 + height);
        instructions2.setSize(instructions2.getPreferredSize());
        getContentPane().add(instructions2);
        javax.swing.JLabel instructions3 = new javax.swing.JLabel("source code.");
        instructions3.setLocation(5, 5 + (2 * height));
        instructions3.setSize(instructions3.getPreferredSize());
        getContentPane().add(instructions3);
        javax.swing.JLabel instructions4 = new javax.swing.JLabel("Please enter the jar or zip file that contains the");
        instructions4.setLocation(5, 15 + (3 * height));
        instructions4.setSize(instructions4.getPreferredSize());
        getContentPane().add(instructions4);
        javax.swing.JLabel instructions5 = new javax.swing.JLabel("source.  It is usually called src.jar.");
        instructions5.setLocation(5, 15 + (4 * height));
        instructions5.setSize(instructions5.getPreferredSize());
        getContentPane().add(instructions5);
        filename = new javax.swing.JTextArea();
        filename.setLocation(5, 15 + (6 * height));
        filename.setSize(190, 25);
        getContentPane().add(filename);
        javax.swing.JButton browse = new javax.swing.JButton("Browse");
        browse.setLocation(200, 15 + (6 * height));
        browse.setSize(85, 25);
        getContentPane().add(browse);
        browse.addActionListener(this);
        javax.swing.JButton okButton = new javax.swing.JButton("OK");
        okButton.setLocation(5, 45 + (6 * height));
        okButton.setSize(85, 25);
        getContentPane().add(okButton);
        okButton.addActionListener(this);
        org.acm.seguin.awt.CenterDialog.center(this, frame);
    }

    /**
     * The user has pressed a button. Handle the action appropriately.
     *
     * @param evt
     * 		A description of the action
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getActionCommand().equals("OK")) {
            // System.out.println("OK button:  " + filename.getText());
            java.lang.String name = filename.getText();
            java.io.File file = new java.io.File(name);
            if (file.exists()) {
                dispose();
                new org.acm.seguin.tools.stub.StubGenerator(name, result).run();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "The file you entered does not exist.\nPlease select the source code for the JDK.", "File does not exist", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } else if (evt.getActionCommand().equals("Browse")) {
            // System.out.println("Browse button");
            javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
            org.acm.seguin.io.ExtensionFileFilter zipFilter = new org.acm.seguin.io.ExtensionFileFilter();
            zipFilter.addExtension(".zip");
            zipFilter.setDescription("Zip files");
            chooser.addChoosableFileFilter(zipFilter);
            org.acm.seguin.io.ExtensionFileFilter jarFilter = new org.acm.seguin.io.ExtensionFileFilter();
            jarFilter.addExtension(".jar");
            jarFilter.setDescription("Jar files");
            chooser.setFileFilter(jarFilter);
            chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);
            int result = chooser.showOpenDialog(this);
            if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                java.io.File selected = chooser.getSelectedFile();
                java.lang.String path = null;
                try {
                    path = selected.getCanonicalPath();
                } catch (java.io.IOException ioe) {
                    path = selected.getPath();
                }
                filename.setText(path);
            }
        }
    }

    /**
     * The main program for the StubPrompter class
     *
     * @param args
     * 		The command line arguments
     */
    public static void main(java.lang.String[] args) {
        new org.acm.seguin.tools.stub.StubPrompter(null, new java.io.File("c:\\temp\\test.stub")).setVisible(true);
    }
}