/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml;
import java.io.IOException;
/**
 * Loads a UMLPackage panel from a package summary
 *
 * @author Chris Seguin
 */
class PackageLoader extends java.lang.Thread {
    private org.acm.seguin.uml.UMLPackage packagePanel;

    private int defaultX;

    private int defaultY;

    private boolean loaded;

    private java.lang.String filename;

    private org.acm.seguin.summary.PackageSummary loadSummary;

    private java.io.File loadFile;

    private java.io.InputStream loadStream;

    /**
     * Constructor for the PackageLoader object
     *
     * @param panel
     * 		the panel that we are loading
     */
    public PackageLoader(org.acm.seguin.uml.UMLPackage panel) {
        packagePanel = panel;
        defaultX = 20;
        defaultY = 20;
        loaded = false;
    }

    /**
     * Main processing method for the PackageLoader object
     */
    public void run() {
        /* Don't run this until we have completed loading the
        summaries from disk
         */
        org.acm.seguin.ide.common.SummaryLoaderThread.waitForLoading();
        synchronized(org.acm.seguin.uml.PackageLoader.class) {
            packagePanel.setLoading(true);
            packagePanel.clear();
            if (loadSummary != null) {
                load(loadSummary);
            }
            if (loadFile != null) {
                load(loadFile);
            }
            if (loadStream != null) {
                load(loadStream);
            }
            packagePanel.updateClassListPanel();
            packagePanel.setLoading(false);
            packagePanel.repaint();
        }
    }

    /**
     * Description of the Method
     *
     * @param summary
     * 		Description of Parameter
     */
    public void start(org.acm.seguin.summary.PackageSummary summary) {
        loadSummary = summary;
        loadStream = null;
        loadFile = null;
        super.start();
    }

    /**
     * Description of the Method
     *
     * @param filename
     * 		Description of Parameter
     */
    public void start(java.lang.String filename) {
        loadSummary = null;
        loadStream = null;
        loadFile = new java.io.File(filename);
        super.start();
    }

    /**
     * Description of the Method
     *
     * @param input
     * 		Description of Parameter
     */
    public void start(java.io.InputStream input) {
        loadSummary = null;
        loadStream = input;
        loadFile = null;
        super.start();
    }

    /**
     * Gets the File attribute of the PackageLoader object
     *
     * @return The File value
     */
    java.io.File getFile() {
        org.acm.seguin.summary.PackageSummary summary = packagePanel.getSummary();
        java.io.File dir = summary.getDirectory();
        java.io.File inputFile;
        if (dir == null) {
            dir = new java.io.File((((java.lang.System.getProperty("user.home") + java.io.File.separator) + ".Refactory") + java.io.File.separator) + "UML");
            dir.mkdirs();
            inputFile = new java.io.File(dir, summary.getName() + ".uml");
        } else {
            inputFile = new java.io.File(summary.getDirectory(), "package.uml");
        }
        return inputFile;
    }

    /**
     * Returns the UMLType panel associated with a summary or null if none is
     *  available
     *
     * @param summary
     * 		the type declaration
     * @return the panel associated with the type
     */
    private org.acm.seguin.uml.UMLType getUMLType(org.acm.seguin.summary.TypeDeclSummary summary) {
        if (summary != null) {
            org.acm.seguin.summary.TypeSummary typeSummary = org.acm.seguin.summary.query.GetTypeSummary.query(summary);
            if (typeSummary != null) {
                org.acm.seguin.uml.UMLType typePanel = packagePanel.findType(typeSummary);
                if (typePanel == null) {
                    typePanel = addType(typeSummary, true);
                }
                return typePanel;
            }
        }
        return null;
    }

    /**
     * Returns the location of this class
     *
     * @param umlType
     * 		the type panel
     * @param summary
     * 		the type summary
     * @return the point where this type panel should be located
     */
    private java.awt.Point getLocation(org.acm.seguin.uml.UMLType umlType, org.acm.seguin.summary.TypeSummary summary) {
        java.awt.Dimension dim = umlType.getPreferredSize();
        java.awt.Point result = new java.awt.Point(defaultX, defaultY);
        defaultX += 20 + dim.width;
        return result;
    }

    /**
     * Reload the summaries
     *
     * @param summary
     * 		the package summary
     */
    private void load(org.acm.seguin.summary.PackageSummary summary) {
        if (summary != null) {
            defaultPositions(summary);
        }
        loadPositions(getFile());
    }

    /**
     * Reload the summaries
     *
     * @param file
     * 		Description of Parameter
     */
    private void load(java.io.File file) {
        loadPositions(file);
    }

    /**
     * Reload the summaries
     *
     * @param input
     * 		Description of Parameter
     */
    private void load(java.io.InputStream input) {
        loadPositions(input);
    }

    /**
     * Loads all the classes into their default positions
     *
     * @param summary
     * 		the package that is being loaded
     */
    private void defaultPositions(org.acm.seguin.summary.PackageSummary summary) {
        // Add all the types
        java.util.Iterator iter = summary.getFileSummaries();
        if (iter != null) {
            while (iter.hasNext()) {
                addFile(((org.acm.seguin.summary.FileSummary) (iter.next())));
            } 
        }
        loadInheretence();
        loadImplements();
        loaded = true;
    }

    /**
     * Adds all the types in a file
     *
     * @param fileSummary
     * 		the file summary
     */
    private void addFile(org.acm.seguin.summary.FileSummary fileSummary) {
        java.util.Iterator iter = fileSummary.getTypes();
        if (iter != null) {
            while (iter.hasNext()) {
                addType(((org.acm.seguin.summary.TypeSummary) (iter.next())), false);
            } 
        }
    }

    /**
     * Adds a UML type
     *
     * @param typeSummary
     * 		the type summary being added
     * @param foreign
     * 		whether this type is in this package (true means it is
     * 		from a different package
     * @return The panel
     */
    private org.acm.seguin.uml.UMLType addType(org.acm.seguin.summary.TypeSummary typeSummary, boolean foreign) {
        org.acm.seguin.uml.UMLType umlType = new org.acm.seguin.uml.UMLType(packagePanel, typeSummary, foreign);
        packagePanel.add(umlType);
        umlType.setLocation(getLocation(umlType, typeSummary));
        return umlType;
    }

    /**
     * Loads the inheritence relationships
     */
    private void loadInheretence() {
        org.acm.seguin.uml.UMLType[] typeList = packagePanel.getTypes();
        for (int ndx = 0; ndx < typeList.length; ndx++) {
            org.acm.seguin.summary.TypeSummary current = typeList[ndx].getSummary();
            org.acm.seguin.summary.TypeDeclSummary parent = current.getParentClass();
            org.acm.seguin.uml.UMLType typePanel = getUMLType(parent);
            if (typePanel != null) {
                packagePanel.add(new org.acm.seguin.uml.line.InheretenceRelationship(typeList[ndx], typePanel));
            }
        }
    }

    /**
     * Loads the inheritence relationships
     */
    private void loadImplements() {
        org.acm.seguin.uml.UMLType[] typeList = packagePanel.getTypes();
        for (int ndx = 0; ndx < typeList.length; ndx++) {
            if (typeList[ndx].isForeign()) {
                continue;
            }
            org.acm.seguin.summary.TypeSummary current = typeList[ndx].getSummary();
            java.util.Iterator iter = current.getImplementedInterfaces();
            if (iter != null) {
                while (iter.hasNext()) {
                    org.acm.seguin.summary.TypeDeclSummary next = ((org.acm.seguin.summary.TypeDeclSummary) (iter.next()));
                    org.acm.seguin.uml.UMLType typePanel = getUMLType(next);
                    if (typePanel != null) {
                        org.acm.seguin.uml.line.SegmentedLine nextLine;
                        if (current.isInterface()) {
                            nextLine = new org.acm.seguin.uml.line.InheretenceRelationship(typeList[ndx], typePanel);
                        } else {
                            nextLine = new org.acm.seguin.uml.line.ImplementsRelationship(typeList[ndx], typePanel);
                        }
                        packagePanel.add(nextLine);
                    }
                } 
            }
        }
    }

    /**
     * Loads the package from disk
     *
     * @param inputFile
     * 		Description of Parameter
     */
    private void loadPositions(java.io.File inputFile) {
        try {
            java.io.FileReader fr = new java.io.FileReader(inputFile);
            java.io.BufferedReader input = new java.io.BufferedReader(fr);
            loadPositions(input);
        } catch (java.io.FileNotFoundException fnfe) {
            // This is a normal and expected condition
        } catch (java.io.IOException ioe) {
            org.acm.seguin.awt.ExceptionPrinter.print(ioe);
        }
    }

    /**
     * Loads the package from disk
     *
     * @param inputStream
     * 		Description of Parameter
     */
    private void loadPositions(java.io.InputStream inputStream) {
        try {
            java.io.InputStreamReader fr = new java.io.InputStreamReader(inputStream);
            java.io.BufferedReader input = new java.io.BufferedReader(fr);
            loadPositions(input);
            input.close();
            fr.close();
        } catch (java.io.IOException ioe) {
            org.acm.seguin.awt.ExceptionPrinter.print(ioe);
        }
    }

    /**
     * Loads the package from disk
     *
     * @param input
     * 		Description of Parameter
     * @exception IOException
     * 		Description of Exception
     */
    private void loadPositions(java.io.BufferedReader input) throws java.io.IOException {
        java.lang.String line = input.readLine();
        while (line != null) {
            // Decide what to do
            char ch = line.charAt(0);
            if (ch == 'P') {
                positionPanel(line);
            } else if (ch == 'S') {
                positionLine(line);
            } else if (ch == 'A') {
                positionAttribute(line);
            } else if (ch == 'V') {
                loadVersion(line);
            }
            // Read the next line
            line = input.readLine();
        } 
    }

    /**
     * position the type in the UMLPackage
     *
     * @param buffer
     * 		the line that describes where to position the type
     */
    private void positionPanel(java.lang.String buffer) {
        java.util.StringTokenizer tok = new java.util.StringTokenizer(buffer, "[]{},\n");
        java.lang.String code = tok.nextToken();
        java.lang.String id = tok.nextToken();
        java.lang.String x = tok.nextToken();
        java.lang.String y = tok.nextToken();
        org.acm.seguin.uml.UMLType type = packagePanel.find(id);
        if (type == null) {
            return;
        }
        java.awt.Point pt = type.getLocation();
        int nX = pt.x;
        int nY = pt.y;
        try {
            nX = java.lang.Integer.parseInt(x);
            nY = java.lang.Integer.parseInt(y);
        } catch (java.lang.NumberFormatException nfe) {
        }
        type.setLocation(nX, nY);
    }

    /**
     * Position the line
     *
     * @param buffer
     * 		the line read from the file
     */
    private void positionLine(java.lang.String buffer) {
        java.util.StringTokenizer tok = new java.util.StringTokenizer(buffer, "[]{}\n");
        java.lang.String code = tok.nextToken();
        java.lang.String pair = tok.nextToken();
        java.lang.String position = tok.nextToken();
        tok = new java.util.StringTokenizer(pair, ",");
        java.lang.String first = tok.nextToken();
        java.lang.String second = tok.nextToken();
        org.acm.seguin.uml.line.SegmentedLine line = packagePanel.find(first, second);
        if (line != null) {
            line.load(position);
        }
    }

    /**
     * The attribute
     *
     * @param buffer
     * 		the input string
     */
    private void positionAttribute(java.lang.String buffer) {
        java.util.StringTokenizer tok = new java.util.StringTokenizer(buffer, "[]{}\n");
        java.lang.String code = tok.nextToken();
        java.lang.String pair = tok.nextToken();
        java.lang.String position = tok.nextToken();
        java.lang.String fieldPosition = tok.nextToken();
        tok = new java.util.StringTokenizer(pair, ",");
        java.lang.String first = tok.nextToken();
        java.lang.String second = tok.nextToken();
        org.acm.seguin.uml.UMLType type = packagePanel.find(first);
        if (type == null) {
            return;
        }
        org.acm.seguin.uml.UMLField field = type.getField(second);
        if (field == null) {
            return;
        }
        field.setAssociation(true);
        org.acm.seguin.uml.line.AssociationRelationship ar = type.convertToAssociation(packagePanel, field);
        ar.load(position);
        // Set the field label position
        tok = new java.util.StringTokenizer(fieldPosition, ",");
        java.lang.String x = tok.nextToken();
        java.lang.String y = tok.nextToken();
        try {
            field.setLocation(java.lang.Integer.parseInt(x), java.lang.Integer.parseInt(y));
        } catch (java.lang.NumberFormatException nfe) {
        }
    }

    /**
     * Loads a version line
     *
     * @param buffer
     * 		the input line that contains the version number and package
     * 		name
     */
    private void loadVersion(java.lang.String buffer) {
        java.util.StringTokenizer tok = new java.util.StringTokenizer(buffer, "[]:\n");
        java.lang.String key = tok.nextToken();
        java.lang.String versionID = tok.nextToken();
        java.lang.String packageName = "";
        if (tok.hasMoreTokens()) {
            packageName = tok.nextToken();
        }
        java.lang.System.out.println((("Loading:  " + packageName) + " from a file with version ") + versionID);
        if (!loaded) {
            org.acm.seguin.summary.PackageSummary summary = org.acm.seguin.summary.PackageSummary.getPackageSummary(packageName);
            packagePanel.setSummary(summary);
            defaultPositions(summary);
        }
    }
}