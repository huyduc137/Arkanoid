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

            double angle;
            do {
                angle = Math.toRadians(30 + Math.random() * 120); // 30° đến 150°
            } while (Math.abs(Math.toDegrees(angle) - 90) < 10); // bỏ vùng 80°–100°

            newBall.setDx(Math.cos(angle) * Constants.BALL_SPEED);
            newBall.setDy(-Math.sin(angle) * Constants.BALL_SPEED);

            model.addBall(newBall);
        }

        shouldRemove = true;
    }
        @Override
    public void remove() { }
}
