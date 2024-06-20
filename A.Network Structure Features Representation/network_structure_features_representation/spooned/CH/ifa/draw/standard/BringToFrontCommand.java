/* @(#)BringToFrontCommand.java 5.1 */
package CH.ifa.draw.standard;
/**
 * BringToFrontCommand brings the selected figures in the front of
 * the other figures.
 *
 * @see SendToBackCommand
 */
public class BringToFrontCommand extends CH.ifa.draw.util.Command {
    private CH.ifa.draw.framework.DrawingView fView;

    /**
     * Constructs a bring to front command.
     *
     * @param name
     * 		the command name
     * @param view
     * 		the target view
     */
    public BringToFrontCommand(java.lang.String name, CH.ifa.draw.framework.DrawingView view) {
        super(name);
        fView = view;
    }

    public void execute() {
        CH.ifa.draw.framework.FigureEnumeration k = new CH.ifa.draw.standard.FigureEnumerator(fView.selectionZOrdered());
        while (k.hasMoreElements()) {
            fView.drawing().bringToFront(k.nextFigure());
        } 
        fView.checkDamage();
    }

    public boolean isExecutable() {
        return fView.selectionCount() > 0;
    }
}