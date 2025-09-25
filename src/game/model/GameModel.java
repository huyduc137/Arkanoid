package game.model;

import game.Constants;
import game.model.entity.Ball;
import game.model.entity.Brick;
import game.model.entity.Paddle;
import game.model.manager.ScoreSystem;
import game.model.manager.TileManager;
import game.model.powerups.ExtendPaddle;
import game.model.powerups.PowerUp;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    private final TileManager tileManager = new TileManager();
    private final ScoreSystem scoreSystem = new ScoreSystem();

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private List<PowerUp> powerups;
    private Random random;
    private int paddleExtension = 0;

    // THÊM: Biến theo dõi trạng thái game
    private boolean gameOver = false;
    // THÊM: Biến theo dõi ball có đang trên paddle không
    private boolean ballOnPaddle = true;

    public Paddle getPaddle() {return paddle;}
    public Ball getBall() {return ball;}
    public List<Brick> getBricks() {return bricks;}

    public ScoreSystem getScoreSystem() {
        return scoreSystem;
    }

    public List<PowerUp> getPowerups() {return powerups;}
    public void addPaddleExtension(int amount) { paddleExtension += amount; }
    public void removePaddleExtension(int amount) { paddleExtension = Math.max(0, paddleExtension - amount); }

    // THÊM: Getter cho trạng thái game
    public boolean isGameOver() { return gameOver; }
    // THÊM: Getter và setter cho ballOnPaddle
    public boolean isBallOnPaddle() { return ballOnPaddle; }
    public void setBallOnPaddle(boolean ballOnPaddle) { this.ballOnPaddle = ballOnPaddle; }

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
        powerups = new ArrayList<>();
        random = new Random();
        paddleExtension = 0;

        // THÊM: Reset trạng thái game
        gameOver = false;
        ballOnPaddle = true; // Ball bắt đầu trên paddle

        initBrick();

        // THÊM: Đặt ball lên paddle
        attachBallToPaddle();
    }

    public void initBrick() {
        bricks = tileManager.loadMap("map/map1.txt");
    }

    public void update(double dt) {
        // THÊM: Kiểm tra nếu game đã kết thúc thì không cập nhật
        if (gameOver) return;

        paddle.move(dt);

        // THÊM: Nếu ball đang trên paddle, di chuyển ball cùng paddle
        if (ballOnPaddle) {
            attachBallToPaddle();
        } else {
            ball.move(dt);
        }

        checkCollisions();

        // THÊM: Kiểm tra ball có rơi xuống không
        checkBallOutOfBounds();

        powerups.removeIf(powerup -> powerup.getIsExpired() || powerup.getY() > Constants.SCREEN_HEIGHT);
        for (PowerUp powerup : powerups) {
            powerup.update(dt);
        }

        paddle.setWidth(Constants.PADDLE_WIDTH + paddleExtension);
    }

    // THÊM: Phương thức gắn ball lên paddle
    private void attachBallToPaddle() {
        ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2);
        ball.setY(paddle.getY() - ball.getHeight());
        ball.setDx(0);
        ball.setDy(0);
    }

    // THÊM: Phương thức phóng ball từ paddle
    public void launchBall() {
        if (ballOnPaddle) {
            ballOnPaddle = false;
            ball.setDx(Constants.BALL_SPEED);
            ball.setDy(-Constants.BALL_SPEED);
        }
    }

    // THÊM: Phương thức kiểm tra ball rơi xuống
    private void checkBallOutOfBounds() {
        if (!ballOnPaddle && ball.getY() > Constants.SCREEN_HEIGHT) {
            // Trừ mạng
            scoreSystem.loseLife();

            // Kiểm tra nếu hết mạng
            if (scoreSystem.getLives() <= 0) {
                gameOver = true;
            } else {
                // Đặt ball lại trên paddle nếu còn mạng
                ballOnPaddle = true;
                attachBallToPaddle();
            }
        }
    }

    private void checkCollisions() {
        // THÊM: Chỉ kiểm tra va chạm với paddle nếu ball không đang trên paddle
        if (!ballOnPaddle && ball.getBounds().intersects(paddle.getBounds())) {
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

                // Tạo power-up ExtendPaddle ngẫu nhiên khi gạch bị phá
                if (brick.isDestroyed() && random.nextFloat() < 0.3) { // 30% cơ hội
                    powerups.add(new ExtendPaddle(brick.getX(), brick.getY(), this));
                }

                break;
            }
        }
    }
}
