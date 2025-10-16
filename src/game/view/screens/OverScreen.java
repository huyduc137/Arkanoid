package game.view.screens;

import game.Constants;
import game.model.GameModel;
import game.model.manager.GameStateManager;
import game.view.UI.UIButton;

import java.awt.*;

import static game.Constants.BUTTON_HEIGHT;
import static game.Constants.BUTTON_WIDTH;

public class OverScreen extends Screen {
    private final GameModel model;

    public OverScreen(GameModel model) {
        super(ScreenType.GAME_OVER);
        this.model = model;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        loadBackground("bg/overScreen.png");
    }

    @Override
    public void initUI() {
        uiManager.add(new UIButton(350, 500,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_replay",
                () -> {
                    model.initGame();
                    model.getGameStateManager().setGameActive();
                }) //action chạy khi click vào nút
        );

        uiManager.add(new UIButton(350 + BUTTON_WIDTH + 10, 500,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_home",
                () -> {
                    model.initGame();
                    model.getGameStateManager().setGameMenu();
                }) //action chạy khi click vào nút
        );
    }
}
