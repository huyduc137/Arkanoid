package game.model.powerups;

import game.Constants;
import game.model.GameModel;
import game.model.entity.GameObject;

public abstract class PowerUp extends GameObject {
    protected double duration; //thời gian tồn tại
    protected double timeLeft; //thời gian còn lại
    protected boolean isActive;
    protected boolean shouldRemove;
    protected double dy;
    protected GameModel model;

    public PowerUp(int x, int y, int width, int height, double duration, GameModel model) {
        super(x, y, width, height);
        this.duration = duration;
        this.timeLeft = duration;
        this.isActive = false;
        this.dy = Constants.POWERUP_SPEED;
        this.shouldRemove = false;
        this.model = model;
    }

    public void update(double dt) {
        if (shouldRemove) return; // Không cập nhật nếu đã đánh dấu xóa

        if (!isActive) {
            // Di chuyển xuống dưới
            y += dy * dt;
            // Kiểm tra va chạm với paddle
            if (getBounds().intersects(model.getPaddle().getBounds())) {
                isActive = true;
                apply();
            }
        } else {
            // Giảm thời gian tồn tại
            timeLeft -= dt;
            if (timeLeft <= 0) {
                remove();
                shouldRemove = true; // Đánh dấu để xóa trong GameModel
            }
        }
    }

    public abstract void apply();

    public abstract void remove();

    public boolean getIsActive() {
        return isActive;
    }

    public boolean getIsExpired() {
        return shouldRemove || timeLeft <= 0;
    }

}