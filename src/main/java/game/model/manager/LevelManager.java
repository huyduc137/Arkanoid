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
                "Smile :)",
                "map/map1.txt",
                false,
                true,
                200,
                0.0
        ));
        levels.add(new Level(
                "Raining Bricks",
                "map/map2.txt",
                true,
                false,
                200,
                0.7
        ));
        levels.add(new Level(
                "3 Angy Men",
                "map/map3.txt",
                true,
                true,
                200,
                0
        ));
        levels.add(new Level(
                "Eye Spy",
                "map/map4.txt",
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

    public void setLevelEasy() {
        currentLevelIndex = 0;
    }

    public void setLevelMedium() {
        currentLevelIndex = 1;
    }

    public void setLevelHard() {
        currentLevelIndex = 2;
    }

    public void setLevelAsian() {
        currentLevelIndex = 3;
    }

    public int getCurrentLevelIndex() {
        return currentLevelIndex + 1;
    }
}
