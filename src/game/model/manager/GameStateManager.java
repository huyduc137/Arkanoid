package game.model.manager;

public class GameStateManager {
    public enum GameState {
        MENU,
        WAITING_FOR_START,
        PLAYING,
        PAUSED,
        GAME_OVER,
        LEVEL_COMPLETE,
        WIN,
        TUTORIAL
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
        return currentState == GameState.PLAYING;
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
}