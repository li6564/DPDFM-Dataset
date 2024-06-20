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
 * @class ExportAction
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public abstract class ExportAction extends uml.ui.FileAction {
    public ExportAction(java.lang.String name, javax.swing.Icon icon) {
        super(name, icon);
    }

    protected abstract java.awt.Component getComponent();

    protected void writeGIF(java.io.File file) throws java.lang.Exception {
        // Make the file extension match
        java.lang.String name = file.getName().toLowerCase();
        if (!name.endsWith(".gif"))
            file = new java.io.File(file.getName() + ".gif");

        java.awt.Component component = getComponent();
        int width = component.getWidth();
        int height = component.getHeight();
        java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics g = bi.createGraphics();
        g.setClip(0, 0, width, height);
        // Disable buffered for a quick paint into the canvas
        javax.swing.RepaintManager mgr = javax.swing.RepaintManager.currentManager(component);
        boolean isBuffered = mgr.isDoubleBufferingEnabled();
        mgr.setDoubleBufferingEnabled(false);
        component.paint(g);
        // Reset buffering
        mgr.setDoubleBufferingEnabled(isBuffered);
        acme.GifEncoder codec = new acme.GifEncoder(bi, new java.io.FileOutputStream(file));
        codec.encode();
    }
}