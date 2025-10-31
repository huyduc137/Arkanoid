package game.view.screens;

import game.Constants;
import game.model.GameModel;
import game.model.manager.GameStateManager;
import game.model.manager.SettingManager;
import game.view.UI.HudElements;
import game.view.UI.UIButton;

import java.awt.*;

public class SettingScreen extends Screen {
    private final GameModel model;
    private UIButton keyboardButton;
    private UIButton mouseButton;
    public SettingScreen(GameModel model) {
        super(ScreenType.SETTING);
        this.model = model;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        loadBackground("bg/SettingBg.png");
    }
    @Override
    public void initUI(){
        //Close button
        uiManager.add(new UIButton(Constants.SCREEN_WIDTH - 225, Constants.SCREEN_HEIGHT / 2 - 215,
                42, 42,
                "button_close",
                () -> model.getGameStateManager().setState(GameStateManager.GameState.MENU))
        );

        //Mute button
        int iconSize = 40;
        uiManager.add(new HudElements.MuteButton(
                Constants.SCREEN_WIDTH / 2 - iconSize / 2,
                520
        ));

        //Keyboard/Mouse buttons
        keyboardButton = new UIButton(Constants.SCREEN_WIDTH / 2 - 215, (Constants.SCREEN_HEIGHT / 2) - 30,
                170, 124,
                "button_tutorial1",
                () -> {
                    SettingManager.setControlType(SettingManager.ControlType.KEYBOARD);
                    System.out.println(SettingManager.getControlType());
                    updateButtonSelection();
                });

        mouseButton = new UIButton(Constants.SCREEN_WIDTH / 2 + 45, (Constants.SCREEN_HEIGHT / 2) - 30,
                170, 124,
                "button_tutorial2",
                () -> {
                    SettingManager.setControlType(SettingManager.ControlType.MOUSE);
                    System.out.println(SettingManager.getControlType());
                    updateButtonSelection();
                });

        uiManager.add(keyboardButton);
        uiManager.add(mouseButton);
        updateButtonSelection();
    }
    private void updateButtonSelection() {
        SettingManager.ControlType currentType = SettingManager.getControlType();

        keyboardButton.setSelected(currentType == SettingManager.ControlType.KEYBOARD);
        mouseButton.setSelected(currentType == SettingManager.ControlType.MOUSE);
    }
}
