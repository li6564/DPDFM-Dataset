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
package uml.diagram;
/**
 *
 * @class AssociationLinkRenderer
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Renderer for AssociationLink Figures.
 */
public class AssociationLinkRenderer extends diagram.DefaultLinkRenderer {
    protected static final uml.diagram.CustomUI associationUI = new uml.diagram.CustomUI("association");

    static {
        javax.swing.UIManager.put("association.foreground", java.awt.Color.black);
        javax.swing.UIManager.put("association.background", java.awt.Color.white);
    }

    /**
     * Create a new renderer
     */
    public AssociationLinkRenderer() {
        setUI(uml.diagram.AssociationLinkRenderer.associationUI);
    }

    /**
     * Paint the normal line with a dashed Stroke.
     */
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
    }

    /**
     * No decoration on the source endpoint
     */
    protected java.awt.geom.GeneralPath getSourceEndpoint(double x, double y, java.awt.geom.GeneralPath path) {
        return null;
    }

    /**
     * Draw an open ArrowHead shape at the sink
     */
    protected java.awt.geom.GeneralPath getSinkEndpoint(double x, double y, java.awt.geom.GeneralPath path) {
        return diagram.shape.ArrowHead.createArrowHead(13.0, diagram.shape.ArrowHead.OPEN, x, y, path);
    }

    /**
     * Paint that open ArrowHead shape with a solid Stroke
     */
    protected void paintSinkEndpoint(java.awt.Graphics2D g2, java.awt.geom.AffineTransform at, java.awt.geom.GeneralPath path) {
        // Set the solid stoke
        super.paintSinkEndpoint(g2, at, path);
        g2.draw(path);
    }
}