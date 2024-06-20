/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.print;
/**
 * Handles printing the page
 *
 * @author Chris Seguin
 * @created August 8, 1999
 */
public abstract class PagePrinter implements java.awt.print.Printable {
    private int filenameFontSize = 14;

    private int datePageFontSize = 8;

    /**
     * Description of the Field
     */
    protected static int headerHeight = 30;

    /**
     * Description of the Field
     */
    protected static java.awt.print.PageFormat pf;

    private static double scale = 1.0;

    /**
     * Description of the Method
     *
     * @param value
     * 		Description of Parameter
     */
    public void setFilenameFontSize(int value) {
        filenameFontSize = value;
    }

    /**
     * Description of the Method
     *
     * @param value
     * 		Description of Parameter
     */
    public void setDatePageCountFontSize(int value) {
        datePageFontSize = value;
    }

    /**
     * Prints the header at the top of the page
     *
     * @param g
     * 		The graphics object
     * @param title
     * 		the title
     * @param pageNumber
     * 		the number of pages
     * @param pageCount
     * 		the page count
     */
    protected void printHeader(java.awt.Graphics g, java.lang.String title, java.lang.String pageNumber, java.lang.String pageCount) {
        // Draw the frame
        int x = ((int) (org.acm.seguin.print.PagePrinter.pf.getImageableX()));
        int y = ((int) (org.acm.seguin.print.PagePrinter.pf.getImageableY()));
        int wide = ((int) (org.acm.seguin.print.PagePrinter.pf.getImageableWidth()));
        int high = org.acm.seguin.print.PagePrinter.headerHeight;
        g.setColor(java.awt.Color.white);
        g.fillRect(x, y, wide - 1, high - 1);
        g.setColor(java.awt.Color.black);
        g.drawRect(x, y, wide - 1, high - 1);
        int quarterWide = wide / 4;
        g.drawLine(x + (2 * quarterWide), y, x + (2 * quarterWide), (y + org.acm.seguin.print.PagePrinter.headerHeight) - 1);
        g.drawLine(x + (3 * quarterWide), y, x + (3 * quarterWide), (y + org.acm.seguin.print.PagePrinter.headerHeight) - 1);
        int centerY = y + (org.acm.seguin.print.PagePrinter.headerHeight / 2);
        // Draw the filename
        g.setFont(new java.awt.Font("Serif", java.awt.Font.BOLD, filenameFontSize));
        java.awt.FontMetrics fm = g.getFontMetrics();
        int tempY = (y + (((org.acm.seguin.print.PagePrinter.headerHeight + fm.getAscent()) + fm.getDescent()) / 2)) - fm.getDescent();
        if ((title != null) && (title.length() > 0)) {
            g.drawString(title, x + 10, tempY);
        }
        // Draw the date
        g.setFont(new java.awt.Font("Serif", java.awt.Font.BOLD, datePageFontSize));
        fm = g.getFontMetrics();
        java.lang.String now = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
        tempY = (y + (((org.acm.seguin.print.PagePrinter.headerHeight + fm.getAscent()) + fm.getDescent()) / 2)) - fm.getDescent();
        g.drawString(now, (x + ((5 * quarterWide) / 2)) - (fm.stringWidth(now) / 2), tempY);
        // Draw the page count
        java.lang.String pages = (pageNumber + " of ") + pageCount;
        g.drawString(pages, (x + ((7 * quarterWide) / 2)) - (fm.stringWidth(pages) / 2), tempY);
    }

    /**
     * Sets the size of the header box
     *
     * @param value
     * 		The size of the header box
     */
    public static void setHeaderHeight(int value) {
        org.acm.seguin.print.PagePrinter.headerHeight = value;
    }

    /**
     * Returns the page
     *
     * @param dialog
     * 		present a dialog screen if none
     * @return the current page format
     */
    public static java.awt.print.PageFormat getPageFormat(boolean dialog) {
        if (dialog) {
            java.awt.print.PrinterJob job = java.awt.print.PrinterJob.getPrinterJob();
            org.acm.seguin.print.PagePrinter.pf = job.pageDialog(job.defaultPage());
        }
        // Get the header height
        try {
            org.acm.seguin.util.FileSettings bundle = org.acm.seguin.util.FileSettings.getSettings("Refactory", "printing");
            org.acm.seguin.print.PagePrinter.setHeaderHeight(java.lang.Integer.parseInt(bundle.getString("header.space")));
        } catch (org.acm.seguin.util.MissingSettingsException mre) {
            org.acm.seguin.awt.ExceptionPrinter.print(mre);
        } catch (java.lang.NumberFormatException inf) {
            org.acm.seguin.awt.ExceptionPrinter.print(inf);
        }
        return org.acm.seguin.print.PagePrinter.pf;
    }

    /**
     * Return the width of the page
     *
     * @return Description of the Returned Value
     */
    public static int getPageWidth() {
        if (org.acm.seguin.print.PagePrinter.pf == null) {
            return -1;
        }
        return ((int) (org.acm.seguin.print.PagePrinter.pf.getImageableWidth() / org.acm.seguin.print.PagePrinter.scale));
    }

    /**
     * Return the width of the page
     *
     * @return Description of the Returned Value
     */
    public static int getPageHeight() {
        if (org.acm.seguin.print.PagePrinter.pf == null) {
            return -1;
        }
        return ((int) (org.acm.seguin.print.PagePrinter.pf.getImageableHeight() / org.acm.seguin.print.PagePrinter.scale));
    }

    /**
     * Sets the scaling
     *
     * @param value
     * 		the scaled value
     */
    protected static void setScale(double value) {
        org.acm.seguin.print.PagePrinter.scale = value;
    }

    /**
     * Returns the scaling
     *
     * @return The scale size
     */
    protected static double getScale() {
        return org.acm.seguin.print.PagePrinter.scale;
    }
}