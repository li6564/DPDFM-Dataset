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
 * @class RealizationLinkEditor
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class RealizationLinkEditor extends diagram.DefaultLinkEditor {
    protected uml.diagram.RealizationItem lastValue;

    /**
     */
    public java.lang.Object getCellEditorValue() {
        if (lastValue == null)
            lastValue = new uml.diagram.RealizationItem();

        lastValue.setName(((java.lang.String) (super.getCellEditorValue())));
        return lastValue;
    }
}