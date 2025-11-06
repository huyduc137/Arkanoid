package game.model.manager;

import game.Constants;
import game.model.GameModel;
import game.model.powerups.ActivePowerUp;
import game.model.powerups.PowerUp;
import game.model.powerups.PowerUpFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerUpManager {
    private final List<PowerUp> fallingPowerUps;
    private final List<ActivePowerUp> activePowerUps;
    private final PowerUpFactory powerUpFactory;
    private final Random random;

    public PowerUpManager(GameModel model) {
        this.fallingPowerUps = new ArrayList<>();
        this.activePowerUps = new ArrayList<>();
        this.powerUpFactory = new PowerUpFactory(model);
        this.random = new Random();
    }

    public void update(double dt) {
        updateActivePowerUps(dt);
        updateFallingPowerUps(dt);
    }

    private void updateFallingPowerUps(double dt) {
        //Cập nhật power-up đang rơi
        List<PowerUp> fallingToRemove = new ArrayList<>();
        for (PowerUp powerUp : fallingPowerUps) {
            // Cập nhật vị trí và kiểm tra va chạm
            powerUp.update(dt);

            if (powerUp.getIsActive()) {
                // Log khi power-up được kích hoạt
                activePowerUps(powerUp);
                fallingToRemove.add(powerUp);
            } else if (powerUp.getY() > Constants.SCREEN_HEIGHT) {
                fallingToRemove.add(powerUp);
            }
        }
        fallingPowerUps.removeAll(fallingToRemove);
    }

    private void updateActivePowerUps(double dt) {
        //Cập nhật power-up đang hoạt động
        List<ActivePowerUp> activeToRemove = new ArrayList<>();
        for (ActivePowerUp active : activePowerUps) {
            active.update(dt); // Cập nhật duration của ActivePowerUp
            active.getPowerUp().update(dt); // Gọi update của PowerUp để xử lý logic đặc biệt (như bắn đạn)

            //Log thời gian còn lại
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

    public void spawnRandomPowerUp(int x, int y) {
        if (random.nextFloat() < 0.3f) {
            PowerUp.PowerUpType type = getRandomPowerUpType();
            PowerUp powerUp = powerUpFactory.create(type, x, y);
            fallingPowerUps.add(powerUp);
        }
    }

    private PowerUp.PowerUpType getRandomPowerUpType() {
        int value = random.nextInt(10);
        return switch (value) {
            case 9 -> PowerUp.PowerUpType.INVERT;
            case 3, 4 -> PowerUp.PowerUpType.FIREBALL;
            case 5, 6 -> PowerUp.PowerUpType.MULTIBALL;
            case 7, 8 -> PowerUp.PowerUpType.PADDLE_WITH_GUN;
            default -> PowerUp.PowerUpType.EXTEND_PADDLE;
        };
    }

    public List<PowerUp> getFallingPowerUps() {
        return fallingPowerUps;
    }

    public List<ActivePowerUp> getActivePowerUps() {
        return activePowerUps;
    }

    public void clearAll() {
        for (ActivePowerUp active : activePowerUps) {
            active.getPowerUp().remove();
        }

        activePowerUps.clear();
        fallingPowerUps.clear();
    }
}
