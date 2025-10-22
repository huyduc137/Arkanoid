package game.model.powerups;

import game.Constants;
import game.model.GameModel;
import game.model.entity.GameObject;
import game.model.manager.SoundManager;

public abstract class PowerUp extends GameObject {
    protected double duration; //thời gian tồn tại
    private final PowerUpType type;
    protected boolean isActive;
    public boolean shouldRemove;
    protected double dy;
    protected GameModel model;

    public enum PowerUpType {
        FIREBALL,
        EXTEND_PADDLE,
        PADDLE_WITH_GUN,
        MULTIBALL,
        INVERT
    }
    public PowerUp(int x, int y, int width, int height, double duration, PowerUpType type, GameModel model) {
        super(x, y, width, height);
        this.duration = duration;
        this.type = type;
        this.isActive = false;
        this.dy = Constants.POWERUP_SPEED;
        this.shouldRemove = false;
        this.model = model;
    }

    public void update(double dt) {
        if (shouldRemove) return; // Không cập nhật nếu đã đánh dấu xóa

        if (!isActive) {
            // Di chuyển xuống dưới
            this.y += dy * dt;
            // Kiểm tra va chạm với paddle
            if (getBounds().intersects(model.getPaddle().getBounds())) {
                isActive = true;
                apply();

                SoundManager.play("power_up");
            }
        }
    }

    public abstract void apply();

    public abstract void remove();

    public boolean getIsActive() {
        return isActive;
    }

    public boolean getIsExpired() {
        return shouldRemove || duration <= 0;
    }

    public double getDuration() { return duration; }

    public PowerUpType getType() { return type; }

}