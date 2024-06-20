/* @(#)BoxHandleKit.java 5.1 */
package CH.ifa.draw.standard;
class WestHandle extends CH.ifa.draw.standard.LocatorHandle {
    WestHandle(CH.ifa.draw.framework.Figure owner) {
        super(owner, CH.ifa.draw.standard.RelativeLocator.west());
    }

    public void invokeStep(int x, int y, int anchorX, int anchorY, CH.ifa.draw.framework.DrawingView view) {
        java.awt.Rectangle r = owner().displayBox();
        owner().displayBox(new java.awt.Point(java.lang.Math.min(r.x + r.width, x), r.y), new java.awt.Point(r.x + r.width, r.y + r.height));
    }
}