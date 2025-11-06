package game.view.screens;

import game.Constants;
import game.model.GameModel;
import game.model.manager.FontManager;
import game.model.manager.HighScoreManager;
import game.view.UI.UIButton;
import game.view.UI.UILabel;

import java.awt.*;
import java.util.function.Supplier;

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
                    model.getLevelManager().toNextLevel();
                    model.initGame();
                    model.getGameStateManager().setGameActive();
                })
        );

        uiManager.add(new UIButton(265 + BUTTON_WIDTH + 20, 505,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_replay",
                () -> {
                    model.initGame();
                    model.getGameStateManager().setGameActive();
                })
        );

        uiManager.add(new UIButton(265 + 2*BUTTON_WIDTH + 20*2, 505,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_home",
                () -> {
                    model.getGameStateManager().setGameMenu();
                })
        );

        String levelName = model.getLevelManager().getCurrentLevel().getName();
        Font scoreFont = FontManager.getFont("Tektur Bold", 30f);
        Color textColor = Color.WHITE;
        Supplier<String> scoreTextSupplier = () -> {
            int highScore = HighScoreManager.getHighScore(levelName);
            return "" + (highScore > 0 ? highScore : "0000");
        };
        uiManager.add(new UILabel(
                555,
                405,
                scoreTextSupplier,
                textColor,
                scoreFont
        ));
        uiManager.add(new UILabel(
                395,
                405,
                ()->""+model.getScoreSystem().getScore(),
                textColor,
                scoreFont
        ));
    }
}
