/* @(#)ElbowConnection.java 5.1 */
package CH.ifa.draw.figures;
class ElbowTextLocator extends CH.ifa.draw.standard.AbstractLocator {
    public java.awt.Point locate(CH.ifa.draw.framework.Figure owner) {
        java.awt.Point p = owner.center();
        java.awt.Rectangle r = owner.displayBox();
        return new java.awt.Point(p.x, p.y - 10);// hack

    }
}