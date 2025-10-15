package game.view.screens;

import game.view.UI.UIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public abstract class Screen extends JPanel {
    private final ScreenType type;
    protected BufferedImage backgroundImage;
    protected final UIManager uiManager = new UIManager();
    protected boolean initializedUI; //Theo dõi xem UI đã được gọi chưa để tránh tràn bộ nhớ :)

    public enum ScreenType {
        MENU,
        GAME,
        PAUSE,
        GAME_OVER,
        LEVEL_COMPLETE,
        TUTORIAL
    }

    public Screen(ScreenType type) {
        this.type = type;
        setOpaque(true); // important for proper repainting
        setLayout(null); // we will manually handle UI positioning
    }

    public ScreenType getType() {
        return type;
    }

    public abstract void initUI();

    //Gọi initUI một lần duy nhất khi show() được gọi trong screenManager
    public void initUIOnce() {
        if(initializedUI) {
            return;
        }

        initializedUI = true;
        initUI();
    }

    protected void loadBackground(String path) {
        try (InputStream is = getClass().getResourceAsStream("/" + path)) {
            if (is != null) {
                backgroundImage = ImageIO.read(is);
            } else {
                System.err.println("Background not found: " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            // fallback
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // Draw UI elements
        if (uiManager != null) {
            uiManager.render(g);
        }
    }
}
