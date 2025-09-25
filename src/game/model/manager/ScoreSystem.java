package game.model.manager;

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
    }

    public int getLives() {
        return lives;
    }

    public void reset() {
        score = 0;
        lives = INITIAL_LIVES;
    }
}
