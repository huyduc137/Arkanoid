package game.model;

import game.Constants;
import java.awt.Rectangle;

public abstract class PowerUp extends GameObject {
    protected double duration; // Thời gian tồn tại của power-up (giây)
    protected double timeLeft; // Thời gian còn lại
    protected boolean isActive; // Trạng thái kích hoạt
    protected double dy; // Tốc độ rơi của power-up
    protected GameModel model; // Tham chiếu đến GameModel để áp dụng hiệu ứng

    public PowerUp(int x, int y, int width, int height, double duration, GameModel model) {
        super(x, y, width, height);
        this.duration = duration;
        this.timeLeft = duration;
        this.isActive = false;
        this.dy = Constants.POWERUP_SPEED; // Tốc độ rơi
        this.model = model;
    }

    // Cập nhật trạng thái power-up
    public void update(double dt) {
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
                isActive = false; // Đánh dấu để xóa trong GameModel
            }
        }
    }

    // Phương thức trừu tượng để áp dụng hiệu ứng
    public abstract void apply();

    // Phương thức trừu tượng để xóa hiệu ứng
    public abstract void remove();

    public boolean isActive() {
        return isActive;
    }

    public boolean isExpired() {
        return timeLeft <= 0;
    }

}