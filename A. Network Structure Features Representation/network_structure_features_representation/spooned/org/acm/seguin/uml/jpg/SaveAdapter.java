package org.acm.seguin.uml.jpg;
/**
 * Object that handles a mouse event on the save operation.
 *
 * @author Chris Seguin
 */
public class SaveAdapter implements java.awt.event.ActionListener {
    private org.acm.seguin.uml.UMLPackage diagram;

    private static java.io.File directory = null;

    /**
     * Constructor for the SaveAdapter object
     *
     * @param packageDiagram
     * 		Description of Parameter
     */
    public SaveAdapter(org.acm.seguin.uml.UMLPackage packageDiagram) {
        diagram = packageDiagram;
    }

    /**
     * Performs the action
     *
     * @param evt
     * 		Description of Parameter
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        java.lang.String filename = getFilename();
        if (filename == null) {
            return;
        }
        new org.acm.seguin.uml.jpg.Save(filename, diagram).run();
    }

    /**
     * Gets the Filename to save the file as
     *
     * @return The Filename value
     */
    private java.lang.String getFilename() {
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        // Create the java file filter
        org.acm.seguin.io.ExtensionFileFilter filter = new org.acm.seguin.io.ExtensionFileFilter();
        filter.addExtension(".jpg");
        filter.setDescription("JPG Image Files (.jpg)");
        chooser.setFileFilter(filter);
        // Set it so that files only can be selected and it is a save box
        chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        chooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        // Set the directory to the current directory
        if (org.acm.seguin.uml.jpg.SaveAdapter.directory == null) {
            org.acm.seguin.uml.jpg.SaveAdapter.directory = new java.io.File(java.lang.System.getProperty("user.dir"));
        }
        chooser.setCurrentDirectory(org.acm.seguin.uml.jpg.SaveAdapter.directory);
        // Get the user's selection
        int returnVal = chooser.showSaveDialog(null);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = chooser.getSelectedFile();
            org.acm.seguin.uml.jpg.SaveAdapter.directory = selectedFile.getParentFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }
}