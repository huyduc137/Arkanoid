package game.view.UI;

import java.awt.*;

public class UIButton extends UIElement {
    private final String text;
    private final Runnable action;
    private final Font font;

    public UIButton(int x, int y, int width, int height, String text, Font font, Runnable action) {
        super(x, y, width, height);
        this.text = text;
        this.action = action;
        this.font = font;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();

        // center text
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int textX = x + (width - textWidth) / 2;
        int textY = y + (height + textHeight) / 2 - 2;

        g.setColor(Color.WHITE);
        g.drawString(text, textX, textY);
    }

    @Override
    public void onClick() {
        if (action != null) action.run();
    }
}
