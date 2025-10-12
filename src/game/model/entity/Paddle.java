package game.model.entity;

import game.Constants;
import game.model.manager.GraphicsManager;

import java.awt.*;

public class Paddle extends MovableObject {
    private boolean gunsActive = false;

    public Paddle(int x , int y , int width , int height) {
        super(x,  y, width, height);
    }

    @Override
    public void draw(Graphics g) {
        String spriteId;
        if (width == Constants.PADDLE_WIDTH) {spriteId = "paddle";}
        else {spriteId = "paddle2";}
        // else spriteId = "paddle3";
        sprite = GraphicsManager.getSprite(spriteId);
        if (sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
        } else {
            System.err.println("No paddle sprite");
        }
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
    public void move(double dt) {
        super.move(dt);
        if (x < 0){
            x = 0;
        }
        if (x + width > Constants.SCREEN_WIDTH){
            x =  Constants.SCREEN_WIDTH - width;
        }
    }

    public boolean hasGuns() {
        return gunsActive;
    }

    public void setGunsActive(boolean active) {
        this.gunsActive = active;
    }
}
