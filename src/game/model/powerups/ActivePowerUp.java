package game.model.powerups;

import game.model.powerups.PowerUp.PowerUpType;

public class ActivePowerUp {
    private final PowerUp powerUp;
    private double duration;

    public ActivePowerUp(PowerUp powerUp, double duration) {
        this.powerUp = powerUp;
        this.duration = duration;
    }

    public void update(double dt) {
        duration -= dt;
    }

    public boolean isExpired() {
        return duration <= 0;
    }

    public void reset(double duration) {
        this.duration = duration;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    public PowerUpType getType() {
        return powerUp.getType();
    }
    public double getDuration() {
        return duration;
    }
}
