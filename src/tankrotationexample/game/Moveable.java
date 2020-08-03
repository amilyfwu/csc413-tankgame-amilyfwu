package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.image.BufferedImage;

public abstract class Moveable extends GameObject {
    protected int vx, vy;
    protected float angle;
    protected int R;

    public Moveable(int x, int y, int vx, int vy, float angle, BufferedImage img,GameID id, Handler handler) {
        super(x, y, img,id, handler);
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    abstract void moveForwards();

    abstract void setR();

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
        vx = ((int) Math.round(R*Math.cos(Math.toRadians(angle))))*-1;
        vy = ((int) Math.round(R*Math.sin(Math.toRadians(angle))))*-1;
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation(x,y);
    }

}
