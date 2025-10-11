package game.view.UI;

import game.model.manager.GraphicsManager;

import java.awt.*;

public class UIButton extends UIElement {
    private final Runnable action;

    public UIButton(int x, int y, int width, int height, String spriteId, Runnable action) {
        super(x, y, width, height);
        this.action = action;

        sprite = GraphicsManager.getSprite(spriteId);
    }

    @Override
    public void draw(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
        } else {
            System.err.println("No button sprite");
        }
    }

    @Override
    public void onClick() {
        if (action != null) action.run();
    }
}
