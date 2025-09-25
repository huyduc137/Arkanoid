package game.view.screens;

import java.awt.*;

import static game.view.ScreenManager.drawCenteredText;

public class GameOverScreen {
    public void render(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        drawCenteredText(g2, "GAME OVER");
    }
}
