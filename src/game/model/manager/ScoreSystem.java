package game.model.manager;

import game.sound.SoundManager;

public class ScoreSystem {
    private int score;
    private int lives;
    private static final int INITIAL_LIVES = 3;

    public ScoreSystem() {
        reset();
    }

    public void addScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    public void loseLife() {
        if (lives > 0) {
            lives--;
        }

        SoundManager.play("life_lose");
    }

    public int getLives() {
        return lives;
    }

    public void reset() {
        score = 0;
        lives = INITIAL_LIVES;
    }
}
