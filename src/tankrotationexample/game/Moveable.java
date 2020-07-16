package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Moveable {
    protected int x, y, vx, vy;
    protected float angle;
    protected int R;
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
    abstract void drawImage(Graphics g);
    private void checkBorder(){
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_SCREEN_WIDTH - 88) {
            x = GameConstants.GAME_SCREEN_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_SCREEN_HEIGHT - 80) {
            y = GameConstants.GAME_SCREEN_HEIGHT - 80;
        }
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