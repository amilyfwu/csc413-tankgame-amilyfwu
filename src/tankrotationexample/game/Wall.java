package tankrotationexample.game;

import com.sun.xml.internal.ws.handler.HandlerException;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall extends Stationary{
    public Wall(int x, int y, BufferedImage wallImage, GameID id, Handler handler){
        super(x,y,wallImage,id, handler);
    }
}
