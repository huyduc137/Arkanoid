package game.view.screens;

import game.Constants;
import game.model.GameModel;
import game.model.manager.FontManager;
import game.model.manager.GameStateManager;
import game.model.manager.HighScoreManager;
import game.view.UI.UIButton;
import game.view.UI.UILabel;

import java.awt.*;
import java.util.function.Supplier;

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

        String levelName = model.getLevelManager().getCurrentLevel().getName();
        Font scoreFont = FontManager.getFont("Tektur Bold", 32f);
        Color textColor = Color.WHITE;
        uiManager.add(new UILabel(
                400,
                400,
                ()->""+model.getScoreSystem().getScore(),
                textColor,
                scoreFont
        ));
        Supplier<String> scoreTextSupplier = () -> {
            int highScore = HighScoreManager.getHighScore(levelName);
            return "" + (highScore > 0 ? highScore : "0000");
        };
        uiManager.add(new UILabel(
                550,
                400,
                scoreTextSupplier,
                textColor,
                scoreFont
        ));

    }
}
