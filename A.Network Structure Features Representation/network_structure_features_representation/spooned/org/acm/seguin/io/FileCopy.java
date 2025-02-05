/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.io;
/**
 * Responsible for copying a file from one location to another. This object
 *  is implemented as a thread so that if the user of this object does not
 *  care when the copy is complete, they can set the parameters and then
 *  launch the thread. <P>
 *
 *  To perform other operations on the streams while copying the thread,
 *  simply overload the getInputStream or getOutputStream operations.
 *
 * @author Chris Seguin
 * @date May 12, 1999
 */
public class FileCopy extends java.lang.Thread {
    // Instance Variables
    private java.io.File source;

    private java.io.File dest;

    private boolean noisy;

    /**
     * Constructor for the FileCopy object
     *
     * @param src
     * 		the source
     * @param dst
     * 		the destination
     */
    public FileCopy(java.io.File src, java.io.File dst) {
        source = src;
        dest = dst;
        noisy = true;
    }

    /**
     * Create a file copy thread
     *
     * @param src
     * 		the source
     * @param dst
     * 		the destination
     * @param isNoisy
     * 		is this thread supposed to report on it's progress
     */
    public FileCopy(java.io.File src, java.io.File dst, boolean isNoisy) {
        source = src;
        dest = dst;
        noisy = isNoisy;
    }

    /**
     * Copy the thread
     */
    public void run() {
        try {
            if (noisy) {
                java.lang.System.out.println((("Copying from " + source.getCanonicalPath()) + " to ") + dest.getCanonicalPath());
            }
            java.io.InputStream fis = getInputStream();
            java.io.OutputStream fos = getOutputStream();
            int bufferSize = (1024 * 8) * 4;
            byte[] buffer = new byte[bufferSize];
            int bytesRead = bufferSize;
            while (bytesRead == bufferSize) {
                bytesRead = fis.read(buffer);
                fos.write(buffer, 0, bytesRead);
            } 
            fos.close();
            fis.close();
        } catch (java.io.IOException ioe) {
            org.acm.seguin.awt.ExceptionPrinter.print(ioe);
        }
    }

    /**
     * Gets the InputStream attribute of the FileCopy object
     *
     * @return The InputStream value
     * @exception FileNotFoundException
     * 		Unable to open the file
     */
    protected java.io.InputStream getInputStream() throws java.io.IOException {
        return new java.io.FileInputStream(source);
    }

    /**
     * Gets the OutputStream attribute of the FileCopy object
     *
     * @return The OutputStream value
     * @exception FileNotFoundException
     * 		Unable to open the file
     */
    protected java.io.OutputStream getOutputStream() throws java.io.IOException {
        java.io.File parent = dest.getParentFile();
        if ((parent != null) && (!parent.exists()))
            parent.mkdirs();

        return new java.io.FileOutputStream(dest);
    }

    /**
     * Tester
     *
     * @param args
     * 		The command line arguments
     */
    public static void main(java.lang.String[] args) {
        if (args.length < 2) {
            java.lang.System.out.println("Syntax:  FileCopy source dest");
            return;
        }
        new org.acm.seguin.io.FileCopy(new java.io.File(args[0]), new java.io.File(args[1])).run();
    }
}