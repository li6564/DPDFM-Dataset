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
import diagram.tool.Tool;
/**
 *
 * @class ToolPalette
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class ToolPalette extends javax.swing.JToolBar implements java.beans.PropertyChangeListener , diagram.tool.ToolListener {
    private uml.ui.ToolPalette.ToolButton pointerButton;

    private javax.swing.ButtonGroup buttonGroup = new javax.swing.ButtonGroup();

    private uml.ui.DiagramContainer container;

    private diagram.tool.Tool currentTool;

    private boolean revertPointer = true;

    /**
     * Create a new MenuBar
     */
    public ToolPalette(uml.ui.DiagramContainer container) {
        super("Drawing Palette", javax.swing.SwingConstants.VERTICAL);
        setFloatable(false);
        addSeparator();
        diagram.tool.CompositeTool arrowTool = new diagram.tool.CompositeTool();
        arrowTool.add(new diagram.tool.SelectionTool());
        arrowTool.add(new diagram.tool.FigureDraggingTool());
        arrowTool.add(new diagram.tool.EditingTool());
        arrowTool.add(new diagram.tool.FigureShappingTool());
        arrowTool.add(new diagram.tool.LinkShappingTool());
        arrowTool.add(new diagram.tool.ClipboardTool());
        arrowTool.add(new uml.ui.CardinalityTool());
        pointerButton = createButton(arrowTool, "images/Arrow.gif", "Pointer");
        add(pointerButton);
        addSeparator();
        diagram.tool.Tool tool = new diagram.tool.FigureTool(new uml.diagram.ClassFigure());
        add(createButton(tool, "images/Class.gif", "Class"));
        tool = new diagram.tool.FigureTool(new uml.diagram.InterfaceFigure());
        add(createButton(tool, "images/Interface.gif", "Interface"));
        tool = new diagram.tool.FigureTool(new uml.diagram.NoteFigure());
        add(createButton(tool, "images/Note.gif", "Note"));
        addSeparator();
        tool = new uml.ui.GeneralizationTool();
        add(createButton(tool, "images/Generalization.gif", "Generalization"));
        tool = new uml.ui.RealizationTool();
        add(createButton(tool, "images/Realization.gif", "Realization"));
        addSeparator();
        tool = new uml.ui.CompositionTool();
        add(createButton(tool, "images/Composition.gif", "Composition"));
        tool = new uml.ui.AssociationTool();
        add(createButton(tool, "images/Association.gif", "Association"));
        tool = new uml.ui.DependencyTool();
        add(createButton(tool, "images/Dependency.gif", "Dependency"));
        container.addPropertyChangeListener(this);
        this.container = container;
        pointerButton.doClick();
    }

    /**
     * Add a button to the palette
     */
    protected uml.ui.ToolPalette.ToolButton createButton(diagram.tool.Tool tool, java.lang.String iconResource, java.lang.String toolTip) {
        return createButton(tool, uml.ui.IconManager.getInstance().getIconResource(this, iconResource), toolTip);
    }

    /**
     * Add a button to the palette
     */
    protected uml.ui.ToolPalette.ToolButton createButton(diagram.tool.Tool tool, javax.swing.Icon icon, java.lang.String toolTip) {
        uml.ui.ToolPalette.ToolButton button = new uml.ui.ToolPalette.ToolButton(tool, icon, toolTip);
        tool.addToolListener(this);
        return button;
    }

    /**
     * Update the menu bar. Add toggle option, etc.
     *
     * @param FlatMenuBar
     */
    public void updateMenus(uml.ui.FlatMenuBar menuBar) {
        // Append the option to the end of the Options menu
        javax.swing.JMenu menu = menuBar.getMenu("Options");
        menu.add(new javax.swing.JSeparator(), -1);
        javax.swing.JCheckBoxMenuItem item = new javax.swing.JCheckBoxMenuItem(new uml.ui.ToolPalette.ToggleRevertAction());
        item.setState(revertPointer);
        menu.add(item, -1);
    }

    /**
     * Listen for the property to changes
     */
    public void propertyChange(java.beans.PropertyChangeEvent e) {
        if (e.getPropertyName().equals("diagram.container")) {
            Diagram diagram = ((Diagram) (e.getNewValue()));
            Diagram oldDiagram = ((Diagram) (e.getOldValue()));
            if (currentTool != null) {
                if (oldDiagram != null)
                    currentTool.uninstall(oldDiagram);

                if (diagram != null)
                    currentTool.install(diagram);

            }
        }
    }

    /**
     * Called when a tool has reacted to an event and has started doing its job
     */
    public void toolStarted(diagram.tool.Tool tool) {
    }

    /**
     * Called when a tool has completed its work
     */
    public void toolFinished(diagram.tool.Tool tool) {
        if (revertPointer && (pointerButton.getTool() != tool))
            pointerButton.doClick();

    }

    /**
     *
     * @class ToolButton
     */
    protected class ToolButton extends javax.swing.JToggleButton {
        protected diagram.tool.Tool tool;

        public ToolButton(diagram.tool.Tool tool, javax.swing.Icon icon, java.lang.String toolTip) {
            super(icon);
            this.setToolTipText(toolTip);
            this.tool = tool;
            buttonGroup.add(this);
        }

        public diagram.tool.Tool getTool() {
            return tool;
        }

        protected void fireActionPerformed(java.awt.event.ActionEvent e) {
            Diagram diagram = container.getView();
            if (diagram != null) {
                if (currentTool != null)
                    currentTool.uninstall(diagram);

                tool.install(diagram);
                currentTool = tool;
            }
            super.fireActionPerformed(e);
        }
    }

    /**
     *
     * @class ToggleRevertAction
     */
    protected class ToggleRevertAction extends javax.swing.AbstractAction {
        public ToggleRevertAction() {
            super("Revert to pointer");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            revertPointer = !revertPointer;
        }
    }
}