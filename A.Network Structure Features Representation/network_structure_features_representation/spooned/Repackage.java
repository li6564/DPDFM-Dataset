import org.acm.seguin.refactor.RefactoringException;
/**
 * Main program for repackaging. This object simply stores the main program
 *  and interprets the command line arguments for repackaging one or more
 *  files.
 *
 * @author Chris Seguin
 * @created June 2, 1999
 */
public class Repackage {
    // Instance Variables
    private org.acm.seguin.refactor.type.MoveClass moveClass;

    private boolean setPackage = false;

    private boolean atLeastOneClass = false;

    /**
     * Actual work of the main program occurs here
     *
     * @param args
     * 		the command line arguments
     * @exception RefactoringException
     * 		Description of Exception
     */
    public void run(java.lang.String[] args) throws org.acm.seguin.refactor.RefactoringException {
        moveClass = org.acm.seguin.refactor.RefactoringFactory.get().moveClass();
        if (init(args)) {
            moveClass.run();
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
                moveClass.setDirectory(args[nCurrentArg + 1]);
                nCurrentArg += 2;
            } else if (args[nCurrentArg].equals("-package")) {
                moveClass.setDestinationPackage(args[nCurrentArg + 1]);
                nCurrentArg += 2;
                setPackage = true;
            } else if (args[nCurrentArg].equals("-nopackage")) {
                moveClass.setDestinationPackage("");
                nCurrentArg++;
                setPackage = true;
            } else if (args[nCurrentArg].equals("-file")) {
                java.lang.String filename = args[nCurrentArg + 1];
                load(filename);
                nCurrentArg += 2;
                atLeastOneClass = true;
            } else if (args[nCurrentArg].equals("-help")) {
                printHelpMessage();
                nCurrentArg++;
                return false;
            } else {
                moveClass.add(args[nCurrentArg]);
                nCurrentArg++;
                atLeastOneClass = true;
            }
        } 
        return atLeastOneClass && setPackage;
    }

    /**
     * Print the help message
     */
    protected void printHelpMessage() {
        java.lang.System.out.println("Syntax:  java Repackage \\ ");
        java.lang.System.out.println("        [-dir <dir>] [-help] ");
        java.lang.System.out.println("        [-package <packagename> | -nopackage] (<file.java>)*");
        java.lang.System.out.println("");
        java.lang.System.out.println("  where:");
        java.lang.System.out.println("    <dir>        is the name of the directory containing the files");
        java.lang.System.out.println("    <package>    is the name of the new package");
        java.lang.System.out.println("    <file.java>  is the name of the java file to be moved");
    }

    /**
     * Loads a file listing the names of java files to be moved
     *
     * @param filename
     * 		the name of the file
     */
    private void load(java.lang.String filename) {
        try {
            java.io.BufferedReader input = new java.io.BufferedReader(new java.io.FileReader(filename));
            java.lang.String line = input.readLine();
            while (line != null) {
                java.util.StringTokenizer tok = new java.util.StringTokenizer(line);
                while (tok.hasMoreTokens()) {
                    java.lang.String next = tok.nextToken();
                    java.lang.System.out.println("Adding:  " + next);
                    moveClass.add(next);
                } 
                line = input.readLine();
            } 
            input.close();
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
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
            new Repackage().run(args);
        } catch (java.lang.Throwable thrown) {
            thrown.printStackTrace(java.lang.System.out);
        }
        java.lang.System.exit(0);
    }
}