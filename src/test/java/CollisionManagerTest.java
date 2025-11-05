import game.Constants;
import game.model.GameModel;
import game.model.entity.Ball;
import game.model.entity.Brick;
import game.model.entity.Paddle;
import game.model.manager.CollisionManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollisionManagerTest {
    public GameModel gameModel;
    private Ball ball;
    private Brick brick;
    private Paddle paddle;
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
        paddle = new Paddle(0, 0, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT);
        gameModel.getBalls().add(ball);
        gameModel.getBricks().add(brick);
        paddle = gameModel.getPaddle();
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
        assertEquals("Sau khi va chạm trên Dy của ball phải được đổi chiều", -ballDy, ball.getDy(), 0.01);
    }

    @Test
    public void testBallHitsBottomOfBrick() {
        brick.setX(200);
        brick.setY(200);

        ball.setX(200 + Constants.BRICK_WIDTH / 2 - Constants.BALL_DIAMETER / 2);
        ball.setY(200 + Constants.BRICK_HEIGHT + 1);

        //Move straight up
        ball.setDx(0);
        ball.setDy(-Constants.BALL_SPEED);

        double oldDy = ball.getDy();
        ball.move(0.016);
        collisionManager.checkCollisions();

        assertEquals("Sau khi va chạm dưới Dy của ball phải được đổi chiều", -oldDy, ball.getDy(), 0.01);
    }

    @Test
    public void testBallHitsLeftSideOfBrick() {
        brick.setX(300);
        brick.setY(200);

        //Set on the left
        ball.setX(300 - Constants.BALL_DIAMETER - 1);
        ball.setY(200 + Constants.BRICK_HEIGHT / 2 - Constants.BALL_DIAMETER / 2);

        //Go right
        ball.setDx(Constants.BALL_SPEED);
        ball.setDy(0);

        double oldDx = ball.getDx();
        ball.move(0.016);
        collisionManager.checkCollisions();

        assertEquals("Sau khi va chạm ngang Dx của ball phải được đổi chiều", -oldDx, ball.getDx(), 0.01);
    }


    @Test
    public void testBallHitsBrickCorner() {
        brick.setX(100);
        brick.setY(100);

        // Place ball top-left corner
        ball.setX(100 - Constants.BALL_DIAMETER);
        ball.setY(100 - Constants.BALL_DIAMETER);

        //Move diagonal
        ball.setDx(Constants.BALL_SPEED);
        ball.setDy(Constants.BALL_SPEED);

        double oldDx = ball.getDx();
        double oldDy = ball.getDy();

        ball.move(0.016);
        collisionManager.checkCollisions();

        boolean bounced = ball.getDx() != oldDx || ball.getDy() != oldDy;
        assertTrue("Ball nảy ngang hoặc dọc khi đập góc", bounced);
    }

    @Test
    public void testBallHitsBetweenTwoBricks() {
        Brick brick2 = new Brick(
                100 + Constants.BRICK_WIDTH,
                100,
                Constants.BRICK_WIDTH,
                Constants.BRICK_HEIGHT,
                1,
                Brick.BrickType.NORMAL);

        brick.setX(100);
        brick.setY(100);
        gameModel.getBricks().add(brick2);

        ball.setX((int) (100 + Constants.BRICK_WIDTH - (Constants.BALL_DIAMETER / 2.0)));
        ball.setY(100 - Constants.BALL_DIAMETER);
        ball.setDx(0);
        ball.setDy(Constants.BALL_SPEED);

        int hp1 = brick.getHitPoints();
        int hp2 = brick2.getHitPoints();

        ball.move(0.016);
        collisionManager.checkCollisions();

        int destroyedCount = 0;
        if (brick.getHitPoints() < hp1) destroyedCount++;
        if (brick2.getHitPoints() < hp2) destroyedCount++;

        assertEquals("Ball chỉ đập một brick, không phải hai", 1, destroyedCount);
    }

    @Test
    public void testBallNearMissDoesNotCollide() {
        brick.setX(400);
        brick.setY(400);

        ball.setX(400 - Constants.BALL_DIAMETER - 1);
        ball.setY((int) (400 + Constants.BRICK_HEIGHT / 2.0));
        ball.setDx(0);
        ball.setDy(Constants.BALL_SPEED);

        int hpBefore = brick.getHitPoints();
        ball.move(0.016);
        collisionManager.checkCollisions();

        assertEquals("Ball suýt trúng thôi thì vẫn không trừ máu brick", hpBefore, brick.getHitPoints());
    }


    @Test
    public void testBallCollisionWithPaddle(){
        paddle.setX(Constants.SCREEN_WIDTH / 2 -  (Constants.PADDLE_WIDTH / 2));
        paddle.setY(Constants.SCREEN_HEIGHT / 2 - (Constants.PADDLE_HEIGHT / 2));

        ball.setX(paddle.getX() + (paddle.getWidth() / 2) - (Constants.BALL_DIAMETER / 2));
        ball.setY(paddle.getY() - Constants.BALL_DIAMETER);

        ball.setDx(Constants.BALL_SPEED);
        ball.setDy(Constants.BALL_SPEED);

        double ballDy = ball.getDy();

        ball.move(0.016);
        collisionManager.checkCollisions();

        assertEquals("Bóng va chạm với paddle sẽ làm đổi chiều Dy", -ballDy, ball.getDy(), 0.01);
    }

    @Test
    public void testBallCollisionRightWithPaddle(){
        paddle.setX(Constants.SCREEN_WIDTH / 2 - (Constants.PADDLE_WIDTH / 2));
        paddle.setY(Constants.SCREEN_HEIGHT / 2 - (Constants.PADDLE_HEIGHT / 2));

        ball.setX(paddle.getX() + (paddle.getWidth() / 2) - (Constants.BALL_DIAMETER / 2) + paddle.getWidth() / 4);
        ball.setY(paddle.getY() - Constants.BALL_DIAMETER);

        ball.setDx(Constants.BALL_SPEED);
        ball.setDy(Constants.BALL_SPEED);
        double ballDx = ball.getDx();

        ball.move(0.016);
        collisionManager.checkCollisions();

        assertEquals("Bóng va chạm phải với paddle thì Dx giữ nguyên dấu", ballDx,  ball.getDx(), 0.01);
    }

    @Test
    public void testFireBallCollisionWithBrick() {
        brick.setX(100);
        brick.setY(100);
        brick.setHitPoints(2);

        ball.setFireBall(true);
        ball.setX(100 + (Constants.BRICK_WIDTH / 2) - (Constants.BALL_DIAMETER / 2));
        ball.setY(100 - Constants.BALL_DIAMETER);
        ball.setDx(0);
        ball.setDy(Constants.BALL_SPEED);

        ball.move(0.016);
        collisionManager.checkCollisions();
        assertEquals("bóng lửa sẽ làm hitpoint của brick về 0 luôn", 0, brick.getHitPoints());
    }
}
