/* @(#)BoxHandleKit.java 5.1 */
package CH.ifa.draw.standard;
class NorthHandle extends CH.ifa.draw.standard.LocatorHandle {
    NorthHandle(CH.ifa.draw.framework.Figure owner) {
        super(owner, CH.ifa.draw.standard.RelativeLocator.north());
    }

    public void invokeStep(int x, int y, int anchorX, int anchorY, CH.ifa.draw.framework.DrawingView view) {
        java.awt.Rectangle r = owner().displayBox();
        owner().displayBox(new java.awt.Point(r.x, java.lang.Math.min(r.y + r.height, y)), new java.awt.Point(r.x + r.width, r.y + r.height));
    }
}