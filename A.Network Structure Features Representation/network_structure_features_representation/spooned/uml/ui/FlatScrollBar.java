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
 * @class FlatScrollBar
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

This is a flat-style scroll bar.
 */
public class FlatScrollBar extends javax.swing.JScrollBar {
    public FlatScrollBar() {
        this(java.awt.Adjustable.VERTICAL);
    }

    public FlatScrollBar(int orientation) {
        super(orientation);
    }

    public void updateUI() {
        setUI(new uml.ui.FlatScrollBar.FlatScrollBarUI());
    }

    /**
     *
     * @class FlatScrollBarUI
     * @author Eric Crahen
     */
    public static class FlatScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        protected void installDefaults() {
            super.installDefaults();
            scrollbar.setBorder(null);
        }

        protected javax.swing.plaf.basic.BasicScrollBarUI.ModelListener createModelListener() {
            return new javax.swing.plaf.basic.BasicScrollBarUI.ModelListener() {
                public void stateChanged(javax.swing.event.ChangeEvent e) {
                    super.stateChanged(e);
                    javax.swing.BoundedRangeModel mdl = ((javax.swing.BoundedRangeModel) (e.getSource()));
                    showDecrementButton(mdl.getValue() != 0);
                    showIncrementButton((mdl.getValue() + mdl.getExtent()) != mdl.getMaximum());
                }
            };
        }

        public void paint(java.awt.Graphics g, javax.swing.JComponent comp) {
            int w = scrollbar.getSize().width;
            int h = scrollbar.getSize().height;
            java.awt.Color c = uml.ui.FlatScrollPane.getViewBackground(((javax.swing.JScrollPane) (comp.getParent())));
            if (c != null)
                g.setColor(c);

            g.fillRect(0, 0, w, h);
        }

        public java.awt.Dimension getPreferredSize(javax.swing.JComponent c) {
            return scrollbar.getOrientation() == javax.swing.JScrollBar.VERTICAL ? new java.awt.Dimension(10, 48) : new java.awt.Dimension(48, 10);
        }

        protected void paintDecreaseHighlight(java.awt.Graphics g) {
        }

        protected void paintIncreaseHighlight(java.awt.Graphics g) {
        }

        protected javax.swing.JButton createDecreaseButton(int orientation) {
            return new uml.ui.FlatArrowButton(orientation);
        }

        protected javax.swing.JButton createIncreaseButton(int orientation) {
            return new uml.ui.FlatArrowButton(orientation);
        }

        private final void showIncrementButton(boolean flag) {
            incrButton.setVisible(flag);
        }

        private final void showDecrementButton(boolean flag) {
            decrButton.setVisible(flag);
        }
    }
}