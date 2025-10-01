package game.view.UI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIManager {
    private final List<UIElement> elements = new ArrayList<>();

    public void add(UIElement e) {
        elements.add(e);
//        System.out.println("UIManager Added element: " + e + " | total=" + elements.size());
    }

    public void clear() {
//        System.out.println("UIManager Clearing " + elements.size() + " elements");
        elements.clear();
    }

    public void render(Graphics g) {
        for (UIElement e : elements) {
            e.render(g);
        }
    }

    public void handleClick(int x, int y) {
        for (UIElement e : elements) {
            if (e.contains(x, y)) {
                e.onClick();
            }
        }
    }
}
