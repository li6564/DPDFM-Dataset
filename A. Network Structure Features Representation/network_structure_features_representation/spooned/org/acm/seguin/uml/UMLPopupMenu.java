/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * UMLPopupMenu
 *
 * @author Chris Seguin
 */
public class UMLPopupMenu {
    // Instance Variables
    private javax.swing.JPopupMenu popupMenu;

    private javax.swing.JPanel activeComponent;

    private org.acm.seguin.uml.UMLPackage current;

    /**
     * Constructor for the UMLPopupMenu object
     *
     * @param top
     * 		the package diagram
     * @param init
     * 		the specific panel
     */
    public UMLPopupMenu(org.acm.seguin.uml.UMLPackage top, javax.swing.JPanel init) {
        // Set the variables
        activeComponent = init;
        current = top;
        popupMenu = createPopupMenu();
        popupMenu.setInvoker(activeComponent);
    }

    /**
     * Get the popup menu
     *
     * @return the popup menu
     */
    public javax.swing.JPopupMenu getMenu() {
        return popupMenu;
    }

    /**
     * Add in the metrics
     *
     * @param menu
     * 		Description of Parameter
     * @return the metrics
     */
    protected javax.swing.JMenuItem getMetricsMenu(javax.swing.JPopupMenu menu) {
        // Add in metrics
        javax.swing.JMenu metrics = new javax.swing.JMenu("Metrics");
        javax.swing.JMenuItem item = new javax.swing.JMenuItem("Project Metrics");
        metrics.add(item);
        org.acm.seguin.uml.ProjectMetricsListener projectML = new org.acm.seguin.uml.ProjectMetricsListener(menu, item);
        item.addMouseListener(projectML);
        item.addActionListener(projectML);
        item = new javax.swing.JMenuItem("Package Metrics");
        metrics.add(item);
        org.acm.seguin.uml.PackageMetricsListener packageML = new org.acm.seguin.uml.PackageMetricsListener(current, menu, item);
        item.addMouseListener(packageML);
        item.addActionListener(packageML);
        item = new javax.swing.JMenuItem("Class Metrics");
        metrics.add(item);
        org.acm.seguin.uml.TypeMetricsListener tml = new org.acm.seguin.uml.TypeMetricsListener(activeComponent, menu, item);
        item.addMouseListener(tml);
        item.addActionListener(tml);
        if (activeComponent == null) {
            // Do nothing
        } else if (activeComponent instanceof org.acm.seguin.uml.UMLMethod) {
            org.acm.seguin.uml.UMLMethod umlMethod = ((org.acm.seguin.uml.UMLMethod) (activeComponent));
            item = new javax.swing.JMenuItem("Method Metrics");
            metrics.add(item);
            org.acm.seguin.uml.MethodMetricsListener listener = new org.acm.seguin.uml.MethodMetricsListener(umlMethod.getSummary(), menu, item);
            item.addMouseListener(listener);
            item.addActionListener(listener);
        }
        // Return the value
        return metrics;
    }

    /**
     * Create the popup menu
     *
     * @return Description of the Returned Value
     */
    protected javax.swing.JPopupMenu createPopupMenu() {
        javax.swing.JMenuItem item;
        javax.swing.JPopupMenu menu = new javax.swing.JPopupMenu("UML Diagram");
        if (activeComponent == null) {
            // Do nothing
        } else if (activeComponent instanceof org.acm.seguin.uml.UMLField) {
            org.acm.seguin.uml.UMLField field = ((org.acm.seguin.uml.UMLField) (activeComponent));
            if (field.isAssociation()) {
                item = new javax.swing.JMenuItem("Convert to Attribute");
            } else {
                item = new javax.swing.JMenuItem("Convert to Association");
            }
            item.setEnabled(field.isConvertable());
            menu.add(item);
            item.addMouseListener(new org.acm.seguin.uml.PopupMenuListener(menu, item));
            item.addActionListener(new org.acm.seguin.uml.ConvertAdapter(current, field));
            menu.addSeparator();
        }
        // Add refactorings
        addRefactorings(menu);
        menu.addSeparator();
        // Add metrics
        item = getMetricsMenu(menu);
        menu.add(item);
        // Add source link
        if (org.acm.seguin.ide.common.SourceBrowser.get().canBrowseSource()) {
            menu.addSeparator();
            item = new javax.swing.JMenuItem("Show source");
            org.acm.seguin.ide.common.SourceBrowserAdapter adapter = new org.acm.seguin.ide.common.SourceBrowserAdapter(((org.acm.seguin.uml.ISourceful) (activeComponent)));
            item.addActionListener(adapter);
            menu.add(item);
        }
        // Return the menu
        return menu;
    }

    /**
     * Refactorings
     *
     * @param menu
     * 		The feature to be added to the Refactorings attribute
     */
    protected void addRefactorings(javax.swing.JPopupMenu menu) {
        addTypeRefactorings(menu);
        if (activeComponent == null) {
            // Do nothing
        } else if (activeComponent instanceof org.acm.seguin.uml.UMLMethod) {
            addMethodRefactorings(menu);
        } else if (activeComponent instanceof org.acm.seguin.uml.UMLField) {
            addFieldRefactorings(menu);
        }
    }

    /**
     * Gets the Type attribute of the UMLPopupMenu object
     *
     * @return The Type value
     */
    private org.acm.seguin.uml.UMLType getType() {
        if (activeComponent instanceof org.acm.seguin.uml.UMLType) {
            return ((org.acm.seguin.uml.UMLType) (activeComponent));
        } else if (activeComponent instanceof org.acm.seguin.uml.UMLLine) {
            return ((org.acm.seguin.uml.UMLLine) (activeComponent)).getParentType();
        }
        return null;
    }

    /**
     * Gets the Type attribute of the UMLPopupMenu object
     *
     * @return The Type value
     */
    private org.acm.seguin.summary.TypeSummary getTypeSummary() {
        org.acm.seguin.uml.UMLType umlType = getType();
        if (umlType == null) {
            return null;
        }
        return umlType.getSummary();
    }

    /**
     * Adds a feature to the FieldRefactorings attribute of the UMLPopupMenu
     *  object
     *
     * @param menu
     * 		The feature to be added to the FieldRefactorings attribute
     */
    private void addFieldRefactorings(javax.swing.JPopupMenu menu) {
        // Add in metrics
        javax.swing.JMenu fieldRefactorings = new javax.swing.JMenu("Field Refactorings");
        menu.add(fieldRefactorings);
        javax.swing.JMenuItem item = new javax.swing.JMenuItem("Rename");
        fieldRefactorings.add(item);
        item.setEnabled(true);
        org.acm.seguin.uml.refactor.RenameFieldListener renameListener = new org.acm.seguin.uml.refactor.RenameFieldListener(current, ((org.acm.seguin.uml.UMLField) (activeComponent)).getSummary(), menu, item);
        item.addMouseListener(renameListener);
        item.addActionListener(renameListener);
        item = new javax.swing.JMenuItem("Push Up");
        fieldRefactorings.add(item);
        item.setEnabled(true);
        org.acm.seguin.uml.refactor.PushUpFieldListener pushUpListener = new org.acm.seguin.uml.refactor.PushUpFieldListener(current, getTypeSummary(), ((org.acm.seguin.uml.UMLField) (activeComponent)).getSummary(), menu, item);
        item.addMouseListener(pushUpListener);
        item.addActionListener(pushUpListener);
        item = new javax.swing.JMenuItem("Push Down");
        fieldRefactorings.add(item);
        item.setEnabled(true);
        org.acm.seguin.uml.refactor.PushDownFieldListener pushDownListener = new org.acm.seguin.uml.refactor.PushDownFieldListener(current, getTypeSummary(), ((org.acm.seguin.uml.UMLField) (activeComponent)).getSummary(), menu, item);
        item.addMouseListener(pushDownListener);
        item.addActionListener(pushDownListener);
    }

    /**
     * Adds a feature to the MethodRefactorings attribute of the UMLPopupMenu
     *  object
     *
     * @param menu
     * 		The feature to be added to the MethodRefactorings attribute
     */
    private void addMethodRefactorings(javax.swing.JPopupMenu menu) {
        org.acm.seguin.summary.MethodSummary methodSummary = ((org.acm.seguin.uml.UMLMethod) (activeComponent)).getSummary();
        // Add in metrics
        javax.swing.JMenu methodRefactorings = new javax.swing.JMenu("Method Refactorings");
        menu.add(methodRefactorings);
        javax.swing.JMenuItem item = new javax.swing.JMenuItem("Rename");
        item.setEnabled(false);
        methodRefactorings.add(item);
        item.addMouseListener(new org.acm.seguin.uml.PopupMenuListener(menu, item));
        item = new javax.swing.JMenuItem("Push Up");
        methodRefactorings.add(item);
        item.setEnabled(true);
        org.acm.seguin.uml.refactor.PushUpMethodListener pushUpListener = new org.acm.seguin.uml.refactor.PushUpMethodListener(current, ((org.acm.seguin.uml.UMLMethod) (activeComponent)).getSummary(), menu, item);
        item.addMouseListener(pushUpListener);
        item.addActionListener(pushUpListener);
        item = new javax.swing.JMenuItem("Push Up (Abstract)");
        methodRefactorings.add(item);
        item.setEnabled(true);
        org.acm.seguin.uml.refactor.PushUpAbstractMethodListener pushUpAbsListener = new org.acm.seguin.uml.refactor.PushUpAbstractMethodListener(current, ((org.acm.seguin.uml.UMLMethod) (activeComponent)).getSummary(), menu, item);
        item.addMouseListener(pushUpAbsListener);
        item.addActionListener(pushUpAbsListener);
        item = new javax.swing.JMenuItem("Push Down");
        methodRefactorings.add(item);
        item.setEnabled(true);
        org.acm.seguin.uml.refactor.PushDownMethodListener pushDownListener = new org.acm.seguin.uml.refactor.PushDownMethodListener(current, getTypeSummary(), methodSummary, menu, item);
        item.addMouseListener(pushDownListener);
        item.addActionListener(pushDownListener);
        item = new javax.swing.JMenuItem("Move Method");
        methodRefactorings.add(item);
        item.setEnabled(methodSummary.getParameterCount() > 0);
        org.acm.seguin.uml.refactor.MoveMethodListener moveListener = new org.acm.seguin.uml.refactor.MoveMethodListener(current, getTypeSummary(), methodSummary, menu, item);
        item.addMouseListener(moveListener);
        item.addActionListener(moveListener);
        if (methodSummary.getParameterCount() == 0) {
            item = new javax.swing.JMenuItem("Rename Parameters");
            item.setEnabled(false);
            methodRefactorings.add(item);
        } else {
            javax.swing.JMenu rename = new javax.swing.JMenu("Rename Parameter:");
            methodRefactorings.add(rename);
            java.util.Iterator iter = methodSummary.getParameters();
            while (iter.hasNext()) {
                org.acm.seguin.summary.ParameterSummary next = ((org.acm.seguin.summary.ParameterSummary) (iter.next()));
                item = new javax.swing.JMenuItem(next.getName());
                rename.add(item);
                org.acm.seguin.uml.refactor.RenameParameterListener rpl = new org.acm.seguin.uml.refactor.RenameParameterListener(menu, item, current, next);
                item.addMouseListener(rpl);
                item.addActionListener(rpl);
            } 
        }
    }

    /**
     * Adds a feature to the TypeRefactorings attribute of the UMLPopupMenu
     *  object
     *
     * @param menu
     * 		The feature to be added to the TypeRefactorings attribute
     */
    private void addTypeRefactorings(javax.swing.JPopupMenu menu) {
        org.acm.seguin.summary.TypeSummary[] typeArray = org.acm.seguin.uml.SelectedSummaryList.list(current, getType());
        // Add in metrics
        javax.swing.JMenu typeRefactorings = new javax.swing.JMenu("Type Refactorings");
        menu.add(typeRefactorings);
        javax.swing.JMenuItem item = new javax.swing.JMenuItem("Rename Class");
        typeRefactorings.add(item);
        org.acm.seguin.uml.refactor.DialogViewListener rcl = new org.acm.seguin.uml.refactor.AddRenameClassListener(current, getTypeSummary(), menu, item);
        item.addMouseListener(rcl);
        item.addActionListener(rcl);
        // Add in moving a class
        item = new javax.swing.JMenuItem("Move Class To");
        item.setEnabled(true);
        typeRefactorings.add(item);
        org.acm.seguin.uml.refactor.DialogViewListener ncl = new org.acm.seguin.uml.refactor.AddMoveClassListener(typeArray, menu, item);
        item.addMouseListener(ncl);
        item.addActionListener(ncl);
        // Add in a parent class
        item = new javax.swing.JMenuItem("Add Abstract Parent Class");
        item.setEnabled(true);
        typeRefactorings.add(item);
        org.acm.seguin.uml.refactor.DialogViewListener aapl = new org.acm.seguin.uml.refactor.AddParentClassListener(current, typeArray, menu, item);
        item.addMouseListener(aapl);
        item.addActionListener(aapl);
        // Add in a child class
        item = new javax.swing.JMenuItem("Add Child Class");
        typeRefactorings.add(item);
        item.setEnabled(true);
        org.acm.seguin.uml.refactor.DialogViewListener accl = new org.acm.seguin.uml.refactor.AddChildClassListener(current, getTypeSummary(), menu, item);
        item.addMouseListener(accl);
        item.addActionListener(accl);
        // Remove a child class
        item = new javax.swing.JMenuItem("Remove Class");
        typeRefactorings.add(item);
        item.setEnabled(true);
        org.acm.seguin.uml.refactor.RemoveClassListener removeListener = new org.acm.seguin.uml.refactor.RemoveClassListener(current, getTypeSummary(), menu, item);
        item.addMouseListener(removeListener);
        item.addActionListener(removeListener);
        // Extract Interface
        item = new javax.swing.JMenuItem("Extract Interface");
        typeRefactorings.add(item);
        item.setEnabled(true);
        org.acm.seguin.uml.refactor.DialogViewListener eil = new org.acm.seguin.uml.refactor.ExtractInterfaceListener(current, typeArray, menu, item);
        item.addMouseListener(eil);
        item.addActionListener(eil);
    }
}