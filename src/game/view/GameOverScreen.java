package game.view;

import game.Constants;
import game.model.GameModel;

import java.awt.*;

public class GameOverScreen {
    private final GameModel model;

    public GameOverScreen(GameModel model) {
        this.model = model;
    }

    public void render(Graphics2D g2) {
        // THÊM: Vẽ thông báo game over nếu cần
        if (model.getGameStateManager().isGameOver()) {
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            g2.setColor(Color.RED);
            String gameOverText = "GAME OVER";
            int textWidth = g2.getFontMetrics().stringWidth(gameOverText);
            g2.drawString(gameOverText,
                    (Constants.SCREEN_WIDTH - textWidth) / 2,
                    Constants.SCREEN_HEIGHT / 2);
        }
    }
}
