/**
 * Draws a UML diagram for all the classes in a package
 *
 * @author Chris Seguin
 */
public class Refactory {
    /**
     * The main program
     *
     * @param args
     * 		the command line arguments
     */
    public static void main(java.lang.String[] args) {
        // Make sure everything is installed properly
        new org.acm.seguin.tools.install.RefactoryInstaller(true).run();
        org.acm.seguin.ide.common.SourceBrowser.set(new org.acm.seguin.ide.command.CommandLineSourceBrowser());
        if (args.length == 0) {
            Refactory.elixir();
        } else {
            Refactory.selectionPanel(args[0]);
        }
    }

    /**
     * Creates the selection panel
     *
     * @param directory
     * 		Description of Parameter
     */
    public static void selectionPanel(java.lang.String directory) {
        org.acm.seguin.ide.command.PackageSelectorPanel panel = org.acm.seguin.ide.command.PackageSelectorPanel.getMainPanel(directory);
        org.acm.seguin.uml.loader.ReloaderSingleton.register(panel);
    }

    /**
     * Insertion point for elixir
     */
    public static void elixir() {
        if (org.acm.seguin.ide.command.PackageSelectorPanel.getMainPanel(null) != null) {
            return;
        }
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        // Add other file filters - All
        org.acm.seguin.io.AllFileFilter allFilter = new org.acm.seguin.io.AllFileFilter();
        chooser.addChoosableFileFilter(allFilter);
        // Set it so that files and directories can be selected
        chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        // Set the directory to the current directory
        chooser.setCurrentDirectory(new java.io.File(java.lang.System.getProperty("user.dir")));
        // Get the user's selection
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            Refactory.selectionPanel(chooser.getSelectedFile().getAbsolutePath());
        }
    }
}