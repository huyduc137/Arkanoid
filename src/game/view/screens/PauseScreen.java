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
        loadBackground("bg/pause_background.png");
        initUI();
    }

    @Override
    public void initUI() {
        // Nút Resume/Play
        uiManager.add(new UIButton((Constants.SCREEN_WIDTH - 84) / 2, (Constants.SCREEN_HEIGHT - 84) / 2, 84, 84,
                "pause_icon",
                () -> model.getGameStateManager().setState(GameStateManager.GameState.PLAYING)));

        int totalButtonsWidth = BUTTON_WIDTH * 2 + 20; // 2 nút + khoảng cách 20px
        int startX = (Constants.SCREEN_WIDTH - totalButtonsWidth) / 2;
        int buttonY = Constants.SCREEN_HEIGHT / 2 + BUTTON_HEIGHT + 50;

        // Nút Setting
        uiManager.add(new UIButton(startX, buttonY,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_setting",
                () -> {
                    // Thêm logic mở setting ở đây
                    System.out.println("Settings opened");
                }));

        // Nút Home - đảm bảo cùng kích thước với nút Setting
        uiManager.add(new UIButton(startX + BUTTON_WIDTH + 20, buttonY,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                "button_home",
                () -> model.getGameStateManager().setState(GameStateManager.GameState.MENU)));
    }
}
