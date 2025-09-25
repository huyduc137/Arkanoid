package game.model.entity;

public abstract class MovableObject extends GameObject {
    protected double dx, dy;

    public MovableObject(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public double getDx() { return dx; }
    public double getDy() { return dy; }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setDx(int dx) {
        this.dx = (double) dx;
    }

    public void setDy(int dy) {
        this.dy = (double) dy;
    }

    public void reverseDx() {
        dx = -dx;
    }

    public void reverseDy() {
        dy = -dy;
    }

    public void move(double dt) {
        x += dx * dt;
        y += dy * dt;
    }
}
