package game.model;

public class Brick extends  GameObject {
    private int hitPoints;
    private boolean destroyed;

    Brick(int x, int y , int width, int height, int hitPoints) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        destroyed = false;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void hit() {
        if (hitPoints > 0) {
            hitPoints--;
            if (hitPoints == 0) {
                destroyed = true;
            }
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
