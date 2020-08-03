package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected int x, y;
    protected Rectangle hitBox;
    protected BufferedImage img;
    protected GameID id;
    protected Handler handler;

    public GameObject(int x, int y, BufferedImage img, GameID id, Handler handler) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.id = id;
        this.handler = handler;
        this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public GameID getId() {
        return id;
    }

    public void setId(GameID id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public abstract void drawImage(Graphics g);
    public abstract void update();
    public Rectangle getHitBox(){
        return hitBox.getBounds();
    }


}
