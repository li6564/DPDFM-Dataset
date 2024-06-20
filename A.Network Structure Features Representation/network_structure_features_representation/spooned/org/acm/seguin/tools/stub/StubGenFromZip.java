/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.tools.stub;
/**
 * Generates a stub set from a file
 *
 * @author Chris Seguin
 */
class StubGenFromZip {
    private java.lang.String filename;

    private org.acm.seguin.tools.stub.StubFile sf;

    /**
     * Constructor for the StubGenFromZip object
     *
     * @param name
     * 		The name of the zip file
     * @param stubKey
     * 		Description of Parameter
     * @param file
     * 		Description of Parameter
     */
    public StubGenFromZip(java.lang.String name, java.lang.String stubKey, java.io.File file) {
        filename = name;
        sf = new org.acm.seguin.tools.stub.StubFile(stubKey, file);
    }

    /**
     * Main processing method for the StubGenFromZip object
     */
    public void run() {
        try {
            java.util.zip.ZipFile zipfile = new java.util.zip.ZipFile(filename);
            java.util.Enumeration entryEnum = zipfile.entries();
            while (entryEnum.hasMoreElements()) {
                java.util.zip.ZipEntry entry = ((java.util.zip.ZipEntry) (entryEnum.nextElement()));
                if (applies(entry)) {
                    java.io.InputStream input = zipfile.getInputStream(entry);
                    generateStub(input, entry.getName());
                    input.close();
                }
            } 
            sf.done();
        } catch (java.lang.Throwable thrown) {
            thrown.printStackTrace(java.lang.System.out);
        }
    }

    /**
     * Does this algorithm apply to this entry
     *
     * @param entry
     * 		the entry
     * @return true if we should generate a stub from it
     */
    private boolean applies(java.util.zip.ZipEntry entry) {
        return (!entry.isDirectory()) && entry.getName().endsWith(".java");
    }

    /**
     * Generates a stub
     *
     * @param input
     * 		the input stream
     * @param filename
     * 		the filename
     */
    private void generateStub(java.io.InputStream input, java.lang.String filename) {
        java.lang.System.out.println("Generating a stub for:  " + filename);
        sf.apply(input, filename);
    }
}