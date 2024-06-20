/* @(#)BoxHandleKit.java 5.1 */
package CH.ifa.draw.standard;
class EastHandle extends CH.ifa.draw.standard.LocatorHandle {
    EastHandle(CH.ifa.draw.framework.Figure owner) {
        super(owner, CH.ifa.draw.standard.RelativeLocator.east());
    }

    public void invokeStep(int x, int y, int anchorX, int anchorY, CH.ifa.draw.framework.DrawingView view) {
        java.awt.Rectangle r = owner().displayBox();
        owner().displayBox(new java.awt.Point(r.x, r.y), new java.awt.Point(java.lang.Math.max(r.x, x), r.y + r.height));
    }
}