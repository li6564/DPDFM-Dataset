/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * Adds an abstract method to the class
 *
 * @author Chris Seguin
 */
public class AddAbstractMethod extends org.acm.seguin.refactor.method.AddNewMethod {
    /**
     * Constructor for the AddAbstractMethod object
     *
     * @param init
     * 		The signature of the method that we are adding
     */
    public AddAbstractMethod(org.acm.seguin.summary.MethodSummary init) {
        super(init);
    }

    /**
     * Sets up the modifiers
     *
     * @param source
     * 		the source holder
     * @param dest
     * 		the destination holder
     */
    protected void setupModifiers(org.acm.seguin.pretty.ModifierHolder source, org.acm.seguin.pretty.ModifierHolder dest) {
        super.setupModifiers(source, dest);
        dest.add("abstract");
        if (dest.isPrivate()) {
            dest.setPrivate(false);
            dest.setProtected(true);
        }
    }

    /**
     * Determines if the method is abstract
     *
     * @return true if the method is abstract
     */
    protected boolean isAbstract() {
        return true;
    }
}