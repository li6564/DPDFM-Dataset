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
import diagram.Diagram;
import diagram.DiagramModel;
import diagram.DiagramUI;
import diagram.Figure;
import diagram.tool.AbstractTool;
import diagram.tool.LinkShappingTool;
import java.awt.event.MouseEvent;
/**
 *
 * @class CardinalityTool
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class CardinalityTool extends AbstractTool {
    protected uml.ui.CardinalityTool.MouseHandler mouseHandler = new uml.ui.CardinalityTool.MouseHandler();

    protected uml.ui.CardinalityTool.Popup popup = new uml.ui.CardinalityTool.Popup();

    protected uml.diagram.CompositionItem item;

    protected uml.diagram.CompositionLink link;

    protected Diagram diagram;

    public void install(Diagram diagram) {
        diagram.addMouseListener(mouseHandler);
    }

    public void uninstall(Diagram diagram) {
        diagram.removeMouseListener(mouseHandler);
        reset();
    }

    /**
     *
     * @class MouseHandler
     */
    protected class MouseHandler extends java.awt.event.MouseAdapter {
        /**
         * Called when the mouse is clicked.
         *
         * @param MouseEvent
         */
        public void mouseClicked(java.awt.event.MouseEvent e) {
            // If the right button was used & nothing else has consumed the event
            // try to join two segments
            if ((!e.isConsumed()) && javax.swing.SwingUtilities.isRightMouseButton(e)) {
                diagram = ((Diagram) (e.getSource()));
                java.awt.Point pt = e.getPoint();
                Figure figure = diagram.findFigure(pt);
                // Find a link
                if (!(figure instanceof uml.diagram.CompositionLink)) {
                    reset();
                    return;
                }
                DiagramModel model = diagram.getModel();
                if (model == null) {
                    reset();
                    return;
                }
                link = ((uml.diagram.CompositionLink) (figure));
                // Don't activate if the linkshapping tool should really handle this right click
                if (link.pointFor(pt.x, pt.y, LinkShappingTool.CLICK_TOLERANCE * 2.0) != (-1)) {
                    reset();
                    return;
                }
                e.consume();
                fireToolStarted();
                // Get the value of the item
                item = ((uml.diagram.CompositionItem) (model.getValue(link)));
                if (item == null) {
                    item = new uml.diagram.CompositionItem();
                    model.setValue(link, item);
                }
                startEditing(pt);
            }
        }
    }

    protected void startEditing(java.awt.Point pt) {
        // Popup cardinality menu
        popup.show(pt);
    }

    protected void stopEditing(java.lang.String n) {
        item.setCardinality(n);
        DiagramUI ui = ((DiagramUI) (diagram.getUI()));
        ui.refreshFigure(link);
    }

    /**
     *
     * @class Popup
     */
    protected class Popup extends javax.swing.JPopupMenu {
        protected javax.swing.JTextField text = new javax.swing.JTextField();

        protected int n;

        public Popup() {
            super("Cardinality");
            javax.swing.JLabel lbl = new javax.swing.JLabel("Cardinality");
            lbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
            add(lbl);
            add(text);
            lbl.setFont(getFont().deriveFont(java.awt.Font.PLAIN, getFont().getSize() - 1));
            javax.swing.JPopupMenu.setDefaultLightWeightPopupEnabled(true);
            javax.swing.border.Border border = javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black), javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
            border = javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), border);
            text.setBorder(border);
        }

        public void show(java.awt.Point pt) {
            text.setText(item.getCardinality());
            super.show(diagram, pt.x, pt.y);
        }

        protected void firePopupMenuWillBecomeInvisible() {
            try {
                stopEditing(text.getText());
            } catch (java.lang.Throwable t) {
            }
            fireToolFinished();
            reset();
        }
    }

    protected void reset() {
        diagram = null;
        item = null;
        link = null;
    }
}