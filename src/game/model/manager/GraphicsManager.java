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
        loadSprite("brick_attack", "sprites/bricks/brick_attack.png");

        loadSprite("button_play", "sprites/buttons/play.png");
        loadSprite("button_replay", "sprites/buttons/replay.png");
        loadSprite("button_home", "sprites/buttons/home.png");
        loadSprite("button_start", "sprites/buttons/start.png");
        loadSprite("button_tutorial", "sprites/buttons/tutorial.png");
        loadSprite("button_close", "sprites/buttons/close.png");
        loadSprite("button_next_level",  "sprites/buttons/NextLevel.png");
        loadSprite("button_high_score",  "sprites/buttons/HighScore.png");
        loadSprite("button_easy",  "sprites/buttons/easy.png");
        loadSprite("button_normal",  "sprites/buttons/normal.png");
        loadSprite("button_hard",  "sprites/buttons/hard.png");
        loadSprite("button_veryhard",  "sprites/buttons/very hard.png");

        loadSprite("paddle", "sprites/paddle/paddle.png");
        loadSprite("paddle2", "sprites/paddle/paddle2.png");
        loadSprite("paddlegun", "sprites/paddle/paddlegun.png");
        loadSprite("paddle2gun", "sprites/paddle/paddle2gun.png");
        //loadSprite("paddle3", "sprites/paddle/paddle3.png");

        loadSprite("fireball", "sprites/powerup/fireball.png");
        loadSprite("multiball", "sprites/powerup/multiball.png");
        loadSprite("paddlewithgun", "sprites/powerup/paddlewithgun.png");
        loadSprite("extendpaddle", "sprites/powerup/extendpaddle.png");
        loadSprite("invert", "sprites/powerup/invert.png");

        loadSprite("pause_icon", "sprites/buttons/pause_icon.png");
        loadSprite("resume_icon", "sprites/buttons/resume_icon.png");

        loadSprite("button_setting", "sprites/buttons/setting.png");
        loadSprite("home_setting", "sprites/buttons/home.png");

        loadSprite("heart_full", "sprites/buttons/red_heart.png");
        loadSprite("heart_empty", "sprites/buttons/white_heart.png");
        loadSprite("sound_icon", "sprites/buttons/unmute.png");
        loadSprite("mute_icon", "sprites/buttons/mute.png");
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
