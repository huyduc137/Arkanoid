package game.view;

import game.Constants;
import game.model.GameModel;
import game.model.manager.GameStateManager;

import java.awt.*;

public class ScreenManager {
    public enum Screen {
        MAIN_MENU,
        GAMEPLAY,
        PAUSE_MENU,
        LEVEL_COMPLETE,
        GAME_OVER,
        SETTINGS,
        HOW_TO_PLAY,
        LEVEL_SELECT,
        HIGH_SCORE,
        WIN
    }

    private Screen currentScreen;
    private Screen overlayScreen;
    private final GameModel model;

    private final GameplayScreen gameplayScreen;
    private final GameOverScreen gameOverScreen;

    public ScreenManager(GameModel model) {
        this.model = model;

        this.currentScreen = Screen.GAMEPLAY;
        this.overlayScreen = null;

        this.gameplayScreen = new GameplayScreen(model);
        this.gameOverScreen = new GameOverScreen(model);
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setScreen(Screen screen) {
        this.currentScreen = screen;
    }

    public void setOverlay(Screen screen) {
        this.overlayScreen = screen;
    }

    public void clearOverlay() {
        this.overlayScreen = null;
    }

    private void drawCenteredText(Graphics2D g2, String text) {
        FontMetrics fm = g2.getFontMetrics();
        int x = (Constants.SCREEN_HEIGHT - fm.stringWidth(text)) / 2;
        int y = (Constants.SCREEN_WIDTH - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(text, x, y);
    }

    //Chọn screen default cho các state (chỉ gồm các screen chính)
    public Screen getDefaultScreenForState(GameStateManager.GameState state) {
        return switch (state) {
            case MENU -> Screen.MAIN_MENU;
            case WAITING_FOR_START, PLAYING -> Screen.GAMEPLAY;
            case PAUSED -> Screen.PAUSE_MENU;
            case GAME_OVER -> Screen.GAME_OVER;
            case LEVEL_COMPLETE -> Screen.LEVEL_COMPLETE;
            case WIN -> Screen.WIN;
        };
    }

    // Xem game state là gì rồi đổi màn hình tương ứng
    public void syncWithGameState() {
        GameStateManager.GameState gameState = model.getGameStateManager().getCurrentState();

        switch (gameState) {
            //Phân biệt overlay với screen bình thường
            case PAUSED -> setOverlay(Screen.PAUSE_MENU);
            case GAME_OVER -> setOverlay(Screen.GAME_OVER);
            case LEVEL_COMPLETE -> setOverlay(Screen.LEVEL_COMPLETE);
            default -> {
                setScreen(getDefaultScreenForState(gameState));
                clearOverlay();
            }
        }
    }

    // Check và vẽ màn hình theo loại
    private void renderScreen(Graphics2D g2, Screen screen) {
        switch (screen) {
            case GAMEPLAY -> gameplayScreen.render(g2);

            case MAIN_MENU -> {
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 40));
                drawCenteredText(g2, "MAIN MENU");
            }

            case GAME_OVER -> gameOverScreen.render(g2);

            case PAUSE_MENU -> {
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 40));
                drawCenteredText(g2, "Paused");
            }

            case SETTINGS -> {
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 40));
                drawCenteredText(g2, "Settings");
            }

            case HOW_TO_PLAY -> {
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 40));
                drawCenteredText(g2, "How to play");
            }

            case HIGH_SCORE -> {
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 40));
                drawCenteredText(g2, "High scores");
            }

            default -> {}
        }
    }

    // Vẽ màn hình chính và overlay
    public void render(Graphics2D g2) {
        renderScreen(g2, currentScreen);

        if(overlayScreen != null) {
            renderScreen(g2, overlayScreen);
        }
    }
}
