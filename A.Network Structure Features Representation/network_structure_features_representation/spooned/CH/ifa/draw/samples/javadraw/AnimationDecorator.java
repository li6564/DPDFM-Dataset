/* @(#)AnimationDecorator.java 5.1 */
package CH.ifa.draw.samples.javadraw;
public class AnimationDecorator extends CH.ifa.draw.standard.DecoratorFigure {
    private int fXVelocity;

    private int fYVelocity;

    /* Serialization support. */
    private static final long serialVersionUID = 7894632974364110685L;

    private int animationDecoratorSerializedDataVersion = 1;

    public AnimationDecorator() {
    }

    public AnimationDecorator(CH.ifa.draw.framework.Figure figure) {
        super(figure);
        fXVelocity = 4;
        fYVelocity = 4;
    }

    public void velocity(int xVelocity, int yVelocity) {
        fXVelocity = xVelocity;
        fYVelocity = yVelocity;
    }

    public java.awt.Point velocity() {
        return new java.awt.Point(fXVelocity, fYVelocity);
    }

    public void animationStep() {
        int xSpeed = fXVelocity;
        int ySpeed = fYVelocity;
        java.awt.Rectangle box = displayBox();
        if (((box.x + box.width) > 300) && (xSpeed > 0))
            xSpeed = -xSpeed;

        if (((box.y + box.height) > 300) && (ySpeed > 0))
            ySpeed = -ySpeed;

        if ((box.x < 0) && (xSpeed < 0))
            xSpeed = -xSpeed;

        if ((box.y < 0) && (ySpeed < 0))
            ySpeed = -ySpeed;

        velocity(xSpeed, ySpeed);
        moveBy(xSpeed, ySpeed);
    }

    // guard concurrent access to display box
    public synchronized void basicMoveBy(int x, int y) {
        super.basicMoveBy(x, y);
    }

    public synchronized void basicDisplayBox(java.awt.Point origin, java.awt.Point corner) {
        super.basicDisplayBox(origin, corner);
    }

    public synchronized java.awt.Rectangle displayBox() {
        return super.displayBox();
    }

    // -- store / load ----------------------------------------------
    public void write(CH.ifa.draw.util.StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fXVelocity);
        dw.writeInt(fYVelocity);
    }

    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        super.read(dr);
        fXVelocity = dr.readInt();
        fYVelocity = dr.readInt();
    }
}