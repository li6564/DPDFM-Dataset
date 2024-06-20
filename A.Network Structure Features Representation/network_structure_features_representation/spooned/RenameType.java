/**
 * Main program for repackaging. This object simply stores the main program
 *  and interprets the command line arguments for repackaging one or more
 *  files.
 *
 * @author Chris Seguin
 */
public class RenameType {
    // Instance Variables
    private org.acm.seguin.refactor.type.RenameClassRefactoring renameClass;

    /**
     * Actual work of the main program occurs here
     *
     * @param args
     * 		the command line arguments
     */
    public void run(java.lang.String[] args) {
        renameClass = org.acm.seguin.refactor.RefactoringFactory.get().renameClass();
        if (init(args)) {
            try {
                renameClass.run();
            } catch (org.acm.seguin.refactor.RefactoringException re) {
                java.lang.System.out.println(re.getMessage());
            }
        }
    }

    /**
     * Initialize the variables with command line arguments
     *
     * @param args
     * 		the command line arguments
     * @return true if we should continue processing
     */
    public boolean init(java.lang.String[] args) {
        int nCurrentArg = 0;
        while (nCurrentArg < args.length) {
            if (args[nCurrentArg].equals("-dir")) {
                renameClass.setDirectory(args[nCurrentArg + 1]);
                nCurrentArg += 2;
            } else if (args[nCurrentArg].equals("-help")) {
                printHelpMessage();
                nCurrentArg++;
                return false;
            } else if (args[nCurrentArg].equals("-from")) {
                renameClass.setOldClassName(args[nCurrentArg + 1]);
                nCurrentArg += 2;
            } else if (args[nCurrentArg].equals("-to")) {
                renameClass.setNewClassName(args[nCurrentArg + 1]);
                nCurrentArg += 2;
            } else {
                java.lang.System.out.println("Unknown argument:  " + args[nCurrentArg]);
                nCurrentArg++;
            }
        } 
        return true;
    }

    /**
     * Print the help message
     */
    protected void printHelpMessage() {
        java.lang.System.out.println("Syntax:  java RenameType \\ ");
        java.lang.System.out.println("        [-dir <dir>] [-help] ");
        java.lang.System.out.println("        -from <className> -to <className>");
        java.lang.System.out.println("");
        java.lang.System.out.println("  where:");
        java.lang.System.out.println("    <dir>        is the name of the directory containing the files");
        java.lang.System.out.println("    <className>  is the name of the class");
    }

    /**
     * Main program
     *
     * @param args
     * 		the command line arguments
     */
    public static void main(java.lang.String[] args) {
        try {
            // Make sure everything is installed properly
            new org.acm.seguin.tools.install.RefactoryInstaller(true).run();
            new RenameType().run(args);
        } catch (java.lang.Throwable thrown) {
            thrown.printStackTrace();
        }
        java.lang.System.exit(0);
    }
}