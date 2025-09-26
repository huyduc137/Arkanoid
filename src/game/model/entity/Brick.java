package game.model.entity;

public class Brick extends GameObject {
    public enum BrickType {
        NORMAL,
        UNBREAKABLE
    }
    private int hitPoints;
    private boolean destroyed;
    private final BrickType brickType;
    private final int score;

    public Brick(int x, int y, int width, int height, int hitPoints, BrickType brickType) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.score = hitPoints;
        destroyed = false;
        this.brickType = brickType;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
    public int getHitPoints() {
        return hitPoints;
    }
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
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
