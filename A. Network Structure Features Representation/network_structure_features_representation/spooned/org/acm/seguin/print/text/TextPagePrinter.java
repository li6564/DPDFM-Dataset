/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.print.text;
/**
 * Handles printing the page
 *
 * @author Chris Seguin
 * @created August 6, 1999
 */
public class TextPagePrinter extends org.acm.seguin.print.PagePrinter {
    private java.lang.String filename;

    private org.acm.seguin.print.text.LineSet lineSet;

    private org.acm.seguin.print.text.LinePrinter linePrinter;

    private int textFontSize = 10;

    private int textSkip = 2;

    private static int linesPerPage = -1;

    /**
     * Constructor for the UMLPagePrinter object
     *
     * @param initFilename
     * 		Description of Parameter
     * @param init
     * 		Description of Parameter
     * @param printer
     * 		Description of Parameter
     */
    public TextPagePrinter(java.lang.String initFilename, java.lang.String init, org.acm.seguin.print.text.LinePrinter printer) {
        lineSet = new org.acm.seguin.print.text.LineSet(init);
        linePrinter = printer;
        filename = initFilename;
    }

    /**
     * Sets the TextFontSize attribute of the TextPagePrinter object
     *
     * @param value
     * 		The new TextFontSize value
     */
    public void setTextFontSize(int value) {
        textFontSize = value;
    }

    /**
     * Sets the BetweenLineSpacing attribute of the TextPagePrinter object
     *
     * @param value
     * 		The new BetweenLineSpacing value
     */
    public void setBetweenLineSpacing(int value) {
        textSkip = value;
    }

    /**
     * Guess the number of pages
     *
     * @param pf
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public int calculatePageCount(java.awt.print.PageFormat pf) {
        int pageHeight = ((int) (pf.getImageableHeight()));
        int pageWidth = ((int) (pf.getImageableWidth()));
        int pagesHigh;
        int lpp = org.acm.seguin.print.text.TextPagePrinter.linesPerPage;
        int lineCount = lineSet.size();
        if (org.acm.seguin.print.text.TextPagePrinter.linesPerPage == (-1)) {
            org.acm.seguin.print.PrintingSettings ps = new org.acm.seguin.print.PrintingSettings();
            lpp = ps.getLinesPerPage();
        }
        pagesHigh = lineCount / lpp;
        if ((lineCount % lpp) != 0) {
            pagesHigh++;
        }
        return pagesHigh;
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
     * @return Whether there is more pages or not
     */
    public int print(java.awt.Graphics g, java.awt.print.PageFormat pf, int pageNumber) {
        int pageCount = calculatePageCount(pf);
        if (pageNumber > pageCount) {
            return java.awt.print.Printable.NO_SUCH_PAGE;
        }
        linePrinter.setFontSize(textFontSize);
        int high = linePrinter.getLineHeight(g) + textSkip;
        if (org.acm.seguin.print.text.TextPagePrinter.linesPerPage == (-1)) {
            int pageHeight = ((int) (pf.getImageableHeight())) - org.acm.seguin.print.PagePrinter.headerHeight;
            org.acm.seguin.print.text.TextPagePrinter.linesPerPage = pageHeight / high;
            org.acm.seguin.print.PrintingSettings ps = new org.acm.seguin.print.PrintingSettings();
            ps.setLinesPerPage(org.acm.seguin.print.text.TextPagePrinter.linesPerPage);
        }
        int startIndex = pageNumber * org.acm.seguin.print.text.TextPagePrinter.linesPerPage;
        int xOffset = ((int) (pf.getImageableX()));
        int yOffset = ((int) (pf.getImageableY())) + org.acm.seguin.print.PagePrinter.headerHeight;
        printHeader(g, filename, "" + (1 + pageNumber), "" + pageCount);
        linePrinter.init(g);
        for (int ndx = 0; ndx < org.acm.seguin.print.text.TextPagePrinter.linesPerPage; ndx++) {
            int index = ndx + (pageNumber * org.acm.seguin.print.text.TextPagePrinter.linesPerPage);
            java.lang.String line = lineSet.getLine(index);
            if (line == null) {
                break;
            }
            linePrinter.print(g, line, xOffset, yOffset + ((1 + ndx) * high), lineSet, index);
        }
        return java.awt.print.Printable.PAGE_EXISTS;
    }
}