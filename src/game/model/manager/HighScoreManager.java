package game.model.manager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HighScoreManager {
    private static final String HIGH_SCORE_FILE = "highscores.txt";

    private static Map<String, Integer> highScores = new HashMap<>();

    static {
        loadHighScores();
    }

    public static int getHighScore(String mapName) {
        return highScores.getOrDefault(mapName, 0);
    }

    public static void loadHighScores() {
        //tạo map mới để đảm bảo dữ liệu cũ được xóa
        Map<String, Integer> loadedScores = new HashMap<>();
        File file = new File(HIGH_SCORE_FILE);

        if (!file.exists()) {
            highScores = loadedScores;
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String mapName = parts[0].trim();
                    try {
                        int score = Integer.parseInt(parts[1].trim());
                        loadedScores.put(mapName, score);
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi định dạng số trên dòng: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file highscores.txt: " + e.getMessage());
        }

        highScores = loadedScores;
    }

    public static void saveHighScore(String mapName, int newScore) {
        int currentHighScore = getHighScore(mapName);
        if (newScore > currentHighScore) {
            highScores.put(mapName, newScore);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE))) {
                for (Map.Entry<String, Integer> entry : highScores.entrySet()) {
                    writer.write(entry.getKey() + "=" + entry.getValue());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Lỗi khi lưu file highscores.txt: " + e.getMessage());
            }
        }
    }
}