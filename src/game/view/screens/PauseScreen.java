package game.view.screens;

import game.Constants;
import game.model.GameModel;
import game.model.manager.GameStateManager;
import game.view.UI.UIButton;
import game.view.UI.UILabel;

import java.awt.*;

import static game.Constants.BUTTON_HEIGHT;
import static game.Constants.BUTTON_WIDTH;

public class PauseScreen extends Screen {
    private final GameModel model;

    public PauseScreen(GameModel model) {
        super(ScreenType.PAUSE);
        this.model = model;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        loadBackground("bg/pause_background.png"); // Giả sử bạn có file background cho pause (blue overlay). Nếu không, có thể dùng fallback hoặc vẽ custom trong paintComponent.
    }

    @Override
    public void initUI() {
        uiManager.add(new UIButton((Constants.SCREEN_WIDTH - 84) / 2, (Constants.SCREEN_HEIGHT - 84) / 2, 84, 84,
                "pause_icon",
                () -> model.getGameStateManager().setState(GameStateManager.GameState.PLAYING)));


        uiManager.add(new UIButton(Constants.SCREEN_WIDTH / 2 - BUTTON_WIDTH - 10, Constants.SCREEN_HEIGHT / 2 + BUTTON_HEIGHT + 20,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_setting",
                () -> {
                    System.out.println("Settings opened");
                }));

        uiManager.add(new UIButton(Constants.SCREEN_WIDTH / 2 + 10, Constants.SCREEN_HEIGHT / 2 + BUTTON_HEIGHT + 20,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_home",
                () -> model.getGameStateManager().setState(GameStateManager.GameState.MENU)));
    }
}
