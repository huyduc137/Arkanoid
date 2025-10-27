package game.view.screens;

import game.Constants;
import game.model.GameModel;
import game.model.entity.Ball;
import game.model.entity.Brick;
import game.model.entity.Paddle;
import game.model.entity.Bullet;
import game.model.powerups.PowerUp;
import game.view.UI.UILabel;
import game.view.UI.UIManager;
import game.view.UI.HudElements;
import game.view.GameView;
import game.model.manager.FontManager;

import java.awt.*;
import java.util.List;

public class GameScreen extends Screen {
    private final GameModel model;
    private final GameView gameView;

    // UI Layout constants
    private static final int TOP_MARGIN = 20;
    private static final int SIDE_MARGIN = 20;
    private static final int ELEMENT_SPACING = 10;
    private static final int ICON_SIZE = 32;

    public GameScreen(GameModel model, GameView gameView) {
        super(ScreenType.GAME);
        this.model = model;
        this.gameView = gameView;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        loadBackground("bg/BackgroundPlay.png");
    }

    @Override
    public void initUI() {
        uiManager.clear();

        int baselineY = 20;
        int elementHeight = 40;
        int iconSize = 40;
        int spacing = 10;

        //Lives Display
        int livesX = 30;
        int livesYOffset = (elementHeight - 28) / 2;
        uiManager.add(new HudElements.LivesDisplay(livesX, baselineY + livesYOffset, () -> model.getScoreSystem().getLives()));

        //Score
        Font scoreFont = FontManager.getFont("Tektur Bold", 28f);
        uiManager.add(new HudElements.CenteredScoreLabel(baselineY, () -> "" + model.getScoreSystem().getScore(), scoreFont));

        //Level, Pause, Mute
        int rightMargin = 30;

        // Mute button
        int muteX = Constants.SCREEN_WIDTH - rightMargin - iconSize;
        uiManager.add(new HudElements.MuteButton(muteX, baselineY));

        // Pause button
        int pauseX = muteX - iconSize - spacing;
        uiManager.add(new HudElements.PauseButton(pauseX, baselineY, model.getGameStateManager()));

        // Level
        int levelLabelWidth = 110;
        int levelX = pauseX - levelLabelWidth - spacing;
        Font levelFont = FontManager.getFont("Tektur SemiBold", 18f);
        uiManager.add(new HudElements.LevelLabel(levelX, baselineY, () -> "Level " + model.getLevelManager().getCurrentLevelIndex(), levelFont));
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
        for (Bullet bullet : model.getBullets()) {
            bullet.draw(g);
        }

        // Draw UI (always normal, not flipped)
        if (uiManager != null) {
            uiManager.render(g);
        }
    }
}
