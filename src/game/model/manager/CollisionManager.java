package game.model.manager;

import game.model.GameModel;
import game.model.entity.Ball;
import game.model.entity.Brick;
import game.model.entity.Paddle;
import game.model.powerups.ExtendPaddle;
import game.model.powerups.FireBall;
import game.model.powerups.PowerUp;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class CollisionManager {
    private final GameModel model;
    private final Random random = new Random();

    public CollisionManager(GameModel model) {
        this.model = model;
    }

    public void checkCollisions() {
        Ball ball = model.getBall();
        Paddle paddle = model.getPaddle();
        List<Brick> bricks = model.getBricks();

        handlePaddleCollision(ball, paddle);
        handleBrickCollisions(ball, bricks);
    }

    private void handlePaddleCollision(Ball ball, Paddle paddle) {
        if (!model.getGameStateManager().isGameOver() && ball.getBounds().intersects(paddle.getBounds())) {
            ball.reverseDy();
            ball.setY(paddle.getY() - ball.getHeight());
        }
    }

    private void handleBrickCollisions(Ball ball, List<Brick> bricks) {
        for (Brick brick : bricks) {
            if (!brick.isDestroyed() && ball.getBounds().intersects(brick.getBounds())) {
                resolveBallBrickCollision(ball, brick);
                handleBrickHit(brick);
                break; // only one collision per update
            }
        }
    }

    //Helper cho handleBrickCollisions
    // Xử lí logic xem bóng va chạm brick theo hướng nào, có hit được không
    private void resolveBallBrickCollision(Ball ball, Brick brick) {
        Rectangle ballHitbox = ball.getBounds();
        Rectangle brickHitbox = brick.getBounds();

        int ballCenterX = ballHitbox.x + ballHitbox.width / 2;
        int ballCenterY = ballHitbox.y + ballHitbox.height / 2;

        int brickCenterX = brickHitbox.x + brickHitbox.width / 2;
        int brickCenterY = brickHitbox.y + brickHitbox.height / 2;

        int dx = ballCenterX - brickCenterX;
        int dy = ballCenterY - brickCenterY;

        //Tính trọng số của chiều dài/ngang của brick so với khoảng cách tâm bóng và tâm brick
        float wy = (brickHitbox.width / 2.0f) * dy;
        float hx = (brickHitbox.height / 2.0f) * dx;

        if (!ball.isFireBall()) {
            if (Math.abs(wy) > Math.abs(hx)) {
                if (dy > 0) { // đập ở trên
                    ball.setY(brickHitbox.y + brickHitbox.height);
                } else { // đập ở dưới
                    ball.setY(brickHitbox.y - ballHitbox.height);
                }
                ball.reverseDy();
            } else {
                if (dx > 0) { // đập bên phải
                    ball.setX(brickHitbox.x + brickHitbox.width);
                } else { // đập bên trái
                    ball.setX(brickHitbox.x - ballHitbox.width);
                }
                ball.reverseDx();
            }
            brick.hit();
        } else {
            brick.setDestroyed(true);
        }
    }

    //Helper cho handleBrickCollisions
    // Xử lí logic game khi brick bị phá
    private void handleBrickHit(Brick brick) {
        if (brick.isDestroyed() && brick.getBrickType() != Brick.BrickType.UNBREAKABLE) {
            model.getScoreSystem().addScore(brick.getScore() * 100);
            System.out.println(model.getScoreSystem().getScore());

            // Drop powerup
            if (random.nextFloat() < 0.3) { // 30% chance
                PowerUp powerup = random.nextInt(9) == 0
                        ? new FireBall(brick.getX(), brick.getY(), model)
                        : new ExtendPaddle(brick.getX(), brick.getY(), model);
                model.getPowerups().add(powerup);
            }
        }
    }
}
