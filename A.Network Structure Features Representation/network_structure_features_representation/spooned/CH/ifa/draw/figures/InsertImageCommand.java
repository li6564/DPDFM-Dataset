/* @(#)InsertImageCommand.java 5.1 */
package CH.ifa.draw.figures;
/**
 * Command to insert a named image.
 */
public class InsertImageCommand extends CH.ifa.draw.util.Command {
    private CH.ifa.draw.framework.DrawingView fView;

    private java.lang.String fImage;

    /**
     * Constructs an insert image command.
     *
     * @param name
     * 		the command name
     * @param image
     * 		the pathname of the image
     * @param view
     * 		the target view
     */
    public InsertImageCommand(java.lang.String name, java.lang.String image, CH.ifa.draw.framework.DrawingView view) {
        super(name);
        fImage = image;
        fView = view;
    }

    public void execute() {
        // ugly cast to component, but AWT wants and Component instead of an ImageObserver...
        java.awt.Image image = CH.ifa.draw.util.Iconkit.instance().registerAndLoadImage(((java.awt.Component) (fView)), fImage);
        CH.ifa.draw.figures.ImageFigure figure = new CH.ifa.draw.figures.ImageFigure(image, fImage, fView.lastClick());
        fView.add(figure);
        fView.clearSelection();
        fView.addToSelection(figure);
        fView.checkDamage();
    }
}