package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends Moveable {
    //int x, y, vx, vy;
    //float angle;
    //int R = 7;
    //BufferedImage img;
    //Rectangle hitBox;
    Boolean show;

    public Bullet(int x, int y, int vx, int vy, float angle, BufferedImage img,GameID id, Handler handler) {
        //this.x = x;
        //this.y = y;
        //this.angle = angle;
        //this.img = img;
        //this.hitBox = new Rectangle(x,y, this.img.getWidth(),this.img.getHeight());
        super(x,y,vx,vy,angle,img,id, handler);
        this.show = true;
    }

    @Override
    void setR(){
        R = 7;
    }

    void moveForwards(){
        setR();
        moveForward();
    }

    //void checkBorder was here

    public void update(){
        moveForwards();
        //if it hits something it disappears
    }

    public void drawImage(Graphics g){
        AffineTransform rotation = AffineTransform.getTranslateInstance(x,y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth()/2.0, this.img.getHeight()/2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        //added from video
        //
        g2d.setColor(Color.RED);
        g2d.drawRect(x, y, this.img.getWidth(),this.img.getHeight());
    }
}
