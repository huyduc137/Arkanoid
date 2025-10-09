package game.model.entity;

import game.Constants;

public class Bullet extends MovableObject {

    public Bullet(int x, int y, int width, int height) {
        super(x, y, width, height);
        // Đạn bay thẳng lên trên (vận tốc y âm)
        this.dy = -Constants.BULLET_SPEED;
    }

    @Override
    public void move(double dt) {
        // Viên đạn chỉ di chuyển theo trục y
        y += dy * dt;
    }
}
