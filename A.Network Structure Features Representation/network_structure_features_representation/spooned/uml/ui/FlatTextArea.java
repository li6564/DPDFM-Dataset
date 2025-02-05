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
package uml.ui;
/**
 *
 * @class FlatTextArea
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Creates a flat-style JTextArea . If the snapback flag is set, the text will be scrolled
back to the origin after the text is modified.
 */
public class FlatTextArea extends uml.ui.FlatScrollPane {
    private static final java.awt.Point origin = new java.awt.Point(0, 0);

    private javax.swing.JTextArea textArea = new javax.swing.JTextArea();

    private boolean snapBack;

    /**
     */
    public FlatTextArea(boolean snapBack) {
        // super(VERTICAL_SCROLLBAR_NEVER, HORIZONTAL_SCROLLBAR_NEVER);
        textArea.setBorder(null);
        setOpaque(false);
        setSnappedBack(snapBack);
        setViewportView(textArea);
    }

    /**
     */
    public void setForeground(java.awt.Color c) {
        super.setForeground(c);
        if (textArea != null)
            textArea.setForeground(c);

    }

    /**
     */
    public void setBackground(java.awt.Color c) {
        super.setBackground(c);
        if (textArea != null)
            textArea.setBackground(c);

    }

    /**
     */
    public java.lang.String getText() {
        return textArea.getText();
    }

    /**
     */
    public void setText(java.lang.String text) {
        textArea.setText(text);
        if (snapBack) {
            getViewport().setViewPosition(uml.ui.FlatTextArea.origin);
            textArea.moveCaretPosition(0);
        }
    }

    /**
     */
    public boolean isSnappedBack() {
        return snapBack;
    }

    /**
     */
    public void setSnappedBack(boolean snapBack) {
        this.snapBack = snapBack;
    }

    /**
     */
    public void setMargin(java.awt.Insets margin) {
        textArea.setMargin(margin);
    }

    /**
     */
    public java.awt.Insets getMargin() {
        return textArea.getMargin();
    }
}