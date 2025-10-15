package game.view.screens;

import game.Constants;
import game.model.GameModel;
import game.model.manager.GameStateManager;
import game.view.UI.UIButton;

import java.awt.*;

import static game.Constants.BUTTON_HEIGHT;
import static game.Constants.BUTTON_WIDTH;

public class WinnerScreen extends Screen{
    private final GameModel model;
    public WinnerScreen(GameModel model) {
        super(ScreenType.GAME_WINNER);
        this.model = model;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        loadBackground("bg/WinnerScreen.png");
    }

    @Override
    public void initUI(){
        uiManager.add(new UIButton(265, 505,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_next_level",
                () -> {
                    model.initGame();
                    model.getGameStateManager().setState(GameStateManager.GameState.PLAYING);
                })
        );

        uiManager.add(new UIButton(265 + BUTTON_WIDTH + 20, 505,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_replay",
                () -> {
                    model.initGame();
                    model.getGameStateManager().setState(GameStateManager.GameState.PLAYING);
                })
        );

        uiManager.add(new UIButton(265 + 2*BUTTON_WIDTH + 20*2, 505,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_home",
                () -> {
                    model.initGame();
                    model.getGameStateManager().setState(GameStateManager.GameState.MENU);
                }) //action chạy khi click vào nút
        );

    }
}
