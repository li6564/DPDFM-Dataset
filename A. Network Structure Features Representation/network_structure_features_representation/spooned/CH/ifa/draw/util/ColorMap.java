/* @(#)ColorMap.java 5.1 */
package CH.ifa.draw.util;
/**
 * A map that is filled with some standard colors. The colors
 * can be looked up by name or index.
 */
public class ColorMap extends java.lang.Object {
    static CH.ifa.draw.util.ColorEntry fMap[] = new CH.ifa.draw.util.ColorEntry[]{ new CH.ifa.draw.util.ColorEntry("Black", java.awt.Color.black), new CH.ifa.draw.util.ColorEntry("Blue", java.awt.Color.blue), new CH.ifa.draw.util.ColorEntry("Green", java.awt.Color.green), new CH.ifa.draw.util.ColorEntry("Red", java.awt.Color.red), new CH.ifa.draw.util.ColorEntry("Pink", java.awt.Color.pink), new CH.ifa.draw.util.ColorEntry("Magenta", java.awt.Color.magenta), new CH.ifa.draw.util.ColorEntry("Orange", java.awt.Color.orange), new CH.ifa.draw.util.ColorEntry("Yellow", java.awt.Color.yellow), new CH.ifa.draw.util.ColorEntry("New Tan", new java.awt.Color(0xebc79e)), new CH.ifa.draw.util.ColorEntry("Aquamarine", new java.awt.Color(0x70db93)), new CH.ifa.draw.util.ColorEntry("Sea Green", new java.awt.Color(0x238e68)), new CH.ifa.draw.util.ColorEntry("Dark Gray", java.awt.Color.darkGray), new CH.ifa.draw.util.ColorEntry("Light Gray", java.awt.Color.lightGray), new CH.ifa.draw.util.ColorEntry("White", java.awt.Color.white), // there is no support for alpha values so we use a special value
    // to represent a transparent color
    new CH.ifa.draw.util.ColorEntry("None", new java.awt.Color(0xffc79e)) };

    public static int size() {
        return CH.ifa.draw.util.ColorMap.fMap.length;
    }

    public static java.awt.Color color(int index) {
        if ((index < CH.ifa.draw.util.ColorMap.size()) && (index >= 0))
            return CH.ifa.draw.util.ColorMap.fMap[index].fColor;

        throw new java.lang.ArrayIndexOutOfBoundsException("Color index: " + index);
    }

    public static java.awt.Color color(java.lang.String name) {
        for (int i = 0; i < CH.ifa.draw.util.ColorMap.fMap.length; i++)
            if (CH.ifa.draw.util.ColorMap.fMap[i].fName.equals(name))
                return CH.ifa.draw.util.ColorMap.fMap[i].fColor;


        return java.awt.Color.black;
    }

    public static java.lang.String name(int index) {
        if ((index < CH.ifa.draw.util.ColorMap.size()) && (index >= 0))
            return CH.ifa.draw.util.ColorMap.fMap[index].fName;

        throw new java.lang.ArrayIndexOutOfBoundsException("Color index: " + index);
    }

    public static int colorIndex(java.awt.Color color) {
        for (int i = 0; i < CH.ifa.draw.util.ColorMap.fMap.length; i++)
            if (CH.ifa.draw.util.ColorMap.fMap[i].fColor.equals(color))
                return i;


        return 0;
    }

    public static boolean isTransparent(java.awt.Color color) {
        return color.equals(CH.ifa.draw.util.ColorMap.color("None"));
    }
}