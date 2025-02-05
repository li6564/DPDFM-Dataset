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
import diagram.Diagram;
import diagram.Figure;
/**
 *
 * @class CompositionLinkRenderer
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class CompositionLinkRenderer extends diagram.DefaultLinkRenderer {
    protected static final uml.diagram.CustomUI compositionUI = new uml.diagram.CustomUI("composition");

    private double angle;

    private double sourceX;

    private double sourceY;

    private java.lang.String cardinality;

    private java.awt.Point pt = new java.awt.Point();

    static {
        javax.swing.UIManager.put("composition.foreground", java.awt.Color.black);
        javax.swing.UIManager.put("composition.background", java.awt.Color.white);
    }

    public CompositionLinkRenderer() {
        super(new uml.diagram.SinkLabelRenderer());
        setUI(uml.diagram.CompositionLinkRenderer.compositionUI);
    }

    public java.awt.Component getRendererComponent(Diagram diagram, Figure figure, boolean isSelected) {
        uml.diagram.CompositionItem item = ((uml.diagram.CompositionItem) (diagram.getModel().getValue(figure)));
        cardinality = (item == null) ? "" : item.getCardinality();
        return super.getRendererComponent(diagram, figure, isSelected);
    }

    public java.awt.geom.Rectangle2D getDecoratedBounds(Diagram diagram, Figure figure, java.awt.geom.Rectangle2D rcBounds) {
        rcBounds = super.getDecoratedBounds(diagram, figure, rcBounds);
        java.awt.Font font = getFont();
        if (font == null)
            return rcBounds;

        java.awt.FontMetrics metrics = getFontMetrics(font);
        int expansion = ((int) (getExpansion()));
        int h = java.lang.Math.max((metrics.getHeight() + 4) - expansion, 0);
        int w = metrics.charsWidth(cardinality.toCharArray(), 0, cardinality.length());
        w = java.lang.Math.max(w - expansion, 0);
        rcBounds.setFrame(rcBounds.getX() - w, rcBounds.getY() - h, rcBounds.getWidth() + (w * 2), rcBounds.getHeight() + (h * 2));
        return rcBounds;
    }

    protected java.awt.geom.GeneralPath getSinkEndpoint(double x, double y, java.awt.geom.GeneralPath path) {
        // This is not painted, only returned so that the paintSinkEndpoint will be invoked
        return diagram.shape.DiamondHead.createDiamondHead(x, y, 7.0, 6.0, path);
    }

    protected java.awt.geom.GeneralPath getSourceEndpoint(double x, double y, java.awt.geom.GeneralPath path) {
        return diagram.shape.DiamondHead.createDiamondHead(x, y, 7.0, 6.0, path);
    }

    protected double getSinkAngle(double x1, double y1, double x2, double y2) {
        double angle = super.getSinkAngle(x1, y1, x2, y2);
        java.awt.FontMetrics metrics = getFontMetrics(getFont());
        int h = metrics.getHeight();
        int w = metrics.charsWidth(cardinality.toCharArray(), 0, cardinality.length());
        pt.x = ((int) (x2 + 24.0));
        pt.y = ((int) (y2 - 4.0));
        if (angle <= ((-java.lang.Math.PI) / 4))
            pt.translate(w, 0);
        else if ((angle > ((-java.lang.Math.PI) / 4)) && (angle < (java.lang.Math.PI / 4)))
            pt.translate(w, -h);
        else if (angle <= ((java.lang.Math.PI * 3) / 4))
            pt.translate(0, -h);
        else if (angle <= ((java.lang.Math.PI * 5) / 4))
            pt.translate(0, 0);

        return this.angle = angle;
    }

    protected void paintSourceEndpoint(java.awt.Graphics2D g2, java.awt.geom.AffineTransform at, java.awt.geom.GeneralPath path) {
        g2.setPaint(getBackground());
        super.paintSourceEndpoint(g2, at, path);
        g2.setPaint(getForeground());
        g2.draw(path);
    }

    protected void paintSinkEndpoint(java.awt.Graphics2D g2, java.awt.geom.AffineTransform at, java.awt.geom.GeneralPath path) {
        at.transform(pt, pt);
        g2.drawString(cardinality, ((float) (pt.x)), ((float) (pt.y)));
    }
}