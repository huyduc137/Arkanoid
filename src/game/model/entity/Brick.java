package game.model.entity;

import game.model.manager.GraphicsManager;
import game.model.manager.SoundManager;

import java.awt.*;

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
        SoundManager.play("brick_destroy");
    }
    public BrickType getBrickType() {
        return brickType;
    }

    public int getScore() {
        return score;
    }

    public boolean hit() {
        if (hitPoints > 0) {
            hitPoints--;
            if(brickType == BrickType.UNBREAKABLE) {
                SoundManager.play("brick_hit_unbreakable");
            } else {
                SoundManager.play("brick_hit");
            }

            if (hitPoints == 0) {
                setDestroyed(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (brickType == BrickType.UNBREAKABLE) {
            sprite = GraphicsManager.getSprite("brick_unbreakable");
        } else {
            sprite = GraphicsManager.getSprite("brick_" + hitPoints);
        }

        if (!isDestroyed()) {
            if(sprite != null) {
                g.drawImage(sprite, x, y, width, height, null);
            } else {
                System.err.println("No brick_" + hitPoints + " image");
            }
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
