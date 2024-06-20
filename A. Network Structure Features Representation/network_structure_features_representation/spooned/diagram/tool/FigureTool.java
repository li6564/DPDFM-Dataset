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
import java.awt.geom.Point2D;
/**
 *
 * @class FigureTool
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

This tool allows a Figure to be added to a Diagram model. The FigureTool
will accept an instance of a Figure to use as a protoype. Each time the
tool is invoked, the prototype is clone()ed and shaped to the correct
size. If the user does not drag the mouse to shape the cloned Figure then
it is set to the default size suplpied by prototype object.
 */
public class FigureTool extends AbstractTool {
    private Diagram diagram;

    private java.awt.geom.Point2D ptPress;

    private Figure fig;

    private Figure current;

    private java.awt.geom.Rectangle2D rcBounds = new java.awt.geom.Rectangle2D.Double();

    private FigureTool.MouseHandler mouseHandler = new FigureTool.MouseHandler();

    /**
     * Create a figure tool that uses the given Figure as a template.
     *
     * @param Figure
     */
    public FigureTool(Figure fig) {
        this.fig = fig;
    }

    /**
     * Install support for something in the given Diagram
     *
     * @param Diagram
     */
    public void install(Diagram diagram) {
        diagram.addMouseListener(mouseHandler);
        diagram.addMouseMotionListener(mouseHandler);
    }

    /**
     * Remove support for something that was previously installed.
     *
     * @param Diagram
     */
    public void uninstall(Diagram diagram) {
        diagram.removeMouseListener(mouseHandler);
        diagram.removeMouseMotionListener(mouseHandler);
        reset();
    }

    protected class MouseHandler extends javax.swing.event.MouseInputAdapter {
        /**
         * Mouse action started, begin drag.
         */
        public void mousePressed(java.awt.event.MouseEvent e) {
            // Start the drag
            java.lang.Object o = e.getSource();
            if ((!e.isConsumed()) && (o instanceof Diagram)) {
                e.consume();
                fireToolStarted();
                ptPress = e.getPoint();
                // Add this figure to the diagram
                current = ((Figure) (fig.clone()));
                diagram = ((Diagram) (o));
                diagram.getModel().add(current);
            }
        }

        /**
         * Mouse dragged, adjust figure bounds.
         */
        public void mouseDragged(java.awt.event.MouseEvent e) {
            if (diagram == null)
                return;

            updateBounds(e.getPoint(), ptPress);
            DiagramUI ui = ((DiagramUI) (diagram.getUI()));
            // Resize & paint the figure
            ui.damageFigure(current);
            current.setBounds(rcBounds.getX(), rcBounds.getY(), rcBounds.getWidth(), rcBounds.getHeight());
            ui.refreshFigure(current);
        }

        /**
         * Mouse action stopped, clean up.
         */
        public void mouseReleased(java.awt.event.MouseEvent e) {
            if (diagram == null)
                return;

            updateBounds(e.getPoint(), ptPress);
            // Fix a default size for small shapes
            if ((rcBounds.getWidth() < 20) || (rcBounds.getHeight() < 20)) {
                rcBounds = fig.getBounds2D(rcBounds);
                current.setBounds(ptPress.getX(), ptPress.getY(), rcBounds.getWidth(), rcBounds.getHeight());
                // Repaint that area
                DiagramUI ui = ((DiagramUI) (diagram.getUI()));
                ui.repaintFigure(current);
            }
            reset();
            fireToolFinished();
        }
    }

    /**
     * Calculate a bounding rectangle between two arbitrary points
     *
     * @param Point2D
     * 		pt1
     * @param Point2D
     * 		pt2
     */
    protected void updateBounds(java.awt.geom.Point2D pt1, java.awt.geom.Point2D pt2) {
        double x1 = pt1.getX();
        double x2 = pt2.getX();
        double y1 = pt1.getY();
        double y2 = pt2.getY();
        double x;
        double y;
        double w;
        double h;
        // Update coords
        if (x1 < x2) {
            x = x1;
            w = x2 - x1;
        } else {
            w = x1 - x2;
            x = x2;
        }
        if (y1 < y2) {
            y = y1;
            h = y2 - y1;
        } else {
            h = y1 - y2;
            y = y2;
        }
        rcBounds.setFrame(x, y, w, h);
    }

    /**
     * Cleanup
     */
    protected void reset() {
        current = null;
        diagram = null;
    }
}