package game.model;

public abstract class MovableObject extends GameObject {
    protected double dx, dy;

    public double getDx() { return dx; }
    public double getDy() { return dy; }

    public void reverseDx(){
        dx = -dx;
    }
    public void reverseDy(){
        dy = -dy;
    }

    public MovableObject(int x, int y ,  int width, int height) {
        super(x , y , width, height);
    }

    public void move(double dt) {
        x += dx * dt;
        y += dy * dt;
    }
}
