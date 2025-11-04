package game.view.UI;

import java.awt.*;
import java.util.function.Supplier;

public class UILabel extends UIElement {
    //Text supplier là functional interface -> cho phép truyền một lamda function để được gói vào thành object
    //gọi get() mỗi khi được render để update text một cách tự động
    protected final Supplier<String> textSupplier;
    protected final Color color;
    protected final Font font;

    public UILabel(int x, int y, Supplier<String> textSupplier, Color color, Font font) {
        super(x, y, 0, 0);
        this.textSupplier = textSupplier;
        this.color = color;
        this.font = font;
    }

    @Override
    public void draw(Graphics g) {
        g.setFont(font);
        g.setColor(color);
        g.drawString(textSupplier.get(), x, y);
    }
}
