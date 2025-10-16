package game.model.manager;

import static game.Constants.INVULNERABLE_DURATION;

public class GameStateManager {
    // Check xem paddle có ăn damage ko
    private boolean invulnerable = false;
    private double invulnerableTimer = 0.0;

    public enum GameState {
        MENU,
        WAITING_FOR_START,
        PLAYING,
        PAUSED,
        GAME_OVER,
        GAME_WINNER,
        LEVEL_COMPLETE,
        HIGH_SCORE
    }

    private GameState currentState;
    private boolean ballOnPaddle;

    public GameStateManager() {
        reset();
    }

    public void reset() {
        currentState = GameState.MENU;
        ballOnPaddle = true;
    }

    //getter + setter
    public GameState getCurrentState() {
        return currentState;
    }

    public void setState(GameState gameState) {
        currentState = gameState;
    }

    public boolean isBallOnPaddle() {
        return ballOnPaddle;
    }

    public void setBallOnPaddle(boolean ballOnPaddle) {
        this.ballOnPaddle = ballOnPaddle;
    }

    public boolean isGameActive() {
        return currentState == GameState.PLAYING ||
                currentState == GameState.PAUSED ||
                currentState == GameState.WAITING_FOR_START;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAME_OVER;
    }

    public boolean isWaitingToStart() {
        return currentState == GameState.WAITING_FOR_START;
    }

    public boolean isPaused() {
        return currentState == GameState.PAUSED;
    }

    public boolean isLevelComplete() {
        return currentState == GameState.LEVEL_COMPLETE;
    }

    public void setInvulnerable(boolean state) {
        this.invulnerable = state;
        this.invulnerableTimer = state ? INVULNERABLE_DURATION : 0.0;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public void updateInvulnerability(double dt) {
        if (invulnerable) {
            invulnerableTimer -= dt;
            if (invulnerableTimer <= 0) {
                invulnerable = false;
            }
        }
    }
}
