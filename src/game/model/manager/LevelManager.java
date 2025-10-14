package game.model.manager;

import game.model.entity.Level;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private final List<Level> levels = new ArrayList<>();
    private int currentLevelIndex = 0;

    public LevelManager() {
        // Test levels
        levels.add(new Level(
                "Level 1",
                "map/map1.txt",
                false,
                false,
                0.0,
                0.0
        ));
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    public void setLevelIndex(int index) {
        if (index < 0 || index >= levels.size()) throw new ArrayIndexOutOfBoundsException("Invalid level index");
        currentLevelIndex = index;
    }

    public boolean hasNextLevel() {
        return currentLevelIndex < levels.size() - 1;
    }

    public Level nextLevel() {
        if(!hasNextLevel()) return getCurrentLevel();
        currentLevelIndex++;
        return getCurrentLevel();
    }
}
