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
import java.beans.PropertyChangeEvent;
/**
 *
 * @class CustomUI
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Implments a simple UI that will install a parituclar theme on a component and listen
to the UIManager for changes in that theme, updating the components that using the ui
 */
public class CustomUI extends javax.swing.plaf.ComponentUI implements java.beans.PropertyChangeListener {
    protected java.util.ArrayList components = new java.util.ArrayList();

    protected java.lang.String prefix;

    /**
     */
    public CustomUI(java.lang.String prefix) {
        if (prefix == null)
            throw new java.lang.IllegalArgumentException();

        this.prefix = prefix;
        javax.swing.UIManager.getDefaults().addPropertyChangeListener(this);
    }

    /**
     */
    public void installUI(javax.swing.JComponent c) {
        if (components.contains(c))
            return;

        components.add(c);
        java.awt.Color fg = javax.swing.UIManager.getColor(getPropertyPrefix() + ".foreground");
        if (fg != null)
            c.setForeground(fg);

        java.awt.Color bg = javax.swing.UIManager.getColor(getPropertyPrefix() + ".background");
        if (bg != null)
            c.setBackground(bg);

        javax.swing.border.Border border = javax.swing.UIManager.getBorder(getPropertyPrefix() + ".border");
        if (border != null)
            c.setBorder(border);

        java.awt.Font font = javax.swing.UIManager.getFont(getPropertyPrefix() + ".font");
        if (font != null)
            c.setFont(font);

    }

    /**
     */
    public void uninstallUI(javax.swing.JComponent c) {
        components.remove(c);
    }

    /**
     */
    protected java.lang.String getPropertyPrefix() {
        return prefix;
    }

    /**
     * Listen to the UIManager for any color, font or border adjustments
     *
     * @param PropertyChangeEvent
     */
    public void propertyChange(java.beans.PropertyChangeEvent e) {
        java.lang.String name = e.getPropertyName();
        if (name.equals(getPropertyPrefix() + ".foreground")) {
            java.awt.Color fg = ((java.awt.Color) (e.getNewValue()));
            if (fg != null) {
                for (int i = 0; i < components.size(); i++)
                    ((javax.swing.JComponent) (components.get(i))).setForeground(fg);

            }
        } else if (name.equals(getPropertyPrefix() + ".background")) {
            java.awt.Color bg = ((java.awt.Color) (e.getNewValue()));
            if (bg != null) {
                for (int i = 0; i < components.size(); i++)
                    ((javax.swing.JComponent) (components.get(i))).setBackground(bg);

            }
        } else if (name.equals(getPropertyPrefix() + ".border")) {
            javax.swing.border.Border border = ((javax.swing.border.Border) (e.getNewValue()));
            if (border != null) {
                for (int i = 0; i < components.size(); i++)
                    ((javax.swing.JComponent) (components.get(i))).setBorder(border);

            }
        } else if (name.equals(getPropertyPrefix() + ".font")) {
            java.awt.Font font = ((java.awt.Font) (e.getNewValue()));
            if (font != null) {
                for (int i = 0; i < components.size(); i++)
                    ((javax.swing.JComponent) (components.get(i))).setFont(font);

            }
        }
    }
}