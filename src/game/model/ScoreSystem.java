package game.model;

// Class này theo dõi logic các state của người chơi
// Score, Health
public class ScoreSystem {
    private int score;

    public void addScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    public void reset() {
        score = 0;
    }
}
