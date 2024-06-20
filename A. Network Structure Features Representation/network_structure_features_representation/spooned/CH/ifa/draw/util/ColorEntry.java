/* @(#)ColorMap.java 5.1 */
package CH.ifa.draw.util;
class ColorEntry {
    public java.lang.String fName;

    public java.awt.Color fColor;

    ColorEntry(java.lang.String name, java.awt.Color color) {
        fColor = color;
        fName = name;
    }
}