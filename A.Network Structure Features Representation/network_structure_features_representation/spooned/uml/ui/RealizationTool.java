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
import diagram.Figure;
/**
 *
 * @class RealizationTool
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class RealizationTool extends diagram.tool.LinkTool {
    /**
     * Create the Figure for the link
     *
     * @param Figure
     * 		source end
     * @param Figure
     * 		sink end
     * @return Link
     */
    protected diagram.Link createLink(diagram.Figure source, diagram.Figure sink) {
        return new uml.diagram.RealizationLink(source, sink);
    }
}