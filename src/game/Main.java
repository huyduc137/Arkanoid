package game;

import game.controller.GameController;
import game.model.GameModel;
import game.view.GameView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameModel model = new GameModel();
            GameController controller = new GameController(model);
            GameView view = new GameView(model, controller);

            controller.setViewGame(view);

            view.setVisible(true);
        });
    }
}