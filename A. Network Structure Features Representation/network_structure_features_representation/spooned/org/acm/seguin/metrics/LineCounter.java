/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.metrics;
import java.io.IOException;
/**
 * Counts the number of lines in a file
 *
 * @author Chris Seguin
 * @created June 30, 1999
 */
public class LineCounter {
    java.io.File file;

    /**
     * Constructor for the LineCounter object
     *
     * @param init
     * 		File to count
     */
    public LineCounter(java.io.File init) {
        file = init;
    }

    /**
     * Counts the number of lines in a file
     *
     * @return The number of lines in the file
     */
    public int countLines() {
        int count = 1;
        try {
            java.io.FileInputStream in = new java.io.FileInputStream(file);
            int ch = 0;
            while (ch != (-1)) {
                ch = in.read();
                count += countCharacter(ch, in);
            } 
            in.close();
        } catch (java.io.IOException ioe) {
            org.acm.seguin.awt.ExceptionPrinter.print(ioe);
        }
        return count;
    }

    /**
     * Prints the message
     *
     * @return The number of lines in the file
     */
    public int printMessage() {
        int count = countLines();
        if (count < 10) {
            java.lang.System.out.println((("      " + count) + "  ") + file.getPath());
        } else if (count < 100) {
            java.lang.System.out.println((("     " + count) + "  ") + file.getPath());
        } else if (count < 1000) {
            java.lang.System.out.println((("    " + count) + "  ") + file.getPath());
        } else if (count < 10000) {
            java.lang.System.out.println((("   " + count) + "  ") + file.getPath());
        } else if (count < 100000) {
            java.lang.System.out.println((("  " + count) + "  ") + file.getPath());
        } else {
            java.lang.System.out.println(((" " + count) + "  ") + file.getPath());
        }
        return count;
    }

    /**
     * Counts how many lines a character counts as (depends on next character
     *  sometimes)
     *
     * @param ch
     * 		The character to be counted
     * @param in
     * 		The input stream
     * @return The number to add to the ongoing count
     * @exception IOException
     * 		Thrown if unable to read from the input stream
     */
    protected int countCharacter(int ch, java.io.InputStream in) throws java.io.IOException {
        if (ch == '\n') {
            return 1;
        } else if (ch == '\r') {
            int next = in.read();
            if (next == '\n') {
                return 1;
            } else {
                return 1 + countCharacter(next, in);
            }
        }
        return 0;
    }

    /**
     * Main program
     *
     * @param args
     * 		Command line arguments
     */
    public static void main(java.lang.String[] args) {
        if (args.length == 0) {
            java.lang.System.out.println("Syntax:  java org.acm.seguin.metrics.LineCounter <filename>");
            return;
        }
        int count = new org.acm.seguin.metrics.LineCounter(new java.io.File(args[0])).printMessage();
    }
}