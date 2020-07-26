package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall extends Stationary{
   // int x, y;
   // BufferedImage wallImage;
    //int state = 2;
    //Rectangle hitBox;
    public Wall(int x, int y, BufferedImage wallImage,GameID id, Handler handler){
        super(x,y,wallImage,id, handler);
       // this.x = x;
       // this.y = y;
       // this.wallImage = wallImage;
       // this.hitBox = new Rectangle(x,y,this.wallImage.getWidth(),this.wallImage.getHeight());
    }


}
