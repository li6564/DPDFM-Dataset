package org.acm.seguin.parser.factory;
/**
 * Generates new parsers for a java file
 *
 * @author Chris Seguin
 * @created June 6, 1999
 */
public class InputStreamParserFactory extends org.acm.seguin.parser.factory.ParserFactory {
    // Instance Variables
    private java.io.InputStream input;

    private java.lang.String key;

    /**
     * Constructor for a file ParserFactory
     *
     * @param inputStream
     * 		Description of Parameter
     * @param initKey
     * 		Description of Parameter
     */
    public InputStreamParserFactory(java.io.InputStream inputStream, java.lang.String initKey) {
        input = inputStream;
        key = initKey;
    }

    /**
     * Return the input stream
     *
     * @return the input stream
     */
    protected java.io.InputStream getInputStream() {
        return input;
    }

    /**
     * A method to return some key identifying the file that is being parsed
     *
     * @return the identifier
     */
    protected java.lang.String getKey() {
        return key;
    }
}