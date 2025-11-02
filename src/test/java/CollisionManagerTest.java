import game.Constants;
import game.model.GameModel;
import game.model.entity.Ball;
import game.model.entity.Brick;
import game.model.manager.CollisionManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CollisionManagerTest {
    public GameModel gameModel;
    private Ball ball;
    private Brick brick;
    private CollisionManager collisionManager;

    /**
     * tạo các đối tượng trước khi test.
     */
    @Before
    public void setUp() {
        gameModel = new GameModel();
        gameModel.getBricks().clear();
        gameModel.getBalls().clear();

        collisionManager = new CollisionManager(gameModel);
        ball = new Ball(0, 0, Constants.BALL_DIAMETER);
        brick = new Brick(0, 0, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT, 1, Brick.BrickType.NORMAL);
        gameModel.getBalls().add(ball);
        gameModel.getBricks().add(brick);
    }

    @Test
    public void testBallCollisionWithBrick() {
        // test va chạm trên

        // set vị trí brick
        brick.setX(100);
        brick.setY(100);

        // set vị trí ball
        ball.setX(100 + (Constants.BRICK_WIDTH / 2) - (Constants.BALL_DIAMETER / 2));
        ball.setY(100 -  (Constants.BALL_DIAMETER));

        // set tốc độ ball
        ball.setDx(0);
        ball.setDy(Constants.BALL_SPEED);
        ball.setCollisionCooldown(0.0);

        int brickHp = brick.getHitPoints();
        double ballDy = ball.getDy();

        System.out.println("Ball X: " + ball.getX() + " Ball Y: " + ball.getY());
        System.out.println("Brick X: " + brick.getX() + " Brick Y: " + brick.getY());
        System.out.println("Fireball? " + ball.isFireBall());
        System.out.println("Giao nhau? " + ball.getBounds().intersects(brick.getBounds()));
        System.out.println("Cooldown: " + ball.getCollisionCooldown());
        System.out.println("Ball Dy: " + ball.getDy());
        System.out.println("Brick HP: " + brick.getHitPoints());

        ball.move(0.016);
        collisionManager.checkCollisions();

        System.out.println("------------------------------------");
        System.out.println("Ball X: " + ball.getX() + " Ball Y: " + ball.getY());
        System.out.println("Brick X: " + brick.getX() + " Brick Y: " + brick.getY());
        System.out.println("Fireball? " + ball.isFireBall());
        System.out.println("Giao nhau? " + ball.getBounds().intersects(brick.getBounds()));
        System.out.println("Cooldown: " + ball.getCollisionCooldown());
        System.out.println("Ball Dy: " + ball.getDy());
        System.out.println("Brick HP: " + brick.getHitPoints());

        // máu của brick giảm đi 1
        assertEquals("Máu của Brick phải giảm đi 1", brickHp - 1, brick.getHitPoints());

        // sau khi va chạm Dy của ball phải được đổi chiều
        assertEquals("sau khi va chạm Dy của ball phải được đổi chiều", -ballDy, ball.getDy(), 0.01);
    }
}
