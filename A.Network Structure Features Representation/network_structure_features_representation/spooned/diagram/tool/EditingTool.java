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
import diagram.FigureEditor;
import diagram.Link;
import diagram.SelectionModel;
import java.awt.event.MouseEvent;
import java.util.EventObject;
/**
 *
 * @class EditingTool
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Add figure editing support to a Diagram. The method used to supprot figure
editing is a simplified version of the method used in Suns JTable
implementation.

Editing is triggered whenever a mouse is clicked inside a Figure in a
Diagram. The FigureEditor for the diagram is obtained and an Component is
produced to perform the editing. Editing will continue until that Component
loses focus, either from clicking off or clicking onto another Figure.

Three Action objects are installed in the Diagram ActionMap,

"startEditing"
"stopEditing"
"cancleEditing"

A diagram can insert mapping for these actions into its InputMap inorder
to take advantage of them. Editing can be controlled programtically, on the
selected Figure, in this way.
 */
public class EditingTool extends AbstractTool {
    // Event handlers
    protected EditingTool.MouseHandler mouseHandler = new EditingTool.MouseHandler();

    protected EditingTool.FocusHandler focusHandler = new EditingTool.FocusHandler();

    // Editing actions
    protected javax.swing.Action actionStart = new EditingTool.StartEditingAction();

    protected javax.swing.Action actionStop = new EditingTool.StopEditingAction();

    protected javax.swing.Action actionCancel = new EditingTool.CancelEditingAction();

    // Current Diagram
    private Diagram diagram;

    // Current Figure
    private Figure figure;

    // Component recieving mouse events during editing. May not be editorComponent.
    private java.awt.Component dispatchComponent;

    // Component being used to render the editing
    private java.awt.Component editorComponent;

    // Bounds for the editing component (cached)
    private java.awt.Rectangle editorBounds = new java.awt.Rectangle();

    // Current editor
    private FigureEditor editor;

    // Selected figures
    private Figure[] selected = new Figure[1];

    /**
     * Install the support in the specified Diagram
     *
     * @param Diagram
     */
    public void install(Diagram diagram) {
        diagram.addMouseListener(mouseHandler);
        diagram.addMouseMotionListener(mouseHandler);
        diagram.addFocusListener(focusHandler);
        javax.swing.ActionMap map = diagram.getActionMap();
        map.put("startEditing", actionStart);
        map.put("stopEditing", actionStop);
        map.put("cancelEditing", actionCancel);
    }

    /**
     * Uninstall the support from the specified Diagram
     *
     * @param Diagram
     */
    public void uninstall(Diagram diagram) {
        diagram.removeMouseListener(mouseHandler);
        diagram.removeMouseMotionListener(mouseHandler);
        diagram.removeFocusListener(focusHandler);
        javax.swing.ActionMap map = diagram.getActionMap();
        map.remove("startEditing");
        map.remove("stopEditing");
        map.remove("cancelEditing");
    }

    /**
     * Check if this support object is currently editing a diagram.
     *
     * @return boolean
     */
    protected boolean isEditing() {
        return editor != null;
    }

    /**
     *
     * @class MouseHandler

    Handle mouse actions that have an affect on editing.
     */
    protected class MouseHandler extends javax.swing.event.MouseInputAdapter {
        // Cached link array for checking link editor bounds
        private Link[] links = new Link[4];

        private final boolean shouldIgnore(java.awt.event.MouseEvent e) {
            return !((!e.isConsumed()) && javax.swing.SwingUtilities.isLeftMouseButton(e));
        }

        /**
         * Locate the correct component to recieve mouse events. This should be
         * the deepest (topmost) component beneath the MouseEvent.
         *
         * @param MouseEvent
         */
        private final void setDispatchComponent(java.awt.event.MouseEvent e) {
            java.awt.Point p = javax.swing.SwingUtilities.convertPoint(diagram, e.getPoint(), editorComponent);
            dispatchComponent = javax.swing.SwingUtilities.getDeepestComponentAt(editorComponent, p.x, p.y);
        }

        /**
         * Translate an event object and dispatch it to the current dispatch
         * component. In some cases this may not be the editing component but a
         * child within that component.
         *
         * @return boolean success
         */
        private final boolean repostEvent(java.awt.event.MouseEvent e) {
            if (dispatchComponent == null)
                return false;

            java.awt.event.MouseEvent e2 = javax.swing.SwingUtilities.convertMouseEvent(diagram, e, dispatchComponent);
            dispatchComponent.dispatchEvent(e2);
            return true;
        }

        /**
         * Check the location of the MouseEvent, if it falls on an editable Figure then
         * start editing. Otherwise, request the focus for the table.
         *
         * @param MouseEvent
         * 		e
         */
        public void mousePressed(java.awt.event.MouseEvent e) {
            // If no editing is going on and this event should be ignored skip looking for
            // figure beneath the mouse since it won't be used
            if ((!isEditing()) && shouldIgnore(e))
                return;

            // Check the figure beneath the mouse
            Diagram diagram = ((Diagram) (e.getSource()));
            Figure pressedFigure = findPressedFigure(diagram, e.getPoint());
            // If the press is outside the current figure, stop editing
            // the current figure
            if (isEditing() && (pressedFigure != figure))
                stopEditing(diagram, figure);

            // If editing is progress, or is started by this event then
            // forward it to the dispatch component
            if (isEditing() || startEditing(diagram, pressedFigure, e)) {
                setDispatchComponent(e);
                repostEvent(e);
                e.consume();
            }
        }

        /**
         * Translate the event and release the current dispatch component.
         *
         * @param MouseEvent
         */
        public void mouseReleased(java.awt.event.MouseEvent e) {
            if ((!isEditing()) || shouldIgnore(e))
                return;

            repostEvent(e);
            dispatchComponent = null;
        }

        /**
         * Translate the event.
         *
         * @param MouseEvent
         */
        public void mouseDragged(java.awt.event.MouseEvent e) {
            if ((!isEditing()) || shouldIgnore(e))
                return;

            repostEvent(e);
        }

        /**
         * Search for a Figure in the diagram at the given point that can be edited
         */
        protected Figure findPressedFigure(Diagram diagram, java.awt.geom.Point2D pt) {
            Figure pressedFigure = diagram.findFigure(pt);
            // If no figure was found by direct clicking, check link editor bounds
            // for label clicks
            if (pressedFigure == null) {
                // Request all links
                links = ((Link[]) (diagram.getModel().toArray(links)));
                for (int i = 0; (i < links.length) && (links[i] != null); i++) {
                    if (diagram.getModel().getValue(links[i]) == null)
                        continue;

                    // Check editor bounds
                    FigureEditor editor = diagram.getFigureEditor(links[i].getClass());
                    editorBounds = ((java.awt.Rectangle) (editor.getDecoratedBounds(diagram, links[i], editorBounds)));
                    if (editorBounds.contains(pt))
                        return links[i];

                }
            }
            return pressedFigure;
        }
    }

    /**
     *
     * @class FocusHandler

    Repaints the area where the Editing component is displayed whenever the focus
    on the containg diagram moves.
     */
    protected class FocusHandler extends java.awt.event.FocusAdapter {
        public void focusGained(java.awt.event.FocusEvent e) {
            repaintEditorCell(((Diagram) (e.getSource())));
        }

        public void focusLost(java.awt.event.FocusEvent e) {
            repaintEditorCell(((Diagram) (e.getSource())));
        }

        private final void repaintEditorCell(Diagram diagram) {
            diagram.paintImmediately(editorBounds);
        }
    }

    /**
     * Try to start editing a Figure
     *
     * @param Diagram
     * @param Figure
     * @param EventObject
     * @return boolean true if editing was successfully started.
     */
    protected boolean startEditing(Diagram diagram, Figure figure, java.util.EventObject e) {
        if ((figure != null) && (diagram != null)) {
            // Get the editor
            editor = diagram.getFigureEditor(figure.getClass());
            if ((editor == null) || (!editor.isCellEditable(e))) {
                editor = null;
                return false;
            }
            // Select the cell being edited
            SelectionModel selectionModel = diagram.getSelectionModel();
            if (editor.shouldSelectCell(e))
                selectionModel.add(figure, true);

            // Get the component for editing
            boolean isSelected = selectionModel.contains(figure);
            editorComponent = editor.getFigureEditorComponent(diagram, figure, isSelected);
            if (editorComponent == null)
                throw new java.lang.RuntimeException("Bad FigureEditor!");

            fireToolStarted();
            // Configure the editing component
            editorBounds = ((java.awt.Rectangle) (editor.getDecoratedBounds(diagram, figure, editorBounds)));
            editorComponent.setBounds(editorBounds.x, editorBounds.y, editorBounds.width, editorBounds.height);
            diagram.add(editorComponent);
            editorComponent.validate();
            editorComponent.setVisible(true);
            editorComponent.requestFocus();
            this.diagram = diagram;
            this.figure = figure;
            return true;
        }
        return false;
    }

    /**
     * Stop editing a certain figure
     */
    protected void stopEditing(Diagram diagram, Figure figure) {
        diagram.getModel().setValue(figure, editor.getCellEditorValue());
        editor.stopCellEditing();
        removeEditor();
        fireToolFinished();
    }

    /**
     * Remove the editing component
     *
     * @post Clears any reference to the editing components no longer
    in use.
     */
    protected void removeEditor() {
        editorBounds = ((java.awt.Rectangle) (editorComponent.getBounds(editorBounds)));
        diagram.remove(editorComponent);
        // Update the area where the editor was
        diagram.requestFocus();
        DiagramUI ui = ((DiagramUI) (diagram.getUI()));
        ui.damageFigure(figure);
        ui.repaintRegion(editorBounds);
        // Discard the unused references
        diagram = null;
        editor = null;
        figure = null;
        editorComponent = null;
    }

    /**
     * Action to start editing, and pass focus to the editor. If a single
     * Figure is selected, editing will begin on that figure.
     */
    private class StartEditingAction extends javax.swing.AbstractAction {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            Diagram diagram = ((Diagram) (e.getSource()));
            SelectionModel model = diagram.getSelectionModel();
            if (model.size() != 1)
                return;

            selected = ((Figure[]) (model.toArray(selected)));
            startEditing(diagram, selected[0], e);
        }
    }

    /**
     * Action to stop editing, and pass focus to the diagram.
     */
    private class StopEditingAction extends javax.swing.AbstractAction {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (!isEditing())
                return;

            editor.stopCellEditing();
            removeEditor();
        }
    }

    /**
     * Action to cancel editing, and pass focus to the diagram.
     */
    private class CancelEditingAction extends javax.swing.AbstractAction {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (!isEditing())
                return;

            editor.cancelCellEditing();
            removeEditor();
        }
    }
}