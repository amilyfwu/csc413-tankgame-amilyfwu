package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected int x, y; // vx, vy;
   // protected float angle;
   // protected int R;
    Rectangle hitBox;
    BufferedImage img;

    public GameObject(int x, int y, BufferedImage img) {
        this.x = x;
        this.y = y;
        //this.vx = vx;
        //this.vy = vy;
        //this.angle = angle;
        this.img = img;
        this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());

    }
    public abstract void drawImage(Graphics g);
    public abstract void update();
    public Rectangle getHitBox(){
        return hitBox.getBounds();
    }


}
