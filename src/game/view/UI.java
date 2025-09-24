package game.view;

import game.Constants;
import game.model.GameModel;

import java.awt.*;

// Class này vẽ mấy cái đồ hoạ đè lên gameplay của người chơi
// Score, Health, Level number,...
public class UI {
    private final GameModel model;

    public UI(GameModel model) {
        this.model = model;
    }

    public void render(Graphics2D g2) {
        //Vẽ score
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + model.getScoreSystem().getScore(), Constants.BRICK_WIDTH / 2, Constants.BRICK_HEIGHT);


    }
}
