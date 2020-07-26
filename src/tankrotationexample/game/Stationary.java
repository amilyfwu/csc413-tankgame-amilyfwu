package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Stationary extends GameObject{
    int state = 2;
    public Stationary(int x, int y, BufferedImage img, GameID id, Handler handler) {
        super(x, y, img, id, handler);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void update() {

    }

    @Override
    public void drawImage(Graphics g){
        if(state == 2){
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.img,x, y, null);
        }else if(state == 1){

        }
    }
}
