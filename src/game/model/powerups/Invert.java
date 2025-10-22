package game.model.powerups;

import game.Constants;
import game.controller.GameController;
import game.model.GameModel;
import game.model.manager.GraphicsManager;
import game.view.GameView;

import java.awt.*;

public class Invert extends PowerUp {
    public Invert(int x, int y, GameModel model) {
        super(x, y, Constants.POWERUP_SIZE, Constants.POWERUP_SIZE, Constants.POWERUP_DURATION, PowerUpType.INVERT, model);
    }


    @Override
    public void apply() {
        if (model.getGameView() != null) {
            model.getGameView().setScreenInverted(true);
        }
    }

    @Override
    public void remove() {
        if (model.getGameView() != null) {
            model.getGameView().setScreenInverted(false);
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        sprite = GraphicsManager.getSprite("invert");
        if (sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
        }
        else {
            System.err.println("Invert Sprite is null");
        }
    }
}
