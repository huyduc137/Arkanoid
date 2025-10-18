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
                "map/map2.txt",
                false,
                true,
                200,
                0.0
        ));
        levels.add(new Level(
                "Level 2",
                "map/map1.txt",
                true,
                true,
                200,
                1
        ));

    }

    public List<Level> getLevels() {
        return levels;
    }
    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    public boolean hasNextLevel() {
        return currentLevelIndex < levels.size() - 1;
    }

    public void toNextLevel() {
        if(!hasNextLevel()) {
            return;
        }
        currentLevelIndex++;
    }
}
