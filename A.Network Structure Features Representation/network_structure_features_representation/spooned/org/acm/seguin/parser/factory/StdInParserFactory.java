package org.acm.seguin.parser.factory;
/**
 * Generates new parsers for standard input
 *
 * @author Chris Seguin
 */
public class StdInParserFactory extends org.acm.seguin.parser.factory.ParserFactory {
    /**
     * Constructor for a standard input ParserFactory
     */
    public StdInParserFactory() {
    }

    /**
     * Return the input stream
     *
     * @return the input stream
     */
    protected java.io.InputStream getInputStream() {
        return java.lang.System.in;
    }

    /**
     * A method to return some key identifying the file that is being parsed
     *
     * @return the identifier
     */
    protected java.lang.String getKey() {
        return "Standard Input";
    }
}