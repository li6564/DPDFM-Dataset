/**
 * Java Diagram Package; An extremely flexible and fast multipurpose diagram
 * component for Swing.
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
package diagram.shape;
import java.awt.geom.GeneralPath;
/**
 *
 * @class DiamondHead
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

This shape can create an diamond head whose center is at the local coordinates
(0,0).
 */
public class DiamondHead {
    /**
     * Create an diamond head
     *
     * @param GeneralPath
     * @return GeneralPath
     */
    public static java.awt.geom.GeneralPath createDiamondHead(double x, double y, double w, double h) {
        return diagram.shape.DiamondHead.createDiamondHead(x, y, w, h, null);
    }

    public static java.awt.geom.GeneralPath createDiamondHead(double x, double y, double w, double h, java.awt.geom.GeneralPath path) {
        if (path == null)
            path = new java.awt.geom.GeneralPath();
        else
            path.reset();

        path.moveTo(((float) (x)), ((float) (y)));
        path.lineTo(((float) (x + (1.5 * w))), ((float) (y - h)));
        path.lineTo(((float) (x + (3.0 * w))), ((float) (y)));
        path.lineTo(((float) (x + (1.5 * w))), ((float) (y + h)));
        path.closePath();
        return path;
    }
}