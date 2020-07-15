package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Moveable {
    private int x;
    private int y;
    private int vx;
    private int vy;
    private float angle;
    private final int R = 7;
    Rectangle hitBox;
    BufferedImage img;

    public Moveable(int x, int y, int vx, int vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.img = img;
        this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
    }

    abstract void moveForwards();
    abstract void update();
    abstract void drawImage();
    private void checkBorder(){

    }
    void moveForwardOrBackward(){
        vx = (int) Math.round(R*Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R*Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation(x,y);
    }
}
