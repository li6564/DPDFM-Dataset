/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty.sort;
/**
 * Stores a number of orderings together and produce a single order from them
 *
 * @author Chris Seguin
 * @created November 12, 1999
 */
public class MultipleOrdering implements org.acm.seguin.util.Comparator {
    private org.acm.seguin.pretty.sort.Ordering[] ordering;

    /**
     * Constructor for the MultipleOrdering object
     *
     * @param settings
     * 		Description of Parameter
     */
    public MultipleOrdering(org.acm.seguin.util.Settings settings) {
        int count = 0;
        try {
            while (true) {
                settings.getString("sort." + (count + 1));
                count++;
            } 
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            // Found the end of the loop
        }
        ordering = new org.acm.seguin.pretty.sort.Ordering[count];
        load(settings);
    }

    /**
     * Constructor for testing purposes. Takes the order strings in an array to
     *  start it off
     *
     * @param order
     * 		Description of Parameter
     */
    public MultipleOrdering(java.lang.String[] order) {
        ordering = new org.acm.seguin.pretty.sort.Ordering[order.length];
        for (int ndx = 0; ndx < order.length; ndx++) {
            ordering[ndx] = parse(order[ndx]);
        }
    }

    /**
     * Description of the Method
     *
     * @param obj1
     * 		Description of Parameter
     * @param obj2
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public int compare(java.lang.Object obj1, java.lang.Object obj2) {
        for (int ndx = 0; ndx < ordering.length; ndx++) {
            int comp = ordering[ndx].compare(obj1, obj2);
            if (comp != 0) {
                return comp;
            }
        }
        return 0;
    }

    /**
     * Description of the Method
     *
     * @param settings
     * 		Description of Parameter
     */
    private void load(org.acm.seguin.util.Settings settings) {
        for (int ndx = 0; ndx < ordering.length; ndx++) {
            java.lang.String order = settings.getString("sort." + (ndx + 1));
            ordering[ndx] = parse(order);
        }
    }

    /**
     * Description of the Method
     *
     * @param order
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private org.acm.seguin.pretty.sort.Ordering parse(java.lang.String order) {
        java.util.StringTokenizer tok = new java.util.StringTokenizer(order, "()");
        java.lang.String name = tok.nextToken();
        java.lang.String args = "";
        if (tok.hasMoreTokens())
            args = tok.nextToken();

        if (name.equals("Type")) {
            return new org.acm.seguin.pretty.sort.TypeOrder(args);
        }
        if (name.equals("Class")) {
            return new org.acm.seguin.pretty.sort.StaticOrder(args);
        }
        if (name.equals("Protection")) {
            return new org.acm.seguin.pretty.sort.ProtectionOrder(args);
        }
        if (name.equals("Method")) {
            return new org.acm.seguin.pretty.sort.SetterGetterOrder(args);
        }
        if (name.equals("FieldInitializers")) {
            return new org.acm.seguin.pretty.sort.FieldInitializerOrder();
        }
        return null;
    }
}