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
package diagram;
/**
 *
 * @class FigureComparator
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Implement a comparator that can be used to compare various Figures to one
another. It considers both the figures class and its bounds when making a
comparision
 */
public class FigureComparator implements java.util.Comparator {
    protected java.awt.Rectangle r1 = new java.awt.Rectangle();

    protected java.awt.Rectangle r2 = new java.awt.Rectangle();

    /**
     * Compare two figures to one another
     *
     * @param Object
     * @param Object
     * @return int
     */
    public int compare(java.lang.Object o1, java.lang.Object o2) {
        diagram.Figure f1 = ((diagram.Figure) (o1));
        diagram.Figure f2 = ((diagram.Figure) (o2));
        // Check for equal reference
        if ((f1 == f2) || o1.equals(f2))
            return 0;

        // Check for different classes
        java.lang.Class c1 = f1.getClass();
        java.lang.Class c2 = f2.getClass();
        // Sort on class name
        if (c1 != c2)
            return c1.getName().compareTo(c2.getName());

        // Same class, sort on bounds
        r1 = ((java.awt.Rectangle) (f1.getBounds2D(r1)));
        r2 = ((java.awt.Rectangle) (f2.getBounds2D(r2)));
        // if(r1.hashCode() == r2.hashCode())
        // return 0;
        return r1.hashCode() < r2.hashCode() ? -1 : 1;
    }
}