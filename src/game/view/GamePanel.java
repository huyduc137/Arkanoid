package game.view;

import game.Constants;

import game.model.GameModel;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final ScreenManager screenManager;

    public ScreenManager getScreenManager() {
        return screenManager;
    }

    public GamePanel(GameModel model) {
        this.screenManager = new ScreenManager(model);

        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));       // LINK với PACK() IN GameView;
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        screenManager.syncWithGameState();
        screenManager.render(g2);
    }
}
