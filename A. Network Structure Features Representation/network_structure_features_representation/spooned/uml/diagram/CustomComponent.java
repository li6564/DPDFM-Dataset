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
 * @class CustomComponent
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Component that keeps its childrens colors & fonts in sync
 */
public class CustomComponent extends javax.swing.JComponent {
    public void setForeground(java.awt.Color c) {
        super.setForeground(c);
        uml.diagram.CustomComponent.setForeground(this, c);
    }

    public void setBackground(java.awt.Color c) {
        super.setBackground(c);
        uml.diagram.CustomComponent.setBackground(this, c);
    }

    public void setFont(java.awt.Font font) {
        super.setFont(font);
        uml.diagram.CustomComponent.setFont(this, font);
    }

    public static void setBackground(java.awt.Container parent, java.awt.Color c) {
        java.awt.Component[] children = parent.getComponents();
        for (int i = 0; i < children.length; i++) {
            java.awt.Component comp = children[i];
            comp.setBackground(c);
            if (comp instanceof java.awt.Container)
                uml.diagram.CustomComponent.setBackground(((java.awt.Container) (comp)), c);

        }
    }

    public static void setForeground(java.awt.Container parent, java.awt.Color c) {
        java.awt.Component[] children = parent.getComponents();
        for (int i = 0; i < children.length; i++) {
            java.awt.Component comp = children[i];
            comp.setForeground(c);
            if (comp instanceof java.awt.Container)
                uml.diagram.CustomComponent.setForeground(((java.awt.Container) (comp)), c);

        }
    }

    public static void setFont(java.awt.Container parent, java.awt.Font font) {
        java.awt.Component[] children = parent.getComponents();
        for (int i = 0; i < children.length; i++) {
            java.awt.Component comp = children[i];
            comp.setFont(font);
            if (comp instanceof java.awt.Container)
                uml.diagram.CustomComponent.setFont(((java.awt.Container) (comp)), font);

        }
    }
}