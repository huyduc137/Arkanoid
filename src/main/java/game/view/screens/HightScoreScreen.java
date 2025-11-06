package game.view.screens;

import game.Constants;
import game.model.GameModel;
import game.model.entity.Level;
import game.model.manager.FontManager;
import game.model.manager.GameStateManager;
import game.model.manager.HighScoreManager;
import game.view.UI.UIButton;
import game.view.UI.UILabel;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

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
        uiManager.add(new UIButton(Constants.SCREEN_WIDTH - 135, Constants.SCREEN_HEIGHT / 2 - 170,
                42, 42,
                "button_close",
                () -> {
                    model.getGameStateManager().setState(GameStateManager.GameState.MENU);
                })
        );

        Font scoreFont = FontManager.getFont("Tektur Bold", 26f);
        Color textColor = Color.WHITE;
        List<Level> levels = model.getLevelManager().getLevels();

        int numberOfLevels = levels.size();
        int startY = 500;
        int posBegin = 180;
        for (int i = 0; i < numberOfLevels; i++) {
            Level level = levels.get(i);
            String levelName = level.getName();
            //function để lấy điểm, không thay đổi
            Supplier<String> scoreTextSupplier = () -> {
                int highScore = HighScoreManager.getHighScore(levelName);
                return "" + (highScore > 0 ? highScore : "0000");
            };
            int positionX = posBegin + posBegin*i + 20*i;
            uiManager.add(new UILabel(
                    positionX,
                    startY,
                    scoreTextSupplier,
                    textColor,
                    scoreFont
            ));
        }

    }
}
