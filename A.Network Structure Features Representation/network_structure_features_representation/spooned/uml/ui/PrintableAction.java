/**
 * QuickUML; A simple UML tool that demonstrates one use of the
 * Java Diagram Package
 *
 * Copyright (C) 2001  Eric Crahen <crahen@cse.buffalo.edu>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package uml.ui;
/**
 *
 * @class PrintableAction
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Action that provides some generic component printing capabilites
 */
public abstract class PrintableAction extends javax.swing.AbstractAction implements java.awt.print.Printable {
    /**
     * Create a new Action
     */
    public PrintableAction(java.lang.String name, javax.swing.Icon icon) {
        super(name, icon);
    }

    /**
     * Create a new Action
     */
    public PrintableAction(java.lang.String name) {
        super(name);
    }

    /**
     * Get the component to print
     */
    public abstract java.awt.Component getComponent();

    /**
     * Implement Printable
     */
    public int print(java.awt.Graphics g, java.awt.print.PageFormat pf, int pageIndex) {
        java.awt.Component component = getComponent();
        // Create a canvas to paint on
        java.awt.Graphics2D g2 = ((java.awt.Graphics2D) (g));
        // double x, y;
        double x;
        // Check the width of the page being printed
        if (pf.getOrientation() != java.awt.print.PageFormat.LANDSCAPE)
            x = pageIndex * pf.getImageableWidth();
        else
            x = pageIndex * pf.getImageableHeight();

        // If the coords would be out of bounds, there is no page here
        // TODO, shift back left and down to print the tall pages
        if (x > component.getWidth())
            return java.awt.print.Printable.NO_SUCH_PAGE;

        x += pf.getImageableX();
        g2.translate(x, pf.getImageableY());
        // Disable buffered for a quick paint into the canvas
        javax.swing.RepaintManager mgr = javax.swing.RepaintManager.currentManager(component);
        boolean isBuffered = mgr.isDoubleBufferingEnabled();
        mgr.setDoubleBufferingEnabled(false);
        // Paint
        component.paint(g2);
        // Reset buffering
        mgr.setDoubleBufferingEnabled(isBuffered);
        return java.awt.print.Printable.PAGE_EXISTS;
    }

    /**
     * Display a print dialog
     */
    public void print() throws java.awt.print.PrinterException {
        java.awt.print.PrinterJob job = java.awt.print.PrinterJob.getPrinterJob();
        job.setPrintable(this);
        if (job.printDialog())
            job.print();

    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        try {
            print();
        } catch (java.lang.Throwable t) {
            javax.swing.JOptionPane.showMessageDialog(getComponent(), t.getMessage(), "Print Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}