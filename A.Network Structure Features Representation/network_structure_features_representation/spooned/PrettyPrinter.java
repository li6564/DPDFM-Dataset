/**
 * Traverses a directory structure and performs all refactorings on the
 *  files.
 *
 * @author Chris Seguin
 * @created May 12, 1999
 */
public class PrettyPrinter extends org.acm.seguin.io.DirectoryTreeTraversal {
    // Instance Variables
    private org.acm.seguin.pretty.PrettyPrintFile ppf;

    /**
     * The main program
     *
     * @param args
     * 		Description of Parameter
     */
    public static void main(java.lang.String[] args) {
        try {
            // Make sure everything is installed properly
            new org.acm.seguin.tools.install.RefactoryInstaller(false).run();
            int lastOption = -1;
            boolean quiet = false;
            for (int ndx = 0; ndx < args.length; ndx++) {
                if (args[ndx].equals("-quiet") || args[ndx].equals("-u")) {
                    quiet = true;
                    lastOption = ndx;
                } else if ((args[ndx].equals("-?") || args[ndx].equalsIgnoreCase("-h")) || args[ndx].equalsIgnoreCase("-help")) {
                    PrettyPrinter.printHelpMessage();
                    return;
                }
            }
            if ((lastOption + 1) >= args.length) {
                // no more arguments left
                if (quiet) {
                    PrettyPrinter.prettyPrinter(quiet);
                } else {
                    PrettyPrinter.prettyPrinter(java.lang.System.getProperty("user.dir"), quiet);
                }
            } else {
                // process remaining arguments as file / dir names
                for (int ndx = lastOption + 1; ndx < args.length; ++ndx) {
                    PrettyPrinter.prettyPrinter(args[ndx], quiet);
                }
            }
        } catch (java.lang.Throwable thrown) {
            thrown.printStackTrace(java.lang.System.out);
        }
        // Exit
        java.lang.System.exit(1);
    }

    /**
     * Print a help message
     */
    private static void printHelpMessage() {
        java.lang.System.out.println("Syntax:  java PrettyPrinter file   //  means refactor this file");
        java.lang.System.out.println("   OR    java PrettyPrinter [-quiet|-u] dir   //  means refactor this directory");
        java.lang.System.out.println("   OR    java PrettyPrinter [-quiet|-u]   //  means refactor the current directory");
        java.lang.System.out.println("  the -quiet or the -u flag tells the pretty printer not to prompt the user");
    }

    /**
     * Refactor the current file
     *
     * @param filename
     * 		Description of Parameter
     */
    public static void prettyPrinter(java.lang.String filename, boolean quiet) {
        new PrettyPrinter(filename, quiet).go();
    }

    /**
     * Refactor the current file
     */
    public static void prettyPrinter(boolean quiet) {
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        // Create the java file filter
        org.acm.seguin.io.ExtensionFileFilter filter = new org.acm.seguin.io.ExtensionFileFilter();
        filter.addExtension(".java");
        filter.setDescription("Java Source Files (.java)");
        chooser.setFileFilter(filter);
        // Add other file filters - All
        javax.swing.filechooser.FileFilter allFilter = new org.acm.seguin.io.AllFileFilter();
        chooser.addChoosableFileFilter(allFilter);
        // Set it so that files and directories can be selected
        chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);
        // Set the directory to the current directory
        chooser.setCurrentDirectory(new java.io.File(java.lang.System.getProperty("user.dir")));
        // Get the user's selection
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            new PrettyPrinter(chooser.getSelectedFile().getAbsolutePath(), quiet).go();
        }
    }

    /**
     * Creates a refactory
     *
     * @param init
     * 		the initial directory or file
     */
    public PrettyPrinter(java.lang.String init, boolean quiet) {
        super(init);
        if (init == null) {
            return;
        }
        ppf = new org.acm.seguin.pretty.PrettyPrintFile();
        ppf.setAsk((!quiet) && new java.io.File(init).isDirectory());
    }

    /**
     * Determines if this file should be handled by this traversal
     *
     * @param currentFile
     * 		the current file
     * @return true if the file should be handled
     */
    protected boolean isTarget(java.io.File currentFile) {
        return currentFile.getName().endsWith(".java");
    }

    /**
     * Visits the current file
     *
     * @param currentFile
     * 		the current file
     */
    protected void visit(java.io.File currentFile) {
        if (ppf.isApplicable(currentFile)) {
            java.lang.System.out.println("Applying the Pretty Printer:  " + currentFile.getPath());
            ppf.apply(currentFile);
        }
    }
}