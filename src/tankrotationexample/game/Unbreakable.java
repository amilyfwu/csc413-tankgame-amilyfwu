package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Unbreakable extends Wall{
    //int x, y;
    //BufferedImage wallImage;
    int state = 2;

    public Unbreakable(int x, int y, BufferedImage wallImage) {
        super(x,y,wallImage);
    }

    @Override
    public void drawImage(Graphics g){
        if(state == 2){
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.img,x, y, null);
        }else if(state == 1){

        }
    }

    @Override
    public boolean canBreak() {
        return false;
    }
}
