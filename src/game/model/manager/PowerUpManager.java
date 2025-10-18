package game.model.manager;

import game.model.GameModel;
import game.model.powerups.ActivePowerUp;
import game.model.powerups.PowerUp;

import java.util.ArrayList;
import java.util.List;

public class PowerUpManager {
    private List<PowerUp> fallingPowerUps = new ArrayList<>();
    private List<ActivePowerUp> activePowerUps = new ArrayList<>();

    public List<PowerUp> getFallingPowerUps() {
        return fallingPowerUps;
    }

    public List<ActivePowerUp> getActivePowerUps() {
        return activePowerUps;
    }

    public void update(double dt) {
        //Cập nhật power-up đang rơi
        List<PowerUp> fallingToRemove = new ArrayList<>();
        for (PowerUp powerUp : fallingPowerUps) {
            // Cập nhật vị trí và kiểm tra va chạm
            powerUp.update(dt);

            if (powerUp.getIsActive()) {
                // Log khi power-up được kích hoạt
                activePowerUps(powerUp);
                fallingToRemove.add(powerUp);
            } else if (powerUp.shouldRemove) {
                fallingToRemove.add(powerUp);
            }
        }
        fallingPowerUps.removeAll(fallingToRemove);

        //Cập nhật power-up đang hoạt động
        List<ActivePowerUp> activeToRemove = new ArrayList<>();
        for (ActivePowerUp active : activePowerUps) {
            active.update(dt); // Cập nhật duration của ActivePowerUp
            active.getPowerUp().update(dt); // Gọi update của PowerUp để xử lý logic đặc biệt (như bắn đạn)

            // Log thời gian còn lại
            //System.out.println("PowerUp đang hoạt động: " + active.getType() + ", Thời gian còn lại: " + active.getDuration() + " giây");

            if (active.isExpired()) {
                //System.out.println("PowerUp hết hạn: " + active.getType());
                deactivatePowerUp(active.getPowerUp());
                activeToRemove.add(active);
            }
        }
        activePowerUps.removeAll(activeToRemove);
    }

    public void activePowerUps(PowerUp powerUp) {
        for (ActivePowerUp active : activePowerUps) {
            if (active.getType() == powerUp.getType()) {
                active.reset(powerUp.getDuration());
                return;
            }
        }
        activePowerUps.add(new ActivePowerUp(powerUp, powerUp.getDuration()));
    }

    private void deactivatePowerUp(PowerUp powerUp) {
        powerUp.remove();
    }
}
