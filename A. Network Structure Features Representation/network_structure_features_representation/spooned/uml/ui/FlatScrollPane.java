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
 * @class FlatScrollPane
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

This is a flat-style scroll pane
 */
public class FlatScrollPane extends javax.swing.JScrollPane {
    public FlatScrollPane() {
        this(null, javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public FlatScrollPane(java.awt.Component view) {
        this(view, javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public FlatScrollPane(int vsbPolicy, int hsbPolicy) {
        this(null, vsbPolicy, hsbPolicy);
    }

    public FlatScrollPane(java.awt.Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
    }

    public void updateUI() {
        setUI(new uml.ui.FlatScrollPane.FlatScrollPaneUI());
    }

    /**
     * Get the background color of the view component or null
     */
    public static java.awt.Color getViewBackground(javax.swing.JScrollPane pane) {
        javax.swing.JViewport viewport = pane.getViewport();
        java.awt.Component view = null;
        if ((viewport != null) && ((view = viewport.getView()) != null))
            return view.getBackground();

        return null;
    }

    public javax.swing.JScrollBar createHorizontalScrollBar() {
        return new uml.ui.FlatScrollBar(javax.swing.JScrollBar.HORIZONTAL);
    }

    public javax.swing.JScrollBar createVerticalScrollBar() {
        return new uml.ui.FlatScrollBar(javax.swing.JScrollBar.VERTICAL);
    }

    /**
     *
     * @class FlatScrollPaneUI
     * @author Eric Crahen
     */
    protected static class FlatScrollPaneUI extends javax.swing.plaf.basic.BasicScrollPaneUI {
        public void installUI(javax.swing.JComponent x) {
            super.installUI(x);
            // Add a transparent corner between the scrollbars
            final javax.swing.JScrollPane thisPane = scrollpane;
            scrollpane.setCorner(getScrollBarCorner(), new java.awt.Component() {
                public void paint(java.awt.Graphics g) {
                    int w = getSize().width;
                    int h = getSize().height;
                    java.awt.Color c = uml.ui.FlatScrollPane.getViewBackground(thisPane);
                    if (c != null)
                        g.setColor(c);

                    g.fillRect(0, 0, w, h);
                }
            });
        }

        public void uninstallUI(javax.swing.JComponent c) {
            scrollpane.setCorner(getScrollBarCorner(), null);
            super.uninstallUI(c);
        }

        protected java.lang.String getScrollBarCorner() {
            return javax.swing.ScrollPaneConstants.LOWER_RIGHT_CORNER;
        }
    }
}