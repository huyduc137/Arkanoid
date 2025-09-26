package game.model;

import game.Constants;
import game.model.entity.Ball;
import game.model.entity.Brick;
import game.model.entity.Paddle;
import game.model.manager.CollisionManager;
import game.model.manager.ScoreSystem;
import game.model.manager.TileManager;
import game.model.manager.GameStateManager;
import game.model.powerups.ExtendPaddle;
import game.model.powerups.FireBall;
import game.model.powerups.PowerUp;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    private final TileManager tileManager = new TileManager();
    private final ScoreSystem scoreSystem = new ScoreSystem();
    private final GameStateManager gameStateManager = new GameStateManager();
    private final CollisionManager collisionManager = new CollisionManager(this);

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private List<PowerUp> powerups;
    private Random random;
    private int paddleExtension = 0;

    public Paddle getPaddle() {
        return paddle;
    }

    public Ball getBall() {
        return ball;
    }

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

    public void addPaddleExtension(int amount) {
        paddleExtension += amount;
    }

    public void removePaddleExtension(int amount) {
        paddleExtension = Math.max(0, paddleExtension - amount);
    }

    public GameModel() {
        initGame();
    }

    public void initGame() {
        ball = new Ball(Constants.SCREEN_WIDTH / 2 - Constants.BALL_DIAMETER,
                Constants.SCREEN_HEIGHT / 2 - Constants.BALL_DIAMETER,
                Constants.BALL_DIAMETER);
        paddle = new Paddle(Constants.SCREEN_WIDTH / 2 - Constants.PADDLE_WIDTH / 2,
                Constants.SCREEN_HEIGHT - Constants.PADDLE_Y_OFFSET - Constants.PADDLE_HEIGHT/2,
                Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT);
        bricks = new ArrayList<>();
        powerups = new ArrayList<>();
        random = new Random();
        paddleExtension = 0;

        // Reset trạng thái game
        gameStateManager.reset();
        scoreSystem.reset();

        initBrick();

        // Đặt ball lên paddle
        attachBallToPaddle();
    }

    public void initBrick() {
        bricks = tileManager.loadMap("map/map1.txt");
    }

    public void update(double dt) {
        paddle.move(dt);

        // THÊM: Nếu ball đang trên paddle, di chuyển ball cùng paddle
        if (gameStateManager.isBallOnPaddle()) {
            attachBallToPaddle();
        } else {
            ball.move(dt);
        }

        collisionManager.checkCollisions();

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
        if (gameStateManager.isBallOnPaddle()) {
            gameStateManager.setBallOnPaddle(false);
            ball.setDx(Constants.BALL_SPEED);
            ball.setDy(-Constants.BALL_SPEED);
        }
    }

    // THÊM: Phương thức kiểm tra ball rơi xuống
    private void checkBallOutOfBounds() {
        if (!gameStateManager.isBallOnPaddle() && ball.getY() > Constants.SCREEN_HEIGHT) {
            // Trừ mạng
            scoreSystem.loseLife();

            // Kiểm tra nếu hết mạng
            if (scoreSystem.getLives() <= 0) {
                gameStateManager.setState(GameStateManager.GameState.GAME_OVER);
            } else {
                // Đặt ball lại trên paddle nếu còn mạng
                gameStateManager.setBallOnPaddle(true);
                attachBallToPaddle();
            }
        }
    }
}
