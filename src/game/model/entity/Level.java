package game.model.entity;

public class Level {
    private final String name;
    private final String mapPath;

    private final boolean bricksFall;
    private final boolean bricksAttack;

    private final double brickFallSpeed;
    private final double brickAttackSpeed;

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


}
