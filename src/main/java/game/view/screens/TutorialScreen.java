package game.view.screens;

import game.Constants;
import game.model.GameModel;
import game.model.manager.GameStateManager;
import game.view.UI.UIButton;

import java.awt.*;

import static game.Constants.BUTTON_HEIGHT;
import static game.Constants.BUTTON_WIDTH;

public class TutorialScreen extends Screen {
    private final GameModel model;

    public TutorialScreen(GameModel model) {
        super(ScreenType.TUTORIAL);
        this.model = model;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        loadBackground("bg/TutorialScreen.png");
    }

    @Override
    public void initUI() {
        uiManager.add(new UIButton(Constants.SCREEN_WIDTH - 220, Constants.SCREEN_HEIGHT / 2 - 230,
                42, 42,
                "button_close",
                () -> {
                    model.getGameStateManager().setState(GameStateManager.GameState.MENU);
                })
        );
    }
}
