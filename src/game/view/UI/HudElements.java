package game.view.UI;

import game.Constants;
import game.model.manager.GraphicsManager;
import game.model.manager.GameStateManager;
import game.sound.SoundManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

public class HudElements {

    //Lives
    public static class LivesDisplay extends UIElement {
        private final Supplier<Integer> livesSupplier;

        public LivesDisplay(int x, int y, Supplier<Integer> livesSupplier) {
            super(x, y, 96, 32);
            this.livesSupplier = livesSupplier;
        }

        @Override
        public void draw(Graphics g) {
            int lives = livesSupplier.get();
            int heartSize = 28;
            int spacing = 4;

            for (int i = 0; i < 3; i++) {
                String spriteId = (i < lives) ? "heart_full" : "heart_empty";
                BufferedImage heart = GraphicsManager.getSprite(spriteId);
                if (heart != null) {
                    g.drawImage(heart, x + i * (heartSize + spacing), y, heartSize, heartSize, null);
                }
            }
        }

        @Override
        public void onClick() {}
    }

    //Score
    public static class CenteredScoreLabel extends UIElement {
        private final Supplier<String> scoreSupplier;
        private final Font font;

        public CenteredScoreLabel(int y, Supplier<String> scoreSupplier, Font font) {
            super(0, y, Constants.SCREEN_WIDTH, 40);
            this.scoreSupplier = scoreSupplier;
            this.font = font != null ? font : new Font("Arial", Font.BOLD, 28);
        }

        @Override
        public void draw(Graphics g) {
            String text = scoreSupplier.get();
            g.setFont(font);
            g.setColor(Color.WHITE);

            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int centerX = (Constants.SCREEN_WIDTH - textWidth) / 2;
            int textY = this.y + fm.getAscent();

            g.drawString(text, centerX, textY);
        }

        @Override
        public void onClick() {}
    }

    //LevelLabel
    public static class LevelLabel extends UIElement {
        private final Supplier<String> levelSupplier;
        private final Font font;

        public LevelLabel(int x, int y, Supplier<String> levelSupplier, Font font) {
            super(x, y, 150, 50);
            this.levelSupplier = levelSupplier;
            this.font = font != null ? font : new Font("Arial", Font.BOLD, 20);
        }

        @Override
        public void draw(Graphics g) {
            String text = levelSupplier.get();
            g.setFont(font);
            g.setColor(Color.WHITE);

            FontMetrics fm = g.getFontMetrics();
            int textY = y + (height - fm.getHeight()) / 2 + fm.getAscent();

            g.drawString(text, x, textY);
        }

        @Override
        public void onClick() {}
    }

    //PauseButton
    public static class PauseButton extends UIElement {
        private final GameStateManager gameStateManager;

        public PauseButton(int x, int y, GameStateManager gsm) {
            super(x, y, 40, 40);
            this.gameStateManager = gsm;
        }

        @Override
        public void draw(Graphics g) {
            String spriteId = (gameStateManager.getCurrentState() == GameStateManager.GameState.PLAYING)
                    ? "pause_icon" : "resume_icon";
            BufferedImage icon = GraphicsManager.getSprite(spriteId);
            if (icon != null) {
                g.drawImage(icon, x, y, width, height, null);
            }
        }

        @Override
        public void onClick() {
            if (gameStateManager.getCurrentState() == GameStateManager.GameState.PLAYING) {
                gameStateManager.setGamePaused();
            } else if (gameStateManager.getCurrentState() == GameStateManager.GameState.PAUSED) {
                gameStateManager.setGameActive();
            }
        }
    }

    //MuteButton
    public static class MuteButton extends UIElement {

        public MuteButton(int x, int y) {
            super(x, y, 40, 40);
        }

        @Override
        public void draw(Graphics g) {
            String spriteId = SoundManager.isMuted() ? "mute_icon" : "sound_icon";
            BufferedImage icon = GraphicsManager.getSprite(spriteId);
            if (icon != null) {
                g.drawImage(icon, x, y, width, height, null);
            }
        }

        @Override
        public void onClick() {
            SoundManager.mute(!SoundManager.isMuted());
        }
    }
}
