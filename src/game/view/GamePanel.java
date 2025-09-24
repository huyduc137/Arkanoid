package game.view;

import game.Constants;
import game.model.Brick;
import game.model.GameModel;
import game.model.Paddle;
import game.model.Ball;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final GameModel model;
    public GamePanel(GameModel model) {
        this.model = model;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));       // LINK với PACK() IN GameView;
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {                 // LINK với repaintPanel() in GameView;
        super.paintComponent(g);

        for(Brick brick: model.getBricks()) {
            if(!brick.isDestroyed()) {
                switch (brick.getBrickType()) {
                    case NORMAL -> {
                        switch (brick.getHitPoints()) {
                            case 1 -> g.setColor(Color.RED);
                            case 2 -> g.setColor(Color.ORANGE);
                            case 3 -> g.setColor(Color.CYAN);
                        }
                    }
                    case UNBREAKABLE -> g.setColor(Color.GRAY);
                }
                g.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                g.setColor(Color.BLACK);
                g.drawRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
            }
        }

        Paddle paddle = model.getPaddle();
        g.setColor(Color.WHITE);
        g.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

        Ball ball = model.getBall();
        g.setColor(Color.YELLOW);
        g.fillOval(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
    }
}
