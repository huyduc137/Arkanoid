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
    private long lastTime;
    private GameView view;
    private boolean leftPressed;
    private boolean rightPressed;

    public GameController(GameModel model) {
        this.model = model;
        this.timer = new Timer(Constants.GAME_DELAY, this);
    }

    public void setViewGame(GameView view) {
        this.view = view;
        this.lastTime = System.nanoTime();
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Paddle paddle = model.getPaddle();
        if (leftPressed && !rightPressed) {
            paddle.moveLeft();
        }
        else if (rightPressed && !leftPressed) {
            paddle.moveRight();
        }
        else {
            paddle.stop();
        }
        long now = System.nanoTime();
        double dt = (now - lastTime) / 1e9; // thời gian giữa các frame
        lastTime = now;

        model.update(dt);
        if (view != null) {
            view.repaintPanel(); // yêu cầu View vẽ lại
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {             // khi nhấn phím
        Paddle paddle = model.getPaddle();
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            leftPressed = true;
            rightPressed = false;
            paddle.moveLeft();
        }
        if (key == KeyEvent.VK_RIGHT) {
            rightPressed = true;
            leftPressed = false;
            paddle.moveRight();
        }
        // THÊM: Xử lý phím Space để phóng ball
        if (key == KeyEvent.VK_SPACE) {
            model.launchBall();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {           // khi thả phím
        Paddle paddle = model.getPaddle();
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            rightPressed = false;
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
