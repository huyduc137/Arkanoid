package game.model.entity;

import game.Constants;
import game.model.manager.GraphicsManager;

import java.awt.*;

public class Ball extends MovableObject {
    private boolean isFireBall;

    private double collisionCooldown = 0.0; // seconds

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

    public double getCollisionCooldown() {
        return collisionCooldown;
    }

    public void setCollisionCooldown(double cooldown) {
        this.collisionCooldown = cooldown;
    }

    // Delay collision, avoid sticking to moving bricks
    public void updateCooldown(double dt) {
        if (collisionCooldown > 0) {
            collisionCooldown -= dt;
            if (collisionCooldown < 0) collisionCooldown = 0;
        }
    }

    @Override
    public void move(double dt){
        super.move(dt);
        // check va chạm tường trái, phai
        if (this.x <= Constants.EXTRA_DISTANCE){
            this.x = Constants.EXTRA_DISTANCE;
            if (this.dx < 0) this.reverseDx();
        }
        else if (this.x + this.width >= Constants.SCREEN_WIDTH - Constants.EXTRA_DISTANCE){
            this.x = Constants.SCREEN_WIDTH - this.width - Constants.EXTRA_DISTANCE;
            if (this.dx > 0) reverseDx();
        }
        if (this.y <= Constants.EXTRA_DISTANCE) {
            this.y = Constants.EXTRA_DISTANCE;
            if(this.dy < 0) reverseDy();
        }
//        System.out.println(this.dx);
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

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if(isFireBall) {
            sprite = GraphicsManager.getSprite("fireBall");
        }
        else {
            sprite = GraphicsManager.getSprite("ball");
        }

        if (sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
        } else {
            g.setColor(isFireBall ? Color.RED : Color.WHITE);
            g.fillOval(x, y, width, height);
        }
    }
}
