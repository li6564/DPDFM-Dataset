/* @(#)Iconkit.java 5.1 */
package CH.ifa.draw.util;
/**
 * The Iconkit class supports the sharing of images. It maintains
 * a map of image names and their corresponding images.
 *
 * Iconkit also supports to load a collection of images in
 * synchronized way.
 * The resolution of a path name to an image is delegated to the DrawingEditor.
 * <hr>
 * <b>Design Patterns</b><P>
 * <img src="images/red-ball-small.gif" width=6 height=6 alt=" o ">
 * <b><a href=../pattlets/sld031.htm>Singleton</a></b><br>
 * The Iconkit is a singleton.
 * <hr>
 */
public class Iconkit {
    private java.util.Hashtable fMap;

    private java.util.Vector fRegisteredImages;

    private java.awt.Component fComponent;

    private static final int ID = 123;

    private static CH.ifa.draw.util.Iconkit fgIconkit = null;

    private static boolean fgDebug = false;

    /**
     * Constructs an Iconkit that uses the given editor to
     * resolve image path names.
     */
    public Iconkit(java.awt.Component component) {
        fMap = new java.util.Hashtable(53);
        fRegisteredImages = new java.util.Vector(10);
        fComponent = component;
        CH.ifa.draw.util.Iconkit.fgIconkit = this;
    }

    /**
     * Gets the single instance
     */
    public static CH.ifa.draw.util.Iconkit instance() {
        return CH.ifa.draw.util.Iconkit.fgIconkit;
    }

    /**
     * Loads all registered images.
     *
     * @see #registerImage
     */
    public void loadRegisteredImages(java.awt.Component component) {
        if (fRegisteredImages.size() == 0)
            return;

        java.awt.MediaTracker tracker = new java.awt.MediaTracker(component);
        // register images with MediaTracker
        java.util.Enumeration k = fRegisteredImages.elements();
        while (k.hasMoreElements()) {
            java.lang.String fileName = ((java.lang.String) (k.nextElement()));
            if (basicGetImage(fileName) == null) {
                tracker.addImage(loadImage(fileName), CH.ifa.draw.util.Iconkit.ID);
            }
        } 
        fRegisteredImages.removeAllElements();
        // block until all images are loaded
        try {
            tracker.waitForAll();
        } catch (java.lang.Exception e) {
        }
    }

    /**
     * Registers an image that is then loaded together with
     * the other registered images by loadRegisteredImages.
     *
     * @see #loadRegisteredImages
     */
    public void registerImage(java.lang.String fileName) {
        fRegisteredImages.addElement(fileName);
    }

    /**
     * Registers and loads an image.
     */
    public java.awt.Image registerAndLoadImage(java.awt.Component component, java.lang.String fileName) {
        registerImage(fileName);
        loadRegisteredImages(component);
        return getImage(fileName);
    }

    /**
     * Loads an image with the given name.
     */
    public java.awt.Image loadImage(java.lang.String filename) {
        if (fMap.containsKey(filename))
            return ((java.awt.Image) (fMap.get(filename)));

        java.awt.Image image = loadImageResource(filename);
        if (image != null)
            fMap.put(filename, image);

        return image;
    }

    public java.awt.Image loadImageResource(java.lang.String resourcename) {
        java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
        try {
            java.net.URL url = getClass().getResource(resourcename);
            if (CH.ifa.draw.util.Iconkit.fgDebug)
                java.lang.System.out.println(resourcename);

            return toolkit.createImage(((java.awt.image.ImageProducer) (url.getContent())));
        } catch (java.lang.Exception ex) {
            return null;
        }
    }

    /**
     * Gets the image with the given name. If the image
     * can't be found it tries it again after loading
     * all the registered images.
     */
    public java.awt.Image getImage(java.lang.String filename) {
        java.awt.Image image = basicGetImage(filename);
        if (image != null)
            return image;

        // load registered images and try again
        loadRegisteredImages(fComponent);
        // try again
        return basicGetImage(filename);
    }

    private java.awt.Image basicGetImage(java.lang.String filename) {
        if (fMap.containsKey(filename))
            return ((java.awt.Image) (fMap.get(filename)));

        return null;
    }
}