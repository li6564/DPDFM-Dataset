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
package uml.diagram;
/**
 *
 * @class ClassComponent
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class ClassEditorComponent extends uml.diagram.CustomComponent {
    protected static final uml.diagram.CustomUI classUI = new uml.diagram.CustomUI("class");

    protected static final java.awt.Insets margin = new java.awt.Insets(1, 1, 1, 1);

    protected javax.swing.JTextField title = new javax.swing.JTextField();

    protected uml.ui.FlatSplitPane pane;

    protected uml.ui.FlatTextArea fields = new uml.ui.FlatTextArea(true);

    protected uml.ui.FlatTextArea members = new uml.ui.FlatTextArea(true);

    /**
     * Create a new Component for painting classes
     */
    public ClassEditorComponent() {
        // Layout the component
        this.setLayout(null);
        // Title area
        title.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black, 1));
        title.setOpaque(true);
        title.setMargin(uml.diagram.ClassEditorComponent.margin);
        title.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        this.add(title);
        // Field text area
        fields.setBorder(null);
        fields.setMargin(uml.diagram.ClassEditorComponent.margin);
        // Member text area
        members.setBorder(null);
        members.setMargin(uml.diagram.ClassEditorComponent.margin);
        pane = new uml.ui.FlatSplitPane(fields, members);
        pane.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black, 1));
        pane.setDividerSize(2);
        this.add(pane);
        setUI(uml.diagram.ClassEditorComponent.classUI);
        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(new diagram.SelectionBorder(), javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2)), getBorder()));
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        pane.setEnabled(enabled);
    }

    public int getDividerLocation() {
        return pane.getDividerLocation();
    }

    public void setDividerLocation(int lastDividerLocation) {
        pane.setDividerLocation(lastDividerLocation);
    }

    public void setTitle(java.lang.String s) {
        title.setText(s);
    }

    public java.lang.String getTitle() {
        return title.getText();
    }

    public void setFields(java.lang.String s) {
        fields.setText(s);
    }

    public java.lang.String getFields() {
        return fields.getText();
    }

    public void setMembers(java.lang.String s) {
        members.setText(s);
    }

    public java.lang.String getMembers() {
        return members.getText();
    }

    /**
     * Create a built in layout, there seems to be a bug with current LayoutManagers
     * placing TextAreas in scroll panes with borders in the same component correctly.
     * They leave an extra pixel at the bottom with the hieght would be an odd number.
     *
     * This will garuntee the component will be laid out as expected.
     */
    public void doLayout() {
        java.awt.Insets insets = this.getInsets();
        int w = this.getWidth() - (insets.left + insets.right);
        int h = this.getHeight() - (insets.top + insets.bottom);
        int x = insets.left;
        int y = insets.top;
        // Layout the title across the top
        int componentHeight = title.getPreferredSize().height + 2;
        title.setBounds(x, y, w, componentHeight);
        // Shift down
        y += componentHeight;
        h -= componentHeight;
        /* // Layout the fields in the middle
        componentHeight = (int)((double)h*(3.0/8.0));
        fields.setBounds(x, y, w, componentHeight);

        // Shift down
        y += componentHeight;
        h -= componentHeight;

        // Layout the members at the bottom 
        componentHeight = h;
        members.setBounds(x, y, w, componentHeight);
         */
        pane.setBounds(x, y, w, h);
    }
}