/* @(#)PaletteIcon.java 5.1 */
package CH.ifa.draw.util;
/**
 * A three state icon that can be used in Palettes.
 *
 * @see PaletteButton
 */
public class PaletteIcon extends java.lang.Object {
    java.awt.Image fNormal;

    java.awt.Image fPressed;

    java.awt.Image fSelected;

    java.awt.Dimension fSize;

    public PaletteIcon(java.awt.Dimension size, java.awt.Image normal, java.awt.Image pressed, java.awt.Image selected) {
        fSize = size;
        fNormal = normal;
        fPressed = pressed;
        fSelected = selected;
    }

    public java.awt.Image normal() {
        return fNormal;
    }

    public java.awt.Image pressed() {
        return fPressed;
    }

    public java.awt.Image selected() {
        return fSelected;
    }

    public int getWidth() {
        return fSize.width;
    }

    public int getHeight() {
        return fSize.height;
    }
}