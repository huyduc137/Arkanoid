package game.model;

import game.Constants;

public class Ball extends MovableObject{
    public Ball(int x, int y , int diameter){
        super(x , y , diameter , diameter);
        resetBall();
    }

    @Override
    public void move(double dt){
        super.move(dt);
        // check va chạm tường trái, phai
        if (x <= 0 || x + width >= Constants.SCREEN_WIDTH) reverseDx();
        // check va chạm trên
        if (y <= 0)  reverseDy();
        // BỎ: không xử lý va chạm dưới ở đây nữa, để GameModel xử lý
    }

    public void resetBall(){
        this.x = Constants.SCREEN_WIDTH / 2 - width / 2;
        this.y = Constants.SCREEN_HEIGHT / 2 - height / 2;
        this.dx = Constants.BALL_SPEED;
        this.dy = -Constants.BALL_SPEED;
    }
}
