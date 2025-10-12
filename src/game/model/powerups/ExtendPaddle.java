package game.model.powerups;

import game.Constants;
import game.model.GameModel;

import java.awt.*;

public class ExtendPaddle extends PowerUp {
    private GameModel model;

    public ExtendPaddle(int x, int y, GameModel model) {
        super(x, y, Constants.POWERUP_SIZE, Constants.POWERUP_SIZE, Constants.POWERUP_DURATION, model);
        this.model = model;
    }

    @Override
    public void apply() {
        if (model != null && model.getPaddle() != null) {
            int currentWidth = model.getPaddle().getWidth();
            int newWidth = currentWidth + Constants.EXTENSION_AMOUNT;

            if (newWidth <= Constants.MAX_PADDLE_WIDTH) {
                model.getPaddle().setWidth(newWidth);
            } else {
                shouldRemove = true; // Xóa power-up ngay lập tức
            }
        }
    }
    @Override
    public void remove() {
        if (model != null && model.getPaddle() != null) {
            int currentWidth = model.getPaddle().getWidth();
            int newWidth = currentWidth - Constants.EXTENSION_AMOUNT;
            model.getPaddle().setWidth(Math.max(Constants.PADDLE_WIDTH, newWidth)); // Đảm bảo không nhỏ hơn chiều rộng ban đầu
            shouldRemove = true;
        }
    }

    @Override
    public void draw(Graphics g) {

    }
}