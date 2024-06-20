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
 * @class ScaledPrintableAction
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Action that provides some generic component printing capabilites
 */
public abstract class ScaledPrintableAction extends uml.ui.PrintableAction {
    /**
     * Create a new Action
     */
    public ScaledPrintableAction(java.lang.String name, javax.swing.Icon icon) {
        super(name, icon);
    }

    /**
     * Implement Printable
     */
    public int print(java.awt.Graphics g, java.awt.print.PageFormat pf, int pageIndex) {
        if (pageIndex > 0)
            return java.awt.print.Printable.NO_SUCH_PAGE;

        java.awt.Component component = getComponent();
        // Disable buffered for a quick paint into the canvas
        javax.swing.RepaintManager mgr = javax.swing.RepaintManager.currentManager(component);
        boolean isBuffered = mgr.isDoubleBufferingEnabled();
        mgr.setDoubleBufferingEnabled(false);
        // Transform & Paint
        java.awt.Graphics2D g2 = ((java.awt.Graphics2D) (g));
        double sx = ((double) (pf.getImageableWidth())) / ((double) (component.getWidth()));
        double sy = ((double) (pf.getImageableHeight())) / ((double) (component.getHeight()));
        double scale = (sx < sy) ? sx : sy;
        g2.scale(scale, scale);
        double x = ((double) (pf.getImageableX())) - ((double) (component.getX()));
        double y = ((double) (pf.getImageableY())) - ((double) (component.getY()));
        g2.translate(x, y);
        component.paint(g2);
        // Reset buffering
        mgr.setDoubleBufferingEnabled(isBuffered);
        return java.awt.print.Printable.PAGE_EXISTS;
    }
}