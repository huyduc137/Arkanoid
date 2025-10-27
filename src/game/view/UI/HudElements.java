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
    public static class CenteredScoreLabel extends UILabel {
        private final int containerWidth;

        public CenteredScoreLabel(int y, Supplier<String> scoreSupplier, Font font) {
            super(0, y, scoreSupplier, Color.WHITE, font != null ? font : new Font("Arial", Font.BOLD, 28));
            this.containerWidth = Constants.SCREEN_WIDTH;
        }

        @Override
        public void draw(Graphics g) {
            g.setFont(font);
            g.setColor(color);

            String text = textSupplier.get();
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int centerX = (containerWidth - textWidth) / 2;
            int textY = y + fm.getAscent();

            g.drawString(text, centerX, textY);
        }
    }

    //PauseButton
    public static class PauseButton extends UIButton {
        private final GameStateManager gameStateManager;

        public PauseButton(int x, int y, GameStateManager gsm) {
            super(x, y, 40, 40, "pause_icon", null);
            this.gameStateManager = gsm;
        }

        @Override
        public void draw(Graphics g) {
            // Update sprite based on game state
            String spriteId = (gameStateManager.getCurrentState() == GameStateManager.GameState.PLAYING)
                    ? "pause_icon" : "resume_icon";
            this.sprite = GraphicsManager.getSprite(spriteId);

            super.draw(g);
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
    public static class MuteButton extends UIButton {
        public MuteButton(int x, int y) {
            super(x, y, 40, 40, SoundManager.isMutedAll() ? "mute_icon" : "sound_icon", null);
        }

        @Override
        public void draw(Graphics g) {
            this.sprite = GraphicsManager.getSprite(SoundManager.isMutedAll() ? "mute_icon" : "sound_icon");
            super.draw(g);
        }

        @Override
        public void onClick() {
            SoundManager.muteAll(!SoundManager.isMutedAll());
            SoundManager.play("button_click");
        }
    }
}
