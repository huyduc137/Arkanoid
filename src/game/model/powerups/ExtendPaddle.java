package game.model.powerups;

import game.Constants;
import game.model.GameModel;

import java.awt.*;

public class ExtendPaddle extends PowerUp {
    private final int EXTENSION_AMOUNT = Constants.PADDLE_WIDTH / 2; // Thêm 50% chiều rộng ban đầu
    private GameModel model;

    public ExtendPaddle(int x, int y, GameModel model) {
        super(x, y, Constants.POWERUP_SIZE, Constants.POWERUP_SIZE, Constants.POWERUP_DURATION, model);
        this.model = model;
    }

    @Override
    public void apply() {
        addPaddleExtension(EXTENSION_AMOUNT);
    }

    @Override
    public void remove() {
        removePaddleExtension(EXTENSION_AMOUNT);
    }

    public void addPaddleExtension(int amount) {
        this.model.getPaddle().setWidth(this.model.getPaddle().getWidth() + amount);
    }
    public void removePaddleExtension(int amount) {
        this.model.getPaddle().setWidth(this.model.getPaddle().getWidth() - amount);
    }

    @Override
    public void draw(Graphics g) {

    }
}