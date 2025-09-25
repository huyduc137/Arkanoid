package game.model.powerups;

import game.Constants;
import game.model.GameModel;

public class ExtendPaddle extends PowerUp {
    private static final int EXTENSION_AMOUNT = Constants.PADDLE_WIDTH / 2; // Thêm 50% chiều rộng ban đầu

    public ExtendPaddle(int x, int y, GameModel model) {
        super(x, y, Constants.POWERUP_SIZE, Constants.POWERUP_SIZE, 10.0, model); // 10 giây
    }

    @Override
    public void apply() {
        model.addPaddleExtension(EXTENSION_AMOUNT);
    }

    @Override
    public void remove() {
        model.removePaddleExtension(EXTENSION_AMOUNT);
    }
}