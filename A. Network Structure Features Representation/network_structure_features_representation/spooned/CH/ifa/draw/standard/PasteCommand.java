/* @(#)PasteCommand.java 5.1 */
package CH.ifa.draw.standard;
/**
 * Command to insert the clipboard into the drawing.
 *
 * @see Clipboard
 */
public class PasteCommand extends CH.ifa.draw.standard.FigureTransferCommand {
    /**
     * Constructs a paste command.
     *
     * @param name
     * 		the command name
     * @param image
     * 		the pathname of the image
     * @param view
     * 		the target view
     */
    public PasteCommand(java.lang.String name, CH.ifa.draw.framework.DrawingView view) {
        super(name, view);
    }

    public void execute() {
        java.awt.Point lastClick = fView.lastClick();
        CH.ifa.draw.framework.FigureSelection selection = ((CH.ifa.draw.framework.FigureSelection) (CH.ifa.draw.util.Clipboard.getClipboard().getContents()));
        if (selection != null) {
            java.util.Vector figures = ((java.util.Vector) (selection.getData(CH.ifa.draw.framework.FigureSelection.TYPE)));
            if (figures.size() == 0)
                return;

            java.awt.Rectangle r = bounds(figures.elements());
            fView.clearSelection();
            insertFigures(figures, lastClick.x - r.x, lastClick.y - r.y);
            fView.checkDamage();
        }
    }

    java.awt.Rectangle bounds(java.util.Enumeration k) {
        java.awt.Rectangle r = ((CH.ifa.draw.framework.Figure) (k.nextElement())).displayBox();
        while (k.hasMoreElements())
            r.add(((CH.ifa.draw.framework.Figure) (k.nextElement())).displayBox());

        return r;
    }
}