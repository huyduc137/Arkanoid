package game.model.manager;

import game.model.GameModel;
import game.model.entity.Ball;
import game.model.entity.Brick;
import game.model.entity.Paddle;
import game.model.entity.Bullet;
import game.model.powerups.ExtendPaddle;
import game.model.powerups.FireBall;
import game.model.powerups.MultiBall;
import game.model.powerups.PowerUp;
import game.model.powerups.PaddleWithGun;

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
        List<Ball> balls = model.getBalls();
        Paddle paddle = model.getPaddle();
        List<Brick> bricks = model.getBricks();
        List<Bullet> bullets = model.getBullets();

        for (Ball ball : balls) {
            handlePaddleCollision(ball, paddle);
            handleBallBrickCollisions(ball, bricks);
        }
        handleBrickPaddleCollisions(paddle, bricks);
        bullets.removeIf(bullet -> handleBulletBrickCollisions(bullet, bricks));
    }

    private void handlePaddleCollision(Ball ball, Paddle paddle) {
        if (!model.getGameStateManager().isGameOver() && ball.getBounds().intersects(paddle.getBounds())) {
            SoundManager.play("paddle_bounce");

            ball.reverseDy();
            double ballCenterX = ball.getX() + (ball.getWidth() / 2.0);
            double paddleCenterX = paddle.getX() + (paddle.getWidth() / 2.0);
            double checkHandlePosition = ballCenterX - paddleCenterX;
            if (checkHandlePosition < 0) {
                if (ball.getDx() > 0) ball.reverseDx();
            }
            else if (checkHandlePosition > 0){
                if (ball.getDx() < 0) ball.reverseDx();
            }
            ball.setY(paddle.getY() - ball.getHeight());
        }
    }

    private void handleBallBrickCollisions(Ball ball, List<Brick> bricks) {
        if (ball.getCollisionCooldown() > 0) return;

        for (Brick brick : bricks) {
            if (!brick.isDestroyed()
                    && ball.getBounds().intersects(brick.getBounds())) {
                resolveBallBrickCollision(ball, brick);
                handleBrickHit(brick);

                ball.setCollisionCooldown(0.04);
                break;
            }
        }
    }

    private boolean handleBulletBrickCollisions(Bullet bullet, List<Brick> bricks) {
        for (Brick brick : bricks) {
            if (!brick.isDestroyed() && bullet.getBounds().intersects(brick.getBounds())) {
                if (brick.hit()) {
                    model.brickDestroyed();
                }
                handleBrickHit(brick);
                return true;
            }
        }
        return false;
    }

    private void resolveBallBrickCollision(Ball ball, Brick brick) {
        Rectangle ballHitbox = ball.getBounds();
        Rectangle brickHitbox = brick.getBounds();

        int ballCenterX = ballHitbox.x + ballHitbox.width / 2;
        int ballCenterY = ballHitbox.y + ballHitbox.height / 2;

        int brickCenterX = brickHitbox.x + brickHitbox.width / 2;
        int brickCenterY = brickHitbox.y + brickHitbox.height / 2;

        int dx = ballCenterX - brickCenterX;
        int dy = ballCenterY - brickCenterY;

        float wy = (brickHitbox.width / 2.0f) * dy;
        float hx = (brickHitbox.height / 2.0f) * dx;

        if (!ball.isFireBall()
                || (ball.isFireBall() && brick.getBrickType() == Brick.BrickType.UNBREAKABLE)
                || (ball.isFireBall() && brick.getBrickType() == Brick.BrickType.ATTACK)) {
            if (Math.abs(wy) > Math.abs(hx)) {
                if (dy > 0) {
                    ball.setY(brickHitbox.y + brickHitbox.height);
                } else {
                    ball.setY(brickHitbox.y - ballHitbox.height);
                }
                ball.reverseDy();
            } else {
                if (dx > 0) {
                    ball.setX(brickHitbox.x + brickHitbox.width);
                } else {
                    ball.setX(brickHitbox.x - ballHitbox.width);
                }
                ball.reverseDx();
            }
            if (brick.hit()) {
                model.brickDestroyed();
            }
        } else {
            if (!brick.isDestroyed()) {
                model.brickDestroyed();
            }
            brick.setDestroyed(true);
        }
    }

    private void handleBrickHit(Brick brick) {
        if (brick.isDestroyed() && brick.getBrickType() != Brick.BrickType.UNBREAKABLE) {
            model.getScoreSystem().addScore(brick.getScore() * 100);
            System.out.println("Score: " + model.getScoreSystem().getScore());

            if (random.nextFloat() < 0.3) {
                int powerupType = random.nextInt(10);
                PowerUp powerup = switch (powerupType) {
                    case 3, 4 -> new FireBall(brick.getX(), brick.getY(), model);
                    case 5, 6 -> new MultiBall(brick.getX(), brick.getY(), model);
                    case 7, 8, 9 -> new PaddleWithGun(brick.getX(), brick.getY(), model);
                    default -> new ExtendPaddle(brick.getX(), brick.getY(), model);
                };
                model.getPowerups().add(powerup);
            }
        }
    }

    private void handleBrickPaddleCollisions(Paddle paddle, List<Brick> bricks) {
        if (model.getGameStateManager().isInvulnerable()) return;

        for (Brick brick : bricks) {
            if (brick.isDestroyed()) continue;
            if (brick.getBounds().intersects(paddle.getBounds())) {
                // Brick hit the paddle
                model.getScoreSystem().loseLife();
                // Kiểm tra nếu hết mạng
                if (model.getScoreSystem().getLives() <= 0) {
                    model.getGameStateManager().setState(GameStateManager.GameState.GAME_OVER);
                }

                // Bật mode ko ăn dame
                model.getGameStateManager().setInvulnerable(true);
                break;
            }
        }
    }

}
