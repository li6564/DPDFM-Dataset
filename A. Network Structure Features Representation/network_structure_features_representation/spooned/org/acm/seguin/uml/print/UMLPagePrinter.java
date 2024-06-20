/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.print;
/**
 * Handles printing the page
 *
 * @author Chris Seguin
 * @created August 6, 1999
 */
public class UMLPagePrinter extends org.acm.seguin.print.PagePrinter {
    private org.acm.seguin.uml.UMLPackage currentPackage;

    /**
     * Constructor for the UMLPagePrinter object
     *
     * @param panel
     * 		the current package
     */
    public UMLPagePrinter(org.acm.seguin.uml.UMLPackage panel) {
        currentPackage = panel;
    }

    /**
     * Guess the number of pages
     *
     * @param pf
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public int calculatePageCount(java.awt.print.PageFormat pf) {
        java.awt.Dimension size = currentPackage.getPreferredSize();
        int pageHeight = ((int) (pf.getImageableHeight())) - org.acm.seguin.print.PagePrinter.headerHeight;
        int pageWidth = ((int) (pf.getImageableWidth()));
        int pagesWide = ((int) (1 + ((org.acm.seguin.print.PagePrinter.getScale() * size.width) / pageWidth)));
        int pagesHigh = ((int) (1 + ((org.acm.seguin.print.PagePrinter.getScale() * size.height) / pageHeight)));
        return pagesWide * pagesHigh;
    }

    /**
     * Print the page
     *
     * @param g
     * 		the graphics object
     * @param pf
     * 		the page format
     * @param pageNumber
     * 		the page number
     * @return Description of the Returned Value
     */
    public int print(java.awt.Graphics g, java.awt.print.PageFormat pf, int pageNumber) {
        java.awt.Dimension size = currentPackage.getPreferredSize();
        int pageHeight = ((int) (pf.getImageableHeight())) - org.acm.seguin.print.PagePrinter.headerHeight;
        int pageWidth = ((int) (pf.getImageableWidth()));
        int pagesWide = ((int) (1 + ((org.acm.seguin.print.PagePrinter.getScale() * size.width) / pageWidth)));
        int pagesHigh = ((int) (1 + ((org.acm.seguin.print.PagePrinter.getScale() * size.height) / pageHeight)));
        if (pageNumber > (pagesWide * pagesHigh)) {
            return java.awt.print.Printable.NO_SUCH_PAGE;
        }
        int row = pageNumber / pagesWide;
        int col = pageNumber % pagesWide;
        /* if (panelBuffer == null) {
        panelBuffer = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        Graphics tempGraphics = panelBuffer.getGraphics();
        tempGraphics.setColor(Color.white);
        tempGraphics.fillRect(0, 0, size.width, size.height);
        currentPackage.print(tempGraphics, 0, 0);
        }
        g.drawImage(panelBuffer,
        ((int) pf.getImageableX()) - col * pageWidth,
        ((int) pf.getImageableY())- row * pageHeight, null);
         */
        ((java.awt.Graphics2D) (g)).translate(pf.getImageableX() - (col * pageWidth), (pf.getImageableY() - (row * pageHeight)) + org.acm.seguin.print.PagePrinter.headerHeight);
        ((java.awt.Graphics2D) (g)).scale(org.acm.seguin.print.PagePrinter.getScale(), org.acm.seguin.print.PagePrinter.getScale());
        currentPackage.print(g, 0, 0);
        ((java.awt.Graphics2D) (g)).scale(1 / org.acm.seguin.print.PagePrinter.getScale(), 1 / org.acm.seguin.print.PagePrinter.getScale());
        ((java.awt.Graphics2D) (g)).translate(-(pf.getImageableX() - (col * pageWidth)), -((pf.getImageableY() - (row * pageHeight)) + org.acm.seguin.print.PagePrinter.headerHeight));
        java.lang.String packageName = currentPackage.getSummary().getName();
        if ((packageName == null) || (packageName.length() == 0)) {
            packageName = "Top Level Package";
        }
        printHeader(g, packageName, ((("(" + (1 + col)) + ", ") + (1 + row)) + ")", ((("(" + pagesWide) + ", ") + pagesHigh) + ")");
        return java.awt.print.Printable.PAGE_EXISTS;
    }

    /**
     * Returns the page
     *
     * @param dialog
     * 		present a dialog screen if none
     * @return the current page format
     */
    public static java.awt.print.PageFormat getPageFormat(boolean dialog) {
        java.awt.print.PageFormat pf = org.acm.seguin.print.PagePrinter.getPageFormat(dialog);
        org.acm.seguin.print.PagePrinter.setScale(0.8);
        return pf;
    }

    /**
     * Return the width of the page
     *
     * @return Description of the Returned Value
     */
    public static int getPageHeight() {
        int result = org.acm.seguin.print.PagePrinter.getPageHeight();
        if (result == (-1)) {
            return -1;
        }
        return result - org.acm.seguin.print.PagePrinter.headerHeight;
    }
}