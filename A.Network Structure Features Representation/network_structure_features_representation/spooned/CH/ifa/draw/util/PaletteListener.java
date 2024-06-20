/* @(#)PaletteListener.java 5.1 */
package CH.ifa.draw.util;
/* @(#)PaletteListener.java 5.1 */
/**
 * Interface for handling palette events.
 *
 * @see PaletteButton
 */
public interface PaletteListener {
    /**
     * The user selected a palette entry. The selected button is
     * passed as an argument.
     */
    void paletteUserSelected(CH.ifa.draw.util.PaletteButton button);

    /**
     * The user moved the mouse over the palette entry.
     */
    void paletteUserOver(CH.ifa.draw.util.PaletteButton button, boolean inside);
}