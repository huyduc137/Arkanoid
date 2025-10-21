package game.view.screens;

import game.Constants;
import game.model.GameModel;
import game.model.manager.GameStateManager;
import game.view.UI.UIButton;

import java.awt.*;

import static game.Constants.BUTTON_HEIGHT;
import static game.Constants.BUTTON_WIDTH;

public class DifficultyScreen extends Screen {
    private final GameModel model;

    public DifficultyScreen(GameModel model) {
        super(ScreenType.DIFFICULTY);
        this.model = model;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        loadBackground("bg/DifficultyScreen.png");
    }

    @Override
    public void initUI() {
        uiManager.add(new UIButton(Constants.SCREEN_WIDTH / 2 - 78 , Constants.SCREEN_HEIGHT / 2 - 85,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_easy",
                () -> {
                    model.getLevelManager().setLevelEasy();
                    model.initGame();
                    model.getGameStateManager().setGameActive();
                })
        );      
        uiManager.add(new UIButton(Constants.SCREEN_WIDTH / 2 - 78 , Constants.SCREEN_HEIGHT / 2 - 16,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_normal",
                () -> {
                    model.getLevelManager().setLevelMedium();
                    model.initGame();
                    model.getGameStateManager().setGameActive();
                })
        );
        uiManager.add(new UIButton(Constants.SCREEN_WIDTH / 2 - 78 , Constants.SCREEN_HEIGHT / 2 + 55,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_hard",
                () -> {
                    model.getLevelManager().setLevelHard();
                    model.initGame();
                    model.getGameStateManager().setGameActive();
                })
        );
        uiManager.add(new UIButton(Constants.SCREEN_WIDTH / 2 - 78 , Constants.SCREEN_HEIGHT / 2 + 125,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_veryhard",
                () -> {
                    model.getLevelManager().setLevelAsian();
                    model.initGame();
                    model.getGameStateManager().setGameActive();
                })
        );
        uiManager.add(new UIButton(Constants.SCREEN_WIDTH - 220, Constants.SCREEN_HEIGHT / 2 - 230,
                42, 42,
                "button_close",
                () -> {
                    model.initGame();
                    model.getGameStateManager().setState(GameStateManager.GameState.MENU);
                })
        );
    }
}
