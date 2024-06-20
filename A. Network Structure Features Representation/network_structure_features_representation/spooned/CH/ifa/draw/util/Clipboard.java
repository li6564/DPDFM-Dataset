/* @(#)Clipboard.java 5.1 */
package CH.ifa.draw.util;
/**
 * A temporary replacement for a global clipboard.
 * It is a singleton that can be used to store and
 * get the contents of the clipboard.
 */
public class Clipboard {
    static CH.ifa.draw.util.Clipboard fgClipboard = new CH.ifa.draw.util.Clipboard();

    /**
     * Gets the clipboard.
     */
    public static CH.ifa.draw.util.Clipboard getClipboard() {
        return CH.ifa.draw.util.Clipboard.fgClipboard;
    }

    private java.lang.Object fContents;

    private Clipboard() {
    }

    /**
     * Sets the contents of the clipboard.
     */
    public void setContents(java.lang.Object contents) {
        fContents = contents;
    }

    /**
     * Gets the contents of the clipboard.
     */
    public java.lang.Object getContents() {
        return fContents;
    }
}