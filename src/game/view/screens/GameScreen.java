package game.view.screens;

import game.Constants;
import game.model.GameModel;
import game.model.entity.Ball;
import game.model.entity.Brick;
import game.model.entity.Paddle;
import game.model.entity.Bullet;
import game.model.manager.GraphicsManager;
import game.model.powerups.ExtendPaddle;
import game.model.powerups.FireBall;
import game.model.powerups.MultiBall;
import game.model.powerups.PowerUp;
import game.model.powerups.PaddleWithGun;
import game.view.UI.UILabel;
import game.view.UI.UIManager;
import game.view.GameView;

import java.awt.*;
import java.util.List;

public class GameScreen extends Screen {
    private final GameModel model;
    private final GameView gameView;

    public GameScreen(GameModel model, GameView gameView) {
        super(ScreenType.GAME);
        this.model = model;
        this.gameView = gameView;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        loadBackground("bg/BackgroundPlay.png");
    }

    @Override
    public void initUI() {
        uiManager.add(new UILabel(Constants.BRICK_WIDTH / 2, Constants.BRICK_HEIGHT,
                () -> "Score: " + model.getScoreSystem().getScore(),
                Color.WHITE, new Font("Arial", Font.PLAIN, 20)));

        uiManager.add(new UILabel(Constants.SCREEN_WIDTH - 100, Constants.BRICK_HEIGHT,
                () -> "Lives: " + model.getScoreSystem().getLives(),
                Color.WHITE, new Font("Arial", Font.PLAIN, 20)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (gameView.isScreenInverted()) {
            // Lật ngược màn hình: xoay 180 độ quanh tâm canvas
            g2.rotate(Math.PI, getWidth() / 2.0, getHeight() / 2.0);
        }
        // Draw background
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            // fallback
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        //Graphics2D g2 = (Graphics2D) g;

        //Vẽ bricks
        for (Brick brick : model.getBricks()) {
            brick.draw(g2);
        }

        // Vẽ paddle
        Paddle paddle = model.getPaddle();
        paddle.draw(g2);

        // Vẽ balls
        for (Ball ball : model.getBalls()) {
            ball.draw(g);
        }

        // Vẽ powerups
        for (PowerUp powerup : model.getPowerups()) {
            powerup.draw(g);
        }

        // Vẽ Bullets
        List<Bullet> bullets = model.getBullets();
        g2.setColor(Color.YELLOW);
        for (Bullet bullet : bullets) {
            g2.fillRect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        }


        // Vẽ UI elements
        if (uiManager != null) {
            uiManager.render(g);
        }
    }
}
