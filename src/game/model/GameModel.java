package game.model;

import game.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;

    public GameModel() {
        initGame();
    }
    public void initGame() {
        ball = new Ball(Constants.SCREEN_WIDTH/2 - Constants.BALL_DIAMETER ,
                        Constants.SCREEN_HEIGHT/2 - Constants.BALL_DIAMETER ,
                           Constants.BALL_DIAMETER);
        paddle = new Paddle(Constants.SCREEN_WIDTH/2 - Constants.PADDLE_WIDTH/2 ,
                            Constants.SCREEN_HEIGHT - Constants.PADDLE_Y_OFFSET - Constants.PADDLE_HEIGHT/2 ,
                               Constants.PADDLE_WIDTH , Constants.PADDLE_HEIGHT);
        bricks = new ArrayList<>();
        initBrick();
    }
    public void initBrick() {

    }
    public Paddle getPaddle() {return paddle;}
    public Ball getBall() {return ball;}
    public List<Brick> getBricks() {return bricks;}
}
