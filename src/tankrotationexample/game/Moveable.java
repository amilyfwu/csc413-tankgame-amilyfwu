package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Moveable extends GameObject {
    protected int vx, vy;
    protected float angle;
    protected int R;
    //Rectangle hitBox;
    //BufferedImage img;

    public Moveable(int x, int y, int vx, int vy, float angle, BufferedImage img,GameID id, Handler handler) {
        super(x, y, img,id,handler);
        //this.x = x;
        //this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
       // this.img = img;
       // this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());

    }

    abstract void moveForwards();
   // abstract void update();
    //abstract void changeR(int changeR);
    abstract void setR();
  //  abstract void drawImage(Graphics g);

    public Rectangle getHitBox(){
        return hitBox.getBounds();
    }

    private void checkBorder(){
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT- 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    void moveForward(){
        vx = (int) Math.round(R*Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R*Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation(x,y);
    }
    void moveBackward(){
        vx = (int) Math.round(R*Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R*Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        this.hitBox.setLocation(x,y);
    }


}
