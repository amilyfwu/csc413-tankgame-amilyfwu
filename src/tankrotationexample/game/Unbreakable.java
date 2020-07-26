package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Unbreakable extends Wall{
    //int x, y;
    //BufferedImage wallImage;
    //int state = 2;

    public Unbreakable(int x, int y, BufferedImage wallImage,GameID id, Handler handler) {
        super(x,y,wallImage,id,handler);
    }
}
