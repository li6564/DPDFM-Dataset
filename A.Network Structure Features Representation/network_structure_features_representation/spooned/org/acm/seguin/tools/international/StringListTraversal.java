package org.acm.seguin.tools.international;
/**
 * Lists the strings in a set of files
 *
 * @author Chris Seguin
 */
class StringListTraversal extends org.acm.seguin.io.DirectoryTreeTraversal {
    /**
     * Constructor for the StringListTraversal object
     *
     * @param init
     * 		Description of Parameter
     */
    public StringListTraversal(java.lang.String init) {
        super(init);
    }

    /**
     * Gets the Target attribute of the StringListTraversal object
     *
     * @param currentFile
     * 		Description of Parameter
     * @return The Target value
     */
    protected boolean isTarget(java.io.File currentFile) {
        java.lang.String name = currentFile.getName();
        java.lang.String lower = name.toLowerCase();
        return lower.endsWith(".java");
    }

    /**
     * Description of the Method
     *
     * @param currentFile
     * 		Description of Parameter
     */
    protected void visit(java.io.File currentFile) {
        java.lang.System.out.println("File:  " + currentFile.getPath());
        try {
            org.acm.seguin.parser.factory.FileParserFactory fpf = new org.acm.seguin.parser.factory.FileParserFactory(currentFile);
            org.acm.seguin.parser.ast.SimpleNode root = fpf.getAbstractSyntaxTree(false);
            if (root != null) {
                root.jjtAccept(new org.acm.seguin.tools.international.StringListVisitor(), null);
            }
        } catch (java.lang.Throwable thrown) {
            thrown.printStackTrace();
        }
        java.lang.System.out.println(" ");
    }

    /**
     * The main program for the StringListTraversal class
     *
     * @param args
     * 		The command line arguments
     */
    public static void main(java.lang.String[] args) {
        if (args.length == 0) {
            new org.acm.seguin.tools.international.StringListTraversal(java.lang.System.getProperty("user.dir")).go();
        } else {
            new org.acm.seguin.tools.international.StringListTraversal(args[0]).go();
        }
    }
}