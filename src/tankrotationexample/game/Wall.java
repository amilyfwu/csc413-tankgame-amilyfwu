package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall {
    int x, y;
    BufferedImage wallImage;
    int state = 2;
    Rectangle hitBox;
    public Wall(int x, int y, BufferedImage wallImage){
        this.x = x;
        this.y = y;
        this.wallImage = wallImage;
        this.hitBox = new Rectangle(x,y,this.wallImage.getWidth(),this.wallImage.getHeight());
    }
    public Rectangle getHitBox(){
        return hitBox.getBounds();
    }
    public abstract void drawImage(Graphics g);
    public abstract boolean canBreak();
}
