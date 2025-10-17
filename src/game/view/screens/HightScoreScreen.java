package game.view.screens;

import game.Constants;
import game.model.GameModel;
import game.model.manager.GameStateManager;
import game.view.UI.UIButton;

import java.awt.*;

public class HightScoreScreen extends Screen {
    private GameModel model;
    public HightScoreScreen(GameModel model) {
        super(ScreenType.HIGH_SCORE);
        this.model = model;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        loadBackground("bg/HighScoreBg.png");
    }
    @Override
    public void initUI(){
        uiManager.add(new UIButton(Constants.SCREEN_WIDTH - 130, Constants.SCREEN_HEIGHT / 2 - 170,
                42, 42,
                "button_close",
                () -> {
                    model.initGame();
                    model.getGameStateManager().setState(GameStateManager.GameState.MENU);
                }) //action chạy khi click vào nút
        );
    }
}
