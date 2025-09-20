package game.model;

import game.Constants;

public class Paddle extends MovableObject {
    public Paddle(int x , int y , int width , int height) {
        super(x,  y, width, height);
    }
    public void moveLeft(){
        dx = -Constants.PADDLE_SPEED;
    }
    public void moveRight(){
        dx = Constants.PADDLE_SPEED;
    }
    public void stop(){
        dx = 0;
    }
    @Override
    public void move(){
        super.move();
        if (x < 0){
            x = 0;
        }
        if (x + width > Constants.SCREEN_WIDTH){
            x =  Constants.SCREEN_WIDTH - width;
        }
    }
}
