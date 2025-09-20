package game.view;

import game.Constants;
import game.model.GameModel;

import javax.swing.*;

public class GameView extends JFrame {
    private GamePanel gamePanel;
    private GameModel gameModel;
    public GameView() {
        this.gameModel = new GameModel();
        gamePanel = new GamePanel(gameModel);
        add(gamePanel);
        initViewGame();
    }
    private void initViewGame() {
        this.setTitle("Arkanoid");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setResizable(false);
        this.pack();                                               // tự động đặt kích thước cho vừa frame
        this.setLocationRelativeTo(null);
    }
    public void repaintPanel() {
        gamePanel.repaint();
    }
}
