package game.controller;

import game.Constants;
import game.model.GameModel;
import game.model.entity.Paddle;
import game.model.manager.GameStateManager;
import game.model.manager.SettingManager;
import game.view.GameView;

import java.awt.event.*;
import javax.swing.*;

public class GameController implements ActionListener, KeyListener, MouseMotionListener, MouseListener {
    private final GameModel model;
    private final Timer timer;
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
        long now = System.nanoTime();
        double dt = (now - lastTime) / 1e9;
        lastTime = now;

        if (model.getGameStateManager().isGameOver()) {
            return;
        }

        // Chỉ update nếu đang PLAYING
        if (model.getGameStateManager().getCurrentState() == GameStateManager.GameState.PLAYING) {
            // Logic update paddle, model.update(dt),...
            Paddle paddle = model.getPaddle();
            if (leftPressed && !rightPressed) {
                paddle.moveLeft();
            } else if (rightPressed && !leftPressed) {
                paddle.moveRight();
            } else {
                paddle.stop();
            }

            model.update(dt);
        }

        if (view != null) {
            view.updateScreen();
            view.repaintPanel();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        Paddle paddle = model.getPaddle();
        int key = e.getKeyCode();

        if (SettingManager.getControlType().equals(SettingManager.ControlType.KEYBOARD)) {
            if (key == KeyEvent.VK_LEFT) {
                leftPressed = true;
                rightPressed = false;
                if (view != null && view.isScreenInverted()) {
                    paddle.moveRight();
                }
                paddle.moveLeft();
            }
            if (key == KeyEvent.VK_RIGHT) {
                rightPressed = true;
                leftPressed = false;
                if (view != null && view.isScreenInverted()) {
                    paddle.moveLeft();
                }
                paddle.moveRight();
            }
        }
        if (key == KeyEvent.VK_SPACE) {
            model.launchBall();
        }
        if (key == KeyEvent.VK_ESCAPE) {
            if (model.getGameStateManager().isGameActive()) {
                model.getGameStateManager().setGameMenu();
            }
        }
        if (key == KeyEvent.VK_P) {
            GameStateManager.GameState current = model.getGameStateManager().getCurrentState();
            if (current == GameStateManager.GameState.PLAYING) {
                model.getGameStateManager().setGamePaused();
                System.out.println("Game Paused");
            } else if (current == GameStateManager.GameState.PAUSED) {
                model.getGameStateManager().setGameActive();
                System.out.println("Game Resumed");
            }
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
        //kiểm tra nếu mà không phải mouse thì không thực thi di chuyển bằng mouse
        if (SettingManager.getControlType() != SettingManager.ControlType.MOUSE){
            return;
        }

        Paddle paddle = model.getPaddle();
        if (e.getX() <= 15 + (Constants.PADDLE_WIDTH / 2) || e.getX() >= Constants.SCREEN_WIDTH - 15 - (Constants.PADDLE_WIDTH/2)) return;
        int newX = e.getX() - paddle.getWidth() / 2;     // lấy vị trí chính giữa của paddle theo trục x;
        paddle.setX(newX);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (model.getGameStateManager().isBallOnPaddle() && model.getGameStateManager().isGameActive()) {
                System.out.println("mouse");
                model.launchBall();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
