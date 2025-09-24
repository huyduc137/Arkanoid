package game.model;

import game.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private final TileManager tileManager = new TileManager();
    private final ScoreSystem scoreSystem = new ScoreSystem();

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;

    public Paddle getPaddle() {return paddle;}
    public Ball getBall() {return ball;}
    public List<Brick> getBricks() {return bricks;}
    public ScoreSystem getScoreSystem() {
        return scoreSystem;
    }

    public GameModel() {
        initGame();
    }

    public void initGame() {
        ball = new Ball(Constants.SCREEN_WIDTH/2 - Constants.BALL_DIAMETER ,
                        Constants.SCREEN_HEIGHT/2 - Constants.BALL_DIAMETER ,
                           Constants.BALL_DIAMETER);
        paddle = new Paddle(Constants.SCREEN_WIDTH/2 - Constants.PADDLE_WIDTH/2 ,
                            Constants.SCREEN_HEIGHT - Constants.PADDLE_Y_OFFSET - Constants.PADDLE_HEIGHT/2 ,
                               Constants.PADDLE_WIDTH , Constants.PADDLE_HEIGHT);
        bricks = new ArrayList<>();
        initBrick();
    }

    public void initBrick() {
        bricks = tileManager.loadMap("map/map1.txt");
    }

    public void update(double dt) {
        paddle.move(dt);
        ball.move(dt);

        checkCollisions();
    }
    private void checkCollisions() {
        // check va chạm bóng và padlle
        if (ball.getBounds().intersects(paddle.getBounds())) {
            ball.reverseDy();
            ball.setY(paddle.getY() - ball.getHeight());
        }

        //Check va chạm bricks và bóng
        for(Brick brick: bricks) {
            if(!brick.isDestroyed() && ball.getBounds().intersects(brick.getBounds())) {
                Rectangle ballHitbox = ball.getBounds();
                Rectangle brickHitbox = brick.getBounds();

                int ballCenterX = ballHitbox.x + ballHitbox.width / 2;
                int ballCenterY = ballHitbox.y + ballHitbox.height / 2;

                int brickCenterX = brickHitbox.x + brickHitbox.width / 2;
                int brickCenterY = brickHitbox.y + brickHitbox.height / 2;

                int dx = ballCenterX - brickCenterX;
                int dy = ballCenterY - brickCenterY;

                //Kiểm tra xem hit dọc hay ngang
                //Tính trọng số của chiều dài/ngang của brick so với khoảng cách
                float wy = (brickHitbox.width / 2.0f) * dy;
                float hx = (brickHitbox.height / 2.0f) * dx;

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

                if(brick.isDestroyed() && brick.getBrickType() != Brick.BrickType.UNBREAKABLE) {
                    scoreSystem.addScore(brick.getScore() * 100);
                    System.out.println(scoreSystem.getScore());
                }

                break;
            }
        }
    }
}
