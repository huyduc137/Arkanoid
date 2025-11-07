package game.view.screens;

import game.Constants;
import game.model.GameModel;
import game.view.UI.UIButton;

import java.awt.*;

import static game.Constants.BUTTON_HEIGHT;
import static game.Constants.BUTTON_WIDTH;

public class PauseScreen extends Screen {
    private final GameModel model;

    public PauseScreen(GameModel model) {
        super(ScreenType.PAUSE);
        this.model = model;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        loadBackground("bg/pause_background.png");
    }

    @Override
    public void initUI() {
        // Nút Resume/Play
        uiManager.add(new UIButton((Constants.SCREEN_WIDTH - 84) / 2, (Constants.SCREEN_HEIGHT - 84) / 2 - 30, 84, 84,
                "resume_icon",
                () -> model.getGameStateManager().setGameActive()));

        int totalButtonsWidth = BUTTON_WIDTH * 2 + 20; // 2 nút + khoảng cách 20px
        int startX = (Constants.SCREEN_WIDTH - totalButtonsWidth) / 2;
        int buttonY = Constants.SCREEN_HEIGHT / 2 + BUTTON_HEIGHT + 50;

        // Nút Replay
        uiManager.add(new UIButton(startX, buttonY,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_replay",
                () -> {
                    model.initGame();
                    model.getGameStateManager().setGameActive();
                }));

        // Nút Home
        uiManager.add(new UIButton(startX + BUTTON_WIDTH + 20, buttonY,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_home",
                () -> model.getGameStateManager().setGameMenu()));
    }
}
