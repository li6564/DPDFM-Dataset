/**
 * QuickUML; A simple UML tool that demonstrates one use of the
 * Java Diagram Package
 *
 * Copyright (C) 2001  Eric Crahen <crahen@cse.buffalo.edu>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package uml.ui;
import java.awt.Component;
/**
 *
 * @class IconManager
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

The IconManager provides a common way to relate and load images
for any Component. Responisble for finding, resolving and loading
the Images & Icons.
 */
public class IconManager {
    private static uml.ui.IconManager instance = new uml.ui.IconManager();

    // registered related images
    private java.util.WeakHashMap resourceMap = new java.util.WeakHashMap();

    // loaded images
    private java.util.HashMap imageMap = new java.util.HashMap();

    private IconManager() {
    }

    /**
     * Get an instance of the IconManager
     *
     * @return IconManager
     */
    public static uml.ui.IconManager getInstance() {
        return uml.ui.IconManager.instance;
    }

    /**
     * Given a resource name, load an image.
     *
     * @param String
     * @return Image
     */
    protected java.awt.Image loadImageResource(java.lang.String resourceName) {
        java.awt.Toolkit kit = java.awt.Toolkit.getDefaultToolkit();
        java.net.URL url = getClass().getResource(resourceName);
        if (url != null) {
            try {
                // Convert the URL into an image
                return kit.createImage(((java.awt.image.ImageProducer) (url.getContent())));
            } catch (java.lang.Throwable t) {
                t.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Register an image resource for this component.
     *
     * @param Component
     * @param String
     */
    public void registerImageResource(java.awt.Component comp, java.lang.String resourceName) {
        java.util.Vector v = ((java.util.Vector) (resourceMap.get(comp)));
        if (v == null) {
            v = new java.util.Vector();
            resourceMap.put(comp, v);
        }
        if (!v.contains(resourceName))
            v.add(resourceName);

    }

    /**
     * Get an Image for a Component. Lazily start to load all registered
     * images as well.
     *
     * @param String
     * @return Image
     */
    public java.awt.Image getImageResource(java.awt.Component comp, java.lang.String resourceName) {
        if (resourceName.charAt(0) != '/')
            resourceName = '/' + resourceName;

        // Get a previously loaded icon
        java.awt.Image img = null;
        if ((img = ((java.awt.Image) (imageMap.get(resourceName)))) == null) {
            java.awt.MediaTracker tracker = new java.awt.MediaTracker(comp);
            // Lazy load all related resources
            java.util.Vector v = ((java.util.Vector) (resourceMap.get(comp)));
            if (v != null) {
                resourceMap.remove(comp);
                // Get the image, add to the tracker
                for (java.util.Iterator i = v.iterator(); i.hasNext();)
                    trackImage(tracker, ((java.lang.String) (i.next())));

            }
            if ((v == null) || (!v.contains(resourceName)))
                trackImage(tracker, resourceName);

            // Wait for all,
            try {
                tracker.waitForAll();
            } catch (java.lang.InterruptedException e) {
            }
            if (tracker.checkAll())
                img = ((java.awt.Image) (imageMap.get(resourceName)));

        }
        if (img == null)
            throw new java.lang.RuntimeException("Resource not found " + resourceName);

        return img;
    }

    /**
     * Start resolving the image with the given MediaTracker
     *
     * @post Images added to the tracker are moved to the image map
     */
    private final java.awt.Image trackImage(java.awt.MediaTracker tracker, java.lang.String resourceName) {
        java.awt.Image img = loadImageResource(resourceName);
        if (img != null) {
            // Start loading
            tracker.addImage(img, 1);
            imageMap.put(resourceName, img);
        }
        return img;
    }

    /**
     * Get an Icon for a Component.
     *
     * @param String
     * @return Image
     */
    public javax.swing.Icon getIconResource(java.awt.Component comp, java.lang.String resourceName) {
        if (comp == null)
            throw new java.lang.RuntimeException("Null component!");

        if (resourceName == null)
            throw new java.lang.RuntimeException("Null resource name!");

        return new javax.swing.ImageIcon(getImageResource(comp, resourceName));
    }
}