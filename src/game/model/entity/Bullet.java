package game.model.entity;

import game.Constants;
import game.model.manager.GraphicsManager;

import java.awt.*;

public class Bullet extends MovableObject {

    public Bullet(int x, int y, int width, int height) {
        super(x, y, width, height);
        // Đạn bay thẳng lên trên (vận tốc y âm)
        this.dy = -Constants.BULLET_SPEED;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        sprite = GraphicsManager.getSprite("bullet");
        if (sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
        }
        else {
            System.err.println("Bullet Sprite is null");
        }
    }

    @Override
    public void move(double dt) {
        // Viên đạn chỉ di chuyển theo trục y
        y += dy * dt;
    }
}
