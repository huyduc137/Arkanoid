package game.view;

import game.Constants;
import game.controller.GameController;
import game.model.GameModel;
import game.model.manager.GraphicsManager;
import game.view.UI.UIManager;
import game.view.screens.GameScreen;
import game.view.screens.MenuScreen;
import game.view.screens.Screen;
import game.view.screens.ScreenManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameView extends JFrame {
    private final GameModel model;
    ScreenManager screenManager;

    public GameView(GameModel model, GameController controller) {
        this.model = model;
        this.addKeyListener(controller);
        screenManager = new ScreenManager();
        //Khởi tạo screen trong screenManager r add vào JFrame
        initScreens();
        this.add(screenManager.getContainer());
        screenManager.getContainer().addMouseMotionListener(controller);
        screenManager.getContainer().addMouseListener(controller);
        //Khởi tạo Panel bằng screenManager r mới pack()
        initViewGame();

        //Load đồ hoạ game
        GraphicsManager.loadAll();

        //Nối vào controller (game loop)
        controller.setViewGame(this);

        screenManager.show(Screen.ScreenType.MENU);

        this.setVisible(true);
    }

    //Thêm tất cả màn hình vào screenManager
    private void initScreens() {
        GameScreen gameScreen = new GameScreen(model);
        screenManager.addScreen(gameScreen);

        MenuScreen menuScreen = new MenuScreen(model);
        screenManager.addScreen(menuScreen);
    }

    //Gọi trong GameController (game loop), check state r vẽ màn tương ứng
    public void updateScreen() {
        switch (model.getGameStateManager().getCurrentState()) {
            case MENU -> {
                screenManager.show(Screen.ScreenType.MENU);
                showMouse();
            }
            case WAITING_FOR_START -> {

            }
            case PLAYING -> {
                screenManager.show(Screen.ScreenType.GAME);
                hideMouse();
            }
            case PAUSED -> screenManager.show(Screen.ScreenType.PAUSE);
            case GAME_OVER -> screenManager.show(Screen.ScreenType.GAME_OVER);
            case LEVEL_COMPLETE, WIN -> screenManager.show(Screen.ScreenType.LEVEL_COMPLETE);
        }
    }

    private void initViewGame() {
        this.setTitle("Arkanoid");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setResizable(false);
        this.pack();                                               // tự động đặt kích thước cho vừa frame
        this.setLocationRelativeTo(null);
    }
    public void repaintPanel() {
        screenManager.getContainer().repaint();
    }

    // ẩn đi con trổ chuột bằng cách tạo 1 con trỏ chuột vô hình
    public void hideMouse(){
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        screenManager.getContainer().setCursor(blankCursor);
    }

    public void showMouse() {
        screenManager.getContainer().setCursor(Cursor.getDefaultCursor());
    }
}
