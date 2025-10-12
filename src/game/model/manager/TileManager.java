package game.model.manager;

import game.Constants;
import game.model.entity.Brick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//0 = empty
//1,2,3 = normal (HP = number)
//9 = unbreakable
public class TileManager {
    public List<Brick> loadMap(String mapPath) {
        int brickHeight = Constants.BRICK_HEIGHT;
        int brickWidth = Constants.BRICK_WIDTH;
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
                        Brick.BrickType brickType;
                        int hp;

                        switch (type) {
                            case 9 -> {
                                brickType = Brick.BrickType.UNBREAKABLE;
                                hp = Integer.MAX_VALUE;
                            }
                            default -> {
                                brickType = Brick.BrickType.NORMAL;
                                hp = type;
                            }
                        }

                        bricks.add(new Brick(x + 32, y, brickWidth, brickHeight, hp, brickType));
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
