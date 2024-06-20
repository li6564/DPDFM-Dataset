/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.util;
/**
 * Settings loaded from a file
 *
 * @author Chris Seguin
 * @created October 3, 1999
 */
public class FileSettings implements org.acm.seguin.util.Settings {
    private java.lang.String app;

    private java.lang.String type;

    private java.io.File file;

    private long lastModified;

    private java.util.Properties props;

    private boolean continuallyReload;

    private boolean reloadNow;

    private org.acm.seguin.util.FileSettings parent;

    private static java.util.Hashtable map = null;

    private static java.io.File settingsRoot = null;

    /**
     * Constructor for the FileSettings object
     *
     * @param express
     * 		The file to use for loading
     * @exception MissingSettingsException
     * 		The file is not found
     */
    public FileSettings(java.io.File express) throws org.acm.seguin.util.MissingSettingsException {
        file = express;
        if (!file.exists()) {
            throw new org.acm.seguin.util.NoSettingsFileException(app, type);
        }
        load();
        this.app = express.getParent();
        this.type = express.getName();
        continuallyReload = false;
        reloadNow = false;
        parent = null;
    }

    /**
     * Constructor for the FileSettings object
     *
     * @param app
     * 		The application name
     * @param type
     * 		The application type
     * @exception MissingSettingsException
     * 		The file is not found
     */
    protected FileSettings(java.lang.String app, java.lang.String type) throws org.acm.seguin.util.MissingSettingsException {
        java.io.File directory = new java.io.File(org.acm.seguin.util.FileSettings.getSettingsRoot(), "." + app);
        if (!directory.exists()) {
            directory.mkdirs();
            throw new org.acm.seguin.util.NoSettingsFileException(app, type);
        }
        file = new java.io.File(directory, type + ".settings");
        if (!file.exists()) {
            throw new org.acm.seguin.util.NoSettingsFileException(app, type);
        }
        load();
        this.app = app;
        this.type = type;
        continuallyReload = false;
        reloadNow = false;
        parent = null;
    }

    /**
     * Sets the ContinuallyReload attribute of the FileSettings object
     *
     * @param way
     * 		The new ContinuallyReload value
     */
    public void setContinuallyReload(boolean way) {
        continuallyReload = way;
    }

    /**
     * Sets the ReloadNow attribute of the FileSettings object
     *
     * @param way
     * 		The new ReloadNow value
     */
    public void setReloadNow(boolean way) {
        reloadNow = way;
        if (reloadNow) {
            load();
        }
    }

    /**
     * Gets the keys associated with this properties
     *
     * @return the iterator
     */
    public java.util.Enumeration getKeys() {
        if (!isUpToDate()) {
            load();
        }
        reloadNow = false;
        return props.keys();
    }

    /**
     * Gets a string
     *
     * @param code
     * 		The code to look up
     * @return The associated string
     */
    public java.lang.String getString(java.lang.String code) {
        if (!isUpToDate()) {
            load();
        }
        reloadNow = false;
        java.lang.String result = props.getProperty(code);
        if ((result == null) && (parent != null)) {
            result = parent.getString(code);
        }
        if (result == null) {
            throw new org.acm.seguin.util.SettingNotFoundException(app, type, code);
        }
        return result;
    }

    /**
     * Gets a integer
     *
     * @param code
     * 		The code to look up
     * @return The associated integer
     */
    public int getInteger(java.lang.String code) {
        try {
            return java.lang.Integer.parseInt(getString(code));
        } catch (java.lang.NumberFormatException mfe) {
            throw new org.acm.seguin.util.SettingNotFoundException(app, type, code);
        }
    }

    /**
     * Gets a double
     *
     * @param code
     * 		The code to look up
     * @return The associated double
     */
    public double getDouble(java.lang.String code) {
        try {
            java.lang.Double value = new java.lang.Double(getString(code));
            return value.doubleValue();
        } catch (java.lang.NumberFormatException mfe) {
            throw new org.acm.seguin.util.SettingNotFoundException(app, type, code);
        }
    }

    /**
     * Gets a boolean
     *
     * @param code
     * 		The code to look up
     * @return The associated boolean
     */
    public boolean getBoolean(java.lang.String code) {
        try {
            java.lang.Boolean value = new java.lang.Boolean(getString(code));
            return value.booleanValue();
        } catch (java.lang.NumberFormatException mfe) {
            throw new org.acm.seguin.util.SettingNotFoundException(app, type, code);
        }
    }

    /**
     * Sets the Parent attribute of the FileSettings object
     *
     * @param value
     * 		The new Parent value
     */
    protected void setParent(org.acm.seguin.util.FileSettings value) {
        parent = value;
    }

    /**
     * Get the escaped character
     *
     * @param ch
     * 		the character
     * @return The character it should be replaced with
     */
    private char getSpecial(char ch) {
        switch (ch) {
            case 'b' :
                return ((char) (8));
            case 'r' :
                return ((char) (13));
            case 'n' :
                return ((char) (10));
            case 'f' :
                return ((char) (12));
            case 't' :
                return ((char) (9));
            default :
                return ch;
        }
    }

    /**
     * Returns true if the file is up to date. This method is used to determine
     *  if it is necessary to reload the file.
     *
     * @return true if it is up to date.
     */
    private boolean isUpToDate() {
        if (continuallyReload || reloadNow) {
            return lastModified == file.lastModified();
        }
        // Assume that it is up to date
        return true;
    }

    /**
     * Loads all the settings from the file
     */
    private synchronized void load() {
        // System.out.println("Loading from:  " + file.getPath() + "  " + file.length());
        props = new java.util.Properties();
        try {
            java.io.BufferedReader input = new java.io.BufferedReader(new java.io.FileReader(file));
            java.lang.String line = input.readLine();
            while (line != null) {
                if ((line.length() == 0) || (line.charAt(0) == '#')) {
                    // Comment - skip the line
                } else {
                    int equalsAt = line.indexOf('=');
                    if (equalsAt > 0) {
                        java.lang.String key = line.substring(0, equalsAt);
                        java.lang.String value = unescapeChars(line.substring(equalsAt + 1));
                        props.put(key, value);
                    }
                }
                line = input.readLine();
            } 
            input.close();
        } catch (java.io.IOException ioe) {
            org.acm.seguin.awt.ExceptionPrinter.print(ioe);
        }
        setReloadNow(false);
        lastModified = file.lastModified();
    }

    /**
     * A transformation on the characters in the string
     *
     * @param value
     * 		the string we are updating
     * @return the updated string
     */
    private java.lang.String unescapeChars(java.lang.String value) {
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        int last = value.length();
        for (int ndx = 0; ndx < last; ndx++) {
            char ch = value.charAt(ndx);
            if (ch == '\\') {
                char nextChar = value.charAt(ndx + 1);
                char result = ' ';
                if (nextChar == 'u') {
                    result = unicode(value, ndx);
                    ndx += 5;
                } else if (java.lang.Character.isDigit(nextChar)) {
                    result = octal(value, ndx);
                    ndx += 3;
                } else if (ndx == (last - 1)) {
                    // Continuation...
                } else {
                    result = getSpecial(nextChar);
                    ndx++;
                }
                buffer.append(result);
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    /**
     * Determine the unicode character
     *
     * @param value
     * 		Description of Parameter
     * @param ndx
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private char unicode(java.lang.String value, int ndx) {
        java.lang.String hex = value.substring(ndx + 2, ndx + 6);
        int result = java.lang.Integer.parseInt(hex, 16);
        return ((char) (result));
    }

    /**
     * Determine the octal character
     *
     * @param value
     * 		Description of Parameter
     * @param ndx
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private char octal(java.lang.String value, int ndx) {
        java.lang.String oct = value.substring(ndx + 1, ndx + 4);
        int result = java.lang.Integer.parseInt(oct, 8);
        return ((char) (result));
    }

    /**
     * Sets the root directory for settings files
     *
     * @param dir
     * 		The new SettingsRoot value
     */
    public static void setSettingsRoot(java.lang.String dir) {
        org.acm.seguin.util.FileSettings.settingsRoot = new java.io.File(dir);
    }

    /**
     * Sets the root directory for settings files
     *
     * @param dir
     * 		The new SettingsRoot value
     */
    public static void setSettingsRoot(java.io.File dir) {
        org.acm.seguin.util.FileSettings.settingsRoot = dir;
    }

    /**
     * Factory method to create FileSettings objects
     *
     * @param app
     * 		The name of the application
     * @param name
     * 		The name of the specific settings
     * @return A settings object
     */
    public static org.acm.seguin.util.FileSettings getSettings(java.lang.String app, java.lang.String name) {
        if (org.acm.seguin.util.FileSettings.map == null) {
            org.acm.seguin.util.FileSettings.init();
        }
        java.lang.String key = (app + "::") + name;
        org.acm.seguin.util.FileSettings result = ((org.acm.seguin.util.FileSettings) (org.acm.seguin.util.FileSettings.map.get(key)));
        if (result == null) {
            result = new org.acm.seguin.util.FileSettings(app, name);
            org.acm.seguin.util.FileSettings.map.put(key, result);
        }
        return result;
    }

    /**
     * Gets the SettingsRoot attribute of the FileSettings class
     *
     * @return The SettingsRoot value
     */
    public static java.lang.String getSettingsRoot() {
        if (org.acm.seguin.util.FileSettings.settingsRoot == null) {
            org.acm.seguin.util.FileSettings.initRootDir();
        }
        return org.acm.seguin.util.FileSettings.settingsRoot.getPath();
    }

    /**
     * Main program to test the FileSettings object
     *
     * @param args
     * 		the command line arguments
     */
    public static void main(java.lang.String[] args) {
        // Make sure everything is installed properly
        new org.acm.seguin.tools.install.RefactoryInstaller(false).run();
        java.lang.String key = "author";
        if (args.length > 0) {
            key = args[0];
        }
        java.lang.String type = "pretty";
        if (args.length > 1) {
            type = args[1];
        }
        java.lang.String app = "Refactory";
        if (args.length > 2) {
            app = args[2];
        }
        java.lang.System.out.println("Found:  " + new org.acm.seguin.util.FileSettings(app, type).getString(key));
    }

    /**
     * Initializes static variables
     */
    private static synchronized void init() {
        if (org.acm.seguin.util.FileSettings.map == null) {
            org.acm.seguin.util.FileSettings.map = new java.util.Hashtable();
            org.acm.seguin.util.FileSettings.initRootDir();
        }
    }

    /**
     * Initializes the root directory
     */
    private static void initRootDir() {
        if (org.acm.seguin.util.FileSettings.settingsRoot != null) {
            return;
        }
        java.lang.String javaHome = java.lang.System.getProperty("jrefactory.home");
        if (javaHome != null) {
            // System.out.println("Home:  " + javaHome);
            org.acm.seguin.util.FileSettings.settingsRoot = new java.io.File(javaHome);
            return;
        }
        javaHome = java.lang.System.getProperty("user.home");
        if (javaHome != null) {
            // System.out.println("Home:  " + javaHome);
            org.acm.seguin.util.FileSettings.settingsRoot = new java.io.File(javaHome);
            return;
        }
        org.acm.seguin.util.FileSettings.settingsRoot = new java.io.File("~/");
        if (org.acm.seguin.util.FileSettings.settingsRoot.exists()) {
            // System.out.println("Home:  ~/");
            return;
        }
        org.acm.seguin.util.FileSettings.settingsRoot = new java.io.File("C:\\winnt\\profiles");
        if (org.acm.seguin.util.FileSettings.settingsRoot.exists()) {
            java.io.File attempt = new java.io.File(org.acm.seguin.util.FileSettings.settingsRoot, java.lang.System.getProperty("user.name"));
            if (attempt.exists()) {
                // System.out.println("Home:  C:\\winnt\\profiles\\currentuser");
                org.acm.seguin.util.FileSettings.settingsRoot = attempt;
                return;
            }
        }
        org.acm.seguin.util.FileSettings.settingsRoot = new java.io.File("c:\\windows");
        // System.out.println("Home:  C:\\windows");
    }
}