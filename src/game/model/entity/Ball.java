package game.model.entity;

import game.Constants;

public class Ball extends MovableObject {
    private boolean isFireBall;

    public Ball(int x, int y , int diameter){
        super(x , y , diameter , diameter);
        this.dx = Constants.BALL_SPEED;
        this.dy = -Constants.BALL_SPEED;
        isFireBall = false;
    }
    public Ball(int diameter) {
        super(Constants.SCREEN_WIDTH / 2 - diameter / 2, Constants.SCREEN_HEIGHT / 2 - diameter / 2, diameter, diameter);
        resetBall();
        isFireBall = false;
    }

    @Override
    public void move(double dt){
        super.move(dt);
        // check va chạm tường trái, phai
        if (this.x <= 0){
            this.x = 0;
            this.reverseDx();
        }
        else if (this.x + this.width >= Constants.SCREEN_WIDTH){
            this.x = Constants.SCREEN_WIDTH - this.width;
            reverseDx();
        }
        if (this.y <= 0) {
            this.y = 0;
            reverseDy();
        }
    }

    public void resetBall(){
        this.x = Constants.SCREEN_WIDTH / 2 - width / 2;
        this.y = Constants.SCREEN_HEIGHT / 2 - height / 2;
        this.dx = Constants.BALL_SPEED;
        this.dy = -Constants.BALL_SPEED;
    }

    public boolean isFireBall() {
        return isFireBall;
    }
    public void setFireBall(boolean fireBall) {
        isFireBall = fireBall;
    }
}
