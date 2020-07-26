package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Breakable extends Wall{
    public Breakable(int x, int y, BufferedImage wallImage, GameID id, Handler handler) {
        super(x,y,wallImage ,id, handler);
    }
}
