package game;

import game.view.GameView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameView gameView = new GameView();
            gameView.setVisible(true);
        });
    }
}