package game.view;

import game.Constants;
import game.controller.GameController;
import game.model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameView extends JFrame {
    private GamePanel gamePanel;
    public GameView(GameModel model, GameController controller) {
        gamePanel = new GamePanel(model);
        this.add(gamePanel);
        gamePanel.addMouseMotionListener(controller);
        this.addKeyListener(controller);
        hideMouse();
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

    // ẩn đi con trổ chuột bằng cách tạo 1 con trỏ chuột vô hình
    public void hideMouse(){
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        gamePanel.setCursor(blankCursor);
    }
}
