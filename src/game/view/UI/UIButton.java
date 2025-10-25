package game.view.UI;

import game.model.manager.GraphicsManager;
import game.model.manager.SoundManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIButton extends UIElement {
    private final Runnable action;

    public UIButton(int x, int y, int width, int height, String spriteId, Runnable action) {
        super(x, y, width, height);
        this.action = action;

        sprite = GraphicsManager.getSprite(spriteId);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Enable smooth rendering
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
        } else {
            System.err.println("No button sprite");
        }
    }

    @Override
    public void onClick() {
        if (action != null) {
            SoundManager.play("button_click");
            action.run();
        }
    }
}
