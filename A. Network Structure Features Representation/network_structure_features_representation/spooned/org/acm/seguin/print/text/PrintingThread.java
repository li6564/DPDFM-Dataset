/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.print.text;
/**
 * Places the print operations in a separate thread
 *
 * @author Chris Seguin
 * @created August 6, 1999
 */
public class PrintingThread extends java.lang.Thread {
    private java.lang.String data;

    private org.acm.seguin.print.text.LinePrinter printer;

    private java.lang.String filename;

    /**
     * Constructor for the PrintingThread object
     *
     * @param filename
     * 		Description of Parameter
     * @param init
     * 		Description of Parameter
     * @param printer
     * 		Description of Parameter
     */
    public PrintingThread(java.lang.String filename, java.lang.String init, org.acm.seguin.print.text.LinePrinter printer) {
        data = init;
        this.printer = printer;
        this.filename = filename;
    }

    /**
     * This is where the work actually gets done
     */
    public void run() {
        java.awt.print.PrinterJob job = java.awt.print.PrinterJob.getPrinterJob();
        java.awt.print.Book book = new java.awt.print.Book();
        // Cover Page goes here
        // Package picture
        org.acm.seguin.print.text.TextPagePrinter textpp = new org.acm.seguin.print.text.TextPagePrinter(filename, data, printer);
        loadDefaults(textpp);
        java.awt.print.PageFormat pf = org.acm.seguin.print.text.TextPagePrinter.getPageFormat(false);
        if (pf == null) {
            pf = org.acm.seguin.print.text.TextPagePrinter.getPageFormat(true);
        }
        int count = textpp.calculatePageCount(pf);
        book.append(textpp, pf, count);
        job.setPageable(book);
        if (job.printDialog()) {
            try {
                job.print();
            } catch (java.lang.Throwable ex) {
                org.acm.seguin.awt.ExceptionPrinter.print(ex);
            }
        }
    }

    /**
     * Description of the Method
     *
     * @param textpp
     * 		Description of Parameter
     */
    private void loadDefaults(org.acm.seguin.print.text.TextPagePrinter textpp) {
        try {
            org.acm.seguin.util.FileSettings bundle = org.acm.seguin.util.FileSettings.getSettings("Refactory", "printing");
            textpp.setTextFontSize(java.lang.Integer.parseInt(bundle.getString("text.font.size")));
            textpp.setBetweenLineSpacing(java.lang.Integer.parseInt(bundle.getString("text.space")));
            textpp.setFilenameFontSize(java.lang.Integer.parseInt(bundle.getString("filename.font.size")));
            textpp.setDatePageCountFontSize(java.lang.Integer.parseInt(bundle.getString("date.font.size")));
        } catch (org.acm.seguin.util.MissingSettingsException mre) {
            org.acm.seguin.awt.ExceptionPrinter.print(mre);
        } catch (java.lang.NumberFormatException inf) {
            org.acm.seguin.awt.ExceptionPrinter.print(inf);
        }
    }

    /**
     * The main program for the PrintingThread class
     *
     * @param args
     * 		The command line arguments
     */
    public static void main(java.lang.String[] args) {
        try {
            java.io.FileReader in = new java.io.FileReader(args[0]);
            java.io.BufferedReader input = new java.io.BufferedReader(in);
            java.lang.StringBuffer all = new java.lang.StringBuffer();
            java.lang.String line = input.readLine();
            while (line != null) {
                all.append(line);
                all.append("\n");
                line = input.readLine();
            } 
            input.close();
            new org.acm.seguin.print.text.PrintingThread(args[0], all.toString(), new org.acm.seguin.print.text.LinePrinter()).run();
        } catch (java.io.IOException ioe) {
            org.acm.seguin.awt.ExceptionPrinter.print(ioe);
        }
    }
}