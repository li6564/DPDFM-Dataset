/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.print;
/**
 * Places the print operations in a separate thread
 *
 * @author Chris Seguin
 * @created August 6, 1999
 */
public class PrintingThread extends java.lang.Thread {
    private org.acm.seguin.uml.UMLPackage currentPackage;

    /**
     * Constructor for the PrintingThread object
     *
     * @param panel
     * 		the current package
     */
    public PrintingThread(org.acm.seguin.uml.UMLPackage panel) {
        currentPackage = panel;
    }

    /**
     * This is where the work actually gets done
     */
    public void run() {
        java.awt.print.PrinterJob job = java.awt.print.PrinterJob.getPrinterJob();
        java.awt.print.Book book = new java.awt.print.Book();
        // Cover Page goes here
        // Package picture
        org.acm.seguin.uml.print.UMLPagePrinter umlpp = new org.acm.seguin.uml.print.UMLPagePrinter(currentPackage);
        java.awt.print.PageFormat pf = org.acm.seguin.uml.print.UMLPagePrinter.getPageFormat(false);
        if (pf == null) {
            pf = org.acm.seguin.uml.print.UMLPagePrinter.getPageFormat(true);
        }
        int count = umlpp.calculatePageCount(pf);
        book.append(umlpp, pf, count);
        job.setPageable(book);
        if (job.printDialog()) {
            try {
                job.print();
            } catch (java.lang.Throwable ex) {
                org.acm.seguin.awt.ExceptionPrinter.print(ex);
            }
        }
    }
}