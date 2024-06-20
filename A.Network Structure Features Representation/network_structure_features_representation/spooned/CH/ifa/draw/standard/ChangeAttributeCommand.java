/* @(#)ChangeAttributeCommand.java 5.1 */
package CH.ifa.draw.standard;
/**
 * Command to change a named figure attribute.
 */
public class ChangeAttributeCommand extends CH.ifa.draw.util.Command {
    private CH.ifa.draw.framework.DrawingView fView;

    private java.lang.String fAttribute;

    private java.lang.Object fValue;

    /**
     * Constructs a change attribute command.
     *
     * @param name
     * 		the command name
     * @param attributeName
     * 		the name of the attribute to be changed
     * @param value
     * 		the new attribute value
     * @param view
     * 		the target view
     */
    public ChangeAttributeCommand(java.lang.String name, java.lang.String attributeName, java.lang.Object value, CH.ifa.draw.framework.DrawingView view) {
        super(name);
        fAttribute = attributeName;
        fValue = value;
        fView = view;
    }

    public void execute() {
        CH.ifa.draw.framework.FigureEnumeration k = fView.selectionElements();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure f = k.nextFigure();
            f.setAttribute(fAttribute, fValue);
        } 
        fView.checkDamage();
    }

    public boolean isExecutable() {
        return fView.selectionCount() > 0;
    }
}