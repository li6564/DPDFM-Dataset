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
import diagram.SelectionModel;
import diagram.figures.FigureBorder;
import java.awt.event.MouseEvent;
/**
 *
 * @class FigureDraggingTool
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

This tool allows selected Figures to be dragged around the Diagram.
 */
public class FigureDraggingTool extends AbstractTool {
    // Drag cursor
    private static final java.awt.Cursor MV_CURSOR = new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR);

    // Orginal diagram cursor
    private java.awt.Cursor originalCursor = null;

    // Selected & related figures
    protected Figure[] selectedFigures = new Figure[4];

    protected Figure[] relatedFigures = new Figure[4];

    // Cached list, used to build the list of related figures
    private java.util.ArrayList relatedList = new java.util.ArrayList();

    // Diagram
    private Diagram diagram;

    // Mouse dragged from
    private java.awt.geom.Point2D dragPoint;

    private FigureDraggingTool.MouseHandler mouseHandler = new FigureDraggingTool.MouseHandler();

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

    /**
     *
     * @class MouseHandler
     */
    protected class MouseHandler extends javax.swing.event.MouseInputAdapter {
        /**
         * Should this event trigger the suport object
         */
        private final boolean shouldIgnore(java.awt.event.MouseEvent e) {
            if ((e.isControlDown() || e.isAltDown()) || (e.getClickCount() > 1))
                return true;

            return (e.isConsumed() || (!javax.swing.SwingUtilities.isLeftMouseButton(e))) || (dragPoint != null);
        }

        /**
         * Called when dragging could possibly begin.
         *
         * @param MouseEvent
         */
        public void mousePressed(java.awt.event.MouseEvent e) {
            // Check for left mouse button & a valid selection
            if (shouldIgnore(e))
                return;

            // Get the diagram and the point
            diagram = ((Diagram) (e.getSource()));
            java.awt.geom.Point2D pt = e.getPoint();
            Diagram diagram = ((Diagram) (e.getSource()));
            SelectionModel selectionModel = diagram.getSelectionModel();
            // Selection model is needed
            if ((selectionModel == null) || (selectionModel.size() < 1))
                return;

            // Find the node which was clicked on
            Figure node = ((Figure) (diagram.findFigure(e.getPoint())));
            // Valid node? Not a Border point?
            if (((node == null) || (!selectionModel.contains(node))) || FigureBorder.isBorderPoint(node, pt))
                return;

            e.consume();
            fireToolStarted();
            // Copy the selected nodes
            selectedFigures = ((Figure[]) (selectionModel.toArray(selectedFigures)));
            getRelatedFigures();
            dragPoint = pt;
            // Change the cursor on glass pane
            originalCursor = diagram.getCursor();
            diagram.setCursor(FigureDraggingTool.MV_CURSOR);
        }

        /**
         * Called as the dragging occurs.
         *
         * @param MouseEvent
         */
        public void mouseDragged(java.awt.event.MouseEvent e) {
            // Check for a valid drag message
            if ((!e.isConsumed()) && (dragPoint != null)) {
                e.consume();
                // Get the delta values
                java.awt.geom.Point2D pt = e.getPoint();
                double dx = pt.getX() - dragPoint.getX();
                double dy = pt.getY() - dragPoint.getY();
                DiagramUI ui = ((DiagramUI) (diagram.getUI()));
                Figure[] selected = selectedFigures;
                Figure[] related = relatedFigures;
                // Damage the related figures
                for (int j = 0; (j < related.length) && (related[j] != null); j++)
                    ui.damageFigure(related[j]);

                // Damage the selected figures, translate them & refresh them
                Figure figure = null;
                for (int i = 0; (i < selected.length) && (selected[i] != null); i++) {
                    figure = selected[i];
                    ui.damageFigure(figure);
                    figure.translate(dx, dy);
                    ui.damageFigure(figure);
                }
                // Damage the related figures
                for (int j = 0; (j < related.length) && (related[j] != null); j++)
                    ui.damageFigure(related[j]);

                // Refresh the first figure, if fast refresh mode is enabled then
                // this will force a repaint of all dirty (damaged) regions
                ui.refreshFigure(figure);
                dragPoint = pt;
            }
        }

        /**
         * Called when the dragging to stops. The Component that was
         * being dragged is moved to its new locations.
         *
         * @param MouseEvent
         */
        public void mouseReleased(java.awt.event.MouseEvent e) {
            if (dragPoint == null)
                return;

            e.consume();
            // Redraw once the drag is completed
            ((DiagramUI) (diagram.getUI())).repaintRegion(null);
            reset();
            fireToolFinished();
        }
    }

    /**
     * Find all the related figures, update the relatedFigures array.
     */
    protected void getRelatedFigures() {
        java.util.ArrayList list = relatedList;
        Figure[] figures = selectedFigures;
        Figure[] cache = relatedFigures;
        // Walk through the selected figures and get all the related figures
        DiagramUI ui = ((DiagramUI) (diagram.getUI()));
        for (int i = 0; (i < figures.length) && (figures[i] != null); i++) {
            // Add the related items to the list
            cache = ui.getConnected(figures[i], cache);
            for (int j = 0; (j < cache.length) && (cache[j] != null); j++)
                relatedList.add(cache[j]);

        }
        // Convert the collected figures to an array (used to damage & repaint later)
        relatedFigures = ((Figure[]) (list.toArray(relatedFigures)));
        list.clear();
    }

    /**
     * Cleanup
     */
    protected void reset() {
        // Reset the cursor
        if ((diagram != null) && (originalCursor != null))
            diagram.setCursor(originalCursor);

        // Clear the used items
        diagram = null;
        dragPoint = null;
        java.util.Arrays.fill(selectedFigures, 0, selectedFigures.length, null);
        java.util.Arrays.fill(relatedFigures, 0, relatedFigures.length, null);
    }
}