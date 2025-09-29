package game;

public class Constants {
    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 768;

    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 20;
    public static final int PADDLE_Y_OFFSET = 40;
    public static final double PADDLE_SPEED = 780.0;

    public static final int BALL_DIAMETER = 20;
    public static final double BALL_SPEED = 420.0;

    public static final int BRICK_WIDTH = 64;
    public static final int BRICK_HEIGHT = 32;

    public static final double POWERUP_SPEED = 200.0;
    public static final int POWERUP_SIZE = 20;
    public static final double POWERUP_DURATION = 10.0;

    public static final int TARGET_FPS = 60;
    public static final int GAME_DELAY = 1000 / TARGET_FPS;
}
