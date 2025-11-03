package game.model.powerups;

import game.model.GameModel;

public class PowerUpFactory {

    private final GameModel model;

    // Constructor nhận GameModel để truyền vào các PowerUp
    public PowerUpFactory(GameModel model) {
        this.model = model;
    }

    //Tạo PowerUp dựa trên loại
    public PowerUp create(PowerUp.PowerUpType type, int x, int y) {
        return switch (type) {
            case PADDLE_WITH_GUN -> new PaddleWithGun(x, y, model);
            case MULTIBALL       -> new MultiBall(x, y, model);
            case INVERT          -> new Invert(x, y, model);
            case FIREBALL        -> new FireBall(x, y, model);
            case EXTEND_PADDLE   -> new ExtendPaddle(x, y, model);
        };
    }
}
