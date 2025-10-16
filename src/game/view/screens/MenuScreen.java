package game.view.screens;

import game.Constants;
import game.model.GameModel;
import game.model.manager.GameStateManager;
import game.view.UI.UIButton;

import java.awt.*;

import static game.Constants.BUTTON_HEIGHT;
import static game.Constants.BUTTON_WIDTH;

public class MenuScreen extends Screen {
    private final GameModel model;

    public MenuScreen(GameModel model) {
        super(ScreenType.MENU);
        this.model = model;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        loadBackground("bg/Menu.png");
    }

    @Override
    public void initUI() {
        uiManager.add(new UIButton(Constants.SCREEN_WIDTH / 2 - BUTTON_WIDTH / 2, Constants.SCREEN_HEIGHT / 2,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_play",
                    () -> {
                        model.initGame();
                        model.getGameStateManager().setGameActive();
                    }) //action chạy khi click vào nút
        );
        uiManager.add(new UIButton(Constants.SCREEN_WIDTH / 2 - BUTTON_WIDTH / 2, Constants.SCREEN_HEIGHT / 2 + 200,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_tutorial",
                () -> {
                    model.initGame();
                    model.getGameStateManager().setState(GameStateManager.GameState.TUTORIAL);
                })
        );
        uiManager.add(new UIButton(Constants.SCREEN_WIDTH / 2 - BUTTON_WIDTH / 2, (Constants.SCREEN_HEIGHT / 2) +  BUTTON_HEIGHT + 20,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_high_score",
                () -> {
                    model.getGameStateManager().setState(GameStateManager.GameState.HIGH_SCORE);
                })
        );
    }
}
