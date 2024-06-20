/* @(#)BoxHandleKit.java 5.1 */
package CH.ifa.draw.standard;
/**
 * A set of utility methods to create Handles for the common
 * locations on a figure's display box.
 *
 * @see Handle
 */
// TBD: use anonymous inner classes (had some problems with JDK 1.1)
public class BoxHandleKit {
    /**
     * Fills the given Vector with handles at each corner of a
     * figure.
     */
    public static void addCornerHandles(CH.ifa.draw.framework.Figure f, java.util.Vector handles) {
        handles.addElement(CH.ifa.draw.standard.BoxHandleKit.southEast(f));
        handles.addElement(CH.ifa.draw.standard.BoxHandleKit.southWest(f));
        handles.addElement(CH.ifa.draw.standard.BoxHandleKit.northEast(f));
        handles.addElement(CH.ifa.draw.standard.BoxHandleKit.northWest(f));
    }

    /**
     * Fills the given Vector with handles at each corner
     * and the north, south, east, and west of the figure.
     */
    public static void addHandles(CH.ifa.draw.framework.Figure f, java.util.Vector handles) {
        CH.ifa.draw.standard.BoxHandleKit.addCornerHandles(f, handles);
        handles.addElement(CH.ifa.draw.standard.BoxHandleKit.south(f));
        handles.addElement(CH.ifa.draw.standard.BoxHandleKit.north(f));
        handles.addElement(CH.ifa.draw.standard.BoxHandleKit.east(f));
        handles.addElement(CH.ifa.draw.standard.BoxHandleKit.west(f));
    }

    public static CH.ifa.draw.framework.Handle south(CH.ifa.draw.framework.Figure owner) {
        return new CH.ifa.draw.standard.SouthHandle(owner);
    }

    public static CH.ifa.draw.framework.Handle southEast(CH.ifa.draw.framework.Figure owner) {
        return new CH.ifa.draw.standard.SouthEastHandle(owner);
    }

    public static CH.ifa.draw.framework.Handle southWest(CH.ifa.draw.framework.Figure owner) {
        return new CH.ifa.draw.standard.SouthWestHandle(owner);
    }

    public static CH.ifa.draw.framework.Handle north(CH.ifa.draw.framework.Figure owner) {
        return new CH.ifa.draw.standard.NorthHandle(owner);
    }

    public static CH.ifa.draw.framework.Handle northEast(CH.ifa.draw.framework.Figure owner) {
        return new CH.ifa.draw.standard.NorthEastHandle(owner);
    }

    public static CH.ifa.draw.framework.Handle northWest(CH.ifa.draw.framework.Figure owner) {
        return new CH.ifa.draw.standard.NorthWestHandle(owner);
    }

    public static CH.ifa.draw.framework.Handle east(CH.ifa.draw.framework.Figure owner) {
        return new CH.ifa.draw.standard.EastHandle(owner);
    }

    public static CH.ifa.draw.framework.Handle west(CH.ifa.draw.framework.Figure owner) {
        return new CH.ifa.draw.standard.WestHandle(owner);
    }
}