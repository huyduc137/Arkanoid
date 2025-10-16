package game.view.screens;

import game.Constants;
import game.model.GameModel;

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

    }
}
