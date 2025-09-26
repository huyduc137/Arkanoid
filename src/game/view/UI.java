package game.view;

import game.Constants;
import game.model.GameModel;

import java.awt.*;

// Class này vẽ mấy cái đồ hoạ đè lên gameplay của người chơi
// Score, Health, Level number,...
public class UI {
    private final GameModel model;

    public UI(GameModel model) {
        this.model = model;
    }

    public void render(Graphics2D g2) {
        // Vẽ score
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + model.getScoreSystem().getScore(), Constants.BRICK_WIDTH / 2, Constants.BRICK_HEIGHT);

        // THÊM: Vẽ số mạng
        g2.drawString("Lives: " + model.getScoreSystem().getLives(),
                Constants.SCREEN_WIDTH - 100, Constants.BRICK_HEIGHT);

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
