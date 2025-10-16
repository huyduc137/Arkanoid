package game.model.entity;

import game.model.manager.GraphicsManager;
import game.model.manager.SoundManager;

import java.awt.*;

public class Brick extends MovableObject {
    public enum BrickType {
        NORMAL,
        UNBREAKABLE,
        ATTACK
    }
    private int hitPoints;
    private boolean destroyed;
    private final BrickType brickType;
    private final int score;

    private double attackTimer = 0.0;
    private boolean attacking = false;
    private boolean returning = false;
    private double originalX, originalY;
    private double targetX, targetY;

    public Brick(int x, int y, int width, int height, int hitPoints, BrickType brickType) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.score = hitPoints;
        destroyed = false;
        this.brickType = brickType;
    }

    public void setOriginalPosition(double x, double y) {
        this.originalX = x;
        this.originalY = y;
    }
    public double getOriginalX() { return originalX; }
    public double getOriginalY() { return originalY; }

    public double getAttackTimer() { return attackTimer; }
    public void setAttackTimer(double t) { attackTimer = t; }

    public boolean isAttacking() { return attacking; }
    public void setAttacking(boolean a) { attacking = a; }

    public boolean isReturning() { return returning; }
    public void setReturning(boolean r) { returning = r; }

    public void setTarget(double x, double y) {
        this.targetX = x;
        this.targetY = y;
    }
    public double getTargetX() { return targetX; }
    public double getTargetY() { return targetY; }

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
            if(brickType == BrickType.UNBREAKABLE || brickType == BrickType.ATTACK) {
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
        } else if (brickType == BrickType.NORMAL) {
            sprite = GraphicsManager.getSprite("brick_" + hitPoints);
        } else {
            sprite = GraphicsManager.getSprite("brick_attack");
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
