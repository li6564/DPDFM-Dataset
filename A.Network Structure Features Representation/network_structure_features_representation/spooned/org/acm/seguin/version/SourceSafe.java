/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.version;
import java.io.IOException;
/**
 * Creates a process that checks out a file from source safe
 *
 * @author Chris Seguin
 * @created June 11, 1999
 */
public class SourceSafe implements org.acm.seguin.version.VersionControl {
    // Instance Variables
    private java.lang.String exeFile;

    private java.lang.String user;

    /**
     * Constructor for the source safe object
     */
    public SourceSafe() {
        try {
            org.acm.seguin.util.FileSettings bundle = org.acm.seguin.util.FileSettings.getSettings("Refactory", "vss");
            exeFile = bundle.getString("vss");
        } catch (org.acm.seguin.util.MissingSettingsException mre) {
            java.lang.System.out.println("Unable to initialize source safe");
            org.acm.seguin.awt.ExceptionPrinter.print(mre);
        } catch (java.lang.NumberFormatException nfe) {
            java.lang.System.out.println("Unable to interpret the count property as an integer");
        }
    }

    /**
     * Determines if a file is under version control
     *
     * @param fullFilename
     * 		The full path of the file
     * @return Returns true if the files is under version control
     */
    public synchronized boolean contains(java.lang.String fullFilename) {
        try {
            java.lang.System.out.println("\tChecking for " + fullFilename);
            java.lang.String project = getProject(java.lang.Runtime.getRuntime(), fullFilename);
            boolean result = (project != null) && (project.length() > 0);
            java.lang.System.out.println(((("\t" + fullFilename) + " is ") + (result ? "" : "not ")) + "in Source Safe");
            return result;
        } catch (java.io.IOException ioe) {
            org.acm.seguin.awt.ExceptionPrinter.print(ioe);
            return false;
        }
    }

    /**
     * Adds a file to version control
     *
     * @param fullFilename
     * 		the file to add
     */
    public synchronized void add(java.lang.String fullFilename) {
    }

    /**
     * Check out the file from source safe
     *
     * @param file
     * 		the name of the file
     */
    public synchronized void checkOut(java.lang.String file) {
        try {
            // Get the runtime program
            java.lang.Runtime processFactory = java.lang.Runtime.getRuntime();
            java.lang.System.out.println("\tFinding the project");
            java.lang.String project = getProject(processFactory, file);
            if (project == null) {
                java.lang.System.out.println("\tNot in any project");
                return;
            }
            java.lang.System.out.println("\tChanging to project:  " + project);
            changeProject(processFactory, project);
            java.lang.System.out.println("\tChecking out the file:  " + file);
            checkout(processFactory, file);
            java.lang.System.out.println("\tDone:  " + file);
        } catch (java.io.IOException ioe) {
            org.acm.seguin.awt.ExceptionPrinter.print(ioe);
        }
    }

    /**
     * Check out the file from source safe
     *
     * @param file
     * 		the name of the file
     */
    public synchronized void checkIn(java.lang.String file) {
        try {
            // Get the runtime program
            java.lang.Runtime processFactory = java.lang.Runtime.getRuntime();
            java.lang.System.out.println("\tFinding the project");
            java.lang.String project = getProject(processFactory, file);
            if (project == null) {
                java.lang.System.out.println("\tNot in any project");
                return;
            }
            java.lang.System.out.println("\tChecking in the file to " + project);
            changeProject(processFactory, project);
            java.lang.System.out.println("\tChanged to the project");
            checkin(processFactory, file);
            java.lang.System.out.println("\tDone:  " + file);
        } catch (java.io.IOException ioe) {
            org.acm.seguin.awt.ExceptionPrinter.print(ioe);
        }
    }

    /**
     * Find the project
     *
     * @param factory
     * 		the run time factory
     * @param file
     * 		the name of the file to find
     * @return the name of the project or null if in no project
     * @exception IOException
     * 		is thrown if the command cannot be executed
     */
    protected java.lang.String getProject(java.lang.Runtime factory, java.lang.String file) throws java.io.IOException {
        java.lang.System.out.println("DEBUG[SourceSafe.getProject]  #1");
        java.io.BufferedReader input = executeLocate(factory, file);
        // Skip the first line
        java.lang.System.out.println("DEBUG[SourceSafe.getProject]  #2");
        java.lang.String firstLine = input.readLine();
        java.lang.System.out.println("DEBUG[SourceSafe.getProject]  #3 - " + firstLine);
        // Get the path
        java.lang.String path = getPath(file);
        java.lang.System.out.println("DEBUG[SourceSafe.getProject]  #4 - " + path);
        java.lang.String found = input.readLine();
        if (isNotInSourceSafe(found)) {
            return null;
        }
        java.lang.System.out.println("DEBUG[SourceSafe.getProject]  #5 - " + found);
        // Find the project
        int matchCode = -1;
        do {
            java.lang.System.out.println("DEBUG[SourceSafe.getProject]  #6");
            if (found.indexOf("(Deleted)") == (-1)) {
                java.lang.System.out.println("DEBUG[SourceSafe.getProject]  #7");
                java.lang.String project = extractProjectName(found);
                java.lang.System.out.println("DEBUG[SourceSafe.getProject]  #8 - " + project);
                if (match(path, project) != (-1)) {
                    java.lang.System.out.println("DEBUG[SourceSafe.getProject]  #9");
                    return project;
                }
            }
            // Read the next line
            java.lang.System.out.println("DEBUG[SourceSafe.getProject]  #10");
            found = input.readLine();
            java.lang.System.out.println("DEBUG[SourceSafe.getProject]  #11 - " + found);
            if ((found.length() == 0) || (found.charAt(0) != '$')) {
                return null;
            }
            java.lang.System.out.println("DEBUG[SourceSafe.getProject]  #12");
        } while (matchCode == (-1) );
        // Not found
        java.lang.System.out.println("DEBUG[SourceSafe.getProject]  #13");
        return null;
    }

    /**
     * Get the filename
     *
     * @param fullFilename
     * 		the fully qualified path
     * @return the filename
     */
    protected java.lang.String getFilename(java.lang.String fullFilename) {
        java.io.File temp = new java.io.File(fullFilename);
        return temp.getName();
    }

    /**
     * Get the path
     *
     * @param fullFilename
     * 		the fully qualified path
     * @return the path
     */
    protected java.lang.String getPath(java.lang.String fullFilename) {
        java.io.File temp = new java.io.File(fullFilename);
        return temp.getParentFile().getPath();
    }

    /**
     * Change to the project directory
     *
     * @param factory
     * 		the run time factory
     * @param project
     * 		the name of the project to change to
     * @exception IOException
     * 		is thrown if the command cannot be executed
     */
    protected void changeProject(java.lang.Runtime factory, java.lang.String project) throws java.io.IOException {
        // Create the executable
        java.lang.String[] args = new java.lang.String[3];
        args[0] = exeFile;
        args[1] = "CP";
        args[2] = project;
        java.lang.Process proc = factory.exec(args);
        try {
            proc.waitFor();
        } catch (java.lang.InterruptedException ie) {
        }
    }

    /**
     * Check out the file
     *
     * @param factory
     * 		the run time factory
     * @param file
     * 		the file that we are checking out
     * @exception IOException
     * 		is thrown if the command cannot be executed
     */
    protected void checkout(java.lang.Runtime factory, java.lang.String file) throws java.io.IOException {
        // Create the executable
        java.lang.String[] args = new java.lang.String[3];
        args[0] = exeFile;
        args[1] = "CHECKOUT";
        args[2] = getFilename(file);
        java.lang.Process proc = factory.exec(args);
        try {
            proc.waitFor();
        } catch (java.lang.InterruptedException ie) {
        }
    }

    /**
     * Check in the file
     *
     * @param factory
     * 		the run time factory
     * @param file
     * 		the file that we are checking out
     * @exception IOException
     * 		is thrown if the command cannot be executed
     */
    protected void checkin(java.lang.Runtime factory, java.lang.String file) throws java.io.IOException {
        // Create the executable
        java.lang.String[] args = new java.lang.String[3];
        args[0] = exeFile;
        args[1] = "CHECKIN";
        args[2] = getFilename(file);
        java.lang.Process proc = factory.exec(args);
        try {
            proc.waitFor();
        } catch (java.lang.InterruptedException ie) {
        }
    }

    /**
     * Matches the path to the projects
     *
     * @param path
     * 		the path
     * @param project
     * 		the project
     * @return the index of the item in the roots, or -1 if not there
     */
    protected int match(java.lang.String path, java.lang.String project) {
        try {
            org.acm.seguin.util.FileSettings bundle = org.acm.seguin.util.FileSettings.getSettings("Refactory", "vss");
            int index = 1;
            while (true) {
                java.lang.String sourceStart = bundle.getString("source." + index);
                java.lang.String projectStart = bundle.getString("project." + index);
                if (startSame(path, sourceStart) && startSame(project, projectStart)) {
                    // Check the rest
                    java.lang.String restPath = path.substring(sourceStart.length());
                    java.lang.String restProject = project.substring(projectStart.length());
                    java.lang.System.out.println(((("    Rest [" + restPath) + "] [") + restProject) + "]");
                    if (compare(restPath, restProject)) {
                        java.lang.System.out.println("\t\tFound!");
                        return index;
                    }
                }
                java.lang.System.out.println("Not this pair...  " + index);
                index++;
            } 
        } catch (org.acm.seguin.util.MissingSettingsException mre) {
        }
        // Not found
        return -1;
    }

    /**
     * Compares two files
     *
     * @param one
     * 		the first name
     * @param two
     * 		the second name
     * @return Description of the Returned Value
     */
    protected boolean compare(java.lang.String one, java.lang.String two) {
        if (one.length() != two.length()) {
            return false;
        }
        int last = one.length();
        for (int ndx = 0; ndx < last; ndx++) {
            char ch1 = one.charAt(ndx);
            char ch2 = two.charAt(ndx);
            if (ch1 == '/') {
                if (!((ch2 == '/') || (ch2 == '\\'))) {
                    return false;
                }
            } else if (ch1 == '\\') {
                if (!((ch2 == '/') || (ch2 == '\\'))) {
                    return false;
                }
            } else if (ch1 != ch2) {
                return false;
            }
        }
        return true;
    }

    /**
     * Description of the Method
     *
     * @param found
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private boolean isNotInSourceSafe(java.lang.String found) {
        if (found == null) {
            return true;
        }
        java.lang.String trimmed = found.trim();
        if (trimmed == null) {
            return true;
        }
        return trimmed.equals("No matches found.");
    }

    /**
     * Description of the Method
     *
     * @param file
     * 		Description of Parameter
     * @param factory
     * 		Description of Parameter
     * @return Description of the Returned Value
     * @exception IOException
     * 		Description of Exception
     */
    private java.io.BufferedReader executeLocate(java.lang.Runtime factory, java.lang.String file) throws java.io.IOException {
        // Create the executable
        java.lang.String[] args = new java.lang.String[3];
        args[0] = exeFile;
        args[1] = "LOCATE";
        args[2] = getFilename(file);
        java.lang.Process proc = factory.exec(args);
        java.io.InputStream in = proc.getInputStream();
        // Read lines
        return new java.io.BufferedReader(new java.io.InputStreamReader(in));
    }

    /**
     * Description of the Method
     *
     * @param found
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private java.lang.String extractProjectName(java.lang.String found) {
        int last = found.lastIndexOf("/");
        if (last < 1) {
            return found;
        }
        return found.substring(0, last);
    }

    /**
     * Description of the Method
     *
     * @param fullName
     * 		Description of Parameter
     * @param prefix
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private boolean startSame(java.lang.String fullName, java.lang.String prefix) {
        java.lang.System.out.println(((("    Comparing   prefix:[" + prefix) + "]   full:[") + fullName) + "]");
        return fullName.startsWith(prefix);
    }

    /**
     * Main program
     *
     * @param args
     * 		the command line arguments
     */
    public static void main(java.lang.String[] args) {
        if (args.length != 1) {
            java.lang.System.out.println("Syntax:  java org.acm.seguin.version.SourceSafe filename");
            return;
        }
        new org.acm.seguin.version.SourceSafe().checkIn(args[0]);
    }
}