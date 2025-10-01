package game.view.screens;

import game.Constants;
import game.model.GameModel;
import game.model.entity.Ball;
import game.model.entity.Brick;
import game.model.entity.Paddle;
import game.model.powerups.ExtendPaddle;
import game.model.powerups.FireBall;
import game.model.powerups.MultiBall;
import game.model.powerups.PowerUp;
import game.view.UI.UILabel;
import game.view.UI.UIManager;

import java.awt.*;

public class GameScreen extends Screen {

    private final GameModel model;

    public GameScreen(GameModel model) {
        super(ScreenType.GAME);
        this.model = model;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        loadBackground("bg/test2.png");
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
            if (!brick.isDestroyed()) {
                switch (brick.getBrickType()) {
                    case NORMAL -> {
                        switch (brick.getHitPoints()) {
                            case 1 -> g2.setColor(Color.RED);
                            case 2 -> g2.setColor(Color.ORANGE);
                            case 3 -> g2.setColor(Color.CYAN);
                        }
                    }
                    case UNBREAKABLE -> g2.setColor(Color.GRAY);
                }
                g2.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                g2.setColor(Color.BLACK);
                g2.drawRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
            }
        }

        // Vẽ paddle
        Paddle paddle = model.getPaddle();
        g2.setColor(Color.WHITE);
        g2.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

        // Vẽ balls
        for (Ball ball : model.getBalls()) {
            g2.setColor(ball.isFireBall() ? Color.RED : Color.YELLOW);
            g2.fillOval(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
        }

        // Vẽ powerups
        for (PowerUp powerup : model.getPowerups()) {
            if (!powerup.getIsActive() && !powerup.getIsExpired()) {
                if (powerup instanceof ExtendPaddle) {
                    g.setColor(Color.GREEN);
                    g.fillRect(powerup.getX(), powerup.getY(), powerup.getWidth(), powerup.getHeight());
                    g.setColor(Color.BLACK);
                    g.drawString("E", powerup.getX() + powerup.getWidth() / 4, powerup.getY() + powerup.getHeight() * 3 / 4);
                } else if (powerup instanceof FireBall) {
                    g.setColor(Color.RED);
                    g.fillRect(powerup.getX(), powerup.getY(), powerup.getWidth(), powerup.getHeight());
                    g.setColor(Color.BLACK);
                    g.drawString("F", powerup.getX() + powerup.getWidth() / 4, powerup.getY() + powerup.getHeight() * 3 / 4);
                }
                else if (powerup instanceof MultiBall) {
                    g.setColor(Color.ORANGE);
                    g.fillRect(powerup.getX(), powerup.getY(), powerup.getWidth(), powerup.getHeight());
                    g.setColor(Color.BLACK);
                    g.drawString("M", powerup.getX() + powerup.getWidth() / 4, powerup.getY() + powerup.getHeight() * 3 / 4);
                }
            }
        }

        // Vẽ UI elements
        if (uiManager != null) {
            uiManager.render(g);
        }
    }
}
