/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml;
import java.io.IOException;
/**
 * Draws a UML diagram for all the classes in a package
 *
 * @author Chris Seguin
 */
public class UMLPackage extends org.acm.seguin.uml.line.LinedPanel implements org.acm.seguin.io.Saveable {
    // Instance Variables
    private org.acm.seguin.summary.PackageSummary summary;

    private org.acm.seguin.uml.line.SegmentedLine currentLine = null;

    private boolean hasChanged;

    private java.lang.String packageName;

    private javax.swing.JScrollPane scrollPane;

    private org.acm.seguin.ide.common.ClassListPanel classListPanel = null;

    private boolean first = false;

    private boolean loading = false;

    /**
     * Constructor for UMLPackage
     *
     * @param packageSummary
     * 		the summary of the package
     */
    public UMLPackage(org.acm.seguin.summary.PackageSummary packageSummary) {
        // Initialize the instance variables
        defaultValues();
        setSummary(packageSummary);
        // Don't use a layout manager
        setLayout(null);
        // Load the summaries
        new org.acm.seguin.uml.PackageLoader(this).start(summary);
        // Reset the size
        setSize(getPreferredSize());
        addMouseAdapter();
    }

    /**
     * Constructor for UMLPackage
     *
     * @param filename
     * 		the name of the file
     */
    public UMLPackage(java.lang.String filename) {
        // Initialize the instance variables
        defaultValues();
        // Don't use a layout manager
        setLayout(null);
        // Load the summaries
        new org.acm.seguin.uml.PackageLoader(this).start(filename);
        // Reset the size
        setSize(getPreferredSize());
        addMouseAdapter();
    }

    /**
     * Constructor for UMLPackage
     *
     * @param input
     * 		the input stream
     */
    public UMLPackage(java.io.InputStream input) {
        // Initialize the instance variables
        defaultValues();
        // Don't use a layout manager
        setLayout(null);
        // Load the summaries
        new org.acm.seguin.uml.PackageLoader(this).start(input);
        // Reset the size
        setSize(getPreferredSize());
        addMouseAdapter();
    }

    /**
     * Sets the Dirty attribute of the UMLPackage object
     */
    public void setDirty() {
        hasChanged = true;
    }

    /**
     * Sets the ScrollPane attribute of the UMLPackage object
     *
     * @param value
     * 		The new ScrollPane value
     */
    public void setScrollPane(javax.swing.JScrollPane value) {
        scrollPane = value;
    }

    /**
     * Sets the class list panel
     *
     * @param value
     * 		the new list
     */
    public void setClassListPanel(org.acm.seguin.ide.common.ClassListPanel value) {
        classListPanel = value;
        first = true;
    }

    /**
     * Sets the loading value
     *
     * @param value
     * 		The new Loading value
     */
    public void setLoading(boolean value) {
        loading = value;
    }

    /**
     * Gets the PackageName attribute of the UMLPackage object
     *
     * @return The PackageName value
     */
    public java.lang.String getPackageName() {
        return packageName;
    }

    /**
     * Get the components that are UMLTypes
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.uml.UMLType[] getTypes() {
        // Instance Variables
        java.awt.Component[] children = getComponents();
        int last = children.length;
        int count = 0;
        // Count the UMLTypes
        for (int ndx = 0; ndx < last; ndx++) {
            if (children[ndx] instanceof org.acm.seguin.uml.UMLType) {
                count++;
            }
        }
        // Count the UMLTypes
        org.acm.seguin.uml.UMLType[] results = new org.acm.seguin.uml.UMLType[count];
        int item = 0;
        for (int ndx = 0; ndx < last; ndx++) {
            if (children[ndx] instanceof org.acm.seguin.uml.UMLType) {
                results[item] = ((org.acm.seguin.uml.UMLType) (children[ndx]));
                item++;
            }
        }
        // Return the result
        return results;
    }

    /**
     * Returns the minimum size
     *
     * @return The size
     */
    public java.awt.Dimension getMinimumSize() {
        return getPreferredSize();
    }

    /**
     * Returns the preferred size
     *
     * @return The size
     */
    public java.awt.Dimension getPreferredSize() {
        // Initialize local variables
        int wide = 10;
        int high = 10;
        java.awt.Component[] children = getComponents();
        int last = children.length;
        // Deselect the children
        for (int ndx = 0; ndx < last; ndx++) {
            java.awt.Rectangle bounds = children[ndx].getBounds();
            wide = java.lang.Math.max(wide, (20 + bounds.x) + bounds.width);
            high = java.lang.Math.max(high, (20 + bounds.y) + bounds.height);
        }
        return new java.awt.Dimension(wide, high);
    }

    /**
     * Get the summary
     *
     * @return The package summary
     */
    public org.acm.seguin.summary.PackageSummary getSummary() {
        return summary;
    }

    /**
     * Gets the File attribute of the UMLPackage object
     *
     * @return The File value
     */
    public java.io.File getFile() {
        return new org.acm.seguin.uml.PackageLoader(this).getFile();
    }

    /**
     * Gets the Dirty attribute of the UMLPackage object
     *
     * @return The Dirty value
     */
    public boolean isDirty() {
        return hasChanged;
    }

    /**
     * Determines the title
     *
     * @return the title
     */
    public java.lang.String getTitle() {
        return "UML Diagram for " + packageName;
    }

    /**
     * Remove the association
     *
     * @param field
     * 		Description of Parameter
     */
    public void removeAssociation(org.acm.seguin.uml.UMLField field) {
        java.util.Iterator iter = getLines();
        while (iter.hasNext()) {
            java.lang.Object next = iter.next();
            if (next instanceof org.acm.seguin.uml.line.AssociationRelationship) {
                org.acm.seguin.uml.line.AssociationRelationship assoc = ((org.acm.seguin.uml.line.AssociationRelationship) (next));
                if (assoc.getField().equals(field)) {
                    assoc.delete();
                    iter.remove();
                    return;
                }
            }
        } 
    }

    /**
     * Paint this object
     *
     * @param g
     * 		the graphics object
     */
    public void paint(java.awt.Graphics g) {
        setBackground(java.awt.Color.lightGray);
        g.setColor(java.awt.Color.lightGray);
        java.awt.Dimension size = getSize();
        g.fillRect(0, 0, size.width, size.height);
        // Draw the grid
        java.awt.print.PageFormat pf = org.acm.seguin.uml.print.UMLPagePrinter.getPageFormat(false);
        if (pf != null) {
            int pageHeight = ((int) (org.acm.seguin.uml.print.UMLPagePrinter.getPageHeight()));
            int pageWidth = ((int) (org.acm.seguin.uml.print.UMLPagePrinter.getPageWidth()));
            g.setColor(java.awt.Color.gray);
            for (int x = pageWidth; x < size.width; x += pageWidth) {
                g.drawLine(x, 0, x, size.height);
            }
            for (int y = pageHeight; y < size.width; y += pageHeight) {
                g.drawLine(0, y, size.width, y);
            }
        }
        // Abort once we are loading
        if (loading) {
            return;
        }
        // Draw the segmented lines
        java.util.Iterator iter = getLines();
        while (iter.hasNext()) {
            ((org.acm.seguin.uml.line.SegmentedLine) (iter.next())).paint(g);
        } 
        // Draw the components
        paintChildren(g);
    }

    /**
     * Print this object
     *
     * @param g
     * 		the graphics object
     * @param x
     * 		the x coordinate
     * @param y
     * 		the y coordinate
     */
    public void print(java.awt.Graphics g, int x, int y) {
        java.awt.Component[] children = getComponents();
        int last = children.length;
        for (int ndx = 0; ndx < last; ndx++) {
            if (children[ndx] instanceof org.acm.seguin.uml.UMLType) {
                java.awt.Point pt = children[ndx].getLocation();
                ((org.acm.seguin.uml.UMLType) (children[ndx])).print(g, x + pt.x, y + pt.y);
            } else if (children[ndx] instanceof org.acm.seguin.uml.UMLLine) {
                java.awt.Point pt = children[ndx].getLocation();
                ((org.acm.seguin.uml.UMLLine) (children[ndx])).print(g, x + pt.x, y + pt.y);
            }
        }
        java.util.Iterator iter = getLines();
        while (iter.hasNext()) {
            ((org.acm.seguin.uml.line.SegmentedLine) (iter.next())).paint(g);
        } 
    }

    /**
     * Reloads the UML class diagrams
     */
    public void reload() {
        // Save the image
        try {
            save();
        } catch (java.io.IOException ioe) {
            org.acm.seguin.awt.ExceptionPrinter.print(ioe);
        }
        // Reload it
        new org.acm.seguin.uml.PackageLoader(this).start(summary);
        // Reset the size
        setSize(getPreferredSize());
        reset();
        // Nothing has changed
        hasChanged = false;
    }

    /**
     * Description of the Method
     */
    public void clear() {
        removeAll();
        super.clear();
    }

    /**
     * Determine what you hit
     *
     * @param actual
     * 		The hit location
     */
    public void hit(java.awt.Point actual) {
        currentLine = null;
        java.util.Iterator iter = getLines();
        while ((currentLine == null) && iter.hasNext()) {
            org.acm.seguin.uml.line.SegmentedLine next = ((org.acm.seguin.uml.line.SegmentedLine) (iter.next()));
            if (next.hit(actual)) {
                currentLine = next;
            }
        } 
        while (iter.hasNext()) {
            org.acm.seguin.uml.line.SegmentedLine next = ((org.acm.seguin.uml.line.SegmentedLine) (iter.next()));
            next.select(false);
        } 
        repaint();
    }

    /**
     * Dragging a segmented line point
     *
     * @param actual
     * 		The mouse's current location
     */
    public void drag(java.awt.Point actual) {
        if (currentLine != null) {
            currentLine.drag(actual);
            repaint();
        }
    }

    /**
     * User dropped an item
     */
    public void drop() {
        if (currentLine != null) {
            currentLine.drop();
            hasChanged = true;
            currentLine = null;
        }
        reset();
    }

    /**
     * Save the files
     *
     * @exception IOException
     * 		Description of Exception
     */
    public void save() throws java.io.IOException {
        // Make sure we have something that has changed
        if (!hasChanged) {
            return;
        }
        // Local Variables
        java.awt.Component[] children = getComponents();
        int last = children.length;
        java.io.File dir = summary.getDirectory();
        java.io.File outputFile;
        if (dir == null) {
            dir = new java.io.File((((java.lang.System.getProperty("user.home") + java.io.File.separator) + ".Refactory") + java.io.File.separator) + "UML");
            dir.mkdirs();
            outputFile = new java.io.File(((dir + java.io.File.separator) + summary.getName()) + ".uml");
        } else {
            outputFile = new java.io.File(summary.getDirectory(), "package.uml");
        }
        java.io.PrintWriter output = new java.io.PrintWriter(new java.io.FileWriter(outputFile));
        output.println(("V[1.1:" + summary.getName()) + "]");
        // Save the line segments
        java.util.Iterator iter = getLines();
        while (iter.hasNext()) {
            ((org.acm.seguin.uml.line.SegmentedLine) (iter.next())).save(output);
        } 
        // Save the types
        for (int ndx = 0; ndx < last; ndx++) {
            if (children[ndx] instanceof org.acm.seguin.uml.UMLType) {
                ((org.acm.seguin.uml.UMLType) (children[ndx])).save(output);
            }
        }
        output.close();
        // Nothing has changed
        hasChanged = false;
    }

    /**
     * Tells the scrollbar to jump to this location
     *
     * @param type
     * 		Description of Parameter
     */
    public void jumpTo(org.acm.seguin.summary.TypeSummary type) {
        org.acm.seguin.uml.UMLType umlType = findType(type);
        if (umlType == null) {
            return;
        }
        java.awt.Point pt = umlType.getLocation();
        javax.swing.JScrollBar horiz = scrollPane.getHorizontalScrollBar();
        horiz.setValue(pt.x - 10);
        javax.swing.JScrollBar vert = scrollPane.getVerticalScrollBar();
        vert.setValue(pt.y - 10);
    }

    /**
     * Find the type based on a summary
     *
     * @param searching
     * 		the variable we are searching for
     * @return the UML type object
     */
    protected org.acm.seguin.uml.UMLType findType(org.acm.seguin.summary.TypeSummary searching) {
        // Instance Variables
        java.awt.Component[] children = getComponents();
        int last = children.length;
        int count = 0;
        org.acm.seguin.summary.TypeSummary current;
        if (searching == null) {
            return null;
        }
        // Count the UMLTypes
        for (int ndx = 0; ndx < last; ndx++) {
            if (children[ndx] instanceof org.acm.seguin.uml.UMLType) {
                current = ((org.acm.seguin.uml.UMLType) (children[ndx])).getSummary();
                if (searching.equals(current)) {
                    return ((org.acm.seguin.uml.UMLType) (children[ndx]));
                }
            }
        }
        // Not found
        return null;
    }

    /**
     * Find the type based on a id code
     *
     * @param id
     * 		the code we are searching for
     * @return the UML type object
     */
    protected org.acm.seguin.uml.UMLType find(java.lang.String id) {
        // Instance Variables
        java.awt.Component[] children = getComponents();
        int last = children.length;
        int count = 0;
        java.lang.String current;
        if (id == null) {
            return null;
        }
        // Find the id that matches
        for (int ndx = 0; ndx < last; ndx++) {
            if (children[ndx] instanceof org.acm.seguin.uml.UMLType) {
                current = ((org.acm.seguin.uml.UMLType) (children[ndx])).getID();
                if (id.equals(current)) {
                    return ((org.acm.seguin.uml.UMLType) (children[ndx]));
                }
            }
        }
        // Not found
        return null;
    }

    /**
     * Find the type based on a id code
     *
     * @param panel1
     * 		Description of Parameter
     * @param panel2
     * 		Description of Parameter
     * @return the UML type object
     */
    protected org.acm.seguin.uml.line.SegmentedLine find(java.lang.String panel1, java.lang.String panel2) {
        org.acm.seguin.uml.UMLType first = find(panel1);
        org.acm.seguin.uml.UMLType second = find(panel2);
        if ((first == null) || (second == null)) {
            return null;
        }
        java.util.Iterator iter = getLines();
        while (iter.hasNext()) {
            org.acm.seguin.uml.line.SegmentedLine line = ((org.acm.seguin.uml.line.SegmentedLine) (iter.next()));
            if (line.match(first, second)) {
                return line;
            }
        } 
        return null;
    }

    /**
     * Sets the summary
     *
     * @param value
     * 		The package summary
     */
    void setSummary(org.acm.seguin.summary.PackageSummary value) {
        summary = value;
        if (summary != null)
            packageName = summary.getName();

    }

    /**
     * Tells the class list panel to laod itself
     */
    void updateClassListPanel() {
        if (classListPanel == null) {
            return;
        }
        if (first) {
            first = false;
            return;
        }
        classListPanel.load(summary);
    }

    /**
     * Set up the default values
     */
    private void defaultValues() {
        packageName = "Unknown Package";
        hasChanged = false;
        try {
            org.acm.seguin.util.FileSettings umlBundle = org.acm.seguin.util.FileSettings.getSettings("Refactory", "uml");
            umlBundle.setContinuallyReload(true);
            org.acm.seguin.uml.line.Vertex.setVertexSize(umlBundle.getInteger("sticky.point.size"));
            org.acm.seguin.uml.line.Vertex.setNear(umlBundle.getDouble("halo.size"));
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            org.acm.seguin.uml.line.Vertex.setNear(3.0);
            org.acm.seguin.uml.line.Vertex.setVertexSize(5);
        }
    }

    /**
     * Adds a feature to the MouseAdapter attribute of the UMLPackage object
     */
    private void addMouseAdapter() {
        org.acm.seguin.uml.line.LineMouseAdapter adapter = new org.acm.seguin.uml.line.LineMouseAdapter(this);
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    /**
     * Resets the scroll panes
     */
    private void reset() {
        if (scrollPane == null) {
            repaint();
        } else {
            java.awt.Dimension panelSize = getPreferredSize();
            javax.swing.JViewport view = scrollPane.getViewport();
            java.awt.Dimension viewSize = view.getSize();
            setSize(java.lang.Math.max(panelSize.width, viewSize.width), java.lang.Math.max(panelSize.height, viewSize.height));
            view.setViewSize(getSize());
            scrollPane.repaint();
        }
    }
}