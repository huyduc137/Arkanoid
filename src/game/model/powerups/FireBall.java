package game.model.powerups;

import game.Constants;
import game.model.GameModel;
import game.model.entity.Ball;

import java.awt.*;

public class FireBall extends PowerUp {
    public FireBall(int x, int y, GameModel model) {
        super(x, y, Constants.POWERUP_SIZE, Constants.POWERUP_SIZE, Constants.POWERUP_DURATION, model);
    }

    @Override
    public void apply() {
        for (Ball ball : model.getBalls()) {
            ball.setFireBall(true);
        }
    }

    @Override
    public void remove() {
        for (Ball ball : model.getBalls()) {
            ball.setFireBall(false);
        }
    }

    @Override
    public void draw(Graphics g) {

    }
}
