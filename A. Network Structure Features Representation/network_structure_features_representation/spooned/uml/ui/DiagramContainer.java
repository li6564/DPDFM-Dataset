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
/**
 *
 * @class DiagramContainer
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class DiagramContainer extends javax.swing.JScrollPane {
    protected javax.swing.Action saveAction = new uml.ui.DiagramContainer.SaveAction();

    protected javax.swing.Action closeAction = new uml.ui.DiagramContainer.CloseAction();

    protected javax.swing.Action printAction = new uml.ui.DiagramContainer.PrintAction();

    protected javax.swing.Action scaledPrintAction = new uml.ui.DiagramContainer.ScaledPrintAction();

    protected javax.swing.Action exportAction = new uml.ui.DiagramContainer.ExportGIFAction();

    protected javax.swing.Action resizeAction = new uml.ui.DiagramContainer.ResizeAction();

    // Placeholders for the menu, the real actions are install on the diagram
    // when ever one is place in the container by the diagram ui
    protected javax.swing.Action copyAction = new uml.ui.DiagramContainer.CopyAction();

    protected javax.swing.Action cutAction = new uml.ui.DiagramContainer.CutAction();

    protected javax.swing.Action pasteAction = new uml.ui.DiagramContainer.PasteAction();

    protected java.awt.Dimension defaultSize;

    // Color editor data
    private static final java.lang.String[] colorProperties = new java.lang.String[]{ "composition.foreground", "composition.background", "class.foreground", "class.background", "association.foreground", "association.background", "dependency.foreground", "dependency.background", "diagram.foreground", "diagram.background", "generalization.foreground", "generalization.background", "interface.foreground", "interface.background", "note.foreground", "note.background", "realization.foreground", "realization.background" };

    // Font editor data
    private static final java.lang.String[] fontProperties = new java.lang.String[]{ "composition.font", "class.font", "association.font", "diagram.font", "generalization.font", "interface.font", "note.font", "dependency.font", "realization.font" };

    /**
     * Create a new Container for a diagram
     */
    public DiagramContainer() {
        super(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        setView(createDiagram());
    }

    /**
     * Update the menu bar. Add toggle option, etc.
     *
     * @param FlatMenuBar
     */
    public void updateMenus(uml.ui.FlatMenuBar menuBar) {
        javax.swing.JMenu menu = menuBar.getMenu("File");
        menu.add(new uml.ui.DiagramContainer.NewAction());
        menu.add(new uml.ui.DiagramContainer.OpenAction());
        menu.add(closeAction);
        menu.add(saveAction);
        menu.addSeparator();
        menu.add(exportAction);
        menu.addSeparator();
        menu.add(printAction);
        menu.add(scaledPrintAction);
        menu = menuBar.getMenu("Edit");
        menu.add(copyAction);
        menu.add(cutAction);
        menu.add(pasteAction);
        menu = menuBar.getMenu("Options");
        menu.add(resizeAction);
        menu.add(new uml.ui.FontAction(this, uml.ui.DiagramContainer.fontProperties));
        menu.add(new uml.ui.ColorAction(this, uml.ui.DiagramContainer.colorProperties));
        menu.add(new javax.swing.JSeparator(), -1);
        javax.swing.JCheckBoxMenuItem item = new javax.swing.JCheckBoxMenuItem(new uml.ui.DiagramContainer.ToggleRefreshAction());
        item.setState(true);
        menu.add(item, -1);
        menu = menuBar.getMenu("Tool");
        menu.add(new uml.ui.BuildAction(this));
    }

    /**
     * Create a new diagram
     */
    public diagram.Diagram createDiagram() {
        Diagram diagram = new Diagram();
        // Install Link renderers
        diagram.setFigureRenderer(uml.diagram.CompositionLink.class, new uml.diagram.CompositionLinkRenderer());
        diagram.setFigureRenderer(uml.diagram.AssociationLink.class, new uml.diagram.AssociationLinkRenderer());
        diagram.setFigureRenderer(uml.diagram.GeneralizationLink.class, new uml.diagram.GeneralizationLinkRenderer());
        diagram.setFigureRenderer(uml.diagram.RealizationLink.class, new uml.diagram.RealizationLinkRenderer());
        diagram.setFigureRenderer(uml.diagram.DependencyLink.class, new uml.diagram.DependencyLinkRenderer());
        // Install Figure renderers
        diagram.setFigureRenderer(uml.diagram.ClassFigure.class, new uml.diagram.ClassRenderer());
        diagram.setFigureRenderer(uml.diagram.InterfaceFigure.class, new uml.diagram.InterfaceRenderer());
        diagram.setFigureRenderer(uml.diagram.NoteFigure.class, new uml.diagram.NoteRenderer());
        // Install Figure editors
        diagram.setFigureEditor(uml.diagram.NoteFigure.class, new uml.diagram.NoteEditor());
        diagram.setFigureEditor(uml.diagram.ClassFigure.class, new uml.diagram.ClassEditor());
        diagram.setFigureEditor(uml.diagram.InterfaceFigure.class, new uml.diagram.InterfaceEditor());
        // Install Link editors
        diagram.setFigureEditor(uml.diagram.CompositionLink.class, new uml.diagram.CompositionLinkEditor());
        diagram.setFigureEditor(uml.diagram.GeneralizationLink.class, new uml.diagram.GeneralizationLinkEditor());
        diagram.setFigureEditor(uml.diagram.RealizationLink.class, new uml.diagram.RealizationLinkEditor());
        diagram.setFigureEditor(uml.diagram.DependencyLink.class, new uml.diagram.DependencyLinkEditor());
        diagram.setFigureEditor(uml.diagram.AssociationLink.class, new uml.diagram.AssociationLinkEditor());
        if (defaultSize != null)
            resizeDiagram(diagram, defaultSize);

        return diagram;
    }

    /**
     * Get the diagram for this view.
     *
     * @return Diagram
     */
    public diagram.Diagram getView() {
        return ((diagram.Diagram) (getViewport().getView()));
    }

    /**
     * Set the diagram for this view.
     *
     * @param Diagram
     */
    public void setView(Diagram diagram) {
        Diagram oldDiagram = getView();
        setViewportView(diagram);
        if (diagram == null) {
            // Disable actions that need a diagram
            closeAction.setEnabled(false);
            saveAction.setEnabled(false);
            exportAction.setEnabled(false);
            printAction.setEnabled(false);
            scaledPrintAction.setEnabled(false);
            resizeAction.setEnabled(false);
            copyAction.setEnabled(false);
            cutAction.setEnabled(false);
            pasteAction.setEnabled(false);
        } else if (oldDiagram == null) {
            // Enable actions that need a diagram
            closeAction.setEnabled(true);
            saveAction.setEnabled(true);
            exportAction.setEnabled(true);
            printAction.setEnabled(true);
            scaledPrintAction.setEnabled(true);
            resizeAction.setEnabled(true);
            copyAction.setEnabled(true);
            cutAction.setEnabled(true);
            pasteAction.setEnabled(true);
        }
        super.firePropertyChange("diagram.container", oldDiagram, diagram);
    }

    /**
     * Find the Frame for this event
     */
    protected java.awt.Component getFrame(java.awt.event.ActionEvent e) {
        return getFrame(((java.awt.Component) (e.getSource())));
    }

    protected java.awt.Frame getFrame(java.awt.Component frame) {
        for (; !(frame instanceof java.awt.Frame); frame = frame.getParent())
            if (frame instanceof javax.swing.JPopupMenu)
                frame = ((javax.swing.JPopupMenu) (frame)).getInvoker();


        return frame instanceof java.awt.Frame ? ((java.awt.Frame) (frame)) : null;
    }

    public java.awt.Frame getFrame() {
        return getFrame(this);
    }

    /**
     * Load an Icon with the IconManager
     */
    protected javax.swing.Icon getIcon(java.lang.String name) {
        return uml.ui.IconManager.getInstance().getIconResource(this, name);
    }

    /**
     * Popup an error message
     */
    protected void displayError(java.lang.Throwable t) {
        t.printStackTrace();
        displayError(t.getClass().getName(), t.getMessage());
    }

    /**
     * Popup an error message
     */
    protected void displayError(java.lang.String title, java.lang.String msg) {
        javax.swing.JOptionPane.showMessageDialog(this, msg, title, javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Resize & update the diagram
     */
    protected void resizeDiagram(Diagram diagram, java.awt.Dimension d) {
        diagram.setMinimumSize(d);
        diagram.setPreferredSize(d);
        diagram.setBounds(0, 0, d.width, d.height);
        doLayout();
    }

    /**
     *
     * @class NewAction
     */
    protected class NewAction extends javax.swing.AbstractAction {
        public NewAction() {
            super("New", getIcon("images/New.gif"));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            setView(createDiagram());
        }
    }

    /**
     *
     * @class CloseAction
     */
    protected class CloseAction extends javax.swing.AbstractAction {
        public CloseAction() {
            super("Close", getIcon("images/Close.gif"));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            setView(null);
        }
    }

    /**
     *
     * @class OpenAction
     */
    protected class OpenAction extends uml.ui.FileAction {
        private uml.ui.FileAction.SimpleFilter filter = new uml.ui.FileAction.SimpleFilter("dia", "Diagrams");

        public OpenAction() {
            super("Open", getIcon("images/Open.gif"));
            putValue(javax.swing.Action.ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke('O', java.awt.Event.CTRL_MASK));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // Configure the chooser for the open request
            javax.swing.JFileChooser chooser = getChooser();
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileFilter(filter);
            if (javax.swing.JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(getFrame(e)))
                openFile(chooser.getSelectedFile());

        }

        public void openFile(java.io.File file) {
            try {
                // Check that the file name matches
                java.lang.String name = file.getName().toLowerCase();
                if (!name.endsWith(".dia"))
                    throw new java.lang.RuntimeException("Not a valid diagram file extension");

                java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.FileInputStream(file));
                // Get the current view or create one
                Diagram diagram = getView();
                if (diagram == null)
                    setView(diagram = createDiagram());

                diagram.setModel(((DiagramModel) (ois.readObject())));
                diagram.repaint();
            } catch (java.lang.Throwable t) {
                t.printStackTrace();
                displayError("File Error", "Invalid diagram file");
            }
        }
    }

    /**
     *
     * @class SaveAction
     */
    protected class SaveAction extends uml.ui.FileAction {
        private uml.ui.FileAction.SimpleFilter filter = new uml.ui.FileAction.SimpleFilter("dia", "Diagrams");

        public SaveAction() {
            super("Save", getIcon("images/Save.gif"));
            putValue(javax.swing.Action.ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke('S', java.awt.Event.CTRL_MASK));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // Configure the chooser for the save request
            javax.swing.JFileChooser chooser = getChooser();
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileFilter(filter);
            if (javax.swing.JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(getFrame(e)))
                saveFile(chooser.getSelectedFile());

        }

        public void saveFile(java.io.File file) {
            // Adjust the file name to match
            java.lang.String name = file.getName().toLowerCase();
            if (!name.endsWith(".dia"))
                file = new java.io.File(file.getName() + ".dia");

            try {
                java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream(file));
                oos.writeObject(((diagram.DiagramModel) (getView().getModel())));
            } catch (java.lang.Throwable t) {
                t.printStackTrace();
                displayError("File Error", "Error writing to file");
            }
        }
    }

    /**
     *
     * @class ExportAction
     */
    protected class ExportGIFAction extends uml.ui.ExportAction {
        private uml.ui.FileAction.SimpleFilter filter = new uml.ui.FileAction.SimpleFilter("gif", "Images");

        public ExportGIFAction() {
            super("Save Image", getIcon("images/ExportImage.gif"));
        }

        protected java.awt.Component getComponent() {
            return getView();
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // Configure the chooser for the save request
            javax.swing.JFileChooser chooser = getChooser();
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileFilter(filter);
            if (javax.swing.JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(getFrame(e))) {
                try {
                    writeGIF(chooser.getSelectedFile());
                } catch (java.lang.Throwable t) {
                    displayError(t);
                }
            }
        }
    }

    /**
     *
     * @class PrintAction
     */
    protected class PrintAction extends uml.ui.PrintableAction {
        public PrintAction() {
            super("Print ...");
        }

        public java.awt.Component getComponent() {
            return getView();
        }
    }

    /**
     *
     * @class ScaledPrintAction
     */
    protected class ScaledPrintAction extends uml.ui.ScaledPrintableAction {
        public ScaledPrintAction() {
            super("Scaled Print", getIcon("images/Print.gif"));
        }

        public java.awt.Component getComponent() {
            return getView();
        }
    }

    /**
     *
     * @class CopyAction
     */
    protected class CopyAction extends javax.swing.AbstractAction {
        public CopyAction() {
            super("Copy", getIcon("images/Copy.gif"));
            putValue(javax.swing.Action.ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke('C', java.awt.Event.CTRL_MASK));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            Diagram diagram = getView();
            if (diagram != null) {
                javax.swing.Action action = diagram.getActionMap().get("copy");
                if (action != null)
                    action.actionPerformed(e);

            }
        }
    }

    /**
     *
     * @class CutAction
     */
    protected class CutAction extends javax.swing.AbstractAction {
        public CutAction() {
            super("Cut", getIcon("images/Cut.gif"));
            putValue(javax.swing.Action.ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke('X', java.awt.Event.CTRL_MASK));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            Diagram diagram = getView();
            if (diagram != null) {
                javax.swing.Action action = diagram.getActionMap().get("cut");
                if (action != null)
                    action.actionPerformed(e);

            }
        }
    }

    /**
     *
     * @class PasteAction
     */
    protected class PasteAction extends javax.swing.AbstractAction {
        public PasteAction() {
            super("Paste", getIcon("images/Paste.gif"));
            putValue(javax.swing.Action.ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke('V', java.awt.Event.CTRL_MASK));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            Diagram diagram = getView();
            if (diagram != null) {
                javax.swing.Action action = diagram.getActionMap().get("paste");
                if (action != null)
                    action.actionPerformed(e);

            }
        }
    }

    /**
     *
     * @class ResizeAction
     */
    protected class ResizeAction extends javax.swing.AbstractAction {
        public ResizeAction() {
            super("Resize ...");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            Diagram diagram = getView();
            if (diagram != null)
                promptResize(diagram);

        }

        protected void promptResize(Diagram diagram) {
            uml.ui.SizePanel size = new uml.ui.SizePanel(diagram);
            int n = javax.swing.JOptionPane.showConfirmDialog(DiagramContainer.this, size, "Resize Diagram", javax.swing.JOptionPane.OK_CANCEL_OPTION);
            if (n == javax.swing.JOptionPane.OK_OPTION) {
                defaultSize = size.getDimension(defaultSize);
                resizeDiagram(diagram, defaultSize);
            }
        }
    }

    /**
     *
     * @class ToggleRefreshAction
     */
    protected class ToggleRefreshAction extends javax.swing.AbstractAction {
        public ToggleRefreshAction() {
            super("Fast refresh");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            Diagram diagram = getView();
            if (diagram != null) {
                boolean toggle = !diagram.isFastRefreshEnabled();
                diagram.enableFastRefresh(toggle);
            }
        }
    }
}