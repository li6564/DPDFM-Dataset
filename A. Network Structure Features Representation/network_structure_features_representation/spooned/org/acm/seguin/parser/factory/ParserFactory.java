package org.acm.seguin.parser.factory;
/**
 * Generates new parsers for a java file
 *
 * @author Chris Seguin
 * @created June 6, 1999
 */
public abstract class ParserFactory {
    // Instance Variables
    private org.acm.seguin.parser.ast.SimpleNode root = null;

    // Class Variables
    private static org.acm.seguin.parser.JavaParser parser = null;

    /**
     * Return the AST
     *
     * @param interactive
     * 		do we want to receive a response in the form of
     * 		a dialog box when a parse exception is encountered
     * @return the simple node which represents the root
     */
    public org.acm.seguin.parser.ast.SimpleNode getAbstractSyntaxTree(boolean interactive) {
        // Check to see if it is here yet
        if (root == null) {
            synchronized(org.acm.seguin.parser.factory.ParserFactory.class) {
                // Look it up
                org.acm.seguin.parser.JavaParser parser = getParser();
                if (parser == null) {
                    return null;
                }
                // Get the parse tree
                try {
                    root = parser.CompilationUnit();
                } catch (org.acm.seguin.parser.ParseException pe) {
                    org.acm.seguin.awt.ExceptionPrinter.print(pe);
                    java.lang.System.out.println("ParserFactory Version 0.1:  Encountered errors during parse:  " + getKey());
                    if (interactive) {
                        org.acm.seguin.awt.ExceptionPrinter.dialog(pe);
                    }
                    return null;
                } catch (java.util.EmptyStackException ese) {
                    org.acm.seguin.awt.ExceptionPrinter.print(ese);
                    java.lang.System.out.println("ParserFactory Version 0.1:  Encountered errors during parse:  " + getKey());
                    root = null;
                }
            }
        }
        // Return the tree
        return root;
    }

    /**
     * Return the input stream
     *
     * @return the input stream
     */
    protected abstract java.io.InputStream getInputStream();

    /**
     * Create the parser
     *
     * @return the java parser
     */
    protected org.acm.seguin.parser.JavaParser getParser() {
        java.io.InputStream in = getInputStream();
        if (in == null) {
            return null;
        }
        // Create the parser
        if (org.acm.seguin.parser.factory.ParserFactory.parser == null) {
            org.acm.seguin.parser.factory.ParserFactory.parser = new org.acm.seguin.parser.JavaParser(in);
        } else {
            org.acm.seguin.parser.factory.ParserFactory.parser.ReInit(in);
        }
        return org.acm.seguin.parser.factory.ParserFactory.parser;
    }

    /**
     * A method to return some key identifying the file that is being parsed
     *
     * @return the identifier
     */
    protected abstract java.lang.String getKey();
}