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
import diagram.DiagramModel;
import diagram.DiagramUI;
import diagram.Figure;
import diagram.Link;
import diagram.SelectionModel;
/**
 *
 * @class ClipboardTool
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

A Clipboard implements the copy/cut/paste actions for a Diagram.
A set of actions are inserted into the target Diagrams ActionMap
(copy, cut, and paste) that allow the UI to map inputs to these
actions as it sees fit.
 */
public class ClipboardTool extends diagram.tool.AbstractTool {
    // This implementation shares a single instance of a clipboard across all
    // threads and uses a ThreadLocal to different between requests
    protected static java.lang.ThreadLocal diagramKey = new java.lang.InheritableThreadLocal();

    protected javax.swing.Action actionCut = new diagram.tool.ClipboardTool.CutAction();

    protected javax.swing.Action actionCopy = new diagram.tool.ClipboardTool.CopyAction();

    protected javax.swing.Action actionPaste = new diagram.tool.ClipboardTool.PasteAction();

    private diagram.Figure[] figures = new diagram.Figure[4];

    private diagram.Figure[] related = new diagram.Figure[4];

    private java.util.ArrayList clipboard = new java.util.ArrayList();

    private java.util.ArrayList copyboard = new java.util.ArrayList();

    /**
     * Install the support in the specified Diagram
     *
     * @param Diagram
     */
    public void install(Diagram diagram) {
        javax.swing.ActionMap map = diagram.getActionMap();
        map.put("cut", actionCut);
        map.put("copy", actionCopy);
        map.put("paste", actionPaste);
        ClipboardTool.setDiagram(diagram);
    }

    /**
     * Install the support in the specified Diagram
     *
     * @param Diagram
     */
    public void uninstall(Diagram diagram) {
        javax.swing.ActionMap map = diagram.getActionMap();
        map.remove("cut");
        map.remove("copy");
        map.remove("paste");
        ClipboardTool.setDiagram(null);
    }

    /**
     * Configure the current context for the given Diagram
     *
     * @param Diagram
     * @return Clipboard
     */
    public static void setDiagram(Diagram diagram) {
        if (ClipboardTool.getDiagram() != diagram)
            ClipboardTool.diagramKey.set(new java.lang.ref.WeakReference(diagram));

    }

    /**
     * Get the diagram the Clipboard is configured for
     *
     * @return Diagram
     */
    public static diagram.Diagram getDiagram() {
        java.lang.Object o = diagram.tool.ClipboardTool.diagramKey.get();
        if (o != null)
            o = ((java.lang.ref.WeakReference) (o)).get();

        return ((diagram.Diagram) (o));
    }

    /**
     * Prepare for a clipboard operation. Copy the current selection into a local array
     * for processing.
     *
     * @return boolean
     */
    protected boolean prepareSelection() {
        // Get the correct diagram
        Diagram diagram = ClipboardTool.getDiagram();
        if (diagram == null)
            return false;

        // Get the selected figures
        SelectionModel selectionModel = diagram.getSelectionModel();
        if (selectionModel == null)
            return false;

        figures = ((Figure[]) (selectionModel.toArray(figures)));
        return true;
    }

    /**
     * Copy the current selection from the Diagram to the Clipboard
     */
    public void doCopy() {
        if (!prepareSelection())
            return;

        synchronized(figures) {
            // Clone the copied figures into a Vector for later processing
            clipboard.clear();
            for (int i = 0; (i < figures.length) && (figures[i] != null); i++) {
                diagram.Figure figure = figures[i];
                clipboard.add(figure);
            }
        }
    }

    /**
     * Cut the current selection from the Diagram
     */
    public void doCut() {
        if (!prepareSelection())
            return;

        diagram.DiagramModel model = diagram.tool.ClipboardTool.getDiagram().getModel();
        diagram.DiagramUI ui = ((diagram.DiagramUI) (diagram.tool.ClipboardTool.getDiagram().getUI()));
        // Walk through the selected items
        diagram.Figure figure = null;
        for (int i = 0; (i < figures.length) && (figures[i] != null); i++) {
            figure = figures[i];
            // Cut related items first (usually links)
            related = ui.getConnected(figure, related);
            for (int j = 0; (j < related.length) && (related[j] != null); j++) {
                model.remove(related[j]);
                ui.damageFigure(related[j]);
            }
            // Cut the selected item
            model.remove(figure);
            ui.damageFigure(figure);
        }
        // Refresh
        ui.refreshFigure(figure);
    }

    /**
     * Paste the top of the clipboard stack to the diagram
     */
    public void doPaste() {
        // Get the correct diagram
        Diagram diagram = ClipboardTool.getDiagram();
        if (diagram == null)
            return;

        // Get the selected figures
        SelectionModel selectionModel = diagram.getSelectionModel();
        if (selectionModel == null)
            return;

        if (clipboard.isEmpty())
            return;

        DiagramModel model = ClipboardTool.getDiagram().getModel();
        DiagramUI ui = ((DiagramUI) (ClipboardTool.getDiagram().getUI()));
        Figure figure = null;
        // Paste all link figures next, only if both the sink & source for the
        // link were also pasted
        copyboard.addAll(clipboard);
        clipboard.clear();
        // Clear current selection, add the pasted figures
        selectionModel.clear();
        // Copy all linked items
        for (int i = 0; i < copyboard.size(); i++) {
            // Skip nulls (from being pasted w/ thier links)
            Figure f = ((Figure) (copyboard.get(i)));
            if (f == null)
                continue;

            // Rember last good figure for refresh at the end
            figure = f;
            ui.damageFigure(figure);
            if (figure instanceof Link) {
                Link link = ((Link) (figure));
                int index;
                // Check the sink
                Figure sink = link.getSink();
                if ((sink != null) && ((index = copyboard.indexOf(sink)) != (-1))) {
                    sink = ((Figure) (sink.clone()));
                    // Paste the sink
                    doPaste(sink, model, selectionModel, ui);
                    copyboard.set(index, null);
                    // Check the source
                    Figure source = link.getSource();
                    if ((source != null) && ((index = copyboard.indexOf(source)) != (-1))) {
                        source = ((Figure) (source.clone()));
                        // Paste the source
                        doPaste(source, model, selectionModel, ui);
                        copyboard.set(index, null);
                        // Clone the link & paste it
                        java.lang.System.err.println(link.equals(link.clone()));
                        link = ((Link) (link.clone()));
                        link.setSink(sink);
                        link.setSource(source);
                        doPaste(link, model, selectionModel, ui);
                        copyboard.set(i, null);
                    }
                }
            }
        }
        // Copy all non-linked items
        for (int i = 0; i < copyboard.size(); i++) {
            // Skip nulls (from being pasted w/ thier links)
            Figure f = ((Figure) (copyboard.get(i)));
            if (f != null) {
                ui.damageFigure(f);
                figure = ((Figure) (f.clone()));
                doPaste(figure, model, selectionModel, ui);
            }
        }
        copyboard.clear();
        // Refresh diagram
        ui.refreshFigure(figure);
    }

    /**
     */
    private final void doPaste(diagram.Figure figure, diagram.DiagramModel model, diagram.SelectionModel selectionModel, diagram.DiagramUI ui) {
        figure.translate(10, 10);
        model.add(figure);
        selectionModel.add(figure);
        clipboard.add(figure);
        ui.damageFigure(figure);
    }

    /**
     *
     * @class CutAction
     */
    protected class CutAction extends javax.swing.AbstractAction {
        public CutAction() {
            super("Cut");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            doCut();
        }
    }

    /**
     *
     * @class CopyAction
     */
    protected class CopyAction extends javax.swing.AbstractAction {
        public CopyAction() {
            super("Copy");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            doCopy();
        }
    }

    /**
     *
     * @class PasteAction
     */
    protected class PasteAction extends javax.swing.AbstractAction {
        public PasteAction() {
            super("Paste");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            doPaste();
        }
    }
}