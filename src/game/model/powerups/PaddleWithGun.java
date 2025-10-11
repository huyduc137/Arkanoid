package game.model.powerups;

import game.Constants;
import game.model.GameModel;
import game.model.entity.Bullet;
import game.model.entity.Paddle;

import java.awt.*;

public class PaddleWithGun extends PowerUp {

    private final double SHOOT_COOLDOWN = 0.4; // Giãn cách mỗi lần bắn là 0.4 giây
    private double timeSinceLastShot;

    public PaddleWithGun(int x, int y, GameModel model) {
        // Giả sử powerup có kích thước chuẩn, hiệu lực trong 5 giây
        super(x, y, Constants.POWERUP_SIZE, Constants.POWERUP_SIZE, Constants.POWERUP_DURATION, model);
        this.timeSinceLastShot = 0;
    }

    @Override
    public void apply() {
        // Khi người chơi nhặt được, kích hoạt súng trên paddle
        model.getPaddle().setGunsActive(true);
    }

    @Override
    public void remove() {
        // Khi hết hạn, hủy kích hoạt súng
        model.getPaddle().setGunsActive(false);
    }

    @Override
    public void update(double dt) {
        // Gọi update của lớp cha để xử lý việc rơi, kích hoạt và đếm ngược thời gian
        super.update(dt);

        // Nếu power-up đang được kích hoạt và chưa hết hạn
        if (isActive && !getIsExpired()) {
            timeSinceLastShot += dt;
            // Nếu đã đến lúc bắn
            if (timeSinceLastShot >= SHOOT_COOLDOWN) {
                shoot();
                timeSinceLastShot = 0; // Reset bộ đếm
            }
        }
    }

    private void shoot() {
        Paddle paddle = model.getPaddle();
        int paddleX = paddle.getX();
        int paddleY = paddle.getY();
        int paddleWidth = paddle.getWidth();

        // Tạo 2 viên đạn ở 2 đầu của paddle
        int bullet1X = paddleX + 5;
        int bullet2X = paddleX + paddleWidth - Constants.BULLET_WIDTH - 5;

        Bullet bullet1 = new Bullet(bullet1X, paddleY, Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT);
        Bullet bullet2 = new Bullet(bullet2X, paddleY, Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT);

        // Thêm đạn vào GameModel để được xử lý
        model.addBullet(bullet1);
        model.addBullet(bullet2);
    }

    @Override
    public void draw(Graphics g) {

    }
}
