/* @(#)UngroupCommand.java 5.1 */
package CH.ifa.draw.figures;
/**
 * Command to ungroup the selected figures.
 *
 * @see GroupCommand
 */
public class UngroupCommand extends CH.ifa.draw.util.Command {
    private CH.ifa.draw.framework.DrawingView fView;

    /**
     * Constructs a group command.
     *
     * @param name
     * 		the command name
     * @param view
     * 		the target view
     */
    public UngroupCommand(java.lang.String name, CH.ifa.draw.framework.DrawingView view) {
        super(name);
        fView = view;
    }

    public void execute() {
        CH.ifa.draw.framework.FigureEnumeration selection = fView.selectionElements();
        fView.clearSelection();
        java.util.Vector parts = new java.util.Vector();
        while (selection.hasMoreElements()) {
            CH.ifa.draw.framework.Figure selected = selection.nextFigure();
            CH.ifa.draw.framework.Figure group = fView.drawing().orphan(selected);
            CH.ifa.draw.framework.FigureEnumeration k = group.decompose();
            while (k.hasMoreElements())
                fView.addToSelection(fView.add(k.nextFigure()));

        } 
        fView.checkDamage();
    }

    public boolean isExecutable() {
        return fView.selectionCount() > 0;
    }
}