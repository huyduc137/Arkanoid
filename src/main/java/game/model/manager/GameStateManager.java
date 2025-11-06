package game.model.manager;

import game.sound.SoundManager;

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
        WIN,
        TUTORIAL,
        HIGH_SCORE,
        DIFFICULTY,
        SETTING
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
                currentState == GameState.PAUSED;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAME_OVER;
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

    // tránh việc mất máu liên tục sau khi nhận sats thương
    public void updateInvulnerability(double dt) {
        if (invulnerable) {
            invulnerableTimer -= dt;
            if (invulnerableTimer <= 0) {
                invulnerable = false;
            }
        }
    }

    public void setGameActive() {
        currentState = GameState.PLAYING;

        SoundManager.stop("background_test");
    }

    public void setGamePaused() {
        currentState = GameState.PAUSED;

        SoundManager.play("pause");
    }

    public void setGameMenu() {
        currentState = GameState.MENU;

        SoundManager.play("background_test");
    }
}
