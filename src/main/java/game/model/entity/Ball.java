package game.model.entity;

import game.Constants;
import game.model.manager.GraphicsManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ball extends MovableObject {
    private final List<Particle> particles = new ArrayList<>();

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
        particles.removeIf(p -> {
            p.update(dt);
            return p.isDead();
        });

        super.move(dt);

        if (isFireBall) {
            spawnParticles();
        }

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

        Graphics2D g2d = (Graphics2D) g;

        // Draw trail (under the ball)
        for (Particle p : particles) {
            p.draw(g2d);
        }

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

    private void spawnParticles() {
        // Random góc, chuyển góc thành vận tốc
        double angle = Math.random() * 2 * Math.PI; // góc từ 0 -> 2pi
        double speed = Constants.BALL_PARTICLE_SPEED + Math.random() * 50; // tốc độ từ Base -> Base + 50
        double dx = Math.cos(angle) * speed;
        double dy = Math.sin(angle) * speed;
        Color color = Color.RED;

        //Độ rộng dải particle
        double particleSpread = Constants.BALL_DIAMETER - 10;

        //Spread từ -0.5 đến 0.5 => Xung quanh tâm bóng
        particles.add(new Particle(
                x + (double) width / 2 + (Math.random() - 0.5) * particleSpread,
                y + (double) height / 2 + (Math.random() - 0.5) * particleSpread,
                dx,
                dy,
                Constants.BALL_PARTICLE_DURATION,
                color
        ));

        // limit the number of active particles (avoid lag)
        if (particles.size() > 100) {
            particles.removeFirst();
        }
    }

}
