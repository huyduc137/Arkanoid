package game.model;

import game.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TileManager {
    int brickWidth = Constants.BRICK_WIDTH;
    int brickHeight = Constants.BRICK_HEIGHT;

    public List<Brick> loadMap(String mapPath) {
        List<Brick> bricks = new ArrayList<>();

        InputStream is = getClass().getResourceAsStream("/" + mapPath);
        if (is == null) {
            throw new RuntimeException("Map file not found: " + mapPath);
        }

        // Tự động đóng br khi try catch kết thúc
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            int row = 0;

            while((line = br.readLine()) != null) {
                String[] loadedLine = line.trim().split("\\s+"); //Xoá space ở cuối, tách số

                for(int col = 0; col < loadedLine.length; col++) {
                    int type = Integer.parseInt(loadedLine[col]);

                    if(type > 0) {
                        int x = col * brickWidth;
                        int y = row * brickHeight;

                        //Type của brick hiện tại chỉ theo hit points, khi thêm type khác cần chỉnh cách thêm brick
                        bricks.add(new Brick(x, y, brickWidth, brickHeight, type));
                    }
                }
                row++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't load map file:" + mapPath, e);
        }

        return bricks;
    }
}
