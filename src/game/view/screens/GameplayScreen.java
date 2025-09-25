package game.view.screens;

import game.model.GameModel;
import game.model.entity.Ball;
import game.model.entity.Brick;
import game.model.entity.Paddle;
import game.model.powerups.PowerUp;
import game.view.UI;

import java.awt.*;

public class GameplayScreen {
    private final GameModel model;
    private final UI ui;

    public GameplayScreen(GameModel model) {
        this.model = model;
        this.ui = new UI(model);
    }

    public void render(Graphics2D g2) {

        for(Brick brick: model.getBricks()) {
            if(!brick.isDestroyed()) {
                switch (brick.getBrickType()) {
                    case NORMAL -> {
                        switch (brick.getHitPoints()) {
                            case 1 -> g2.setColor(Color.RED);
                            case 2 -> g2.setColor(Color.ORANGE);
                            case 3 -> g2.setColor(Color.CYAN);
                        }
                    }
                    case UNBREAKABLE -> g2.setColor(Color.GRAY);
                }
                g2.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                g2.setColor(Color.BLACK);
                g2.drawRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
            }
        }

        Paddle paddle = model.getPaddle();
        g2.setColor(Color.WHITE);
        g2.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

        Ball ball = model.getBall();
        g2.setColor(Color.YELLOW);
        g2.fillOval(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());


        for (PowerUp powerup : model.getPowerups()) {
            if (!powerup.getIsActive() && !powerup.getIsExpired()) {
                g2.setColor(Color.GREEN); // Màu cho ExtendPaddle
                g2.fillRect(powerup.getX(), powerup.getY(), powerup.getWidth(), powerup.getHeight());
            }
        }

        ui.render(g2);
    }
}

