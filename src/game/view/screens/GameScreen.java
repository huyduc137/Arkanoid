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

import java.awt.*;
import java.util.List;

public class GameScreen extends Screen {

    private final GameModel model;

    public GameScreen(GameModel model) {
        super(ScreenType.GAME);
        this.model = model;
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
        // Draw background
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            // fallback
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        Graphics2D g2 = (Graphics2D) g;

        //Vẽ bricks
        for (Brick brick : model.getBricks()) {
            brick.draw(g2);
        }

        // Vẽ paddle
        Paddle paddle = model.getPaddle();
        paddle.draw(g2);

        // Vẽ súng trên paddle
        if (paddle.hasGuns()) {
            g2.setColor(Color.DARK_GRAY);
            int gunWidth = 5;
            int gunHeight = paddle.getHeight() / 2;
            int gunY = paddle.getY() + paddle.getHeight() / 4;
            // Súng bên trái
            g2.fillRect(paddle.getX() - gunWidth, gunY, gunWidth, gunHeight);
            // Súng bên phải
            g2.fillRect(paddle.getX() + paddle.getWidth(), gunY, gunWidth, gunHeight);
        }


        // Vẽ balls
        for (Ball ball : model.getBalls()) {
            ball.draw(g);
        }

        // Vẽ powerups
        for (PowerUp powerup : model.getPowerups()) {
            if (!powerup.getIsActive() && !powerup.getIsExpired()) {
                if (powerup instanceof ExtendPaddle) {
                    g2.setColor(Color.GREEN);
                    g2.fillRect(powerup.getX(), powerup.getY(), powerup.getWidth(), powerup.getHeight());
                    g2.setColor(Color.BLACK);
                    g2.drawString("E", powerup.getX() + powerup.getWidth() / 4, powerup.getY() + powerup.getHeight() * 3 / 4);
                } else if (powerup instanceof FireBall) {
                    powerup.draw(g);
                }
                else if (powerup instanceof MultiBall) {
                    g2.setColor(Color.ORANGE);
                    g2.fillRect(powerup.getX(), powerup.getY(), powerup.getWidth(), powerup.getHeight());
                    g2.setColor(Color.BLACK);
                    g2.drawString("M", powerup.getX() + powerup.getWidth() / 4, powerup.getY() + powerup.getHeight() * 3 / 4);
                }
                // Vẽ PaddleWithGun powerup
                else if (powerup instanceof PaddleWithGun) {
                    g2.setColor(Color.MAGENTA);
                    g2.fillRect(powerup.getX(), powerup.getY(), powerup.getWidth(), powerup.getHeight());
                    g2.setColor(Color.WHITE);
                    g2.drawString("G", powerup.getX() + powerup.getWidth() / 4, powerup.getY() + powerup.getHeight() * 3 / 4);
                }
            }
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
