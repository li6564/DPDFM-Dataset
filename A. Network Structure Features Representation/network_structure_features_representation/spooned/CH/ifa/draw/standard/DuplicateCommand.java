/* @(#)DuplicateCommand.java 5.1 */
package CH.ifa.draw.standard;
/**
 * Duplicate the selection and select the duplicates.
 */
public class DuplicateCommand extends CH.ifa.draw.standard.FigureTransferCommand {
    /**
     * Constructs a duplicate command.
     *
     * @param name
     * 		the command name
     * @param view
     * 		the target view
     */
    public DuplicateCommand(java.lang.String name, CH.ifa.draw.framework.DrawingView view) {
        super(name, view);
    }

    public void execute() {
        CH.ifa.draw.framework.FigureSelection selection = fView.getFigureSelection();
        fView.clearSelection();
        java.util.Vector figures = ((java.util.Vector) (selection.getData(CH.ifa.draw.framework.FigureSelection.TYPE)));
        insertFigures(figures, 10, 10);
        fView.checkDamage();
    }

    public boolean isExecutable() {
        return fView.selectionCount() > 0;
    }
}