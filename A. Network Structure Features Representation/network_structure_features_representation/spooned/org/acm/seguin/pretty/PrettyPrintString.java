/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty;
/**
 * Pretty prints the string
 *
 * @author Chris Seguin
 */
abstract class PrettyPrintString extends org.acm.seguin.pretty.PrettyPrintFile {
    // Instance Variables
    private java.io.ByteArrayOutputStream outputStream;

    /**
     * Create an PrettyPrintString object
     */
    public PrettyPrintString() {
        outputStream = new java.io.ByteArrayOutputStream();
    }

    /**
     * Sets the input string to be pretty printed
     *
     * @param input
     * 		the input buffer
     */
    protected void setInputString(java.lang.String input) {
        if (input == null) {
            return;
        }
        setParserFactory(new org.acm.seguin.parser.factory.BufferParserFactory(input));
    }

    /**
     * Get the output buffer
     *
     * @return a string containing the results
     */
    protected java.lang.String getOutputBuffer() {
        return new java.lang.String(outputStream.toByteArray());
    }

    /**
     * Create the output stream
     *
     * @param file
     * 		the name of the file
     * @return the output stream
     */
    protected java.io.OutputStream getOutputStream(java.io.File file) {
        return outputStream;
    }

    /**
     * Reset the output buffer
     */
    protected void resetOutputBuffer() {
        outputStream.reset();
    }
}