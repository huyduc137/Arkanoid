package game.model;

public class Brick extends  GameObject{
    private boolean destroyed;
    Brick(int x, int y , int width, int height) {
        super(x, y, width, height);
        destroyed = false;
    }
    public boolean isDestroyed() {
        return destroyed;
    }
}
