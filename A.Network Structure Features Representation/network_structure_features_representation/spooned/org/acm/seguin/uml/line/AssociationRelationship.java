/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.line;
/**
 * AssociationRelationship
 *
 * @author Chris Seguin
 * @created August 1, 1999
 */
public class AssociationRelationship extends org.acm.seguin.uml.line.SegmentedLine {
    private org.acm.seguin.uml.UMLField field;

    private boolean dead;

    /**
     * Constructor for the InheretenceRelationship object
     *
     * @param start
     * 		Description of Parameter
     * @param end
     * 		Description of Parameter
     * @param init
     * 		Description of Parameter
     */
    public AssociationRelationship(org.acm.seguin.uml.line.EndPointPanel start, org.acm.seguin.uml.line.EndPointPanel end, org.acm.seguin.uml.UMLField init) {
        super(start, end);
        field = init;
        dead = false;
        java.awt.Point startPt = vertices[vertices.length - 2].getPoint();
        java.awt.Point endPt = vertices[vertices.length - 1].getPoint();
        java.awt.Dimension fieldSize = field.getSize();
        int nX;
        int nY;
        if (startPt.x < endPt.x) {
            nX = (endPt.x - fieldSize.width) - 10;
        } else {
            nX = endPt.x + 10;
        }
        if (startPt.y < endPt.y) {
            nY = (endPt.y - fieldSize.height) - 10;
        } else {
            nY = endPt.y + 10;
        }
        field.setLocation(nX, nY);
    }

    /**
     * Gets the Field attribute of the AssociationRelationship object
     *
     * @return The Field value
     */
    public org.acm.seguin.uml.UMLField getField() {
        return field;
    }

    /**
     * Draws the segmented line
     *
     * @param g
     * 		Description of Parameter
     */
    public void paint(java.awt.Graphics g) {
        super.paint(g);
    }

    /**
     * Saves a segmented to the output stream
     *
     * @param output
     * 		the output stream
     */
    public void save(java.io.PrintWriter output) {
        output.print("A[");
        saveStartPanel(output);
        output.print(",");
        output.print(field.getSummary().getName());
        output.print("]");
        saveVertices(output);
        java.awt.Point pt = field.getUnscaledLocation();
        output.println(((("{" + pt.x) + ",") + pt.y) + "}");
    }

    /**
     * Delete this segmented line
     */
    public void delete() {
        dead = true;
    }

    /**
     * Draws the arrow and the last segment
     *
     * @param g
     * 		the graphics object
     */
    protected void drawArrow(java.awt.Graphics g) {
        int last = vertices.length;
        double X0 = vertices[last - 2].getPoint().getX();
        double Y0 = vertices[last - 2].getPoint().getY();
        java.awt.Point end = vertices[last - 1].getPoint();
        java.awt.Point above = getArrowPointAbove();
        java.awt.Point below = getArrowPointBelow();
        Xs[0] = ((int) (X0));
        Xs[1] = ((int) (end.getX()));
        Xs[2] = ((int) (below.getX()));
        Xs[3] = ((int) (end.getX()));
        Xs[4] = ((int) (above.getX()));
        Ys[0] = ((int) (Y0));
        Ys[1] = ((int) (end.getY()));
        Ys[2] = ((int) (below.getY()));
        Ys[3] = ((int) (end.getY()));
        Ys[4] = ((int) (above.getY()));
        g.drawPolyline(Xs, Ys, 5);
    }

    /**
     * Updates the location of the end vertex
     */
    protected void updateEnd() {
        if (dead) {
            return;
        }
        int last = vertices.length;
        // Remember where we came from
        java.awt.Point temp = vertices[last - 1].getPoint();
        int beforeX = temp.x;
        int beforeY = temp.y;
        // Update the end point
        super.updateEnd();
        // Learn where we went to
        temp = vertices[last - 1].getPoint();
        int afterX = temp.x;
        int afterY = temp.y;
        // Get location of the field
        temp = field.getLocation();
        int X = temp.x;
        int Y = temp.y;
        // Update the field
        field.setLocation((X + afterX) - beforeX, (Y + afterY) - beforeY);
    }
}