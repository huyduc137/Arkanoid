package game.model;

import game.Constants;
import game.model.entity.Ball;
import game.model.entity.Brick;
import game.model.entity.Paddle;
import game.model.entity.Bullet;
import game.model.manager.CollisionManager;
import game.model.manager.ScoreSystem;
import game.model.manager.TileManager;
import game.model.manager.GameStateManager;
import game.model.powerups.PowerUp;
import java.util.ArrayList;
import java.util.List;


public class GameModel {
    private final TileManager tileManager = new TileManager();
    private final ScoreSystem scoreSystem = new ScoreSystem();
    private final GameStateManager gameStateManager = new GameStateManager();
    private final CollisionManager collisionManager = new CollisionManager(this);

    private List<Ball> balls;
    private Paddle paddle;
    private List<Brick> bricks;
    private List<PowerUp> powerups;
    private List<Bullet> bullets;

    public Paddle getPaddle() {
        return paddle;
    }

    public List<Ball> getBalls() { return balls; }
    public Ball getBall() { return balls.isEmpty() ? null : balls.getFirst(); } // Bóng chính

    public List<Brick> getBricks() {
        return bricks;
    }

    public ScoreSystem getScoreSystem() {
        return scoreSystem;
    }

    public GameStateManager getGameStateManager () {
        return gameStateManager;
    }

    public List<PowerUp> getPowerups() {
        return powerups;
    }

    // THÊM CÁC PHƯƠNG THỨC QUẢN LÝ ĐẠN
    public List<Bullet> getBullets() {
        return bullets;
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void addBall(Ball ball) { balls.add(ball); }

    public GameModel() {
        initGame();
    }

    public void initGame() {
        balls = new ArrayList<>();
        Ball mainBall = new Ball(Constants.SCREEN_WIDTH / 2 - Constants.BALL_DIAMETER,
                Constants.SCREEN_HEIGHT / 2 - Constants.BALL_DIAMETER,
                Constants.BALL_DIAMETER);
        mainBall.resetBall();
        balls.add(mainBall);

        paddle = new Paddle(Constants.SCREEN_WIDTH / 2 - Constants.PADDLE_WIDTH / 2,
                Constants.SCREEN_HEIGHT - Constants.PADDLE_Y_OFFSET - Constants.PADDLE_HEIGHT/2,
                Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT);

        bricks = new ArrayList<>();
        bullets = new ArrayList<>();

        powerups = new ArrayList<>();

        // Reset trạng thái game
        scoreSystem.reset();

        initBrick();

        // Đặt ball lên paddle
        attachBallToPaddle(mainBall);
    }

    public void initBrick() {
        bricks = tileManager.loadMap("map/map1.txt");
    }

    public void update(double dt) {
        paddle.move(dt);

        // THÊM: Nếu ball đang trên paddle, di chuyển ball cùng paddle
        if (gameStateManager.isBallOnPaddle()) {
            Ball mainBall = getBall();
            if (mainBall != null) {
                attachBallToPaddle(mainBall);
            }
        }
        for (Ball ball : balls) {
            if (!gameStateManager.isBallOnPaddle() || ball != getBall()) { // Không di chuyển bóng chính nếu bám
                ball.move(dt);
            }
        }

        // CẬP NHẬT LOGIC CHO ĐẠN
        for (Bullet bullet : bullets) {
            bullet.move(dt);
        }
        // Xóa đạn đã bay ra khỏi màn hình
        bullets.removeIf(bullet -> bullet.getY() < 0);

        collisionManager.checkCollisions();

        // THÊM: Kiểm tra ball có rơi xuống không
        checkBallOutOfBounds();

        powerups.removeIf(powerup -> powerup.getIsExpired() || powerup.getY() > Constants.SCREEN_HEIGHT);
        for (PowerUp powerup : powerups) {
            powerup.update(dt);
        }

        balls.removeIf(ball -> ball.getY() > Constants.SCREEN_HEIGHT);
    }

    // THÊM: Phương thức gắn ball lên paddle
    private void attachBallToPaddle(Ball ball) {
        gameStateManager.setBallOnPaddle(true);

        ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2);
        ball.setY(paddle.getY() - ball.getHeight());
        ball.setDx(0);
        ball.setDy(0);
    }

    // THÊM: Phương thức phóng ball từ paddle
    public void launchBall() {
        if (gameStateManager.isBallOnPaddle()) {
            gameStateManager.setBallOnPaddle(false);
            Ball mainBall = getBall();
            if (mainBall != null) {
                mainBall.setDx(Constants.BALL_SPEED);
                mainBall.setDy(-Constants.BALL_SPEED);
            }
        }
    }

    // THÊM: Phương thức kiểm tra ball rơi xuống
    private void checkBallOutOfBounds() {
        if (!gameStateManager.isBallOnPaddle() && balls.isEmpty()) {
            // Trừ mạng
            scoreSystem.loseLife();

            // Kiểm tra nếu hết mạng
            if (scoreSystem.getLives() <= 0) {
                gameStateManager.setState(GameStateManager.GameState.GAME_OVER);
            } else {
                Ball mainBall = new Ball(Constants.BALL_DIAMETER);
                attachBallToPaddle(mainBall);

                balls.add(mainBall);
                gameStateManager.setBallOnPaddle(true);
            }
        }
    }
}
