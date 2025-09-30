package game.model.powerups;

import game.model.GameModel;
import game.model.entity.Ball;
import game.Constants;
import java.util.List;

public class MultiBall extends PowerUp {
    public MultiBall(int x, int y, GameModel model) {
        super(x, y, Constants.POWERUP_SIZE, Constants.POWERUP_SIZE, Double.POSITIVE_INFINITY, model);
    }

    @Override
    public void apply() {
        List<Ball> currentBalls = model.getBalls();
        int numBallsToAdd = currentBalls.size(); // x2 bóng
        if (model.getGameStateManager().isBallOnPaddle()) {
            Ball mainBall = currentBalls.get(0);
            mainBall.setDx(Constants.BALL_SPEED);
            mainBall.setDy(-Constants.BALL_SPEED);
            model.getGameStateManager().setBallOnPaddle(false);
        }

        for (int i = 0; i < numBallsToAdd; i++) {
            Ball oldBall = currentBalls.get(i);

            // Tạo bóng mới tại đúng vị trí của bóng gốc
            Ball newBall = new Ball(oldBall.getX(), oldBall.getY(), Constants.BALL_DIAMETER);

            double dx = Math.random() < 0.5 ? -Constants.BALL_SPEED : Constants.BALL_SPEED;
            double dy = -Constants.BALL_SPEED;

            newBall.setDx(dx);
            newBall.setDy(dy);

            model.addBall(newBall);
        }

        shouldRemove = true;
    }
        @Override
    public void remove() { }
}
