/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty.ai;
/**
 * Adds the tags as they are required by the pretty.settings file.
 *
 * @author Chris Seguin
 */
public class RequiredTags {
    java.lang.Object[] arguments;

    private static org.acm.seguin.pretty.ai.RequiredTags tags = null;

    /**
     * Constructor for the RequiredTags object
     */
    private RequiredTags() {
        arguments = new java.lang.Object[3];
        arguments[0] = java.lang.System.getProperty("user.name");
        arguments[1] = java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG).format(new java.util.Date());
    }

    /**
     * Adds the tags that are required for this object
     *
     * @param bundle
     * 		the pretty printer bundle
     * @param key
     * 		the key for this object (class for classes and interfaces,
     * 		method for methods and constructors, or field)
     * @param name
     * 		the name of the object
     * @param jdi
     * 		the javadoc tag holder
     */
    public void addTags(org.acm.seguin.util.FileSettings bundle, java.lang.String key, java.lang.String name, org.acm.seguin.pretty.JavaDocableImpl jdi) {
        java.lang.String tags = bundle.getString(key + ".tags");
        java.util.StringTokenizer tok = new java.util.StringTokenizer(tags, ", \t\n");
        while (tok.hasMoreTokens()) {
            java.lang.String next = tok.nextToken();
            add(bundle, next, name, jdi);
        } 
    }

    /**
     * Adds a particular tag
     *
     * @param bundle
     * 		the file settings
     * @param tag
     * 		the tag we are about to add
     * @param name
     * 		the name of the object
     * @param jdi
     * 		the java doc holder
     */
    private void add(org.acm.seguin.util.FileSettings bundle, java.lang.String tag, java.lang.String name, org.acm.seguin.pretty.JavaDocableImpl jdi) {
        if (tag.equalsIgnoreCase("param")) {
        } else if (tag.equalsIgnoreCase("return")) {
        } else if (tag.equalsIgnoreCase("exception")) {
        } else if (tag.equalsIgnoreCase("throws")) {
        } else if (jdi.contains(tag)) {
        } else {
            addNormalTag(bundle, tag, name, jdi);
        }
    }

    /**
     * Adds a normal tag to the javadoc comment
     *
     * @param bundle
     * 		the pretty.settings bundle
     * @param tag
     * 		the tag we are adding
     * @param name
     * 		the name of the object
     * @param jdi
     * 		the javadoc comment holder
     */
    private void addNormalTag(org.acm.seguin.util.FileSettings bundle, java.lang.String tag, java.lang.String name, org.acm.seguin.pretty.JavaDocableImpl jdi) {
        try {
            java.lang.String format = bundle.getString(tag + ".descr");
            arguments[2] = name;
            java.lang.String value = java.text.MessageFormat.format(format, arguments);
            jdi.require("@" + tag, value);
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            // Not required since there was no tag.descr involved
        }
    }

    /**
     * Gets the Tagger attribute of the RequiredTags class
     *
     * @return The Tagger value
     */
    public static org.acm.seguin.pretty.ai.RequiredTags getTagger() {
        if (org.acm.seguin.pretty.ai.RequiredTags.tags == null) {
            org.acm.seguin.pretty.ai.RequiredTags.tags = new org.acm.seguin.pretty.ai.RequiredTags();
        }
        return org.acm.seguin.pretty.ai.RequiredTags.tags;
    }
}