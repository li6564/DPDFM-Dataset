/* @(#)SendToBackCommand.java 5.1 */
package CH.ifa.draw.standard;
/**
 * A command to send the selection to the back of the drawing.
 */
public class SendToBackCommand extends CH.ifa.draw.util.Command {
    private CH.ifa.draw.framework.DrawingView fView;

    /**
     * Constructs a send to back command.
     *
     * @param name
     * 		the command name
     * @param view
     * 		the target view
     */
    public SendToBackCommand(java.lang.String name, CH.ifa.draw.framework.DrawingView view) {
        super(name);
        fView = view;
    }

    public void execute() {
        CH.ifa.draw.framework.FigureEnumeration k = new CH.ifa.draw.standard.ReverseFigureEnumerator(fView.selectionZOrdered());
        while (k.hasMoreElements()) {
            fView.drawing().sendToBack(k.nextFigure());
        } 
        fView.checkDamage();
    }

    public boolean isExecutable() {
        return fView.selectionCount() > 0;
    }
}