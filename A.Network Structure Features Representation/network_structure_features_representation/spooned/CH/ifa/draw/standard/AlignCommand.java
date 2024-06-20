/* @(#)AlignCommand.java 5.1 */
package CH.ifa.draw.standard;
/**
 * Align a selection of figures relative to each other.
 */
public class AlignCommand extends CH.ifa.draw.util.Command {
    private CH.ifa.draw.framework.DrawingView fView;

    private int fOp;

    /**
     * align left sides
     */
    public static final int LEFTS = 0;

    /**
     * align centers (horizontally)
     */
    public static final int CENTERS = 1;

    /**
     * align right sides
     */
    public static final int RIGHTS = 2;

    /**
     * align tops
     */
    public static final int TOPS = 3;

    /**
     * align middles (vertically)
     */
    public static final int MIDDLES = 4;

    /**
     * align bottoms
     */
    public static final int BOTTOMS = 5;

    /**
     * Constructs an alignment command.
     *
     * @param name
     * 		the command name
     * @param view
     * 		the target view
     * @param op
     * 		the alignment operation (LEFTS, CENTERS, RIGHTS, etc.)
     */
    public AlignCommand(java.lang.String name, CH.ifa.draw.framework.DrawingView view, int op) {
        super(name);
        fView = view;
        fOp = op;
    }

    public boolean isExecutable() {
        return fView.selectionCount() > 1;
    }

    public void execute() {
        CH.ifa.draw.framework.FigureEnumeration selection = fView.selectionElements();
        CH.ifa.draw.framework.Figure anchorFigure = selection.nextFigure();
        java.awt.Rectangle r = anchorFigure.displayBox();
        while (selection.hasMoreElements()) {
            CH.ifa.draw.framework.Figure f = selection.nextFigure();
            java.awt.Rectangle rr = f.displayBox();
            switch (fOp) {
                case CH.ifa.draw.standard.AlignCommand.LEFTS :
                    f.moveBy(r.x - rr.x, 0);
                    break;
                case CH.ifa.draw.standard.AlignCommand.CENTERS :
                    f.moveBy((r.x + (r.width / 2)) - (rr.x + (rr.width / 2)), 0);
                    break;
                case CH.ifa.draw.standard.AlignCommand.RIGHTS :
                    f.moveBy((r.x + r.width) - (rr.x + rr.width), 0);
                    break;
                case CH.ifa.draw.standard.AlignCommand.TOPS :
                    f.moveBy(0, r.y - rr.y);
                    break;
                case CH.ifa.draw.standard.AlignCommand.MIDDLES :
                    f.moveBy(0, (r.y + (r.height / 2)) - (rr.y + (rr.height / 2)));
                    break;
                case CH.ifa.draw.standard.AlignCommand.BOTTOMS :
                    f.moveBy(0, (r.y + r.height) - (rr.y + rr.height));
                    break;
            }
        } 
        fView.checkDamage();
    }
}