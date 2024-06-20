/* @(#)FigureTransferCommand.java 5.1 */
package CH.ifa.draw.standard;
/**
 * Common base clase for commands that transfer figures
 * between a drawing and the clipboard.
 */
abstract class FigureTransferCommand extends CH.ifa.draw.util.Command {
    protected CH.ifa.draw.framework.DrawingView fView;

    /**
     * Constructs a drawing command.
     *
     * @param name
     * 		the command name
     * @param view
     * 		the target view
     */
    protected FigureTransferCommand(java.lang.String name, CH.ifa.draw.framework.DrawingView view) {
        super(name);
        fView = view;
    }

    /**
     * Deletes the selection from the drawing.
     */
    protected void deleteSelection() {
        fView.drawing().removeAll(fView.selection());
        fView.clearSelection();
    }

    /**
     * Copies the selection to the clipboard.
     */
    protected void copySelection() {
        CH.ifa.draw.framework.FigureSelection selection = fView.getFigureSelection();
        CH.ifa.draw.util.Clipboard.getClipboard().setContents(selection);
    }

    /**
     * Inserts a vector of figures and translates them by the
     * given offset.
     */
    protected void insertFigures(java.util.Vector figures, int dx, int dy) {
        CH.ifa.draw.framework.FigureEnumeration e = new CH.ifa.draw.standard.FigureEnumerator(figures);
        while (e.hasMoreElements()) {
            CH.ifa.draw.framework.Figure figure = e.nextFigure();
            figure.moveBy(dx, dy);
            figure = fView.add(figure);
            fView.addToSelection(figure);
        } 
    }
}