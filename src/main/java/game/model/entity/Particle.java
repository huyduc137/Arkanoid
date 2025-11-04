package game.model.entity;

import game.Constants;

import java.awt.*;

public class Particle {
    double x, y;
    double dx, dy;
    double duration; // seconds
    Color color;
    float alpha; // độ rõ alpha 0–1 (0 thì nhìn xuyên qua luôn)

    public Particle(double x, double y, double dx, double dy, double duration, Color color) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.duration = duration;
        this.color = color;
        this.alpha = 1.0f;
    }

    public void update(double dt) {
        x += dx * dt;
        y += dy * dt;
        duration -= dt;
        alpha = (float)Math.max(0, duration / Constants.BALL_PARTICLE_DURATION); //Biến mất dần theo duration
    }

    public void draw(Graphics2D g) {
        if (duration <= 0) return;
        Composite old = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.setColor(color);
        g.fillOval((int)x, (int)y, 6, 6);
        g.setComposite(old);
    }

    public boolean isDead() { return duration <= 0; }
}

