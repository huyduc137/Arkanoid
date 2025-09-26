package game.model.powerups;

import game.Constants;
import game.model.GameModel;

public class FireBall extends PowerUp {
    public FireBall(int x, int y, GameModel model) {
        super(x, y, Constants.POWERUP_SIZE, Constants.POWERUP_SIZE, 10.0, model);
    }
    public void apply() {
        model.getBall().setFireBall(true);
    }
    public void remove() {
        model.getBall().setFireBall(false);
    }
}
