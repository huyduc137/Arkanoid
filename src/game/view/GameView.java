package game.view;

import game.Constants;
import game.controller.GameController;
import game.model.GameModel;

import javax.swing.*;

public class GameView extends JFrame {
    private GamePanel gamePanel;
    public GameView(GameModel model, GameController controller) {
        gamePanel = new GamePanel(model);
        this.add(gamePanel);
        this.addKeyListener(controller);
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
