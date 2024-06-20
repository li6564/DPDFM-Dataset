/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.factory;
/**
 * Generates new parsers for a java file
 *
 * @author Chris Seguin
 * @created June 6, 1999
 */
public class FileParserFactory extends org.acm.seguin.parser.factory.ParserFactory {
    // Instance Variables
    private java.io.File input;

    /**
     * Constructor for a file ParserFactory
     *
     * @param file
     * 		the file that we want to create a parser for
     */
    public FileParserFactory(java.io.File file) {
        input = file;
    }

    /**
     * Return the input stream
     *
     * @return the input stream
     */
    protected java.io.InputStream getInputStream() {
        try {
            return new java.io.FileInputStream(input);
        } catch (java.io.FileNotFoundException fnfe) {
            java.lang.System.err.println("Unable to find the file specified by " + getKey());
            return null;
        } catch (java.io.IOException ioe) {
            java.lang.System.err.println("Unable to create the file " + getKey());
            return null;
        }
    }

    /**
     * A method to return some key identifying the file that is being parsed
     *
     * @return the identifier
     */
    protected java.lang.String getKey() {
        return input.getAbsolutePath();
    }
}