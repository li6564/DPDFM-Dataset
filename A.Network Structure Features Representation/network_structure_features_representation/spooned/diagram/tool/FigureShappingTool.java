/**
 * Java Diagram Package; An extremely flexible and fast multipurpose diagram
 * component for Swing.
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
package diagram.tool;
import diagram.Diagram;
import diagram.DiagramUI;
import diagram.Figure;
import diagram.Link;
import diagram.figures.FigureBorder;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
/**
 *
 * @class FigureShapping
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Add node shaping support to a Diagram.
 */
public class FigureShappingTool extends AbstractTool {
    // Cache some frequently used Cursors
    private static final java.awt.Cursor HZ_CURSOR = new java.awt.Cursor(java.awt.Cursor.W_RESIZE_CURSOR);

    private static final java.awt.Cursor VT_CURSOR = new java.awt.Cursor(java.awt.Cursor.N_RESIZE_CURSOR);

    private static final java.awt.Cursor SENW_CURSOR = new java.awt.Cursor(java.awt.Cursor.NW_RESIZE_CURSOR);

    private static final java.awt.Cursor SWNE_CURSOR = new java.awt.Cursor(java.awt.Cursor.SW_RESIZE_CURSOR);

    // Sizing directions
    private static final int QUAD_LEFT = 0x1;

    private static final int QUAD_MIDDLE = 0x2;

    private static final int QUAD_RIGHT = 0x4;

    private static final int QUAD_TOP = 0x100;

    private static final int QUAD_CENTER = 0x200;

    private static final int QUAD_BOTTOM = 0x400;

    // Minimum size
    private static final int MINIMUM_SIZE = 0x10;

    private FigureShappingTool.MouseHandler mouseHandler = new FigureShappingTool.MouseHandler();

    // Graphics context to paint on
    private java.awt.Graphics2D graphics;

    // Working data
    private java.awt.geom.Point2D dragPoint;

    private java.awt.geom.Point2D lastPoint;

    private java.awt.geom.Rectangle2D.Double outline = new java.awt.geom.Rectangle2D.Double();

    private java.awt.Cursor originalCursor = null;

    private Diagram diagram;

    // Selected figure info
    private java.awt.geom.Point2D center;

    private java.awt.geom.Rectangle2D bounds;

    private Figure figure = null;

    protected Figure[] relatedFigures = new Figure[4];

    private int sizingDirection = -1;

    /**
     * Install the support in the specified Diagram
     *
     * @param Diagram
     */
    public void install(Diagram diagram) {
        diagram.addMouseListener(mouseHandler);
        diagram.addMouseMotionListener(mouseHandler);
    }

    /**
     * Uninstall the support from specified Diagram
     *
     * @param Diagram
     */
    public void uninstall(Diagram diagram) {
        diagram.removeMouseListener(mouseHandler);
        diagram.removeMouseMotionListener(mouseHandler);
        reset();
    }

    /**
     *
     * @class MouseHandler
     */
    protected class MouseHandler extends javax.swing.event.MouseInputAdapter {
        /**
         * Called when dragging could possibly begin. If the coordinated of this
         * event fall within the boundaries of some object in the Diagram
         * then a drag is started.
         *
         * @param MouseEvent
         * @pre MouseEvent must not have been consumed.
         */
        public void mousePressed(java.awt.event.MouseEvent e) {
            // Check for left mouse button & a valid selection
            if (e.isConsumed() || (!javax.swing.SwingUtilities.isLeftMouseButton(e)))
                return;

            diagram = ((Diagram) (e.getSource()));
            java.awt.geom.Point2D pt = e.getPoint();
            // Find the figure which was clicked on
            // DiagramModel diagramModel = diagram.getModel();
            figure = ((Figure) (diagram.findFigure(pt)));
            if ((figure == null) || (figure instanceof Link)) {
                reset();
                return;
            }
            // Get the bounds & figure info
            bounds = figure.getBounds2D(bounds);
            center = figure.getCenter(center);
            // Check the click point to see if it lies on the boundary of the figure
            if (!FigureBorder.isBorderPoint(figure, pt)) {
                reset();
                return;
            }
            e.consume();
            fireToolStarted();
            // Get a drag outline setup
            outline.setFrame(bounds);
            dragPoint = pt;
            getQuadrant(dragPoint);
            // Change the cursor
            originalCursor = diagram.getCursor();
            diagram.setCursor(getCursor());
            // Save the graphics
            graphics = getGraphics(diagram);
            // Get related figures
            DiagramUI ui = ((DiagramUI) (diagram.getUI()));
            relatedFigures = ui.getConnected(figure, relatedFigures);
        }

        /**
         * Called as the dragging occurs.
         *
         * @param MouseEvent
         */
        public void mouseDragged(java.awt.event.MouseEvent e) {
            // Check for a valid drag message
            if (dragPoint == null)
                return;

            java.awt.geom.Point2D pt = e.getPoint();
            double dx = pt.getX() - dragPoint.getX();
            double dy = pt.getY() - dragPoint.getY();
            paintOutline(dx, dy, false);
            lastPoint = pt;
        }

        /**
         * Called when the dragging to stops. The Figure that was
         * being dragged is moved to its new locations.
         *
         * @param MouseEvent
         */
        public void mouseReleased(java.awt.event.MouseEvent e) {
            if (dragPoint != null) {
                if (lastPoint != null) {
                    // Erase left over outline
                    paintOutline(0, 0, true);
                    // Reshape the figure
                    figure.setBounds(outline.getX(), outline.getY(), outline.getWidth(), outline.getHeight());
                }
                // Damage related figures
                DiagramUI ui = ((DiagramUI) (diagram.getUI()));
                Figure[] related = relatedFigures;
                for (int j = 0; (j < related.length) && (related[j] != null); j++)
                    ui.damageFigure(related[j]);

                // Reset
                ((DiagramUI) (diagram.getUI())).refreshFigure(figure);
                reset();
                fireToolFinished();
            }
        }
    }

    /**
     * Paint the resizing outline of the Figure.
     *
     * @param dx
     * 		- position change
     * @param dy
     * 		- position change
     * @param boolean
     * 		true if only need to erase last drawn outline
     */
    private final void paintOutline(double dx, double dy, boolean eraseOnly) {
        // Erase last outline, if any
        if (lastPoint != null)
            graphics.drawRect(((int) (outline.x)), ((int) (outline.y)), ((int) (outline.width)), ((int) (outline.height)));

        // Move & draw the dragout line
        if ((!eraseOnly) && (!((dx == 0) && (dy == 0)))) {
            reshapeOutline(dx, dy);
            graphics.drawRect(((int) (outline.x)), ((int) (outline.y)), ((int) (outline.width)), ((int) (outline.height)));
        }
        ((DiagramUI) (diagram.getUI())).damageFigure(figure);
    }

    /**
     * Reshape the drag outline.
     *
     * @param double
     * @param double
     */
    private final void reshapeOutline(double dx, double dy) {
        int min = FigureShappingTool.MINIMUM_SIZE;
        double t;
        if (hasQuad(FigureShappingTool.QUAD_LEFT)) {
            if ((t = bounds.getWidth() - dx) > min) {
                outline.x = bounds.getX() + dx;
                outline.width = t;
            }
        } else if (hasQuad(FigureShappingTool.QUAD_RIGHT))
            if ((t = bounds.getWidth() + dx) > min)
                outline.width = t;


        if (hasQuad(FigureShappingTool.QUAD_TOP)) {
            if ((t = bounds.getHeight() - dy) > min) {
                outline.y = bounds.getY() + dy;
                outline.height = t;
            }
        } else if (hasQuad(FigureShappingTool.QUAD_BOTTOM))
            if ((t = bounds.getHeight() + dy) > min)
                outline.height = t;


    }

    /**
     * Find the direction using the clicked point & the center of
     * the target Figure.
     *
     * @param Point2D
     * 		clicked point
     */
    private final void getQuadrant(java.awt.geom.Point2D pt) {
        double padx = bounds.getWidth() / 8.0;
        double pady = bounds.getHeight() / 8.0;
        double x = pt.getX();
        double y = pt.getY();
        double centerX = center.getX();
        double centerY = center.getY();
        int dir;
        if (x < (centerX - padx))
            dir = FigureShappingTool.QUAD_LEFT;
        else if (x < (centerX + padx))
            dir = FigureShappingTool.QUAD_MIDDLE;
        else
            dir = FigureShappingTool.QUAD_RIGHT;

        if (y < (centerY - pady))
            dir |= FigureShappingTool.QUAD_TOP;
        else if (y < (centerY + pady))
            dir |= FigureShappingTool.QUAD_CENTER;
        else
            dir |= FigureShappingTool.QUAD_BOTTOM;

        sizingDirection = dir;
    }

    /**
     * Test the quadrant flag
     *
     * @return boolean
     */
    private final boolean hasQuad(int quad) {
        return (sizingDirection & quad) == quad;
    }

    /**
     * Guess the right cursor to use based on some point in the Figure
     *
     * @return Cursor
     */
    private final java.awt.Cursor getCursor() {
        // Left/Right only
        if (hasQuad(FigureShappingTool.QUAD_CENTER | FigureShappingTool.QUAD_LEFT) || hasQuad(FigureShappingTool.QUAD_CENTER | FigureShappingTool.QUAD_RIGHT))
            return FigureShappingTool.HZ_CURSOR;

        // Top/Bottom only
        if (hasQuad(FigureShappingTool.QUAD_MIDDLE | FigureShappingTool.QUAD_TOP) || hasQuad(FigureShappingTool.QUAD_MIDDLE | FigureShappingTool.QUAD_BOTTOM))
            return FigureShappingTool.VT_CURSOR;

        // Diagonals
        if (hasQuad(FigureShappingTool.QUAD_BOTTOM | FigureShappingTool.QUAD_LEFT) || hasQuad(FigureShappingTool.QUAD_TOP | FigureShappingTool.QUAD_RIGHT))
            return FigureShappingTool.SWNE_CURSOR;

        return FigureShappingTool.SENW_CURSOR;
    }

    /**
     * Prepare the graphcis context.
     *
     * @return Graphics2D
     */
    private final java.awt.Graphics2D getGraphics(java.awt.Component c) {
        java.awt.Graphics2D g = ((java.awt.Graphics2D) (c.getGraphics()));
        g.setColor(java.awt.Color.gray);
        g.setXORMode(c.getBackground());
        g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        return g;
    }

    protected void reset() {
        if ((diagram != null) && (originalCursor != null))
            diagram.setCursor(originalCursor);

        originalCursor = null;
        diagram = null;
        figure = null;
        graphics = null;
        lastPoint = dragPoint = null;
        java.util.Arrays.fill(relatedFigures, 0, relatedFigures.length, null);
    }
}