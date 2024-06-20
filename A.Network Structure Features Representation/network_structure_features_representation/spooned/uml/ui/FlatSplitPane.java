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
 * @class FlatSplitPane
 * @date 08-27-2001
 * @author Eric Crahen
 * @version 1.0

This class implements a flat-style SplitPane.
 */
public class FlatSplitPane extends javax.swing.JSplitPane {
    public FlatSplitPane() {
    }

    public FlatSplitPane(int newOrientation) {
        super(newOrientation);
    }

    public FlatSplitPane(int newOrientation, boolean newContinuousLayout) {
        super(newOrientation, newContinuousLayout);
    }

    public FlatSplitPane(java.awt.Component newLeftComponent, java.awt.Component newRightComponent) {
        this(javax.swing.JSplitPane.VERTICAL_SPLIT, newLeftComponent, newRightComponent);
    }

    public FlatSplitPane(int newOrientation, java.awt.Component newLeftComponent, java.awt.Component newRightComponent) {
        super(newOrientation, newLeftComponent, newRightComponent);
    }

    public FlatSplitPane(int newOrientation, boolean newContinuousLayout, java.awt.Component newLeftComponent, java.awt.Component newRightComponent) {
        super(newOrientation, newContinuousLayout, newLeftComponent, newRightComponent);
    }

    public void updateUI() {
        setUI(new uml.ui.FlatSplitPane.FlatSplitPaneUI());
    }

    /**
     *
     * @class FlatSplitPaneUI
     */
    /* public void paint(Graphics g, JComponent jc)  {
    super.paint(g, jc);
    System.err.println(getSplitPane().getDividerLocation());
    }
     */
    protected static class FlatSplitPaneUI extends javax.swing.plaf.basic.BasicSplitPaneUI {
        public void installUI(javax.swing.JComponent c) {
            super.installUI(c);
            divider.setBorder(null);
        }

        public javax.swing.plaf.basic.BasicSplitPaneDivider createDefaultDivider() {
            return new uml.ui.FlatSplitPane.FlatSplitPaneDivider(this);
        }
    }

    /**
     *
     * @class FlatSplitPaneDivider
     */
    protected static class FlatSplitPaneDivider extends javax.swing.plaf.basic.BasicSplitPaneDivider {
        public FlatSplitPaneDivider(uml.ui.FlatSplitPane.FlatSplitPaneUI ui) {
            super(ui);
        }

        /**
         * Paints the divider.
         */
        public void paint(java.awt.Graphics g) {
            int w = getWidth();
            int h = getHeight();
            g.fillRect(0, 0, w, h);
        }
    }
}