/**
 * Tool that uses the pretty printer to number lines
 *
 * @author Chris Seguin
 * @created May 11, 1999
 */
public class LineNumberTool {
    // Instance Variables
    private java.util.ArrayList inputList;

    private java.lang.String dest;

    private java.io.OutputStream out;

    /**
     * Constructor for the line numbering
     */
    public LineNumberTool() {
        inputList = new java.util.ArrayList();
        dest = null;
        out = null;
    }

    /**
     * Read command line inputs
     *
     * @param args
     * 		Description of Parameter
     */
    protected void init(java.lang.String[] args) {
        int last = args.length;
        for (int ndx = 0; ndx < last; ndx++) {
            if (args[ndx].equals("-help")) {
                java.lang.System.out.println("Pretty Printer Version 1.0.  Has the following inputs");
                java.lang.System.out.println("         java LineNumberTool [-out filename] (inputfile)*");
                java.lang.System.out.println("OR");
                java.lang.System.out.println("         java LineNumberTool [-out filename] < inputfile");
                java.lang.System.out.println("where");
                java.lang.System.out.println("         -out filename     Output to the file or directory");
            } else if (args[ndx].equals("-out")) {
                ndx++;
                dest = args[ndx];
            } else {
                // Add another input to the list
                inputList.add(args[ndx]);
            }
        }
    }

    /**
     * Run the pretty printer
     */
    protected void run() {
        // Local Variables
        int last = inputList.size();
        // Create the visitor
        org.acm.seguin.pretty.PrettyPrintVisitor printer = new org.acm.seguin.pretty.PrettyPrintVisitor();
        // Create the appropriate print data
        org.acm.seguin.pretty.PrintData data = null;
        for (int index = 0; (index < last) || (index == 0); index++) {
            data = getPrintData(index, data);
            // Create the parser and visit the parse tree
            printer.visit(getRoot(index), data);
            // Flush the output stream
            data.flush();
        }
        data.close();
    }

    /**
     * Create the output stream
     *
     * @param index
     * 		the index of the output stream
     * @param filename
     * 		the name of the file
     * @return the output stream
     */
    private java.io.OutputStream getOutputStream(int index, java.lang.String filename) {
        // Local Variables
        java.io.OutputStream out = null;
        // Check the destination
        if (dest == null) {
            out = java.lang.System.out;
        } else {
            try {
                out = new java.io.FileOutputStream(dest);
            } catch (java.io.IOException ioe) {
                // Hmmm...  Can't create the output stream, then fall back to stdout
                out = java.lang.System.out;
            }
        }
        // Return the output stream
        return out;
    }

    /**
     * Return the appropriate print data
     *
     * @param oldPrintData
     * 		the old print data
     * @param index
     * 		Description of Parameter
     * @return the print data
     */
    private org.acm.seguin.pretty.PrintData getPrintData(int index, org.acm.seguin.pretty.PrintData oldPrintData) {
        if (oldPrintData == null) {
            out = getOutputStream(index, null);
            return new org.acm.seguin.pretty.line.LineNumberingData(out);
        } else {
            oldPrintData.flush();
            try {
                out.write(12);
            } catch (java.io.IOException ioe) {
            }
            return oldPrintData;
        }
    }

    /**
     * Create the parser
     *
     * @param index
     * 		the index
     * @return the java parser
     */
    private org.acm.seguin.parser.ast.SimpleNode getRoot(int index) {
        java.io.File in;
        org.acm.seguin.parser.factory.ParserFactory factory;
        if (inputList.size() > index) {
            in = new java.io.File(((java.lang.String) (inputList.get(index))));
            factory = new org.acm.seguin.parser.factory.FileParserFactory(in);
        } else {
            factory = new org.acm.seguin.parser.factory.StdInParserFactory();
        }
        // Create the parse tree
        return factory.getAbstractSyntaxTree(true);
    }

    /**
     * Main program
     *
     * @param args
     * 		the command line arguments
     */
    public static void main(java.lang.String[] args) {
        // Make sure everything is installed properly
        new org.acm.seguin.tools.install.RefactoryInstaller(false).run();
        try {
            LineNumberTool pp = new LineNumberTool();
            pp.init(args);
            pp.run();
        } catch (java.lang.Throwable t) {
            if (t == null) {
                java.lang.System.out.println("We have caught a null throwable");
            } else {
                java.lang.System.out.println("t is a " + t.getClass().getName());
                java.lang.System.out.println("t has a message " + t.getMessage());
                t.printStackTrace(java.lang.System.out);
            }
        }
    }
}