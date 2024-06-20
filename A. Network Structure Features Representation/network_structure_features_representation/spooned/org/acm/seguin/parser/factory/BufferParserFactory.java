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
public class BufferParserFactory extends org.acm.seguin.parser.factory.ParserFactory {
    // Instance Variables
    private java.lang.String inputBuffer;

    /**
     * Constructor for the buffer parser factory
     *
     * @param buffer
     * 		the initial buffer
     */
    public BufferParserFactory(java.lang.String buffer) {
        inputBuffer = buffer;
    }

    /**
     * Return the input stream
     *
     * @return the input stream
     */
    protected java.io.InputStream getInputStream() {
        return new java.io.ByteArrayInputStream(inputBuffer.getBytes());
    }

    /**
     * A method to return some key identifying the file that is being parsed
     *
     * @return the identifier
     */
    protected java.lang.String getKey() {
        return "the current file";
    }
}