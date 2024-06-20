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
 * @class NoteComponent
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Component used to draw the Note on a Diagram
 */
public class NoteComponent extends uml.diagram.CustomComponent {
    protected static final uml.diagram.CustomUI noteUI = new uml.diagram.CustomUI("note");

    protected static final java.awt.Insets margin = new java.awt.Insets(1, 1, 1, 1);

    protected uml.ui.FlatTextArea text = new uml.ui.FlatTextArea(true);

    static {
        // Set up some default colors
        javax.swing.UIManager.put("note.background", new java.awt.Color(0xff, 0xff, 0xee));
        javax.swing.UIManager.put("note.foreground", java.awt.Color.black);
        javax.swing.UIManager.put("note.border", new uml.diagram.NoteBorder());
    }

    /**
     * Create a new Component for painting Notes
     */
    public NoteComponent() {
        // Layout the component
        setLayout(new java.awt.BorderLayout());
        text.setBorder(null);
        text.setMargin(uml.diagram.NoteComponent.margin);
        add(text);
        setUI(uml.diagram.NoteComponent.noteUI);
    }

    public void setText(java.lang.String s) {
        text.setText(s);
    }

    public java.lang.String getText() {
        return text.getText();
    }
}