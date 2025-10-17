package game.model;

import game.Constants;
import game.model.entity.*;
import game.model.manager.*;
import game.model.powerups.PowerUp;
import java.util.ArrayList;
import java.util.List;


public class GameModel {
    private final TileManager tileManager = new TileManager();
    private final ScoreSystem scoreSystem = new ScoreSystem();
    private final GameStateManager gameStateManager = new GameStateManager();
    private final CollisionManager collisionManager = new CollisionManager(this);
    private final PowerUpManager powerUpManager = new PowerUpManager(this);
    private final LevelManager levelManager = new LevelManager();

    private int countBrickModel;

    private List<Ball> balls;
    private Paddle paddle;
    private List<Brick> bricks;
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

    public PowerUpManager getPowerUpManager() {
        return powerUpManager;
    }
    public LevelManager getLevelManager() {
        return levelManager;
    }
    public List<PowerUp> getPowerups() {
        // Trả về danh sách power-up đang rơi từ Manager
        return powerUpManager.getFallingPowerUps();
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

        // Reset trạng thái game
        scoreSystem.reset();

        initBrick();

        // Đặt ball lên paddle
        attachBallToPaddle(mainBall);
        countBrickModel = tileManager.getCountBrick();

        powerUpManager.getFallingPowerUps().removeAll(powerUpManager.getFallingPowerUps());
        powerUpManager.getActivePowerUps().removeAll(powerUpManager.getActivePowerUps());
    }

    public void initBrick() {
        String mapPath = levelManager.getCurrentLevel().getMapPath();
        bricks = tileManager.loadMap(mapPath);
        for (Brick brick : bricks) {
            brick.setOriginalPosition(brick.getX(), brick.getY());
            brick.setAttackTimer(0.0);
            brick.setAttacking(false);
            brick.setReturning(false);
            brick.setDx(0);
            brick.setDy(0);
            brick.setTarget(0, 0);
        }
    }

    public void update(double dt) {
        Level currentLevel = levelManager.getCurrentLevel();
        if (currentLevel == null || !gameStateManager.isGameActive()) {
            return;
        }

        paddle.move(dt);

        // THÊM: Nếu ball đang trên paddle, di chuyển ball cùng paddle
        if (gameStateManager.isBallOnPaddle()) {
            Ball mainBall = getBall();
            if (mainBall != null) {
                attachBallToPaddle(mainBall);
            }
        }
        for (Ball ball : balls) {
            if (!gameStateManager.isBallOnPaddle() || ball != getBall()) {// Không di chuyển bóng chính nếu bám
                ball.updateCooldown(dt);
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

        powerUpManager.update(dt);

        balls.removeIf(ball -> ball.getY() > Constants.SCREEN_HEIGHT);

        updateBricks(dt);

        collisionManager.checkCollisions();
        gameStateManager.updateInvulnerability(dt);

        // THÊM: Kiểm tra ball có rơi xuống không
        checkBallOutOfBounds();
        checkGameWinner();
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
                HighScoreManager.saveHighScore(levelManager.getCurrentLevel().getName(), scoreSystem.getScore());
                gameStateManager.setState(GameStateManager.GameState.GAME_OVER);
            } else {
                Ball mainBall = new Ball(Constants.BALL_DIAMETER);
                attachBallToPaddle(mainBall);

                balls.add(mainBall);
                gameStateManager.setBallOnPaddle(true);
            }
        }
    }

    private void checkGameWinner(){
//        System.out.println("countBrickModel: " + countBrickModel);
        if (gameStateManager.isGameActive()
                && countBrickModel <= 0) {
            HighScoreManager.saveHighScore(levelManager.getCurrentLevel().getName(), scoreSystem.getScore());
            gameStateManager.setState(GameStateManager.GameState.GAME_WINNER);
        }
    }
    public void brickDestroyed() {
        if (countBrickModel > 0) {
            countBrickModel--;
        }
    }

    private void updateBricks(double dt) {
        Level currentLevel = levelManager.getCurrentLevel();
        // Brick Attack mode
        if (currentLevel.isBricksAttack()) {
            double attackSpeed = currentLevel.getBrickAttackSpeed();
            double attackDelay = 2.0;
            double paddleCenterX = paddle.getX() + paddle.getWidth() / 2.0;
            double paddleCenterY = paddle.getY() + paddle.getHeight() / 2.0;

            for (Brick brick : bricks) {
                if (brick.isDestroyed()) continue;
                if (brick.getBrickType() != Brick.BrickType.ATTACK) continue;

                brick.setAttackTimer(brick.getAttackTimer() + dt);

                // timer = delay -> attack
                if (!brick.isAttacking() && !brick.isReturning() && brick.getAttackTimer() >= attackDelay) {
                    brick.setAttacking(true);
                    brick.setAttackTimer(0.0);
                    brick.setTarget(paddleCenterX, paddleCenterY);
                }

                double vx = 0.0, vy = 0.0;
                double brickCenterX = brick.getX() + brick.getWidth() / 2.0;
                double brickCenterY = brick.getY() + brick.getHeight() / 2.0;

                if (brick.isAttacking()) {
                    // Tính vector từ brick đến target
                    double dirX = brick.getTargetX() - brickCenterX;
                    double dirY = brick.getTargetY() - brickCenterY;
                    double len = Math.hypot(dirX, dirY);

                    if (len > 0.0001) {
                        // Unit vector * speed
                        vx = (dirX / len) * attackSpeed;
                        vy = (dirY / len) * attackSpeed;
                    }

                    // Đến chỗ paddle rồi thì quay lại
                    if (brickCenterY >= brick.getTargetY() - brick.getHeight() / 2.0) {
                        brick.setAttacking(false);
                        brick.setReturning(true);
                    }
                } else if (brick.isReturning()) {
                    // Tính vector từ brick đến original pos
                    double dirX = brick.getOriginalX() - brick.getX();
                    double dirY = brick.getOriginalY() - brick.getY();
                    double len = Math.hypot(dirX, dirY);

                    if (len > 0.0001) {
                        // Unit vector * speed
                        vx = (dirX / len) * attackSpeed;
                        vy = (dirY / len) * attackSpeed;
                    }
                    //Gắn vào chỗ nếu đủ gần
                    if (len <= attackSpeed * dt + 0.5) {
                        brick.setX((int) brick.getOriginalX());
                        brick.setY((int) brick.getOriginalY());
                        brick.setReturning(false);
                        brick.setAttackTimer(0.0);
                        vx = vy = 0;
                    }
                }

                brick.setDx(vx);
                brick.setDy(vy);

                if (vx != 0.0 || vy != 0.0) {
                    brick.move(dt);
                }
            }
        }

        // Brick Fall mode
        if (currentLevel.isBricksFall()) {
            double interval = 5.0 / currentLevel.getBrickFallSpeed(); // speed cao -> rơi thường xuyên hơn
            currentLevel.setBrickFallTimer(currentLevel.getBrickFallTimer() + dt);

            if (currentLevel.getBrickFallTimer() >= interval) {
                currentLevel.setBrickFallTimer(0.0);
                for (Brick brick : bricks) {
                    if (brick.isDestroyed() || brick.getBrickType() == Brick.BrickType.ATTACK) continue;
                    brick.setY(brick.getY() + brick.getHeight());

                    if (brick.getY() + brick.getHeight() >= Constants.SCREEN_HEIGHT) {
                        gameStateManager.setState(GameStateManager.GameState.GAME_OVER);
                    }
                }
            }
        }
    }
}
