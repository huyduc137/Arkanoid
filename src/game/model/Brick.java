package game.model;

public class Brick extends  GameObject {
    public enum BrickType {
        NORMAL,
        UNBREAKABLE
    }
    private int hitPoints;
    private boolean destroyed;
    private BrickType brickType;
    private int score;

    Brick(int x, int y , int width, int height, int hitPoints, BrickType brickType) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.score = hitPoints;
        destroyed = false;
        this.brickType = brickType;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public BrickType getBrickType() {
        return brickType;
    }

    public int getScore() {
        return score;
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
