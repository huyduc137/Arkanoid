package game.model;

public abstract class MovableObject extends GameObject {
    protected int dx, dy;
    public MovableObject(int x, int y ,  int width, int height) {
        super(x , y , width, height);
    }
    public void move(){
        x += dx;
        y += dy;
    }
}
