/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common;
/**
 * Object that is responsible for reloading the class diagrams
 *
 * @author Chris Seguin
 */
public abstract class ClassDiagramReloader implements org.acm.seguin.uml.loader.Reloader {
    private java.util.Vector umlDiagrams;

    /**
     * Constructor for the ClassDiagramReloader object
     */
    public ClassDiagramReloader() {
        umlDiagrams = new java.util.Vector();
        org.acm.seguin.uml.loader.ReloaderSingleton.register(this);
    }

    /**
     * Adds a class diagram to the loader
     *
     * @param diagram
     * 		the class diagram
     */
    public void add(org.acm.seguin.uml.UMLPackage diagram) {
        if ((diagram != null) && (!umlDiagrams.contains(diagram))) {
            umlDiagrams.add(diagram);
        }
    }

    /**
     * Removes a specific class diagram from the registry
     *
     * @param diagram
     * 		the class diagram
     */
    public void remove(org.acm.seguin.uml.UMLPackage diagram) {
        if (diagram != null) {
            umlDiagrams.remove(diagram);
        }
    }

    /**
     * Removes all class diagrams from this loader
     */
    public void clear() {
        umlDiagrams.removeAllElements();
    }

    /**
     * Reload the diagrams
     */
    protected void reloadDiagrams() {
        Enumeration = umlDiagrams.elements();
        new org.acm.seguin.ide.common.RefreshDiagramThread().start();
    }

    /**
     * Reload the summary information and update the diagrams
     */
    public abstract void reload();
}