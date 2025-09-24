package game.model;

public abstract class MovableObject extends GameObject {
    protected int dx, dy;

    public int getDx() { return dx; }
    public int getDy() { return dy; }

    public void reverseDx(){
        dx = -dx;
    }
    public void reverseDy(){
        dy = -dy;
    }

    public MovableObject(int x, int y ,  int width, int height) {
        super(x , y , width, height);
    }

    public void move(){
        x += dx;
        y += dy;
    }
}
