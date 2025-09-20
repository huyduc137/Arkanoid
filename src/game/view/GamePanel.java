package game.view;

import game.Constants;
import game.model.GameModel;
import game.model.Paddle;
import game.model.Ball;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private GameModel model;
    public GamePanel(GameModel model) {
        this.model = model;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Paddle paddle = model.getPaddle();
        g.setColor(Color.WHITE);
        g.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

        Ball ball = model.getBall();
        g.setColor(Color.YELLOW);
        g.fillOval(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
    }
}
