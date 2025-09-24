package game.controller;

import game.Constants;
import game.model.GameModel;
import game.model.Paddle;
import game.view.GameView;

import java.awt.event.*;
import javax.swing.Timer;

public class GameController implements ActionListener, KeyListener, MouseMotionListener {
    private GameModel model;
    private Timer timer;
    private GameView view;
    public GameController(GameModel model) {
        this.model = model;
        this.timer = new Timer(Constants.GAME_DELAY, this);
    }
    public void setViewGame(GameView view) {
        this.view = view;
        this.timer.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        model.update();
        if (view != null) {
            view.repaintPanel(); // yêu cầu View vẽ lại
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e){             // khi nhấn phím
        Paddle paddle = model.getPaddle();
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            paddle.moveLeft();
        }
        if (key == KeyEvent.VK_RIGHT) {
            paddle.moveRight();
        }
    }
    @Override
    public void keyReleased(KeyEvent e){           // khi thả phím
        Paddle paddle = model.getPaddle();
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            paddle.stop();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Paddle paddle = model.getPaddle();
        int newX = e.getX() - paddle.getWidth() / 2;     // lấy vị trí chính giữa của paddle theo trục x;
        paddle.setX(newX);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }
}
