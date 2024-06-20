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
 * @class InterfaceComponent
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class InterfaceComponent extends uml.diagram.CustomComponent {
    protected static final uml.diagram.CustomUI interfaceUI = new uml.diagram.CustomUI("interface");

    protected static final java.awt.Insets margin = new java.awt.Insets(1, 1, 1, 1);

    protected javax.swing.JLabel label = new javax.swing.JLabel("<< interface >>", javax.swing.JLabel.CENTER);

    protected javax.swing.JTextField title = new javax.swing.JTextField();

    protected uml.ui.FlatTextArea members = new uml.ui.FlatTextArea(true);

    static {
        // Set up some default colors
        javax.swing.UIManager.put("interface.background", new java.awt.Color(0xff, 0xff, 0xdd));
        javax.swing.UIManager.put("interface.foreground", java.awt.Color.black);
        javax.swing.UIManager.put("interface.border", javax.swing.BorderFactory.createLineBorder(java.awt.Color.black, 1));
    }

    /**
     * Create a new Component for painting interfaces
     */
    public InterfaceComponent() {
        // Layout the component
        this.setLayout(null);
        label.setOpaque(true);
        this.add(label);
        title.setOpaque(true);
        title.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        title.setMargin(uml.diagram.InterfaceComponent.margin);
        title.setBorder(null);
        this.add(title);
        members.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black, 1));
        members.setMargin(uml.diagram.InterfaceComponent.margin);
        this.add(members);
        // Trigger the label to reset its font
        setUI(uml.diagram.InterfaceComponent.interfaceUI);
        setFont(title.getFont());
    }

    public void setTitle(java.lang.String s) {
        title.setText(s);
    }

    public java.lang.String getTitle() {
        return title.getText();
    }

    public void setMembers(java.lang.String s) {
        members.setText(s);
    }

    public java.lang.String getMembers() {
        return members.getText();
    }

    public void setFont(java.awt.Font font) {
        super.setFont(font);
        font = font.deriveFont(java.awt.Font.ITALIC | java.awt.Font.PLAIN, font.getSize() - 2.0F);
        label.setFont(font);
    }

    public void doLayout() {
        java.awt.Insets insets = this.getInsets();
        int w = this.getWidth() - (insets.left + insets.right);
        int h = this.getHeight() - (insets.top + insets.bottom);
        int x = insets.left;
        int y = insets.top;
        // Layout the title across the top
        int componentHeight = label.getPreferredSize().height + 2;
        label.reshape(x + 1, y + 1, w - 2, componentHeight);
        // Shift down, insert room for a border
        y += componentHeight + 1;
        h -= componentHeight + 1;
        // Layout the title across the top
        componentHeight = title.getPreferredSize().height + 2;
        title.setBounds(x + 1, y, w - 2, componentHeight);
        // Shift down, insert room for a border
        y += componentHeight + 1;
        h -= componentHeight + 1;
        // Layout the members at the bottom
        componentHeight = h;
        members.setBounds(x, y, w, componentHeight);
    }

    /**
     * Paint the normal border, and the border around the label & the text field
     */
    public void paintBorder(java.awt.Graphics g) {
        super.paintBorder(g);
        java.awt.Insets insets = this.getInsets();
        int x = insets.left;
        int y = insets.top;
        int w = label.getWidth() + 1;
        int h = (label.getHeight() + title.getHeight()) + 1;
        g.setColor(java.awt.Color.black);
        g.drawRect(x, y, w, h);
    }
}