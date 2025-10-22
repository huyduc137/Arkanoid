package game.model.entity;

public class Level {
    private final String name;
    private final String mapPath;

    private final boolean bricksFall;
    private final boolean bricksAttack;

    private final double brickFallSpeed;
    private final double brickAttackSpeed;

    private double brickFallTimer = 0.0;

    public Level(String name, String mapPath,
                 boolean bricksFall, boolean bricksAttack,
                 double brickAttackSpeed, double brickFallSpeed) {
        this.name = name;
        this.mapPath = mapPath;
        this.bricksFall = bricksFall;
        this.bricksAttack = bricksAttack;
        this.brickFallSpeed = brickFallSpeed;
        this.brickAttackSpeed = brickAttackSpeed;
    }

    public double getBrickFallTimer() { return brickFallTimer; }
    public void setBrickFallTimer(double t) { brickFallTimer = t; }

    public double getBrickFallSpeed() {
        return brickFallSpeed;
    }

    public double getBrickAttackSpeed() {
        return brickAttackSpeed;
    }

    public boolean isBricksAttack() {
        return bricksAttack;
    }

    public boolean isBricksFall() {
        return bricksFall;
    }

    public String getMapPath() {
        return mapPath;
    }

    public String getName() {
        return name;
    }

    public void reset() {
        brickFallTimer = 0.0;
    }
}
