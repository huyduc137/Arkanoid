package game.model.manager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GraphicsManager {
    private static final Map<String, BufferedImage> sprites = new HashMap<>();

    private GraphicsManager() {}

    public static void loadAll() {
        loadSprite("ball", "sprites/ball/ball.png");
        loadSprite("fireBall", "sprites/ball/fireBall.png");

        loadSprite("brick_1", "sprites/bricks/brick_1.png");
        loadSprite("brick_2", "sprites/bricks/brick_2.png");
        loadSprite("brick_3", "sprites/bricks/brick_3.png");
        loadSprite("brick_unbreakable", "sprites/bricks/brick_unbreakable.png");

        loadSprite("button_play", "sprites/buttons/play.png");
        loadSprite("button_replay", "sprites/buttons/replay.png");
        loadSprite("button_home", "sprites/buttons/home.png");
        loadSprite("button_next_level",  "sprites/buttons/NextLevel.png");
        loadSprite("button_high_score",  "sprites/buttons/HighScore.png");

        loadSprite("paddle", "sprites/paddle/paddle.png");
        loadSprite("paddle2", "sprites/paddle/paddle2.png");
        //loadSprite("paddle3", "sprites/paddle/paddle3.png");

        loadSprite("fireball", "sprites/powerup/fireball.png");

        loadSprite("pause_icon", "sprites/buttons/pause_icon.png");
        loadSprite("button_setting", "sprites/buttons/setting.png");
        loadSprite("resume_icon", "sprites/buttons/pause_icon.png");
    }

    public static void loadSprite(String id, String path) {
        try (InputStream is = GraphicsManager.class.getResourceAsStream("/" + path)) {
            if (is == null) {
                System.err.println("Sprite not found: " + path);
                return;
            }

            BufferedImage img = ImageIO.read(is);
            sprites.put(id, img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage getSprite(String id) {
        return sprites.get(id);
    }
}
