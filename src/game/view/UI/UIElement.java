package game.view.UI;

import java.awt.*;

public abstract class UIElement {
    protected int x, y, width, height;

    public UIElement(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void render(Graphics g);

    public abstract void onClick();

    public boolean contains(int px, int py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }
}
