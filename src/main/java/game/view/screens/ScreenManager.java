package game.view.screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class ScreenManager {
    private final JPanel container;
    private final CardLayout layout;
    private final Map<Screen.ScreenType, Screen> screens = new HashMap<>();
    private Screen currentScreen;

    public ScreenManager() {
        layout = new CardLayout();
        container = new JPanel(layout);

        container.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentScreen.uiManager.handleClick(e.getX(), e.getY());
            }
        });
    }

    public JPanel getContainer() {
        return container;
    }

    public void addScreen(Screen screen) {
        screens.put(screen.getType(), screen);
        container.add(screen, screen.getType().name());
    }

    public void show(Screen.ScreenType type) {
        //Kiểm tra xem màn hình hiện tại có đúng với màn hình yêu cầu ko, nếu không thì xoá đi
        if (currentScreen != null && currentScreen.getType() != type) {
            currentScreen.uiManager.clear();
            currentScreen.initializedUI = false;
        }

        currentScreen = screens.get(type);
        if (currentScreen != null) {
//            System.out.println("[ScreenManager] Switching to screen: " + currentScreen.getType());
            layout.show(container, type.name());

            currentScreen.initUIOnce();
//            System.out.println("[ScreenManager] UI initialized for screen: " + type);

            container.revalidate();
            container.repaint();
        }
    }
}
